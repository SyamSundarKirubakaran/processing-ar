import processing.ar.*;
import processing.ar.render.*;
PShape cube;
public void setup() {
    fullScreen(AR);
    orientation(PORTRAIT);
    cube = createShape(BOX, 400);
}
public void draw() {
    PPlane.setPlaneColor(0x00BCD4FF);
    lights();
    background(0);
    shape(cube);
}