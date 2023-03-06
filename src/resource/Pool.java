package resource;

@SuppressWarnings("unchecked")
public class Pool<T> {

    private final int CAPACTIY;
    private volatile T[] queue;
    private volatile int numItems;
    private volatile int head;
    private volatile int tail;

    public Pool(int capacity) {
        CAPACTIY = capacity;
        queue = (T[]) new Object[CAPACTIY];
    }

    private boolean isEmpty() {
        return numItems == 0;
    }

    private boolean isFull() {
        return numItems == CAPACTIY;
    }


    /**
     * Puts newly made item into resource pool if there is an empty slot,
     * else lets thread wait for empty slot
     * 
     * @param item can either be Bread or Egg
     * @throws InterruptedException
     */
    public synchronized void push(T item) throws InterruptedException {
        // Let thread wait if pool has no free slots
        while (isFull()) this.wait();

        // Enqueue item once a free slot is available and notify other threads
        queue[tail++ % CAPACTIY] = item;
        numItems++;
        this.notifyAll();
    }


    /**
     * Takes an item, Bread or Egg, out of a resource pool if there is something,
     * else lets thread wait for item to be put into pool
     * 
     * @return item, Bread of Egg
     * @throws InterruptedException
     */
    public synchronized T pop() throws InterruptedException {
        // Let thread wait if pool has no items in it
        while (isEmpty()) this.wait();

        // Dequeue the item at the head and notify other threads
        numItems--;
        T popped = queue[head];
        head = (head + 1) % CAPACTIY;
        this.notifyAll();

        return popped;
    }

}
