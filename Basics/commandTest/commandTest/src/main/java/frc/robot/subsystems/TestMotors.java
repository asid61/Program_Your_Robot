/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;

import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.DriveWithPercent;

/**
 * Add your docs here.
 */
public class TestMotors extends Subsystem {

  public TalonSRX masterTalon;
  public VictorSPX slaveVictor;

  public TestMotors() {

    controllerInit();
  }

  void controllerInit() { // initializes the motor controller settings
    masterTalon = new TalonSRX(RobotMap.TestMotors.masterDrive);
    slaveVictor = new VictorSPX(RobotMap.TestMotors.slaveDrive);

    masterTalon.configFactoryDefault(RobotMap.TIMEOUT); // reset all the settings to default
    slaveVictor.configFactoryDefault(RobotMap.TIMEOUT);

    masterTalon.setNeutralMode(NeutralMode.Brake); // set the behavior when 0 output
    slaveVictor.setNeutralMode(NeutralMode.Brake);

    masterTalon.setInverted(RobotMap.TestMotors.invertMaster); // set if the output direction is to be inverted
    slaveVictor.follow(masterTalon); // follow the master
    slaveVictor.setInverted(RobotMap.TestMotors.invertSlave);

    masterTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    masterTalon.setSensorPhase(RobotMap.TestMotors.invertMasterEncoder);
    masterTalon.config_kF(0, 0);
    masterTalon.config_kP(0, 0.2);
    masterTalon.config_kI(0, 0.0002);
    masterTalon.config_kD(0, 2);
    masterTalon.selectProfileSlot(0, 0);


  }

  public void drive(double master, double slave) {
    masterTalon.set(ControlMode.PercentOutput, master);
    //slaveVictor.set(ControlMode.PercentOutput, slave);
  }

  public void goToPosition(double target) {
    masterTalon.set(ControlMode.Position, target);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveWithPercent());
  }
}
