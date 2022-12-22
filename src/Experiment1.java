import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import alg.GraphFactory;

public class Experiment1 {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
        // esecuzione a vuoto
        for(int q=0; q<10; q++)
            GraphFactory.genRandomConnectedGraph(100, 1000);

        String outputString;
        int step;
        int test;
        int k;
        PrintWriter writer;

        boolean[] run = {true, true, true, true};

        if(run[0]){
            outputString = "n\tm\tm=n-1\n";
            step = 100000;
            test = 100;
            for(int i=step; i<=step*10; i+=step){
                System.out.println(i);
                for(int j=0; j<test; j++){
                    int e = i-1;
                    long t0=System.nanoTime();
                    GraphFactory.genRandomConnectedGraph(i, e);
                    long t1=System.nanoTime();
    
                    outputString += (i + "\t" + e + "\t" + (t1-t0)/1E6);
                    outputString += "\n";
                }   
                outputString += "\n\n";
            }
            System.out.println(outputString);
            writer = new PrintWriter("../ExperimentData/Experiment1/exp11.txt", "UTF-8");
            writer.println(outputString);
            writer.close();
        }
        
        if(run[1]){
            k=1000;
            outputString = "n\tm\tm=n+k\n";
            step = 10000;
            test = 100;
            
            for(int i=step; i<=step*10; i+=step){
                System.out.println(i);
                for(int j=0; j<test; j++){
                    int e = i+k;
                    long t0=System.nanoTime();
                    GraphFactory.genRandomConnectedGraph(i, e);
                    long t1=System.nanoTime();
    
                    outputString += (i + "\t" + e + "\t" + (t1-t0)/1E6);
                    outputString += "\n";
                }   
                outputString += "\n\n";
            }
            System.out.println(outputString);
            writer = new PrintWriter("../ExperimentData/Experiment1/exp12.txt", "UTF-8");
            writer.println(outputString);
            writer.close();
        }
        
        if(run[2]){
            k=2;
            outputString = "n\tm\tm=kn\n";
            step = 1000;
            test = 100;
            for(int i=step; i<=step*10; i+=step){
                System.out.println(i);
                for(int j=0; j<test; j++){
                    int e = k*i;
                    long t0=System.nanoTime();
                    GraphFactory.genRandomConnectedGraph(i, e);
                    long t1=System.nanoTime();
    
                    outputString += (i + "\t" + e + "\t" + (t1-t0)/1E6);
                    outputString += "\n";
                }   
                outputString += "\n\n";
            }
            System.out.println(outputString);
            writer = new PrintWriter("../ExperimentData/Experiment1/exp13.txt", "UTF-8");
            writer.println(outputString);
            writer.close();
        }
        
        if(run[3]){ 
            k=2;
            outputString = "n\tm\tm=kn^2\n";
            step = 100;
            test = 10;
            for(int i=step; i<=step*10; i+=step){
                System.out.println(i);
                for(int j=0; j<test; j++){
                    int e = k*i*i;
                    long t0=System.nanoTime();
                    GraphFactory.genRandomConnectedGraph(i, e);
                    long t1=System.nanoTime();
    
                    outputString += (i + "\t" + e + "\t" + (t1-t0)/1E6);
                    outputString += "\n";
                }   
                outputString += "\n\n";
            }
            System.out.println(outputString);
            writer = new PrintWriter("../ExperimentData/Experiment1/exp14.txt", "UTF-8");
            writer.println(outputString);
            writer.close();
        }
        
    }
}
