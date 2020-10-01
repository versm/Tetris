public class Shape extends AllShapes{

  //  private Color color;
    private int color;
    private boolean[][] coordinates;
    //private int [][] coordinatesOnBoard;


    Shape(){

        int randomNumber = (int)(Math.random()*7);

        color =randomNumber;
       // color=AllShapes.colors[numberOfColor];
        coordinates=super.coordinates[randomNumber];

       // coordinatesOnBoard=new int[4][2];
    }


    public boolean[][] getCoordinates() {
        return coordinates;
    }

    public int getColor() {
        return color;
    }


}
