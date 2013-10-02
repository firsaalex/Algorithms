import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] items;                                  // Array of items
    private int size;                                      // Number of elements in the structure

    public RandomizedQueue()                               // Construct an empty randomized queue
    {
        items = (Item[]) new Object[1];
        size  = 0;
    }

    public boolean isEmpty() { return size == 0; }         // Is the queue empty?

    public int size() { return size; }                     // Return the number of items on the queue

    public void enqueue(Item item)                         // Add the item
    {
        if (item == null)
            throw new java.lang.NullPointerException();

        if (size == items.length) resize(2*items.length);

        items[size++] = item;
    }

    private void resize(int capacity)                      // Resize the array to the capacity specified
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = items[i];
        items = copy;
    }

    public Item dequeue()                                  // Delete and return a random item
    {
        int position;
        Item item;
        if (size == 0)
            throw new java.util.NoSuchElementException();
        position        = StdRandom.uniform(size);
        item            = items[position];
        items[position] = items[--size];
        items[size]     = null;

        if (size > 0 && size == items.length / 4) resize(items.length / 2);

        return item;
    }

    public Item sample()                                   // Return (but do not delete) a random item
    {
        int position;
        if (size == 0)
            throw new java.util.NoSuchElementException();
        position = StdRandom.uniform(size);
        return  items[position];
    }

    public Iterator<Item> iterator()                       // Return an independent iterator over items in random order
    { return new RandomizeQueueIterator(); }

    private class RandomizeQueueIterator implements Iterator<Item>
    {
        private int i = size;
        private Item[] shuffleItems;

        public RandomizeQueueIterator()
        {
            shuffleItems = (Item[]) new Object[size];
            System.arraycopy(items, 0, shuffleItems, 0, size);
            StdRandom.shuffle(shuffleItems, 0, size-1);
        }

        public boolean hasNext() { return i > 0; }

        public void remove()     { throw new java.lang.UnsupportedOperationException(); }

        public Item next()
        {
            if (i == 0)
                throw new java.util.NoSuchElementException();
            return shuffleItems[--i];
        }
    }
}