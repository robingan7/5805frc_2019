/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;
    public static int dbFrontRight =11;
    public static int dbFrontLeft =12;
    public static int dbBackLeft =14;
    public static int dbBackRight =13;
    public static int dbMidRight =15;
    public static int dbMidLeft =16;
    public static int tiltMotor =17;
    
    // 10, 11, 13
    public static int extender = 2;
    public static int pusher = 3;
    public static int grabberF = 4;
    public static int grabberR = 5;
    //-----Joystick1------
    public static int forward_backward=1;
    public static int turn=4;
    public static int liftup_drivebase;
    public static int turn180;
    public static int centervision;
    public static int driveduringvision;
    //-------Joystick2--------
    public static int hatchlvl1=1;
    public static int hatchlvl2=2;
    public static int hatchlvl3=4;
    public static int turn_wrist=8;
    public static int gethatch=3;
    //public static int extend;
    public static int push=7;
    public static int grab=5;

    public static int mannualtilt=3;//axis
    public static int mannuallift=2;//axis
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
