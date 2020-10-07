import javax.swing.*;
import java.awt.*;

public class Game{

  //  static final int height =  Toolkit.getDefaultToolkit().getScreenSize().height - 100;
    static final int height =  600;
    static final int width = height/2;
    JFrame jFrame;
    Board board;
    SidePanel sidePanel;

    public Game() {
        board=new Board();
        sidePanel = new SidePanel(board);
        board.setSidePanel(sidePanel);
        jFrame = new JFrame("Tetris");
        jFrame.add(board);
        jFrame.add(sidePanel);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new FlowLayout());

        jFrame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-width,100);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.pack();
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(()->{
            new Game();
        });
    }

}
