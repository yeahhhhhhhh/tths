package cn.edu.cqupt.scie.tths.util;

import cn.edu.cqupt.scie.tths.constant.StatusCodeConstant;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.model.page.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static ResponseJson listHandel (List<?> list , Page page){
        Map<String , Object> map = new HashMap<>();
        map.put("list",list);
        map.put("page",page);
        ResponseJson responseJson =new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(map);
        return responseJson;
    }

    public static ResponseJson validateHandel(BindingResult result){
        List<ObjectError>objectErrors = result.getAllErrors();
        List<String> msgList = new ArrayList<String>();
        for(ObjectError objectError : objectErrors){
            msgList.add(objectError.getDefaultMessage());
        }
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        responseJson.setBody(msgList);
        return responseJson;
    }

}
