package cn.edu.cqupt.scie.tths.service;

import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.PageJson;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by why on 2017/3/23.
 */
public interface IUserService {
    ResponseJson findTeachers(PageJson userModel,String teacher_title,String teacher_job,String username);

    ResponseJson login(UserModel userModel, HttpServletRequest request);

    ResponseJson changeInfo(UserModel userModel);

    ResponseJson logout(HttpServletRequest request);
}
