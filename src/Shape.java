public class Shape extends AllShapes{

    private final int color;
 //   private boolean[][] coordinates;
    private int [][] coordinates;

    Shape(){
        int randomNumber = (int)(Math.random()*7);
        color =randomNumber;
        coordinates=super.coordinates[randomNumber];
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int [][] coordinates){
        this.coordinates=coordinates;
    }

    public int getColor() {
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
