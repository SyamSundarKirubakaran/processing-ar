package processing.ar;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
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
    private GLSurfaceView surfaceV;
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
    }

    @Override
    public Context getContext() {
        return sketch.getContext();
    }

    @Override
    public void finish() {
        sketch.getActivity().finish();
    }

    @Override
    public AssetManager getAssets() {
        return sketch.getContext().getAssets();
    }

    @Override
    public void startActivity(Intent intent) {
        sketch.getContext().startActivity(intent);
    }

    @Override
    public void initView(int sketchWidth, int sketchHeight) {
        Window window = sketch.getActivity().getWindow();

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        window.setContentView(surfaceView);
    }

    @Override
    public String getName() {
        return sketch.getActivity().getComponentName().getPackageName();
    }

    @Override
    public void setOrientation(int which) {
    }

    @Override
    public File getFilesDir() {
        return sketch.getActivity().getFilesDir();
    }

    @Override
    public InputStream openFileInput(String filename) {
        return null;
    }

    @Override
    public File getFileStreamPath(String path) {
        return sketch.getActivity().getFileStreamPath(path);
    }

    @Override
    public void dispose() {
    }


    public class SurfaceViewAR extends SurfaceViewGLES {
        public SurfaceViewAR(Context context) {
            super(context,surfaceView.getHolder());

            final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
            final boolean supportsGLES2 = configurationInfo.reqGlEsVersion >= 0x20000;

            if (!supportsGLES2) {
                throw new RuntimeException("OpenGL ES 2.0 is not supported by this device.");
            }

            setFocusable(true);
            setFocusableInTouchMode(true);
            requestFocus();

            setRenderer(getARRenderer());
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


}
