package entity.machine;

import app.Manager;
import entity.food.Egg;
import resource.Util;

public class EggMaker extends Machine {

    private static int makerID = 0;
    private static volatile int totalProduced = 0;

    public EggMaker(int eggRate) {
        super(eggRate);
        id = makerID++;
    }

    /**
     * Produce egg till number of egg needed is reached
     * Logs each entry of egg production
     */
    public void produceEgg() {
        while (totalProduced++ < Manager.N_SANDWICHES) {

            // Simulate action (wait time)
            Util.goWork(machineRate);

            // Egg has been cooked
            Egg egg = new Egg(foodID++, this);

            // Put egg into egg pool
            try {
                Manager.eggPool.push(egg);
            } catch (InterruptedException e) {
            }

            // Log entry
            Manager.LOGGER.log(String.format("E%d puts egg %d", id, egg.id));
        }
    }

    @Override
    public void runThread() {
        produceEgg();
    }
}
