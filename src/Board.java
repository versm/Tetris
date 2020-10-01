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

    Timer timer;
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

        board = new int[24][10];
        coordinatesOfCurrentShape = new int[4][2];
        allShapesOnBoard = new ArrayList<>();

        for (int[] ints : board) Arrays.fill(ints, -1);       //-1 means no element on this index

        timer = new Timer(400, this);


        currentShape = new Shape();
        nextShape= new Shape();
        setNewShapeOnBoard(currentShape);

        this.setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


        for (int[] ints : coordinatesOfCurrentShape) {
            for (int anInt : ints) System.out.print(anInt + " ");

            System.out.println();
        }

        displayCurrentBoard(g);

        if(gameStared) {
            displayCurrentBoard(g);
            if(!fallOneLine()) {
                generateNewShape();
                setNewShapeOnBoard(currentShape);
            }
        }
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

            if (!tryToMoveDown(coordinatesOfOnePiece[0] + 1, coordinatesOfOnePiece[1]))
                return false;
        }

        for (int i = 0; i < coordinatesOfCurrentShape.length; i++) {
            board[coordinatesOfCurrentShape[i][0]][coordinatesOfCurrentShape[i][1]] = -1;
            coordinatesOfCurrentShape[i][0] = coordinatesOfCurrentShape[i][0] + 1;
        }

        for (int i = 0; i < coordinatesOfCurrentShape.length; i++)
            for (int j = 0; j < coordinatesOfCurrentShape[i].length-1; j++)
                board[coordinatesOfCurrentShape[i][j]][coordinatesOfCurrentShape[i][j+1]]=currentShape.getColor();

        return true;
    }



    boolean tryToMoveDown(int newX, int newY) {

        if (newX >= board.length || newY <= 0 || newY >= NUMBER_OF_COLUMNS)
            return false;

        return board[newX][newY] == -1;
    }

    boolean tryToMoveLeft(int newX, int newY) {
        return false;
    }

    boolean tryToMoveRight(int newX, int newY){
        return true;
    }


    void startGame() {
        timer.start();
        gameStared = true;
    }

    void generateNewShape() {
        currentShape=nextShape;
        nextShape = new Shape();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }


}
