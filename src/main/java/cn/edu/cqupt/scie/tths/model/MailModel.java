package cn.edu.cqupt.scie.tths.model;

import cn.edu.cqupt.scie.tths.exception.MsgException;

/**
 * Created by why on 2017/4/3.
 */
public class MailModel {
    private String username;
    private String validatacode;//数字签名
    private long outdate;//过期时间
    private String newPassword;//新密码
    private String newPassword2;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getValidatacode() {
        return validatacode;
    }

    public void setValidatacode(String validatacode) {
        this.validatacode = validatacode;
    }

    public long getOutdate() {
        return outdate;
    }

    public void setOutdate(long outdate) {
        this.outdate = outdate;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    public void checkValues() throws MsgException {
        if(username == null || "".equals(username)){
            throw new MsgException("用户名不能为空!");
        }
        if(validatacode == null || "".equals(validatacode)){
            throw new MsgException("数字签名不能为空!");
        }
        if(newPassword == null || "".equals(newPassword2) || newPassword2 == null || "".equals(newPassword2)){
            throw new MsgException("密码不能为空!");
        }
        if(!newPassword.equals(newPassword2)){
            throw new MsgException("两次密码不一致!");
        }
    }
}
