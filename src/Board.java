import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Board extends JPanel implements ActionListener {

    final int NUMBER_OF_COLUMNS = 10;
    final int NUMBER_OF_ROWS = 20;
    final int cellSize = Game.height / 20;

    private Timer timer;
    boolean gameStared;

    int[][] board;
    int currentY;     // Coordinates on board from which new elements begin to fall
    int currentX;       // Value of X depends on shape's width
    Shape currentShape;
    Shape nextShape;
    int[][] coordinatesOfCurrentShape;
    int removedLines;

    Menu menu;

    public Board() {
        this.setPreferredSize(new Dimension(Game.width, Game.height));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.addKeyListener(new GameKeyListener(this));

        board = new int[24][10];
        coordinatesOfCurrentShape = new int[4][2];
        removedLines=0;

        for (int[] ints : board) Arrays.fill(ints, -1);       //-1 means no element on this index

        timer = new Timer(600, this);

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

    void setShapeOnBoard(Shape currentShape) {

        int k = 0;

        for (int i = 0; i < currentShape.getCoordinates().length; i++){

                int x= currentShape.getCoordinates()[i][0];
                int y= currentShape.getCoordinates()[i][1];

                board[currentX+y][currentY+x]=currentShape.getNumberOfColor();
                coordinatesOfCurrentShape[k++] = new int[]{currentX + y, currentY + x};
        }
    }

    boolean fallOneLine() {

        if(tryToMove(1,0)) {
            currentX++;
            updateBoard(1, 0);
            return true;
        }

        setShapeOnBoard(currentShape);
        return false;
    }

    boolean checkNextPositionOfShape(int newX, int newY) {

        if (newX >= board.length || newY < 0 || newY >= NUMBER_OF_COLUMNS)
            return false;

        return board[newX][newY] == -1;
    }

    void moveLeft() {

        if(tryToMove(0,-1)){
            currentY--;
            updateBoard(0,-1);
        }else
            setShapeOnBoard(currentShape);

    }

    void moveRight(){

        if(tryToMove(0,1)){
            currentY++;
            updateBoard(0,1);
        }else
            setShapeOnBoard(currentShape);
    }

    void rotateRight(){

        if(currentShape.getNumberOfColor()==3)      //checking if current figure isn't square shape
            return;

        int [][] newCoordinates= currentShape.rotateRight();

        if(tryToRotate(newCoordinates))
            currentShape.setCoordinates(newCoordinates);

        setShapeOnBoard(currentShape);

    }

    boolean tryToMove(int newX, int newY){

        deleteShape(coordinatesOfCurrentShape);     //removing current shape from board to check if it can move left, right or down

        for (int i = 0; i < coordinatesOfCurrentShape.length; i++)
            if(!checkNextPositionOfShape(coordinatesOfCurrentShape[i][0]+newX,coordinatesOfCurrentShape[i][1]+newY))
                return false;

        return true;
    }

    boolean tryToRotate(int [][] newCoordinates){

        deleteShape(coordinatesOfCurrentShape);
        for (int i = 0; i < newCoordinates.length; i++){

            int x= newCoordinates[i][0];
            int y= newCoordinates[i][1];

            if(!checkNextPositionOfShape(currentX+y,currentY+x))
                return false;
        }

        return true;
    }

    void updateBoard(int addedX, int addedY){

        deleteShape(coordinatesOfCurrentShape);

        for (int i = 0; i < coordinatesOfCurrentShape.length; i++) {
            coordinatesOfCurrentShape[i][0] = coordinatesOfCurrentShape[i][0] + addedX;
            coordinatesOfCurrentShape[i][1] = coordinatesOfCurrentShape[i][1] + addedY;
            board[coordinatesOfCurrentShape[i][0]][coordinatesOfCurrentShape[i][1]]=currentShape.getNumberOfColor();
        }

    }

    void removeFullLines(){

        boolean isFull= true;

        for (int i = board.length-1; i >= 4; i--) {
            isFull=true;
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] == -1)
                    isFull=false;
            }
            if(isFull) {
                for (int k = i - 1; k > 4; k--)
                    board[k + 1] = board[k].clone();

                removedLines++;
            }
        }
        System.out.println(removedLines);
    }

    void startGame() {

        if(gameStared)
            timer.start();

        else {
            currentY = 4;
            currentX = 2;
            timer.start();
            gameStared = true;
            currentShape = new Shape();
            nextShape = new Shape();
            menu.repaint();
            setShapeOnBoard(currentShape);
        }
    }

    void pauseGame(){
        timer.stop();
    }

    void finishGame(){
        timer.stop();
        clearBoard();
        repaint();
        gameStared=false;
    }

    void generateNewShape() {
        currentShape=nextShape;
        nextShape = new Shape();

        menu.repaint();
    }

    void deleteShape(int [][] coordinates){

        for (int i = 0; i < coordinates.length; i++)
            board[coordinates[i][0]][coordinates[i][1]] = -1;

    }

    void clearBoard(){
        for (int[] ints : board)
            Arrays.fill(ints, -1);
    }

    boolean isGameOver(){
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++)
            if(board[3][i] != -1)
                return true;

            return false;
    }

    void gameOver(){
        timer.stop();
        clearBoard();

        JLabel gameOver = new JLabel("GAME OVER");
        gameOver.setBounds(100,300,200,200);
        this.add(gameOver);

    }

    Shape getNextShape(){
        return nextShape;
    }

    void setTimer(int delay){
        timer.setDelay(delay);
    }

    int getCellSize(){
        return cellSize;
    }

    void setMenu(Menu menu){
        this.menu=menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(gameStared) {
            if(!fallOneLine()) {
                if(isGameOver())
                    gameOver();

                removeFullLines();
                generateNewShape();
                currentX=2;
                currentY=4;
                setShapeOnBoard(currentShape);
            }
        }
        repaint();
    }


}
