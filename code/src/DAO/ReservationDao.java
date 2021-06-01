package DAO;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import CMN.DTO;
import CMN.EStringUtil;
import CMN.WorkStd;
import VO.ReservationVO;
import VO.SearchVO;



public class ReservationDao implements WorkStd {

	// 멤버 변수 영역-----------------------------------------------------------------------------
	// Logger
	private final Logger LOG = Logger.getLogger(ReservationDao.class);
	
	// 예약 데이터 저장 Path
	private String reservationDataFilePath = "";
	
	// 시스템 환경 설정
	private static Properties properties;
	
	// 파일 -> List 변환
	private List<ReservationVO> reservationList;	
	// 멤버 변수 영역-----------------------------------------------------------------------------
	
	// 생성자 초기화
	public ReservationDao() {
		LOG.debug("= ReservationDao() = "); 
		
		// Properties : 환경설정 set
		properties = EStringUtil.readProperties("dev");
		reservationDataFilePath = properties.getProperty("reservationPath");
		
		reservationList = (List<ReservationVO>) doReadFile(reservationDataFilePath);
	}
	
	// 파일 저장
	@Override
	public int doSaveFile(List<? extends DTO> list) {
		// -----------------------------------------------------------------------------
		// 1. List를 읽는다
		// 2. 파일을 읽는다
		// 3. List -> ReservationVO를 추출한다
		// 4. ReservationVO에서 데이터를 꺼내 파일에 기록한다
		// 5. count를 반환한다.
		// -----------------------------------------------------------------------------
		int cnt = 0;
		
		// 2. 파일 읽기
		File file = new File(reservationDataFilePath);
		FileWriter writer = null;
		BufferedWriter bw = null;
		
		try {
			writer = new FileWriter(file);
			bw = new BufferedWriter(writer);
			
			for (int i = 0; i < list.size(); i ++) {
				// 3. List - > ReservationVO 추출
				ReservationVO vo = (ReservationVO) list.get(i);
				StringBuffer sb = new StringBuffer(100);
				String delim = ",";
				
				// v01 = new ReservationVO("예약번호01", "예약자아이디01", "예약식당아이디01", "예약음식메뉴01", "2021/01/22 18:00:01", "예약테이블01");
				sb.append(vo.getReseNum() + delim);
				sb.append(vo.getReseConId() + delim);
				sb.append(vo.getReseBusId() + delim);
				sb.append(vo.getReseMenu() + delim);
				sb.append(vo.getReseTime() + delim);
				sb.append(vo.getReseTable());
				
				if ((i + 1) != list.size()) {
					sb.append("\n");
				}
				
				//LOG.debug("sb : " + sb.toString());
				
				// 등록 건수 증가
				cnt++;
				
				// 4. 파일에 기록
				bw.write(sb.toString());
			}// --- for i
		} catch (IOException e) {
			LOG.debug("= IOException = " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					LOG.debug("= IOException = " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		LOG.debug("파일에 기록 건수 : " + cnt);
		
		// 5. count 반환
		return cnt;
	} // --- doSaveFile

	// 파일 읽기
	// reservation.csv 파일 읽어 List로 변환
	@Override
	public List<? extends DTO> doReadFile(String path) {
		// -----------------------------------------------------------------------------
		// 1. 파일을 읽는다
		// 2. 읽은 파일 정보를 Reservation에 할당한다
		// 3. List에 추가한다
		// 4. List를 반환한다
		// -----------------------------------------------------------------------------
		List<ReservationVO> list = new ArrayList<ReservationVO>();
		
		// 1. 파일 읽기
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			String data = "";
			
			while ((data = br.readLine()) != null) {
				ReservationVO vo = null;
				
				// 데이터 공백 제거
				data = data.trim();
				
				// 데이터 존재 확인
				if ("".equals(data) || data.length() == 0) {
					continue;
				}
				
				// data to List : 파일에서 읽은 데이터를 List로 변환
				String[] reservationArray = data.split(","); // ","(쉼포) 단위로 데이터 구분
				
				// 2. 읽은 파일 정보를 ReservationVO에 할당
				vo = new ReservationVO(reservationArray[0], reservationArray[1], reservationArray[2], reservationArray[3], reservationArray[4], reservationArray[5]);
				
				// 3. List에 추가
				list.add(vo);
			} // --- while
			
			for (ReservationVO vo : list) {
				//LOG.debug("list : " + vo);
			}
		} catch (IOException e) {
			LOG.debug("= IOException = " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOG.debug("= IOException = " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		// 4. List 반환
		return list;
	} // --- doReadFile
	
	// List에서 reseNum이 있는지 확인
	// 예약 번호 중복 : flag = 1
	// 예약 번호 중복 X : flag = 0
	public int checkReseNum(ReservationVO vo) {
		// -----------------------------------------------------------------------------
		// 1. List에 reseNum이 있는지 찾는다
		//   1.1 List를 반복문 돌린다
		// 2. param의 reseNum과 List의 reseNum을 비교한다
		//   2.1 같으면 flag = 1
		// -----------------------------------------------------------------------------
		int flag = 0;
		
		for (int i = 0; i < reservationList.size(); i++) {
			ReservationVO vsVO = reservationList.get(i);
			
			if (vo.getReseNum().equals(vsVO.getReseNum())) {
				flag = 1;
				break;
			}
		}
		return flag;
	} // --- checkReseNum

	// 단건 등록 : 예약 정보 등록
	// 예약 정보 등록 실패 : flag = 0
	// 예약 정보 등록 성공 : flag = 1
	// 예약 정보 등록 실패 -> 이미 해당 예약 번호 존재 : flag = 2
	@Override
	public int doSave(DTO obj) {
		// -----------------------------------------------------------------------------
		// 1. param을 읽는다
		// 2. 메뉴번호 중복 체크
		// 3. List에 기록
		// 4. 파일에 저장
		// -----------------------------------------------------------------------------
		int flag = 0;
		
		// 1. param 읽기
		ReservationVO inVO = (ReservationVO) obj;
		
		// 2. 예약 번호 중복 체크
		int reseNumCheck = checkReseNum(inVO);
		
		if (reseNumCheck == 1) {
			flag = 2;
			return flag;
		}
		
		// 3. List에 기록
		boolean addFlag = reservationList.add(inVO);
		//LOG.debug("addFlag = " + addFlag);
		
		flag = (addFlag == true) ? 1 : 0;
		
		// 4. 파일에 저장
		int saveCnt = doSaveFile(reservationList);
		//LOG.debug("saveCnt = " + saveCnt);
		//LOG.debug("flag = " + flag);
		
		return flag;
	} // --- doSave

	// 예약 정보 수정
	// 예약 정보 수정 성공 : flag = 2
	// 예약 정보 수정 실패 : flag = 0(등록된 예약 번호 없음)
	// 예약 정보 수정 실패 : falg = 1(저장 실패)
	@Override
	public int doUpdate(DTO obj) {
		// 예약 번호를 통해 수정 데이터 확인
		// 삭제 후 등록하는 방식
		int flag = 0;
		
		// param 읽기
		ReservationVO inVO = (ReservationVO) obj;
		
		// 예약 존재 여부 확인
		if ((checkReseNum(inVO) == 1)) {
			// 삭제
			flag = doDelete(inVO);
			
			if (flag == 1) {
				// 등록
				flag += doSave(inVO);
			}
		}
		LOG.debug("= flag = " + flag);
		
		return flag;
	} // --- doUpdate

	// 예약 정보 삭제
	// 예약 정보 삭제 성공 : flag = 1
	// 예약 정보 삭제 실패 : flag = 0
	// 예약 정보 삭제 실패 : flag = 20(저장 실패)
	@Override
	public int doDelete(DTO obj) {
		// -----------------------------------------------------------------------------
		// 1. param 데이터를 읽는다
		// 2. List를 반복문으로 처리
		//   2.1 삭제할 데이터를 찾는다
		//   2.2 찾았으면 삭제한다
		// 3. 데이터를 삭제 후 List를 파일에 기록한다
		// -----------------------------------------------------------------------------
		int flag = 0;
		
		// 1. param 데이터 읽기
		ReservationVO inVO = (ReservationVO) obj;
		
		// 삭제 전 데이터 건수
		int reseBeforeDelcnt = reservationList.size();
		
		// 2. List 삭제 : 뒤에서부터
		for (int i = reservationList.size() - 1; i >= 0; i--) {
			ReservationVO vo = reservationList.get(i);
			
			// 2.1 삭제할 데이터 찾기
			if (inVO.getReseNum().equals(vo.getReseNum())) {
				
				// 2.2 데이터 삭제
				reservationList.remove(i);
				flag = 1;
				break;
			}
		} // --- for i
		
		// 1(삭제 성공) / 0(삭제 실패) / 20(저장 실패)
		if (flag == 1) {
			
			// 3. 데이터 삭제 후 List를 파일에 기록
			int saveCnt = this.doSaveFile(reservationList);
			
			// 파일 저장 실패
			if (reseBeforeDelcnt != (saveCnt + 1)) {
				flag = 20;
			}
		}
		return flag;
	} // --- doDelete

	// 단건 조회(예약 번호 조회)
	@Override
	public DTO doSelectOne(DTO obj) {
		// -----------------------------------------------------------------------------
		// 1. param을 읽는다
		// 2. List에서 param을 찾는다
		// 3. 존재하면 BusinessVO 반환
		// -----------------------------------------------------------------------------
		ReservationVO outVO = null;
		
		// 1. param 읽기
		ReservationVO inVO = (ReservationVO) obj;
		
		for (int i = 0; i < this.reservationList.size(); i++) {
			ReservationVO vo = reservationList.get(i);
			
			// 2. List에서 param 찾기
			if (inVO.getReseNum().equals(vo.getReseNum())) {
				outVO = vo;
				break;
			}
		} // --- for i
		
		// 3. ReservationVO 반환
		return outVO;
	} // --- doSelectOne

	// 예약 조회 : 예약 번호, 예약자 ID, 예약 식당 ID로 List 조회
	@Override
	public List<ReservationVO> doSelectList(DTO obj) {
		// -----------------------------------------------------------------------------
		// 메뉴 번호 : 10
		// 예약자 ID : 20
		// 예약 식당 ID : 30
		// 1. 검색 데이터 저장
		// 2. param 읽기
		// 3. csv 파일 데이터와 param 비교
		// -----------------------------------------------------------------------------
		
		// 1. 검색 데이터 저장
		List<ReservationVO> list = new ArrayList<ReservationVO>();
		
		// 2. param 읽기
		SearchVO inVO = (SearchVO) obj;
		//LOG.debug("param : " + inVO);
		
		for (int i = 0; i < reservationList.size(); i++) {
			ReservationVO vo = reservationList.get(i);
			
			String data = "";
			
			// 검색 구분
			if (inVO.getSearchDiv() == 10) {
				data = vo.getReseNum();
			} else if (inVO.getSearchDiv() == 20) {
				data = vo.getReseConId();
			} else if (inVO.getSearchDiv() == 30) {
				data = vo.getReseBusId();
			}
			
			// SQL like 효과
			if (data.matches(".*" + inVO.getSearchWord() + ".*")) {
				list.add(vo);
			}
		} // --- for i
		
		return list;
	} // --- doSelectList

} // --- class
