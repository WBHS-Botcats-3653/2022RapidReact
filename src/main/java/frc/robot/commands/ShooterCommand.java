// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Imports ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
//Imports RunCommand
import edu.wpi.first.wpilibj2.command.RunCommand;
//Imports OI
import frc.robot.OI;
import frc.robot.commands.subcommands.indexerSubcommands.IndexerCommand;
//Imports Shooter subsystem
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends ParallelCommandGroup {
	//Holds instances of OI and Shooter subsystem
	private OI m_oi;
	private Shooter m_shooter;
	private double shoot;

	public ShooterCommand() {
		//Initializes instance variables with OI and Shooter subsystem
		m_oi = OI.getInstance();
		m_shooter = Shooter.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		super.initialize();
	}
	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (m_oi.getEMStop()) {
			m_shooter.setSpinSpeed(0);
			return;
		}

		/*shoot = m_oi.getShoot();
		addCommands(
			new PrintCommand("this is the speed: " + shoot),
			new RunCommand(() -> m_shooter.spinSpinner(shoot), m_shooter).withInterrupt(() -> m_oi.getShoot() == 0),
			new IndexerCommand().withInterrupt(() -> m_oi.getShoot() == 0)
		);*/
	}
}