package VO;

import CMN.DTO;

public class SearchVO extends DTO {
	
	// 1. 검색구분
	// 2. 검색어
	
	// 소비자 ID, 사업자 ID, 메뉴 번호, 예약번호 검색 : 10
	// 소비자 이름, 사업자 가게명, 사업자 ID, 예약자 ID 검색 : 20
	// 예약식당 ID 검색 : 30
	private int searchDiv; 				// 검색구분
	private String searchWord;			// 검색어
	
	
	public SearchVO(int searchDiv, String searchWord) {
		this.searchDiv = searchDiv;
		this.searchWord = searchWord;
	}
	
	public SearchVO() {
		
	}

	public int getSearchDiv() {
		return searchDiv;
	}

	public void setSearchDiv(int searchDiv) {
		this.searchDiv = searchDiv;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	@Override
	public String toString() {
		return "SearchVO [searchDiv=" + searchDiv + ", searchWord=" + searchWord + ", toString()=" + super.toString()
				+ "]";
	}
	
	
	

}
