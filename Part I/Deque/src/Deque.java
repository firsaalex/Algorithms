import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Created with IntelliJ IDEA.
 * User: eprib
 * Date: 06.09.13
 * Time: 13:15
 * This class implements Double-Ended Queue data structure
 */
public class Deque<Item> implements Iterable<Item> {

    private int  N;          //  Deque size
    private Node first;      //  First element of deque
    private Node last;       //  Last element of deque


    // Inner class for linked list
    private class Node<Item> {
        private Item item;
        private Node next;
        private Node prev;
    }

    // Construct an empty deque
    public Deque() {
        N = 0;
        first = null;
        last = null;
    }


    //Is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }

    //Return the number of items on the deque
    public int size() {
        return N;
    }

    // Insert the item at the front
    public void addFirst(Item item) {
        if(item == null)throw new NullPointerException(" Can't add NULL element in the deque");

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;

        N++;

        if(N == 1) last = first;

        if (N > 1) oldFirst.prev = first;

    }


    // Insert the item at the end
    public void addLast(Item item) {
        if(item == null)throw new NullPointerException("Can't add NULL element in the deque");

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;

        N++;

        if(N == 1) first = last;
        if(N > 1)oldLast.next = last;

    }


    // Delete and return the item at the front
    public Item removeFirst() {

        if(isEmpty())throw new NoSuchElementException(" Deck is already empty");

        Node oldFirst = first;
        first = oldFirst.next;
        if(N > 1)first.prev = null;
        if(N == 1) last = null;

        N--;

        return (Item) oldFirst.item;

    }

    // Delete and return the item at the end
    public Item removeLast() {

        if(isEmpty())throw new NoSuchElementException(" Deck is already empty");

        Node oldLast = last;
        last = oldLast.prev;
        if (N > 1) last.next = null;
        if (N == 1) first = null;
        N--;

        return (Item) oldLast.item;

    }



    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return (current != null);
        }

        public void remove() {

            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No next element");
            Item item = (Item) current.item;
            current = current.next;
            return item;
        }

    }


    /*
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }
    */

    public static void main (String args[]) throws IOException {

        Deque<String> deque = new Deque<String>();

        for (int i = 0; i < 1000000; i++) {
            deque.addFirst(String.format("%d",i));
        }


        System.out.println("[Size = " + deque.size() + "] " + deque.toString());

        for( int i = 0; i < 1000001; i++) {
            //System.in.read();
            deque.removeFirst();
            //System.out.println("[Size = " + deque.size() + "] " + deque.toString());
            }

    }
}
