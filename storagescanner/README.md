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
