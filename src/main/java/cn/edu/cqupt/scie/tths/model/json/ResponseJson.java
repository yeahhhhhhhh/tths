package cn.edu.cqupt.scie.tths.model.json;

import cn.edu.cqupt.scie.tths.constant.StatusCodeConstant;

/**
 * Created by why on 2017/2/21.
 */
public class ResponseJson {
    private Integer code;
    private String message;
    private Object body="";

    public ResponseJson() {
    }

    public ResponseJson(StatusCodeConstant statusCodeConstant){
        this.code = statusCodeConstant.getCode();
        this.message = statusCodeConstant.getMessage();
    }

    public ResponseJson(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
