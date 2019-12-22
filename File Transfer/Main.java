import javax.swing.*;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {
    public static int BUFFER_SIZE = 1024 * 1024;
    public static void main(String[] args){
        File selectedFile = new File("");
        JFileChooser fileChooser = new JFileChooser(".");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) selectedFile = fileChooser.getSelectedFile();
        int counter = 0;
        RandomAccessFile infile, outfile;
        RandomFileBuffer2 readBuffer, writeBuffer;
        try {
            infile = new RandomAccessFile(selectedFile, "rw");
            outfile = new RandomAccessFile("outfile", "rw");
            readBuffer = new RandomFileBuffer2(infile, BUFFER_SIZE, "rb");
            writeBuffer = new RandomFileBuffer2(outfile, BUFFER_SIZE, "wb");


            infile.seek(0L); // rewind the file
            long start = System.nanoTime();
            while (readBuffer.getLength() != -1) {
                //outfile.writeByte(infile.readByte());
                writeBuffer.append(readBuffer.read(1));
            } // while loop
            writeBuffer.flush();
            long end = System.nanoTime();
            double duration = (end - start)/1000000000.0;
            System.out.println(duration);
        } // try
        catch (EOFException eof){ }
        catch (IOException ioe) { }
    }
}
