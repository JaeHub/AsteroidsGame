//your variable declarations here
PImage[] fire = new PImage[8];
Star [] field;
ArrayList <Asteroids> rocks;
ArrayList <Bullet> bullets;
Spaceship ship;
boolean hyperSpace;
int rocksNum = 26;
int rocksCorners = (int)(Math.random()*4);
public void setup()
{
  //your code here
  size(600,600);
  frameRate(120);
  smooth();
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
  if(hyperSpace == false){
    background(0);
    for(int i = 0; i < field.length; i++){
      field[i].twinkle();
      field[i].show();
    }
    for(int i = 0; i < rocksNum; i++){
      rocks.get(i).setDirectionX(0);
      rocks.get(i).setDirectionY(0);
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
    if(bullets.get(i).getX() >= width || bullets.get(i).getY() >= height || bullets.get(i).getX() <= 0 || bullets.get(i).getY() <= 0){
      bullets.remove(i);
      i--;
    }
  }
  for(int i = 0; i < rocksNum; i++){
    rocks.get(i).setDirectionX(0.5);
    rocks.get(i).setDirectionY(0.5);
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
        break;
      }
    }
  }
}

public void keyPressed(){
  if(key == 'w'){
    ship.accelerate(0.3);
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
    ship.setDirectionX(0);
    ship.setDirectionY(0);
    hyperSpace = true;
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
    ship.opacity -= 255;
  }
}
