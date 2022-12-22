package alg;

import struct.OccupancyGrid;

public class OccupancyGridFactory {
    public static OccupancyGrid genRandomOccupancyGrid(int nrRow, int nrCol, double occupancyProb) {
        OccupancyGrid og = new OccupancyGrid(nrRow, nrCol);
        for(int i=0; i<nrRow; i++){
            for(int j=0; j<nrCol; j++){
                boolean cellValue = Math.random()<occupancyProb;
                og.setCell(i, j, cellValue);
            }
        }
        return og;
    }
}
