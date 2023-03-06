package entity.machine;

import app.Manager;
import entity.food.Bread;
import entity.food.Egg;
import resource.Util;

public class SandwichPacker extends Machine {

    private static int packerID = 0;

    public SandwichPacker(int packingRate) {
        super(packingRate);
        id = packerID++;
    }

    /**
     * Takes 2 bread and 1 egg from the shared resource pool to pack into 1 sandwich
     * 
     * If either bread or sandwich pool is empty, thread will be blocked
     */
    public void packSandwich() {
        while (Manager.totalPacked++ < Manager.N_SANDWICHES) {

            // Simulate do work (wait time)
            Util.goWork(machineRate);

            // Start packing 2 bread + 1 egg
            Bread firstSlice = null;
            Egg egg = null;
            Bread secondSlice = null;
            try {
                while (firstSlice == null)
                    firstSlice = Manager.breadPool.pop();

                while (egg == null)
                    egg = Manager.eggPool.pop();

                while (secondSlice == null)
                    secondSlice = Manager.breadPool.pop();

            } catch (InterruptedException e) {
            }

            // Log entry
            Manager.LOGGER.log(
                    String.format(
                            "S%d packs sandwich %d with bread %d from B%d and egg %d from E%d and bread %d from B%d",
                            id, foodID++, firstSlice.id, firstSlice.maker.id, egg.id,
                            egg.maker.id, secondSlice.id, secondSlice.maker.id));
        }
    }

    @Override
    public void runThread() {
        packSandwich();
    }
}
