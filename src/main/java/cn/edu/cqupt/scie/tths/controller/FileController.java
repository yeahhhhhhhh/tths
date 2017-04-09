package cn.edu.cqupt.scie.tths.controller;

import cn.edu.cqupt.scie.tths.model.FileModel;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by why on 2017/3/10.
 */
@Controller
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private IFileService fileService;

    /**
     * 单文件上传
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/fileUpload",method = RequestMethod.POST)
    public @ResponseBody ResponseJson fileUpload(MultipartHttpServletRequest request) throws IOException {
        return fileService.uploadFile(request);
    }

    /**
     * 多文件上传
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/fileUploads",method = RequestMethod.POST)
    public @ResponseBody ResponseJson filesUpload(MultipartHttpServletRequest request) throws IOException{
        return fileService.uploadFiles(request);
    }

    /**
     * 文件下载
     * @param request
     * @param response
     * @param fid
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/fileDownload")
    public @ResponseBody ResponseJson fileDownload(HttpServletRequest request, HttpServletResponse response,int fid) throws UnsupportedEncodingException {
        return fileService.fileDownload(request,response,fid);
    }

    /**
     * 文件删除
     * @param fileModel
     * @param request
     * @return
     */
    @RequestMapping(value = "/fileDelete")
    public @ResponseBody ResponseJson fileDelete(FileModel fileModel,HttpServletRequest request){
        return fileService.fileDelete(fileModel,request);
    }

    @RequestMapping(value = "/fileList")
    public @ResponseBody ResponseJson fileList(int uid){
        return fileService.findFileList(uid);
    }

}
