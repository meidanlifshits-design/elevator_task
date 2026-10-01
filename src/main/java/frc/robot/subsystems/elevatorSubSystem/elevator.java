package frc.robot.subsystems.elevatorSubSystem;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.elevatorSubSystem.elevatorIO.elevatorIOInputs;
import org.littletonrobotics.junction.Logger;

public class elevator extends SubsystemBase {
    public enum elevatorSubSystemState{
        COLLECT_MODE,
        MIDDLE_MODE,
        MAX_MODE,
        IDLE
    }
    private double setPoint = 0;
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
        double positionInCm = (inputs.position*elevatorConstants.GEAR_RATIO)*(Math.PI*elevatorConstants.WHEEL_DIAMETER_CM);
        SmartDashboard.putNumber("positionInCm", positionInCm);
        Logger.processInputs("Elevator", inputs);
        if (positionInCm>elevatorConstants.MAX_SOFT_LIMIT_CM || positionInCm<elevatorConstants.MIN_SOFT_LIMIT_CM){
            io.stopElevatorMotor();
            return;
        }
        currentSystemState = handleStateTransition();
        switch(currentSystemState){
            case IDLE:
                System.out.println("elevator is idle");
                break;
            case COLLECT_MODE:
                System.out.println("elevator is in collect mode");
                setPoint = elevatorConstants.COLLECT_MODE_HEIGHT;
                io.elevatorPid(inputs.position, setPoint);
                break;
            case MIDDLE_MODE:
                System.out.println("elevator is in middle mode");
                setPoint = elevatorConstants.MIDDLE_MODE_HEIGHT;
                io.elevatorPid(inputs.position, setPoint);
                break;
            case MAX_MODE:
                setPoint = elevatorConstants.MAX_MODE_HEIGHT;
                io.elevatorPid(inputs.position, setPoint);
                System.out.println("elevator is in max mode");
                break;
            
        }
    }
        private elevatorSubSystemState handleStateTransition(){
            return wantedSystemState;
        }
        public void setWantedState(elevatorSubSystemState wantedSystemState){
            this.wantedSystemState = wantedSystemState;
        }
        

    

}
