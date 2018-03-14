class Start {
  int x=500, y=250;
  boolean push=false;
  
  void display() {
    background(#88bfbf);
    stroke(#00608d);
    noFill();
    rect(480,260,240,50);
    fill(#D8D8D8);
    fill(#00608d);
    textFont(myFont);
    textSize(40);
    image(title,360,60);
    image(howtoplay,210,400,800,300);
    text("Game Start", 490, 300);
  }
}