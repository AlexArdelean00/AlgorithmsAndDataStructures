import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import alg.AStar;
import alg.GraphFactory;
import struct.Graph;
import struct.Node;

public class Experiment5 {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{

        String outputString;
        PrintWriter writer;
        int test;
        Graph g;
        int n;
        int m;
        Node source;
        Node dest;
        long t0;
        long t1;

        Graph[] randomGraphs = new Graph[10];
        int size = 100000;
        for(int i=0; i<randomGraphs.length; i++){

            n = (i+1)*size;
            m = i-1;

            randomGraphs[i] = GraphFactory.genRandomConnectedGraph(n, m);
            System.out.println(randomGraphs[i].getNodesNr());
        }


        outputString = "n\tEuclideanHeuristic\tNullHeuristic\n";
        test = 100;
        for(int i=0; i<randomGraphs.length; i++){
            System.out.println(i);
            g = randomGraphs[i];
            for(int j=0; j<test; j++){
                source = g.getRandomNode();
                dest = g.getRandomNode();
                
                t0=System.nanoTime();
                AStar.aStar(g, source, dest, false);
                t1=System.nanoTime();
                long euclideanTime = t1-t0;

                t0=System.nanoTime();
                AStar.aStar(g, source, dest, true);
                t1=System.nanoTime();
                long nullTime = t1-t0;

                outputString += (g.getNodesNr() + "\t" + euclideanTime/1E6 + "\t" + nullTime/1E6);
                outputString += "\n";
            }   
            outputString += "\n\n";
        }
        //System.out.println(outputString);
        writer = new PrintWriter("../ExperimentData/Experiment5/euclideanNullHeuristic.txt", "UTF-8");
        writer.println(outputString);
        writer.close();

    }
}
