package struct;

public class OccupancyGrid {
    private boolean[][] grid; // true = cella occupata

    public OccupancyGrid(int rows, int cols){
        this.grid = new boolean[rows][cols];
        System.out.println(this.grid);
    }

    public void setCell(int r, int c, boolean value){
        this.grid[r][c] = value;
    }

    public boolean getCell(int r, int c){
        return this.grid[r][c];
    }

    public int getRowsNr(){
        return this.grid.length;
    }

    public int getColsNr(){
        return this.grid[0].length;
    }

    public Graph convertToGraph(){
        Graph g = new Graph();

        Node[][] nodes = new Node[this.getRowsNr()][this.getColsNr()];

        for(int i=0; i<this.getRowsNr(); i++){
            for(int j=0; j<this.getColsNr(); j++){
                if(!this.getCell(i, j)){
                    nodes[i][j] = new Node(i, j);
                    g.addEdge(nodes[i][j], null , 0);
                }
                else
                    nodes[i][j] = null;
            }
        }

        for(int i=0; i<this.getRowsNr(); i++){
            for(int j=0; j<this.getColsNr(); j++){
                try {
                    if(nodes[i+1][j]!=null)
                        g.addEdge(nodes[i][j], nodes[i+1][j], 1); 
                }catch(ArrayIndexOutOfBoundsException exception){};
                try{
                    if(nodes[i-1][j]!=null)
                        g.addEdge(nodes[i][j], nodes[i-1][j], 1);
                }catch(ArrayIndexOutOfBoundsException exception){};
                try{
                    if(nodes[i][j+1]!=null)
                        g.addEdge(nodes[i][j], nodes[i][j+1], 1);
                }catch(ArrayIndexOutOfBoundsException exception){};
                try{
                    if(nodes[i][j-1]!=null)
                        g.addEdge(nodes[i][j], nodes[i][j-1], 1);
                }catch(ArrayIndexOutOfBoundsException exception){};
                

                // try {
                //     if(nodes[i+1][j+1]!=null)
                //         g.addEdge(nodes[i][j], nodes[i+1][j+1], 1.4); 
                // }catch(ArrayIndexOutOfBoundsException exception){};
                // try{
                //     if(nodes[i-1][j+1]!=null)
                //         g.addEdge(nodes[i][j], nodes[i-1][j+1], 1.4);
                // }catch(ArrayIndexOutOfBoundsException exception){};
                // try{
                //     if(nodes[i+1][j+1]!=null)
                //         g.addEdge(nodes[i][j], nodes[i+1][j+1], 1.4);
                // }catch(ArrayIndexOutOfBoundsException exception){};
                // try{
                //     if(nodes[i-1][j-1]!=null)
                //         g.addEdge(nodes[i][j], nodes[i-1][j-1], 1.4);
                // }catch(ArrayIndexOutOfBoundsException exception){};

            }
        }

        return g;
    }

    @Override
    public String toString(){
        String s = "";
        for(int i=0; i<this.getRowsNr(); i++){
            for(int j=0; j<this.getColsNr(); j++){
                s += this.getCell(i, j) + "\t";
            }
            s += "\n";
        }
        return s;
    }
}
