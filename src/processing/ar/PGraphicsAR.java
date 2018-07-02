package processing.ar;

import processing.opengl.PGL;
import processing.opengl.PGLES;
import processing.opengl.PGraphics3D;
import processing.opengl.PGraphicsOpenGL;

public class PGraphicsAR extends PGraphics3D {

    public PGraphicsAR() {
    }

    @Override
    protected PGL createPGL(PGraphicsOpenGL pGraphicsOpenGL) {
        return new PGLES(pGraphicsOpenGL);
    }



}
