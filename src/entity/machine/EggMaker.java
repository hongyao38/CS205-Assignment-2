package entity.machine;

import app.Manager;
import entity.food.Egg;
import resource.Util;

public class EggMaker {
    
    private static int makerID = 0;
    private static volatile int totalProduced = 0;

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


    /**
     * Returns the number of eggs produced by the machine
     * 
     * @return number of egg produced
     */
    public int getTotalProduced() {
        return eggID;
    }


    /**
     * Produce egg till number of egg needed is reached
     * Logs each entry of egg production
     */
    public void produceEgg() {
        while (totalProduced++ < Manager.N_SANDWICHES) {

            // Simulate action (wait time)
            Util.goWork(eggRate);

            // Egg has been cooked
            Egg egg = new Egg(eggID++, this);

            // Put egg into egg pool
            try { Manager.eggPool.push(egg); } catch (InterruptedException e) {}

            // Log entry
            Manager.LOGGER.write(String.format("E%d puts egg %d", id, egg.id));
        }
    }
}
