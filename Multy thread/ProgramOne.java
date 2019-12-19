import java.io.*;
import java.util.Scanner;
public class ProgramOne {

    public static void main(String[] args){
        int TotalPCount=0;
        double TotalThreadTime=0;
        double speedup=0;
        Scanner in = new Scanner(System.in);

            System.out.print("Enter the lower range: ");
        int lrange = in.nextInt();
            System.out.print("Enter the higher range: ");
        int hrange = in.nextInt();
            System.out.print("Enter the number of threads: ");
        int tnum = in.nextInt();
//            start();
        int startNumber = lrange;
        int rangeLength = (hrange - lrange) / tnum;
        int endNumber;
        PrimeCounter[] pCounters = new PrimeCounter[tnum];
        long start = System.nanoTime();
        for (int i = 0; i < tnum; i++){
            endNumber = startNumber + rangeLength;
            if (i == tnum -1) endNumber = hrange;
            pCounters[i] = new PrimeCounter(startNumber, endNumber, "Thread " + i);
            pCounters[i].start();
            startNumber = endNumber;
        }
        for(int i = 0; i< tnum; i++){
            try {
                pCounters[i].join();
            } catch (Exception e){
                System.out.println(e);
            }

        }
        for(int i=0;i<tnum;i++) {
            System.out.println(pCounters[i]);
        }
        long end = System.nanoTime();
        double nTime = (end - start)/1000000000.0;
        for(int i=0;i<tnum;i++) {
            TotalPCount+=pCounters[i].getPCount();
            TotalThreadTime+=pCounters[i].getSTime();
        }
        System.out.println();
        System.out.println();
        System.out.println("Total Prime: "+TotalPCount);
        System.out.println("Total Thread Time: "+TotalThreadTime+ " sec");
        System.out.println("Run Time: "+nTime+ " sec");
        speedup=TotalThreadTime/nTime;
        System.out.println("Speed up: "+speedup);

    }

}
