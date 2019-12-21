import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class OddEvenDriver {
    public static ArrayList<Integer> numbers;
    public static ArrayList<OddEvenSorter> sorters;
    public static int tnum, size, range, length;

    private static void fillNumbers(int size, int range ){
        numbers = new ArrayList<Integer>(size);
        Random rand = new Random();
        for (int i = 0; i < size; i++) numbers.add(rand.nextInt(range));
    }

    private static void printNumbers(){
        for (int i = 0; i < numbers.size(); i++) {
            System.out.print(numbers.get(i) + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of threads: ");
        tnum = in.nextInt();
        System.out.println("Enter the size of int array: ");
        size = in.nextInt();
        //System.out.println("Enter the max limit of number: ");
        range = 100;
        length = size / tnum;
        System.out.println("length is " + length);

        fillNumbers(size, range);
        printNumbers();
        StatusKeeper.setParties(tnum);
        CyclicBarrier barrier = new CyclicBarrier(tnum);

        OddEvenSorter sorter;
        int findex, lindex;
        String name;
        for (int i = 0; i < tnum; i++) {
            findex = i * length;
            lindex = findex + length;
            name = i + "th";
            if (i == tnum - 1) lindex = size -1;
            if (i == 0) name = "first";
            sorter = new OddEvenSorter(findex, lindex, numbers, barrier, name );
            sorter.start();
        }


    }
}
