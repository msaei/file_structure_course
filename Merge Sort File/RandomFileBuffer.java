import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.EOFException;
import java.nio.ByteBuffer;

public class RandomFileBuffer
{
    private RandomAccessFile file;
    private String name;
    private int BUFFER_SIZE;
    private int length;
    private byte [] buffer;
    private int currR;
    private int currW;
    public static  byte[] intToBytes(int myInteger){
        return ByteBuffer.allocate(4).putInt(myInteger).array();
    }

    public static int byteToInt(byte [] byteBarray){
        return ByteBuffer.wrap(byteBarray).getInt();
    }

    public RandomFileBuffer(RandomAccessFile file, int size, String name) throws IOException {
        this.file = file;
        reset();
        BUFFER_SIZE = size;
        buffer = new byte[BUFFER_SIZE*4];
        length = 0;
        currR = 0;
        currW = 0;
        this.name = name;
    }

    // Fill empty buffer from File
    public void fill()
    {
        int n = 0;
        try {
            n = file.read(buffer);
        }

        catch (EOFException eof) {   }
        catch (IOException ioe) { System.err.println("Error in fillBuffer()"); ioe.printStackTrace();  }

        length = n;
        currR = currW = 0;

    }


    // Writes to the File from buffer
    public void writeToFile() throws IOException
    {
        int count = 0;
        file.write(buffer, 0, currW);
        length = 0;
        currR = currW = 0;
    }


    public void append(int value) throws IOException
    {
        if (full())
        {
            writeToFile();
        }
        byte [] biteInt = intToBytes(value);
        for (int i = 0; i < 4; i++)
            buffer[currW++] = biteInt[i];
        length += 4;
    }


    public int read()
    {
        if (empty())
        {
            fill();
        }

        if (length == -1) return -1; //EOF
        byte [] byteInt = new byte[4];
        for (int i =0; i < 4; i++)
            byteInt[i] = buffer[currR++];
        int intValue = byteToInt(byteInt);

        return intValue;
    }


    public void clear() throws IOException
    {
        currR = currW = length = 0;
    }


    public void reset() throws IOException
    {
        file.seek(0L);
        length = 0;
        currR = 0;
        currW = 0;
    }
    public void resetLength() throws IOException {
        file.setLength(0L);
        length = 0;
        currR = 0;
        currW = 0;
    }


    public int getBufferSize()
    {
        return BUFFER_SIZE;
    }

    public int getLength()
    {
        return length;
    }

    public boolean empty()
    {
        return currR == length;
    }


    public boolean full()
    {
        return length >= BUFFER_SIZE*4;
    }

    private String getName()
    {
        return name;
    }

    public void flush() throws IOException
    {
        writeToFile();
        //file.close();
    }


}
