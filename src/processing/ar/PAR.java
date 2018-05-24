package processing.ar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.ar.core.Session;
import processing.core.PApplet;

public class PAR {

    //checking if the import statement works as expected.

    private static PApplet sketch;
    private Session session;

    //end of check


    public PAR(PApplet sketch) {
        this.sketch = sketch;
        message("Title","Context");
    }

    public static void message(String _title, String _message) {
        final Activity parent = sketch.getActivity();
        final String message = _message;
        final String title = _title;

        parent.runOnUiThread(new Runnable() {
            public void run() {
                new AlertDialog.Builder(parent)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
            }
        });

    }
}