package cn.edu.cqupt.scie.tths.dao;

import cn.edu.cqupt.scie.tths.model.FileModel;
import cn.edu.cqupt.scie.tths.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by why on 2017/3/12.
 */
@Repository("fileDao")
public interface IFileDao {
    int uploadFile(FileModel fileModel);

    int uploadFiles(List<FileModel> fileModelList);

    FileModel findFileByFid(int fid);

    void addDownLoadCount(int fid);

    int uploadHeadImg(UserModel userModel);

    List<FileModel> findFileList(int uid);
}
