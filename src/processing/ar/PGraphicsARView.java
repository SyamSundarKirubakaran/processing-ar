package processing.ar;

import android.view.SurfaceHolder;
import processing.android.AppComponent;
import processing.core.PGraphics;
import processing.core.PSurface;
import processing.opengl.PGraphics3D;

public class PGraphicsARView extends PGraphics3D {

    public PGraphicsARView() {
    }

    @Override
    public PSurface createSurface(AppComponent appComponent, SurfaceHolder surfaceHolder, boolean b) {
        if (b) pgl.resetFBOLayer();
        PGraphics.showWarning("Reached - 1");
        return new PSurfaceAR(this,appComponent,surfaceHolder);
    }
}
