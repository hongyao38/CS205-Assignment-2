package resource;

import java.util.concurrent.TimeUnit;

public class Util {

    public static void goWork(int n) {
        for (int i = 0; i < n; i++) {
            try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) {}
        }
    }
}
