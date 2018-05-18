package cn.edu.cqupt.scie.tths.model;

import cn.edu.cqupt.scie.tths.model.group.LoginGroup;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * Created by why on 2017/3/30.
 */
public class AdminModel {
    private int uid;
    @NotEmpty(message = "用户名不能为空!",groups = {LoginGroup.class})
    private String username;
    @NotEmpty(message = "密码不能为空!",groups = {LoginGroup.class})
    private String password;
    private String email;
    private String lastlogin_ip;
    private String lastlogin_time;
    private int loginCount;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastlogin_ip() {
        return lastlogin_ip;
    }

    public void setLastlogin_ip(String lastlogin_ip) {
        this.lastlogin_ip = lastlogin_ip;
    }

    public String getLastlogin_time() {
        return lastlogin_time;
    }

    public void setLastlogin_time(String lastlogin_time) {
        this.lastlogin_time = lastlogin_time;
    }
}
