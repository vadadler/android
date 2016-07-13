package com.odinarts.android.iq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DumbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dumb);

        Bundle bundle = getIntent().getExtras();
        String how = bundle.getString("how");

        ((TextView)findViewById(R.id.textViewDumbStatus)).setText(how);
    }
}
