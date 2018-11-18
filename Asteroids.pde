class Asteroids extends Floater
{
  private int astRot;
  public Asteroids(int x, int y, int rot){
    astRot = rot;
    myColor = #808487;
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
