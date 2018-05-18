package cn.edu.cqupt.scie.tths.model;

import cn.edu.cqupt.scie.tths.model.group.ChangePasswordGroup;
import cn.edu.cqupt.scie.tths.model.group.EmailLoginGroup;
import cn.edu.cqupt.scie.tths.model.group.LoginGroup;
import cn.edu.cqupt.scie.tths.model.group.UpdateGroup;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by why on 2017/3/23.
 */
public class UserModel {
    private int uid;
    @NotEmpty(message = "用户名不能为空！",groups = {LoginGroup.class})
    private String username;
    private String pyName;
    @NotEmpty(message = "密码不能为空！",groups = {LoginGroup.class,ChangePasswordGroup.class,EmailLoginGroup.class})
    private String password;
    @NotEmpty(message = "用户名不能为空！",groups = {EmailLoginGroup.class})
    @Email(message = "邮箱格式不正确！",groups = {UpdateGroup.class})
    private String email;
    private String number;
    private String avator;
    private String teacher_title;
    private String teacher_job;
    private String teacher_job2;
    private String postal_address;
    private String achievement;
    private String resume;
    private String college;
    private Integer status;
    private String role;
    private int loginCount;
    @Length(min = 6,max = 30,message = "新密码必须在6-30位",groups = {ChangePasswordGroup.class})
    private String newPassword;
    @Length(min = 6,max = 30,message = "确认密码必须在6-30位",groups = {ChangePasswordGroup.class})
    private String newPassword2;

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

    public String getPyName() {
        return pyName;
    }

    public void setPyName(String pyName) {
        this.pyName = pyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getTeacher_title() {
        return teacher_title;
    }

    public void setTeacher_title(String teacher_title) {
        this.teacher_title = teacher_title;
    }

    public String getTeacher_job() {
        return teacher_job;
    }

    public void setTeacher_job(String teacher_job) {
        this.teacher_job = teacher_job;
    }

    public String getTeacher_job2() {
        return teacher_job2;
    }

    public void setTeacher_job2(String teacher_job2) {
        this.teacher_job2 = teacher_job2;
    }

    public String getPostal_address() {
        return postal_address;
    }

    public void setPostal_address(String postal_address) {
        this.postal_address = postal_address;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }
}
