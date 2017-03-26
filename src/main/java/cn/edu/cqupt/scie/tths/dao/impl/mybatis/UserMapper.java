package cn.edu.cqupt.scie.tths.dao.impl.mybatis;

import cn.edu.cqupt.scie.tths.dao.IUserDao;
import cn.edu.cqupt.scie.tths.model.UserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by why on 2017/3/23.
 */
public interface UserMapper extends IUserDao {
    @Override
    List<UserModel> findTeachers(@Param("begin") int begin,@Param("count") int count);

    @Override
    UserModel findUserByUid(@Param("uid") int uid);
}
