/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class DriveBase extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static WPI_TalonSRX frontRight, frontLeft;
  public static WPI_VictorSPX backRight, backLeft, midRight, midLeft;
  private double leftOutput, rightOutput;
  private Solenoid shifterSolenoid;
  private Solenoid dbLifter;
  private boolean solenoidPos;
  private int direction;
  private double goal;
  private DifferentialDrive m_Drive;
  public static final SpeedControllerGroup m_Right = new SpeedControllerGroup(frontRight, midRight, backRight);
  public static final SpeedControllerGroup m_Left = new SpeedControllerGroup(frontLeft, midLeft, backLeft);

   public DriveBase(){
    solenoidPos=false;
    frontRight = new WPI_TalonSRX(RobotMap.dbFrontRight);
    midRight = new WPI_VictorSPX(RobotMap.dbMidRight);
    backRight = new WPI_VictorSPX(RobotMap.dbBackRight);
      midRight.follow(frontRight);
      backRight.follow(frontRight);
    
    frontLeft = new WPI_TalonSRX(RobotMap.dbFrontLeft);
    midLeft = new WPI_VictorSPX(RobotMap.dbMidLeft);
    backLeft = new WPI_VictorSPX(RobotMap.dbBackLeft);
      midLeft.follow(frontLeft);
      backLeft.follow(frontLeft);
    

    m_Drive = new DifferentialDrive(m_Left, m_Right);

  }

  public void arcadeDrive(double forward, double rotate){
    m_Drive.arcadeDrive(forward*direction, rotate);
  }

  public void switchDirection(){
    direction = direction*-1;
  }

  public void driveDistance(double g){
    //frontLeft.set(ControlMode.MotionMagic, g);
    //frontRight.set(ControlMode.MotionMagic, g);
  }

  public void liftDB(boolean state){
    solenoidPos = state;
  }
  public boolean getLiftDB(){
    return solenoidPos;
  }
  public void shiftGears(){
    
  }

  public void periodic(){
    dbLifter.set(solenoidPos);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
