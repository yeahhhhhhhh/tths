package cn.edu.cqupt.scie.tths.service;

import cn.edu.cqupt.scie.tths.model.MailModel;
import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.PageJson;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by why on 2017/3/23.
 */
public interface IUserService {
    ResponseJson findTeachers(PageJson userModel, String teacherTitle, String teacher_title, String teacher_job,String py_name);

    ResponseJson login(UserModel userModel, HttpServletRequest request, String valistr);

    ResponseJson changeInfo(UserModel userModel);

    ResponseJson logout(HttpServletRequest request);

    ResponseJson findTeacherInfo(int uid);

    ResponseJson changePassword(UserModel userModel);

    ResponseJson valiTeacher(String username, HttpServletRequest request, String valistr);

    ResponseJson sendMail(String username, String email, HttpServletRequest request);

    ResponseJson resetPassword(MailModel mailModel);

    ResponseJson findTeacherJob(String typeName);

    ResponseJson findTeacherJobAll();

    ResponseJson contactAdmin(HttpServletRequest request, String valistr, String msg);

    ResponseJson emailLogin(UserModel userModel, HttpServletRequest request, String valistr);

    ResponseJson userEmail();
}
