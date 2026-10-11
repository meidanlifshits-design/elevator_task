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
        elevatorMotor.getConfigurator().apply(elevatorConstants.config);
        elevatorMotor.setPosition(0);
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
    public void updateInputs(elevatorIOInputs inputs){
        BaseStatusSignal.refreshAll(signals);

        inputs.position = positionSignal.getValueAsDouble();
        inputs.velocity = velocitySignal.getValueAsDouble();
        inputs.current = currentSignal.getValueAsDouble();
    }
    
}
