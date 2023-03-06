package entity.machine;

import app.Manager;
import entity.food.Bread;
import resource.Util;

public class BreadMaker {

    private static int makerID = 0;
    private static volatile int totalProduced = 0;

    public int id;
    public Runnable producer;
    private int breadID;
    private int breadRate;


    public BreadMaker(int breadRate) {
        this.breadRate = breadRate;
        id = makerID++;

        // Implement a runnable for the production of bread
        producer = new Runnable() {
            @Override
            public void run() { produceBread(); }
        };
    }

    /**
     * Returns the number of bread the machine produced
     * 
     * @return number of bread produced
     */
    public int getTotalProduced() {
        return breadID;
    }


    /**
     * Produce bread till number of bread needed is reached
     * Logs each entry of bread production
     */
    public void produceBread() {
        while (totalProduced++ < Manager.N_SANDWICHES * 2) {

            // Simulate action (wait time)
            Util.goWork(breadRate);

            // Bread finished toasting
            Bread bread = new Bread(breadID++, this);

            // Add bread to bread pool
            try { Manager.breadPool.push(bread); } catch (InterruptedException e) {}

            // Log entry
            Manager.LOGGER.write(String.format("B%d puts bread %d", id, bread.id));
        }
    }
}
