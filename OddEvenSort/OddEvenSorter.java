import javax.naming.PartialResultException;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class OddEvenSorter extends Thread {
    private int first, last;
    private ArrayList<Integer> numbers;
    private CyclicBarrier cBarier;
    public boolean isSorted;
    public String name;
    public OddEvenSorter(int firstIndex, int lastIndex, ArrayList<Integer> array, CyclicBarrier barier,  String name){
        this.first = firstIndex;
        this.last = lastIndex;
        this.numbers = array;
        this.cBarier = barier;
        this.name = name;
        this.isSorted = false;
    }
    private  void printNumbers(){
        for (int i = 0; i < numbers.size(); i++) {
            System.out.print(numbers.get(i) + ", ");
        }
        System.out.println();
    }

    public void run() {
        boolean sorted;
        int step = 0;
        while (!StatusKeeper.isAllSorted()){
//        for (int j = 0; j < numbers.size(); j++){
            sorted = true;
            int temp =0;

            // Perform Bubble sort on odd indexed element
            for (int i=first+1; i < last; i=i+2)
            {
                if (numbers.get(i) > numbers.get(i + 1))
                {
                    temp = numbers.get(i);
                    numbers.set(i, numbers.get(i + 1));
                    numbers.set(i + 1, temp);
                    sorted = false;
                }
            }

            try {
                cBarier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            //if (name == "first" ) printNumbers();

            // Perform Bubble sort on even indexed element
            for (int i=first; i < last; i=i+2)
            {
                if (numbers.get(i) > numbers.get(i + 1))
                {
                    temp = numbers.get(i);
                    numbers.set(i, numbers.get(i + 1));
                    numbers.set(i + 1, temp);
                    sorted = false;
                }
            }
            StatusKeeper.report(sorted);
            try {
                cBarier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            if (name == "first" ) {
                step++;
                System.out.print(step + ": ");
                printNumbers();
            }

        }

    }
}

