import java.io.*;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DriverRead {
    private static Block block;
    private static Friend friend;
    private static RandomAccessFile file;

    public static void main(String[] s) {
        block = new Block();

        try {
            file = new RandomAccessFile( "phonebook.dat", "rw" );
            file.seek(0L); // rewind the file
            System.out.println(String.format("Data Pointer: %-10d", file.readLong()));
            System.out.println(String.format("Free Pointer: %-10d", file.readLong()));
            while (true) {
                long loc = file.getFilePointer();
                System.out.println("_______________________________________________________");
                System.out.println(String.format("Location: %d", loc));
                block.read(file);
                System.out.println(block);
            } // while loop
        } // try
        catch (EOFException eof){}
        catch (IOException ioe) { }
    }
}


