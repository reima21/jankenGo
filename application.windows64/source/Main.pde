import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;
 
Minim minim;  //Minim型変数であるminimの宣言
AudioPlayer player, loopSound;  //サウンドデータ格納用の変数
//AudioOutput out;

Start start;
CastleL castleL;
CastleR castleR;
Status status;
Timer timer;
Battle battle;
//Commandはじゃんけんの種類
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
int x_L = 240, y_L = 150; //Barの初期座標(左)
int x_R = 950, y_R = 150; //Barの初期座標(右)

PFont myFont;

int display_num; //バトルモード変更
Boolean canShootL=true, canShootR=true;
int time_sL=0, time_sR=0;

void setup(){
  background(#ffffff);
  size(1200,750);
  start = new Start();
  status = new Status();
  castleL = new CastleL();
  castleR = new CastleR();
  timer = new Timer();
  
  minim = new Minim(this);  //初期化
  player = minim.loadFile("boin.mp3");  //groove.mp3をロードする
  //loopSound = minim.loadFile("MainBGM.mp3");
  //out = minim.getLineOut();
  //loopSound = minim.loadFile("MainBGM.mp3");  //groove.mp3をロードする
  //loopSound.play();  //再生
  
  //ArrayListのインスタンス生成
  //3行確保のため、setup()で最初にaddで3つのArrayListを追加しておく
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
  
  //スタート画面
  howtoplay = loadImage("howtoplay.png");
  title = loadImage("title.png");
  
  //じゃんけん画像の読み込み
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
  
  
  //城画像の読み込み
  castle_r = loadImage("castle_r.png");
  castle_l = loadImage("castle_l.png");
  stage = loadImage("stage.png");
  battle = new Battle(gue, choki, pa);
}

void draw(){
  if (display_num == 0) {
    start.display();
    if (start.push) display_num = 1;
  } else if (display_num == 1 && status.hp1 > 0 && status.hp2 > 0){
    //if(timer.time == 1){
    //  loopSound = minim.loadFile("MainBGM.mp3");  //groove.mp3をロードする
    //  loopSound.play();  //再生
    //}
    //制限時間orどちらかのHPがなくなるまでタワーディフェンス
   background(#67b5b7);
   line(240, 150, 240, 750); // 縦左
   line(480, 150, 480, 750); // 縦中左
   line(720, 150, 720, 750); // 縦中右
   line(960, 150, 960, 750); // 縦右
   line(240, 150, 960, 150); // 横上
   line(240, 350, 960, 350); // 横中
   line(240, 550, 960, 550); // 横下
 
    //じゃんけんの動きを行ごとに描画
    for(int i = 0;i < 3;i++) {
      for(int j = 0; j < rights.get(i).size();j++) {
        rights.get(i).get(j).move(2);
        rights.get(i).get(j).display();
      }
      for(int j = 0; j < lefts.get(i).size();j++) {
        lefts.get(i).get(j).move(1);
        lefts.get(i).get(j).display();
      }
      //空でない、かつ、城にあったらリムーブ
      if(!lefts.get(i).isEmpty() && lefts.get(i).get(0).x+80 > 950){
        lefts.get(i).remove(0);
        //右の城に衝突した際にプレイヤー2のHPを30減らす
        status.Hp2(30);
      }
      if(!rights.get(i).isEmpty() && rights.get(i).get(0).x < 240) {
        rights.get(i).remove(0);
        //左の城に衝突した際にプレイヤー1のHPを30減らす    
        status.Hp1(30);
      }
     
     //空でない、かつ、じゃんけんアイテムがすれ違ったら、判定によりリムーブ処理
     //leftsにrightsを渡す
     //負け、あいこでリムーブ
     if((!rights.get(i).isEmpty()) && (!lefts.get(i).isEmpty())){
       if((lefts.get(i).get(0).x >= rights.get(i).get(0).x)){
         int judge = lefts.get(i).get(0).judge(rights.get(i).get(0));
         if(judge == 1){
           rights.get(i).remove(0);
         } else if(judge == 0) {
           // make item
           int ran = int(random(5));
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
    
    //城描画
    castleL.display();
    castleR.display();
    
    //Bar描画
    barL.display();
    barR.display();
    
    //ステータスディスプレイ
    status.display();
    
    //タワーディフェンスのディスプレイ
    timer.timeCalculation();
    timer.display();
    time_sL++;
    time_sR++;
    
    if(status.hp1 == 0 || status.hp2 == 0){
      timer.timeLimit = 5; //バトルモードのために5秒
      display_num = 2; //バトルモードに変更
      timer.timecnt = 0;
      timer.time = 0;
    }
    
    //タワーディフェンス勝敗判定
    if(status.hp1 == status.hp2) battle.battlejudge = 0;
    else if(status.hp1 > status.hp2) battle.battlejudge = 1;
    else battle.battlejudge = 2;
    battle.set();
    
    }else if(display_num == 2){
      //バトルモード
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

void keyPressed(){
  if(display_num == 1){
    //タワーディフェンスのキーボード入力
    if(key == 'w') selectJL++;  //じゃんけんの種類をセレクト
    if(key == 's' && canShootL){
      //じゃんけんの座標、種類、画像をコンストラクタで初期化し、行ごとに追加していく
      player = minim.loadFile("boin.mp3");  //groove.mp3をロードする
      player.play();  //再生
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
        //行操作(上昇)
        selectRowL -= 1;
        y_L -= 200;
      }
    }
    
    if(key == 'a'){
      barL.moveDown();
      if(selectRowL < 2){
        //行操作(下降)
        selectRowL += 1;
        y_L += 200;
      }
    }
    
    if(key == '@') selectJR++;  //じゃんけんの種類をセレクト
    if(key == ';' && canShootR){
      //じゃんけんの座標、種類、画像をコンストラクタで初期化し、行ごとに追加していく
      player = minim.loadFile("boin.mp3");  //groove.mp3をロードする
      player.play();  //再生
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
        //行操作(上昇)
        selectRowR -= 1;
        y_R -= 200;
      }
    }
    if(key == ':'){
      barR.moveDown();
      if(selectRowR < 2){
        //行操作(下降)
        selectRowR += 1;
        y_R += 200;
      }
    }
  }else /*if(display_num == 2)*/{
    //バトルモードのキーボード入力
    //じゃんけんの種類を更新
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

void mouseClicked() {
  if (start.x <= mouseX && mouseX <= start.x+200 && start.y <= mouseY && mouseY <= start.y+100) {
    start.push = true;
  }
}