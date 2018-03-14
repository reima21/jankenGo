import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import ddf.minim.effects.*; 
import ddf.minim.signals.*; 
import ddf.minim.spi.*; 
import ddf.minim.ugens.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Main extends PApplet {







 
Minim minim;  //Minim\u578b\u5909\u6570\u3067\u3042\u308bminim\u306e\u5ba3\u8a00
AudioPlayer player, loopSound;  //\u30b5\u30a6\u30f3\u30c9\u30c7\u30fc\u30bf\u683c\u7d0d\u7528\u306e\u5909\u6570
//AudioOutput out;

Start start;
CastleL castleL;
CastleR castleR;
Status status;
Timer timer;
Battle battle;
//Command\u306f\u3058\u3083\u3093\u3051\u3093\u306e\u7a2e\u985e
ArrayList<ArrayList<Command>> rights, lefts;
ArrayList<ArrayList<Item>> items;

BarL barL;
BarR barR;
int selectJL = 0, selectJR = 0;
int selectRowL = 0, selectRowR = 0;
PImage title;
PImage howtoplay;
PImage heart_img, three_img;
PImage castle_r, castle_l;
PImage stage;
PImage gue, gueR_img, gueL_img;
PImage choki, chokiR_img, chokiL_img;
PImage pa, paR_img, paL_img;
int x_L = 240, y_L = 150; //Bar\u306e\u521d\u671f\u5ea7\u6a19(\u5de6)
int x_R = 950, y_R = 150; //Bar\u306e\u521d\u671f\u5ea7\u6a19(\u53f3)

PFont myFont;

int display_num; //\u30d0\u30c8\u30eb\u30e2\u30fc\u30c9\u5909\u66f4
Boolean canShootL=true, canShootR=true;
int time_sL=0, time_sR=0;

public void setup(){
  background(0xffffffff);
  
  start = new Start();
  status = new Status();
  castleL = new CastleL();
  castleR = new CastleR();
  timer = new Timer();
  
  minim = new Minim(this);  //\u521d\u671f\u5316
  player = minim.loadFile("boin.mp3");  //groove.mp3\u3092\u30ed\u30fc\u30c9\u3059\u308b
  //loopSound = minim.loadFile("MainBGM.mp3");
  //out = minim.getLineOut();
  //loopSound = minim.loadFile("MainBGM.mp3");  //groove.mp3\u3092\u30ed\u30fc\u30c9\u3059\u308b
  //loopSound.play();  //\u518d\u751f
  
  //ArrayList\u306e\u30a4\u30f3\u30b9\u30bf\u30f3\u30b9\u751f\u6210
  //3\u884c\u78ba\u4fdd\u306e\u305f\u3081\u3001setup()\u3067\u6700\u521d\u306badd\u30673\u3064\u306eArrayList\u3092\u8ffd\u52a0\u3057\u3066\u304a\u304f
  rights = new ArrayList<ArrayList<Command>>();
  rights.add(new ArrayList<Command>());
  rights.add(new ArrayList<Command>());
  rights.add(new ArrayList<Command>());
  lefts = new ArrayList<ArrayList<Command>>();
  lefts.add(new ArrayList<Command>());
  lefts.add(new ArrayList<Command>());
  lefts.add(new ArrayList<Command>());
  
  //item add 3row
  items = new ArrayList<ArrayList<Item>>();
  items.add(new ArrayList<Item>());
  items.add(new ArrayList<Item>());
  items.add(new ArrayList<Item>());
  
  barL = new BarL(x_L, y_L); //(240,150)
  barR = new BarR(x_R, y_R); //(950,150)
  
  myFont = loadFont("AmericanTypewriter-48.vlw");
  display_num = 0;  //start display
  
  //\u30b9\u30bf\u30fc\u30c8\u753b\u9762
  howtoplay = loadImage("howtoplay.png");
  title = loadImage("title.png");
  
  //\u3058\u3083\u3093\u3051\u3093\u753b\u50cf\u306e\u8aad\u307f\u8fbc\u307f
  pa = loadImage("pa.png");
  choki = loadImage("choki.png");
  gue = loadImage("gue.png");
  paR_img = loadImage("paR.png");
  chokiR_img = loadImage("chokiR.png");
  gueR_img = loadImage("gueR.png");
  paL_img = loadImage("paL.png");
  chokiL_img = loadImage("chokiL.png");
  gueL_img = loadImage("gueL.png");
  
  heart_img = loadImage("heart.png");
  three_img = loadImage("three.png");
  
  
  //\u57ce\u753b\u50cf\u306e\u8aad\u307f\u8fbc\u307f
  castle_r = loadImage("castle_r.png");
  castle_l = loadImage("castle_l.png");
  stage = loadImage("stage.png");
  battle = new Battle(gue, choki, pa);
}

public void draw(){
  if (display_num == 0) {
    start.display();
    if (start.push) display_num = 1;
  } else if (display_num == 1 && status.hp1 > 0 && status.hp2 > 0){
    //if(timer.time == 1){
    //  loopSound = minim.loadFile("MainBGM.mp3");  //groove.mp3\u3092\u30ed\u30fc\u30c9\u3059\u308b
    //  loopSound.play();  //\u518d\u751f
    //}
    //\u5236\u9650\u6642\u9593or\u3069\u3061\u3089\u304b\u306eHP\u304c\u306a\u304f\u306a\u308b\u307e\u3067\u30bf\u30ef\u30fc\u30c7\u30a3\u30d5\u30a7\u30f3\u30b9
   background(0xff67b5b7);
   line(240, 150, 240, 750); // \u7e26\u5de6
   line(480, 150, 480, 750); // \u7e26\u4e2d\u5de6
   line(720, 150, 720, 750); // \u7e26\u4e2d\u53f3
   line(960, 150, 960, 750); // \u7e26\u53f3
   line(240, 150, 960, 150); // \u6a2a\u4e0a
   line(240, 350, 960, 350); // \u6a2a\u4e2d
   line(240, 550, 960, 550); // \u6a2a\u4e0b
 
    //\u3058\u3083\u3093\u3051\u3093\u306e\u52d5\u304d\u3092\u884c\u3054\u3068\u306b\u63cf\u753b
    for(int i = 0;i < 3;i++) {
      for(int j = 0; j < rights.get(i).size();j++) {
        rights.get(i).get(j).move(2);
        rights.get(i).get(j).display();
      }
      for(int j = 0; j < lefts.get(i).size();j++) {
        lefts.get(i).get(j).move(1);
        lefts.get(i).get(j).display();
      }
      //\u7a7a\u3067\u306a\u3044\u3001\u304b\u3064\u3001\u57ce\u306b\u3042\u3063\u305f\u3089\u30ea\u30e0\u30fc\u30d6
      if(!lefts.get(i).isEmpty() && lefts.get(i).get(0).x+80 > 950){
        lefts.get(i).remove(0);
        //\u53f3\u306e\u57ce\u306b\u885d\u7a81\u3057\u305f\u969b\u306b\u30d7\u30ec\u30a4\u30e4\u30fc2\u306eHP\u309230\u6e1b\u3089\u3059
        status.Hp2(30);
      }
      if(!rights.get(i).isEmpty() && rights.get(i).get(0).x < 240) {
        rights.get(i).remove(0);
        //\u5de6\u306e\u57ce\u306b\u885d\u7a81\u3057\u305f\u969b\u306b\u30d7\u30ec\u30a4\u30e4\u30fc1\u306eHP\u309230\u6e1b\u3089\u3059    
        status.Hp1(30);
      }
     
     //\u7a7a\u3067\u306a\u3044\u3001\u304b\u3064\u3001\u3058\u3083\u3093\u3051\u3093\u30a2\u30a4\u30c6\u30e0\u304c\u3059\u308c\u9055\u3063\u305f\u3089\u3001\u5224\u5b9a\u306b\u3088\u308a\u30ea\u30e0\u30fc\u30d6\u51e6\u7406
     //lefts\u306brights\u3092\u6e21\u3059
     //\u8ca0\u3051\u3001\u3042\u3044\u3053\u3067\u30ea\u30e0\u30fc\u30d6
     if((!rights.get(i).isEmpty()) && (!lefts.get(i).isEmpty())){
       if((lefts.get(i).get(0).x >= rights.get(i).get(0).x)){
         int judge = lefts.get(i).get(0).judge(rights.get(i).get(0));
         if(judge == 1){
           rights.get(i).remove(0);
         } else if(judge == 0) {
           // make item
           int ran = PApplet.parseInt(random(5));
           if(ran == 2) {  // When 2 of {0,1,2,3,4}, make item
             items.get(i).add(new Heart(lefts.get(i).get(0).x, lefts.get(i).get(0).y));
           }else if(ran == 3){
             items.get(i).add(new Three(lefts.get(i).get(0).x, lefts.get(i).get(0).y));
           }
           lefts.get(i).remove(0);
           rights.get(i).remove(0);
         } else {
           lefts.get(i).remove(0);
         }
       }
     }
     
     if(!lefts.get(i).isEmpty() && !items.get(i).isEmpty()){
       if(items.get(i).get(0).x <= lefts.get(i).get(0).x+80){
         items.get(i).get(0).judge(1, i);
         items.get(i).remove(0);
       }
     } 
     if(!rights.get(i).isEmpty() && !items.get(i).isEmpty()){
       if(items.get(i).get(0).x+50 >= rights.get(i).get(0).x){
         items.get(i).get(0).judge(2, i);
         items.get(i).remove(0);
       }
     }
 
   
     // item display
     for(int j = 0; j < items.get(i).size(); j++) items.get(i).get(j).display();
    }
    
    //\u57ce\u63cf\u753b
    castleL.display();
    castleR.display();
    
    //Bar\u63cf\u753b
    barL.display();
    barR.display();
    
    //\u30b9\u30c6\u30fc\u30bf\u30b9\u30c7\u30a3\u30b9\u30d7\u30ec\u30a4
    status.display();
    
    //\u30bf\u30ef\u30fc\u30c7\u30a3\u30d5\u30a7\u30f3\u30b9\u306e\u30c7\u30a3\u30b9\u30d7\u30ec\u30a4
    timer.timeCalculation();
    timer.display();
    time_sL++;
    time_sR++;
    
    if(status.hp1 == 0 || status.hp2 == 0){
      timer.timeLimit = 5; //\u30d0\u30c8\u30eb\u30e2\u30fc\u30c9\u306e\u305f\u3081\u306b5\u79d2
      display_num = 2; //\u30d0\u30c8\u30eb\u30e2\u30fc\u30c9\u306b\u5909\u66f4
      timer.timecnt = 0;
      timer.time = 0;
    }
    
    //\u30bf\u30ef\u30fc\u30c7\u30a3\u30d5\u30a7\u30f3\u30b9\u52dd\u6557\u5224\u5b9a
    if(status.hp1 == status.hp2) battle.battlejudge = 0;
    else if(status.hp1 > status.hp2) battle.battlejudge = 1;
    else battle.battlejudge = 2;
    battle.set();
    
    }else if(display_num == 2){
      //\u30d0\u30c8\u30eb\u30e2\u30fc\u30c9
      timer.timeCalculation();
      if(timer.time < 5){
        battle.display1();
        timer.battletime();
      }else if(timer.time < 6){
        battle.display2();
      }else{
        battle.display3();
        if(timer.time == 7) {
          timer.timecnt = 0;
          timer.time = 0;
          timer.timeLimit = 5;
          battle.p1_cnt -= battle.p1_minus;
          battle.p2_cnt -= battle.p2_minus;
          battle.p1_minus = 0;
          battle.p2_minus = 0;
          if(battle.p1_cnt == 0 || battle.p2_cnt == 0) display_num = 3;
        }
      }
    }else if(display_num == 3){
      battle.display4();
    }
}

public void keyPressed(){
  if(display_num == 1){
    //\u30bf\u30ef\u30fc\u30c7\u30a3\u30d5\u30a7\u30f3\u30b9\u306e\u30ad\u30fc\u30dc\u30fc\u30c9\u5165\u529b
    if(key == 'w') selectJL++;  //\u3058\u3083\u3093\u3051\u3093\u306e\u7a2e\u985e\u3092\u30bb\u30ec\u30af\u30c8
    if(key == 's' && canShootL){
      //\u3058\u3083\u3093\u3051\u3093\u306e\u5ea7\u6a19\u3001\u7a2e\u985e\u3001\u753b\u50cf\u3092\u30b3\u30f3\u30b9\u30c8\u30e9\u30af\u30bf\u3067\u521d\u671f\u5316\u3057\u3001\u884c\u3054\u3068\u306b\u8ffd\u52a0\u3057\u3066\u3044\u304f
      player = minim.loadFile("boin.mp3");  //groove.mp3\u3092\u30ed\u30fc\u30c9\u3059\u308b
      player.play();  //\u518d\u751f
      if(selectJL%3 == 0) lefts.get(selectRowL%3).add(new Gue(x_L+40, y_L+80, "gue", gueL_img));
      if(selectJL%3 == 1) lefts.get(selectRowL%3).add(new Choki(x_L+40, y_L+80, "choki", chokiL_img));
      if(selectJL%3 == 2) lefts.get(selectRowL%3).add(new Pa(x_L+40, y_L+80, "pa", paL_img));
      canShootL = false;
    }
    if(time_sL >= 60) {
      canShootL = true;
      time_sL = 0;
    }
    
    if(key == 'q') {
      barL.moveUp();
      if(selectRowL > 0) {
        //\u884c\u64cd\u4f5c(\u4e0a\u6607)
        selectRowL -= 1;
        y_L -= 200;
      }
    }
    
    if(key == 'a'){
      barL.moveDown();
      if(selectRowL < 2){
        //\u884c\u64cd\u4f5c(\u4e0b\u964d)
        selectRowL += 1;
        y_L += 200;
      }
    }
    
    if(key == '@') selectJR++;  //\u3058\u3083\u3093\u3051\u3093\u306e\u7a2e\u985e\u3092\u30bb\u30ec\u30af\u30c8
    if(key == ';' && canShootR){
      //\u3058\u3083\u3093\u3051\u3093\u306e\u5ea7\u6a19\u3001\u7a2e\u985e\u3001\u753b\u50cf\u3092\u30b3\u30f3\u30b9\u30c8\u30e9\u30af\u30bf\u3067\u521d\u671f\u5316\u3057\u3001\u884c\u3054\u3068\u306b\u8ffd\u52a0\u3057\u3066\u3044\u304f
      player = minim.loadFile("boin.mp3");  //groove.mp3\u3092\u30ed\u30fc\u30c9\u3059\u308b
      player.play();  //\u518d\u751f
      if(selectJR%3 == 0) rights.get(selectRowR%3).add(new Gue(x_R-70, y_R+80, "gue", gueR_img));
      if(selectJR%3 == 1) rights.get(selectRowR%3).add(new Choki(x_R-70, y_R+80, "choki", chokiR_img));
      if(selectJR%3 == 2) rights.get(selectRowR%3).add(new Pa(x_R-70, y_R+80, "pa", paR_img));
      canShootR = false;
    }
    if(time_sR >= 60) {
      canShootR = true;
      time_sR = 0;
    }
   
    if(key == '['){
      barR.moveUp();
      if(selectRowR > 0){
        //\u884c\u64cd\u4f5c(\u4e0a\u6607)
        selectRowR -= 1;
        y_R -= 200;
      }
    }
    if(key == ':'){
      barR.moveDown();
      if(selectRowR < 2){
        //\u884c\u64cd\u4f5c(\u4e0b\u964d)
        selectRowR += 1;
        y_R += 200;
      }
    }
  }else /*if(display_num == 2)*/{
    //\u30d0\u30c8\u30eb\u30e2\u30fc\u30c9\u306e\u30ad\u30fc\u30dc\u30fc\u30c9\u5165\u529b
    //\u3058\u3083\u3093\u3051\u3093\u306e\u7a2e\u985e\u3092\u66f4\u65b0
    if(timer.time < 5){
      if(key == 'q') battle.p1 = "gue";
      if(key == 'w') battle.p1 = "choki";
      if(key == 'e') battle.p1 = "pa";
      if(key == 'i') battle.p2 = "gue";
      if(key == 'o') battle.p2 = "choki";
      if(key == 'p') battle.p2 = "pa";
    }
  }
}

public void mouseClicked() {
  if (start.x <= mouseX && mouseX <= start.x+200 && start.y <= mouseY && mouseY <= start.y+100) {
    start.push = true;
  }
}
abstract class Bar{  
  // Bar\u30b9\u30fc\u30d1\u30fc\u30af\u30e9\u30b9
  int x, y;
  
  Bar(int x0, int y0){
    x = x0;
    y = y0;
  }
  
  public void moveUp() {
    // Bar\u4e0a\u3078\u306e\u52d5\u304d
    if(y > 150) y -= 200;
  }
  
  public void moveDown() {
    // Bar\u4e0b\u3078\u306e\u52d5\u304d
    if(y < height-200) y += 200;
  }
  
  public abstract void display();  // \u63cf\u753b
}

class BarL extends Bar{
  // Bar\u30b5\u30d6\u30af\u30e9\u30b9(\u5de6Bar)
  BarL(int x0, int y0){
    // \u30b3\u30f3\u30b9\u30c8\u30e9\u30af\u30bf
    super(x0, y0);
  }
  
  public void display() {
    fill(255);
    rect(x, y, 10, 200);
  }
}

class BarR extends Bar{
  // Bar\u30b5\u30d6\u30af\u30e9\u30b9(\u53f3Bar)
  BarR(int x0, int y0){
    // \u30b3\u30f3\u30b9\u30c8\u30e9\u30af\u30bf
    super(x0, y0);
  }
  
  public void display() {
    fill(255);
    rect(x, y, 10, 200);
  }
}
class Battle{
  PImage gue_img, choki_img, pa_img;
  String p1 = "gue", p2 = "gue";
  int battlejudge;
  int p1_cnt, p2_cnt;
  int p1_minus=0, p2_minus=0;
  
  Battle(PImage gue_img, PImage choki_img, PImage pa_img){
    //\u30b3\u30f3\u30b9\u30c8\u30e9\u30af\u30bf\u3067\u753b\u50cf\u3092\u53d7\u3051\u53d6\u308b
    this.gue_img = gue_img;
    this.choki_img = choki_img;
    this.pa_img = pa_img;
  }
  
  public void set(){
    //\u512a\u52a3\u8a2d\u5b9a
    //3\u56de\u52dd\u3066\u3070\u52dd\u3061 \u5f15\u304d\u5206\u3051\u306f\uff11\u767a\u52dd\u8ca0
    if(battlejudge == 0){
      p1_cnt = 1;
      p2_cnt = 1;
    }else if(battlejudge == 1){
      p1_cnt = 3;
      p2_cnt = 1;
    }else{
      p1_cnt = 1;
      p2_cnt = 3;
    }
  }
  
  public void display1(){
    background(0xff88bfbf);
    fill(0xffff0000);
    textFont(myFont);
    textSize(60);
    image(gue_img,190,180,100,100);
    text("--> q", 300, 250);
    image(choki_img,190,330,100,100);
    text("--> w", 300, 400);
    image(pa_img,190,480,100,100);
    text("--> e", 300, 550);
    
    image(gue_img,700,180,100,100);
    text("--> i", 810, 250);
    image(choki_img,700,330,100,100);
    text("--> o", 810, 400);
    image(pa_img,700,480,100,100);
    text("--> p", 810, 550);
  }
  
  public void display2(){
    background(0xff88bfbf);
    if(p1.equals("gue")) image(gue_img,100,200,400,400);
    else if(p1.equals("choki")) image(choki_img,100,200,400,400);
    else /*if(p1.equals("pa"))*/ image(pa_img,100,200,400,400);
    
    if(p2.equals("gue")) image(gue_img,700,200,400,400);
    else if(p2.equals("choki")) image(choki_img,700,200,400,400);
    else /*if(p2.equals("pa"))*/ image(pa_img,700,200,400,400);
  }
  
  public void display3(){
    background(0xff88bfbf);
    if(p1.equals("gue")) image(gue_img,100,200,400,400);
    else if(p1.equals("choki")) image(choki_img,100,200,400,400);
    else /*if(p1.equals("pa"))*/ image(pa_img,100,200,400,400);
    
    if(p2.equals("gue")) image(gue_img,700,200,400,400);
    else if(p2.equals("choki")) image(choki_img,700,200,400,400);
    else /*if(p2.equals("pa"))*/ image(pa_img,700,200,400,400);
    
    fill(0xffff0000);
    textFont(myFont);
    textSize(40);
    if(p1.equals("gue") && p2.equals("choki")){
      text("P1 WIN!!", 500, 400);
      p2_minus = 1;
    }else if(p1.equals("gue") && p2.equals("pa")){
      text("P2 WIN!!", 500, 400);
      p1_minus = 1;
    }else if(p1.equals("choki") && p2.equals("pa")){
      text("P1 WIN!!", 500, 400);
      p2_minus = 1;
    }else if(p1.equals("choki") && p2.equals("gue")){
      text("P2 WIN!!", 500, 400);
      p1_minus = 1;
    }else if(p1.equals("pa") && p2.equals("gue")){
      text("P1 WIN!!", 500, 400);
      p2_minus = 1;
    }else if(p1.equals("pa") && p2.equals("choki")){
      text("P2 WIN!!", 500, 400);
      p1_minus = 1;
    }else{
      /*if(p1 == p2) */text("DROW", 500, 400);
    }
  }
  
  public void display4(){
    background(0xff88bfbf);
    fill(0xffff0000);
    textFont(myFont);
    textSize(70);
    if(p1_cnt == 0){
      text("P2 WIN!!", 450, 400);
    }else if(p2_cnt == 0){
      text("P1 WIN!!", 450, 400);
    }
  }  
}
abstract class Castle {
  public abstract void display();
}

class CastleL{
  public void display(){
    image(castle_l,40,300,200,300);
  }
}

class CastleR{
  public void display(){
    image(castle_r,960,300,200,300);
  }
}
abstract class Command {
  PImage image;
  String name;
  int x, y;
  
  Command(int x0, int y0, String _name, PImage _img) {
    x = x0;
    y = y0;
    name = _name;
    image = _img;
  }
  
  public void move(int shift) {
    if(shift == 1) x += 3;
    if(shift == 2) x -= 3;
  }
  
  public void display() {
    image(image, x, y, 80, 50);
  }
  
  public abstract int judge(Command o);
}

class Gue extends Command {
  Gue(int x0, int y0, String _name, PImage _img) {
    super( x0, y0,_name, _img);
  }
  
  public int judge(Command o) { 
    //\u53d7\u3051\u53d6\u3063\u305f\u3058\u3083\u3093\u3051\u3093\u306e\u7a2e\u985e\u306b\u3088\u3063\u3066
    //\u8fd4\u3059\u5024\u3092\u5909\u3048\u308b
    if(o.name.equals("choki")) return 1;  //\u52dd\u3061
    else if(o.name.equals("gue")) return 0;  //\u3042\u3044\u3053
    else /*if(name == "pa")*/ return -1;  //\u8ca0\u3051
  }
}

class Choki extends Command {
  Choki(int x0, int y0,String _name, PImage _img) {
    super(x0, y0, _name, _img);
  }
  
  public int judge(Command o) {
    if(o.name.equals("pa")) return 1;
    else if(o.name.equals("choki")) return 0;
    else /*if(name == "gue")*/ return -1;
  }
}

class Pa extends Command {
  Pa(int x0, int y0, String _name, PImage _img) {
    super(x0, y0, _name, _img);
  }
  
  public int judge(Command o) { 
    if(o.name.equals("gue")) return 1;
    else if(o.name.equals("pa")) return 0;
    else /*if(name == "choki")*/ return -1;
  }
}


  
abstract class Item{
  int x, y;
  
  Item(int x0, int y0){
    x = x0;
    y = y0;
  }
  public abstract void judge(int j, int row);
  public abstract void display();
}

class Heart extends Item{
  Heart(int x0, int y0){
    super(x0, y0);
  }
  public void judge(int j, int row){
    if(j == 1){
      status.Hp1Recovery(30);
    }else{
      status.Hp2Recovery(30);
    }
  }
  
  public void display(){
    //fill(#ffffff);
    image(heart_img, x, y, 50, 50);
    //rect(x, y, 50, 50);
  }
}

class Three extends Item{
  Three(int x0, int y0){
    super(x0, y0);
  }
  public void judge(int j, int row){
    if(j == 1){
      if(lefts.get(row).get(0).name.equals("gue")) {
        if(PApplet.parseInt(random(2)) == 0){
          lefts.get(row).set(0, new Choki(lefts.get(row).get(0).x, lefts.get(row).get(0).y , "choki", chokiL_img));
        }else{
          lefts.get(row).set(0, new Pa(lefts.get(row).get(0).x, lefts.get(row).get(0).y , "pa", paL_img));
        } 
      }else if(lefts.get(row).get(0).name.equals("choki")){
        lefts.get(row).set(0, new Pa(lefts.get(row).get(0).x, lefts.get(row).get(0).y , "pa", paL_img));
      }else{
        lefts.get(row).set(0, new Choki(lefts.get(row).get(0).x, lefts.get(row).get(0).y , "choki", chokiL_img));
      }
    }else{
      if(rights.get(row).get(0).name.equals("gue")) {
        if(PApplet.parseInt(random(2)) == 0){
          rights.get(row).set(0, new Choki(rights.get(row).get(0).x, rights.get(row).get(0).y , "choki", chokiR_img));
        }else{
          rights.get(row).set(0, new Pa(rights.get(row).get(0).x, rights.get(row).get(0).y , "pa", paR_img));
        } 
      }else if(rights.get(row).get(0).name.equals("choki")){
        rights.get(row).set(0, new Pa(rights.get(row).get(0).x, rights.get(row).get(0).y , "pa", paR_img));
      }else{
        rights.get(row).set(0, new Choki(rights.get(row).get(0).x, rights.get(row).get(0).y , "choki", chokiR_img));
      }
    }
  }
  
  public void display(){
    //fill(#ffffff);
    image(three_img, x, y-30, 100, 100);
    //rect(x, y, 50, 50);
  }
}
class Start {
  int x=500, y=250;
  boolean push=false;
  
  public void display() {
    background(0xff88bfbf);
    stroke(0xff00608d);
    noFill();
    rect(480,260,240,50);
    fill(0xffD8D8D8);
    fill(0xff00608d);
    textFont(myFont);
    textSize(40);
    image(title,360,60);
    image(howtoplay,210,400,800,300);
    text("Game Start", 490, 300);
  }
}
class Status{
  
  int hp1 = 300;
  int hp2 = 300;
  int hp2_ = 0;
  
 public void display(){
   fill(0xff88bfbf);
   //fill(#01A9DB);
   //\u30b9\u30c6\u30fc\u30bf\u30b9
   rect(0, 0, 1200, 150);
   
   //Player1\u306eHP
   fill(0xffD8D8D8);
   rect(20,70,300,30);
   fill(0xff93ff93);
   rect(20,70,hp1,30);
   
   //Player2\u306eHP
   fill(0xffD8D8D8);
   rect(870,70,300,30);
   fill(0xff93ff93);
   rect(870+hp2_,70,hp2,30);
    
   //Player1\u306e\u30ad\u30e3\u30e9\u9078\u629e
   if(selectJL%3 == 0){
    //\u30b0\u30fc\u306e\u9078\u629e
    image(gue, 80, 10, 45, 45);
    image(choki, 130, 10, 36, 36);
    image(pa, 180, 10, 36, 36);

   }else if(selectJL%3 == 1){
    //\u30c1\u30e7\u30ad\u306e\u9078\u629e
    image(gue, 80, 10, 36, 36);
    image(choki, 130, 10, 45, 45);
    image(pa, 180, 10, 36, 36);

      
   }else if(selectJL%3 == 2){
    //\u30d1\u30fc\u306e\u9078\u629e
    image(gue, 80, 10, 36, 36);
    image(choki, 130, 10, 36, 36);
    image(pa, 180, 10, 45, 45);

   }
    
    
   //Player2\u306e\u30ad\u30e3\u30e9\u9078\u629e
   if(selectJR%3 == 0){
    //\u30b0\u30fc\u306e\u9078\u629e
    image(gue, 1020, 10, 45, 45);
    image(choki, 1070, 10, 36, 36);
    image(pa, 1120, 10, 36, 36);

   }else if(selectJR%3 == 1){
    //\u30c1\u30e7\u30ad\u306e\u9078\u629e
    image(gue, 1020, 10, 36, 36);
    image(choki, 1070, 10, 45, 45);
    image(pa, 1120, 10, 36, 36);

      
   }else if(selectJR%3 == 2){
    //\u30d1\u30fc\u306e\u9078\u629e
    image(gue, 1020, 10, 36, 36);
    image(choki, 1070, 10, 36, 36);
    image(pa, 1120, 10, 45, 45);

   } 
 }
 
 public void Hp1(int dx1){
   if(hp1 >= 30){
     hp1 -= dx1;
   }
 }
 
 public void Hp1Recovery(int hp1R){
   if(hp1 <= 270) hp1 += hp1R;
 }
 
 public void Hp2(int dx2){
   if(hp2 >= 30){
     hp2 -= dx2;
     hp2_ += dx2;
   }
 }
 
 public void Hp2Recovery(int hp2R){
   if(hp2 <= 270) {
     hp2 += hp2R;
     hp2_ -= hp2R;
   }
 }
}
class Timer{
  int timecnt = 0; //frameRate\u3092\u8a18\u61b6
  int time = 0;    //\u5b9f\u969b\u306e\u6642\u9593
  int timeLimit = 60; // \u30bf\u30a4\u30e0\u30ea\u30df\u30c3\u30c8
  
  public void timeCalculation(){
    timecnt++;
    if(timecnt == 60){
      timeLimit -= 1;
      timecnt = 0;
      time++;
    }
  }
  
  public void display(){
    //\u30bf\u30ef\u30fc\u30c7\u30a3\u30d5\u30a7\u30f3\u30b9
    textFont(myFont);
    textSize(40);
    fill(0xff696969);
    text("time:" + timeLimit, 520, 30, 600, 600);
    if(timeLimit == 0) {
      //\u30bf\u30a4\u30e0\u30ea\u30df\u30c3\u30c8
      timeLimit = 5; //\u30d0\u30c8\u30eb\u30e2\u30fc\u30c9\u306e\u305f\u3081\u306b5\u79d2
      display_num = 2; //\u30d0\u30c8\u30eb\u30e2\u30fc\u30c9\u306b\u5909\u66f4
      timecnt = 0;
      time = 0;
    }
  }
  
  public void battletime(){
    //\u30d0\u30c8\u30eb
    textFont(myFont);
    textSize(40);
    text("time:" + timeLimit, 500, 100, 600, 600);
  }
}
  public void settings() {  size(1200,750); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Main" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
