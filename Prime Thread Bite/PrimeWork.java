
package primethreadbite;

/**
 * @author Shreyash & Kosta
 */

public class PrimeWork {
    
    final int lo;
    final int hi;
    final int bite;
    private int current;
    private boolean complete;
    
    public PrimeWork(int max, int bite){
        lo = 3;
        hi = max;
        current = lo;
        this.bite = bite;
    }
    
    public PrimeWork(int min, int max, int bite){
        lo = min;
        hi = max; 
        current = lo;
        this.bite = bite;
    }
    
    public synchronized Range getWork(){
        Range range;
        if (! complete){
            if (current + bite > hi){
                range = new Range(current, hi);
                complete = true;
            }else{
                range = new Range(current, current + bite-1);
                current += bite;
            }
            return range;
        }
        return null;
    }
    
    public boolean moreWork(){
        return ! complete;
    }
}

