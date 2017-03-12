package cn.edu.cqupt.scie.tths.service;

import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by why on 2017/3/11.
 */
public interface IFileService {
    ResponseJson uploadFile(MultipartHttpServletRequest request) throws IOException;

    ResponseJson uploadFiles(MultipartHttpServletRequest request) throws IOException;

    ResponseJson fileDownload(HttpServletRequest request, HttpServletResponse response,String fid);
}
