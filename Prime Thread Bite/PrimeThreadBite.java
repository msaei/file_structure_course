
package primethreadbite;

/**
 * @author Shreyash & Kosta
 */

public class PrimeThreadBite extends Thread{
    
    private long nTime;
    private int pCount;
    private final PrimeWork myWork;
    private final String thread;
    private int bite;
    
    public PrimeThreadBite(PrimeWork myWork, String name){
        thread = name;
        this.myWork = myWork;
    }
    public boolean isPrime(int n) {
        if (n % 2 == 0) return false;
        int limit = (int)Math.ceil(Math.sqrt(n));
        for (int divisor = 3; divisor <= limit; divisor +=2)
            if (n % divisor == 0)
                return false;
        return true;
    }
    
    @Override
    public void run(){
        long start = System.nanoTime();
        while (myWork.moreWork()){
            bite++;
            Range range = myWork.getWork();
            for (int num = range.lo; num <= range.hi; num +=2){
                if (isPrime(num)){
                    pCount++;
                }
            }
        }
        long end = System.nanoTime();
        nTime = end - start;
        System.out.println(thread + " - Bite: " + bite);
        System.out.println(thread + " - Prime Count: "+ pCount);
        System.out.println(thread + " - Time : " + getSTime() + " sec ");
        System.out.println("----------------------------");
    }
    
    public int getPCount(){ return pCount; }
    public long getNTime(){ return nTime; }
    public double getSTime(){ return nTime/1000000000.0;}
    
}

