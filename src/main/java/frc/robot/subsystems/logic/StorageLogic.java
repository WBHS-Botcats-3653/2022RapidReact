package frc.robot.subsystems.logic;

public class StorageLogic {
	private static StorageLogic m_storageLogic = null;

	public StorageLogic() {
		
	}

	public static StorageLogic getStorage() {
		if (m_storageLogic == null) {
			m_storageLogic = new StorageLogic();
		}
		return m_storageLogic;
	}
}
