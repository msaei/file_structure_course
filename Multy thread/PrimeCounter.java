public class PrimeCounter extends Thread {
    private long startTime, endTime;
    private int lowerRange, higherRange;
    private String id;
    private int pcount = 0;
    PrimeCounter(int low, int high, String id){
        this.lowerRange = low;
        this.higherRange = high;
        this.id = id;
    }

    private boolean isPrime(int n) {
        if (n % 2 == 0) return false;
        int limit = (int)Math.ceil(Math.sqrt(n));
        for (int divisor = 3; divisor <= limit; divisor +=2)
            if (n % divisor == 0)
                return false;
        return true;
    }

    public void run(){
        startTime = System.nanoTime();

        for (int i = lowerRange; i < higherRange; i++){
            if (isPrime(i)) pcount++;
        }

        endTime = System.nanoTime();
    }

    public double getSTime(){ return (endTime - startTime) /1000000000.0;}
    public int getPCount() {return pcount;}

    public String toString(){
        return id +": PRIMECOUNT: " + this.pcount +  " TIMER: "+ getSTime() + " sec";
    }
}
