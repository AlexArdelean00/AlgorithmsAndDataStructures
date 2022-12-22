package struct;

public class Edge {
    private Node connectedNode;
    private double weight;

    public Edge(Node node, double weight){
        this.connectedNode = node;
        this.weight = weight;
    }

    public Node getConnectedNode(){
        return this.connectedNode;
    }

    public double getWeight(){
        return this.weight;
    }

    @Override
    public String toString(){
        String s = "";
        s += this.connectedNode + " [" + this.weight + "]";
        return s;
    }

    @Override
    public boolean equals(Object obj){
        Edge e = (Edge)obj;
        if(this.connectedNode.equals(e.getConnectedNode()) && this.weight == e.getWeight())
            return true;
        return false;
    }
}
