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
import java.util.*;

/**
 * Created by why on 2017/3/23.
 */
@Transactional
@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public ResponseJson findTeachers(PageJson pageJson, String teacher_title, String teacher_job, String username,String py_name) {
        if(username != null || !"".equals(username))
            username = "%"+username+"%";
        if(py_name != null || !"".equals(py_name)){
            py_name = py_name.toLowerCase()+"%";
        }
        //分页查询教师基本信息
        List<UserModel>teacherList = userDao.findTeachers((pageJson.getNowPage()-1)*pageJson.getListCount(),pageJson.getListCount(),teacher_title,teacher_job,username,py_name);
        //查询教师总人数
        int teacherCount = userDao.findTeachersCount(teacher_title,teacher_job,username,py_name);
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
    public ResponseJson login(UserModel userModel, HttpServletRequest request, String valistr) {
        if(!valistr.equals((String) request.getSession().getAttribute("valistr"))){
            return new ResponseJson(StatusCodeConstant.VALICODE_ERROR);
        }
        //查询该教师名字是否重复
        int sameCount = userDao.findSameUsername(userModel);
        if(sameCount >= 2){
            ResponseJson responseJson2 = new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
            responseJson2.setBody("教师名字重复！请用邮箱登录！");
            return responseJson2;
        }
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
//            List<String>mailList = userDao.findEmails();
            List<String>mailListExceptUid = userDao.findEmailsExceptUid(userModel.getUid());
            if(mailListExceptUid.contains(userModel.getEmail())){
                ResponseJson responseJson = new ResponseJson(StatusCodeConstant.EMAIL_EXISTED);
                responseJson.setBody("该邮箱已被占用！");
                return responseJson;
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
    public ResponseJson valiTeacher(String username, HttpServletRequest request, String valistr) {
        if(!valistr.equals((String) request.getSession().getAttribute("valistr"))){
            return new ResponseJson(StatusCodeConstant.VALICODE_ERROR);
        }

        if(username == null || "".equals(username))
            return new ResponseJson(StatusCodeConstant.USER_NOT_EXIST);
        UserModel userModel = userDao.findTeacherByUsername(username);
        if(userModel != null){
            if(userModel.getEmail() == null || "".equals(userModel.getEmail())){
                return new ResponseJson(StatusCodeConstant.EMAIL_NOT_EXIST);
            }
        }else {
            return new ResponseJson(StatusCodeConstant.USER_NOT_EXIST);
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
        if(typeName == null || "".equals(typeName)){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        //查找job和title
        String TeacherJob = userDao.findTeacherJob(typeName);
        if(TeacherJob == null || "".equals(TeacherJob)){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        String[] jobs = TeacherJob.split(",");
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(jobs);
        return responseJson;
    }

    @Override
    public ResponseJson findTeacherJobAll() {
        String TeacherJob = userDao.findTeacherJob("teacher_job");
        String TeacherTitle = userDao.findTeacherJob("teacher_title");
        Map<String,String[]> teacher_job_Map = new HashMap<String,String[]>();
        Map<String,String[]> teacher_title_Map = new HashMap<String,String[]>();
        teacher_job_Map.put("teacher_job",TeacherJob.split(","));
        teacher_title_Map.put("teacher_title",TeacherTitle.split(","));
        List<Map> typeList = new ArrayList<Map>();
        typeList.add(teacher_job_Map);
        typeList.add(teacher_title_Map);
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(typeList);
        return responseJson;
    }

    @Override
    public ResponseJson contactAdmin(HttpServletRequest request, String valistr, String msg) {
        //验证验证码
        String valistrSession = (String) request.getSession().getAttribute("valistr");
        if(!valistrSession.equals(valistr)){
            return new ResponseJson(StatusCodeConstant.VALICODE_ERROR);
        }
        //查找管理员邮箱
        String adminEmail = userDao.findAdminEmail();
        System.out.println("adminEmail:"+adminEmail);
        if(adminEmail != null && !"".equals(adminEmail)){
            //发送邮件给管理员
            MailUtil.sendMailToContactAdmin(adminEmail,msg);
            //添加留言信息到数据库
            userDao.saveMsg(msg);
        }
        return new ResponseJson(StatusCodeConstant.OK);
    }

    @Override
    public ResponseJson emailLogin(UserModel userModel, HttpServletRequest request, String valistr) {
        if(!valistr.equals((String) request.getSession().getAttribute("valistr"))){
            return new ResponseJson(StatusCodeConstant.VALICODE_ERROR);
        }
        UserModel userByEmail = userDao.findUserByEmail(userModel);
        if(userByEmail == null){
            return new ResponseJson(StatusCodeConstant.USER_NOT_EXIST);
        }
        String passwordMD5 = MD5Util.MD5(userModel.getPassword(),"");
        if(!passwordMD5.equalsIgnoreCase(userByEmail.getPassword())){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        request.getSession().setAttribute("uid",userByEmail.getUid());
        userByEmail.setPassword("");
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        //增加登录次数
        userDao.addLoginCount(userByEmail.getUid());
        userByEmail.setLoginCount(userByEmail.getLoginCount()+1);
        responseJson.setBody(userByEmail);
        return responseJson;
    }

    @Override
    public ResponseJson userEmail() {
        int sum = 0;
        String a = "\"鲍宁海\"<baonh@cqupt.edu.cn>;\"柴蓉\"<chairong@cqupt.edu.cn>;\"曹建玲\"<caojl@cqupt.edu.cn>;\"曹傧\"<caobin@cqupt.edu.cn>;\"蔡丽\"<caili@cqupt.edu.cn>;\"陈昌川\"<chencc@cqupt.edu.cn>;\"陈发堂\"<chenft@cqupt.edu.cn>;\"陈莉\"<chenl@cqupt.edu.cn>;\"陈英\"<chenying@cqupt.edu.cn>;\"陈培然\"<chenpr@cqupt.edu.cn>;\"陈善学\"<chensx@cqupt.edu.cn>;\"陈文星\"<chenwx1@cqupt.edu.cn>;\"代少升\"<daiss@cqupt.edu.cn>;\"崔太平\"<cuitp@cqupt.edu.cn>;\"戴翠琴\"<daicq@cqupt.edu.cn>;\"邓炳光\"<dengbg@cqupt.edu.cn>;\"段洁\"<duanjie@cqupt.edu.cn>;\"范馨月\"<fanxy@cqupt.edu.cn>;\"高陈强\"<gaocq@cqupt.edu.cn>;\"甘臣权\"<gancq@cqupt.edu.cn>;\"龚璞\"<gongpu@cqupt.edu.cn>;\"郭晓金\"<guoxj@cqupt.edu.cn>;\"刘宏清\"<hongqingliu@cqupt.edu.cn>;\"何维\"<hewei@cqupt.edu.cn>;\"黄晓舸\"<huangxg@cqupt.edu.cn>;\"黄宏程\"<huanghc@cqupt.edu.cn>;\"黄明华\"<huangmh@cqupt.edu.cn>;\"黄胜\"<huangs@cqupt.edu.cn>;\"胡昊南\"<huhn@cqupt.edu.cn>;\"胡敏\"<humin@cqupt.edu.cn>;\"胡庆\"<huqing@cqupt.edu.cn>;\"景小荣\"<jingxr@cqupt.edu.cn>;\"蒋莹\"<jiangying@cqupt.edu.cn>;\"蒋青\"<jiangq@cqupt.edu.cn>;\"雷维嘉\"<leiwj@cqupt.edu.cn>;\"梁燕\"<liangyan@cqupt.edu.cn>;\"雷芳\"<leifang1@cqupt.edu.cn>;\"雷宏江\"<leihj@cqupt.edu.cn>;\"廖述平\"<liaosp@cqupt.edu.cn>;\"廖希\"<liaoxi@cqupt.edu.cn>;\"李国权\"<ligq@cqupt.edu.cn>;\"李玲霞\"<lilx@cqupt.edu.cn>;\"李季碧\"<lijb@cqupt.edu.cn>;\"李秋俊\"<liqj@cqupt.edu.cn>;\"李强\"<liqiang@cqupt.edu.cn>;\"刘楚川\"<liucc@cqupt.edu.cn>;\"刘畅\"<liuchang@cqupt.edu.cn>;\"刘景刚\"<liujg@cqupt.edu.cn>;\"刘焕淋\"<liuhl@cqupt.edu.cn>;\"刘劲松\"<liujs@cqupt.edu.cn>;\"刘科征\"<liukz@cqupt.edu.cn>;\"刘鸿\"<liuhong@cqupt.edu.cn>;\"刘亮\"<liuliang@cqupt.edu.cn>;\"刘媛妮\"<liuyn@cqupt.edu.cn>;\"刘乔寿\"<liuqs@cqupt.edu.cn>;\"刘裔\"<liuyi@cqupt.edu.cn>;\"刘小莉\"<liuxl@cqupt.edu.cn>;\"李小文\"<lixw@cqupt.edu.cn>;\"刘占军\"<liuzj@cqupt.edu.cn>;\"李兆玉\"<lizhaoyu@cqupt.edu.cn>;\"龙恳\"<longken@cqupt.edu.cn>;\"李永刚\"<lyg@cqupt.edu.cn>;\"罗文丰\"<luowf@cqupt.edu.cn>;\"罗忠涛\"<luozt@cqupt.edu.cn>;\"罗臻\"<luozhen@cqupt.edu.cn>;\"聂伟\"<niewei@cqupt.edu.cn>;\"明艳\"<mingyan@cqupt.edu.cn>;\"泮凯\"<pankai@cqupt.edu.cn>;\"裴二荣\"<peier@cqupt.edu.cn>;\"蒲巧林\"<puql@cqupt.edu.cn>;\"蒲旭敏\"<puxm@cqupt.edu.cn>;\"漆晶\"<qijing@cqupt.edu.cn>;\"师黎明\"<shilm@cqupt.edu.cn>;\"史朝翔\"<shicx@cqupt.edu.cn>;\"任智\"<renzhi@cqupt.edu.cn>;\"邵凯\"<shaokai@cqupt.edu.cn>;\"邵羽\"<shaoyu@cqupt.edu.cn>;\"申滨\"<shenbin@cqupt.edu.cn>;\"舒娜\"<shuna@cqupt.edu.cn>;\"宋铁成\"<songtc@cqupt.edu.cn>;\"唐建华\"<tangjh@cqupt.edu.cn>;\"唐红\"<tanghong@cqupt.edu.cn>;\"唐宏\"<tangh@cqupt.edu.cn>;\"腾欢\"<tenghuan@cqupt.edu.cn>;\"谭钦红\"<tanqh@cqupt.edu.cn>;\"唐伦\"<tangl@cqupt.edu.cn>;\"谭歆\"<tanxin@cqupt.edu.cn>;\"陶洋\"<taoyang@cqupt.edu.cn>;\"田增山\"<tianzs@cqupt.edu.cn>;\"王丹\"<wangdan@cqupt.edu.cn>;\"王华华\"<wanghh@cqupt.edu.cn>;\"王俊\"<wangjun@cqupt.edu.cn>;\"王洋\"<wangyang@cqupt.edu.cn>;\"王英\"<wangying@cqupt.edu.cn>;\"王汝言\"<wangry@cqupt.edu.cn>;\"王毅\"<wangyi81@cqupt.edu.cn>;\"王正强\"<wangzq@cqupt.edu.cn>;\"王永\"<wangyong@cqupt.edu.cn>;\"蔚承英\"<weicy@cqupt.edu.cn>;\"韦世红\"<weish@cqupt.edu.cn>;\"吴大鹏\"<wudp@cqupt.edu.cn>;\"王平\"<wp@cqupt.edu.cn>;\"武俊\"<wujun@cqupt.edu.cn>;\"吴坤君\"<wukj@cqupt.edu.cn>;\"向劲松\"<xiangjs@cqupt.edu.cn>;\"余翔\"<xiangyu@cqupt.edu.cn>;\"鲜永菊\"<xianyj@cqupt.edu.cn>;\"夏绪玖\"<xiaxj@cqupt.edu.cn>;\"熊炼\"<xionglian@cqupt.edu.cn>;\"谢良波\"<xielb@cqupt.edu.cn>;\"徐川\"<xuchuan@cqupt.edu.cn>;\"熊炫睿\"<xiongxr@cqupt.edu.cn>;\"徐鹏\"<xupeng@cqupt.edu.cn>;\"席镯轩\"<xizhen@cqupt.edu.cn>;\"徐文云\"<xuwy@cqupt.edu.cn>;\"徐勇军\"<xuyj@cqupt.edu.cn>;\"杨柳\"<yangliu@cqupt.edu.cn>;\"阳莉\"<yanglit@cqupt.edu.cn>;\"杨路\"<yanglu@cqupt.edu.cn>;\"阎英\"<yanying@cqupt.edu.cn>;\"杨茂斌\"<yangmb@cqupt.edu.cn>;\"杨晓波\"<yangxb@cqupt.edu.cn>;\"杨晓非\"<yangxf@cqupt.edu.cn>;\"杨英\"<yangying@cqupt.edu.cn>;\"叶志红\"<yezh@cqupt.edu.cn>;\"易琛\"<yichen@cqupt.edu.cn>;\"姚玉坤\"<yaoyk@cqupt.edu.cn>;\"殷茜\"<yinqian@cqupt.edu.cn>;\"张毅\"<yizhang@cqupt.edu.cn>;\"袁泉\"<yuanquan@cqupt.edu.cn>;\"于秀兰\"<yuxl@cqupt.edu.cn>;\"余跃\"<yuyue@cqupt.edu.cn>;\"张家波\"<zhangjb@cqupt.edu.cn>;\"张刚\"<zhanggang@cqupt.edu.cn>;\"张海波\"<zhanghb@cqupt.edu.cn>;\"曾帅\"<zengshuai@cqupt.edu.cn>;\"张勤\"<zhangqin@cqupt.edu.cn>;\"张盛峰\"<zhangsf@cqupt.edu.cn>;\"张天骐\"<zhangtq@cqupt.edu.cn>;\"张挺\"<zhangting@cqupt.edu.cn>;\"张祖凡\"<zhangzf@cqupt.edu.cn>;\"郑丹玲\"<zhengdl@cqupt.edu.cn>;\"赵辉\"<zhaohui@cqupt.edu.cn>;\"周非\"<zhoufei@cqupt.edu.cn>;\"周牧\"<zhoumu@cqupt.edu.cn>;\"周翊\"<zhouy@cqupt.edu.cn>;\"周晓霞\"<zhouxx@cqupt.edu.cn>;\"周杨\"<zhouyang@cqupt.edu.cn>;\"庄陵\"<zhuangling@cqupt.edu.cn>;\"朱江\"<zhujiang@cqupt.edu.cn>;\"李长萍\"<licp@cqupt.edu.cn>;\"郑涵英\"<zhenghy@cqupt.edu.cn>;\"杨小龙\"<yangxl@cqupt.edu.cn>;\"廖莎莎\"<liaoss@cqupt.edu.cn>;\"崔亚平\"<cuiyp@cqupt.edu.cn>;\"张普宁\"<zhangpn@cqupt.edu.cn>;\"赵悦\"<zhaoyue@cqupt.edu.cn>;";
        String[] nameAndEmails = a.split(";");
        for(String nameAndEmail : nameAndEmails){
            String name = nameAndEmail.split("<")[0];
            String email = nameAndEmail.split("<")[1];
            email = email.split(">")[0];
            name = name.substring(1);
            name = name.split("\"")[0];
//            System.out.println(name);
//            System.out.println(email);
            UserModel userModel = userDao.findTeacherByUsername(name);
            if(userModel != null){
                int i = userDao.addEmailToUsers(name,email);
                sum = sum+i;
            }
        }
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(sum);
        return responseJson;
    }




}
