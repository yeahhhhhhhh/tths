package cn.edu.cqupt.scie.tths.controller;

import cn.edu.cqupt.scie.tths.model.AdminModel;
import cn.edu.cqupt.scie.tths.model.MailModel;
import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.PageJson;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.service.IAdminService;
import cn.edu.cqupt.scie.tths.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by why on 2017/3/23.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IAdminService adminService;

    /**
     * 查找老师列表
     * @param pageJson
     * @param teacher_title
     * @param teacher_job
     * @param username
     * @return
     */
    @RequestMapping(value = "/findTeachers")
    public @ResponseBody ResponseJson findTeachers(PageJson pageJson,String teacher_title,String teacher_job,String username){
        return userService.findTeachers(pageJson,teacher_title,teacher_job,username);
    }

    /**
     * 查看老师详细信息
     * @param uid
     * @return
     */
    @RequestMapping(value = "/findTeacherInfo")
    public @ResponseBody ResponseJson findTeacherInfo(int uid){
        return userService.findTeacherInfo(uid);
    }

    /**
     * 教师登录
     * @param userModel
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    public @ResponseBody ResponseJson login(UserModel userModel, HttpServletRequest request){
        return userService.login(userModel,request);
    }
    /**
     * 管理员登录
     * @param adminModel
     * @param request
     * @return
     */
    @RequestMapping(value = "/adminLogin")
    public @ResponseBody ResponseJson login(AdminModel adminModel, HttpServletRequest request){
        return adminService.login(adminModel,request);
    }

    /**
     * 修改个人基本信息
     * @param userModel
     * @return
     */
    @RequestMapping(value = "/changeInfo")
    public @ResponseBody ResponseJson changeInfo(UserModel userModel){
        return userService.changeInfo(userModel);
    }

    /**
     * 注销
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    public @ResponseBody ResponseJson logout(HttpServletRequest request){
        return userService.logout(request);
    }

    /**
     * 利用原密码修改密码
     * @param userModel
     * @return
     */
    @RequestMapping(value = "/changePassword")
    public @ResponseBody ResponseJson changePassword(UserModel userModel){
        return userService.changePassword(userModel);
    }

    /**
     * 邮箱找回密码第一步：验证该用户是否存在且邮箱是否填写
     * @param username
     * @param request
     * @return
     */
    @RequestMapping(value = "/valiTeacher")
    public @ResponseBody ResponseJson valiTeacher(String username,HttpServletRequest request){
        return userService.valiTeacher(username,request);
    }

    /**
     * 邮箱找回密码第二步：发送邮件
     * @param username
     * @param email
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendMail")
    public @ResponseBody ResponseJson sendMail(String username,String email,HttpServletRequest request){
        return userService.sendMail(username,email,request);
    }

    /**
     * 邮箱找回密码第三步：重置密码
     * @param mailModel
     * @return
     */
    @RequestMapping(value = "/resetPassword")
    public @ResponseBody ResponseJson resetPassword(MailModel mailModel){
        return userService.resetPassword(mailModel);
    }

    /**
     * 查询教师job和title
     * @param typeName
     * @return
     */
    @RequestMapping(value = "/findTeacherType")
    public @ResponseBody ResponseJson findTeacherJob(String typeName){
        return userService.findTeacherJob(typeName);
    }

}
