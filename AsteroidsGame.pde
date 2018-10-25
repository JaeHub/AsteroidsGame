//your variable declarations here
PImage[] fire = new PImage[8];
Star [] field;
Spaceship ship;
public void setup()
{
  //your code here
  size(600,600);
  frameRate(120);
  smooth();
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
  ship.setX(300);
  ship.setY(300);
}
public void draw()
{
  //your code here
  background(0);
  strokeWeight(1);
  for(int i = 0; i < field.length; i++){
    field[i].twinkle();
    field[i].show();
  }
  ship.move();
  ship.show();
}

public void keyPressed(){
  if(key == 'w'){
    ship.accelerate(0.5);
    ship.setFired(true);
  }
  if(key == 's'){
    ship.accelerate(-0.5);
  }
  if(key == 'a'){
    ship.turn(-15);
  }
  if(key == 'd'){
    ship.turn(15);
  }
  if(key == ' '){
    ship.disappear();
  }
}

public void keyReleased(){
  if(key == 'w'){
    ship.setFired(false);
  }
  if(key == ' '){
    ship.appear();
    ship.setX((int)(Math.random()*600));
    ship.setY((int)(Math.random()*600));
    ship.setDirectionX(0);
    ship.setDirectionY(0);
    ship.setPointDirection((int)(Math.random()*360));
  }
}
