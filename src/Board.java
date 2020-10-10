import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Board extends JPanel implements ActionListener {

    final int cellSize = Game.height / 20;

    private Timer timer;
    private int periodInterval;
    private boolean gameStared;
    private boolean isPaused;
    private boolean isGameOver;

    private int[][] board;
    private int currentY, currentX;     // Describes "center" piece (the point around which it rotates)
    private Shape currentShape;
    private Shape nextShape;
    private int[][] coordinatesOfCurrentShape;
    private int allRemovedLines;
    private int linesRemovedInThisLevel;
    private int level;
    private int points;

    private SidePanel sidePanel;
    private ScoringSystem scoringSystem;

    public Board() {
        this.setPreferredSize(new Dimension(Game.width, Game.height));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.addKeyListener(new GameKeyListener(this));

        periodInterval =600;
        timer = new Timer(periodInterval, this);
        scoringSystem = new ScoringSystem();
        board = new int[24][10];
        coordinatesOfCurrentShape = new int[4][2];
        allRemovedLines =0;
        linesRemovedInThisLevel=0;

        for (int[] ints : board) Arrays.fill(ints, -1);       //-1 means no element on this index

        this.setFocusable(true);
        this.requestFocus();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        displayCurrentBoard(g);

        if(isPaused){
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
            g.setColor(Color.WHITE);
            g.drawString("PAUSED",85,this.getHeight()/2);
        }else if(isGameOver){
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER",50,this.getHeight()/2);
        }

        requestFocus();
    }

    void displayCurrentBoard(Graphics g) {

        for (int i = 4; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != -1) {
                    g.setColor(AllShapes.colors[board[i][j]]);
                    g.fillRect(j * cellSize, (i - 4) * cellSize, cellSize, cellSize);
                }
                g.setColor(new Color(77, 77, 77));
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

        if (newX >= board.length || newY < 0 || newY >= board[0].length)
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

    //removes previous location of shape and create the new one
    void updateBoard(int addedX, int addedY){

        deleteShape(coordinatesOfCurrentShape);

        for (int i = 0; i < coordinatesOfCurrentShape.length; i++) {
            coordinatesOfCurrentShape[i][0] = coordinatesOfCurrentShape[i][0] + addedX;
            coordinatesOfCurrentShape[i][1] = coordinatesOfCurrentShape[i][1] + addedY;
            board[coordinatesOfCurrentShape[i][0]][coordinatesOfCurrentShape[i][1]]=currentShape.getNumberOfColor();
        }

    }

    void removeFullLines(){

        boolean isFull;
        int linesRemovedAtOnce=0;

        for (int i = 4; i < board.length; i++) {
            isFull=true;
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] == -1)
                    isFull=false;
            }
            if(isFull) {
                for (int k = i - 1; k >= 3; k--)
                    board[k + 1] = board[k].clone();

                linesRemovedAtOnce++;
            }

        }
        linesRemovedInThisLevel+=linesRemovedAtOnce;
        allRemovedLines +=linesRemovedAtOnce;
        points+=scoringSystem.addPoints(level,linesRemovedAtOnce);

    }

    void updateStatistics(){
        sidePanel.setNumberOfRemovedLines(allRemovedLines);
        sidePanel.setPoints(points);
        sidePanel.setLevel(level);
    }

    //increases level with every 10 lines removed
    //decreases the interval period by 30 with each level (unless it gets to 60)
    void updateLevelAndPeriodInterval(){
        if(linesRemovedInThisLevel%10==0 && linesRemovedInThisLevel != 0){
            level++;
            linesRemovedInThisLevel=0;
            periodInterval -= periodInterval >= 60 ? 30 : 0;
        }
    }

    void generateNewShape() {
        currentShape=nextShape;
        nextShape = new Shape();

        sidePanel.repaint();
    }

    void clearBoard(){
        for (int[] ints : board)
            Arrays.fill(ints, -1);
    }

    void deleteShape(int [][] coordinates){

        for (int i = 0; i < coordinates.length; i++)
            board[coordinates[i][0]][coordinates[i][1]] = -1;
    }

    boolean isGameStarted(){
        return gameStared;
    }

    boolean isGamePaused(){
        return isPaused;
    }

    boolean isGameOver(){
        for (int i = 0; i < board[0].length; i++)
            if(board[3][i] != -1)
                return true;

        return false;
    }

    void startGame() {

        if(gameStared) {
            timer.start();
            isPaused=false;
        } else {
            restartGame();
            currentY = 4;
            currentX = 2;
            timer.start();
            gameStared = true;
            currentShape = new Shape();
            nextShape = new Shape();
            sidePanel.repaint();
            setShapeOnBoard(currentShape);
        }
    }

    void pauseGame(){
        isPaused=true;
        repaint();
        timer.stop();
    }

    void gameOver(){
        isGameOver=true;
        repaint();
        timer.stop();
        gameStared=false;
    }

    void restartGame(){
        isGameOver=false;
        isPaused=false;
        clearBoard();
        repaint();
        timer.setDelay(600);
    }

    Shape getNextShape(){ return nextShape; }

    void setTimer(int delay){ timer.setDelay(delay); }

    int getCellSize(){ return cellSize; }

    int getPeriodInterval(){ return periodInterval; }

    void setSidePanel(SidePanel sidePanel){
        this.sidePanel = sidePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(gameStared) {
            if(!fallOneLine()) {
                if(isGameOver())
                    gameOver();

                removeFullLines();
                updateLevelAndPeriodInterval();
                updateStatistics();
                generateNewShape();
                currentX=2;
                currentY=4;
                setShapeOnBoard(currentShape);
            }
        }
        repaint();
    }
}
