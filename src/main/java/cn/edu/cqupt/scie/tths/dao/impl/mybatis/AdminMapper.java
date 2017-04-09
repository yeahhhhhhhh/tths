package cn.edu.cqupt.scie.tths.dao.impl.mybatis;

import cn.edu.cqupt.scie.tths.dao.IAdminDao;
import org.apache.ibatis.annotations.Param;

/**
 * Created by why on 2017/3/30.
 */
public interface AdminMapper extends IAdminDao {
    @Override
    void addTeacherType(@Param("typeName") String typeName, @Param("newType") String newType);

    @Override
    int deleteTeacherType(@Param("typeName")String typeName,@Param("jobStr") String jobStr);
}
