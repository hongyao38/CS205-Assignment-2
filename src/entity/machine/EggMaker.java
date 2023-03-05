package entity.machine;

import app.Manager;
import entity.food.Egg;
import resource.Util;

public class EggMaker {
    
    public static int makerID = 0;
    private volatile static int totalProduced = 0;
    private static final int EGGS_TO_PRODUCE = Manager.N_SANDWICHES;

    public int id;
    public Runnable producer;
    private int eggID;
    private int eggRate;


    public EggMaker(int eggRate) {
        this.eggRate = eggRate;
        id = makerID++;

        // Implement a runnable for the production of egg
        producer = new Runnable() {
            @Override
            public void run() { produceEgg(); }
        };
    }


    public synchronized void produceEgg() {
        while (totalProduced < EGGS_TO_PRODUCE) {

            // Simulate action (wait time)
            Util.goWork(eggRate);

            // Egg has been cooked
            Egg egg = new Egg(eggID++, this);
            
            // Wait for empty slot in egg pool
            while (Manager.eggPool.isFull()) {
                try { this.wait(); } catch (InterruptedException e) {}
            }

            // Put egg into egg pool
            Manager.eggPool.push(egg);

            // Log entry
            Manager.LOGGER.write(String.format("E%d puts egg %d", id, egg.id));
            totalProduced++;
            this.notifyAll();
        }
    }
}