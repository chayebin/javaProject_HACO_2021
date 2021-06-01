package MAIN;

import java.util.List;

import java.util.Objects;

import org.apache.log4j.Logger;

import DAO.ReservationDao;
import VO.ReservationVO;
import VO.SearchVO;


public class ReservationDaoTestMain {
	
	final Logger LOG = Logger.getLogger(ReservationDaoTestMain.class);
	
	ReservationVO v01;
	ReservationVO v02;
	ReservationVO v03;
	
	ReservationVO voSearch;
	
	ReservationDao dao;
	
	SearchVO search;
	
	// 기본 생성자 초기화
	public ReservationDaoTestMain() {
		v01 = new ReservationVO("예약번호01", "예약자아이디01", "예약식당아이디01", "예약음식메뉴01", "2021/01/22 18:00:01", "예약테이블01");
		v02 = new ReservationVO("예약번호02", "예약자아이디02", "예약식당아이디02", "예약음식메뉴02", "2021/01/22 18:00:02", "예약테이블01");
		v03 = new ReservationVO("예약번호03", "예약자아이디03", "예약식당아이디03", "예약음식메뉴03", "2021/01/22 18:00:03", "예약테이블01");
		
		voSearch = new ReservationVO("예약번호01", "예약자아이디01", "예약식당아이디01", "예약음식메뉴01", "2021/01/22 18:00:01", "예약테이블01");
		
		dao = new ReservationDao();
	}
	
	// 예약 번호 중복 체크
	public void checkReseNum() {
		//voSearch = ReservationVO("예약번호01", "예약자아이디01", "예약식당아이디01", "예약음식메뉴번호01", "2021/01/22 18:00:01", "예약테이블01");
		int flag = dao.checkReseNum(voSearch);
		
		if (flag == 0) {
			LOG.debug("= 예약 번호 중복 X = " + voSearch.getReseNum());
		} else {
			LOG.debug("= 예약 번호 중복 O = " + voSearch.getReseNum());
		}
	} // --- checkReseNum
	
	// 단건 등록 : 예약 정보 등록
	// 예약 정보 등록 실패 : flag = 0
	// 예약 정보 등록 성공 : flag = 1
	// 예약 정보 등록 실패 -> 이미 해당 예약 번호 존재 : flag = 2
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
	
	// 예약 정보 수정
	public void doUpdate() {
		// * 매번 수행 시 동일한 결과를 얻기 위해 삭제 후 등록 *
		
		// 기존 데이터 삭제
		dao.doDelete(v01);
		
		// 데이터 등록
		int flag =  dao.doSave(v01);
		
		// 데이터 조회
		ReservationVO getVO01 = (ReservationVO) dao.doSelectOne(v01);
		
		if (Objects.equals(getVO01, v01) == true) {
			// 수정
			getVO01.setReseMenu("수정01");
			
			flag = dao.doUpdate(getVO01);
			
			// 데이터 비교
			ReservationVO upVO = (ReservationVO) dao.doSelectOne(getVO01);
			
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
	} // --- doDelete
	
	// 단건 조회(예약 번호 조회)
	public void doSelectOne() {
		
		try {
			ReservationVO vo = (ReservationVO) dao.doSelectOne(v02);
			
			if (Objects.equals(vo, v02)) {
				LOG.debug("= 단건 조회 성공 = ");
			}
		} catch (NullPointerException e) {
			LOG.debug("= 단건 조회 실패(조회 데이터 없음) = " + e.getMessage());
			e.printStackTrace();
		}
	} // -- doSelectOne
	
	// 예약 번호, 예약자 ID, 예약 식당 ID를 통해 예약 정보 검색
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
			// 예약번호(10), 예약자ID(20), 예약식당 ID(30)
			SearchVO search = new SearchVO();
			search.setSearchDiv(10);
			search.setSearchWord("예약번호02");
			
			List<ReservationVO> list = dao.doSelectList(search);
			
			if (list.size() > 0) {
				LOG.debug("= 검색 성공 = ");
				
				for (ReservationVO vo : list) {
					LOG.debug(vo);
				}
			} else {
				LOG.debug("= 검색 실패 = ");
			}
		}
	} // -- doSelectList
	
	public static void main(String[] args) {
		ReservationDaoTestMain reservationMain = new ReservationDaoTestMain();
		
		// 예약 번호 중복 체크 : 예약 정보 등록 시 예약 번호 중복 체크
		//reservationMain.checkReseNum();
		
		// 단건 등록 : 예약 정보 등록
		//reservationMain.doSave();
		
		// 예약 정보 수정 
		//reservationMain.doUpdate();
		
		// 예약 정보 삭제
		//reservationMain.doDelete();
		
		// 단건 조회 : 예약 번호 검색
		//reservationMain.doSelectOne();
		
		// 예약 번호 or 예약자 ID or 예약식당 ID를 통해 해당 예약 정보 검색
		//reservationMain.doSelectList();

	} // --- main

} // --- class