package processing.ar;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.*;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.*;
import processing.android.AppComponent;
import processing.ar.render.PBackground;
import processing.ar.render.RotationHandler;
import processing.core.PGraphics;
import processing.opengl.PGLES;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PSurfaceGLES;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.io.File;
import java.io.InputStream;

public class PSurfaceAR extends PSurfaceGLES {

    private GLSurfaceView surfaceView;
    protected AndroidARRenderer renderer;

    private Session session;
    private RotationHandler displayRotationHelper;

    private final PBackground backgroundRenderer = new PBackground();

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


    public PSurfaceAR(PGraphics graphics, AppComponent appComponent, SurfaceHolder surfaceHolder) {
        super(graphics,appComponent,surfaceHolder);
        this.sketch = graphics.parent;
        this.graphics = graphics;
        this.component = appComponent;
        this.pgl = (PGLES)((PGraphicsOpenGL)graphics).pgl;
        displayRotationHelper = new RotationHandler(activity);
        surfaceView = new SurfaceViewAR(activity);
        PGraphics.showWarning("Reached - 2");
        ProgressDialog progressdialog = new ProgressDialog(activity);
        progressdialog.setMessage("Searching for Surfaces");
        progressdialog.show();
    }

    @Override
    public Context getContext() {
        PGraphics.showWarning("Reached - 5");
        return activity;
    }

    @Override
    public void finish() {
        PGraphics.showWarning("Reached - 6");
        sketch.getActivity().finish();
    }

    @Override
    public AssetManager getAssets() {
        PGraphics.showWarning("Reached - 7");
        return sketch.getContext().getAssets();
    }

    @Override
    public void startActivity(Intent intent) {
        PGraphics.showWarning("Reached - 8");
        sketch.getContext().startActivity(intent);
    }

    @Override
    public void initView(int sketchWidth, int sketchHeight) {
        Window window = sketch.getActivity().getWindow();


        window.getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        window.setContentView(surfaceView);
        PGraphics.showWarning("Reached - 9");
    }

    @Override
    public String getName() {
        PGraphics.showWarning("Reached - 10");
        return sketch.getActivity().getComponentName().getPackageName();
    }

    @Override
    public void setOrientation(int which) {
        PGraphics.showWarning("Reached - 11");
    }

    @Override
    public File getFilesDir() {
        PGraphics.showWarning("Reached - 12");
        return sketch.getActivity().getFilesDir();
    }

    @Override
    public InputStream openFileInput(String filename) {
        PGraphics.showWarning("Reached - 13");
        return null;
    }

    @Override
    public File getFileStreamPath(String path) {
        PGraphics.showWarning("Reached - 14");
        return sketch.getActivity().getFileStreamPath(path);
    }

    @Override
    public void dispose() {
        PGraphics.showWarning("Reached - 15");
    }


    public class SurfaceViewAR extends GLSurfaceView {
        public SurfaceViewAR(Context context) {
            super(context);

            PGraphics.showWarning("Reached - 4");

            final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
            final boolean supportsGLES2 = configurationInfo.reqGlEsVersion >= 0x20000;

            if (!supportsGLES2) {
                throw new RuntimeException("OpenGL ES 2.0 is not supported by this device.");
            }

            setFocusable(true);
            setFocusableInTouchMode(true);
            requestFocus();

            setPreserveEGLContextOnPause(true);
            setEGLContextClientVersion(2);
            setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            setRenderer(getARRenderer());
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return sketch.surfaceTouchEvent(event);
        }


        @Override
        public boolean onKeyDown(int code, android.view.KeyEvent event) {
            sketch.surfaceKeyDown(code, event);
            return super.onKeyDown(code, event);
        }


        @Override
        public boolean onKeyUp(int code, android.view.KeyEvent event) {
            sketch.surfaceKeyUp(code, event);
            return super.onKeyUp(code, event);
        }
    }

    public AndroidARRenderer getARRenderer() {
        renderer = new AndroidARRenderer();
        return renderer;
    }


    protected class AndroidARRenderer implements GLSurfaceView.Renderer {
        public AndroidARRenderer() {
            PGraphics.showWarning("Reached - 3");
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            PGraphics.showWarning("Reached - 16");
            GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
            backgroundRenderer.createOnGlThread(activity);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            PGraphics.showWarning("Reached - 20");
            displayRotationHelper.onSurfaceChanged(width, height);
            GLES20.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            PGraphics.showWarning("Reached - 21");
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

            if (session == null) {
                return;
            }
            displayRotationHelper.updateSessionIfNeeded(session);

            try {
                session.setCameraTextureName(backgroundRenderer.getTextureId());
                Frame frame = session.update();
                backgroundRenderer.draw(frame);
            } catch (Throwable t) {
                PGraphics.showWarning("Exception on the OpenGL thread");
            }
        }
    }

    @Override
    public void startThread() {
        PGraphics.showWarning("Reached - 17");
    }

    @Override
    public void pauseThread() {
        PGraphics.showWarning("Reached - 18");
        if (session != null) {
            displayRotationHelper.onPause();
            surfaceView.onPause();
            session.pause();
        }
    }

    @Override
    public void resumeThread() {
        PGraphics.showWarning("Reached - 19");
        if (session == null) {
            String message = null;
            String exception = null;
            try {
                switch (ArCoreApk.getInstance().requestInstall(sketch.getActivity(), true)) {
                    case INSTALL_REQUESTED:
                        message(T_ALERT_MESSAGE, C_NOT_SUPPORTED);
                        return;
                    case INSTALLED:
                        break;
                }

                if (!hasCameraPermission(sketch.getActivity())) {
                    requestCameraPermission(sketch.getActivity());
                    return;
                }

                session = new Session(activity);
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

            Config config = new Config(session);
            if (!session.isSupported(config)) {
                message(T_PROMPT_MESSAGE,C_DEVICE);
            }
            session.configure(config);

        }
        try {
            session.resume();
        } catch (CameraNotAvailableException e) {
        }
        surfaceView.onResume();
        displayRotationHelper.onResume();
    }

    public void message(String _title, String _message) {
        final Activity parent = activity;
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
