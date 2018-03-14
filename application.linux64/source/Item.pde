abstract class Item{
  int x, y;
  
  Item(int x0, int y0){
    x = x0;
    y = y0;
  }
  abstract void judge(int j, int row);
  abstract void display();
}

class Heart extends Item{
  Heart(int x0, int y0){
    super(x0, y0);
  }
  void judge(int j, int row){
    if(j == 1){
      status.Hp1Recovery(30);
    }else{
      status.Hp2Recovery(30);
    }
  }
  
  void display(){
    //fill(#ffffff);
    image(heart_img, x, y, 50, 50);
    //rect(x, y, 50, 50);
  }
}

class Three extends Item{
  Three(int x0, int y0){
    super(x0, y0);
  }
  void judge(int j, int row){
    if(j == 1){
      if(lefts.get(row).get(0).name.equals("gue")) {
        if(int(random(2)) == 0){
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
        if(int(random(2)) == 0){
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
  
  void display(){
    //fill(#ffffff);
    image(three_img, x, y-30, 100, 100);
    //rect(x, y, 50, 50);
  }
}