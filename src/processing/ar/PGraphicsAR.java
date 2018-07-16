package processing.ar;

import com.google.ar.core.Frame;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import processing.opengl.PGL;
import processing.opengl.PGLES;
import processing.opengl.PGraphics3D;
import processing.opengl.PGraphicsOpenGL;

import static processing.ar.PSurfaceAR.*;

public class PGraphicsAR extends PGraphics3D {

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
//        super.backgroundImpl();
        if (session != null) {
            displayRotationHelper.updateSessionIfNeeded(session);
            PGraphicsAR.showWarning("Graphics: inside IF background()");
            session.setCameraTextureName(backgroundRenderer.getTextureId());
            try {
                Frame frame = session.update();
                backgroundRenderer.draw(frame);
            } catch (CameraNotAvailableException e) {
                PGraphicsAR.showWarning("Graphics: Exception inside IF");
            }
        }
        PGraphicsAR.showWarning("Graphics: background()");
    }

    @Override
    public void surfaceChanged() {
        PGraphicsAR.showWarning("Graphics: surfaceChanged()");
    }

    public void updateProjection(){
        //yet to implement
        PGraphicsAR.showWarning("Graphics: updateProjection()");
    }

    public void updateView(){
        //yet to implement
        PGraphicsAR.showWarning("Graphics: updateView()");
    }

    public void updateInferences(){
        updateProjection();
        updateView();
        setARCamera();
    }

    protected void setARCamera() {

        modelview.set(PSurfaceAR.viewmtx[0], PSurfaceAR.viewmtx[4], PSurfaceAR.viewmtx[8],  PSurfaceAR.viewmtx[12],
                PSurfaceAR.viewmtx[1], PSurfaceAR.viewmtx[5], PSurfaceAR.viewmtx[9],  PSurfaceAR.viewmtx[13],
                PSurfaceAR.viewmtx[2], PSurfaceAR.viewmtx[6], PSurfaceAR.viewmtx[10], PSurfaceAR.viewmtx[14],
                PSurfaceAR.viewmtx[3], PSurfaceAR.viewmtx[7], PSurfaceAR.viewmtx[11], PSurfaceAR.viewmtx[15]);

        float tx = -defCameraX;
        float ty = -defCameraY;
        float tz = -defCameraZ;
        modelview.translate(tx,ty,tz);

        camera.set(modelview);
        PGraphicsAR.showWarning("Graphics: ARCamera()");
    }

}
