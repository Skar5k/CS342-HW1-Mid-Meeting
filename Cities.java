//Holds data for cities

import java.util.Comparator;
import java.util.Vector;

public class Cities implements Comparator<Cities>, Comparable<Cities>{
    private int cityID;                                     //city number
    private String city;                                    //Actual name/State
    private String state;
    private int ToChicago;                                  //distance to Chicago
    Vector<Adjacent> a = new Vector<>();

    public Cities(String c, String s, Vector<Edges> E, int CID){        //constructor
        city = c;
        state = s;
        cityID = CID;
        ToChicago = 0;
        for (int i = 0; i<E.size();i++) {                               //fill in adjacency with edges vector
            if(E.get(i).getCit1() == CID){
                int city_adj = E.get(i).getCit2();
                int dist_adj = E.get(i).getDistance();
                Adjacent temp = new Adjacent(city_adj,dist_adj);
                a.addElement(temp);
            }
            if(E.get(i).getCit2() == CID){
                int city_adj = E.get(i).getCit1();
                int dist_adj = E.get(i).getDistance();
                Adjacent temp = new Adjacent(city_adj,dist_adj);
                a.addElement(temp);
            }
        }

    }

    public int compareTo(Cities c){                                     //overloaded compares for sort function
        return this.city.compareTo(c.getCity());                        //compares City name to sort alphabetically
    }
    public int compare(Cities c, Cities c1){
        return c.getCity().compareTo(c1.getCity());
    }

    public Vector<Adjacent> getA(){                                     //accessors/setters
        Vector<Adjacent> Temp = (Vector<Adjacent>) a.clone();
        return Temp;
    }
    public int getCityID(){
        return cityID;
    }
    public void PrintCities(){
        System.out.println(city+" "+ state);
    }
    public String getCity(){return city;}
    public void Dist_Chicago(int d){
        ToChicago = d;
    }
    public int getToChicago(){return ToChicago;}
}
