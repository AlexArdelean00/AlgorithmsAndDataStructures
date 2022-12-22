import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import alg.AStar;
import alg.GraphFactory;
import struct.Graph;
import struct.Node;

public class Experiment4{
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

        String rootPath = "./../Road Networks/";
        String[] roadsFolder = {"OL/", "TG/", "NA/", "SF/"};
        Graph[] roads = new Graph[roadsFolder.length];
        Graph[] randomGraphs = new Graph[roadsFolder.length];
        for(int i=0; i<roads.length; i++){
            roads[i] = new Graph();
            String nodes = rootPath + roadsFolder[i] + "Nodes.txt";
            String edges = rootPath + roadsFolder[i] + "Edges.txt";
            roads[i].readFromFile(nodes, edges);

            n = roads[i].getNodesNr();
            m = roads[i].getEdgesNr();

            randomGraphs[i] = GraphFactory.genRandomConnectedGraph(n, m);
        }


        outputString = "n-m\tRoads\tRandom\n";
        test = 100;
        for(int i=0; i<roads.length; i++){
            System.out.println(i);
            for(int j=0; j<test; j++){
                g = roads[i];
                source = g.getRandomNode();
                dest = g.getRandomNode();
                t0=System.nanoTime();
                AStar.aStar(g, source, dest, false);
                t1=System.nanoTime();
                long roadsTime = t1-t0;

                g = randomGraphs[i];
                source = g.getRandomNode();
                dest = g.getRandomNode();
                t0=System.nanoTime();
                AStar.aStar(g, source, dest, false);
                t1=System.nanoTime();
                long randomTime = t1-t0;

                outputString += ("n: " + g.getNodesNr() + " m: " + g.getEdgesNr() + "\t" + roadsTime/1E6 + "\t" + randomTime/1E6);
                outputString += "\n";
            }   
            outputString += "\n\n";
        }
        //System.out.println(outputString);
        writer = new PrintWriter("../ExperimentData/Experiment4/roadsRandom.txt", "UTF-8");
        writer.println(outputString);
        writer.close();

    }
}
