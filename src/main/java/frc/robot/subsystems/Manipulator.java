/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Manipulator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private boolean position;
  private WPI_TalonSRX tiltMotor;
  private double encoderPos=24;  //will change
  private Solenoid extender, pusher;
  private DoubleSolenoid grabber;
  private boolean extenderB, grabberB, pusherB,wristB;
  private double goal;
  public static final double pickup_to_put=1000;//pickup to put
  public static final double put_to_pickup=-1000;//put to pickup

  public Manipulator(){
    tiltMotor = new WPI_TalonSRX(RobotMap.tiltMotor);  
    extender = new Solenoid(RobotMap.extender);
    grabber = new DoubleSolenoid(RobotMap.grabberF, RobotMap.grabberR);
    pusher = new Solenoid(RobotMap.pusher);
    pusherB=false;
    extenderB=false;
		tiltMotor.configFactoryDefault();
		tiltMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		tiltMotor.setSensorPhase(true);
    tiltMotor.setInverted(false);
    tiltMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		tiltMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
		tiltMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
		tiltMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
		tiltMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
		tiltMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		tiltMotor.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		tiltMotor.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		tiltMotor.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		tiltMotor.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
		tiltMotor.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);
		tiltMotor.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
		tiltMotor.configMotionAcceleration(6000, Constants.kTimeoutMs);
    tiltMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

  }
  //moving rotating the manipulator 180 degrees
  public void changePosition(boolean input){
        
  }
  //rotating the manipulator for x degrees
  public void tiltWrist(double g){
    goal=g;
  }

  public void tiltWristPercent(double g){
    tiltMotor.set(ControlMode.PercentOutput, g);
  }
  
  public void tiltWrist(boolean state){
    if(state){
      goal=pickup_to_put;
    }
    else{
      goal=put_to_pickup;
    }
  }
  public double getEncoderPos(){
    return encoderPos;

  }
  
  public boolean isExtended(){
    return position;
  }
  
  public boolean getGrabState(){
    return grabberB;
  }

  public boolean getPushState(){
    return pusherB;
  }
  public void grabHatch(boolean state){
    grabberB=state;
  }
  
  public void pushHatch(boolean state){
    pusherB=state;
  }
  public boolean getWristState(){
    return wristB;
  }
  public void extendManipulator(boolean state){
    extenderB=state;
  }
  public void periodic(){
    tiltMotor.set(ControlMode.MotionMagic, goal);
    extender.set(extenderB);
    pusher.set(pusherB);
    
    if(grabberB){
      grabber.set(Value.kForward);
    }else{
      grabber.set(Value.kReverse);
    }
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
