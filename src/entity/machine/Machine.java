package entity.machine;

public abstract class Machine {

    public int id;
    public Runnable threadRunnable;
    protected int foodID;
    protected int machineRate;

    public Machine(int machineRate) {
        this.machineRate = machineRate;

        // Create a Runnable instance for thread creation
        threadRunnable = new Runnable() {
            @Override
            public void run() { runThread(); }
        };
    }

    /**
     * Returns the number of food the machine produced/consumed
     * 
     * @return number of food produced/consumed
     */
    public int getTotalFoodOutput() {
        return foodID;
    }

    /**
     * This is a wrapper method to run either the
     * production or consumption of bread and eggs
     */
    public abstract void runThread();
}
