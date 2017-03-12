package cn.edu.cqupt.scie.tths.dao;

import cn.edu.cqupt.scie.tths.model.FileModel;

import java.util.List;

/**
 * Created by why on 2017/3/12.
 */
public interface IFileDao {
    int uploadFile(FileModel fileModel);

    int uploadFiles(List<FileModel> fileModelList);

    FileModel findFileByFid(String fid);
}
