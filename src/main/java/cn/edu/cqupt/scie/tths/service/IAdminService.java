package cn.edu.cqupt.scie.tths.service;

import cn.edu.cqupt.scie.tths.model.AdminModel;
import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.PageJson;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by why on 2017/3/30.
 */
public interface IAdminService {
    ResponseJson login(AdminModel adminModel,HttpServletRequest request);

    ResponseJson addNewTeachers(UserModel userModel);

    ResponseJson changeTeacherType(String typeName, String newType, String addOrDelete);

    ResponseJson deleteTeachers(String username, String type, String addOrDelete);

    ResponseJson findLeftTeachers(PageJson pageJson, String username);
}
