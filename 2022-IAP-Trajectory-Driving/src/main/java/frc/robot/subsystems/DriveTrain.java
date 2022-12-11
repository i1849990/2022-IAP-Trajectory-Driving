package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.kauailabs.navx.frc.AHRS;

import org.opencv.core.TickMeter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.SPI;
//These are the imports to the files where we can access different methods

public class DriveTrain extends SubsystemBase {

  private WPI_TalonSRX left;
  private WPI_TalonSRX right;
  
  private AHRS navx;
  /** Creates a new DriveTrain. */
  public DriveTrain() { // constuctor
    navx = new AHRS(SPI.Port.kMXP);
    left = new WPI_TalonSRX(Constants.leftPort);
    right = new WPI_TalonSRX(Constants.rightPort);
    left.configFactoryDefault();
    right.configFactoryDefault();

    left.setInverted(true);
    right.setInverted(false);
    //default constructor conditions
  }
  public void tankDrive(double lPower, double rPower){
    left.set(ControlMode.PercentOutput, lPower);
    right.set(ControlMode.PercentOutput, rPower);
    //Sets how fast it should drive
  }
  public double getLeftPos(){
    return left.getSelectedSensorPosition()*Constants.ticksToMeters;
  }
  public double getRightPos(){
    return right.getSelectedSensorPosition()*Constants.ticksToMeters;
  }
  public double getPos(){
    return ((left.getSelectedSensorPosition()+right.getSelectedSensorPosition())/(2*Constants.ticksToMeters));
  }

  public void resetEncoders(){
    left.setSelectedSensorPosition(0,0,10);
    right.setSelectedSensorPosition(0,0,10);
  }
  public double getAngle(){
    return navx.getAngle(); 
  }
  public void resetN(){
    navx.reset();
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    tankDrive(RobotContainer.getJoy1().getY(), RobotContainer.getJoy2().getY());
  }
  

}