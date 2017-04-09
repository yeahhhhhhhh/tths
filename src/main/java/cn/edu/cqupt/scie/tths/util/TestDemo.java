package cn.edu.cqupt.scie.tths.util;


import cn.edu.cqupt.scie.tths.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by why on 2017/4/3.
 */
public class TestDemo {
    public static void main(String args[]) throws UnsupportedEncodingException {
//        long date = System.currentTimeMillis();
//        System.out.println("date:"+date);
//        Timestamp outDate = new Timestamp(System.currentTimeMillis()+30*60*1000);
//        System.out.println(outDate.getTime()/1000*1000);

//        String str = new String("������ʦ1".getBytes("iso-8859-1"),"utf-8");
//        System.out.println(str);
//        String encodingStr = URLEncoder.encode("","utf-8");
//        System.out.println(encodingStr);
//        String decodingStr = URLDecoder.decode(encodingStr,"utf-8");
//        System.out.println(decodingStr);
        String jobStr = "aaa,bbb,ccc,";
        jobStr = jobStr.substring(0,jobStr.lastIndexOf(","));
        System.out.println(jobStr);
    }
}
