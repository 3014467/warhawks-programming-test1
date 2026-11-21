package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Autos;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;

public class RobotContainer {
  private final Drive drive = new Drive();
  private final Climber climber = new Climber();

  private final CommandXboxController driver = new CommandXboxController(0);
  private final CommandXboxController operator = new CommandXboxController(1);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {

    drive.setDefaultCommand(
        drive.arcadeDriveCommand(
            () -> -MathUtil.applyDeadband(driver.getLeftY(), 0.08),
            () -> -MathUtil.applyDeadband(driver.getRightX(), 0.08)));

    operator.y().whileTrue(climber.raiseCommand());
    operator.a().whileTrue(climber.lowerCommand());
  }

  public Command getAutonomousCommand() {
    return Autos.driveToHubAndTurn(drive);
  }
}
