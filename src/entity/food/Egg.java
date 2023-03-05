package entity.food;

import entity.machine.EggMaker;

public class Egg {

    public int id;
    public EggMaker maker;

    public Egg(int id, EggMaker maker) {
        this.id = id;
        this.maker = maker;
    }
}
