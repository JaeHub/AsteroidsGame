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
ArrayList <Asteroids> rocks;
ArrayList <Bullet> bullets;
Spaceship ship;
boolean hyperSpace;
int rocksNum = 26;
public void setup()
{
  //your code here
  
  frameRate(120);
  
  fire[0] = loadImage("data/Intense_Fire_1.gif");
  fire[1] = loadImage("data/Intense_Fire_2.gif");
  fire[2] = loadImage("data/Intense_Fire_3.gif");
  fire[3] = loadImage("data/Intense_Fire_4.gif");
  fire[4] = loadImage("data/Intense_Fire_5.gif");
  fire[5] = loadImage("data/Intense_Fire_6.gif");
  fire[6] = loadImage("data/Intense_Fire_7.gif");
  fire[7] = loadImage("data/Intense_Fire_8.gif");
  field = new Star[150];
  for(int i = 0; i < field.length; i++){
    field[i] = new Star((int)(Math.random()*600), (int)(Math.random()*600));
  }
  ship = new Spaceship();
  bullets = new ArrayList <Bullet> ();
  rocks = new ArrayList <Asteroids> ();
  for(int i = 0; i < rocksNum; i++){
    rocks.add(i, new Asteroids((int)(Math.random()*600), (int)(Math.random()*600), (int)((Math.random()*3)+1)));
  }
  ship.setX(width/2);
  ship.setY(height/2);
}
public void draw()
{
  //your code here
  if(!hyperSpace){
    background(0);
    for(int i = 0; i < field.length; i++){
      field[i].twinkle();
      field[i].show();
    }
  }
  else{
    ship.opacity--;
    for(int i = 0; i < field.length; i++){
      field[i].show();
    }
    if(ship.opacity == 0){
      hyperSpace = false;
    }
  }
  ship.move();
  ship.show();
  for(int i = 0; i < bullets.size(); i++){
    bullets.get(i).move();
    bullets.get(i).show();
  }
  for(int i = 0; i < rocksNum; i++){
    rocks.get(i).setDirectionX(1.5f);
    rocks.get(i).setDirectionY(1.5f);
    rocks.get(i).move();
    rocks.get(i).show();
    //Checks distance between ships and rocks
    if(dist(ship.getX(),ship.getY(), rocks.get(i).getX(), rocks.get(i).getY()) <= 23){
      rocks.remove(i);
      rocks.add(i, new Asteroids(0, (int)(Math.random()*600),(int)((Math.random()*3)+1)));
    }
  }
  for(int i = 0; i < bullets.size(); i++){
    for(int n = 0; n < rocksNum; n++){
      if(dist(bullets.get(i).getX(), bullets.get(i).getY(), rocks.get(n).getX(), rocks.get(n).getY()) <= 23){
        bullets.remove(i);
        rocksNum -= 1;
        rocks.remove(n);
        // rocks.add(i, new Asteroids(0, (int)(Math.random()*600),(int)((Math.random()*3)+1)));
        break;
      }
    }
  }
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
    ship.opacity -= 255;
  }
  if(key == 'j'){
    bullets.add(new Bullet(ship));
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
    hyperSpace = false;
    ship.opacity += 255;
  }
}
class Asteroids extends Floater
{
  private int astRot;
  public Asteroids(int x, int y, int rot){
    astRot = rot;
    myColor = 0xff808487;
    corners = 7;
    xCorners = new int[corners];
    yCorners = new int[corners];
    xCorners[0] = -8;
    yCorners[0] = -12;
    xCorners[1] = 3;
    yCorners[1] = -18;
    xCorners[2] = 12;
    yCorners[2] = -16;
    xCorners[3] = 16;
    yCorners[3] = 0;
    xCorners[4] = 12;
    yCorners[4] = 16;
    xCorners[5] = -8;
    yCorners[5] = 16;
    xCorners[6] = -2;
    yCorners[6] = 0;
    setX(x);
    setY(y);
  }
  public void show(){
    super.show();
  }
  public void move(){
    turn(astRot);
    super.move();
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
}
class Bullet extends Floater{
  public Bullet (Spaceship theShip){
    myCenterX = theShip.getX();
    myCenterY = theShip.getY();
    myPointDirection = theShip.getPointDirection();
    double dRadians = (double)(theShip.getPointDirection()*(Math.PI/180));
    myDirectionX = (5 * Math.cos(dRadians) + theShip.getDirectionX());
    myDirectionY = (5 * Math.sin(dRadians) + theShip.getDirectionY());
  }

  public void show(){
    super.show();
    stroke(0xffb02227);
    fill(0xffb02227);
    ellipse(getX(),getY(), 3, 3);
  }

  public void move(){
    super.move();
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
      opacity = 0;
      fire = new PImage[8];
      fire[0] = loadImage("data/Intense_Fire_1.gif");
      fire[1] = loadImage("data/Intense_Fire_2.gif");
      fire[2] = loadImage("data/Intense_Fire_3.gif");
      fire[3] = loadImage("data/Intense_Fire_4.gif");
      fire[4] = loadImage("data/Intense_Fire_5.gif");
      fire[5] = loadImage("data/Intense_Fire_6.gif");
      fire[6] = loadImage("data/Intense_Fire_7.gif");
      fire[7] = loadImage("data/Intense_Fire_8.gif");
    }

    public void show ()  //Draws the floater at the current position
    {
      if(hyperSpace == false){
        fill(myColor);
        stroke(myColor);
      }else{
        fill(myColor);
        stroke(myColor);
        tint(255, 0);
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
    glitterX = (float)((Math.random()*8)+1);
  }
  public void show(){
    stroke(255);
    fill(255);
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
