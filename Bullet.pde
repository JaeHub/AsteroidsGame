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
    stroke(#b02227);
    fill(#b02227);
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
