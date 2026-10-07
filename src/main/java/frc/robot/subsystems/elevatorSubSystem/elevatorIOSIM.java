package frc.robot.subsystems.elevatorSubSystem;

import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.RobotController;

public class elevatorIOSIM extends elevatorIOCTRE{
    private final TalonFXSimState elevatorMotorSim;
    private final DCMotorSim elevatorMotorPhysicsSim;

    public elevatorIOSIM(){
        super();

        elevatorMotorSim = elevatorMotor.getSimState();

        elevatorMotorPhysicsSim = new DCMotorSim(LinearSystemId.createDCMotorSystem(
            DCMotor.getKrakenX60(1),
            elevatorConstants.ELEVATOR_MOTOR_SIM_MOI,
            elevatorConstants.GEAR_RATIO),
                DCMotor.getKrakenX60(1));//J_LOAD
    }
    @Override
    public void updateInputs(elevatorIOInputs inputs){
        updateSimulation();
        super.updateInputs(inputs);
    }
    public void updateSimulation(){
        elevatorMotorSim.setSupplyVoltage(RobotController.getBatteryVoltage());
        elevatorMotorPhysicsSim.setInputVoltage(elevatorMotorSim.getMotorVoltage());
        elevatorMotorPhysicsSim.update(0.02);
        double positionInRads = elevatorMotorPhysicsSim.getAngularPositionRad();
        double positionInRots = positionInRads/(2*Math.PI)*elevatorConstants.GEAR_RATIO;
        double velocityInRads = elevatorMotorPhysicsSim.getAngularVelocityRadPerSec();
        double velocityInRps = velocityInRads/(2*Math.PI)*elevatorConstants.GEAR_RATIO;
        elevatorMotorSim.setRawRotorPosition(positionInRots);
        elevatorMotorSim.setRotorVelocity(velocityInRps);
    }
}
