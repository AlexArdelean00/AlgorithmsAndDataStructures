import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import alg.AStar;
import alg.OccupancyGridFactory;
import struct.Graph;
import struct.Node;

public class Experiment6 {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{

        String outputString;
        PrintWriter writer;
        int test;
        Graph g;
        Node source;
        Node dest;
        long t0;
        long t1;

        Graph[] randomGraphs = new Graph[10];
        int size = 1000;
        double op = 0;
        for(int i=0; i<randomGraphs.length; i++){
            randomGraphs[i] = OccupancyGridFactory.genRandomOccupancyGrid(size, size, op).convertToGraph();
            op += 0.1;
        }

        for(int i=0; i<10; i++)
            AStar.aStar(randomGraphs[0], randomGraphs[0].getRandomNode(), randomGraphs[0].getRandomNode(), false);


        outputString = "OccupancyProb\tA*\tPathExists\n";
        test = 100;
        for(int i=0; i<randomGraphs.length; i++){
            System.out.println(i);
            g = randomGraphs[i];

            for(int j=0; j<test; j++){

                source = g.getRandomNode();
                dest = g.getRandomNode();
                
                t0=System.nanoTime();
                boolean pathExists = AStar.aStar(g, source, dest, false);
                t1=System.nanoTime();

                outputString += (0.1*i*100 + "\t" + (t1-t0)/1E6 + "\t" + pathExists);
                outputString += "\n";
            }   

            outputString += "\n\n";
        }
        //System.out.println(outputString);
        writer = new PrintWriter("../ExperimentData/Experiment6/occGrid.txt", "UTF-8");
        writer.println(outputString);
        writer.close();

    }
}
