package cn.edu.cqupt.scie.tths.dao;

import cn.edu.cqupt.scie.tths.model.FileModel;
import cn.edu.cqupt.scie.tths.model.UserModel;
import org.apache.ibatis.annotations.Param;
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

    void deleteFilesByUid(int uid);

    List<FileModel> findFileListByPage(@Param("uid") int uid,@Param("begin") int begin,@Param("listCount") int listCount);

    int findFileCountByUid(int uid);

    void deleteFilesByFid(int fid);
}
