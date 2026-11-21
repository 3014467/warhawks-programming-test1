package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.DoubleSupplier;

public class Drive extends SubsystemBase {
  private final SparkMax leftLeader = new SparkMax(1, MotorType.kBrushless);
  private final SparkMax leftFollower = new SparkMax(2, MotorType.kBrushless);
  private final SparkMax rightLeader = new SparkMax(3, MotorType.kBrushless);
  private final SparkMax rightFollower = new SparkMax(4, MotorType.kBrushless);

  private final DifferentialDrive drive = new DifferentialDrive(leftLeader, rightLeader);

  public Drive() {
    SparkMaxConfig base = new SparkMaxConfig();
    base.smartCurrentLimit(40).idleMode(IdleMode.kBrake);

    SparkMaxConfig leftCfg = new SparkMaxConfig();
    leftCfg.apply(base).inverted(false);
    SparkMaxConfig rightCfg = new SparkMaxConfig();
    rightCfg.apply(base).inverted(true);

    SparkMaxConfig leftFollowCfg = new SparkMaxConfig();
    leftFollowCfg.apply(base).follow(leftLeader);
    SparkMaxConfig rightFollowCfg = new SparkMaxConfig();
    rightFollowCfg.apply(base).follow(rightLeader);

    configure(leftLeader, leftCfg);
    configure(rightLeader, rightCfg);
    configure(leftFollower, leftFollowCfg);
    configure(rightFollower, rightFollowCfg);
  }

  private static void configure(SparkMax m, SparkMaxConfig c) {
    m.configure(c, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void arcade(double forward, double rotation) {
    drive.arcadeDrive(forward, rotation);
  }

  public void stop() {
    drive.stopMotor();
  }

  public Command arcadeDriveCommand(DoubleSupplier forward, DoubleSupplier rotation) {
    return run(() -> arcade(forward.getAsDouble(), rotation.getAsDouble()))
        .finallyDo(interrupted -> stop());
  }

  public Command driveCommand(double forward, double rotation) {
    return run(() -> arcade(forward, rotation)).finallyDo(interrupted -> stop());
  }
}
