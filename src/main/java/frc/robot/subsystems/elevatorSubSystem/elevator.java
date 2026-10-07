package frc.robot.subsystems.elevatorSubSystem;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.elevatorSubSystem.elevatorIO.elevatorIOInputs;

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
        double positionInMeters = (inputs.position*elevatorConstants.GEAR_RATIO)*(Math.PI*elevatorConstants.RATIO)/100;
        SmartDashboard.putNumber("positionInMeters", positionInMeters);
        SmartDashboard.putNumber("setPoint", setPoint);
        Logger.processInputs("Elevator", inputs);
        Logger.recordOutput("setPoint",setPoint);

        
        // if (inputs.velocity<elevatorConstants.VELOCITY_TRASHOLD_RPS &&
        //  inputs.current>elevatorConstants.CURRENT_TRASHOLD_AMPS){
        //     io.stopElevatorMotor();//take care of this,slow down
            
        // }
        handleStateTransition();
        switch(currentSystemState){
            case IDLE:
                System.out.println("elevator is idle");
                setPoint = elevatorConstants.IDLE_MODE_HEIGHT;
                break;
            case L1:
                System.out.println("elevator is in collect mode");
                setPoint = elevatorConstants.L1_HEIGHT;
                break;
            case L2:
                System.out.println("elevator is in middle mode");
                setPoint = elevatorConstants.L2_HEIGHT;
                break;
            case L3:
                setPoint = elevatorConstants.L3_HEIGHT;
                System.out.println("elevator is in max mode");
                break;
            case L4:
                setPoint = elevatorConstants.L4_HEIGHT;
                System.out.println("L4");
                break;
            
            }
            double ClampedSetPoint = MathUtil.clamp(setPoint, elevatorConstants.MIN_SOFT_LIMIT_CM, elevatorConstants.MAX_SOFT_LIMIT_CM);
            if (elevatorConstants.MAX_SOFT_LIMIT_CM>positionInMeters || elevatorConstants.MIN_SOFT_LIMIT_CM<positionInMeters){
                if (Math.abs( inputs.velocity) < elevatorConstants.VELOCITY_TRASHOLD_RPS &&
                Math.abs(inputs.current)>elevatorConstants.CURRENT_TRASHOLD_AMPS){
                    io.elevatorLowPid(positionInMeters, ClampedSetPoint);//not good solution,
            
                }
                else{
                    io.elevatorPid(positionInMeters, ClampedSetPoint);
                }
        }
    }
        private void handleStateTransition(){
            currentSystemState = wantedSystemState;
            //return wantedSystemState;
        }
        public void setWantedState(elevatorSubSystemState wantedSystemState){
            this.wantedSystemState = wantedSystemState;
        }
        

    

}
