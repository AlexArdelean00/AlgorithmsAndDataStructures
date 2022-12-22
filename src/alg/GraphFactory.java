package alg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import struct.Edge;
import struct.Graph;
import struct.Node;

public class GraphFactory {
    private static final double X_RANGE = 800;
    private static final double Y_RANGE = 800;

    public static Graph genRandomConnectedGraph(int nrNodes, int nrEdges){
        long maxEdges = ((long)nrNodes)*(nrNodes-1)/2;
        if(nrEdges > maxEdges)
            nrEdges = nrNodes*(nrNodes-1)/2;
        else if(nrEdges < nrNodes-1)
            nrEdges = nrNodes-1;

        // add nrNodes random connected nodes to the graph
        Graph g = new Graph();
        ArrayList<Node> availableNodes = new ArrayList<>();
        Node n1 = GraphFactory.genRandomNode();
        availableNodes.add(n1);
        for(int i=0; i<nrNodes-1; i++){
            Node n2 = GraphFactory.genRandomNode();
            if(nrNodes > 3)
                availableNodes.add(n2);
            g.addEdge(n1, n2, n1.euclideanDistance(n2));
            n1 = n2;
        }

        int remaingEdges = nrEdges-(nrNodes-1); 
        ArrayList<Node> candidateNodes = new ArrayList<>(nrNodes);
        LinkedList<Edge> adj;
        Node n2;
        for(int i=0; i<remaingEdges; i++){
            // select a random n1 node from availableNodes
            n1 = availableNodes.get(new Random().nextInt(availableNodes.size()));

            // create the array candidateNodes = {{nodes-n1}-adj}
            candidateNodes.clear();
            candidateNodes.addAll(g.getNodes());
            candidateNodes.remove(n1);
            adj = g.getAdjListOf(n1);
            for(Edge e: adj)
                candidateNodes.remove(e.getConnectedNode());

            // select a random n2 and create the edge (n1, n2)
            n2 = candidateNodes.get(new Random().nextInt(candidateNodes.size()));
            g.addEdge(n1, n2, n1.euclideanDistance(n2));

            // remove n1 and n2 from availableNodes if not more available
            if(adj.size()==nrNodes-1)
                availableNodes.remove(n1);
            if(g.getAdjListOf(n2).size()==nrNodes-1)
                availableNodes.remove(n2);
        }
        return g;
    }

    private static Node genRandomNode(){
        double x = Math.random()*X_RANGE;
        double y = Math.random()*Y_RANGE;
        return new Node(x, y);
    }
}
