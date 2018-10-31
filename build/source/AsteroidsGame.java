import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class AsteroidsGame extends PApplet {

//your variable declarations here
PImage[] fire = new PImage[8];
Star [] field;
Spaceship ship;
boolean hyperSpace;
int opacity = 60;
public void setup()
{
  //your code here
  
  frameRate(120);
  
  fire[0] = loadImage("Intense_Fire_1.gif");
  fire[1] = loadImage("Intense_Fire_2.gif");
  fire[2] = loadImage("Intense_Fire_3.gif");
  fire[3] = loadImage("Intense_Fire_4.gif");
  fire[4] = loadImage("Intense_Fire_5.gif");
  fire[5] = loadImage("Intense_Fire_6.gif");
  fire[6] = loadImage("Intense_Fire_7.gif");
  fire[7] = loadImage("Intense_Fire_8.gif");
  ship = new Spaceship();
  field = new Star[150];
  for(int i = 0; i < field.length; i++){
    field[i] = new Star((int)(Math.random()*600), (int)(Math.random()*600));
  }
  ship.setX(width/2);
  ship.setY(height/2);
}
public void draw()
{
  //your code here
  if(!hyperSpace){
    background(0);
    strokeWeight(1);
    for(int i = 0; i < field.length; i++){
      field[i].twinkle();
      field[i].show();
    }
  }
  else{
    opacity--;
    for(int i = 0; i < field.length; i++){
      field[i].twinkle();
      field[i].show();
    }
    if(opacity == 0){
      hyperSpace = false;
    }
  }
  ship.move();
  ship.show();
}

public void keyPressed(){
  if(key == 'w'){
    ship.accelerate(0.5f);
    ship.setFired(true);
  }
  if(key == 's'){
    ship.accelerate(-0.5f);
  }
  if(key == 'a'){
    ship.turn(-15);
  }
  if(key == 'd'){
    ship.turn(15);
  }
  if(key == ' '){
    ship.setDirectionX(0);
    ship.setDirectionY(0);
    hyperSpace = true;
    opacity = 60;
  }
}

public void keyReleased(){
  if(key == 'w'){
    ship.setFired(false);
  }
  if(key == ' '){
    ship.setX((int)(Math.random()*600));
    ship.setY((int)(Math.random()*600));
    ship.setDirectionX(0);
    ship.setDirectionY(0);
    ship.setPointDirection((int)(Math.random()*360));
  }
}
abstract class Floater //Do NOT modify the Floater class! Make changes in the Spaceship class
{
  protected int corners;  //the number of corners, a triangular floater has 3
  protected int[] xCorners;
  protected int[] yCorners;
  protected int myColor;
  protected double myCenterX, myCenterY; //holds center coordinates
  protected double myDirectionX, myDirectionY; //holds x and y coordinates of the vector for direction of travel
  protected double myPointDirection; //holds current direction the ship is pointing in degrees
  abstract public void setX(int x);
  abstract public int getX();
  abstract public void setY(int y);
  abstract public int getY();
  abstract public void setDirectionX(double x);
  abstract public double getDirectionX();
  abstract public void setDirectionY(double y);
  abstract public double getDirectionY();
  abstract public void setPointDirection(int degrees);
  abstract public double getPointDirection();

  //Accelerates the floater in the direction it is pointing (myPointDirection)
  public void accelerate (double dAmount)
  {
    //convert the current direction the floater is pointing to radians
    double dRadians =myPointDirection*(Math.PI/180);
    //change coordinates of direction of travel
    myDirectionX += ((dAmount) * Math.cos(dRadians));
    myDirectionY += ((dAmount) * Math.sin(dRadians));
  }
  public void turn (int nDegreesOfRotation)
  {
    //rotates the floater by a given number of degrees
    myPointDirection+=nDegreesOfRotation;
  }
  public void move ()   //move the floater in the current direction of travel
  {
    //change the x and y coordinates by myDirectionX and myDirectionY
    myCenterX += myDirectionX;
    myCenterY += myDirectionY;

    //wrap around screen
    if(myCenterX >width)
    {
      myCenterX = 0;
    }
    else if (myCenterX<0)
    {
      myCenterX = width;
    }
    if(myCenterY >height)
    {
      myCenterY = 0;
    }

    else if (myCenterY < 0)
    {
      myCenterY = height;
    }
  }
  public void show ()  //Draws the floater at the current position
  {
    fill(myColor);
    stroke(myColor);

    //translate the (x,y) center of the ship to the correct position
    translate((float)myCenterX, (float)myCenterY);

    //convert degrees to radians for rotate()
    float dRadians = (float)(myPointDirection*(Math.PI/180));

    //rotate so that the polygon will be drawn in the correct direction
    rotate(dRadians);

    //draw the polygon
    beginShape();
    for (int nI = 0; nI < corners; nI++)
    {
      vertex(xCorners[nI], yCorners[nI]);
    }
    endShape(CLOSE);

    //"unrotate" and "untranslate" in reverse order
    rotate(-1*dRadians);
    translate(-1*(float)myCenterX, -1*(float)myCenterY);
  }
}
class Spaceship extends Floater
{
    private PImage[] fire;
    private boolean fired;
    private float opacity;
    //your code here

    public Spaceship(){
      myColor = 255;
      corners = 4;
      xCorners = new int[corners];
      yCorners = new int[corners];
      xCorners[0] = -8;
      yCorners[0] = -8;
      xCorners[1] = 16;
      yCorners[1] = 0;
      xCorners[2] = -8;
      yCorners[2] = 8;
      xCorners[3] = -2;
      yCorners[3] = 0;
      fire = new PImage[8];
      fire[0] = loadImage("Intense_Fire_1.gif");
      fire[1] = loadImage("Intense_Fire_2.gif");
      fire[2] = loadImage("Intense_Fire_3.gif");
      fire[3] = loadImage("Intense_Fire_4.gif");
      fire[4] = loadImage("Intense_Fire_5.gif");
      fire[5] = loadImage("Intense_Fire_6.gif");
      fire[6] = loadImage("Intense_Fire_7.gif");
      fire[7] = loadImage("Intense_Fire_8.gif");
    }

    public void show ()  //Draws the floater at the current position
    {
      if(hyperSpace == false){
        fill(myColor);
        stroke(myColor);
      }else{
        tint(255, 255 + opacity);
        // fill(255,255,255, 127 - opacity);
        // stroke(255,255,255, 60 - opacity);
      }

      //translate the (x,y) center of the ship to the correct position
      translate((float)myCenterX, (float)myCenterY);

      //convert degrees to radians for rotate()
      float dRadians = (float)(myPointDirection*(Math.PI/180));

      //rotate so that the polygon will be drawn in the correct direction
      rotate(dRadians);

      //draw the polygon
      beginShape();
      for (int nI = 0; nI < corners; nI++)
      {
        vertex(xCorners[nI], yCorners[nI]);
      }
      endShape(CLOSE);

      if(fired == true){
        image(fire[frameCount%8],-40,-23,45,45);
      }

      //"unrotate" and "untranslate" in reverse order
      rotate(-1*dRadians);
      translate(-1*(float)myCenterX, -1*(float)myCenterY);
    }

    public void setX(int x){myCenterX = x;}
    public int getX(){return (int)myCenterX;}
    public void setY(int y){myCenterY = y;}
    public int getY(){return (int)myCenterY;}
    public void setDirectionX(double x){myDirectionX = x;}
    public double getDirectionX(){return myDirectionX;}
    public void setDirectionY(double y){myDirectionY = y;}
    public double getDirectionY(){return myDirectionY;}
    public void setPointDirection(int degrees){myPointDirection = degrees;}
    public double getPointDirection(){return (double)myPointDirection;}

    public void setFired(boolean x){fired = x;}
}
class Star //note that this class does NOT extend Floater
{
  //your code here
  private int starX, starY;
  private double glitterX;
  public Star(int x, int y){
    starX = x;
    starY = y;
  }
  public void twinkle(){
    glitterX = (float)((Math.random()*9)+1);
  }
  public void show(){
    ellipse(starX,starY,(float)glitterX,(float)glitterX);
  }
}
  public void settings() {  size(600,600);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "AsteroidsGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
