package app;

import entity.food.Bread;
import entity.food.Egg;
import entity.machine.BreadMaker;
import entity.machine.EggMaker;
import entity.machine.SandwichPacker;
import resource.Logger;
import resource.Pool;

public class Manager {

    public static int N_SANDWICHES;
    public static volatile Pool<Bread> breadPool;
    public static volatile Pool<Egg> eggPool;
    public static final Logger LOGGER = new Logger("HongYaoAS2.txt");;

    public static void main(String[] args) {

        // App parameters
        N_SANDWICHES = Integer.parseInt(args[0]);
        final int BREAD_CAPACITY = Integer.parseInt(args[1]);
        final int EGG_CAPACITY = Integer.parseInt(args[2]);
        final int N_BREAD_MAKERS = Integer.parseInt(args[3]);
        final int N_EGG_MAKERS = Integer.parseInt(args[4]);
        final int N_SANDWICH_PACKERS = Integer.parseInt(args[5]);
        final int BREAD_RATE = Integer.parseInt(args[6]);
        final int EGG_RATE = Integer.parseInt(args[7]);
        final int PACKING_RATE = Integer.parseInt(args[8]);


        // Instantiate entities
        breadPool = new Pool<>(BREAD_CAPACITY);
        eggPool = new Pool<>(EGG_CAPACITY);

        // Create arrays to store threads for producers and consumers
        Thread[] breadMakers = new Thread[N_BREAD_MAKERS];
        Thread[] eggMakers = new Thread[N_EGG_MAKERS];
        Thread[] sandwichPackers = new Thread[N_SANDWICH_PACKERS];


        // Start threads for bread and egg makers
        for (int i = 0; i < N_EGG_MAKERS; i++) {
            eggMakers[i] = new Thread(new EggMaker(EGG_RATE).producer);
            eggMakers[i].start();
        }
        for (int i = 0; i < N_BREAD_MAKERS; i++) {
            breadMakers[i] = new Thread(new BreadMaker(BREAD_RATE).producer);
            breadMakers[i].start();
        }
        for (int i = 0; i < N_SANDWICH_PACKERS; i++) {
            sandwichPackers[i] = new Thread(new SandwichPacker(PACKING_RATE).consumer);
            sandwichPackers[i].start();
        }

        // Join threads
        for (int i = 0; i < N_EGG_MAKERS; i++) {
            try { eggMakers[i].join(); } catch (InterruptedException e) {}
        }
        for (int i = 0; i < N_BREAD_MAKERS; i++) {
            try { breadMakers[i].join(); } catch (InterruptedException e) {}
        }
        for (int i = 0; i < N_SANDWICH_PACKERS; i++) {
            try { sandwichPackers[i].join(); } catch (InterruptedException e) {}
        }
    }
}
