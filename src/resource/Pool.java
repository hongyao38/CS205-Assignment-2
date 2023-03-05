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
        queue = (T[])new Object[CAPACTIY];
    }

    private boolean isEmpty() {
        return numItems == 0;
    }

    private boolean isFull() {
        return numItems == CAPACTIY;
    }

    public synchronized void push(T item) throws InterruptedException {
        while (isFull()) 
            this.wait();
        
        queue[tail++ % CAPACTIY] = item;
        numItems++;
        this.notifyAll();
    }

    public synchronized T pop() throws InterruptedException {
        while (isEmpty())
            this.wait();
        
        numItems--;
        T popped = queue[head];
        head = (head + 1) % CAPACTIY;
        this.notifyAll();
        return popped;
    }

}
