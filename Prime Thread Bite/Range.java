/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primethreadbite;

/**
 * @author Shreyash & Kosta
 */

public class Range {
    
    public int lo;
    public int hi;
    
    public Range(int lo, int hi){
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public String toString(){
        return "[" + lo + "," + hi + "]";
    }
}


