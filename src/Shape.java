public class Shape extends AllShapes{

    private final int color;
    private boolean[][] coordinates;
  //  private int [][] coordinates;

    Shape(){
        int randomNumber = (int)(Math.random()*7);
        color =randomNumber;
        coordinates=super.coordinates[randomNumber];
    }

    public boolean[][] getCoordinates() {
        return coordinates;
    }

    public int getColor() {
        return color;
    }


}
