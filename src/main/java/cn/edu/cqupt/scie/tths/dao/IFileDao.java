package cn.edu.cqupt.scie.tths.dao;

import cn.edu.cqupt.scie.tths.model.FileModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by why on 2017/3/12.
 */
@Repository("fileDao")
public interface IFileDao {
    int uploadFile(FileModel fileModel);

    int uploadFiles(List<FileModel> fileModelList);

    FileModel findFileByFid(String fid);
}
