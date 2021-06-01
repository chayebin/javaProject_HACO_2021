package VO;

import CMN.DTO;

public class BusinessVO extends DTO {
	private String busId; // 사업자 ID
	private String busPw; // 사업자 PW
	private String busName; // 사업자 가게명
	private String busPhone; // 사업자 전화번호
	private String busAddr; // 사업자 가게 주소
	private int busTable; // 사업자 가게 테이블 수
	private String busDate; // 사업자 회원 등록일

	public BusinessVO() {
	}

	// 기본 생성자
	public BusinessVO(String busId, String busPw, String busName, String busPhone, String busAddr, int busTable,
			String busDate) {
		super();
		this.busId = busId;
		this.busPw = busPw;
		this.busName = busName;
		this.busPhone = busPhone;
		this.busAddr = busAddr;
		this.busTable = busTable;
		this.busDate = busDate;
	}

	// --------------------------- Getters and Setters ---------------------------
	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getBusPw() {
		return busPw;
	}

	public void setBusPw(String busPw) {
		this.busPw = busPw;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getBusPhone() {
		return busPhone;
	}

	public void setBusPhone(String busPhone) {
		this.busPhone = busPhone;
	}

	public String getBusAddr() {
		return busAddr;
	}

	public void setBusAddr(String busAddr) {
		this.busAddr = busAddr;
	}

	public int getBusTable() {
		return busTable;
	}

	public void setBusTable(int busTable) {
		this.busTable = busTable;
	}

	public String getBusDate() {
		return busDate;
	}

	public void setBusDate(String busDate) {
		this.busDate = busDate;
	}

	// --------------------------- Getters and Setters ---------------------------
	// hashCode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((busAddr == null) ? 0 : busAddr.hashCode());
		result = prime * result + ((busDate == null) ? 0 : busDate.hashCode());
		result = prime * result + ((busId == null) ? 0 : busId.hashCode());
		result = prime * result + ((busName == null) ? 0 : busName.hashCode());
		result = prime * result + ((busPhone == null) ? 0 : busPhone.hashCode());
		result = prime * result + ((busPw == null) ? 0 : busPw.hashCode());
		result = prime * result + busTable;
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
		BusinessVO other = (BusinessVO) obj;
		if (busAddr == null) {
			if (other.busAddr != null)
				return false;
		} else if (!busAddr.equals(other.busAddr))
			return false;
		if (busDate == null) {
			if (other.busDate != null)
				return false;
		} else if (!busDate.equals(other.busDate))
			return false;
		if (busId == null) {
			if (other.busId != null)
				return false;
		} else if (!busId.equals(other.busId))
			return false;
		if (busName == null) {
			if (other.busName != null)
				return false;
		} else if (!busName.equals(other.busName))
			return false;
		if (busPhone == null) {
			if (other.busPhone != null)
				return false;
		} else if (!busPhone.equals(other.busPhone))
			return false;
		if (busPw == null) {
			if (other.busPw != null)
				return false;
		} else if (!busPw.equals(other.busPw))
			return false;
		if (busTable != other.busTable)
			return false;
		return true;
	}

	// toString()
	@Override
	public String toString() {
		return "BusinessVO [busId=" + busId + ", busPw=" + busPw + ", busName=" + busName + ", busPhone=" + busPhone
				+ ", busAddr=" + busAddr + ", busTable=" + busTable + ", busDate=" + busDate + ", toString()="
				+ super.toString() + "]";
	}
} // --- class