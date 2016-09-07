/* ------------------------------------------------
  * Prog 1: Mid Meeting
  *
  * Class: CS 342, Fall 2016
  * System: Windows, IntelliJ IDE
  * Jaskaran Singh  -   jsingh10
  *
  *
  * -------------------------------------------------
  */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.Vector;
import java.io.IOException;


public class MainClass {
    private static Vector<Cities> C;                        //members to hold the values from the .txt files
    private static Vector<Participant> P;
    private static Vector<Edges> Paths;

    //Performs dijkstra's algorithm using the input cities/edges. Returns the shortest path between the two
    //arguments.
    private static int dijkstra(int Start, int End){
        Vector<Cities> Unvisited_Nodes = (Vector)C.clone();     //vectors to keep track of nodes visited and not
        Vector<Integer> Visited_Nodes = new Vector();           //as well as keeping track of minimum distances.
        Vector<Boolean> Visited = new Vector();
        Vector<Integer> MinWeights = new Vector();              //min distance to nodes

        for(int i = 0; i <= Unvisited_Nodes.size();i++){        //Set initial distances to infinity and all nodes
            MinWeights.addElement(Integer.MAX_VALUE-1);         //to unvisited
            Visited.addElement(false);
        }

        int current = Start;                                    //set up using the start argument.
        Visited.set(current,true);
        Unvisited_Nodes.remove(current);
        Visited_Nodes.addElement(current);
        MinWeights.set(current,0);
        Vector<Adjacent> Neighbors;
                                                                //The comparison is 1 because I entered a dummy node
                                                                //in order to keep consistent indexing with city id's
        while(Unvisited_Nodes.size() > 1){                      //Loop finds best path to take using dijkstra's
            int currWeight = MinWeights.get(current);           //algorithm, removing visited nodes from the list until empty.
            Neighbors = C.get(current).getA();
            for(Adjacent a : Neighbors){                        //Update with new better paths from the current node
                if(!Visited.get(a.getCity()) && MinWeights.get(a.getCity()) > a.getDistance()+currWeight) {
                    MinWeights.set(a.getCity(), a.getDistance() + currWeight);
                }
            }
            int minimum = Integer.MAX_VALUE;
            int index = -1;
            for(int i = 1; i < Unvisited_Nodes.size(); i++){                //choose the next node to visit
                if(MinWeights.get(Unvisited_Nodes.get(i).getCityID()) < minimum){
                    minimum = MinWeights.get(Unvisited_Nodes.get(i).getCityID());
                    index = i;
                }
            }
            Visited.set(Unvisited_Nodes.get(index).getCityID(),true);           //Update the lists for the next loop
            Visited_Nodes.addElement(Unvisited_Nodes.get(index).getCityID());
            current = Unvisited_Nodes.get(index).getCityID();
            Unvisited_Nodes.remove(index);
        }
        return MinWeights.get(End);
    }


    public static void main(String[] args) {
        System.out.printf("Jaskaran Singh   -   jsingh10\n Class: CS342, Fall 2016\n Program: #1: Mid Meeting\n\n");
        C = new Vector();                                                   //initialize the vectors used to store input
        P = new Vector();
        Paths = new Vector();
        int num_participants=0;                                             //holds the number of cities/participants.
        int num_cities = 0;
                                                                            //Read in from given files and store in Vectors
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:/Users/Khals/IdeaProjects/cs342-Prog1/src/CityNames.txt"));
            BufferedReader br2 = new BufferedReader(new FileReader("C:/Users/Khals/IdeaProjects/cs342-Prog1/src/Participants.txt"));
            BufferedReader br3 = new BufferedReader(new FileReader("C:/Users/Khals/IdeaProjects/cs342-Prog1/src/CityDistances.txt"));
            Cities T1 = new Cities("0", "0", Paths, 0);
            C.addElement(T1);
            String line = br3.readLine();
            int size = Integer.parseInt(line);
            int i;
            Edges T = new Edges(0,0,0);
            Paths.addElement(T);
            for(i=1; i<=size; i++){
                line = br3.readLine();
                String[] ReadDist = line.split("\\s+");
                Edges Temp = new Edges(Integer.parseInt(ReadDist[0]),Integer.parseInt(ReadDist[1]),Integer.parseInt(ReadDist[2]));
                Paths.addElement(Temp);
            }
            line = br.readLine();
            size = Integer.parseInt(line);
            num_cities = size;
            for(i=1;i<size;i++) {
                line = br.readLine();
                String[] ReadCit = line.split(",");
                Cities Temp = new Cities(ReadCit[0], ReadCit[1],Paths,i);
                C.addElement(Temp);
            }

            line = br2.readLine();
            size = Integer.parseInt(line);
            num_participants = size;
            for(i=0; i<size; i++){
                line = br2.readLine();
                String[] ReadPar = line.split("\\s+");
                Participant Temp = new Participant(Integer.parseInt(ReadPar[1]),ReadPar[0]);
                P.addElement(Temp);
            }


        }
        catch (FileNotFoundException not_found)                         //in case file doesn't open
        {
            System.out.println("file not found");
        }
        catch (IOException io){
            System.out.println("Invalid File Name");
        }

        double average = Double.POSITIVE_INFINITY;                      //will keep track of lowest average
        double sum = 0;                                                 //holds sum of distances for each city
        int index = 0;                                                  //index where lowest average is found

        for(int i = 1; i < num_cities; i++){                            //for each city, calculate how far each
            for( int j = 0; j < num_participants; j++){                 //participant is and average it
                sum += dijkstra(P.get(j).getCity(), C.get(i).getCityID());
            }
            if(sum / num_participants < average){
                average = sum/num_participants;
                index = i;
            }
            sum = 0;                                                    //reset sum for next city
        }

        Cities Best = C.get(index);

        System.out.println("Part 1. Printing Adjacency List's:");               //Print Adjacency List's for part 1
        for( Cities Cit : C){
            if(Cit.getCityID() != 0) {
                System.out.println("For City: ");
                Cit.PrintCities();
                System.out.println("City Num.   Distance");
                Vector<Adjacent> Adj = Cit.getA();
                for (Adjacent A : Adj) {
                    A.print_adj();
                }
                System.out.println(" ");
            }
        }

        System.out.println("Part 2. Distances from Chicago");

        for(Cities cit : C){                                               //find dist to Chicago
            if(cit.getCityID() != 0) {
                cit.Dist_Chicago(dijkstra(cit.getCityID(), 58));
            }
        }

        Collections.sort(C);                                                //sort with overloaded compare functions
        for(Cities cit : C){
            if(cit.getCityID() != 0) {
                cit.PrintCities();
                System.out.println("   " + cit.getToChicago());
            }
        }

        System.out.println("Part 3. Best Meeting Spot");                          //print results for part 3
        System.out.println("In the given set, the best meeting place is :");
        Best.PrintCities();
        System.out.println("The average distance from the participants to this is city is: ");
        System.out.format("%.2f",average);

    }


}
