import java.io.IOException;
import java.io.RandomAccessFile;

public class Friend
{
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private static int LENGTH = 15;
    public void write(RandomAccessFile file ) throws IOException
    {

        StringBuffer buf;
        if( phoneNumber != null ) buf=new StringBuffer( phoneNumber );
        else buf = new StringBuffer(LENGTH);
        buf.setLength(LENGTH);
        file.writeChars( buf.toString() );
        if( firstName != null ) buf=new StringBuffer( firstName );
        else buf = new StringBuffer(LENGTH);
        buf.setLength(LENGTH);
        file.writeChars( buf.toString() );
        if ( lastName != null ) buf = new StringBuffer( lastName );
        else buf = new StringBuffer(LENGTH);
        buf.setLength(LENGTH);
        file.writeChars( buf.toString() );
    }

    public void read(RandomAccessFile file) throws IOException
    {
        char phone[] = new char[LENGTH];
        for ( int i = 0; i < phone.length; i++ )
            phone[ i ] = file.readChar();
        phoneNumber = new String( phone );
        char first[] = new char[LENGTH];
        for ( int i = 0; i < first.length; i++ )
            first[ i ] = file.readChar();
        firstName = new String( first );
        char last[] = new char[LENGTH];
        for ( int i = 0; i < last.length; i++ )
            last[ i ] = file.readChar();
        lastName = new String( last );
    }
    public String getFirstName() { return firstName;}
    public String getLastName() {return  lastName;}
    public String getPhoneNumber() {return phoneNumber;}
    public void setLastName(String lname){
        StringBuffer buf;
        if( lname != null ) buf=new StringBuffer( lname );
        else buf = new StringBuffer(LENGTH);
        buf.setLength(LENGTH);
        lastName = buf.toString();
    }
    public void setFirstName(String fname){
        StringBuffer buf;
        if( fname != null ) buf=new StringBuffer( fname );
        else buf = new StringBuffer(LENGTH);
        buf.setLength(LENGTH);
        firstName = buf.toString();
    }
    public void setPhoneNumber(String phone){ phoneNumber = phone; }

    public String toString()
    {
        return String.format("%-15s %-15s Phone: %-15s",
                firstName, lastName, phoneNumber);
    }
    public static int size() { return 90; }
}
