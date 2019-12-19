import java.util.Scanner;

public class ProgramTwo {

    static PrimeWork work;
    static Range r;

    public static void main(String[] args) throws InterruptedException {
        int TotalPCount=0;
        int n = 0;
        int range = 0;
        int bitesize = 0;
        double TotalThreadTime=0;
        double speedup=0;

        System.out.println("Enter the range: ");
        Scanner ran = new Scanner(System.in);
        range=ran.nextInt();

        System.out.println("Enter the Bite Size: ");
        Scanner Bite = new Scanner(System.in);
        bitesize=Bite.nextInt();
        work = new PrimeWork(range, bitesize);

        System.out.println("Enter the number of thread: ");
        Scanner in = new Scanner(System.in);
        n=in.nextInt();
        PrimeThreadBite a[]= new PrimeThreadBite[n];

        for(int i=0;i<n;i++) {
            a[i]=new PrimeThreadBite(work, "Thread "+(i+1));
        }

        long start = System.nanoTime();
        for(int i=0;i<n;i++) {
            a[i].start();
        }
        for(int i=0;i<n;i++) {
            a[i].join();
        }
        System.out.println();
        System.out.println();
        for(int i=0;i<n;i++) {
            System.out.println(a[i]);
        }

        long end = System.nanoTime();
        double nTime = (end - start)/1000000000.0;
        for(int i=0;i<n;i++) {
            TotalPCount+=a[i].getPCount();
            TotalThreadTime+=a[i].getSTime();
        }
        System.out.println();
        System.out.println();
        System.out.println("Total Prime: "+TotalPCount);
        System.out.println("Total Thread Time: "+TotalThreadTime+ " sec");
        System.out.println("Run Time: "+nTime+ " sec");
        speedup=TotalThreadTime/nTime;
        System.out.println("Speed up: "+speedup);
        System.out.println("Available processors: "+ Runtime.getRuntime().availableProcessors());

    }
}