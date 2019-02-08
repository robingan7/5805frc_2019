/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Manipulator;
import frc.robot.commands.MoveWrist;
import frc.robot.Robot;
import frc.robot.Until.Modes.LifterMode;

public class MoveArmTo extends Command {
  private LifterMode goal;
  public MoveArmTo(LifterMode mode) {

    goal= mode;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.lift);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.lift.releaseBrake();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Thread t1=new Thread(new Runnable(){
    
      @Override
      public void run() {
        if(Robot.lift.isForward()==false){
            Robot.manipulator.tiltWrist(Manipulator.pickup_to_put);
        }
        else{
          Robot.manipulator.tiltWrist(Manipulator.put_to_pickup);
        }
      }
    });

    Thread t2=new Thread(new Runnable(){
    
      @Override
      public void run() {
        Robot.lift.moveTo(goal);
      }
    });
    t1.start();
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
     // engageDiscBrake on = new engageDiscBrake();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
