import processing.ar.*;
import processing.ar.render.*;
PShape cube;
public void setup() {
    fullScreen(AR);
    cube = createShape(BOX, 200);
}
public void draw() {
    lights();
    background(0);
    shape(cube);
}