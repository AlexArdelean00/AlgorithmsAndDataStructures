package alg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

import struct.Edge;
import struct.Graph;
import struct.Node;

public class AStar {
    public static boolean aStar(Graph g, Node source, Node dest, boolean useDijkstra){
        TreeSet<Node> openSet = new TreeSet<Node>();
        openSet.add(source);

        calcHeuristic(g, dest, useDijkstra);
        initializeSingleSource(g, source);

        while(!openSet.isEmpty()){
            Node current = openSet.pollFirst();
            if(current == dest)
                return true;
            
            LinkedList<Edge> neighbors = g.getAdjListOf(current);
            for(Edge e: neighbors){
                Node neighbor = e.getConnectedNode();
                double tentativeDScore = current.getD() + e.getWeight();

                if(tentativeDScore < neighbor.getD()){
                    neighbor.setPi(current);
                    neighbor.setD(tentativeDScore);
                    neighbor.setF(tentativeDScore+neighbor.getH());
                    
                    // decrease key
                    openSet.remove(neighbor);   
                    openSet.add(neighbor);
                }
            }
        }
        return false;
    }

    private static void calcHeuristic(Graph g, Node dest, boolean useDijkstra) {
        ArrayList<Node> nodes = g.getNodes();
        for(Node v: nodes){
            if(useDijkstra)
                v.setH(0); // dijkstra
            else 
                v.setH(v.euclideanDistance(dest));
            
        }
    }

    private static void initializeSingleSource(Graph g, Node source) {
        ArrayList<Node> nodes = g.getNodes();
        for(Node v: nodes){
            v.setPi(null);
            v.setD(Double.MAX_VALUE);
            v.setF(Double.MAX_VALUE);
        }
        source.setD(0);
        source.setF(source.getD()+source.getH());
    }
}
