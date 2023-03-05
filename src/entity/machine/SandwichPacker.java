package entity.machine;

import app.Manager;
import entity.food.Bread;
import entity.food.Egg;
import resource.Util;

public class SandwichPacker {
    
    private static int packerID = 0;
    private volatile static int totalPacked = 0;
    private static final int SANDWICHES_TO_PACK = Manager.N_SANDWICHES;

    public int id;
    public Runnable consumer;
    private int packingRate;
    private int sandwichID;


    public SandwichPacker(int packingRate) {
        this.packingRate = packingRate;
        id = packerID++;

        consumer = new Runnable() {
            @Override
            public void run() { packSandwich(); }
        };
    }

    public synchronized void packSandwich() {
        while (totalPacked < SANDWICHES_TO_PACK) {
            
            // Simulate do work (wait time)
            Util.goWork(packingRate);

            // If no bread available, wait
            while (Manager.breadPool.isEmpty()) {
                try { this.wait(); } catch (InterruptedException e) {}
            }
            Bread firstSlice = Manager.breadPool.pop();

            // If no egg available, wait
            while (Manager.eggPool.isEmpty()) {
                try { this.wait(); } catch (InterruptedException e) {}
            }
            Egg egg = Manager.eggPool.pop();

            // If no bread available, wait
            while (Manager.breadPool.isEmpty()) {
                try { this.wait(); } catch (InterruptedException e) {}
            }
            Bread secondSlice = Manager.breadPool.pop();

            // Log entry
            Manager.LOGGER.write(String.format("S%d packs sandwich %d with bread %d from B%d and egg %d from E%d and bread %d from B%d",
                                        id, sandwichID++, firstSlice.id, firstSlice.maker.id, egg.id, 
                                        egg.maker.id, secondSlice.id, secondSlice.maker.id));
            totalPacked++;
            this.notifyAll();
        }
    }

}
