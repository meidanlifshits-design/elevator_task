package frc.robot.subsystems.elevatorSubSystem;

import com.ctre.phoenix6.configs.TalonFXConfiguration;

public class elevatorConstants {
    public static final double MAX_SOFT_LIMIT_M=1.4;
    public static final double MIN_SOFT_LIMIT_M=0;

    public static final double KP = 0.5;//i need take care of these values
    public static final double KI = 0.00;
    public static final double KD = 0;
    public static final double KG=0;
    public static final double KV =0;
    public static final double KA = 0;


    

    TalonFXConfiguration config = new TalonFXConfiguration();
    
    public static final double GEAR_RATIO = 4.5;
    public static final double RATIO = (Math.PI*(3.23342/100))/(GEAR_RATIO);//new ratio
    public static final double ELEVATOR_MOTOR_SIM_MOI = 2;
    
    public static final double IDLE_MODE_HEIGHT = 0;
    public static final double L1_HEIGHT = 0.2;
    public static final double L2_HEIGHT = 0.4;
    public static final double L3_HEIGHT = 0.7;
    public static final double L4_HEIGHT = 0.9;

    


}
