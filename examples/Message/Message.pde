import processing.ar.*;
color backgroundcolor = color(0, 0, 0);

void setup()
{
  fullScreen();
  orientation(PORTRAIT);
  PAR a = new PAR(this);
}

void draw()
{
  background(backgroundcolor);
}
