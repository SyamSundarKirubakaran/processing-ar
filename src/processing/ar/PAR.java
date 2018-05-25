package processing.ar;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;
import processing.core.PApplet;

public class PAR {

    //checking if the import statement works as expected.

    private static PApplet sketch;
    private Session session;
    private static String T_ALERT_MESSAGE = "ALERT";
    private static String C_NOT_SUPPORTED = "ARCore SDK required to run this app type";
    private static String T_PROMPT_MESSAGE = "PROMPT";
    private static String C_SUPPORTED = "ARCore SDK is installed";
    private static String C_EXCEPT_INSTALL = "Please install ARCore";
    private static String C_EXCEPT_UPDATE_SDK = "Please update ARCore";
    private static String C_EXCEPT_UPDATE_APP = "Please update this app";
    private static String C_DEVICE = "This device does not support AR";

    private static final int CAMERA_PERMISSION_CODE = 0;
    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;

    //end of check

    public PAR(PApplet sketch) {
        this.sketch = sketch;
        if (session == null) {
            String message = null;
            String exception = null;
            try {
                switch (ArCoreApk.getInstance().requestInstall(sketch.getActivity(), true)) {
                    case INSTALL_REQUESTED:
                        message(T_ALERT_MESSAGE, C_NOT_SUPPORTED);
                        return;
                    case INSTALLED:
//                        ARCore is already installed
//                        message(T_PROMPT_MESSAGE,C_SUPPORTED);
                        break;
                }
                session = new Session(/* context= */ sketch.getContext());
            } catch (UnavailableArcoreNotInstalledException
                    | UnavailableUserDeclinedInstallationException e) {
                message = C_EXCEPT_INSTALL;
                exception = e.toString();
            } catch (UnavailableApkTooOldException e) {
                message = C_EXCEPT_UPDATE_SDK;
                exception = e.toString();
            } catch (UnavailableSdkTooOldException e) {
                message = C_EXCEPT_UPDATE_APP;
                exception = e.toString();
            } catch (Exception e) {
            }

            if(message != null){
                message(T_ALERT_MESSAGE,message+" -- "+exception);
            }


            if (!hasCameraPermission(sketch.getActivity())) {
                requestCameraPermission(sketch.getActivity());
            }

        }
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


    public static boolean hasCameraPermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, CAMERA_PERMISSION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCameraPermission(Activity activity) {
        ActivityCompat.requestPermissions(
                activity, new String[] {CAMERA_PERMISSION}, CAMERA_PERMISSION_CODE);
    }


}