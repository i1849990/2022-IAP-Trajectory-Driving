package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class DistanceAuto extends CommandBase{
    private final DriveTrain driveTrain;
    private double pos;
    private PIDController pid;

    public DistanceAuto(DriveTrain dt, double pos){
        //use addRequirements() here to declare subsystem dependencies
        driveTrain = dt;
        this.pos = pos;
        addRequirements(driveTrain);
    }

    public void initialize(){
        driveTrain.resetEncoders();
        pid = new PIDController(Constants.PIDConstants.kP, Constants.PIDConstants.kI, Constants.PIDConstants.kD);
        pid.setSetpoint(pos);
    }

    public void execute(){
        pid.calculate(driveTrain.getLeftPos());
    }

    @Override
    public boolean isFinished(){
        return Math.abs(pos - driveTrain.getLeftPos()) < 0.1;
    }
}
