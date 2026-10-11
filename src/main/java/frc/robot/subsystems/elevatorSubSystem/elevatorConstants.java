package frc.robot.subsystems.elevatorSubSystem;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import edu.wpi.first.math.MathUtil;

public class elevatorConstants {
    public static final double MAX_SOFT_LIMIT_M=1.4;
    public static final double MIN_SOFT_LIMIT_M=0;

    public static final double KP = 0.5;//i need take care of these values
    public static final double KI = 0.00;
    public static final double KD = 0;
    public static final double KG=0;
    public static final double KV =0;
    public static final double KA = 0;
    public static final double GEAR_RATIO = 4.5;
    public static final double RATIO = (Math.PI*(3.23342/100))/(GEAR_RATIO);//new ratio
    public static final double ELEVATOR_MOTOR_SIM_MOI = 2;
    
    public static final double IDLE_MODE_HEIGHT = 0;
    public static final double L1_HEIGHT = 0.2;
    public static final double L2_HEIGHT = 0.4;
    public static final double L3_HEIGHT = 0.7;
    public static final double L4_HEIGHT = 0.9;
    public static final TalonFXConfiguration config = new TalonFXConfiguration();
        static{
    config.Slot0.kP = KP;
    config.Slot0.kD = KD;
    config.Slot0.kI = KI;
    config.Slot0.kG = KG;
    config.Slot0.kV = KV;
    config.Slot0.kA = KA;
    config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
    config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = MAX_SOFT_LIMIT_M/RATIO;
    config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
    config.SoftwareLimitSwitch.ReverseSoftLimitThreshold =MIN_SOFT_LIMIT_M/RATIO;  
    config.Slot0.GravityType = GravityTypeValue.Elevator_Static;
    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    config.CurrentLimits.StatorCurrentLimit = 60;      
    config.CurrentLimits.StatorCurrentLimitEnable = true;
    config.CurrentLimits.SupplyCurrentLimit = 40;  
    config.CurrentLimits.SupplyCurrentLimitEnable = true;
    config.MotionMagic.MotionMagicCruiseVelocity = 0.4/RATIO;  // understand why do you need it
    config.MotionMagic.MotionMagicAcceleration = 0.4/RATIO;//use the rollout furmula and write it
    }
    


}
