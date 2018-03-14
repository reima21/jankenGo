class Battle{
  PImage gue_img, choki_img, pa_img;
  String p1 = "gue", p2 = "gue";
  int battlejudge;
  int p1_cnt, p2_cnt;
  int p1_minus=0, p2_minus=0;
  
  Battle(PImage gue_img, PImage choki_img, PImage pa_img){
    //コンストラクタで画像を受け取る
    this.gue_img = gue_img;
    this.choki_img = choki_img;
    this.pa_img = pa_img;
  }
  
  void set(){
    //優劣設定
    //3回勝てば勝ち 引き分けは１発勝負
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
  
  void display1(){
    background(#88bfbf);
    fill(#ff0000);
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
  
  void display2(){
    background(#88bfbf);
    if(p1.equals("gue")) image(gue_img,100,200,400,400);
    else if(p1.equals("choki")) image(choki_img,100,200,400,400);
    else /*if(p1.equals("pa"))*/ image(pa_img,100,200,400,400);
    
    if(p2.equals("gue")) image(gue_img,700,200,400,400);
    else if(p2.equals("choki")) image(choki_img,700,200,400,400);
    else /*if(p2.equals("pa"))*/ image(pa_img,700,200,400,400);
  }
  
  void display3(){
    background(#88bfbf);
    if(p1.equals("gue")) image(gue_img,100,200,400,400);
    else if(p1.equals("choki")) image(choki_img,100,200,400,400);
    else /*if(p1.equals("pa"))*/ image(pa_img,100,200,400,400);
    
    if(p2.equals("gue")) image(gue_img,700,200,400,400);
    else if(p2.equals("choki")) image(choki_img,700,200,400,400);
    else /*if(p2.equals("pa"))*/ image(pa_img,700,200,400,400);
    
    fill(#ff0000);
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
  
  void display4(){
    background(#88bfbf);
    fill(#ff0000);
    textFont(myFont);
    textSize(70);
    if(p1_cnt == 0){
      text("P2 WIN!!", 450, 400);
    }else if(p2_cnt == 0){
      text("P1 WIN!!", 450, 400);
    }
  }  
}