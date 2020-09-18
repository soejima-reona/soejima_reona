package chapter7.beans;

import java.io.Serializable;

public class Branch implements Serializable {
	private static final long serialVersionUID = 1L;

	private int branchId;
	private String branchName;

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
