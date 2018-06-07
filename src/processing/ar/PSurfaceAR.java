package processing.ar;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.view.*;
import com.google.ar.core.Session;
import processing.android.AppComponent;
import processing.ar.render.Background;
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

    private SurfaceViewGLES gl;
    private GLSurfaceView surfaceView;
    protected AndroidARRenderer renderer;

    private Session session;
    private RotationHandler displayRotationHelper;

    private final Background backgroundRenderer = new Background();


    public PSurfaceAR(PGraphics graphics, AppComponent appComponent, SurfaceHolder surfaceHolder) {
        super(graphics,appComponent,surfaceHolder);
        this.sketch = graphics.parent;
        this.graphics = graphics;
        this.component = appComponent;
        this.pgl = (PGLES)((PGraphicsOpenGL)graphics).pgl;
        surfaceView = new SurfaceViewAR(activity);
        PGraphics.showWarning("Reached - 2");
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
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {

        }
    }


}
