import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.spi.BreakIteratorProvider;

public class GameKeyListener implements KeyListener {

    Board board;

    GameKeyListener(Board board){
        this.board=board;
    }
    @Override
    public void keyPressed(KeyEvent e) {

        if(!board.gameStared)
            return;

        switch (e.getKeyCode()){

            case KeyEvent.VK_LEFT:
                board.moveLeft();
                board.repaint();
                break;
            case KeyEvent.VK_RIGHT:
                board.moveRight();
                  board.repaint();
                break;
            case KeyEvent.VK_DOWN:
                board.setTimer(50);
                break;
            case KeyEvent.VK_UP:
                board.rotateRight();
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(!board.gameStared)
            return;

        if(e.getKeyCode()==KeyEvent.VK_DOWN)
            board.setTimer(board.getCycleDuration());

    }

    @Override
    public void keyTyped(KeyEvent e) { }
}
