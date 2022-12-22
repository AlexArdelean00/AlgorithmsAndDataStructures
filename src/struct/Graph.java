package struct;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Graph {
    private ArrayList<Node> nodes;
    private ArrayList<LinkedList<Edge>> adj;

    public Graph(){
        this.nodes = new ArrayList<Node>();
        this.adj = new ArrayList<LinkedList<Edge>>();
    }

    public boolean addEdge(Node n1, Node n2, double w){
        if(n1 != null){
            this.addNode(n1);
            if(n2 != null){
                this.addNode(n2);
                if(!existsEdge(n1, n2)){
                    double weight = w;
                    Edge e1 = new Edge(n2, weight);
                    Edge e2 = new Edge(n1, weight);
                    this.adj.get(n1.getID()).add(e1);
                    this.adj.get(n2.getID()).add(e2);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean addNode(Node n){
        // check if node does not exists
        if(n.getID() > this.nodes.size()){
            n.setID(this.nodes.size());
            this.adj.add(new LinkedList<Edge>());
            this.nodes.add(n.getID(), n);
        }
        // if already exists
        return false;
    }

    private boolean existsEdge(Node n1, Node n2){
        LinkedList<Edge> adjs = this.getAdjListOf(n1);
        for(Edge e: adjs){
            if(e.getConnectedNode() == n2)
                return true;
        }
        return false;
    }

    public void removeEdge(Node n, Edge e){
        this.adj.get(n.getID()).remove(e);
    }

    public ArrayList<Node> getNodes(){
        return this.nodes;
    }

    public LinkedList<Edge> getAdjListOf(Node n) {
        return this.adj.get(n.getID());
    }

    public int getEdgesNr(){
        int i=0;
        for(Node n: this.getNodes()){
            i += this.getAdjListOf(n).size();
        }
        return i/2;
    }

    public int getNodesNr(){
        return this.nodes.size();
    }

    public Node getRandomNode() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(this.getNodesNr());
        return this.nodes.get(index);
    }

    public boolean readFromFile(String nodes, String edges){
        ArrayList<Node> nodesList = new ArrayList<>();
        try{
            System.out.println("Building graph from files " + nodes + " and " + edges + "...");
            try {
                File file = new File(nodes);
                Scanner scanner = new Scanner(file);
                scanner.useDelimiter(",|\r\n");
                while (scanner.hasNext()) {
                    String[] nodeArray = scanner.next().split("\\s"); // id - x - y
                    Node n = new Node(Double.parseDouble(nodeArray[1]), Double.parseDouble(nodeArray[2]));
                    nodesList.add(Integer.parseInt(nodeArray[0]), n);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } 
    
            try {
                File file = new File(edges);
                Scanner scanner = new Scanner(file);
                scanner.useDelimiter(",|\r\n");
                while (scanner.hasNext()) {
                    String[] nodeArray = scanner.next().split("\\s"); // id - n1 - n2 - w
                    Node n1 = nodesList.get(Integer.parseInt(nodeArray[1]));
                    Node n2 = nodesList.get(Integer.parseInt(nodeArray[2]));
                    this.addEdge(n1, n2, Double.parseDouble(nodeArray[3]));
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } 
        }
        catch(Exception e){
            return false;
        }
        System.out.println("Build completed");
        return true;
    }

    public void writeToFile(String nodesFileName, String edgesFileName) throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter writer = new PrintWriter(nodesFileName, "UTF-8");
        
        PrintWriter writer2 = new PrintWriter(edgesFileName, "UTF-8");

        int i=0;

        class Couple{
            int i, j;
            public Couple(int i, int j){
                this.i = i;
                this.j = j;
            }

            @Override
            public boolean equals(Object obj){
                Couple e = (Couple)obj;
                if((i==e.i && j==e.j) || (i==e.j && j==e.i))
                    return true;
                return false;
            }

            @Override
            public int hashCode() {
                int result = i;
                result = 31 * result + j;
                return result;
            }
        }

        Set<Couple> printedEdges = new HashSet(); 

        for(Node n: this.getNodes()){
            writer.println(n.getID() + " " + n.getX() + " " + n.getY());

            for(Edge e: this.getAdjListOf(n)){
                Couple c = new Couple(n.getID(), e.getConnectedNode().getID());
                Couple c2 = new Couple(e.getConnectedNode().getID(), n.getID());
                if(!printedEdges.contains(c) && !printedEdges.contains(c2)){
                    writer2.println(i + " " + n.getID() + " " + e.getConnectedNode().getID() + " " + e.getWeight());
                    i++;
                }
                    
                printedEdges.add(c);
                printedEdges.add(c2);
                //System.out.println(printedEdges.size());

            }
        }

        writer.close();
        writer2.close();
    }

    @Override
    public String toString(){
        String s = "";
        for(Node n: this.nodes){
            s += n;
            LinkedList<Edge> adjs = this.getAdjListOf(n);
            for(Edge e: adjs){
                s += "->";
                s += e;
            }
            s += "\n";
        }
        return s;
    }

    public Node closestNodeTo(double x, double y) {
        double minDist = Double.MAX_VALUE;
        Node closestNode = null;
        for(Node n: this.nodes){
            double dist = Math.sqrt(Math.pow(x-n.getX(), 2) + Math.pow(y-n.getY(), 2));
            if(dist < minDist){
                minDist = dist;
                closestNode = n;
            }
        }
        return closestNode;
    }
}
