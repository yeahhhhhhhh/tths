package cn.edu.cqupt.scie.tths.service.impl;

import cn.edu.cqupt.scie.tths.constant.AddOrDelete;
import cn.edu.cqupt.scie.tths.constant.ConfigConstant;
import cn.edu.cqupt.scie.tths.constant.StatusCodeConstant;
import cn.edu.cqupt.scie.tths.dao.*;
import cn.edu.cqupt.scie.tths.model.AdminModel;
import cn.edu.cqupt.scie.tths.model.FileModel;
import cn.edu.cqupt.scie.tths.model.MsgModel;
import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.PageJson;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.service.IAdminService;
import cn.edu.cqupt.scie.tths.util.ChineseToEnglish;
import cn.edu.cqupt.scie.tths.util.MD5Util;
import cn.edu.cqupt.scie.tths.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    private IArticleDao articleDao;

    @Autowired
    private IFileDao fileDao;

    @Autowired
    private IMsgDao msgDao;

    @Override
    public ResponseJson login(AdminModel adminModel, HttpServletRequest request, String valistr) {
        if(!valistr.equals((String) request.getSession().getAttribute("valistr"))){
            return new ResponseJson(StatusCodeConstant.VALICODE_ERROR);
        }
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
        ResponseJson responseJson;
        //获取教师名字
        String[] teacherNames = userModel.getUsername().split(",");
        userModel.setPassword(MD5Util.MD5("123456",""));
        //已存在老师
        List<String> existedTeachers = new ArrayList<String>();
        existedTeachers.add("以下老师已存在或邮箱已被该教师占用");
        for(String name : teacherNames){
            //注意先获取email，否则覆盖name
            String email = name.split("-")[1];
            name = name.split("-")[0];
            if(name != null && !"".equals(name) && email != null && !"".equals(email)){
                userModel.setEmail(email);
                //查看email是否重复
                if(userDao.findUserByEmail(userModel) != null){
                    existedTeachers.add(name+":"+email);
                    continue;
                }
//                if(userDao.findTeacherByUsername(name) != null){
//                    existedTeachers.add(name);
//                    continue;
//                }
                userModel.setUsername(name);
                userModel.setPyName(ChineseToEnglish.getPingYin(name));
                userModel.setEmail(email);
                adminDao.addNewTeachers(userModel);
            }else{
                return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
            }
        }
        responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(existedTeachers);
        if(existedTeachers.size() != 1){
            responseJson.setCode(1009);
            responseJson.setMessage("添加未完全成功");
        }
        return responseJson;
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
                    //删除老师
                    userDao.forceDeleteTeacherByUsername(username);
                    //删除老师文章
                    articleDao.deleteArticleByUid(userModel.getUid());
                    //删除文章类型
                    articleDao.deleteCategoryByUid(userModel.getUid());
                    //查找该教师上传文件的信息
                    List<FileModel>fileModelList = fileDao.findFileList(userModel.getUid());
                    //删除教师文件
                    if(fileModelList.size() > 0){
                        deleteFiles(fileModelList);
                    }
                    //从数据库中删除文件信息
                    fileDao.deleteFilesByUid(userModel.getUid());
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

    @Override
    public ResponseJson coverJobandTitle(String typeName, String newType) {
        if(typeName.equals(ConfigConstant.TEACHER_JOB.getConfig())){
            adminDao.coverJob(newType);
        }else if(typeName.equals(ConfigConstant.TEACHER_TITLE.getConfig())){
            adminDao.coverTitle(newType);
        }else{
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        return new ResponseJson(StatusCodeConstant.OK);
    }

    @Override
    public ResponseJson changeAdminEmail(String email) {
        adminDao.changeAdminEmail(email);
        return new ResponseJson(StatusCodeConstant.OK);
    }

    @Override
    public ResponseJson findMsg(PageJson pageJson) {
        int begin = (pageJson.getNowPage()-1)*pageJson.getListCount();
        //分页查找留言
        List<MsgModel>msgModelList = msgDao.findMsgList(begin,pageJson.getListCount());
        //查找留言总数
        int allCount = msgDao.findMsgCount();
        //计算总页数
        int pageCount = allCount % pageJson.getListCount() == 0 ? allCount / pageJson.getListCount() : allCount / pageJson.getListCount() + 1;
        //封装会bean
        pageJson.setAllCount(allCount);
        pageJson.setPageCount(pageCount);
        pageJson.setPageList(msgModelList);
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(pageJson);
        return responseJson;
    }

    @Override
    public ResponseJson deleteTeachers2(int uid, String type, String addOrDelete) {
        //判断是否为空
        if(uid == 0 || type == null || "".equals(type) || addOrDelete == null || "".equals(addOrDelete)){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        //恢复教师
        if(addOrDelete.equals(AddOrDelete.ADD.getAddOrDeleteStr())){
            userDao.changeStatusByUidToAdd(uid);
        }
        //删除教师
        if(addOrDelete.equals(AddOrDelete.DELETE.getAddOrDeleteStr())){
            //从数据库全部删除
            if(type.equals(ConfigConstant.FORCE.getConfig())){
                //为了避免误删，先判断该教师是否离职，即status是否等于0
                UserModel userModel = userDao.findUserByUid(uid);
                if(userModel.getStatus() == 1){
                    return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
                }else if(userModel.getStatus() == 0){
                    //删除老师
                    userDao.forceDeleteTeacherByUid(uid);
                    //删除老师文章
                    articleDao.deleteArticleByUid(userModel.getUid());
                    //删除文章类型
                    articleDao.deleteCategoryByUid(userModel.getUid());
                    //查找该教师上传文件的信息
                    List<FileModel>fileModelList = fileDao.findFileList(userModel.getUid());
                    //删除教师文件
                    if(fileModelList.size() > 0){
                        deleteFiles(fileModelList);
                    }
                    //从数据库中删除文件信息
                    fileDao.deleteFilesByUid(userModel.getUid());
                }
                //教师离职
            }else if(type.equals(ConfigConstant.LEAVE.getConfig())){
                userDao.changeStatusByUidToDelete(uid);
            }
        }
        return new ResponseJson(StatusCodeConstant.OK);
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
    private void deleteFiles(List<FileModel> fileModelList){
        //获取当前路径
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        //循环删除文件
        for(FileModel fileModel : fileModelList){
            //将日期格式转成yyyy-MM-dd
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(fileModel.getUploadTime());
            //拼接路径
            String path = servletContext.getRealPath("uploads")+"/"+date+"/"+fileModel.getRealName();
            File file = new File(path);
            if(file.exists()){
                file.delete();
            }
        }
    }

}
