package entity.food;

import entity.machine.BreadMaker;

public class Bread {

    public int id;
    public BreadMaker maker;

    public Bread(int id, BreadMaker maker) {
        this.id = id;
        this.maker = maker;
    }
}
