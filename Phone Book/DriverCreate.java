import java.io.IOException;
import java.io.RandomAccessFile;
public class DriverCreate {
    private static Friend friend;
    private static Block block;
    private static RandomAccessFile file;
    private static int COUNTS = 8;


    public static void main(String[] s) {
        friend = new Friend();
        block = new Block();
        try {
            file = new RandomAccessFile("phonebook.dat", "rw");
            file.writeLong(-1); // DP data pointer
            file.writeLong(16); //FP free location pointer
            //long prev = -1;
            long cur, next ;
            for (int i = 0; i < COUNTS; i++) {
                cur = file.getFilePointer();
                next = cur + Block.size();
                if (i == COUNTS -1) next = -1;
                friend.setPhoneNumber("(111) 222-3344");
                friend.setFirstName("JOHN");
                friend.setLastName("DOE");
                //System.out.println(friend); // echo to screen
                block.setFriend(friend);
                //block.setPrev(prev);
                block.setNext(next);
                block.write(file);
                //prev = cur;
            } // write to file
        } // try
        catch (IOException e) {
            System.err.println("File not opened properly\n" + e.toString());
            System.exit(1);
        }
    }
}
