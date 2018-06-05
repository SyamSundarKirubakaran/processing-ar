package processing.ar;

import android.view.SurfaceHolder;
import processing.android.AppComponent;
import processing.core.PSurface;

public class PGraphicsARView extends PGraphicsAR {

    public PGraphicsARView() {}

    @Override
    public PSurface createSurface(AppComponent appComponent, SurfaceHolder surfaceHolder, boolean b) {
//        if (b) pgl.resetFBOLayer();
        return new PSurfaceAR(this,appComponent,surfaceHolder);
    }
}
