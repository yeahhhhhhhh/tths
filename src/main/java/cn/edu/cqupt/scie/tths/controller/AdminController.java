package cn.edu.cqupt.scie.tths.controller;

import cn.edu.cqupt.scie.tths.model.AdminModel;
import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.PageJson;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by why on 2017/3/30.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;



    /**
     * 添加新教师
     * @param userModel
     * @return
     */
    @RequestMapping(value = "/addNewTeachers")
    public @ResponseBody ResponseJson addNewTeachers(UserModel userModel){
        return adminService.addNewTeachers(userModel);
    }

    /**
     * 删除恢复教师
     * @param username
     * @param type
     * @param addOrDelete
     * @return
     */
    @RequestMapping(value = "/deleteTeachers")
    public @ResponseBody ResponseJson deleteTeachers(String username,String type,String addOrDelete){
        return adminService.deleteTeachers(username,type,addOrDelete);
    }

    /**
     * 添加删除教师job和title
     * @param typeName
     * @param newType
     * @param addOrDelete
     * @return
     */
    @RequestMapping(value = "/changeTeacherType")
    public @ResponseBody ResponseJson changeTeacherType(String typeName,String newType,String addOrDelete){
        return adminService.changeTeacherType(typeName,newType,addOrDelete);
    }

    @RequestMapping(value = "/leftTeachers")
    public @ResponseBody ResponseJson leftTeachers(PageJson pageJson,String username){
        return adminService.findLeftTeachers(pageJson,username);
    }
}
