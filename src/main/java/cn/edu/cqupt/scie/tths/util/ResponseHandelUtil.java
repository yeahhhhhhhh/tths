package cn.edu.cqupt.scie.tths.util;

import cn.edu.cqupt.scie.tths.constant.StatusCodeConstant;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;

/**
 * Created by why on 2017/3/12.
 */
public class ResponseHandelUtil {
    public static ResponseJson handleIntCondition(int expectResult, int actualResult, StatusCodeConstant errorCode){
        if(expectResult == actualResult)
            return new ResponseJson(StatusCodeConstant.OK);
        else
            return new ResponseJson(errorCode);
    }
}
