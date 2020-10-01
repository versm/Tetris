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

        switch (e.getKeyCode()){

            case KeyEvent.VK_LEFT:

                break;
            case KeyEvent.VK_RIGHT:

                break;
            case KeyEvent.VK_DOWN:

                break;


        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
            board.timer.setDelay(900);

    }

    @Override
    public void keyTyped(KeyEvent e) { }
}
