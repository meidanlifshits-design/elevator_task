package frc.robot.subsystems.elevatorSubSystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.elevatorSubSystem.elevatorIO.elevatorIOInputs;
import org.littletonrobotics.junction.Logger;
import java.security.Timestamp;
import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.TimedRobot;

public class elevator extends SubsystemBase {
    public enum elevatorSubSystemState{
        L1,
        L2,
        L3,
        L4,
        IDLE
    }
    private double setPoint = 0;
    double clampedSetPoint;
    
    private elevatorSubSystemState wantedSystemState = elevatorSubSystemState.IDLE;
    private elevatorSubSystemState currentSystemState = elevatorSubSystemState.IDLE;
    
    private final elevatorIO io;
    private final elevatorIOInputsAutoLogged inputs = new elevatorIOInputsAutoLogged();

    public elevator(elevatorIO io){
        this.io = io;
    }
    
    @Override
    public void periodic(){
        io.updateInputs(inputs);
        double positionInMeters = inputs.position*elevatorConstants.RATIO;
        
        SmartDashboard.putNumber("positionInMeters", positionInMeters);
        SmartDashboard.putNumber("setPoint", setPoint);
        Logger.processInputs("Elevator", inputs);
        Logger.recordOutput("setpoint",setPoint);
        Logger.recordOutput("elevator state", currentSystemState);

        handleStateTransition();
        switch(currentSystemState){
            case IDLE:
                setPoint = elevatorConstants.IDLE_MODE_HEIGHT;
                io.setHeight(setPoint);
                break;

            case L1:
                setPoint = elevatorConstants.L1_HEIGHT;
                io.setHeight(setPoint);
                break;

            case L2:
                setPoint = elevatorConstants.L2_HEIGHT;
                io.setHeight(setPoint);
                break;

            case L3:
                setPoint = elevatorConstants.L3_HEIGHT;
                io.setHeight(setPoint);
                break;

            case L4:
                setPoint = elevatorConstants.L4_HEIGHT;
                io.setHeight(setPoint);
                break;
            }
    }
    private void handleStateTransition(){
        currentSystemState = wantedSystemState;
    }
    public void setWantedState(elevatorSubSystemState wantedSystemState){
        this.wantedSystemState = wantedSystemState;
    }
}
