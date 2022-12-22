package struct;

public class Node implements Comparable<Node>{
    private int nodeID;
    private double x,y;
    private double d; // costo da start
    private double h; // costo minimo fino a destinazione 
    private double f; // somma del costo da start più il costo minimo fino a destinazione
    private Node pi;

    public Node(double x, double y){
        this.x = x;
        this.y = y;
        this.nodeID = Integer.MAX_VALUE;
    }

    public int getID(){
        return this.nodeID;
    }

    public void setID(int id){
        this.nodeID = id;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public void setD(double d){
        this.d = d;
    }

    public double getD(){
        return this.d;
    }

    public void setF(double f){
        this.f = f;
    }

    public double getF(){
        return this.f;
    }

    public void setH(double h){
        this.h = h;
    }

    public double getH(){
        return this.h;
    }

    public void setPi(Node node){
        this.pi = node;
    }

    public Node getPi(){
        return this.pi;
    }

    public double euclideanDistance(Node n2){
        return Math.sqrt(Math.pow(this.x-n2.getX(), 2)+Math.pow(this.y-n2.getY(), 2));
    }

    @Override
    public String toString(){
        String s = "";
        s += "node" + this.nodeID + "=" + "(" + this.x + ", " + this.y + ")";
        return s;
    }

    @Override
    public int compareTo(Node o) {
        Node node = (Node)o;
        if (this.f == node.getF()) {
            return Double.valueOf(this.x).compareTo(node.getX());
        } else {
            return Double.valueOf(this.f).compareTo(node.getF());
        }
    }
}
