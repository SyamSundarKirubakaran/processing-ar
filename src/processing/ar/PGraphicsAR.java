package processing.ar;

import android.opengl.Matrix;
import processing.opengl.PGL;
import processing.opengl.PGLES;
import processing.opengl.PGraphics3D;
import processing.opengl.PGraphicsOpenGL;

import static processing.ar.PSurfaceAR.session;

public class PGraphicsAR extends PGraphics3D {
    
    float[] resultant = new float[16];

    public PGraphicsAR() {
    }

    @Override
    protected PGL createPGL(PGraphicsOpenGL pGraphicsOpenGL) {
        PGraphicsAR.showWarning("Graphics: Creation");
        return new PGLES(pGraphicsOpenGL);
    }

    @Override
    public void beginDraw() {
        super.beginDraw();
        updateInferences();
        PGraphicsAR.showWarning("Graphics: BeginDraw()");
    }

    @Override
    protected void backgroundImpl() {
        if (session != null) {
            PSurfaceAR.performRendering();
        }
        PGraphicsAR.showWarning("Graphics: background()");
    }

    @Override
    public void surfaceChanged() {
        PGraphicsAR.showWarning("Graphics: surfaceChanged()");
    }

    public void updateInferences(){
        setAR();
    }

    protected void setAR() {
        resetMatrix();
        if(PSurfaceAR.viewmtx != null && PSurfaceAR.anchorMatrix != null) {
            Matrix.multiplyMM(resultant, 0, PSurfaceAR.viewmtx, 0, PSurfaceAR.anchorMatrix, 0);
            modelview.set(resultant[0], resultant[4], resultant[8], resultant[12],
                    resultant[1], resultant[5], resultant[9], resultant[13],
                    resultant[2], resultant[6], resultant[10], resultant[14],
                    resultant[3], resultant[7], resultant[11], resultant[15]);
            float tx = -defCameraX;
            float ty = -defCameraY;
            float tz = -defCameraZ;
            modelview.translate(tx, ty, tz);
//            applyProjection(PSurfaceAR.projmtx[0], PSurfaceAR.projmtx[4], PSurfaceAR.projmtx[8], PSurfaceAR.projmtx[12],
//                    PSurfaceAR.projmtx[1], PSurfaceAR.projmtx[5], PSurfaceAR.projmtx[9], PSurfaceAR.projmtx[13],
//                    PSurfaceAR.projmtx[2], PSurfaceAR.projmtx[6], PSurfaceAR.projmtx[10], PSurfaceAR.projmtx[14],
//                    PSurfaceAR.projmtx[3], PSurfaceAR.projmtx[7], PSurfaceAR.projmtx[11], PSurfaceAR.projmtx[15]);
            updateProjmodelview();
        }
    }
}
