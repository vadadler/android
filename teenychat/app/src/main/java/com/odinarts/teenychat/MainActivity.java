package com.odinarts.teenychat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

public class MainActivity extends Activity {

    private Handler mHandler;
    private Socket mSocket;
    private Thread mClientThread;
    private static final int SERVER_PORT = 4001;
    private static final int BUFFER_SIZE = 1024;

    // For running
    private static final String SERVER_IP = "10.0.2.2";
    private static final String OFFLINE_MESSAGES = "messages.txt";
    
    Button bSend;
    EditText etMessage;
    TextView tvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bSend = (Button) findViewById(R.id.button_send);
        etMessage = (EditText) findViewById(R.id.edittext_message);
        tvResponse = (TextView) findViewById(R.id.textview_response);
        
        tvResponse.setMovementMethod(new ScrollingMovementMethod());
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                try {
                    System.out.println("TeenyChat " + msg);
                    JSONObject msgObj = new JSONObject((String)msg.obj);
                    String message = (String) msgObj.get("msg");
                    String when = (String) msgObj.getString("server_time");
                    long timeStamp = Long.valueOf(when);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timeStamp);

                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutes = calendar.get(Calendar.MINUTE);

                    System.out.println("TeenyChat: " + message + " " + year + "/" + month + "/" + day +  " " + hour + ":" + minutes);

                    String timestamp = String.format("%02d:%02d", hour, minutes);
                    tvResponse.append(timestamp + " " + message + "\n");
                    tvResponse.invalidate();
                }
                catch(Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        };

        // Handle Enter key of the EditText component.
        etMessage.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    bSend.callOnClick();
                    handled = true;
                }
                return handled;
            }
        });

        // Send Button click listener. Send message.
        bSend.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString();
                // Build and send message.
                // {"msg":"hi","client_time":1446754551485}
                try {
                    JSONObject msg = new JSONObject();
                    msg.put("msg", message);
                    msg.put("client_time", System.currentTimeMillis());

                    PrintWriter out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(mSocket.getOutputStream())), true);
                    out.println(msg.toString());

                    etMessage.setText("");
                }
                catch (IOException ioe) {
                    // Assume socket is not connected. Save message.
                    System.out.println("IO Exception: " + ioe.getLocalizedMessage());
                    writeToFile("\n" + message);

                }
                catch(Exception e) {
                    // TODO: handle exceptions
                    System.out.println(e.getLocalizedMessage());
                }
            }
        });

        // Start client thread.
        mClientThread = new Thread(new ClientRunnable());
        mClientThread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Write message to file if there is no connectivity.
    private void writeToFile(String message) {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(openFileOutput(OFFLINE_MESSAGES, Context.MODE_PRIVATE));
            outputStreamWriter.write(message);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            System.out.println("writeToFile exception" + e.getLocalizedMessage());
        }
    }

    // Send messages stored in file when there was no connectivity.
    private String readFromFile() {
        String message = "";

        try {
            InputStream inputStream = openFileInput(OFFLINE_MESSAGES);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                message = stringBuilder.toString();

                // TODO: Don't forget to purge the file.
                writeToFile("");
            }
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("TeenyChat FileNotFoundException " + fnfe.getLocalizedMessage());
        }
        catch (IOException e) {
            System.out.println("TeenyChat Exception " + e.getLocalizedMessage());
        }

        return message;
    }

    class ClientRunnable implements Runnable {
        @Override
        public void run() {
            try {
                InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
                mSocket = new Socket(serverAddress, SERVER_PORT);

                // Read messages from server.
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(mSocket.getInputStream()), BUFFER_SIZE);
                while(mSocket.isConnected()) {
                    String msg = in.readLine();
                    //System.out.println("TeenyChat " + msg);
                    Message message = mHandler.obtainMessage();
                    message.obj = msg;
                    mHandler.dispatchMessage(message);
                }
            }
            catch (UnknownHostException uhe) {
                System.out.println("TeenyChat UnknownHostException: " + uhe.getLocalizedMessage());
            }
            catch (IOException ioe) {
                System.out.println("TeenyChat IOException: " + ioe.getLocalizedMessage());
            }
            catch (Exception e) {
                System.out.println("TeenyChat Exception: " + e.getLocalizedMessage());
            }
        }
    }
}
