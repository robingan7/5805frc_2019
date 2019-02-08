/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import java.util.concurrent.TimeUnit;		// Delay
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Until.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import frc.robot.Until.Modes.LifterMode;

public class Lift extends Subsystem {
  // create public doubles that are finals that you can use as arguments in IO
  // we are using 2 775 motors 1:625 gearbox for the arm
  // we are using 1 talonsrx motorcontroller for 
  private double encoderPos;
  private WPI_TalonSRX liftMotor;
  private WPI_VictorSPX liftMotor2;
  private Solenoid discBrake;
  private final int VERTEX=18000; //will change
  private double goal;
  
  public Lift(){
    
    
		liftMotor.configFactoryDefault();
		liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		/**
		 * Configure Talon SRX Output and Sesnor direction accordingly
		 * Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
		 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
		 */

		liftMotor.setSensorPhase(true);
		liftMotor.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		liftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		liftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
		/* Set the peak and nominal outputs */
		liftMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
		liftMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
		liftMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
		liftMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		/* Set Motion Magic gains in slot0 - see documentation */
		liftMotor.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		liftMotor.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		liftMotor.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		liftMotor.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
		liftMotor.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);
		/* Set acceleration and vcruise velocity - see documentation */
		liftMotor.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
		liftMotor.configMotionAcceleration(6000, Constants.kTimeoutMs);
		/* Zero the sensor */
    liftMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    
    liftMotor2.follow(liftMotor);
  }
  public void arcadeDrive(double forward, double rotate){
    double rotate2 = rotate;
    double forward2 = forward;
    
  }
  public void moveTo(double input){
    goal=input;
  }
  public void moveTo(final LifterMode mode){
    goal=mode.getValue();
  }
  public void moveToPercent(double input){
    liftMotor.set(ControlMode.PercentOutput,input);
  }
  public void engageBrake(){
    discBrake.set(true);    
    //make sure that no power is going to the arm currently
    // is based on the error of the PID loop

  }

  public void releaseBrake(){
    discBrake.set(false);
    //is dependent upon the position of the PID loop error
    //
    
  }
  public double getEncoderPos(){
    return 0.0;// for now because it got angry
  }

  public boolean isForward(){

    if(getEncoderPos()>VERTEX){
      return false;
    }else {
      return true;
    }
  }

  public void periodic(){
    liftMotor.set(ControlMode.MotionMagic, goal);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
