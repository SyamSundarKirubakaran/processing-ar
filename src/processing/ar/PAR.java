package processing.ar;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;
import processing.core.PApplet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PAR implements GLSurfaceView.Renderer {

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
//                        message(T_PROMPT_MESSAGE,C_SUPPORTED);
                        break;
                }
                session = new Session(/* context= */ sketch.getContext());
            } catch (UnavailableArcoreNotInstalledException
                    | UnavailableUserDeclinedInstallationException e) {
//                message = C_EXCEPT_INSTALL;
//                exception = e.toString();
            } catch (UnavailableApkTooOldException e) {
//                message = C_EXCEPT_UPDATE_SDK;
//                exception = e.toString();
            } catch (UnavailableSdkTooOldException e) {
//                message = C_EXCEPT_UPDATE_APP;
//                exception = e.toString();
            } catch (Exception e) {
//                message = C_DEVICE;
//                exception = e.toString();
            }

//            if(message != null){
//                message(T_ALERT_MESSAGE,message+"--"+exception);
//            }


            if (!hasCameraPermission(sketch.getActivity())) {
                requestCameraPermission(sketch.getActivity());
//                if (!shouldShowRequestPermissionRationale(sketch.getActivity())) {
//                     Permission denied with checking "Do not ask again".
//                    launchPermissionSettings(sketch.getActivity());
//                }
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

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }

    public static boolean hasCameraPermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, CAMERA_PERMISSION)
                == PackageManager.PERMISSION_GRANTED;
    }

    /** Check to see we have the necessary permissions for this app, and ask for them if we don't. */
    public static void requestCameraPermission(Activity activity) {
        ActivityCompat.requestPermissions(
                activity, new String[] {CAMERA_PERMISSION}, CAMERA_PERMISSION_CODE);
    }

//    /** Check to see if we need to show the rationale for this permission. */
//    public static boolean shouldShowRequestPermissionRationale(Activity activity) {
//        return ActivityCompat.shouldShowRequestPermissionRationale(activity, CAMERA_PERMISSION);
//    }
//
//    /** Launch Application Setting to grant permission. */
//    public static void launchPermissionSettings(Activity activity) {
//        Intent intent = new Intent();
//        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
//        activity.startActivity(intent);
//    }

}