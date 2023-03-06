package resource;

import java.util.concurrent.TimeUnit;

public class Util {

    /**
     * Simulate work being done
     * 
     * @param n number of units of work
     */
    public static void goWork(int n) {
        for (int i = 0; i < n; i++) {
            // long m = 300000000;
            // while (m-- > 0);

            try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) {}
        }
    }
}
