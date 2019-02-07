package frc.robot.commands;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
//import frc.robot.Robot;
/**
 * An example command.  You can replace me with your own command.
 */
public class DriveDuringVision extends Command {
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  public static final double HEIGHT_DIFFERENCE=100;
  public static final double DIStANCE_DESIRE=70;
  private boolean is_close=false;
  public DriveDuringVision() {
    // Use requires() here to declare subsystem dependencies
   requires(Robot.drivebase);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {    
      System.out.println("Start Ajusting");
  }
  // Called repeatedly when this Command is scheduled to run
  protected double map(double x, double in_min,double in_max,double out_min,double out_max){
    return (x-in_min)*(out_max-out_min)/(in_max-in_min)+out_min;
  }
  @Override
  protected void execute() {
    
    table.getEntry("ledMode").setNumber(0);
    NetworkTableEntry tx2 = table.getEntry("tx");
    double isSeetarget = tx2.getDouble(0.0);
   // System.out.println(isSeetarget);
    if(Math.abs(isSeetarget)>17 || isSeetarget==0.0){
      System.out.println("Can't See the target");
    }
    else{
      //while(!Robot.joy.getRawButton(3)){
      table.getEntry("ledMode").setNumber(0);
      NetworkTableEntry tx = table.getEntry("tx");
      double x = tx.getDouble(0.0);
      //NetworkTableEntry ty = table.getEntry("ty");
      //double y = ty.getDouble(0.0);
      double heading_error = x;
      //double current_angle=y;
      double total_angle=4.4;
     // double fix_angle=Math.abs(total_angle-current_angle);
      double Kp = -1.0;
      double min_command=0.00005;
      double steering_adjust = map(Kp * heading_error,-27,27,-1,1);
      //double distance_error= map(Kp * fix_angle,-20.5,20.5,-1,1);
     // double distance_adjust=Kp * distance_error;
      if(x>1)
        steering_adjust-=min_command;
      else
        steering_adjust+=min_command;
    //  System.out.println(distance_adjust);  
      //if(distance_adjust==0){
     //   is_close=true;
     // }
      Robot.drivebase.arcadeDrive(Robot.joy.getRawAxis(1), steering_adjust+Robot.joy.getRawAxis(4));
      }
    }
  //}
  //Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    table.getEntry("ledMode").setNumber(1);
    System.out.println("Target Found");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    System.out.println("Robot Stop");
  }
}
