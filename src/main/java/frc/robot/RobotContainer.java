// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.subsystems.elevatorSubSystem.elevator;
import frc.robot.subsystems.elevatorSubSystem.elevatorIOCTRE;
import frc.robot.subsystems.elevatorSubSystem.elevatorIOSIM;
import frc.robot.subsystems.elevatorSubSystem.elevator.elevatorSubSystemState;


public class RobotContainer {
    public final CommandPS5Controller p = new CommandPS5Controller(0);


  public elevator Elevator;
  public RobotContainer() {

    if (Robot.isReal()){
      Elevator = new elevator(new elevatorIOCTRE());
    }
    else{
      Elevator = new elevator(new elevatorIOSIM());
    }
    configureBindings();

  }

  private void configureBindings() {
    p.cross().onTrue(
        Commands.runOnce(() -> Elevator.setWantedState(elevatorSubSystemState.L1), Elevator)
    );
    p.circle().onTrue(
        Commands.runOnce(() -> Elevator.setWantedState(elevatorSubSystemState.L4), Elevator)
    );
    p.triangle().onTrue(
        Commands.runOnce(() -> Elevator.setWantedState(elevatorSubSystemState.L2), Elevator)
    );
    p.square().onTrue(
        Commands.runOnce(() -> Elevator.setWantedState(elevatorSubSystemState.L3), Elevator)
    );
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
