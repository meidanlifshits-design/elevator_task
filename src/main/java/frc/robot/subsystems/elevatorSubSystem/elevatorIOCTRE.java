package frc.robot.subsystems.elevatorSubSystem;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;

public class elevatorIOCTRE implements elevatorIO{

    private BaseStatusSignal positionSignal;
    private BaseStatusSignal velocitySignal;
    private BaseStatusSignal currentSignal;
    private BaseStatusSignal[] signals;
    protected final TalonFX elevatorMotor;
    PIDController pid = new PIDController(elevatorConstants.KP, elevatorConstants.KI, elevatorConstants.KD);

    public elevatorIOCTRE(){
        elevatorMotor = new TalonFX(0);

        positionSignal = elevatorMotor.getPosition();
        velocitySignal = elevatorMotor.getVelocity();
        currentSignal  = elevatorMotor.getStatorCurrent();

        signals = new BaseStatusSignal[]{
            positionSignal,
            velocitySignal,
            currentSignal
        };

    }
    @Override
    public void elevatorPid(double position, double setPoint){
        elevatorMotor.set(pid.calculate(
            (position*elevatorConstants.GEAR_RATIO)*(3.14*elevatorConstants.WHEEL_DIAMETER_CM),
             setPoint));
    }
    // @Override
    public void stopElevatorMotor(){
        elevatorMotor.stopMotor();
    }
    @Override
    public void updateInputs(elevatorIOInputs inputs){
        BaseStatusSignal.refreshAll(signals);
        inputs.position = positionSignal.getValueAsDouble();
        inputs.velocity = velocitySignal.getValueAsDouble();
        inputs.current = currentSignal.getValueAsDouble();
    }
    
}
