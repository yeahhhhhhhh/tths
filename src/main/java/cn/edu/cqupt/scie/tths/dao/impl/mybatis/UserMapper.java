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
    List<UserModel> findTeachers(@Param("begin") int begin, @Param("count") int count, @Param("teacher_title") String teacher_title, @Param("teacher_job") String teacher_job, @Param("username") String username, @Param("py_name") String py_name);

    @Override
    UserModel findUserByUid(@Param("uid") int uid);

    @Override
    int findTeachersCount(@Param("teacher_title") String teacher_title, @Param("teacher_job") String teacher_job, @Param("username") String username,@Param("py_name") String py_name);

    @Override
    List<UserModel> findLeftTeachers(@Param("begin") int begin,@Param("count") int count, @Param("username")String username);

    @Override
    int findLeftTeachersCount(@Param("username") String username);

    @Override
    int addEmailToUsers(@Param("name") String name, @Param("email") String email);
}
