package VO;

import CMN.DTO;

public class ReservationVO extends DTO {

	private String reseNum;
	private String reseConId;
	private String reseBusId;
	private String reseMenu;
	private String reseTime;
	private String reseTable;
	
	
	public ReservationVO() {
		
	}

	// 기본 생성자
	public ReservationVO(String reseNum, String reseConId, String reseBusId, String reseMenu, String reseTime,
			String reseTable) {
		super();
		this.reseNum = reseNum;
		this.reseConId = reseConId;
		this.reseBusId = reseBusId;
		this.reseMenu = reseMenu;
		this.reseTime = reseTime;
		this.reseTable = reseTable;
	}
	
	// --------------------------- Getters and Setters ---------------------------
	public String getReseNum() {
		return reseNum;
	}

	public void setReseNum(String reseNum) {
		this.reseNum = reseNum;
	}

	public String getReseConId() {
		return reseConId;
	}

	public void setReseConId(String reseConId) {
		this.reseConId = reseConId;
	}

	public String getReseBusId() {
		return reseBusId;
	}

	public void setReseBusId(String reseBusId) {
		this.reseBusId = reseBusId;
	}

	public String getReseMenu() {
		return reseMenu;
	}

	public void setReseMenu(String reseMenu) {
		this.reseMenu = reseMenu;
	}

	public String getReseTime() {
		return reseTime;
	}

	public void setReseTime(String reseTime) {
		this.reseTime = reseTime;
	}

	public String getReseTable() {
		return reseTable;
	}

	public void setReseTable(String reseTable) {
		this.reseTable = reseTable;
	}
	// --------------------------- Getters and Setters ---------------------------

	// hashCode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reseBusId == null) ? 0 : reseBusId.hashCode());
		result = prime * result + ((reseConId == null) ? 0 : reseConId.hashCode());
		result = prime * result + ((reseMenu == null) ? 0 : reseMenu.hashCode());
		result = prime * result + ((reseNum == null) ? 0 : reseNum.hashCode());
		result = prime * result + ((reseTable == null) ? 0 : reseTable.hashCode());
		result = prime * result + ((reseTime == null) ? 0 : reseTime.hashCode());
		return result;
	}

	// equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservationVO other = (ReservationVO) obj;
		if (reseBusId == null) {
			if (other.reseBusId != null)
				return false;
		} else if (!reseBusId.equals(other.reseBusId))
			return false;
		if (reseConId == null) {
			if (other.reseConId != null)
				return false;
		} else if (!reseConId.equals(other.reseConId))
			return false;
		if (reseMenu == null) {
			if (other.reseMenu != null)
				return false;
		} else if (!reseMenu.equals(other.reseMenu))
			return false;
		if (reseNum == null) {
			if (other.reseNum != null)
				return false;
		} else if (!reseNum.equals(other.reseNum))
			return false;
		if (reseTable == null) {
			if (other.reseTable != null)
				return false;
		} else if (!reseTable.equals(other.reseTable))
			return false;
		if (reseTime == null) {
			if (other.reseTime != null)
				return false;
		} else if (!reseTime.equals(other.reseTime))
			return false;
		return true;
	}
	
	// toString()
		@Override
		public String toString() {
			return "ReservationVO [reseNum=" + reseNum + ", reseConId=" + reseConId + ", reseBusId=" + reseBusId
					+ ", reseMenu=" + reseMenu + ", reseTime=" + reseTime + ", reseTable=" + reseTable + ", toString()="
					+ super.toString() + "]";
		}

	
} // --- class
