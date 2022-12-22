package gfx;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

import alg.AStar;
import alg.GraphFactory;
import alg.OccupancyGridFactory;
import struct.Graph;
import struct.Node;
import struct.OccupancyGrid;

public class GraphHandler extends JPanel implements ActionListener, MouseListener{
    private GraphDrawer gd;

    private JPanel leftPanel;

    // Random graph generator handler
    private JButton genRandomGraphButton;
    private JSpinner randomGraphNodesNrSetter;
    private JSpinner randomGraphEdgesNrSetter;

    private final String GEN_RANDOM_GRAPH_BUTTON_STRING = "Gen Random Graph";
    private final int DEFAULT_NODES_NR = 10;
    private final int MIN_NODES_NR = 2;
    private final int MAX_NODES_NR = 1000;
    private final int DEFAULT_EDGES_NR = 10;
    private final int MIN_EDGES_NR = 0;
    private final int MAX_EDGES_NR = MAX_NODES_NR*(MAX_NODES_NR-1)/2;
    private final String RANDOM_GRAPH_NODES_NR_SETTER_STRING = "Nr nodi";
    private final String RANDOM_GRAPH_EDGES_NR_SETTER_STRING = "Nr archi";

    // Random OccupancyGrid generator handler
    private JButton genRandomOccupancyGridButton;
    private JSpinner randomOccupancyGridRowsNrSetter;
    private JSpinner randomOccupancyGridColsNrSetter;
    private JSpinner randomOccupancyGridProbabilitySetter;
    private JButton switchOGViewButton;

    private final String GEN_RANDOM_OCCUPANCY_GRID_BUTTON_STRING = "Gen Random OccupancyGrid";
    private final String SWITCH_OG_VIEW_BUTTON_STRING = "Switch view: OG<->Graph";
    private final int DEFAULT_ROW = 20;
    private final int DEFAULT_COL = 20;
    private final int MAX_ROW = 200;
    private final int MAX_COL = 200;
    private final int MIN_ROW = 5;
    private final int MIN_COL = 5;
    private final int DEFAULT_PROBABILITY = 10;
    private final int MAX_PROBABILITY = 100;
    private final int MIN_PROBABILITY = 0;
    private final String RANDOM_OCCUPANCY_GRID_ROWS_SETTER_STRING = "Nr righe";
    private final String RANDOM_OCCUPANCY_GRID_COLS_SETTER_STRING = "Nr colonne";
    private final String RANDOM_OCCUPANCY_GRID_PROBABILITY_SETTER_STRING = "Probabilità cella occupata";

    // Road Graphs handler
    private JButton nodesFileChoserButton;
    private JButton edgesFileChoserButton;
    private JButton generateRoadGraphButton;

    private final String NODES_FILE_CHOSER_BUTTON_STRING = "Scegli il file nodi";
    private final String NODES_FILE_CHOSER_STRING = "Nodi";
    private final String EDGES_FILE_CHOSER_BUTTON_STRING = "Scegli il file archi";
    private final String EDGES_FILE_CHOSER_STRING = "Achi";
    private final String ROAD_GRAPH_BUTTON_STRING = "Genera grafo da file";

    private String edgesPath;
    private String nodesPath;
    private boolean flipY;

    // AStar handler
    private JRadioButton sourceRadioButton;
    private JRadioButton destRadioButton;
    private JButton calculatePathWithAStar;
    private final String CALCULATE_PATH_BUTTON_STRING = "Calcola percorso con A*";
    private final String SOURCE_RADIO_BUTTON_STRING = "Seleziona la sorgente con il mouse";
    private final String DEST_RADIO_BUTTON_STRING = "Seleziona la destinazione con il mouse";
    
    public GraphHandler(GraphDrawer gd){
        this.gd = gd;

        this.gd.addMouseListener(this);

        this.leftPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        this.leftPanel.setPreferredSize(new Dimension(300, 800));

        this.createRandomGraphGeneratorPanel();
        this.createRandomOGGeneratorPanel();
        this.createGraphFromFilePanel();
        this.aStarRunnerPanel();

        this.add(leftPanel);

        this.setVisible(true);
    }

    public void createRandomGraphGeneratorPanel() {
        // Random graph generator handler
        this.genRandomGraphButton = new JButton(GEN_RANDOM_GRAPH_BUTTON_STRING);
        this.genRandomGraphButton.addActionListener(this);

        this.randomGraphNodesNrSetter = new JSpinner(
            new SpinnerNumberModel(DEFAULT_NODES_NR, 
            MIN_NODES_NR, MAX_NODES_NR, 1));
        JPanel randomGraphNodesNrSetterPanel = createLabeledComponent(RANDOM_GRAPH_NODES_NR_SETTER_STRING, this.randomGraphNodesNrSetter);

        this.randomGraphEdgesNrSetter = new JSpinner(
            new SpinnerNumberModel(DEFAULT_EDGES_NR, 
            MIN_EDGES_NR, MAX_EDGES_NR, 1));
        JPanel randomGraphEdgesNrSetterPanel = createLabeledComponent(RANDOM_GRAPH_EDGES_NR_SETTER_STRING, this.randomGraphEdgesNrSetter);

        JPanel randomGraphPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        randomGraphPanel.setBorder(new TitledBorder("Random Graph Generator"));
        randomGraphPanel.add(randomGraphNodesNrSetterPanel);
        randomGraphPanel.add(randomGraphEdgesNrSetterPanel);
        randomGraphPanel.add(this.genRandomGraphButton);

        this.leftPanel.add(randomGraphPanel);
    }

    public void createRandomOGGeneratorPanel() {
        // Random OccupancyGrid generator handler
        this.genRandomOccupancyGridButton = new JButton(GEN_RANDOM_OCCUPANCY_GRID_BUTTON_STRING);
        this.genRandomOccupancyGridButton.addActionListener(this);

        this.switchOGViewButton = new JButton(SWITCH_OG_VIEW_BUTTON_STRING);
        this.switchOGViewButton.addActionListener(this);

        this.randomOccupancyGridRowsNrSetter = new JSpinner(
            new SpinnerNumberModel(DEFAULT_ROW, 
            MIN_ROW, MAX_ROW, 1));
        JPanel randomOccupancyGridRowSetterPanel = createLabeledComponent(RANDOM_OCCUPANCY_GRID_ROWS_SETTER_STRING, this.randomOccupancyGridRowsNrSetter);

        this.randomOccupancyGridColsNrSetter = new JSpinner(
            new SpinnerNumberModel(DEFAULT_COL, 
            MIN_COL, MAX_COL, 1));
        JPanel randomOccupancyGridColSetterPanel = createLabeledComponent(RANDOM_OCCUPANCY_GRID_COLS_SETTER_STRING, this.randomOccupancyGridColsNrSetter);

        this.randomOccupancyGridProbabilitySetter = new JSpinner(
            new SpinnerNumberModel(DEFAULT_PROBABILITY, 
            MIN_PROBABILITY, MAX_PROBABILITY, 10));
        JPanel randomOccupancyGridProbSetterPanel = createLabeledComponent(RANDOM_OCCUPANCY_GRID_PROBABILITY_SETTER_STRING, this.randomOccupancyGridProbabilitySetter);

        JPanel randomOGPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        randomOGPanel.setBorder(new TitledBorder("Random OccupancyGrid Generator"));
        randomOGPanel.add(randomOccupancyGridRowSetterPanel);
        randomOGPanel.add(randomOccupancyGridColSetterPanel);
        randomOGPanel.add(randomOccupancyGridProbSetterPanel);
        randomOGPanel.add(this.genRandomOccupancyGridButton);
        randomOGPanel.add(this.switchOGViewButton);

        this.leftPanel.add(randomOGPanel);
    }

    public void createGraphFromFilePanel() {
        // Road graph handler
        this.nodesFileChoserButton = new JButton(NODES_FILE_CHOSER_BUTTON_STRING);
        this.nodesFileChoserButton.addActionListener(this);
        JPanel nodesSetterPanel = createLabeledComponent(NODES_FILE_CHOSER_STRING, this.nodesFileChoserButton);

        this.edgesFileChoserButton = new JButton(EDGES_FILE_CHOSER_BUTTON_STRING);
        this.edgesFileChoserButton.addActionListener(this);
        JPanel edgesSetterPanel = createLabeledComponent(EDGES_FILE_CHOSER_STRING, this.edgesFileChoserButton);

        this.generateRoadGraphButton = new JButton(ROAD_GRAPH_BUTTON_STRING);
        this.generateRoadGraphButton.addActionListener(this);

        JPanel roadGraphPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        roadGraphPanel.setBorder(new TitledBorder("Road graph"));
        roadGraphPanel.add(nodesSetterPanel);
        roadGraphPanel.add(edgesSetterPanel);
        roadGraphPanel.add(this.generateRoadGraphButton);

        this.leftPanel.add(roadGraphPanel);
    }

    public void aStarRunnerPanel() {
        // source-dest selector
        ButtonGroup group = new ButtonGroup();   
        this.sourceRadioButton = new JRadioButton(SOURCE_RADIO_BUTTON_STRING);
        this.sourceRadioButton.setSelected(true);
        this.destRadioButton = new JRadioButton(DEST_RADIO_BUTTON_STRING);
        group.add(sourceRadioButton);
        group.add(destRadioButton);
        this.calculatePathWithAStar = new JButton(CALCULATE_PATH_BUTTON_STRING);
        this.calculatePathWithAStar.addActionListener(this);

        JPanel AStarPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        AStarPanel.setBorder(new TitledBorder("A*"));
        AStarPanel.add(this.sourceRadioButton);
        AStarPanel.add(this.destRadioButton);
        AStarPanel.add(this.calculatePathWithAStar);

        this.leftPanel.add(AStarPanel);
    }

    private JPanel createLabeledComponent(String s, JComponent spinner){
        JPanel panel = new JPanel();
        JLabel label = new JLabel(s);   
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);
        panel.add(label);
        panel.add(spinner);
        layout.putConstraint(SpringLayout.WEST, label,
                             5,
                             SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, label,
                             5,
                             SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, spinner,
                             5,
                             SpringLayout.EAST, label);
        layout.putConstraint(SpringLayout.NORTH, spinner,
                             5,
                             SpringLayout.NORTH, panel);

        return panel;
    }

    private void updateGraph(Graph g){
        this.gd.setSource(null);
        this.gd.setSource(null);
        this.gd.setSource(null);
        this.gd.setDest(null);
        this.gd.setFlipY(this.flipY);
        this.gd.drawGraph(g);
    }

    // Mouse Listener
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        int x=e.getX();
        int y=e.getY();
        System.out.println(x+","+y); 
        if(this.sourceRadioButton.isSelected()){
            Node source = this.gd.getNodeAt(x,y);
            this.gd.setSource(source);
            System.out.println("source " + source);
        }
        else if(this.destRadioButton.isSelected()){
            Node dest = this.gd.getNodeAt(x,y);
            this.gd.setDest(dest);
            System.out.println("dest " + dest);
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    // Action Listener
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == this.genRandomGraphButton){
            System.out.print("button clicked");
            int nrNodes = (int) this.randomGraphNodesNrSetter.getValue();
            int nrEdges = (int) this.randomGraphEdgesNrSetter.getValue();
            Graph g = GraphFactory.genRandomConnectedGraph(nrNodes, nrEdges);
            this.flipY = false;
            updateGraph(g);
        }

        if(e.getSource() == this.genRandomOccupancyGridButton){
            System.out.print("button clicked");
            int nrRow = (int) this.randomOccupancyGridRowsNrSetter.getValue();
            int nrCol = (int) this.randomOccupancyGridColsNrSetter.getValue();
            double prob = (double) ((int)this.randomOccupancyGridProbabilitySetter.getValue())/100;
            OccupancyGrid og = OccupancyGridFactory.genRandomOccupancyGrid(nrRow, nrCol, prob);
            this.flipY = false;
            updateGraph(og.convertToGraph());
            this.gd.drawOG(og);
        }

        if(e.getSource() == this.nodesFileChoserButton){
            System.out.print("button clicked");
            JFileChooser fc = new JFileChooser("..");
            fc.showSaveDialog(null);
            this.nodesPath = fc.getSelectedFile().getAbsolutePath();
            System.out.print(nodesPath);
        }

        if(e.getSource() == this.edgesFileChoserButton){
            System.out.print("button clicked");
            JFileChooser fc = new JFileChooser("..");
            fc.showSaveDialog(null);
            this.edgesPath = fc.getSelectedFile().getAbsolutePath();
            System.out.print(edgesPath);
        }

        if(e.getSource() == this.generateRoadGraphButton){
            System.out.print("button clicked");
            Graph g = new Graph();
            boolean result = g.readFromFile(this.nodesPath, this.edgesPath);
            if(!result){
                JOptionPane.showMessageDialog(null, "File selezionati non validi");
            }
            else{
                this.flipY = true;
                updateGraph(g);
            }
        }

        if(e.getSource() == this.calculatePathWithAStar){
            System.out.print("button clicked");
            if(this.gd.getSource() == null){
                JOptionPane.showMessageDialog(null, "Sorgente non selezionata");
            }
            else if(this.gd.getDest() == null){
                JOptionPane.showMessageDialog(null, "Destinazione non selezionata");
            }
            else{
                boolean path = AStar.aStar(this.gd.getGraph(), this.gd.getSource(), this.gd.getDest(), false);
                if(!path)
                    JOptionPane.showMessageDialog(null, "Nessun percorso trovato");
                this.gd.drawPath(path);
            }
        }

        if(e.getSource() == this.switchOGViewButton){
            this.gd.switchOGView();
        }
    }
    
}
