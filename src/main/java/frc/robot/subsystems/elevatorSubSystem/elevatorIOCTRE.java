package frc.robot.subsystems.elevatorSubSystem;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;

public class elevatorIOCTRE implements elevatorIO{

    private BaseStatusSignal positionSignal;
    private BaseStatusSignal velocitySignal;
    private BaseStatusSignal currentSignal;
    private BaseStatusSignal[] signals;
    protected final TalonFX elevatorMotor;
    PIDController pid = new PIDController(elevatorConstants.KP, elevatorConstants.KI, elevatorConstants.KD);
    PIDController lowPid = new PIDController(elevatorConstants.LOW_KP, elevatorConstants.LOW_KI, elevatorConstants.LOW_KD);
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
    public void elevatorPid(double position, double  setPoint){
        elevatorMotor.set(pid.calculate((position),setPoint));
        
    }
    @Override
    public void elevatorLowPid(double position, double setPoint){
        elevatorMotor.set(lowPid.calculate(position,setPoint));
    }
    @Override
    public void stopElevatorMotor(){
        elevatorMotor.stopMotor();
        
    }
    
    @Override
    public void updateInputs(elevatorIOInputs inputs){
        BaseStatusSignal.refreshAll(signals);
        inputs.position = positionSignal.getValueAsDouble();
        inputs.velocity = velocitySignal.getValueAsDouble();
        inputs.current = currentSignal.getValueAsDouble();
        //inputs.setPoint = setPoint.getValueAsDouble();
    }
    
}
