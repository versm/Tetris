import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board extends JPanel implements ActionListener {

    final int NUMBER_OF_COLUMNS = 10;
    final int NUMBER_OF_ROWS = 20;
    final int cellSize = Game.height / 20;

    private Timer timer;
    boolean isFalling;
    boolean gameStared;
    boolean gamePaused;

    int[][] board;
    int currentY = 0;     // Coordinates on board from which new elements begin to fall
    int currentX;       // Value of X depends on shape's width
    Shape currentShape;
    Shape nextShape;
    List<Shape> allShapesOnBoard;
    int[][] coordinatesOfCurrentShape;


    public Board() {
        this.setPreferredSize(new Dimension(Game.width, Game.height));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.addKeyListener(new GameKeyListener(this));

        board = new int[24][10];
        coordinatesOfCurrentShape = new int[4][2];
        allShapesOnBoard = new ArrayList<>();

        for (int[] ints : board) Arrays.fill(ints, -1);       //-1 means no element on this index

        timer = new Timer(600, this);

        currentShape = new Shape();
        nextShape= new Shape();
        setNewShapeOnBoard(currentShape);


        this.setFocusable(true);
        this.requestFocus();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        displayCurrentBoard(g);

        requestFocus();
    }

    void displayCurrentBoard(Graphics g) {

        for (int i = 4; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != -1) {
                    g.setColor(AllShapes.colors[board[i][j]]);
                    g.fillRect(j * cellSize, (i - 4) * cellSize, cellSize, cellSize);
                }
                g.setColor(Color.GRAY);
                g.drawRect(j * cellSize, (i - 4) * cellSize, cellSize, cellSize);
            }
        }
    }

    void setNewShapeOnBoard(Shape currentShape) {

        currentX = NUMBER_OF_COLUMNS / 2 - currentShape.getCoordinates()[0].length / 2;

        int k = 0;

        for (int i = 0; i < currentShape.getCoordinates().length; i++)
            for (int j = 0; j < currentShape.getCoordinates()[i].length; j++)
                if (currentShape.getCoordinates()[i][j]) {
                    board[currentY + i][currentX + j] = currentShape.getColor();
                    coordinatesOfCurrentShape[k++] = new int[]{currentY + i, currentX + j};
                }

    }

    boolean fallOneLine() {

        for (int i = 0; i < coordinatesOfCurrentShape.length; i++) {

            int[] coordinatesOfOnePiece = coordinatesOfCurrentShape[i];

            boolean isAboveOtherPiece = false;
            for (int j = 0; j < coordinatesOfCurrentShape.length; j++)
                if ((coordinatesOfOnePiece[0] + 1 == coordinatesOfCurrentShape[j][0] && coordinatesOfOnePiece[1] == coordinatesOfCurrentShape[j][1]))
                    isAboveOtherPiece = true;

            if(isAboveOtherPiece)
                continue;

            if (!tryToMove(coordinatesOfOnePiece[0] + 1, coordinatesOfOnePiece[1]))
                return false;
        }

        updateBoard(1,0);
        return true;
    }

    boolean tryToMove(int newX, int newY) {

        if (newX >= board.length || newY < 0 || newY >= NUMBER_OF_COLUMNS)
            return false;

        return board[newX][newY] == -1;
    }

    boolean moveLeft() {

        for (int i = 0; i < coordinatesOfCurrentShape.length; i++) {

            int[] coordinatesOfOnePiece = coordinatesOfCurrentShape[i];
            boolean otherPieceOnTheLeft=false;

            for (int j = 0; j < coordinatesOfCurrentShape.length; j++)

                if(coordinatesOfOnePiece[0]==coordinatesOfCurrentShape[j][0] && coordinatesOfOnePiece[1]==coordinatesOfCurrentShape[j][1]+1)
                    otherPieceOnTheLeft=true;

            if(otherPieceOnTheLeft)
                continue;

            if(!tryToMove(coordinatesOfCurrentShape[i][0],coordinatesOfCurrentShape[i][1]-1))
                return false;
        }

        updateBoard(0,-1);
        return true;
    }

    boolean moveRight(){
        for (int i = 0; i < coordinatesOfCurrentShape.length; i++) {

            int[] coordinatesOfOnePiece = coordinatesOfCurrentShape[i];
            boolean otherPieceOnTheRight=false;

            for (int j = 0; j < coordinatesOfCurrentShape.length; j++)

                if(coordinatesOfOnePiece[0]==coordinatesOfCurrentShape[j][0] && coordinatesOfOnePiece[1]==coordinatesOfCurrentShape[j][1]-1)
                    otherPieceOnTheRight=true;

            if(otherPieceOnTheRight)
                continue;

            if(!tryToMove(coordinatesOfCurrentShape[i][0],coordinatesOfCurrentShape[i][1]+1))
                return false;
        }

        updateBoard(0,1);
        return true;
    }

    void turnRight(){

        int [][] tmp = new int [4][2];
        int px = coordinatesOfCurrentShape[2][0];
        int py = coordinatesOfCurrentShape[2][1];

        for (int i = 0; i < tmp.length; i++)
            for (int j = 0; j < tmp[i].length; j++)
                tmp[i][j] = coordinatesOfCurrentShape[i][j];

        for (int i = 0; i < tmp.length; i++) {
            coordinatesOfCurrentShape[i][0] = tmp[i][1]+px-py-1;
            coordinatesOfCurrentShape[i][1] = px+py-tmp[i][0];
        }

        for (int i = 0; i < tmp.length; i++)
            board[tmp[i][0]][tmp[i][1]] = -1;

        for (int i = 0; i < coordinatesOfCurrentShape.length; i++)
            for (int j = 0; j < coordinatesOfCurrentShape[i].length-1; j++)
                board[coordinatesOfCurrentShape[i][j]][coordinatesOfCurrentShape[i][j+1]]=currentShape.getColor();


    }

    void updateBoard(int newX, int newY){

        for (int i = 0; i < coordinatesOfCurrentShape.length; i++) {
            board[coordinatesOfCurrentShape[i][0]][coordinatesOfCurrentShape[i][1]] = -1;
            coordinatesOfCurrentShape[i][0] = coordinatesOfCurrentShape[i][0] + newX;
            coordinatesOfCurrentShape[i][1] = coordinatesOfCurrentShape[i][1] + newY;
        }

        for (int i = 0; i < coordinatesOfCurrentShape.length; i++)
            for (int j = 0; j < coordinatesOfCurrentShape[i].length-1; j++)
                board[coordinatesOfCurrentShape[i][j]][coordinatesOfCurrentShape[i][j+1]]=currentShape.getColor();

    }

    void startGame() {
        timer.start();
        gameStared = true;
    }

    void pauseGame(){
        timer.stop();
    }

    void generateNewShape() {
        currentShape=nextShape;
        nextShape = new Shape();
    }

    void setTimer(int delay){
        timer.setDelay(delay);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(gameStared) {
            if(!fallOneLine()) {
                generateNewShape();
                setNewShapeOnBoard(currentShape);
            }
        }
        repaint();
    }


}
