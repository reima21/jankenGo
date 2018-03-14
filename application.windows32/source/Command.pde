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
  
  void move(int shift) {
    if(shift == 1) x += 3;
    if(shift == 2) x -= 3;
  }
  
  void display() {
    image(image, x, y, 80, 50);
  }
  
  abstract int judge(Command o);
}

class Gue extends Command {
  Gue(int x0, int y0, String _name, PImage _img) {
    super( x0, y0,_name, _img);
  }
  
  int judge(Command o) { 
    //受け取ったじゃんけんの種類によって
    //返す値を変える
    if(o.name.equals("choki")) return 1;  //勝ち
    else if(o.name.equals("gue")) return 0;  //あいこ
    else /*if(name == "pa")*/ return -1;  //負け
  }
}

class Choki extends Command {
  Choki(int x0, int y0,String _name, PImage _img) {
    super(x0, y0, _name, _img);
  }
  
  int judge(Command o) {
    if(o.name.equals("pa")) return 1;
    else if(o.name.equals("choki")) return 0;
    else /*if(name == "gue")*/ return -1;
  }
}

class Pa extends Command {
  Pa(int x0, int y0, String _name, PImage _img) {
    super(x0, y0, _name, _img);
  }
  
  int judge(Command o) { 
    if(o.name.equals("gue")) return 1;
    else if(o.name.equals("pa")) return 0;
    else /*if(name == "choki")*/ return -1;
  }
}


  