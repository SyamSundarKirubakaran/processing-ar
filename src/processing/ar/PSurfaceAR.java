package processing.ar;

import android.view.SurfaceHolder;
import com.google.ar.core.Session;
import processing.android.AppComponent;
import processing.ar.render.Background;
import processing.ar.render.RotationHandler;
import processing.core.PGraphics;
import processing.opengl.PGLES;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PSurfaceGLES;

public class PSurfaceAR extends PSurfaceGLES {

    private SurfaceViewGLES gl;

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
}
