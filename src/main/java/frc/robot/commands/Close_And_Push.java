package frc.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Close_And_Push extends CommandGroup {
  public Close_And_Push() {
    addSequential(new CloseOpenMani());
    addSequential(new PushHatch());
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    //dont know if we would require the arm because you can do both at the same time
  }
}