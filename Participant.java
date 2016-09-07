//Holds data from participants file.

public class Participant {
    private int CityID;
    private String Name;

    public Participant(int CID, String name){           //Constructor
        CityID = CID;
        Name=name;

    }

    public void PrintParticipants(){
        System.out.println("Name: "+Name + "  "+ CityID);
    }

    public int getCity(){return CityID;}                //accessor
}
