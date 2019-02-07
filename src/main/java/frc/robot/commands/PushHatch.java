package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PushHatch extends Command {
  boolean state;
  public PushHatch() {
    requires(Robot.manipulator);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    //dont know if we would require the arm because you can do both at the same time
  }
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Start Push");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.manipulator.pushHatch(!Robot.manipulator.getPushState());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
      System.out.println("Push Finish");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
