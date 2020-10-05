import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


public class Menu extends JPanel {

    Board board;
    Shape nextShape;


    public Menu(Board board) {

        this.board=board;

        setPreferredSize(new Dimension(Game.width/2,Game.height));
        setBackground(new Color(153,153,255));
        setLayout(null);
       // setBorder(BorderFactory.createSoftBevelBorder(5,Color.BLACK,Color.GRAY));

        JLabel nextShape = new JLabel("NEXT SHAPE");
        nextShape.setBounds(20,15,110,20);
        nextShape.setForeground(Color.BLACK);
        nextShape.setFont(new Font("Arial", Font.BOLD, 17));

        JButton start = new JButton("START");
        start.setBounds(20,200,100,20);
        start.addActionListener(e -> board.startGame());

        JButton pause = new JButton("PAUSE");
        pause.setBounds(20,235,100,20);
        pause.addActionListener(e -> board.pauseGame());

        JButton finish = new JButton("FINISH");
        finish.setBounds(20,270,100,20);
        finish.addActionListener(e -> board.finishGame());

        this.add(nextShape);
        this.add(start);
        this.add(pause);
        this.add(finish);

        this.setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);

        g.fillRect(10,50,130,130);

        if(!board.gameStared)
            return;

        displayNextShape(g);

    }

    void displayNextShape(Graphics g){

        Shape nextShape= board.getNextShape();
        int[][] shape = new int[4][4];
        int currX=15;
        int currY=55;

        int initialX=2;
        int initialY=1;

        for (int i = 0; i < nextShape.getCoordinates().length; i++){

            int x= nextShape.getCoordinates()[i][0];
            int y= nextShape.getCoordinates()[i][1];

            shape[initialX+y][initialY+x]=1;
        }

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if(shape[i][j] == 1){
                    g.setColor(nextShape.getColor());
                    g.fillRect(currX + j * board.getCellSize(),currY + i*board.getCellSize(),board.getCellSize(),board.getCellSize());
                    g.setColor(Color.GRAY);
                    g.drawRect(currX + j * board.getCellSize(),currY + i*board.getCellSize(),board.getCellSize(),board.getCellSize());
                }
            }
        }


    }


}
