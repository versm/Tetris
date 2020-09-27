import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {

    Board board;


    public Menu(Board board) {
        this.board=board;

        setPreferredSize(new Dimension(Game.width/2,Game.height));
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createSoftBevelBorder(5,Color.BLACK,Color.GRAY));

        JButton startButton = new JButton("START");
        startButton.setBounds(20,20,30,30);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.timer.start();
                board.gameStarted=true;
                board.startGame();
            }
        });
        this.add(startButton);
    }


}
