import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    private int N;
    private Node first, last;
    
    private class Node {
        
        private Item item;
        private Node next;
    }
    
    // construct an empty deque    
    public Deque() {
        
        N = 0;
        first = null;
        last = null;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        
        return (first == null);
    }
    
    // return the number of items on the deque
    public int size() {
        return N;
    }
    

    
    // insert the item at the front
    public void addFirst(Item item) {
        
        Node oldFirst  = first;
        first = new Node();
        first.next = oldFirst;
        first.item = item;
        N++;
        
        if (N == 1) {
            last = first;
        }
    }
    
    // insert the item at the end
    public void addLast(Item item) {
        
        Node oldLast = last;
        last = new Node(); 
        
        if (N != 0) {
            oldLast.next = last;            
        }
        
        last.item = item;
        N++;
        
        if (N == 1) {
            first = last;
            last.next = null;
        }
        

    }
    
    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;
        first = first.next;
        N--;
        
        return item;
    }
    
    // delete and return the item at the end
    //public Item removeLast() {
        
    
    //}
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();        
    }
    
    private class ListIterator implements Iterator<Item> {
        
        private Node current = first;
        
        public boolean hasNext() { 
            return (current != null);      
        } 
        
        public void remove() { 
            
          throw new UnsupportedOperationException();  
        } 
        public Item next() {
            
            Item item = current.item;
            current = current.next;
            return item;
        }
        
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }
    
    public static void main(String[] args) {
        
        Deque<String> d = new Deque<String>();
        
        
        d.addFirst("1");
        StdOut.println(d.toString());
        d.addFirst("2");
        StdOut.println(d.toString());
        d.addFirst("3");
        StdOut.println(d.toString());
        d.addLast("4");
        StdOut.println(d.toString());
        d.addLast("5");
        StdOut.println(d.toString());

        
    }
}