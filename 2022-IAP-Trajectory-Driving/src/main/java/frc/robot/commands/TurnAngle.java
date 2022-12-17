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
        driveTrain.tankDrive(pid.calculate(driveTrain.getAngle()),-1 * pid.calculate(driveTrain.getAngle()));
    }

    @Override
    public boolean isFinished(){
        return (Math.abs(angle - driveTrain.getAngle()) < 1);
    }
}