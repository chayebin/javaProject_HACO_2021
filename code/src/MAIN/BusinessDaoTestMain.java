package MAIN;

import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;

import DAO.BusinessDao;
import VO.BusinessVO;
import VO.SearchVO;

public class BusinessDaoTestMain {
	final Logger LOG = Logger.getLogger(BusinessDaoTestMain.class);
	BusinessVO v01;
	BusinessVO v02;
	BusinessVO v03;
	BusinessVO voSearch;
	BusinessDao dao;
	SearchVO search;

	// 기본 생성자 초기화
	public BusinessDaoTestMain() {
		v01 = new BusinessVO("아이디01", "비밀번호01", "가게명01", "02-123-4567", "주소01", 6, "2021/01/20 18:00:01");
		v02 = new BusinessVO("아이디02", "비밀번호02", "가게명02", "02-123-4567", "주소02", 6, "2021/01/20 18:00:02");
		v03 = new BusinessVO("아이디03", "비밀번호03", "가게명03", "02-123-4567", "주소03", 6, "2021/01/20 18:00:03");
		voSearch = new BusinessVO("아이디01", "비밀번호01", "가게명11", "02-123-4567", "주소11", 6, "2021/01/20 18:00:11");
		dao = new BusinessDao();
	}

	// 소비자 ID 중복 체크
	public void checkBusinessId() {
		// voSearch = new BusinessVO("아이디04", "비밀번호04", "가게명04", "02-123-4567", "주소04",
		// 6, "2021/01/20 18:00:04");
		int flag = dao.checkBusinessId(voSearch);
		if (flag == 0) {
			LOG.debug("= ID 중복 X = " + voSearch.getBusId());
		} else {
			LOG.debug("= ID 중복 O = " + voSearch.getBusId());
		}
	} // --- checkBusinessId
		// 단건 등록 : 회원가입
		// 회원가입 실패 : flag = 0
		// 회원가입 성공 : flag = 1
		// 회원가입 실패 -> 이미 해당 ID 존재 : flag = 2

	public void doSave() {
		int flag = dao.doSave(v01);
		if (flag == 1) {
			LOG.debug("= 등록 성공 = ");
		} else if (flag == 2) {
			LOG.debug("= 등록 실패 : id 중복 = ");
		} else {
			LOG.debug("= 등록 실패 = ");
		}
	} // --- doSave
		// 사업자 회원 로그인 : ID, PW 일치 여부 확인
		// 로그인 실패 -> 비밀번호가 틀렸습니다 : flag = 1
		// 로그인 성공 -> 로그인 성공 : flag = 2
		// 로그인 실패 -> 가입되지 않은 ID입니다 : flag = 0

	public void doLogIn() {
		int flag = dao.doLogIn(voSearch);
		if (flag == 1) {
			LOG.debug("= 로그인 실패 -> 비밀번호가 틀렸습니다 = " + voSearch.getBusId() + ", " + voSearch.getBusPw());
		} else if (flag == 2) {
			LOG.debug("= 로그인 성공 -> 로그인 성공 = " + voSearch.getBusId() + ", " + voSearch.getBusPw());
		} else if (flag == 0) {
			LOG.debug("= 로그인 실패 -> 가입되지 않은 ID입니다 = " + voSearch.getBusId());
		}
	} // --- doLogIn

	public void doUpdate() {
		// * 매번 수행 시 동일한 결과를 얻기 위해 삭제 후 등록 *
		// 기존 데이터 삭제
		dao.doDelete(v01);
		// 데이터 등록
		int flag = dao.doSave(v01);
		// 데이터 조회
		BusinessVO getVO01 = (BusinessVO) dao.doSelectOne(v01);
		if (Objects.equals(getVO01, v01) == true) {
			// 수정
			getVO01.setBusName("수정01");
			flag = dao.doUpdate(getVO01);
			// 데이터 비교
			BusinessVO upVO = (BusinessVO) dao.doSelectOne(getVO01);
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
	} // dodoDelete
		// 단건 조회(ID 조회)

	public void doSelectOne() {
		try {
			BusinessVO vo = (BusinessVO) dao.doSelectOne(v02);
			if (Objects.equals(vo, v02)) {
				LOG.debug("= 단건 조회 성공 = ");
			}
		} catch (NullPointerException e) {
			LOG.debug("= 단건 조회 실패(조회 데이터 없음 = " + e.getMessage());
			e.printStackTrace();
		}
	} // --- doSelectOne
		// 소비자 회원 ID, 이름을 통해 회원 정보 검색

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
		LOG.debug("flag : " + flag);
		// 3. 데이터 검색
		if (flag == 3) {
			// ID(10), 이름(20)
			SearchVO search = new SearchVO();
			search.setSearchDiv(10);
			search.setSearchWord("아이디02");
			List<BusinessVO> list = dao.doSelectList(search);
			if (list.size() > 0) {
				LOG.debug("= 검색 성공 = ");
				for (BusinessVO vo : list) {
					LOG.debug(vo);
				}
			} else {
				LOG.debug("= 검색 실패 = ");
			}
		}
	} // --- doSelectList

	public static void main(String[] args) {
		BusinessDaoTestMain businessMain = new BusinessDaoTestMain();
		// ID 중복 체크 : 사업자 회원가입 시 ID 중복 체크
		//businessMain.checkBusinessId();
		// 단건 등록 : 사업자 회원가입
		// businessMain.doSave();
		// 로그인 : 사업자 ID, PW 일치 여부 확인
		// businessMain.doLogIn();
		// 사업자 회원 정보 수정
		// businessMain.doUpdate();
		// 사업자 회원 삭제
		// businessMain.doDelete();
		// 단건 조회 : 사업자 회원 ID 검색
		// businessMain.doSelectOne();
		// 사업자 회원 ID, 이름을 통해 해당 회원 정보 검색
		// businessMain.doSelectList();
	} // --- main
} // --- class