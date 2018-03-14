abstract class Bar{  
  // Barスーパークラス
  int x, y;
  
  Bar(int x0, int y0){
    x = x0;
    y = y0;
  }
  
  void moveUp() {
    // Bar上への動き
    if(y > 150) y -= 200;
  }
  
  void moveDown() {
    // Bar下への動き
    if(y < height-200) y += 200;
  }
  
  abstract void display();  // 描画
}

class BarL extends Bar{
  // Barサブクラス(左Bar)
  BarL(int x0, int y0){
    // コンストラクタ
    super(x0, y0);
  }
  
  void display() {
    fill(255);
    rect(x, y, 10, 200);
  }
}

class BarR extends Bar{
  // Barサブクラス(右Bar)
  BarR(int x0, int y0){
    // コンストラクタ
    super(x0, y0);
  }
  
  void display() {
    fill(255);
    rect(x, y, 10, 200);
  }
}