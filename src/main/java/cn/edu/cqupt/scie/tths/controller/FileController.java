package cn.edu.cqupt.scie.tths.controller;

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

    @RequestMapping(value = "/fileUpload",method = RequestMethod.POST)
    public @ResponseBody ResponseJson fileUpload(MultipartHttpServletRequest request) throws IOException {
        return fileService.uploadFile(request);
    }

    @RequestMapping(value = "/fileUploads",method = RequestMethod.POST)
    public @ResponseBody ResponseJson filesUpload(MultipartHttpServletRequest request) throws IOException{
        return fileService.uploadFiles(request);
    }

    @RequestMapping(value = "/fileDownload")
    public @ResponseBody ResponseJson fileDownload(HttpServletRequest request, HttpServletResponse response,String fid) throws UnsupportedEncodingException {
        return fileService.fileDownload(request,response,fid);
    }
}
