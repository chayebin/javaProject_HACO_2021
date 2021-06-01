package CMN;

import java.util.List;


/**
 * DAO 작업표준 
 * @author sist
 *
 */
public interface WorkStd {
	/**
	 *  list를 파일에 저장
	 */
	int doSaveFile(List<? extends DTO> list);
	
	/**
	 * file을 읽어 List로 변환
	 */
	 List<? extends DTO> doReadFile(String path);

    /**
     * 단건 등록 
     * @param obj
     * @return 1/0(1:성공,0:실패)
     */
    int doSave(DTO obj);

    /**
     * 수정 
     * @param obj
     * @return 1/0(1:성공,0:실패)
     */
    int doUpdate(DTO obj);	
    /**
     * 삭제
     * @param obj
     * @return 1/0(1:성공,0:실패)
     */
    int doDelete(DTO obj);
    
    /**
     * 단건 조회
     * @param obj
     * @return Object
     */
    DTO doSelectOne(DTO obj);
    /**
     * 목록 조회
     * @param obj
     * @return Vector<Object>
     */
    List<? extends DTO> doSelectList(DTO obj);
	
	
}
