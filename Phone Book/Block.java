import java.io.IOException;
import java.io.RandomAccessFile;

public class Block
{
    private Friend friend = new Friend();
    private Long next;
    private Long location;

    public void writeBack(RandomAccessFile file ) throws IOException
    {
        file.seek(location);
        friend.write(file);
        file.writeLong(next);
    }

    public void write(RandomAccessFile file) throws IOException
    {
        friend.write(file);
        file.writeLong(next);
    }

    public void read(RandomAccessFile file) throws IOException
    {
        location = file.getFilePointer();
        friend.read(file);
        next = file.readLong();
        //prev =  file.readLong();
    }
    public Friend getFriend() {return friend;}
    public Long getNext() { return next;}
    //public Long getPrev() { return prev; }

    public void setFriend(Friend f){ friend = f; }
    public void setNext(Long n){ next = n; }
    //public void setPrev(Long p){ prev = p; }

    public String toString()
    {
        return friend + String.format("\n Next: %-10d", next);
    }
    public static int size() { return 8 + Friend.size(); }
}

