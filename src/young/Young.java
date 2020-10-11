/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package young;

import java.awt.Color;
import org.generation5.bio.CellularAutomata;

/**
 * This class implements Conway's Life using the CellularAutomata classes and
 * double-buffering for speed.
 *
 * @author James Matthews
 */
public class Young extends CellularAutomata {
    /**
     * Default constructor
     */    
    double R1,R2,w1,w2,percent;
    
    public Young() {
        this(0, 0);
    }
    
    /**
     * Constructor with size initializors.
     * @param size_x the x-size of the world.
     * @param size_y the y-size of the world.
     */    
    public Young(int size_x, int size_y) {
        super(size_x, size_y, DOUBLE_BUFFERING);
    }
    
    public Young(int n,double _R1, double _R2, double _w1, double _w2, double percent) {
        super(n, n, DOUBLE_BUFFERING);
        this.R1=_R1;
        this.R2=_R2;
        this.w1=_w1;
        this.w2=_w2;
        this.percent=percent;
        setCASize(500/n);
        drawGrid(true);       
        
    }
    
    void setPercent(double _percent){
    this.percent=_percent;
    }
    
    double euclidea(int i, int j, int iv, int jv) {
        double a=Math.pow(i-iv,2);
        double b=Math.pow(j-jv,2);
        
        return Math.sqrt(a+b);
    }
    
    /**
     * Advance the world one timestep.
     */    
    @Override
    public void doStep() {
        double R=0;
        double sum=0;
        
        for (int i = 0; i < caWorld_x; i++) {
            for (int j = 0; j < caWorld_y; j++) {
                for (int iv = 0; iv < caWorld_x; iv++) {
                    for (int jv = 0; jv < caWorld_y; jv++) {
                        if (getWorldAt(iv,jv) == 1) {
                            R = euclidea(i, j, iv, jv);                            
                        }
                        if((R<=R2) && (iv!=i || jv!=j)){
                            if(R<=R1) sum+=w1;
                            if((R>R1) && (R<=R2)) sum+=w2;
                        }
                        
                    }

                }
                
                if(sum>0) setWorldAt(i, j, 1);
                if(sum==0) setWorldAt(i, j, getWorldAt(i, j));                    
                if(sum<0) setWorldAt(i, j, 0);                                
                sum=0;
            }

        }
        
        flipBuffer(); 
        
    }
    
    /**
     * Randomly initializes the world.
     */    
    @Override
    public void init() {
        // Default colours
        setWorldColour(0, Color.black);
        setWorldColour(1, Color.yellow);
        // Default geometry
        setGeometry(TORODIAL);
        // Create an initial random grid
        for (int i = 0; i < caWorld_x; i++) {
            for (int j = 0; j < caWorld_y; j++) {
                if (Math.random() > percent)
                    setWorldAt(i, j, 1);
                else
                    setWorldAt(i, j, 0);
            }
        }
        
        flipBuffer();
    }    
    
}
