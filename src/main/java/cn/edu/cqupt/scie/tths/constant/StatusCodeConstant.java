/**
 * 
 */
package cn.edu.cqupt.scie.tths.constant;

public enum StatusCodeConstant {
    OK(200,"OK"),//成功返回
    CREATED(201,"CREATED"),//新建或修改资源成功
    NO_CONTENT(204,"NO_CONTENT"),//删除数据成功
    INVALID_REQUEST(400,"INVALID_REQUEST"),//请求有误或者参数错误
    UNAUTHORIZED(401,"UNAUTHORIZED"),//没有权限
    FORBIDDEN(403,"FORBIDDEN"),//得到了权限但是被禁止
    NOT_FOUND(404,"NOT_FOUND"),//请求了不存在的资源
    NOT_ACCEPTABLE(406,"NOT_ACCEPTABLE"),//用户请求的格式不可得到
    GONE(410,"GONE"),//用户请求资源被永久删除
    UNPROCESABLE_ENTITY(422,"UNPROCESABLE_ENTITY"),//创建对象时验证错误
    INTERNAL_SERVER_ERROR(500,"INTERNAL_SERVER_ERROR"),//服务器内部错误
    USER_NOT_EXIST(1000,"用户不存在"),
    EMAIL_NOT_EXIST(1001,"该用户未填写邮箱"),
    EMAIL_EXISTED(1002,"该邮箱已被占用"),
    USER_UNLOGIN(1003,"用户未登录"),
    CODE_ERROR(1004,"数字签名错误"),
    TIME_OUT(1005,"签名已过期"),
    CONFIG_EXISTED(1006,"该配置已存在"),
    CONFIG_NOT_EXIST(1007,"该配置不存在"),
    VALICODE_ERROR(1008,"验证码错误"),
    NOTALLSUCCESS(1009,"添加未完全成功"),
    TOKEN_NOT_EXIST(2000,"token not exist"),
    JSON_PARSE_ERROR(4000,"JSON PARSE ERROR");
    private int code;
    private String message;
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private StatusCodeConstant(int code, String message) {
	this.code = code;
	this.message = message;
    }
}