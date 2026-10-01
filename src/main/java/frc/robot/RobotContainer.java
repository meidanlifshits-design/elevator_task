// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.elevatorSubSystem.elevator;
import frc.robot.subsystems.elevatorSubSystem.elevatorIOCTRE;
import frc.robot.subsystems.elevatorSubSystem.elevatorIOSIM;

public class RobotContainer {
  public elevator Elevator;
  public RobotContainer() {
    configureBindings();
    if (Robot.isReal()){
      Elevator = new elevator(new elevatorIOCTRE());
    }
    else{
      Elevator = new elevator(new elevatorIOSIM());
    }
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
