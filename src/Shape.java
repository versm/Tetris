import java.awt.*;

public class Shape {

    private final int numberOfColor;
    private final Color color;
    private int [][] coordinates;

    Shape(){
        int randomNumber = (int)(Math.random()*7);
        numberOfColor =randomNumber;
        color= AllShapes.colors[randomNumber];
        coordinates= AllShapes.coordinates[randomNumber];
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int [][] coordinates){
        this.coordinates=coordinates;
    }

    public int getNumberOfColor() {
        return numberOfColor;
    }

    public Color getColor(){
        return color;
    }

    public int[][] rotateRight(){

        int [][] tmp = new int[4][2];

        for (int i = 0; i < 4; i++) {
            int x1 = coordinates[i][0];
            int y1 = coordinates[i][1];

            tmp[i][0]=-y1;
            tmp[i][1]=x1;

        }
        return tmp;
    }
}
