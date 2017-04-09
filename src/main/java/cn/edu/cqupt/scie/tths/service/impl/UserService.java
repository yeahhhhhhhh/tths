package cn.edu.cqupt.scie.tths.service.impl;

import cn.edu.cqupt.scie.tths.constant.StatusCodeConstant;
import cn.edu.cqupt.scie.tths.dao.IUserDao;
import cn.edu.cqupt.scie.tths.exception.MsgException;
import cn.edu.cqupt.scie.tths.model.MailModel;
import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.PageJson;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.service.IUserService;
import cn.edu.cqupt.scie.tths.util.MD5Util;
import cn.edu.cqupt.scie.tths.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * Created by why on 2017/3/23.
 */
@Transactional
@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public ResponseJson findTeachers(PageJson pageJson,String teacher_title,String teacher_job,String username) {
        if(username != null || !"".equals(username))
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
        //增加登录次数
        userDao.addLoginCount(userByUsername.getUid());
        userByUsername.setLoginCount(userByUsername.getLoginCount()+1);
        responseJson.setBody(userByUsername);
        return responseJson;
    }

    @Override
    public ResponseJson changeInfo(UserModel userModel) {
        if(userModel.getEmail() != null || !"".equals(userModel.getEmail())){
            List<String>mailList = userDao.findEmails();
            if(mailList.contains(userModel.getEmail())){
                return new ResponseJson(StatusCodeConstant.EMAIL_EXISTED);
            }
        }
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

    @Override
    public ResponseJson findTeacherInfo(int uid) {
        ResponseJson responseJson = null;
        UserModel userModel = userDao.findTeacherInfo(uid);
        if(userModel != null){
            userModel.setPassword("");
            responseJson = new ResponseJson(StatusCodeConstant.OK);
            responseJson.setBody(userModel);
        }else {
            responseJson = new ResponseJson(StatusCodeConstant.USER_NOT_EXIST);
        }
        return responseJson;
    }

    @Override
    public ResponseJson changePassword(UserModel userModel) {
        UserModel userByUsername = userDao.findUserByUsername(userModel);
        if(userByUsername == null){
            return new ResponseJson(StatusCodeConstant.USER_NOT_EXIST);
        }
        String passwordMD5 = MD5Util.MD5(userModel.getPassword(),"");
        if(!passwordMD5.equalsIgnoreCase(userByUsername.getPassword())){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        if(!userModel.getNewPassword().equals(userModel.getNewPassword2()) || userModel.getNewPassword() == null){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        userByUsername.setNewPassword(MD5Util.MD5(userModel.getNewPassword(),""));
        int result = userDao.changePassword(userByUsername);
        if(result == 1){
            return new ResponseJson(StatusCodeConstant.OK);
        }else {
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
    }

    @Override
    public ResponseJson valiTeacher(String username, HttpServletRequest request) {
        if(username == null || "".equals(username))
            return new ResponseJson(StatusCodeConstant.USER_NOT_EXIST);
        UserModel userModel = userDao.findTeacherByUsername(username);
        if(userModel.getEmail() == null || "".equals(userModel.getEmail())){
            return new ResponseJson(StatusCodeConstant.EMAIL_NOT_EXIST);
        }
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(userModel);
        request.getSession().setAttribute("username",username);
        return responseJson;
    }

    @Override
    public ResponseJson sendMail(String username, String email, HttpServletRequest request) {
        String sessionUsername = (String) request.getSession().getAttribute("username");
        if(sessionUsername == null || "".equals(sessionUsername) || !sessionUsername.equals(username)){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        if(email == null || "".equals(email)){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        //根据用户名查找该用户email,判断email是否一致
        UserModel userModel = userDao.findTeacherByUsername(username);
        if(!userModel.getEmail().equals(email)){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }

        //30分钟后过期
        long date = System.currentTimeMillis()+30*60*1000;
        //生成key
        String key = username+"$"+date+"$"+UUID.randomUUID().toString();
        System.out.println("key:"+key);
        //数字签名
        String validatacode = MD5Util.MD5(key,"");
        System.out.println("validatacode:"+validatacode);

        MailModel mailModel = new MailModel();
        mailModel.setUsername(username);
        mailModel.setValidatacode(validatacode);
        mailModel.setOutdate(date);
        //将数字签名和过期时间存入数据库
        userDao.updateDigitalSignatureAndOutdate(mailModel);

        //生成URL
        String URL = null;
        try {
            URL = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/html/findbackPassword.html?validatacode="+validatacode+"&username="+ URLEncoder.encode(username,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("URL:"+URL);

        MailUtil.sendMail(username,email,URL);

        return new ResponseJson(StatusCodeConstant.OK);
    }

    @Override
    public ResponseJson resetPassword(MailModel mailModel) {
        try {
            mailModel.checkValues();
        } catch (MsgException e) {
            e.printStackTrace();
            return new ResponseJson(400,e.getMessage());
        }
        try {
            mailModel.setUsername(URLDecoder.decode(mailModel.getUsername(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MailModel db_mailModel = userDao.findMailModel(mailModel);
        if(!db_mailModel.getValidatacode().equals(mailModel.getValidatacode())){
            return new ResponseJson(StatusCodeConstant.CODE_ERROR);
        }
        //获取当前时间
        long nowDate = System.currentTimeMillis();
        long outDate = db_mailModel.getOutdate();
        if(outDate-nowDate <= 0){
            return new ResponseJson(StatusCodeConstant.TIME_OUT);
        }
        mailModel.setNewPassword(MD5Util.MD5(mailModel.getNewPassword(),""));
        //设置新密码
        userDao.resetPassword(mailModel);
        return new ResponseJson(StatusCodeConstant.OK);
    }

    @Override
    public ResponseJson findTeacherJob(String typeName) {
        String TeacherJob = userDao.findTeacherJob(typeName);
        if(typeName == null || "".equals(typeName)){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        if(TeacherJob == null || "".equals(TeacherJob)){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        String[] jobs = TeacherJob.split(",");
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(jobs);
        return responseJson;
    }


}
