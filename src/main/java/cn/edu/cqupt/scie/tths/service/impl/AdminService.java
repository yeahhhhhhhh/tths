package cn.edu.cqupt.scie.tths.service.impl;

import cn.edu.cqupt.scie.tths.constant.AddOrDelete;
import cn.edu.cqupt.scie.tths.constant.ConfigConstant;
import cn.edu.cqupt.scie.tths.constant.StatusCodeConstant;
import cn.edu.cqupt.scie.tths.dao.IAdminDao;
import cn.edu.cqupt.scie.tths.dao.IUserDao;
import cn.edu.cqupt.scie.tths.model.AdminModel;
import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.PageJson;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.service.IAdminService;
import cn.edu.cqupt.scie.tths.util.MD5Util;
import cn.edu.cqupt.scie.tths.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by why on 2017/3/30.
 */
@Transactional
@Service
public class AdminService implements IAdminService {

    @Autowired
    private IAdminDao adminDao;

    @Autowired
    private IUserDao userDao;

    @Override
    public ResponseJson login(AdminModel adminModel,HttpServletRequest request) {
        AdminModel adminByUsername = adminDao.findAdminByUsername(adminModel);
        if(adminByUsername == null)
            return new ResponseJson(StatusCodeConstant.USER_NOT_EXIST);
        String passwordMD5 = MD5Util.MD5(adminModel.getPassword(),"");
        if(!passwordMD5.equalsIgnoreCase(adminByUsername.getPassword()))
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        request.getSession().setAttribute("uid",adminByUsername.getUid());
        adminByUsername.setPassword("");
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(adminByUsername);

        //获取当前ip
        String lastlogin_ip = request.getRemoteAddr();
        adminByUsername.setLastlogin_ip(lastlogin_ip);
        //获取当前时间
        adminByUsername.setLastlogin_time(TimeUtil.getNowTime2());
        //添加登录日志
        adminDao.addLoginLog(adminByUsername);
        //添加访问次数
        adminDao.addLoginCount(adminByUsername);
        return responseJson;
    }

    @Override
    public ResponseJson addNewTeachers(UserModel userModel) {
        //获取教师名字
        String[] teacherNames = userModel.getUsername().split(",");
        userModel.setPassword(MD5Util.MD5("123456",""));
        for(String name : teacherNames){
            if(name != null && !"".equals(name)){
                userModel.setUsername(name);
                adminDao.addNewTeachers(userModel);
            }
        }
        return new ResponseJson(StatusCodeConstant.OK);
    }

    @Override
    public ResponseJson changeTeacherType(String typeName, String newType, String addOrDelete) {
        if(typeName == null || "".equals(typeName) || newType == null || "".equals(newType) || addOrDelete == null || "".equals(addOrDelete)){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        if(addOrDelete.equals(AddOrDelete.ADD.getAddOrDeleteStr())){
            if(typeName.equals(ConfigConstant.TEACHER_JOB.getConfig()) || typeName.equals(ConfigConstant.TEACHER_TITLE.getConfig())){
                //查询数据库原有的类型
                String[] jobs = findJobStr(typeName);
                if(jobs != null){
                    //要添加的类型，多个用逗号隔开
                    String[] newTypes = newType.split(",");
                    //判断是否重复
                    for(String job : jobs){
                        for(String newTyp : newTypes){
                            if(job.equals(newTyp)){
                                ResponseJson responseJson = new ResponseJson(StatusCodeConstant.CONFIG_EXISTED);
                                responseJson.setBody(newTyp);
                                return responseJson;
                            }
                        }
                    }
                    adminDao.addTeacherType(typeName,newType);
                }else {
                    return new ResponseJson(StatusCodeConstant.INTERNAL_SERVER_ERROR);
                }
            }else {
                return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
            }
        }else if(addOrDelete.equals(AddOrDelete.DELETE.getAddOrDeleteStr())){
            //数据库原有的类型用逗号隔开
            String[] jobs = findJobStr(typeName);
            if(jobs != null){
                //要删除的类型，多个用逗号隔开
                String[] newTypes = newType.split(",");
                String jobStr = "";
                for(String job : jobs){
                    for(String newTyp : newTypes){
                        if(!job.equals(newTyp)){
                            jobStr += job + ",";
                        }
                    }
                }
                jobStr = jobStr.substring(0,jobStr.lastIndexOf(","));
                if(jobStr.equals(userDao.findTeacherJob(typeName))){
                    return new ResponseJson(StatusCodeConstant.CONFIG_NOT_EXIST);
                }
                adminDao.deleteTeacherType(typeName,jobStr);
            }else {
                return new ResponseJson(StatusCodeConstant.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseJson(StatusCodeConstant.OK);
    }

    @Override
    public ResponseJson deleteTeachers(String username, String type, String addOrDelete) {
        //判断是否为空
        if(username == null || "".equals(username) || type == null || "".equals(type) || addOrDelete == null || "".equals(addOrDelete)){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        //恢复教师
        if(addOrDelete.equals(AddOrDelete.ADD.getAddOrDeleteStr())){
            userDao.changeStatusByAdd(username);
        }
        //删除教师
        if(addOrDelete.equals(AddOrDelete.DELETE.getAddOrDeleteStr())){
            //从数据库全部删除
            if(type.equals(ConfigConstant.FORCE.getConfig())){
                //为了避免误删，先判断该教师是否离职，即status是否等于0
                UserModel userModel = userDao.findTeacherByUsername(username);
                if(userModel.getStatus() == 1){
                    return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
                }else if(userModel.getStatus() == 0){
                    //这里应该还包含删除文章，现在不做
//                    userDao.forceDeleteTeacherByUsername(username);
                }
            //教师离职
            }else if(type.equals(ConfigConstant.LEAVE.getConfig())){
                userDao.changeStatusByDelete(username);
            }
        }
        return new ResponseJson(StatusCodeConstant.OK);
    }

    @Override
    public ResponseJson findLeftTeachers(PageJson pageJson, String username) {
        if(username != null)
            username = "%"+username+"%";
        //分页查询已辞职教师基本信息
        List<UserModel>userModelList = userDao.findLeftTeachers((pageJson.getNowPage()-1)*pageJson.getListCount(),pageJson.getListCount(),username);
        //查找已辞职教师人数
        int leftTeacherCount = userDao.findLeftTeachersCount(username);
        //计算总页数
        int pageCount = leftTeacherCount % pageJson.getListCount() == 0 ? leftTeacherCount / pageJson.getListCount() : (leftTeacherCount / pageJson.getListCount()) + 1;
        //将信息存入pageJson
        pageJson.setPageCount(pageCount);
        pageJson.setAllCount(leftTeacherCount);
        pageJson.setPageList(userModelList);

        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(pageJson);
        return responseJson;
    }


    private String[] findJobStr(String typeName){
        //查询数据库原有的类型
        String TeacherJob = userDao.findTeacherJob(typeName);
        if(TeacherJob != null && !"".equals(TeacherJob)){
            //数据库原有的类型用逗号隔开
            String[] jobs = TeacherJob.split(",");
            return jobs;
        }else {
            return null;
        }

    }
}
