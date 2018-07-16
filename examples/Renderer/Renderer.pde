import processing.ar.*;
import processing.ar.render.*;
PShape cube;
public void setup() {
    fullScreen(AR);
    cube = createShape(BOX, 400);
}
public void draw() {
    lights();
    translate(width/2,height/2);
    rotateX(200);
    rotateY(200);
    shape(cube);
}