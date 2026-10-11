package frc.robot.subsystems.elevatorSubSystem;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import edu.wpi.first.math.MathUtil;

public class elevatorIOCTRE implements elevatorIO{
    protected final TalonFX elevatorMotor = new TalonFX(0);
    private final MotionMagicVoltage motionMagicRequest = new MotionMagicVoltage(0.0).withSlot(0);
    
    private BaseStatusSignal positionSignal;
    private BaseStatusSignal velocitySignal;
    private BaseStatusSignal currentSignal;
    private BaseStatusSignal[] signals;
    double setPointRots;
    

    public elevatorIOCTRE(){
        TalonFXConfiguration config = new TalonFXConfiguration();
        elevatorMotor.setPosition(0);
        config.Slot0.kP = elevatorConstants.KP;
        config.Slot0.kD = elevatorConstants.KD;
        config.Slot0.kI = elevatorConstants.KI;
        config.Slot0.kG = elevatorConstants.KG;
        config.Slot0.kV = elevatorConstants.KV;
        config.Slot0.kA = elevatorConstants.KA;


        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = elevatorConstants.MAX_SOFT_LIMIT_M/elevatorConstants.RATIO;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = elevatorConstants.MIN_SOFT_LIMIT_M/elevatorConstants.RATIO;  
        config.Slot0.GravityType = GravityTypeValue.Elevator_Static;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        config.CurrentLimits.StatorCurrentLimit = 60;      
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = 40;  
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        config.MotionMagic.MotionMagicCruiseVelocity = 0.4/elevatorConstants.RATIO;  // understand why do you need it
        config.MotionMagic.MotionMagicAcceleration = 0.4/elevatorConstants.RATIO;//use the rollout furmula and write it
        elevatorMotor.getConfigurator().apply(config);

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
    public void setHeight(double setPoint){
        double clampedSetPoint = MathUtil.clamp(setPoint, elevatorConstants.MIN_SOFT_LIMIT_M, elevatorConstants.MAX_SOFT_LIMIT_M);
        setPointRots = (clampedSetPoint/elevatorConstants.RATIO);
        elevatorMotor.setControl(motionMagicRequest.withPosition(setPointRots));
        
        
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
        inputs.setPoint = setPointRots;
    }
    
}
