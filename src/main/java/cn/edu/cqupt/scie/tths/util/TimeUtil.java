/**
 * 
 */
package cn.edu.cqupt.scie.tths.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */

public class TimeUtil {
    public static String getNowTime() {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	return sdf.format(new Date());
    }
    
    public static Long getTimestamp() {
	return System.currentTimeMillis()/1000;
    }
}
