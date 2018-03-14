class Status{
  
  int hp1 = 300;
  int hp2 = 300;
  int hp2_ = 0;
  
 void display(){
   fill(#88bfbf);
   //fill(#01A9DB);
   //ステータス
   rect(0, 0, 1200, 150);
   
   //Player1のHP
   fill(#D8D8D8);
   rect(20,70,300,30);
   fill(#93ff93);
   rect(20,70,hp1,30);
   
   //Player2のHP
   fill(#D8D8D8);
   rect(870,70,300,30);
   fill(#93ff93);
   rect(870+hp2_,70,hp2,30);
    
   //Player1のキャラ選択
   if(selectJL%3 == 0){
    //グーの選択
    image(gue, 80, 10, 45, 45);
    image(choki, 130, 10, 36, 36);
    image(pa, 180, 10, 36, 36);

   }else if(selectJL%3 == 1){
    //チョキの選択
    image(gue, 80, 10, 36, 36);
    image(choki, 130, 10, 45, 45);
    image(pa, 180, 10, 36, 36);

      
   }else if(selectJL%3 == 2){
    //パーの選択
    image(gue, 80, 10, 36, 36);
    image(choki, 130, 10, 36, 36);
    image(pa, 180, 10, 45, 45);

   }
    
    
   //Player2のキャラ選択
   if(selectJR%3 == 0){
    //グーの選択
    image(gue, 1020, 10, 45, 45);
    image(choki, 1070, 10, 36, 36);
    image(pa, 1120, 10, 36, 36);

   }else if(selectJR%3 == 1){
    //チョキの選択
    image(gue, 1020, 10, 36, 36);
    image(choki, 1070, 10, 45, 45);
    image(pa, 1120, 10, 36, 36);

      
   }else if(selectJR%3 == 2){
    //パーの選択
    image(gue, 1020, 10, 36, 36);
    image(choki, 1070, 10, 36, 36);
    image(pa, 1120, 10, 45, 45);

   } 
 }
 
 void Hp1(int dx1){
   if(hp1 >= 30){
     hp1 -= dx1;
   }
 }
 
 void Hp1Recovery(int hp1R){
   if(hp1 <= 270) hp1 += hp1R;
 }
 
 void Hp2(int dx2){
   if(hp2 >= 30){
     hp2 -= dx2;
     hp2_ += dx2;
   }
 }
 
 void Hp2Recovery(int hp2R){
   if(hp2 <= 270) {
     hp2 += hp2R;
     hp2_ -= hp2R;
   }
 }
}