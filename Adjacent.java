//Class to hold Adjacency list. Can return city, and distance as well as print adjacency entry
public class Adjacent {
    private int City;
    private int distance;

    public Adjacent(int C, int D){
        City = C;
        distance = D;
    }
    public int getCity(){return City;}
    public int getDistance(){return distance;}
    public void print_adj(){
        System.out.println("      "+City+" "+distance);
    }
}
