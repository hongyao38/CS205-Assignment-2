package entity.machine;

import app.Manager;
import entity.food.Bread;
import entity.food.Egg;
import resource.Util;

public class SandwichPacker {
    
    private static int packerID = 0;

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

    public int getTotalPacked() {
        return sandwichID;
    }

    public void packSandwich() {
        while (Manager.totalPacked < Manager.N_SANDWICHES) {
            Manager.totalPacked++;
            
            // Simulate do work (wait time)
            Util.goWork(packingRate);
            
            // Start packing
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
                
            } catch (InterruptedException e) {}

            // Log entry
            Manager.LOGGER.write(
                String.format("S%d packs sandwich %d with bread %d from B%d and egg %d from E%d and bread %d from B%d",
                    id, sandwichID++, firstSlice.id, firstSlice.maker.id, egg.id, 
                    egg.maker.id, secondSlice.id, secondSlice.maker.id));
        }
    }

}
