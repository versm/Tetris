import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {

    private Board board;
    private JLabel level;
    private JLabel removedLines;
    private JLabel points;

    public SidePanel(Board board) {

        this.board=board;

        setPreferredSize(new Dimension(Game.width/2,Game.height));
        setBackground(new Color(153,153,255));
        setLayout(null);

        JLabel nextShape = new JLabel("NEXT SHAPE");
        nextShape.setBounds(20,15,110,20);
        nextShape.setForeground(Color.BLACK);
        nextShape.setFont(new Font("Arial", Font.BOLD, 17));

        JButton start = new JButton("START");
        start.setBounds(20,200,100,20);
        start.setBackground(new Color(176,178,166));
        start.setBorder(BorderFactory.createLineBorder(Color.GRAY,3));
        start.addActionListener(e -> board.startGame());

        JButton pause = new JButton("PAUSE");
        pause.setBounds(20,235,100,20);
        pause.setBackground(new Color(176,178,166));
        pause.setBorder(BorderFactory.createLineBorder(Color.GRAY,3));
        pause.addActionListener(e -> board.pauseGame());

        JButton finish = new JButton("FINISH");
        finish.setBounds(20,270,100,20);
        finish.setBackground(new Color(176,178,166));
        finish.setBorder(BorderFactory.createLineBorder(Color.GRAY,3));
        finish.addActionListener(e -> board.gameOver());

        level = new JLabel("0");
        level.setBounds(15,320,150,35);
        level.setForeground(Color.BLACK);
        level.setFont(new Font("Arial", Font.BOLD, 42));

        JLabel levelLabel = new JLabel("LEVEL");
        levelLabel.setBounds(15,360,70,20);
        levelLabel.setForeground(Color.BLACK);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 16));

        removedLines = new JLabel("0");
        removedLines.setBounds(15,395,150,35);
        removedLines.setForeground(Color.BLACK);
        removedLines.setFont(new Font("Arial", Font.BOLD, 42));

        JLabel lines = new JLabel("LINES");
        lines.setBounds(15,435,70,20);
        lines.setForeground(Color.BLACK);
        lines.setFont(new Font("Arial", Font.BOLD, 16));

        points = new JLabel("0");
        points.setBounds(15,470,150,35);
        points.setForeground(Color.BLACK);
        points.setFont(new Font("Arial", Font.BOLD, 42));

        JLabel pointsLabel = new JLabel("POINTS");
        pointsLabel.setBounds(15,510,70,20);
        pointsLabel.setForeground(Color.BLACK);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 16));

        this.add(nextShape);
        this.add(start);
        this.add(pause);
        this.add(finish);
        this.add(levelLabel);
        this.add(level);
        this.add(lines);
        this.add(removedLines);
        this.add(pointsLabel);
        this.add(points);

        this.setFocusable(true);
        this.requestFocus();
    }

    void setNumberOfRemovedLines(int numberOfRemovedLines){
        removedLines.setText(String.valueOf(numberOfRemovedLines));
    }

    void setPoints(int numberOfPoints){
        points.setText(String.valueOf(numberOfPoints));
    }

    void setLevel(int level){
        this.level.setText(String.valueOf(level));
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.fillRect(10,50,130,130);

        if(!board.isGameStarted())
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

        for (int i = 0; i < shape.length; i++)
            for (int j = 0; j < shape[i].length; j++)
                if(shape[i][j] == 1){
                    g.setColor(nextShape.getColor());
                    g.fillRect(currX + j * board.getCellSize(),currY + i*board.getCellSize(),board.getCellSize(),board.getCellSize());
                    g.setColor(Color.GRAY);
                    g.drawRect(currX + j * board.getCellSize(),currY + i*board.getCellSize(),board.getCellSize(),board.getCellSize());
                }
    }


}
