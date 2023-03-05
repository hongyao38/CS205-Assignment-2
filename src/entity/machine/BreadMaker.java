package entity.machine;

import app.Manager;
import entity.food.Bread;
import resource.Util;

public class BreadMaker {

    private static int makerID = 0;
    private volatile static int totalProduced = 0;
    private static final int BREADS_TO_PRODUCE = Manager.N_SANDWICHES * 2;

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

    public int getTotalProduced() {
        return breadID;
    }


    public void produceBread() {
        while (totalProduced < BREADS_TO_PRODUCE) {
            totalProduced++;

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
