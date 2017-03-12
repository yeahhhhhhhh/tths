package cn.edu.cqupt.scie.tths.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by why on 2017/3/12.
 */
public class IOUtil {
    public static void in2Out(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = is.read(buf)) > 0){
            os.write(buf,0,len);
        }
    }

    public static void close(InputStream is, OutputStream os){
        try {
            if(is != null) is.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            is = null;
        }

        try {
            if(os != null) {
                os.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            os = null;
        }
    }
}
