package cn.edu.cqupt.scie.tths.dao.impl.mybatis;

import cn.edu.cqupt.scie.tths.dao.IFileDao;
import org.apache.ibatis.annotations.Param;

/**
 * Created by why on 2017/3/12.
 */
public interface FileMapper extends IFileDao{
    @Override
    void addDownLoadCount(@Param("fid") int fid);
}
