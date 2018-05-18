package cn.edu.cqupt.scie.tths.service.impl;

import cn.edu.cqupt.scie.tths.constant.StatusCodeConstant;
import cn.edu.cqupt.scie.tths.dao.IAdminDao;
import cn.edu.cqupt.scie.tths.dao.IFileDao;
import cn.edu.cqupt.scie.tths.dao.IUserDao;
import cn.edu.cqupt.scie.tths.model.FileModel;
import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.EUditorJson;
import cn.edu.cqupt.scie.tths.model.json.PageJson;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.service.IFileService;
import cn.edu.cqupt.scie.tths.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by why on 2017/3/11.
 */
@Transactional
@Service
public class FileService implements IFileService{

    @Autowired
    private IFileDao fileDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IAdminDao adminDao;

    /**
     * 已测试，单文件上传
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public ResponseJson uploadFile(MultipartHttpServletRequest request) throws IOException {

        //获取uid
        int uid;
        int sessionUid;
        //获取session里的uid
        if(request.getSession().getAttribute("uid") != null && request.getParameter("uid") != null){
            sessionUid = (int) request.getSession().getAttribute("uid");
            uid = Integer.parseInt(request.getParameter("uid"));
            if(sessionUid != uid){
                return new ResponseJson(StatusCodeConstant.USER_UNLOGIN);
            }else {
                //根据uid查找filepLoader
                UserModel userModel = userDao.findUserByUid(uid);

                //获取文件
                MultipartFile file = request.getFile("file1");

                //判断该文件是否为空
                if(file == null || file.isEmpty())
                    return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);


                //获取路径
                String path = request.getServletContext().getRealPath("uploads");
                System.out.println("path:"+path);

                //获取日期
                String date = TimeUtil.getNowTime();
                System.out.println("date:"+date);

                //获取文件名称
                String fileName = file.getOriginalFilename();
                System.out.println("fileName:"+fileName);

                //获取文件类型
//        String type = file.getContentType();
                //获取文件类型
                String type = fileName.substring(fileName.lastIndexOf(".")+1);
                System.out.println("type:"+type);

                //获取文件大小
                int length = (int) file.getSize();
                System.out.println(length);

                //构造文件名称
                String realName = System.currentTimeMillis()+"."+type;
                System.out.println("realName:"+realName);

                String savePath = null;
                int result;
                //判断是上传头像还是普通文件
                if(request.getParameter("headImgOrFile").equals("head_img")){
                    //文件存储路径
                    savePath = path + "/head_imgs/" + date;
                    System.out.println(savePath);

                    //将数据封装到usermodel
                    userModel.setAvator("/uploads/head_imgs/"+date+"/"+realName);
                    result = fileDao.uploadHeadImg(userModel);

                }else if(request.getParameter("headImgOrFile").equals("file")){
                    //文件存储路径
                    savePath = path + "/file/" + date;
                    System.out.println(savePath);

                    //将数据封装到FileModel
                    FileModel fileModel = new FileModel();
                    fileModel.setFileName(fileName);
                    fileModel.setRealName(realName);
                    fileModel.setType(type);
                    fileModel.setLength(length);
                    fileModel.setUid(uid);
                    fileModel.setFileUploader(userModel.getUsername());

                    result = fileDao.uploadFile(fileModel);
                    System.out.println(result);
                }else {
                    return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
                }

                //文件存储路径
//                String savePath = path + "/" + date;
//                System.out.println(savePath);

                //判断该文件夹是否存在
                File localFile = new File(savePath,realName);
                if(!localFile.exists())
                    localFile.mkdirs();
                localFile.setWritable(true,false);
                file.transferTo(localFile);

                if(result == 1){
                    return new ResponseJson(StatusCodeConstant.OK);
                }else {
                    return new ResponseJson(StatusCodeConstant.INTERNAL_SERVER_ERROR);
                }
            }
        }else {
            return new ResponseJson(StatusCodeConstant.USER_UNLOGIN);
        }


    }

    /**
     * 多文件上传还未测试
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public ResponseJson uploadFiles(MultipartHttpServletRequest request) throws IOException {
        List<MultipartFile> fileList = request.getFiles("files");
        if(fileList.size() == 0)
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        //获取路径
        String path = request.getServletContext().getRealPath("uploads");
        //创建一个list用于装fileModel的集合
        List<FileModel> fileModelList = new LinkedList<FileModel>();
        for(int i = 0; i < fileList.size(); i++){
            //获取单个文件
            MultipartFile file = fileList.get(i);
            //获取日期
            String date = TimeUtil.getNowTime();
            System.out.println("date:"+date);

            //获取文件名称
            String fileName = file.getOriginalFilename();
            System.out.println("fileName:"+fileName);

            //获取文件类型
//        String type = file.getContentType();
            //获取文件类型
            String type = fileName.substring(fileName.lastIndexOf(".")+1);
            System.out.println("type:"+type);

            //获取文件大小
            int length = (int) file.getSize();
            System.out.println(length);

            //构造文件名称
            String realName = System.currentTimeMillis()+"."+type;
            System.out.println("realName:"+realName);

            //文件存储路径
            String savePath = path + "/" + date;
            System.out.println(savePath);

            //判断该文件夹是否存在
            File localFile = new File(savePath,realName);
            if(!localFile.exists())
                localFile.mkdirs();
            file.transferTo(localFile);

            //将数据封装到FileModel
            FileModel fileModel = new FileModel();
            fileModel.setFileName(fileName);
            fileModel.setRealName(realName);
            fileModel.setType(type);
            fileModel.setLength(length);

            //将fileModel添加进list
            fileModelList.add(fileModel);
        }

        //调用fileDao
        int result = fileDao.uploadFiles(fileModelList);
        ResponseJson responseJson = ResponseHandelUtil.handleIntCondition(
                fileList.size(),
                result,
                StatusCodeConstant.INTERNAL_SERVER_ERROR);
        //如果文件的个数和数据库执行成功的条数相同，说明该方法执行成功
        if(fileList.size() == result)
            responseJson.setBody(fileModelList);
        return responseJson;
    }

    /**
     * 已测试,文件下载,已解决中文问题
     * @param request
     * @param response
     * @param fid
     * @return
     */
    @Override
    public ResponseJson fileDownload(HttpServletRequest request, HttpServletResponse response, int fid) throws UnsupportedEncodingException {
        //判断fid是否为空
        if(fid == 0)
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        //根据fid查找文件信息
        FileModel fileModel = fileDao.findFileByFid(fid);
        System.out.println(fileModel.toString());
        System.out.println(fileModel.getUploadTime());
        //判断查找出的文件信息是否为空
        if(fileModel == null)
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);

        byte[] bytes = fileModel.getFileName().getBytes("utf-8");
        //设置html头部信息,http协议下载
        response.setContentType("multipart/form-data;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename="+ new String(bytes,"iso-8859-1"));

        //将日期格式转成yyyy-MM-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(fileModel.getUploadTime());
        System.out.println(date);
        //获取文件路径
        String filePath = request.getServletContext().getRealPath("uploads/file/")+date+"/"+fileModel.getRealName();
        try {
            //获取输入输出流
            InputStream is = new FileInputStream(filePath);
            OutputStream os = response.getOutputStream();
            IOUtil.in2Out(is,os);
            IOUtil.close(is,os);
            fileDao.addDownLoadCount(fid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new ResponseJson(StatusCodeConstant.OK);
    }

    @Override
    public ResponseJson fileDelete(FileModel fileModel, HttpServletRequest request) {
        //根据fid查找文件路径信息
        FileModel fileByfid = fileDao.findFileByFid(fileModel.getFid());
        if(fileByfid == null){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        //获得用户uid
        int uid = (int) request.getSession().getAttribute("uid");
        if(fileByfid.getUid() != uid){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        //将日期格式转成yyyy-MM-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(fileByfid.getUploadTime());
        //获取realName
        String realName = fileByfid.getRealName();
        //获取路径
        String path = request.getServletContext().getRealPath("uploads/file")+"/"+date+"/"+realName;
        File file = new File(path);
        if(file.exists()){
            file.delete();
            //从数据库中删除该记录
            fileDao.deleteFilesByFid(fileByfid.getFid());
        }else{
            //从数据库中删除该记录
            fileDao.deleteFilesByFid(fileByfid.getFid());
            return new ResponseJson(StatusCodeConstant.INTERNAL_SERVER_ERROR);
        }
        return new ResponseJson(StatusCodeConstant.OK);
    }

    @Override
    public ResponseJson findFileList(int uid) {
        List<FileModel> fileModelList = fileDao.findFileList(uid);
        if(fileModelList != null){
            for(FileModel fileModel : fileModelList){
                fileModel.setFormatLength(FileSizeUtil.convertFileSize(fileModel.getLength()));
            }
        }
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(fileModelList);
        return responseJson;
    }

    @Override
    public ResponseJson excelUpload(MultipartHttpServletRequest request,String type) {
        //判断管理员是否登录
        if(request.getSession().getAttribute("uid") == null){
            return new ResponseJson(StatusCodeConstant.USER_UNLOGIN);
        }
        int uid = (int) request.getSession().getAttribute("uid");
        if(!request.getParameter("uid").equals(uid+"")){
            return new ResponseJson(StatusCodeConstant.USER_UNLOGIN);
        }

        MultipartFile file = request.getFile("file1");
        List<UserModel>userModelList = ExcelUtil.readExcel(file);
        if(userModelList == null){
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        }
        List<String> exsitedTeacherList = new ArrayList<String>();
        int num = 0;

        //判断type是否为all
        if(type.equals("all")){
            //将教师全部设置成离职
            userDao.offTeachersAll();
        }
        for(UserModel excelUserModel : userModelList){
            //判断该邮箱是否已存在
            UserModel userModel = userDao.findUserByEmail(excelUserModel);
            if(userModel == null){
                //老师不存在,添加老师
                excelUserModel.setPassword(MD5Util.MD5("123456",""));
                adminDao.addNewTeachers(excelUserModel);
                num++;
            }else {
                //老师存在
                exsitedTeacherList.add(excelUserModel.getUsername());
                //若type为all，将教师恢复
                if(type.equals("all")){
                    userDao.changeStatusByAdd(excelUserModel.getUsername());
                }
            }
        }
        exsitedTeacherList.add("添加成功"+num+"名老师,以上老师已存在");
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(exsitedTeacherList);
        return responseJson;
    }

    @Override
    public ResponseJson findFileListByPage(int uid, PageJson pageJson) {
        int begin = (pageJson.getNowPage() - 1) * pageJson.getListCount();
        //分页查询教师文件
        List<FileModel> fileModelList = fileDao.findFileListByPage(uid,begin,pageJson.getListCount());
        //格式化文件大小
        if(fileModelList != null){
            for(FileModel fileModel : fileModelList){
                fileModel.setFormatLength(FileSizeUtil.convertFileSize(fileModel.getLength()));
            }
        }
        //查询教师文件数量
        int allCount = fileDao.findFileCountByUid(uid);
        //计算总页数
        int pageCount = allCount % pageJson.getListCount() == 0 ? allCount / pageJson.getListCount() : allCount / pageJson.getListCount() + 1;
        //将信息封装到pageJson
        pageJson.setAllCount(allCount);
        pageJson.setPageCount(pageCount);
        pageJson.setPageList(fileModelList);
        //将pageJson封装到responseJson
        ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
        responseJson.setBody(pageJson);
        return responseJson;
    }

    @Override
    public EUditorJson ueditorUploadImage(MultipartHttpServletRequest request) throws IOException {
        MultipartFile file = request.getFile("upfile");
        //获取文件名称
        String fileName = file.getOriginalFilename();
        System.out.println("fileName:"+fileName);
        //获取路径
        String path = request.getServletContext().getRealPath("uploads");
        System.out.println("path:"+path);
        //获取日期
        String date = TimeUtil.getNowTime();
        System.out.println("date:"+date);
        //获取文件类型
        String type = fileName.substring(fileName.lastIndexOf(".")+1);
        System.out.println("type:"+type);
        //构造文件名称
        String realName = System.currentTimeMillis()+"."+type;
        System.out.println("realName:"+realName);
        //文件存储路径
        String savePath = null;
        savePath = path + "/ueditor/image/" + date;
        System.out.println(savePath);
        //判断该文件夹是否存在
        File localFile = new File(savePath,realName);
        if(!localFile.exists())
            localFile.mkdirs();
        localFile.setWritable(true,false);
        file.transferTo(localFile);
        EUditorJson eUditorJson = new EUditorJson("SUCCESS");
        eUditorJson.setUrl(date+"/"+realName);
        return eUditorJson;
    }

}
