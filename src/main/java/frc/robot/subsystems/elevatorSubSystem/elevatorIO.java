package frc.robot.subsystems.elevatorSubSystem;

import org.littletonrobotics.junction.AutoLog;


public interface elevatorIO {
    default void elevatorPid(double position,double setPoint) {}
    default void elevatorLowPid(double position,double setPoint) {}
    default void stopElevatorMotor() {}
    default void updateInputs(elevatorIOInputs inputs) {}

    @AutoLog
    public class elevatorIOInputs {
        public double position = 0;
        public double velocity = 0;
        public double current =  0;
        public double setPoint = 0;
    }
    
}
