/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Until.Modes.LifterMode;
import frc.robot.commands.*;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);
    public OI(){
      //----------Joystick--------------
      Button centervision=new JoystickButton(Robot.joy, RobotMap.centervision);
      centervision.whileHeld(new VisionCenter());

      Button driveduringvision=new JoystickButton(Robot.joy, RobotMap.driveduringvision);
      driveduringvision.whileHeld(new DriveDuringVision());

      Button liftup_drivebase=new JoystickButton(Robot.joy, RobotMap.liftup_drivebase);
      liftup_drivebase.whenPressed(new LiftDB());

      //----------Joystick2-------------
      Button grab = new JoystickButton(Robot.joy2, RobotMap.grab);
      grab.whileHeld(new CloseOpenMani());

      Button push =new JoystickButton(Robot.joy2, RobotMap.push);
      push.whenPressed(new PushHatch());

      Button turnwrist =new JoystickButton(Robot.joy2, RobotMap.turn_wrist);
      turnwrist.whenPressed(new MoveWrist());

      Button hatchlvl1 =new JoystickButton(Robot.joy2, RobotMap.hatchlvl1);
      hatchlvl1.whenPressed(new MoveArmTo(LifterMode.Hatchlvl1));

      Button hatchlvl2 =new JoystickButton(Robot.joy2, RobotMap.hatchlvl2);
      hatchlvl2.whenPressed(new MoveArmTo(LifterMode.Hatchlvl2));

      Button hatchlvl3 =new JoystickButton(Robot.joy2, RobotMap.hatchlvl3);
      hatchlvl3.whenPressed(new MoveArmTo(LifterMode.Hatchlvl3));

      Button pickup =new JoystickButton(Robot.joy2, RobotMap.gethatch);
      pickup.whenPressed(new MoveArmTo(LifterMode.PickUp));

      

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
}
