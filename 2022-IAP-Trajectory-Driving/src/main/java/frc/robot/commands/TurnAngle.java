package frc.robot.commands;

import edu.wpi.first.math.trajectory.*;
import edu.wpi.first.math.trajectory.constraint.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;



public class TurnAngle extends CommandBase{
    private final DriveTrain driveTrain;
    private double angle;
    private PIDController pid;
    public double error;

    public TurnAngle(DriveTrain dt, double angle){
        //use addRequirements() here to declare subsystem dependencies
        driveTrain = dt;
        this.angle = angle;
        addRequirements(driveTrain);
    }

    public void initialize(){
        driveTrain.resetEncoders();
        pid = new PIDController(Constants.PIDConstants.kP, Constants.PIDConstants.kI, Constants.PIDConstants.kD);
        pid.setSetpoint(angle);
    }

    public void execute(){
        error = Math.abs(angle - ((driveTrain.getAngle());
        if(angle>= 180 && Math.abs(angle - ((driveTrain.getAngle()))) > 0.1){
            driveTrain.tankDrive(0.3, -0.3);
        }else if(angle< 180 && Math.abs(angle - ((driveTrain.getAngle()))) > 0.1){
            driveTrain.tankDrive(-0.3, 0.3);
        }else{
            driveTrain.tankDrive(0, 0);
        }
        pid.calculate(driveTrain.getAngle());
        SmartDashboard.putNumber("angle: ", angle);
    }

    @Override
    public boolean isFinished(){
        return (error < 0.1);
    }
}

}