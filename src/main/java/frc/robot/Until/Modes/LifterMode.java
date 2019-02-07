package frc.robot.Until.Modes;

public enum LifterMode{
    Cargolvl1(1),
    Cargolvl2(2),
    Cargolvl3(3),
    Hatchlvl1(4),
    Hatchlvl2(5),
    Hatchlvl3(6),
    PickUp(7);
    private int value;
    LifterMode(int output){
        value=output;
    }
    public int getValue(){
        return value;
    }
}