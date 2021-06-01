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
import VO.ConsumerVO;
import VO.SearchVO;

public class ConsumerDao implements WorkStd {
	// -------------------------------------------- 멤버 변수 영역
	// --------------------------------------------
	// Logger
	private final Logger LOG = Logger.getLogger(ConsumerDao.class);
	// 소비자 data 저장 Path
	private String consumerDataFilePath = "";
	// 시스템 환경 설정
	private static Properties properties;
	// 파일 -> List 변환
	private List<ConsumerVO> consumerList;

	// -------------------------------------------- 멤버 변수 영역
	// --------------------------------------------
	// 생성자 초기화
	public ConsumerDao() {
		LOG.debug("= ConsumerDao() =");
		// Properties : 환경설정 set
		properties = EStringUtil.readProperties("dev");
		consumerDataFilePath = properties.getProperty("consumerPath");
		consumerList = (List<ConsumerVO>) doReadFile(consumerDataFilePath);
	}

	// 파일 저장
	@Override
	public int doSaveFile(List<? extends DTO> list) {
		// -----------------------------------------------------------------------------
		// 1. List를 읽는다
		// 2. 파일을 읽는다
		// 3. list -> ConsumerVO를 추출한다
		// 4. ConsumerVO에서 데이터를 꺼내 파일에 기록한다
		// 5. count를 반환한다.
		// -----------------------------------------------------------------------------
		int cnt = 0;
		// 2. 파일 읽기
		File file = new File(consumerDataFilePath);
		FileWriter writer = null;
		BufferedWriter bw = null;
		try {
			writer = new FileWriter(file);
			bw = new BufferedWriter(writer);
			for (int i = 0; i < list.size(); i++) {
				// 3. List -> ConsumerVO 추출
				ConsumerVO vo = (ConsumerVO) list.get(i);
				StringBuffer sb = new StringBuffer(100);
				String delim = ",";
				// v01 = new ConsumerVO("아이디01", "비밀번호01", "이름01", "010-1234-5678",
				// "2021/01/2122:00:01");
				sb.append(vo.getConId() + delim);
				sb.append(vo.getConPw() + delim);
				sb.append(vo.getConName() + delim);
				sb.append(vo.getConPhone() + delim);
				sb.append(vo.getConDate());
				if ((i + 1) != list.size()) {
					sb.append("\n");
				}
				// LOG.debug("sb : " + sb.toString());
				// 등록 건수 증가
				cnt++;
				// 4. 파일에 기록
				bw.write(sb.toString());
			} // --- for i
		} catch (IOException e) {
			LOG.debug("= IOException = " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		LOG.debug("파일에 기록 건수 : " + cnt);
		// 5. count 반환
		return cnt;
	} // --- doSaveFile
		// 파일 읽기
		// consumer.csv 파일 읽어 List로 변환

	@Override
	public List<? extends DTO> doReadFile(String path) {
		// -----------------------------------------------------------------------------
		// 1. 파일을 읽는다
		// 2. 읽은 파일 정보를 ConsumerVO에 할당한다
		// 3. List에 추가한다
		// 4. List를 반환한다
		// -----------------------------------------------------------------------------
		List<ConsumerVO> list = new ArrayList<ConsumerVO>();
		// 1. 파일 읽기
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			String data = "";
			while ((data = br.readLine()) != null) {
				ConsumerVO vo = null;
				// data 공백 제거
				data = data.trim();
				// data 존재 확인
				if ("".equals(data) || data.length() == 0) {
					continue;
				}
				// data to List : 파일에서 읽은 데이터를 List로 변환
				String[] consumerArray = data.split(","); // ","(쉼표) 단위로 데이터 구분
				// 2. 읽은 파일 정보를 ConsumerVo에 할당
				vo = new ConsumerVO(consumerArray[0], consumerArray[1], consumerArray[2], consumerArray[3],
						consumerArray[4]);
				// 3. List에 추가
				list.add(vo);
			} // --- while
			for (ConsumerVO vo : list) {
				// LOG.debug("list : " + vo);
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
		return list;
	} // doReadFile
		// 소비자 ID 중복 체크
		// ID 중복 : flag = 1, ID 중복 X : flag = 0

	public int checkConsumerId(ConsumerVO param) {
		// -----------------------------------------------------------------------------
		// 1. List에 ConsumerID가 있는지 찾는다
		// 1.1 List를 반복문 돌린다
		// 2. param의 소비자 ID와 List의 소비자 ID를 비교한다
		// 2.1 같으면 flag = 1
		// -----------------------------------------------------------------------------
		int flag = 0;
		for (int i = 0; i < consumerList.size(); i++) {
			ConsumerVO vsVO = consumerList.get(i);
			if (param.getConId().equals(vsVO.getConId())) {
				flag = 1;
				break;
			}
		}
		return flag;
	} // --- checkConsumerId
		// 단건 등록 : 회원가입
		// 회원가입 실패 : flag = 0
		// 회원가입 성공 : flag = 1
		// 회원가입 실패 -> 이미 해당 ID 존재 : flag = 2

	@Override
	public int doSave(DTO obj) {
		// -----------------------------------------------------------------------------
		// 1. param을 읽는다
		// 2. 소비자 ID 중복체크
		// 3. List에 기록
		// 4. 파일에 저장
		// -----------------------------------------------------------------------------
		int flag = 0;
		// 1. param 읽기
		ConsumerVO inVO = (ConsumerVO) obj;
		// 2. 소비자 ID 중복체크
		int idCheck = checkConsumerId(inVO);
		if (idCheck == 1) {
			flag = 2;
			return flag;
		}
		// 3. List에 기록
		boolean addFlag = consumerList.add(inVO);
		// LOG.debug("addFlag = " + addFlag);
		flag = (addFlag == true) ? 1 : 0;
		// 4. 파일에 저장
		int saveCnt = doSaveFile(consumerList);
		// LOG.debug("saveCnt = " + saveCnt);
		// LOG.debug("flag = " + flag);
		return flag;
	} // --- doSave
		// 소비자 회원 로그인
		// 로그인 실패 -> 비밀번호가 틀렸습니다 : flag = 1
		// 로그인 성공 -> 로그인 성공 : flag = 2
		// 로그인 실패 -> 가입되지 않은 ID입니다 : flag = 0

	public int doLogIn(ConsumerVO param) {
		// -----------------------------------------------------------------------------
		// 1. List에 BusinessID가 있는지 찾는다
		// 1.1 List를 반복문 돌린다
		// 2. param의 소비자 ID와 List의 소비자 ID를 비교한다
		// 2.1 같으면 flag = 1
		// 3. param의 소비자 PW와 List의 소비자 PW를 비교한다
		// 3.1 같으면 flag = 2
		// 4. pram의 소비자 ID가 List의 소비자 ID에 없을 경우 flag = 3
		// -----------------------------------------------------------------------------
		int flag = 0;
		for (int i = 0; i < consumerList.size(); i++) {
			ConsumerVO vsVO = consumerList.get(i);
			if (param.getConId().equals(vsVO.getConId())) {
				flag = 1;
				if (param.getConPw().equals(vsVO.getConPw())) {
					flag = 2;
					break;
				}
			}
		}
		return flag;
	} // --- doLogIn
		// 소비자 회원 정보 수정
		// 소비자 회원 정보 수정 성공 : flag = 2
		// 소비자 회원 정보 수정 실패 : flag = 0(등록된 ID없음)
		// 소비자 회원 정보 수정 실패 : flag = 1

	@Override
	public int doUpdate(DTO obj) {
		// ID를 통해 수정 데이터 확인
		// 삭제 후 등록하는 방식
		int flag = 0;
		// param 읽기
		ConsumerVO inVO = (ConsumerVO) obj;
		// id 존재 여부 확인
		if ((checkConsumerId(inVO)) == 1) {
			// 삭제
			flag = doDelete(inVO);
			if (flag == 1) {
				// 등록
				flag += doSave(inVO);
			}
		}
		return flag;
	} // --- doUpdate
		// 소비자 회원 정보 삭제
		// 소비자 회원 정보 삭제 성공 : flag = 1
		// 소비자 회원 정보 삭제 실패 : flag = 0
		// 소비자 회원 정보 저장 실패 : flag = 20

	@Override
	public int doDelete(DTO obj) {
		// -----------------------------------------------------------------------------
		// 1. param 데이터를 읽는다
		// 2. List를 반복문으로 처리
		// 2.1 삭제할 데이터를 찾는다
		// 2.2 찾았으면 삭제한다
		// 3. 데이터를 삭제 후 List를 파일에 기록한다
		// -----------------------------------------------------------------------------
		int flag = 0;
		// 1. param 데이터 읽기
		ConsumerVO inVO = (ConsumerVO) obj;
		// 삭제 전 데이터 건수
		int consumerBeforeDelcnt = consumerList.size();
		// 2. List 삭제 : 뒤에서부터
		for (int i = consumerList.size() - 1; i >= 0; i--) {
			ConsumerVO vo = consumerList.get(i);
			// 2.1 삭제할 데이터 찾기
			if (inVO.getConId().equals(vo.getConId())) {
				// 2.2 데이터 삭제
				consumerList.remove(i);
				flag = 1;
				break;
			}
		} // --- for i
			// 1(삭제 성공) / 0(삭제 실패) / 20(저장 실패)
		if (flag == 1) {
			// 3. 데이터 삭제 후 List를 파일에 기록
			int saveCnt = this.doSaveFile(consumerList);
			// 파일 저장 실패
			if (consumerBeforeDelcnt != (saveCnt + 1)) {
				flag = 20;
			}
		}
		return flag;
	} // --- doDelete
		// 단건 조회(ID 조회)

	@Override
	public DTO doSelectOne(DTO obj) {
		// -----------------------------------------------------------------------------
		// 1. param을 읽는다
		// 2. List에서 param을 찾는다
		// 3. 존재하면 ConsumerVO 반환
		// -----------------------------------------------------------------------------
		ConsumerVO outVO = null;
		// 1. param 읽기
		ConsumerVO inVO = (ConsumerVO) obj;
		for (int i = 0; i < this.consumerList.size(); i++) {
			ConsumerVO vo = consumerList.get(i);
			// 2. List에서 param 찾기
			if (inVO.getConId().equals(vo.getConId())) {
				outVO = vo;
				break;
			}
		} // --- for i
			// 3. ConsumerVO 반환
		return outVO;
	} // --- doSelectOne
		// 목록 조회 : ID, 이름으로 List 조회

	@Override
	public List<ConsumerVO> doSelectList(DTO obj) {
		// -----------------------------------------------------------------------------
		// Id : 10
		// 이름 : 20
		// 1. 검색 데이터 저장
		// 2. param 읽기
		// 3. csv 파일 데이터와 param 비교
		// -----------------------------------------------------------------------------
		// 1. 검색 데이터 저장
		List<ConsumerVO> list = new ArrayList<ConsumerVO>();
		// 2. param 읽기
		SearchVO inVO = (SearchVO) obj;
		// LOG.debug("param : " + inVO);
		for (int i = 0; i < consumerList.size(); i++) {
			ConsumerVO vo = consumerList.get(i);
			String data = "";
			// 검색 구분
			if (inVO.getSearchDiv() == 10) {
				data = vo.getConId();
			} else if (inVO.getSearchDiv() == 20) {
				data = vo.getConName();
			}
			// SQL like 효과
			// like "%" || 제목 || "%"
			if (data.matches(".*" + inVO.getSearchWord() + ".*")) {
				list.add(vo);
			}
		} // --- for i
		return list;
	} // --- doSelectList
} // --- class