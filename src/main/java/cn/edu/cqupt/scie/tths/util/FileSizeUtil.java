package cn.edu.cqupt.scie.tths.util;

import java.math.BigDecimal;

/**
 * Created by why on 2017/4/9.
 */
public class FileSizeUtil {

    public static String convertFileSize(int length){
        int kb = 1024;
        int mb = kb * 1024;
        if(length >= mb){
            BigDecimal bd = new BigDecimal((float)length/mb).setScale(2,BigDecimal.ROUND_HALF_UP);
            return bd + " MB";
        }else if(length >= kb && length < mb){
            BigDecimal bd = new BigDecimal((float)length/kb).setScale(2,BigDecimal.ROUND_HALF_UP);
            return bd + " KB";
        }else if(length < kb && length > 0){
            return length + " B";
        }else{
            return null;
        }
    }
}
