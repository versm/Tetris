import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JPanel implements  ActionListener {

    final int NUMBER_OF_COLUMNS= 10;
    final int NUMBER_OF_ROWS= 20;
    final int boardCellSize = Game.height/20;

    Shape [][] board;
    Shape currentShape;
    Timer timer;
    boolean isFalling;
    boolean gameStarted;


    public Board(){
        this.setPreferredSize(new Dimension(Game.width,Game.height));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        timer = new Timer(900,this);
        //timer.start();

        board = new Shape[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                Shape shape = new Shape(i,j);
                board[i][j] = shape;
                shape.setBounds(j * boardCellSize, i * boardCellSize, boardCellSize, boardCellSize);
                shape.setBackground(Color.BLACK);

                this.add(shape);
            }
        }

        this.addKeyListener(new GameKeyListener(this));
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if(gameStarted)
            fallOneLine();

    }

//    @Override
//    public void keyPressed(KeyEvent e) {
//
//        System.out.println("naciskam!!!!");
//        int x = currentShape.getX();
//        int y = currentShape.getY();
//
//        switch (e.getKeyCode()){
//
//            case KeyEvent.VK_LEFT:
//                if(tryToMove(currentShape,0,-1)) {
//                    currentShape.setBackground(Color.BLACK);
//                    y--;
//                    currentShape = board[x][y];
//                }
//                break;
//            case KeyEvent.VK_RIGHT:
//                if(tryToMove(currentShape,0,1) ) {
//                    currentShape.setBackground(Color.BLACK);
//                    y++;
//                    System.out.println("bjbii");;
//                    currentShape = board[x][y];
//                }
//                break;
//            case KeyEvent.VK_DOWN:
//                if(tryToMove(currentShape,1,0)){
//                    currentShape.setBackground(Color.BLACK);
//                    timer.setDelay(400);
//                    x++;
//                    currentShape = board[x][y];
//                }
//                break;
//
//
//        }
//        paintShape(currentShape);
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        if(e.getKeyCode()==KeyEvent.VK_DOWN)
//            timer.setDelay(900);
//
//    }
//
//    @Override
//    public void keyTyped(KeyEvent e) { }


    public void paintShape(Shape shape){
        shape.setBackground(Color.GREEN);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    void fallOneLine(){

        int x = currentShape.x;
        int y = currentShape.y;

        if(tryToMove(currentShape,1,y)){
            currentShape.setBackground(Color.BLACK);
            x++;
            currentShape = board[x][y];
        }
        paintShape(currentShape);

    }

    boolean tryToMove(Shape shape ,int newX, int newY) {
       // System.out.println("check");

        if(shape.getX() + newX <= NUMBER_OF_ROWS)
            System.out.println("war 1");
        if(shape.getY() + newY <= NUMBER_OF_COLUMNS)
            System.out.println("war 2");
        if(shape.getY() + newY >= 0)
            System.out.println("war 3");

        return (shape.getX() + newX <= NUMBER_OF_ROWS && shape.getY() + newY <= NUMBER_OF_COLUMNS && shape.getY() + newY >= 0);

    }

    void startGame(){
        currentShape = board[0][board[0].length/2];
        currentShape.x=0;
        currentShape.y=board[0].length/2;
        currentShape.setBackground(Color.GREEN);

        isFalling= true;
    }



}
