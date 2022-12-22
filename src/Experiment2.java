import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import alg.AStar;
import struct.Graph;
import struct.Node;

public class Experiment2 {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{

        String path = "./../RandomGraph/graph_n_2n/";
        Graph[] graphs = new Graph[10];
        int step = 50000;
        for(int i=0; i<graphs.length; i++){
            System.out.println("Loading graph " + i);
            int n = (i+1)*step;
            int m = 2*n;
            String folder = "graph_n" + n + "_m" + m + "/"; 
            String nodesPath = path + folder + "nodes.txt";
            String edgesPath = path + folder + "edge.txt";
            graphs[i] = new Graph();
            graphs[i].readFromFile(nodesPath, edgesPath);
        }

        for(int t=0; t<10; t++)
            AStar.aStar(graphs[0], graphs[0].getRandomNode(), graphs[0].getRandomNode(), false);

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

        outputString = "n\tm\tA*\n";
        step = 50000;
        test = 100;
        int index = 0;
        for(int i=step; i<=step*10; i+=step){
            n = i;
            m = i*2;
            g = graphs[index];
            index++;
            System.out.println(i);
            for(int j=0; j<test; j++){
                source = g.getRandomNode();
                dest = g.getRandomNode();
                t0=System.nanoTime();
                AStar.aStar(g, source, dest, false);
                t1=System.nanoTime();

                outputString += (n + "\t" + m + "\t" + (t1-t0)/1E6);
                outputString += "\n";
            }   
            outputString += "\n\n";
        }
        //System.out.println(outputString);
        writer = new PrintWriter("../ExperimentData/Experiment2/astar.txt", "UTF-8");
        writer.println(outputString);
        writer.close();

    }
}
