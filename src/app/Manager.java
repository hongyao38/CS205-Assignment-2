package app;

import entity.food.Bread;
import entity.food.Egg;
import entity.machine.BreadMaker;
import entity.machine.EggMaker;
import entity.machine.SandwichPacker;
import resource.Logger;
import resource.Pool;

public class Manager {

    public static volatile int totalPacked = 0;
    public static int N_SANDWICHES;
    public static Pool<Bread> breadPool;
    public static Pool<Egg> eggPool;
    public static final Logger LOGGER = new Logger("HongYaoAS2.txt");

    public static void main(String[] args) {

        // Insufficient arguments given
        if (args.length < 9) {
            LOGGER.write("Insufficient arguments given!");
            return;
        }

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

        // Instantiate resource pools
        breadPool = new Pool<>(BREAD_CAPACITY);
        eggPool = new Pool<>(EGG_CAPACITY);

        // Create arrays to store threads for producers and consumers
        BreadMaker[] breadMakers = new BreadMaker[N_BREAD_MAKERS];
        EggMaker[] eggMakers = new EggMaker[N_EGG_MAKERS];
        SandwichPacker[] sandwichPackers = new SandwichPacker[N_SANDWICH_PACKERS];

        Thread[] breadMakerThreads = new Thread[N_BREAD_MAKERS];
        Thread[] eggMakerThreads = new Thread[N_EGG_MAKERS];
        Thread[] sandwichPackerThreads = new Thread[N_SANDWICH_PACKERS];

        // Log header
        logHeader(args);

        // Create threads for bread and egg makers
        for (int i = 0; i < N_BREAD_MAKERS; i++) {
            breadMakers[i] = new BreadMaker(BREAD_RATE);
            breadMakerThreads[i] = new Thread(breadMakers[i].producer);
        }
        for (int i = 0; i < N_EGG_MAKERS; i++) {
            eggMakers[i] = new EggMaker(EGG_RATE);
            eggMakerThreads[i] = new Thread(eggMakers[i].producer);
        }
        for (int i = 0; i < N_SANDWICH_PACKERS; i++) {
            sandwichPackers[i] = new SandwichPacker(PACKING_RATE);
            sandwichPackerThreads[i] = new Thread(sandwichPackers[i].consumer);
        }

        // Start thread
        for (int i = 0; i < N_BREAD_MAKERS; i++)
            breadMakerThreads[i].start();
        for (int i = 0; i < N_EGG_MAKERS; i++)
            eggMakerThreads[i].start();
        for (int i = 0; i < N_SANDWICH_PACKERS; i++)
            sandwichPackerThreads[i].start();

        // Join threads
        for (int i = 0; i < N_BREAD_MAKERS; i++)
            try { breadMakerThreads[i].join(); } catch (InterruptedException e) {}
        for (int i = 0; i < N_EGG_MAKERS; i++)
            try { eggMakerThreads[i].join(); } catch (InterruptedException e) {}
        for (int i = 0; i < N_SANDWICH_PACKERS; i++)
            try { sandwichPackerThreads[i].join(); } catch (InterruptedException e) {}


        // Wait for packing to be done
        while (totalPacked < N_SANDWICHES);

        // Log summary
        LOGGER.write("\nsummary:");
        for (int i = 0; i < N_BREAD_MAKERS; i++) {
            BreadMaker b = breadMakers[i];
            LOGGER.write(String.format("B%d makes %d", b.id, b.getTotalProduced()));
        }

        for (int i = 0; i < N_EGG_MAKERS; i++) {
            EggMaker e = eggMakers[i];
            LOGGER.write(String.format("E%d makes %d", e.id, e.getTotalProduced()));
        }

        for (int i = 0; i < N_SANDWICH_PACKERS; i++) {
            SandwichPacker s = sandwichPackers[i];
            LOGGER.write(String.format("S%d packs %d", s.id, s.getTotalPacked()));
        }
    }

    
    private static void logHeader(String[] args) {
        LOGGER.write("sandwiches: " + args[0]);
        LOGGER.write("bread capacity: " + args[1]);
        LOGGER.write("egg capacity: " + args[2]);
        LOGGER.write("bread makers: " + args[3]);
        LOGGER.write("egg makers: " + args[4]);
        LOGGER.write("sandwich packers: " + args[5]);
        LOGGER.write("bread rate: " + args[6]);
        LOGGER.write("egg rate: " + args[7]);
        LOGGER.write("packing rate: " + args[8]);
        LOGGER.write("");
    }
}
