import javax.swing.*;
import java.awt.*;

public class Game{

    static final int height =  600;
    static final int width = height/2;
    private JFrame jFrame;
    private Board board;
    private SidePanel sidePanel;

    public Game() {
        board=new Board();
        sidePanel = new SidePanel(board);
        board.setSidePanel(sidePanel);
        jFrame = new JFrame("Tetris");
        jFrame.add(board);
        jFrame.add(sidePanel);
        jFrame.setForeground(Color.RED);
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
