package processing.ar;

import android.view.SurfaceHolder;
import processing.android.AppComponent;
import processing.core.PGraphics;
import processing.opengl.PSurfaceGLES;

public class PSurfaceAR extends PSurfaceGLES {

    public PSurfaceAR(PGraphics pGraphics, AppComponent appComponent, SurfaceHolder surfaceHolder) {
        super(pGraphics, appComponent, surfaceHolder);
    }

}
