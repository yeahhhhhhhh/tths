package cn.edu.cqupt.scie.tths.service.impl;

import cn.edu.cqupt.scie.tths.constant.StatusCodeConstant;
import cn.edu.cqupt.scie.tths.dao.IFileDao;
import cn.edu.cqupt.scie.tths.model.FileModel;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.service.IFileService;
import cn.edu.cqupt.scie.tths.util.IOUtil;
import cn.edu.cqupt.scie.tths.util.ResponseHandelUtil;
import cn.edu.cqupt.scie.tths.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by why on 2017/3/11.
 */
@Service
public class FileService implements IFileService{

    @Autowired
    private IFileDao fileDao;

    /**
     * 已测试，单文件上传
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public ResponseJson uploadFile(MultipartHttpServletRequest request) throws IOException {
        //获取文件
        MultipartFile file = request.getFile("file1");

        //判断该文件是否为空
        if(file == null || file.isEmpty())
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        //获取路径
        String path = request.getServletContext().getRealPath("upload");
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

        int result = fileDao.uploadFile(fileModel);
        System.out.println(result);

        return null;
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
        String path = request.getServletContext().getRealPath("upload");
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
     * 已测试,文件下载,未解决中文问题
     * @param request
     * @param response
     * @param fid
     * @return
     */
    @Override
    public ResponseJson fileDownload(HttpServletRequest request, HttpServletResponse response, String fid) {
        //判断fid是否为空
        if(fid == null || "".equals(fid))
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);
        //根据fid查找文件信息
        FileModel fileModel = fileDao.findFileByFid(fid);
        System.out.println(fileModel.toString());
        System.out.println(fileModel.getUploadTime());
        //判断查找出的文件信息是否为空
        if(fileModel == null)
            return new ResponseJson(StatusCodeConstant.INVALID_REQUEST);

        //设置html头部信息,http协议下载
        response.setContentType("application/octct-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename="+ fileModel.getFileName());

        //将日期格式转成yyyy-MM-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(fileModel.getUploadTime());
        System.out.println(date);
        //获取文件路径
        String filePath = request.getServletContext().getRealPath("upload")+"/"+date+"/"+fileModel.getRealName();
        try {
            //获取输入输出流
            InputStream is = new FileInputStream(filePath);
            OutputStream os = response.getOutputStream();
            IOUtil.in2Out(is,os);
            IOUtil.close(is,os);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new ResponseJson(StatusCodeConstant.OK);
    }


}
