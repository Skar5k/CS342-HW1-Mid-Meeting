//Holds data from city distances. Used to form adjacency lists
public class Edges {
    private int City1;
    private int City2;
    private int distance;

    public Edges(int c1, int c2, int d){                //constructor
        City1 = c1;
        City2 = c2;
        distance = d;
    }
    public int getCit1(){ return City1;}                //accessors
    public int getCit2(){ return City2;}
    public int getDistance(){ return distance;}
}
