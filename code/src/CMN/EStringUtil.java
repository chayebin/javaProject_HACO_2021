package CMN;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

import VO.MenuVO;
import VO.ReservationVO;


public class EStringUtil {
	 
	public static ReservationVO param = new ReservationVO();
	
	public static MenuVO param02 = new MenuVO();
	
	public static String getPK(String format) {
		return formatDate(format) + getUUID();
	} // --- get PK

	public static String getUUID() {
		String uuidStr = "";
		UUID uuId = UUID.randomUUID();
		// uuid 생성, "-" 구분 기호 제거
		uuidStr = uuId.toString().replaceAll("-", "");
		return uuidStr;
	} // --- getUUID

	public static String formatDate(String format) {
		// format이 null 또는 "" 이면 기본값 : yyyy/MM/dd HH:mm:ss
		if (Objects.equals(format, "")) {
			format = "yyyy/MM/dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	} // --- formatDate

	public static Properties readProperties(String param) {
		String propPath = "haco_dev.properties";
		Properties properties = new Properties();
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(propPath);
			bis = new BufferedInputStream(fis);
			// 파일 read
			properties.load(bis);
			System.out.println("version : " + properties.getProperty("version"));
			System.out.println("writer : " + properties.getProperty("writer"));
			System.out
					.println("======================================================================================");
			System.out.println("consumerPath : " + properties.getProperty("consumerPath"));
			System.out.println("businessPath : " + properties.getProperty("businessPath"));
			System.out.println("reservationPath : " + properties.getProperty("reservationPath"));
			System.out.println("menuPath : " + properties.getProperty("menuPath"));
			System.out
					.println("======================================================================================");
		} catch (IOException e) {
			System.out.println("=================================");
			System.out.println("=========== IOException ============" + e.getMessage());
			System.out.println("=================================");
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("파일 Read : " + propPath);
		return properties;
	} // --- readProperties
} // --- EStringUtil