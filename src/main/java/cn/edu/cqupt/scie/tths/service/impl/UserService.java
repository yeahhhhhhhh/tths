package cn.edu.cqupt.scie.tths.service.impl;

import cn.edu.cqupt.scie.tths.constant.StatusCodeConstant;
import cn.edu.cqupt.scie.tths.dao.IUserDao;
import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.PageJson;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.service.IUserService;
import cn.edu.cqupt.scie.tths.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by why on 2017/3/23.
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public ResponseJson findTeachers(PageJson pageJson,String teacher_title,String teacher_job,String username) {
        if(username != null)
            username = "%"+username+"%";
        //分页查询教师基本信息
        List<UserModel>teacherList = userDao.findTeachers((pageJson.getNowPage()-1)*pageJson.getListCount(),pageJson.getListCount(),teacher_title,teacher_job,username);
        //查询教师总人数
        int teacherCount = userDao.findTeachersCount(teacher_title,teacher_job,username);
        //计算总页数
        int pageCount = teacherCount % pageJson.getListCount() == 0 ? teacherCount / pageJson.getListCount() : (teacherCount / pageJson.getListCount()) + 1;
        //将信息存入pageJson
        pageJson.setAllCount(teacherCount);
        pageJson.setPageCount(pageCount);
        pageJson.setPageList(teacherList);

        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(pageJson);
        return responseJson;
    }

    @Override
    public ResponseJson login(UserModel userModel, HttpServletRequest request) {
        UserModel userByUsername = userDao.findUserByUsername(userModel);
        if(userByUsername == null){
            return new ResponseJson(StatusCodeConstant.USER_NOT_EXIST);
        }
        String passwordMD5 = MD5Util.MD5(userModel.getPassword(),"");
        if(!passwordMD5.equalsIgnoreCase(userByUsername.getPassword())){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        request.getSession().setAttribute("uid",userByUsername.getUid());
        userByUsername.setPassword("");
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(userByUsername);
        return responseJson;
    }

    @Override
    public ResponseJson changeInfo(UserModel userModel) {
        userDao.changeInfo(userModel);
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        return responseJson;
    }

    @Override
    public ResponseJson logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("uid") != null && !"".equals(session.getAttribute("uid"))){
            session.invalidate();
            return new ResponseJson(StatusCodeConstant.OK);
        }
        return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
    }
}
