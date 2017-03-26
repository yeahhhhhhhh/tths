package cn.edu.cqupt.scie.tths.controller;

import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.PageJson;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
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

    @RequestMapping(value = "/findTeachers")
    public @ResponseBody ResponseJson findTeachers(PageJson pageJson){
        return userService.findTeachers(pageJson);
    }

    @RequestMapping(value = "/login")
    public @ResponseBody ResponseJson login(UserModel userModel, HttpServletRequest request){
        return userService.login(userModel,request);
    }

    @RequestMapping(value = "/changeInfo")
    public @ResponseBody ResponseJson changeInfo(UserModel userModel){
        return userService.changeInfo(userModel);
    }

    @RequestMapping(value = "/logout")
    public @ResponseBody ResponseJson logout(HttpServletRequest request){
        return userService.logout(request);
    }
}
