package entity.machine;

import app.Manager;
import entity.food.Bread;
import resource.Util;

public class BreadMaker extends Machine {

    private static int makerID = 0;
    private static volatile int totalProduced = 0;

    public BreadMaker(int breadRate) {
        super(breadRate);
        id = makerID++;
    }

    /**
     * Produce bread till number of bread needed is reached
     * Logs each entry of bread production
     */
    public void produceBread() {
        while (totalProduced++ < Manager.N_SANDWICHES * 2) {

            // Simulate action (wait time)
            Util.goWork(machineRate);

            // Bread finished toasting
            Bread bread = new Bread(foodID++, this);

            // Add bread to bread pool
            try {
                Manager.breadPool.push(bread);
            } catch (InterruptedException e) {
            }

            // Log entry
            Manager.LOGGER.log(String.format("B%d puts bread %d", id, bread.id));
        }
    }

    @Override
    public void runThread() {
        produceBread();
    }
}
