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
