package gfx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import struct.Edge;
import struct.Graph;
import struct.Node;
import struct.OccupancyGrid;

public class GraphDrawer extends JPanel{
    private Graph graph;
    private Node source;
    private Node dest;
    private int diameter;
    private double scaleFactor;
    private int xGap;
    private int yGap;
    private boolean flipY;
    private OccupancyGrid og;
    private int cellSize;
    private boolean path;
    private boolean ogView;

    private static final Color NODE_DEFUALT_COLOR = Color.GRAY;
    private static final Color EDGE_DEFUALT_COLOR = Color.GRAY;
    private static final int NODE_DEFAULT_DIAMETER = 40;
    private static final int EDGE_DEFAULT_SIZE = 1;
    private static final int DRAWING_SPACE_GAP = 10;  
    private static final Color OCCUPIED_CELL_COLOR = Color.BLACK;
    private static final Color FREE_CELL_COLOR = Color.WHITE;
    private static final int OG_GAP = 10;
    private static final Color SOURCE_NODE_COLOR = Color.BLUE;
    private static final Color DEST_NODE_COLOR = Color.RED;
    private static final Color PATH_COLOR = Color.YELLOW;
    private static final int MIN_DIAMETER_SOURCE_DEST = 10;

    public GraphDrawer(){
        this.setPreferredSize(new Dimension(800, 800));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.flipY = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;

        if(this.flipY){
            g2d.scale(1, -1);
            g2d.translate(0, -this.getHeight());
        }

        this.updateDrawingParams();

        if(this.og != null && this.ogView){
            for(int i=0; i<this.og.getRowsNr(); i++){
                for(int j=0; j<this.og.getColsNr(); j++){
                    this.drawCell(i, j, this.og.getCell(i, j), g2d, FREE_CELL_COLOR);
                }
            }

            if(path){
                Node current = dest;
                while(current.getPi()!=null){
                    drawCell((int)current.getX(), (int)current.getY(), false, g2d, PATH_COLOR);
                    current = current.getPi();
                }
            }

            if(this.source != null){
                this.drawCell((int)this.source.getX(), (int)this.source.getY(), false, g2d, SOURCE_NODE_COLOR);
            }

            if(this.dest != null)
                this.drawCell((int)this.dest.getX(), (int)this.dest.getY(), false, g2d, DEST_NODE_COLOR);
        }
        else if(this.graph != null){
            drawGraph(this.graph, g2d, NODE_DEFUALT_COLOR, EDGE_DEFUALT_COLOR);

            if(path){
                Node current = dest;
                while(current.getPi()!=null){
                    drawNode(current, g2d, PATH_COLOR);
                    drawEdge(current, current.getPi(), g2d, PATH_COLOR, EDGE_DEFAULT_SIZE*2);   
                    current = current.getPi();
                }
            }

            this.diameter = Math.max(this.diameter, MIN_DIAMETER_SOURCE_DEST);
            if(this.source != null){
                this.drawNode(this.source, g2d, SOURCE_NODE_COLOR);
            }

            if(this.dest != null)
                this.drawNode(this.dest, g2d, DEST_NODE_COLOR);
        }
    }

    private void drawGraph(Graph g, Graphics2D g2d, Color nodeColor, Color edgeColor){
        ArrayList<Node> nodes = g.getNodes();
        for(Node n: nodes){
            this.drawNode(n, g2d, nodeColor);
        }

        for(Node n1: nodes){
            LinkedList<Edge> adjs = g.getAdjListOf(n1);
            for(Edge e: adjs){
                Node n2 = e.getConnectedNode();
                this.drawEdge(n1, n2, g2d, edgeColor, EDGE_DEFAULT_SIZE);
            }
        }
    }

    private void drawCell(int i, int j, boolean occupied, Graphics2D g2d, Color color){
        int x = i*(cellSize) + OG_GAP;
        int y = j*(cellSize) + OG_GAP;
        if(occupied){
            g2d.setColor(OCCUPIED_CELL_COLOR);
            g2d.fillRect(x, y, cellSize, cellSize);
        }
        else{
            g2d.setColor(color);
            g2d.fillRect(x, y, cellSize, cellSize);
            g2d.setColor(OCCUPIED_CELL_COLOR);
            g2d.drawRect(x, y, cellSize, cellSize);
        }

    }

    private void drawNode(Node n, Graphics2D g2d, Color color) {
        int x = convertXToDrawPanel(n.getX());
        int y = convertYToDrawPanel(n.getY());
        
        g2d.setColor(color);
        g2d.fillOval(x, y, diameter, diameter);
    }

    private void drawEdge(Node n1, Node n2, Graphics2D g2d, Color color, int size){
        int x1 = convertXToDrawPanel(n1.getX()) + this.diameter/2;
        int y1 = convertYToDrawPanel(n1.getY()) + this.diameter/2;

        int x2 = convertXToDrawPanel(n2.getX()) + this.diameter/2;
        int y2 = convertYToDrawPanel(n2.getY()) + this.diameter/2;

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(size));
        g2d.drawLine(x1, y1, x2, y2);
    }

    public void setSource(Node source){
        this.source = source;
        this.path = false;
        repaint();
    }

    public void setDest(Node dest){
        this.dest = dest;
        this.path = false;
        repaint();
    }

    public void drawGraph(Graph graph){
        this.graph = graph;
        this.path = false;
        this.og = null;
        repaint();
    }

    public int convertXToDrawPanel(double x){
        return (int) ((int)x*this.scaleFactor+this.xGap);
    }

    public int convertYToDrawPanel(double y){
        return (int) ((int)y*this.scaleFactor+this.yGap);
    }

    private void updateDrawingParams() {
        int minPanelSize = Math.min(this.getWidth(), this.getHeight()) - DRAWING_SPACE_GAP*2;
        if(this.graph != null){
            // max delta calc
            double maxX = Double.MIN_VALUE;
            double minX = Double.MAX_VALUE;
            double maxY = Double.MIN_VALUE;
            double minY = Double.MAX_VALUE;

            ArrayList<Node> nodes = this.graph.getNodes();
            for(Node n: nodes){
                if(n.getX() < minX)
                    minX = n.getX();
                if(n.getX() > maxX)
                    maxX = n.getX();
                if(n.getY() < minY)
                    minY = n.getY();
                if(n.getY() > maxY)
                    maxY = n.getY();
            }

            double deltaX = maxX-minX;
            double deltaY = maxY-minY;
            double maxDelta = Math.max(deltaX, deltaY);

            // nodes diameter param
            int nodesNr = this.graph.getNodesNr()/5;
            int maxNodesPerRow = minPanelSize/NODE_DEFAULT_DIAMETER;
            this.diameter = NODE_DEFAULT_DIAMETER;
            if(nodesNr>maxNodesPerRow){
                this.diameter = NODE_DEFAULT_DIAMETER*maxNodesPerRow/nodesNr;
            }

            minPanelSize -= this.diameter;

            // scale factor
            this.scaleFactor = minPanelSize/maxDelta;

            // gaps
            this.xGap = (int) (-minX*this.scaleFactor) + DRAWING_SPACE_GAP;
            this.yGap = (int) (-minY*this.scaleFactor) + DRAWING_SPACE_GAP;
        }

        if(this.og != null){
            int maxCells = Math.max(this.og.getColsNr(), this.og.getRowsNr());
            this.cellSize = (int)Math.ceil(minPanelSize/maxCells);
        }
    }

    public void setFlipY(boolean flipY) {
        this.flipY = flipY;
    }

    public void drawOG(OccupancyGrid og) {
        this.og = og;
        this.ogView = true;
        repaint();
    }

    public Node getNodeAt(int xF, int yF) {
        if(this.og != null){
            double x = (xF-OG_GAP)/cellSize;
            double y = (yF-OG_GAP)/cellSize;
            return this.graph.closestNodeTo(x,y);
        }
        else if(this.graph != null){
            double x = (xF-this.xGap)/this.scaleFactor;
            double y = (yF-this.yGap)/this.scaleFactor;
            if(flipY){
                double h = (this.getHeight()-10-this.yGap)/this.scaleFactor;
                y = Math.abs(y-h);
            }
            System.out.println(x + " " + y);
            return this.graph.closestNodeTo(x,y);
        }
        
        return null;
    }

    public Graph getGraph(){
        return this.graph;
    }

    public void drawPath(boolean path) {
        this.path = path;
        repaint();
    }

    public Node getSource() {
        return this.source;
    }

    public Node getDest() {
        return this.dest;
    }

    public void switchOGView() {
        this.ogView = !this.ogView;
        repaint();
    }

}