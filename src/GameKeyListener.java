import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {

    Board board;

    GameKeyListener(Board board){
        this.board=board;
    }
    @Override
    public void keyPressed(KeyEvent e) {

        System.out.println("naciskam!!!!");
        int x = board.currentShape.getX();
        int y = board.currentShape.getY();

        switch (e.getKeyCode()){

            case KeyEvent.VK_LEFT:
                if(board.tryToMove(board.currentShape,0,-1)) {
                    board.currentShape.setBackground(Color.BLACK);
                    y--;
                    board.currentShape = board.board[x][y];
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(board.tryToMove(board.currentShape,0,1) ) {
                    board.currentShape.setBackground(Color.BLACK);
                    y++;
                    System.out.println("bjbii");;
                    board.currentShape = board.board[x][y];
                }
                break;
            case KeyEvent.VK_DOWN:
                if(board.tryToMove(board.currentShape,1,0)){
                    board.currentShape.setBackground(Color.BLACK);
                    board.timer.setDelay(400);
                    x++;
                    board. currentShape = board.board[x][y];
                }
                break;


        }
        board.paintShape(board.currentShape);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
            board.timer.setDelay(900);

    }

    @Override
    public void keyTyped(KeyEvent e) { }
}
