package MAIN;

import java.util.List;

import java.util.Objects;

import org.apache.log4j.Logger;

import DAO.MenuDao;
import VO.MenuVO;
import VO.SearchVO;



public class MenuDaoTestMain {

	final Logger LOG = Logger.getLogger(MenuDaoTestMain.class);
	
	MenuVO v01;
	MenuVO v02;
	MenuVO v03;
	
	MenuVO voSearch;
	
	MenuDao dao;
	
	SearchVO search;
	
	// 기본 생성자 초기화
	public MenuDaoTestMain() {
		v01 = new MenuVO("메뉴번호01", "아이디01", "메뉴이름01", 6000, "2021/01/22 18:00:01");
		v02 = new MenuVO("메뉴번호02", "아이디02", "메뉴이름02", 6000, "2021/01/22 18:00:02");
		v03 = new MenuVO("메뉴번호03", "아이디03", "메뉴이름03", 6000, "2021/01/22 18:00:03");
		
		voSearch = new MenuVO("메뉴번호01", "아이디01", "메뉴이름01", 6000, "2021/01/22 18:00:01");
		
		dao = new MenuDao();
	}
	
	// 메뉴 번호 중복 체크
	public void checkMenuNum() {
		//voSearch = new MenuVO("메뉴번호01", "아이디01", "메뉴이름01", 6000, "2021/01/22 18:00:01");	
		int flag = dao.checkMenuNum(voSearch);
		
		if (flag == 0) {
			LOG.debug("= 메뉴 번호 중복 X = " + voSearch.getMenuNum());
		} else {
			LOG.debug("= 메뉴 번호 중복 O = " + voSearch.getMenuNum());
		}
	} // --- checkMenuNum
	
	// 단건 등록 : 메뉴 등록
	// 메뉴 등록 실패 : flag = 0
	// 메뉴 등록 성공 : flag = 1
	// 메뉴 등록 실패 -> 이미 해당 메뉴 번호 존재 : flag = 2
	public void doSave() {
		int flag = dao.doSave(v01);
		
		if (flag == 1) {
			LOG.debug("= 메뉴 등록 성공 = ");
		} else if (flag == 2) {
			LOG.debug("= 메뉴 등록 실패 : 메뉴 번호 중복 = ");
		} else {
			LOG.debug("= 메뉴 등록 실패 = ");
		}
	} // --- doSave
	
	// 메뉴 정보 수정
	public void doUpdate() {
		// * 매번 수행 시 동일한 결과를 얻기 위해 삭제 후 등록 *
		
		// 기존 데이터 삭제 
		dao.doDelete(v01);
		
		// 데이터 등록
		int flag = dao.doSave(v01);
		
		// 데이터 조회
		MenuVO getVO01 = (MenuVO) dao.doSelectOne(v01);
		
		if (Objects.equals(getVO01, v01) == true) {
			// 수정
			getVO01.setMenuName("수정01");
			
			flag = dao.doUpdate(getVO01);
			
			// 데이터 비교
			MenuVO upVO = (MenuVO) dao.doSelectOne(getVO01);
		
			if (flag == 2 && Objects.equals(upVO, getVO01)) {
				LOG.debug("= 수정 성공 = ");
			} else {
				LOG.debug("= 수정 실패 = ");
			}
		}
	} // --- doUpdate
	
	// 삭제
	public void doDelete() {
		int flag = dao.doDelete(v03);
		
		if (flag == 1) {
			LOG.debug("= 삭제 성공 = ");
		} else {
			LOG.debug("= 삭제 실패 = ");
		}
	} // -- doDelete
	
	// 단건 조회(메뉴 번호 조회)
	public void doSelectOne() {
		
		try {
			MenuVO vo = (MenuVO) dao.doSelectOne(v02);
			
			if (Objects.equals(vo, v02)) {
				LOG.debug("= 단건 조회 성공 = ");
			}
		} catch (NullPointerException e) {
			LOG.debug("= 단건 조회 실패(조회 데이터 없음) = " + e.getMessage());
			e.printStackTrace();
		}
		
	} // doSelectOne
	
	// 메뉴 번호, 사업자 ID를 통해 메뉴 정보 검색
	public void doSelectList() {
		// 1. 기존 데이터 삭제
		// 2. 데이터 추가
		// 3. 검색
		
		// 1. 기존 데이터 삭제
		dao.doDelete(v01);
		dao.doDelete(v02);
		dao.doDelete(v03);
		
		// 2. 데이터 추가
		int flag = dao.doSave(v01);
		flag += dao.doSave(v02);
		flag += dao.doSave(v03);
		
		//LOG.debug("flag : " + flag);
		
		// 3. 데이터 검색
		if (flag == 3) {
			// 메뉴 번호(10), 사업자 ID(20)
			SearchVO search = new SearchVO();
			search.setSearchDiv(10);
			search.setSearchWord("메뉴번호02");
			
			List<MenuVO> list = dao.doSelectList(search);
			
			if (list.size() > 0) {
				LOG.debug("= 검색 성공 = ");
				
				for (MenuVO vo : list) {
					LOG.debug(vo);
				}
			} else {
				LOG.debug("= 검색 실패 = ");
			}
		}
	} // -- doSelectList
	
	public static void main(String[] args) {
		MenuDaoTestMain menuMain = new MenuDaoTestMain();
		
		// 메뉴 번호 중복 체크 : 메뉴 등록 시 메뉴 번호 중복 체크
		//menuMain.checkMenuNum();
		
		// 단건 등록 : 메뉴 등록
		//menuMain.doSave();
		
		// 메뉴 정보 수정
		//menuMain.doUpdate();
		
		// 메뉴 정보 삭제
		//menuMain.doDelete();
		
		// 단건 조회 : 메뉴 번호 검색
		//menuMain.doSelectOne();
		
		// 메뉴 번호 or 사업자 ID를 통해 해당 메뉴 정보 검색
		//menuMain.doSelectList();
		
	} // --- main

} // --- class
