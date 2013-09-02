import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    private int N;        // size of the deque
    private Node first;   // top of à he deque
    private Node last;    // bottom of à he deque
    
    //helper linked list class
    
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    
   // empty deque constructor 
    public Deque() {
        
        first = null;
        last = null;
        N = 0;
        assert check();
    }
    
   // is the deque empty? 
    public boolean isEmpty() {
        
        return first == null;
    }
   
    // return the number of items on the deque
    public int size() {
        return N;
    }
    
    // insert the item at the front 
    public void addFirst(Item item) {
        
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = first;
        first.prev = null;
        N++;
        assert check();
    }
    
   // insert the item at the end 
    public void addLast(Item item) {
        
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        oldLast.next = last;
        N++;
        assert check();
    }
    
    // delete and return the item at the front 
    public Item removeFirst() {
        
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        first = first.next;        
        first.prev = null;
        N--;
        assert check();
        
        return item;
    }
    
   // delete and return the item at the end
    public Item removeLast() {
        
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        last = last.prev;
        last.next = null;
        N--;
        assert check();
        
        return item;
    }
    
    private boolean check() {
        return true;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }
    
   // return an iterator over items in order from front to end 
    
   
   public Iterator<Item> iterator()  { return new ListIterator();  }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
       

       
       
    public static void main (String[] args) {
           
           Deque<String> s = new Deque<String>();
           
           s.addFirst("1");
           
          StdOut.println("===[" + s.toString() + "]===");
           
    }
       
       
}