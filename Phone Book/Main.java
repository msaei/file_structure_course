import java.io.EOFException;
import java.io.IOException;
import java.security.Key;
import java.util.*;
import java.io.RandomAccessFile;
public class Main {

    public static Long getNextLocation(String KeyWord, Long newNext)
    {
        // find last block in data stack
        Block block = new Block();
        Long prev, cur;
        try {
            RandomAccessFile file = new RandomAccessFile( "phonebook.dat", "rw" );
            file.seek(0L); // to find the next data pointer
            long dp = file.readLong();
            cur = dp;
            prev = -1L;
            while (cur != -1) {
                file.seek(cur);
                block.read(file);
                String curKey = block.getFriend().getLastName()+block.getFriend().getFirstName();
                //System.out.println("current key: " + curKey);
                if (KeyWord.compareTo(curKey) <= 0) {
                    break;
                }
                prev = cur;
                cur = block.getNext();
            } // while loop
            // update the previus block next attr
            if (prev == -1L) {
                file.seek(0L);
                file.writeLong(newNext);
            } else {
                file.seek(prev);
                block.read(file);
                block.setNext(newNext);
                block.writeBack(file);
            }
            return cur;
        } // try
        catch (EOFException eof){}
        catch (IOException ioe) { }
        return -1L;
    }

    public static void showAll()
    {
        // code to show all contacts
        Block block = new Block();
        Long next;
        int counter = 0;
        try {
            RandomAccessFile file = new RandomAccessFile( "phonebook.dat", "rw" );
            file.seek(0L); // to find the next data pointer
            next = file.readLong();
            while (next != -1) {
                file.seek(next);
                //long loc = file.getFilePointer();
                System.out.println("_______________________________________________________");
                block.read(file);
                System.out.println(block.getFriend());
                next = block.getNext();
                counter += 1;
            } // while loop
            System.out.println("_______________________________________________________");
            System.out.println(String.format("Total Contacts: %d ", counter));
        } // try
        catch (EOFException eof){}
        catch (IOException ioe) { }
    }

    public static void addContact()
    {
        // code to add  a new contact
        Friend friend = new Friend();
        Block block = new Block();
        Scanner sin = new Scanner(System.in);

        try {
            RandomAccessFile file = new RandomAccessFile("phonebook.dat", "rw");
            file.seek(8L); // to find the next free space pointer
            Long free = file.readLong();
            int num;
            if (free == -1){
                System.out.println("Can not add another contact. Memory is full!");
                return;
            } else {
                // get the contact info
                System.out.print("First Name:");
                friend.setFirstName(sin.next());
                System.out.print("Last Name:");
                friend.setLastName(sin.next());
                System.out.print("Phone Number:");
                friend.setPhoneNumber(sin.next());
                // find the proper location in data stack
                String key = friend.getLastName() + friend.getFirstName();
                //System.out.println("key: " + key);
                Long nextLoc = getNextLocation(key, free);
                // set block next and prev
                file.seek(free);
                block.read(file); // read block
                long fp = block.getNext();
                block.setFriend(friend);
                block.setNext(nextLoc);
                block.writeBack(file);
                // update FP
                file.seek(8L);
                file.writeLong(fp);

                System.out.println("the new contact added successfully!");
            }

        } // try
        catch (IOException e) {
            System.err.println("File not opened properly\n" + e.toString());
            System.exit(1);
        }
    }

    public static void searchContact()
    {
        // code to search for a contact
        Scanner sin = new Scanner(System.in);
        System.out.print("Enter the search term: ");
        String sterm = sin.next();
        Friend f = new Friend();
        f.setLastName(sterm);
        sterm = f.getLastName();

        Block block = new Block();
        Long next;
        try {
            RandomAccessFile file = new RandomAccessFile( "phonebook.dat", "rw" );
            file.seek(0L); // to find the next data pointer
            next = file.readLong();
            int counter = 0;
            while (next != -1) {
                file.seek(next);
                block.read(file);
                Friend friend = block.getFriend();
                if (sterm.compareTo(friend.getLastName())==0 || sterm.compareTo(friend.getFirstName()) == 0)
                {
                    System.out.println(friend);
                    counter++;
                }
                next = block.getNext();
            } // while loop
            System.out.println(String.format("%d contacts found for %s", counter, sterm));
        } // try
        catch (EOFException eof){}
        catch (IOException ioe) { }
    }

    public static void deleteContact()
    {
        // code to delete a nontact
        Scanner sin = new Scanner(System.in);
        System.out.print("Enter contact you want to delete: ");
        String sterm = sin.next();
        Friend f = new Friend();
        f.setLastName(sterm);
        sterm = f.getLastName();

        Block block = new Block();
        Long next;
        try {
            RandomAccessFile file = new RandomAccessFile( "phonebook.dat", "rw" );
            file.seek(0L); // to find the next data pointer
            next = file.readLong();
            Long prev = -1L;
            while (next != -1) {
                file.seek(next);
                block.read(file);
                Long newNext = block.getNext();
                Friend friend = block.getFriend();
                if (sterm.compareTo(friend.getLastName())==0 || sterm.compareTo(friend.getFirstName()) == 0)
                {
                    System.out.println(friend);
                    System.out.print("Do you want to delete this contact?(y/n)");
                    if (sin.next().charAt(0) == 'y'){
                        //update previus block next attr
                        if (prev == -1L) {
                            file.seek(0L);
                            file.writeLong(newNext);
                        } else {
                            file.seek(prev);
                            block.read(file);
                            block.setNext(newNext);
                            block.writeBack(file);
                        }
                        //yield current block to free apace
                        file.seek(8l);
                        Long nextFree = file.readLong();
                        file.seek(8L);
                        file.writeLong(next);
                        file.seek(next);
                        block.read(file);
                        block.setNext(nextFree);
                        block.writeBack(file);
                        System.out.println("The contact deleted successfully!");
                        return;
                    }
                }
                prev = next;
                next = newNext;
            } // while loop
            System.out.println("No such contact fount!");
        } // try
        catch (EOFException eof){}
        catch (IOException ioe) { }
    }
    public static void main(String[] args) throws IOException
    {
        Scanner sin = new Scanner(System.in);
        char opt;
        while (true) {
            System.out.println("___________________________________");
            System.out.println("Enter 'a' to show all the contacts:");
            System.out.println("Enter 'n' to create a new contact:");
            System.out.println("Enter 's' to search for a contact:");
            System.out.println("Enter 'd' to delete a contact:");
            System.out.println("Enter 'q' for Quite:");
            opt =sin.next().charAt(0);
            if (opt == 'a') showAll();
            if (opt=='n') addContact();
            if (opt=='s') searchContact();
            if (opt=='d') deleteContact();
            if (opt  == 'f') DriverCreate.main(null);
            if (opt == 'r') DriverRead.main(null);
            if (opt=='q') break;

        }
    }
}

