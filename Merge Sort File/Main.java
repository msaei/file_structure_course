import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Main {
    public static int MAXINT = 100;
    public static int BUFFER_SIZE = 1024 * 1024;
    public static int FILE_SIZE = 64 * 1024 * 1024 ;
    public static RandomAccessFile sourceFile, leftFile, rightFile;
    public static RandomFileBuffer sourceBuffer, leftBuffer, rightBuffer;

    public static void generateIntFile(int size)
    {
        RandomAccessFile intFile;
        RandomFileBuffer writeBuffer;
        Random rand = new Random();
        int randomInt;
        try {
            intFile = new RandomAccessFile("intFile", "rw");
            intFile.setLength(0); // clear previous content of file
            writeBuffer = new RandomFileBuffer(intFile, BUFFER_SIZE, "wb");


            intFile.seek(0L); // rewind the file

            for (int i= 0; i < size; i++){
                randomInt = rand.nextInt(MAXINT);
                //System.out.println(randomInt);
                writeBuffer.append(randomInt);
            }
            writeBuffer.flush();
            intFile.close();

        } // try
        catch (EOFException eof){ }
        catch (IOException ioe) { }

    }

    public static void displayFileContent(RandomFileBuffer bfr)
    {
//        RandomAccessFile intFile;
//        RandomFileBuffer readBuffer;
        try {
//            intFile = new RandomAccessFile(fileName, "rw");
//            readBuffer = new RandomFileBuffer(intFile, BUFFER_SIZE, "rb");


//            intFile.seek(0L); // rewind the file
            bfr.reset();
            int currentRead = bfr.read();
            while(currentRead != -1){
                System.out.println(currentRead);
                currentRead = bfr.read();
            } // while loop
            //sourceFile.close();
        } // try
        catch (EOFException eof){ }
        catch (IOException ioe) { }

    }

    public static void displayFileContent()
    {
//        RandomAccessFile intFile;
//        RandomFileBuffer readBuffer;
        try {
//            intFile = new RandomAccessFile(fileName, "rw");
//            readBuffer = new RandomFileBuffer(intFile, BUFFER_SIZE, "rb");


//            intFile.seek(0L); // rewind the file
            sourceBuffer.reset();
            int currentRead = sourceBuffer.read();
            while(currentRead != -1){
                System.out.println(currentRead);
                currentRead = sourceBuffer.read();
            } // while loop
            sourceFile.close();
        } // try
        catch (EOFException eof){ }
        catch (IOException ioe) { }

    }

    public static void displayFileContent(String fileName, int count)
    {
        RandomAccessFile intFile;
        RandomFileBuffer readBuffer;
        try {
            intFile = new RandomAccessFile(fileName, "rw");
            readBuffer = new RandomFileBuffer(intFile, BUFFER_SIZE, "rb");

            Long size = intFile.length()/4;
            intFile.seek(0L); // rewind the file
            int counter = 0;
            int currentRead = readBuffer.read();
            while(currentRead != -1){
                //System.out.println(currentRead);
                if (counter < count || counter > (size - count)){
                    System.out.println(currentRead);
                }
                if (counter == count) {
                    System.out.println(".......");
                }
                currentRead = readBuffer.read();
                counter++;
            } // while loop
            intFile.close();
        } // try
        catch (EOFException eof){ }
        catch (IOException ioe) { }

    }

    public static void split(int length)
    {

        try {
            sourceBuffer.reset();
            leftBuffer.resetLength();
            rightBuffer.resetLength();

            int currentRead = sourceBuffer.read();
            boolean odd = true;
            int counter = length;
            while(currentRead != -1){

                if (odd) {
                    leftBuffer.append(currentRead);
                    counter--;
                } else {
                    rightBuffer.append(currentRead);
                    counter--;
                }

                currentRead = sourceBuffer.read();
                if (counter == 0){
                    counter = length;
                    odd = !odd;
                }

            } // while loop
            leftBuffer.flush();
            rightBuffer.flush();
        }
        catch (EOFException eof){ System.out.println("eof split");}
        catch (IOException ioe) { System.err.println("Error in fillBuffer()"); ioe.printStackTrace();}
    }

    public static void merge(int length){

        try {

            leftBuffer.reset();
            rightBuffer.reset();
            sourceBuffer.resetLength();

            int currentRead1 = leftBuffer.read();
            int currentRead2 = rightBuffer.read();
            int i1 = length;
            int i2 = length;
            while(currentRead1 > -1 || currentRead2 > -1){
                if (currentRead1 == -1 || i1 == 0){
                    sourceBuffer.append(currentRead2);
                    currentRead2 = rightBuffer.read();
                    i2--;
                } else if (currentRead2 == -1 || i2 == 0){
                    sourceBuffer.append(currentRead1);
                    currentRead1 = leftBuffer.read();
                    i1--;
                } else if (currentRead1 < currentRead2) {
                    sourceBuffer.append(currentRead1);
                    currentRead1 = leftBuffer.read();
                    i1--;
                } else {
                    sourceBuffer.append(currentRead2);
                    currentRead2 = rightBuffer.read();
                    i2--;
                }
                if (i1 == 0 && i2 == 0){
                    i1 = length;
                    i2 = length;
                }
            } // while loop
            sourceBuffer.flush();
            //sourceFile.close();
            //leftFile.close();
            //rightFile.close();

        }
        catch (EOFException eof){ }
        catch (IOException ioe) { }

    }

    public static boolean isSorted(String fileName)
    {
        RandomAccessFile intFile;
        RandomFileBuffer readBuffer;
        try {
            intFile = new RandomAccessFile(fileName, "rw");
            readBuffer = new RandomFileBuffer(intFile, BUFFER_SIZE, "rb");


            intFile.seek(0L); // rewind the file

            int currentRead = readBuffer.read();
            int previus = 0;
            while(currentRead != -1){
                //System.out.println(currentRead);
                if (currentRead < previus){
                    return false;
                }
                previus = currentRead;
                currentRead = readBuffer.read();
            } // while loop
            intFile.close();
            return true;
        } // try
        catch (EOFException eof){ return false; }
        catch (IOException ioe) { return false; }
    }

    public static void main(String args[]){
        // generate a file of random integer numbers
        generateIntFile(FILE_SIZE);
        try {
            sourceFile = new RandomAccessFile("intFile", "rw");
            sourceBuffer = new RandomFileBuffer(sourceFile, BUFFER_SIZE, "sb");

            leftFile = new RandomAccessFile("intFile1", "rw");
            leftBuffer = new RandomFileBuffer(leftFile, BUFFER_SIZE, "lb");

            rightFile = new RandomAccessFile("intFile2", "rw");
            rightBuffer = new RandomFileBuffer(rightFile, BUFFER_SIZE, "rb");
        }
        catch (EOFException eof){ System.out.println("eof error"); }
        catch (IOException ioe) { System.out.println("ioe error");}

        displayFileContent("intFile", 5);
//        System.out.println("___source_____");
//        displayFileContent(sourceBuffer);

        // merge sort the file


        long start = System.nanoTime();
        for (int i = 1; i <= FILE_SIZE; i*=2){
            //System.out.println(i);
            split(i);
//            System.out.println("___file1_____");
//            displayFileContent(leftBuffer);
//            System.out.println("___file2_____");
//            displayFileContent(rightBuffer);
            merge(i);
//            System.out.println("___source_____");
//            displayFileContent(sourceBuffer);
        }

        long end = System.nanoTime();
        double duration = (end - start)/1000000000.0;
        System.out.print("Time in seconds: ");
        System.out.println(duration);
        // check the correctness
        //displayFileContent("intFile");
        if (isSorted("intFile")) {
            System.out.println("Sorted Successfully !");
            displayFileContent("intFile", 5);
            //displayFileContent(sourceBuffer);
        } else {
            System.out.println("NOT Sorted correctly !!");
        }

    }
}
