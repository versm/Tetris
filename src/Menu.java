import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {

    Board board;

    public Menu(Board board) {

        setPreferredSize(new Dimension(Game.width/2,Game.height));
        setBackground(Color.GRAY);
        setLayout(null);
        setBorder(BorderFactory.createSoftBevelBorder(5,Color.BLACK,Color.GRAY));

        JButton start = new JButton("START");
        start.setBounds(20,180,100,20);

        start.addActionListener(e -> board.startGame());
        this.add(start);

        this.setFocusable(true);
        this.requestFocus();
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//    }
}
