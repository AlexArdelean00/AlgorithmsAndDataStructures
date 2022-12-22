package gfx;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSeparator;

public class Window extends JFrame{
    private GraphDrawer gd;
    private GraphHandler gh;

    public Window(String title) {
        this.setTitle(title);
        this.setSize(900, 900);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
        this.gd = new GraphDrawer();
        this.gh = new GraphHandler(gd);

        this.add(new JSeparator(), BorderLayout.PAGE_START);
        this.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_END);
        this.add(new JSeparator(), BorderLayout.PAGE_END);

        this.add(gd, BorderLayout.CENTER);
        this.add(gh, BorderLayout.WEST);

        this.pack();
        this.setMinimumSize(this.getSize()); 
        this.setVisible(true);
    }

    public GraphDrawer getGD(){
        return this.gd;
    }
}

