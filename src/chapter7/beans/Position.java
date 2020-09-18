package chapter7.beans;

import java.io.Serializable;

public class Position implements Serializable {
	private static final long serialVersionUID = 1L;

	private int positionId;
	private String positionName;

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
