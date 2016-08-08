**Goal**

Write an app that scans all files on the external storage (SD card) and collects following information:

1. Names and sizes of 10 biggest files
2. Average file size
3. 5 most frequent file extensions (with their frequencies)

Functional Acceptance Criteria:

* This information should be displayed in a convenient way.
* App allows the user to start and stop scanning.
* The app must display progress of ongoing scan.
* App contains a button allowing the user to share obtained statistics via standard Android sharing menu (button is not active until the activity receives the statistics from the  server).
* App shows a status bar notification while it scans the external storage.
* UI must handle screen orientation changes.
* When app is sent to background (by pressing HOME button), the scan should continue.
* When app is stopped by the user (by pressing BACK button), the scan must be stopped immediately.
 

**Architectual and implementation notes**

* Fragment and AsyncTask are used to achieve background processing and application state preservation during configuration changes (screen rotation)
* SQLite is used to store statistial data. It is used as source for analysis. Two tables are used: files (columns: name, path, length) and extenstions (columns: extension and count)
* To support Back button click functionality main activity is derived from FragmentActivity which keeps reference to the fragment.
* Notifications support. 
  * Display scan progress in sync with progress bar on the main screen.
  * Based on stage of the scan display different text and icons in notification drawer. 
  * Return to running activity when notification is clicked.
* Run time permissions.
  * Basic support for prompting to allow access to external storage. 
  * Decline perminssion is not supported in this release.
* Sharing is done using standard Android chooser dialog. Data being shared is string representation of JSON formatted results. Here is a sample of data being sent as an extra:
```json
{
  "average_length": 140428,
  "extensions_data": [
    {
      "extension": "class",
      "count": 357
    },
    {
      "extension": "adoc",
      "count": 244
    },
    {
      "extension": "java",
      "count": 136
    },
    {
      "extension": "sql",
      "count": 88
    },
    {
      "extension": "xml",
      "count": 46
    }
  ],
  "files_data": [
    {
      "name": "contexthub-adminws-0.1.0.jar",
      "path": "/storage/emulated/0/contexthub-adminws/bin/target/contexthub-adminws-0.1.0.jar",
      "length": 65982404
    },
    {
      "name": "contexthub-adminws-0.1.0.jar",
      "path": "/storage/emulated/0/contexthub-adminws/target/contexthub-adminws-0.1.0.jar",
      "length": 65982404
    },
    {
      "name": "pack-a16bf5535bd043dfd05e9db1ceece23ab5ea2ee8.pack",
      "path": "/storage/emulated/0/contexthub-adminws/.git/objects/pack/pack-a16bf5535bd043dfd05e9db1ceece23ab5ea2ee8.pack",
      "length": 714240
    },
    {
      "name": "contexthub-adminws-0.1.0.jar.original",
      "path": "/storage/emulated/0/contexthub-adminws/bin/target/contexthub-adminws-0.1.0.jar.original",
      "length": 244769
    },
    {
      "name": "contexthub-adminws-0.1.0.jar.original",
      "path": "/storage/emulated/0/contexthub-adminws/target/contexthub-adminws-0.1.0.jar.original",
      "length": 244769
    },
    {
      "name": "pack-a16bf5535bd043dfd05e9db1ceece23ab5ea2ee8.idx",
      "path": "/storage/emulated/0/contexthub-adminws/.git/objects/pack/pack-a16bf5535bd043dfd05e9db1ceece23ab5ea2ee8.idx",
      "length": 168400
    },
    {
      "name": "index.html",
      "path": "/storage/emulated/0/contexthub-adminws/bin/target/classes/static/docs/index.html",
      "length": 122585
    },
    {
      "name": "index.html",
      "path": "/storage/emulated/0/contexthub-adminws/bin/target/generated-docs/index.html",
      "length": 122585
    },
    {
      "name": "index.html",
      "path": "/storage/emulated/0/contexthub-adminws/target/classes/static/docs/index.html",
      "length": 122585
    },
    {
      "name": "index.html",
      "path": "/storage/emulated/0/contexthub-adminws/target/generated-docs/index.html",
      "length": 122585
    }
  ]
}
```
* To present data about 10 longest files and 5 most used extesions [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) package is used. 
  * 10 biggest files analysis is presented as horizontal bar chart.
  * 5 most frequently used extensions analysis is represented as pie chart.
  ![alt text](https://github.com/vadadler/android/blob/master/storagescanner/images/top5extensions.png)

**Testing and debugging notes**

* Testing is done on Nexus 5 running 6.0.1
* To test Back button functionality.
  * In Android Studio setup logging filter to **OA.**
  * Start scan.
  * Observe messages in Logcat monitor similar to below
```
08-07 17:33:47.202 17748-18528/com.odinarts.android.storagescanner I/OA.DoWork: class
08-07 17:33:47.206 17748-18528/com.odinarts.android.storagescanner I/OA.DoWork: class
08-07 17:33:47.211 17748-18528/com.odinarts.android.storagescanner I/OA.DoWork: class
08-07 17:33:47.222 17748-18528/com.odinarts.android.storagescanner I/OA.DoWork: class
```
  * Press Back button and observe the following two messages indicating application had stopped.
```
08-07 17:33:47.251 17748-18528/com.odinarts.android.storagescanner I/OA.DoWork: class
08-07 17:33:47.257 17748-17748/com.odinarts.android.storagescanner I/OA.DoWorkFragment: Back button was pressed. Stop scanning.
08-07 17:33:47.268 17748-18528/com.odinarts.android.storagescanner I/OA.DoWork: Work is cancelled
```
