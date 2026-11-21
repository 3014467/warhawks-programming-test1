package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Drive;

public final class Autos {
  private static final double DRIVE_SPEED = 0.5;
  private static final double DRIVE_TIME_SEC = 3.0;
  private static final double TURN_SPEED = 0.4;
  private static final double TURN_90_TIME_SEC = 0.75;

  private Autos() {}

  public static Command driveToHubAndTurn(Drive drive) {
    return Commands.sequence(
        drive.driveCommand(DRIVE_SPEED, 0.0).withTimeout(DRIVE_TIME_SEC),
        drive.driveCommand(0.0, TURN_SPEED).withTimeout(TURN_90_TIME_SEC));
  }
}
