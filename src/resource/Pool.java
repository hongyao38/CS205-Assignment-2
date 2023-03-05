package resource;

@SuppressWarnings("unchecked")
public class Pool<T> {
    
    public T[] queue;
    private final int CAPACTIY;
    private volatile int numItems;
    private volatile int head;
    private volatile int tail;

    public Pool(int capacity) {
        CAPACTIY = capacity;
        queue = (T[])new Object[CAPACTIY];
        head = 0;
        tail = 0;
    }

    public boolean isEmpty() {
        return numItems == 0;
    }

    public boolean isFull() {
        return numItems == CAPACTIY;
    }

    public boolean push(T item) {
        if (isFull()) 
            return false;
        
        queue[tail++ % CAPACTIY] = item;
        numItems++;
        return true;
    }

    public T pop() {
        if (isEmpty())
            return null;
        
        numItems--;
        T popped = queue[head];
        head = (head + 1) % CAPACTIY;
        return popped;
    }

}
