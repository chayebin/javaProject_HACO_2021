package VO;

import CMN.DTO;

public class MenuVO extends DTO {
	
	private String menuNum;			// 메뉴 번호
	private String menuBusId;		// 사업자 ID
	private String menuName;		// 메뉴 이름
	private int menuPrice;			// 메뉴 가격
	private String menuDate;		// 메뉴 등록일

	
	public MenuVO() {
		
	}

	// 기본 생성자
	public MenuVO(String menuNum, String menuBusId, String menuName, int menuPrice, String menuDate) {
		super();
		this.menuNum = menuNum;
		this.menuBusId = menuBusId;
		this.menuName = menuName;
		this.menuPrice = menuPrice;
		this.menuDate = menuDate;
	}

	// // --------------------------- Getters and Setters ---------------------------
	public String getMenuNum() {
		return menuNum;
	}

	public void setMenuNum(String menuNum) {
		this.menuNum = menuNum;
	}

	public String getMenuBusId() {
		return menuBusId;
	}

	public void setMenuBusId(String menuBusId) {
		this.menuBusId = menuBusId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(int menuPrice) {
		this.menuPrice = menuPrice;
	}

	public String getMenuDate() {
		return menuDate;
	}

	public void setMenuDate(String menuDate) {
		this.menuDate = menuDate;
	}
	// --------------------------- Getters and Setters ---------------------------

	// hashCode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuBusId == null) ? 0 : menuBusId.hashCode());
		result = prime * result + ((menuDate == null) ? 0 : menuDate.hashCode());
		result = prime * result + ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result + ((menuNum == null) ? 0 : menuNum.hashCode());
		result = prime * result + menuPrice;
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
		MenuVO other = (MenuVO) obj;
		if (menuBusId == null) {
			if (other.menuBusId != null)
				return false;
		} else if (!menuBusId.equals(other.menuBusId))
			return false;
		if (menuDate == null) {
			if (other.menuDate != null)
				return false;
		} else if (!menuDate.equals(other.menuDate))
			return false;
		if (menuName == null) {
			if (other.menuName != null)
				return false;
		} else if (!menuName.equals(other.menuName))
			return false;
		if (menuNum == null) {
			if (other.menuNum != null)
				return false;
		} else if (!menuNum.equals(other.menuNum))
			return false;
		if (menuPrice != other.menuPrice)
			return false;
		return true;
	}
	
	// toString()
		@Override
		public String toString() {
			return "MenuVO [menuNum=" + menuNum + ", menuBusId=" + menuBusId + ", menuName=" + menuName + ", menuPrice="
					+ menuPrice + ", menuDate=" + menuDate + ", toString()=" + super.toString() + "]";
		}
	
} // --- class