package frc.robot.subsystems.logic;

public class ShooterLogic {
	public static ShooterLogic m_shooterLogic = null;

	public ShooterLogic() {
		
	}

	public static ShooterLogic getInstance() {
		if (m_shooterLogic == null) {
			m_shooterLogic = new ShooterLogic();
		}
		return m_shooterLogic;
	}
}
