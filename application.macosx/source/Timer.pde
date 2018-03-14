class Timer{
  int timecnt = 0; //frameRateを記憶
  int time = 0;    //実際の時間
  int timeLimit = 60; // タイムリミット
  
  void timeCalculation(){
    timecnt++;
    if(timecnt == 60){
      timeLimit -= 1;
      timecnt = 0;
      time++;
    }
  }
  
  void display(){
    //タワーディフェンス
    textFont(myFont);
    textSize(40);
    fill(#696969);
    text("time:" + timeLimit, 520, 30, 600, 600);
    if(timeLimit == 0) {
      //タイムリミット
      timeLimit = 5; //バトルモードのために5秒
      display_num = 2; //バトルモードに変更
      timecnt = 0;
      time = 0;
    }
  }
  
  void battletime(){
    //バトル
    textFont(myFont);
    textSize(40);
    text("time:" + timeLimit, 500, 100, 600, 600);
  }
}