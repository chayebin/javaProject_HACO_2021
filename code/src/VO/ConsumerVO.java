package VO;

import CMN.DTO;

public class ConsumerVO extends DTO {
	private String conId; // 소비자 ID
	private String conPw; // 소비자 PW
	private String conName; // 소비자 이름
	private String conPhone; // 소비자 전화번호
	private String conDate; // 소비자 회원 등록일

	public ConsumerVO() {

	}

	// 기본 생성자
	public ConsumerVO(String conId, String conPw, String conName, String conPhone, String conDate) {
		super();
		this.conId = conId;
		this.conPw = conPw;
		this.conName = conName;
		this.conPhone = conPhone;
		this.conDate = conDate;
	}

	// --------------------------- Getters and Setters ---------------------------
	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getConPw() {
		return conPw;
	}

	public void setConPw(String conPw) {
		this.conPw = conPw;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public String getConPhone() {
		return conPhone;
	}

	public void setConPhone(String conPhone) {
		this.conPhone = conPhone;
	}

	public String getConDate() {
		return conDate;
	}

	public void setConDate(String conDate) {
		this.conDate = conDate;
	}

	// --------------------------- Getters and Setters ---------------------------
	// toString()
	@Override
	public String toString() {
		return "ConsumerVO [conId=" + conId + ", conPw=" + conPw + ", conName=" + conName + ", conPhone=" + conPhone
				+ ", conDate=" + conDate + ", toString()=" + super.toString() + "]";
	}

	// hashCode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conDate == null) ? 0 : conDate.hashCode());
		result = prime * result + ((conId == null) ? 0 : conId.hashCode());
		result = prime * result + ((conName == null) ? 0 : conName.hashCode());
		result = prime * result + ((conPhone == null) ? 0 : conPhone.hashCode());
		result = prime * result + ((conPw == null) ? 0 : conPw.hashCode());
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
		ConsumerVO other = (ConsumerVO) obj;
		if (conDate == null) {
			if (other.conDate != null)
				return false;
		} else if (!conDate.equals(other.conDate))
			return false;
		if (conId == null) {
			if (other.conId != null)
				return false;
		} else if (!conId.equals(other.conId))
			return false;
		if (conName == null) {
			if (other.conName != null)
				return false;
		} else if (!conName.equals(other.conName))
			return false;
		if (conPhone == null) {
			if (other.conPhone != null)
				return false;
		} else if (!conPhone.equals(other.conPhone))
			return false;
		if (conPw == null) {
			if (other.conPw != null)
				return false;
		} else if (!conPw.equals(other.conPw))
			return false;
		return true;
	}

} // --- class