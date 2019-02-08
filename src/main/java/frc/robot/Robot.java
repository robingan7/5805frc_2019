/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Manipulator;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.jaci.pathfinder.Pathfinder;
import frc.robot.jaci.pathfinder.PathfinderFRC;
import frc.robot.jaci.pathfinder.Trajectory;
import frc.robot.jaci.pathfinder.followers.EncoderFollower;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static OI m_oi;
  public static DriveBase drivebase = new DriveBase();
  public static Manipulator manipulator = new Manipulator();
  public static Lift lift = new Lift();
  public static Compressor compressor = new Compressor(0);
  public static Joystick joy =new Joystick(0);
  public static Joystick joy2 =new Joystick(1);
  //--------Motion Profile---------------
  private static final int k_ticks_per_rev = 1024;
  private static final double k_wheel_diameter = 4.0 / 12.0;
  private static final double k_max_velocity = 10;

  private static final int k_left_channel = 0;
  private static final int k_right_channel = 1;

  private static final int k_left_encoder_port_a = 0;
  private static final int k_left_encoder_port_b = 1;
  private static final int k_right_encoder_port_a = 2;
  private static final int k_right_encoder_port_b = 3;

  private static final int k_gyro_port = 0;

  private static final String k_path_name = "PutFirstCube";

  private SpeedController m_left_motor;
  private SpeedController m_right_motor;

  private Encoder m_left_encoder;
  private Encoder m_right_encoder;

  private AnalogGyro m_gyro;

  private EncoderFollower m_left_follower;
  private EncoderFollower m_right_follower;
  
  private Notifier m_follower_notifier;
  
//------------Motion Profile--------------
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    // m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    m_left_encoder = new Encoder(k_left_encoder_port_a, k_left_encoder_port_b);
    m_right_encoder = new Encoder(k_right_encoder_port_a, k_right_encoder_port_b);
    m_gyro = new AnalogGyro(k_gyro_port);
    SmartDashboard.putData("Auto mode", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();
    Trajectory left_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".left");
    Trajectory right_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".right");

    m_left_follower = new EncoderFollower(left_trajectory);
    m_right_follower = new EncoderFollower(right_trajectory);

    m_left_follower.configureEncoder(m_left_encoder.get(), k_ticks_per_rev, k_wheel_diameter);
    // You must tune the PID values on the following line!
    m_left_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / k_max_velocity, 0);

    m_right_follower.configureEncoder(m_right_encoder.get(), k_ticks_per_rev, k_wheel_diameter);
    // You must tune the PID values on the following line!
    m_right_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / k_max_velocity, 0);
    
    m_follower_notifier = new Notifier(this::followPath);
    m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }
  private void followPath() {
    if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
      m_follower_notifier.stop();
    } else {
      double left_speed = m_left_follower.calculate(m_left_encoder.get());
      double right_speed = m_right_follower.calculate(m_right_encoder.get());
      double heading = m_gyro.getAngle();
      double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      double turn =  0.8 * (-1.0/80.0) * heading_difference;
      Robot.drivebase.m_Right.set(left_speed + turn);
      Robot.drivebase.m_Left.set(right_speed - turn);
    }
  }
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    Robot.drivebase.arcadeDrive(Robot.joy.getRawAxis(RobotMap.forward_backward), Robot.joy.getRawAxis(RobotMap.turn));
    Robot.manipulator.tiltWristPercent(Robot.joy2.getRawAxis(RobotMap.mannualtilt));
    Robot.lift.moveToPercent(Robot.joy2.getRawAxis(RobotMap.mannuallift));
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    Robot.drivebase.arcadeDrive(Robot.joy.getRawAxis(RobotMap.forward_backward), Robot.joy.getRawAxis(RobotMap.turn));
  }
}
