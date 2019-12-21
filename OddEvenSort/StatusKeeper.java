import java.util.ArrayList;

public class StatusKeeper {
   private static boolean allSorted = false;
   private static boolean eachSorted = true;
   private static int parties= 1;
   private static int reported = 0;

   public static void setParties(int pnum) {
       parties = pnum;
   }
   public synchronized static void report(boolean sorted){
       eachSorted =  eachSorted & sorted;
       reported ++;
       if (reported == parties){
           allSorted = eachSorted;
           eachSorted = true;
           reported = 0;
       }
   }
   public static boolean isAllSorted(){
       return allSorted;
   }
}
