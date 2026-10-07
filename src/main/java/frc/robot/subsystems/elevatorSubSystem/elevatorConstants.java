package frc.robot.subsystems.elevatorSubSystem;

public class elevatorConstants {
    public static final double MAX_SOFT_LIMIT_CM=1.2;
    public static final double MIN_SOFT_LIMIT_CM=0;//not good

    public static final double KP = 1.1;//take care of these values,see if they're actually making sense
    public static final double KI = 0.006;
    public static final double KD = 0.3;

    public static final double LOW_KP = 0.5;
    public static final double LOW_KI = 0;
    public static final double LOW_KD = 0.15;
    
    public static final double GEAR_RATIO = 4.5;
    public static final double RATIO = GEAR_RATIO/(Math.PI*3.23342);
    public static final double ELEVATOR_MOTOR_SIM_MOI = 2;
    public static final double WHEEL_DIAMETER_CM = 0.15;

    public static final double IDLE_MODE_HEIGHT = 0;
    public static final double L1_HEIGHT = 0.03;
    public static final double L2_HEIGHT = 0.4576;
    public static final double L3_HEIGHT = 0.7;
    public static final double L4_HEIGHT = 0.9;

    public static final double CURRENT_TRASHOLD_AMPS = 40;
    public static final double VELOCITY_TRASHOLD_RPS = 0.5;


}
