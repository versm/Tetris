import javax.swing.*;
import java.awt.*;

public class Shape extends JPanel {

    Color color;
    int x,y;


    Shape(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX(){ return x; }

    public int getY(){ return y; }


}
