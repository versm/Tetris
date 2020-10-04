import javax.swing.*;
import java.awt.*;


public class Menu extends JPanel {

    Board board;

    int shapeCurrentX,getShapeCurrentY;

    public Menu(Board board) {

        setPreferredSize(new Dimension(Game.width/2,Game.height));
        setBackground(Color.GRAY);
        setLayout(null);
        setBorder(BorderFactory.createSoftBevelBorder(5,Color.BLACK,Color.GRAY));

        JButton start = new JButton("START");
        start.setBounds(20,200,100,20);

        start.addActionListener(e -> board.startGame());
        this.add(start);


        JButton pause = new JButton("PAUSE");
        pause.setBounds(20,240,100,20);

        pause.addActionListener(e -> board.pauseGame());
        this.add(pause);

        JButton finish = new JButton("FINISH");
        finish.setBounds(20,280,100,20);

        finish.addActionListener(e -> board.finishGame());
        this.add(finish);

        this.setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);

        g.fillRect(50,100,30,30);

    }


}
