package frc.robot.Until.Modes;

public enum ManipularMode{
    IntakeCargo(1),
    InTakeHatch(2),
    ReleaseCargo(3),
    ReleaseHatch(4);
    private int value;
    ManipularMode(int output){
        value=output;
    }
    public int getValue(){
        return value;
    }
}