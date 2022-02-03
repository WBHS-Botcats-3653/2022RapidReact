// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//Imports SubsystemBase
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Dashboard extends SubsystemBase {
	private static Dashboard Dashboard=null;

	/**Constructor
	 * it is a singleton
	 */
	private Dashboard() {

	}

	/**Returns an instance of the Dashboard
	 * this helps with the singleton
	 * 
	 * @return
	 */
	public static Dashboard getDashboard(){
		if (Dashboard == null)
			Dashboard = new Dashboard();
		return Dashboard;
	}
}
