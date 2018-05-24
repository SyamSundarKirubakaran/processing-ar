package processing.ar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.opengl.GLSurfaceView;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;
import processing.core.PApplet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PAR implements GLSurfaceView.Renderer{

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
                        message(T_PROMPT_MESSAGE,C_SUPPORTED);
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
                message = C_DEVICE;
                exception = e.toString();
            }

            if(message != null){
                message(T_ALERT_MESSAGE,message+"--"+exception);
            }

            // Create default config and check if supported.
//            Config config = new Config(session);
//            if (!session.isSupported(config)) {
//                message("3", "This Device doesn't support AR.!");
//            }
//            session.configure(config);
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

}