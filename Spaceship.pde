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
