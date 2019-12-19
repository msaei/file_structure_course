public class Range {

    public int lo;
    public int hi;

    public Range(int lo, int hi){
        this.lo = lo;
        this.hi = hi;
    }
    public String toString(){
        return "[" + lo + "," + hi + "]";
    }
}
