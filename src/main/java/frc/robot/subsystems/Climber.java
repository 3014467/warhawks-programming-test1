package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private static final double RAISE_SPEED = 1.0;
  private static final double LOWER_SPEED = -0.8;

  private final SparkMax leftMotor = new SparkMax(20, MotorType.kBrushless);
  private final SparkMax rightMotor = new SparkMax(21, MotorType.kBrushless);

  public Climber() {
    SparkMaxConfig leftCfg = new SparkMaxConfig();
    leftCfg.smartCurrentLimit(60).idleMode(IdleMode.kBrake);

    SparkMaxConfig rightCfg = new SparkMaxConfig();
    rightCfg.smartCurrentLimit(60).idleMode(IdleMode.kBrake).follow(leftMotor, true);

    leftMotor.configure(leftCfg, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightMotor.configure(rightCfg, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void set(double percent) {
    leftMotor.set(percent);
  }

  public void stop() {
    leftMotor.stopMotor();
  }

  public Command raiseCommand() {
    return runEnd(() -> set(RAISE_SPEED), this::stop).withName("ClimberRaise");
  }

  public Command lowerCommand() {
    return runEnd(() -> set(LOWER_SPEED), this::stop).withName("ClimberLower");
  }
}
