package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

public class Close_And_Push extends Command {
  boolean state;
  public Close_And_Push() {
    requires(Robot.manipulator);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    //dont know if we would require the arm because you can do both at the same time
  }
  public Close_And_Push(boolean state) {
    requires(Robot.manipulator);
    this.state=state;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    //dont know if we would require the arm because you can do both at the same time
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Start Finish");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Thread t1=new Thread(new Runnable(){
    
        @Override
        public void run() {
            Robot.manipulator.pushHatch(true);
        }
    });

    Thread t2=new Thread(new Runnable(){
    
        @Override
        public void run() {
            Robot.manipulator.grabHatch(true);
        }
    });

    t1.start();
    Timer.delay(0.5);
    t2.start();

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
      System.out.println("Extend Finish");
      Robot.manipulator.grabHatch(false);
    Robot.manipulator.pushHatch(false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
