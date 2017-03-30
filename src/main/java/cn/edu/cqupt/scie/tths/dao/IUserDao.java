package cn.edu.cqupt.scie.tths.dao;

import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.PageJson;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by why on 2017/3/23.
 */
@Repository("userDao")
public interface IUserDao {
    List<UserModel> findTeachers(int begin,int count,String teacher_title,String teacher_job,String username);

    int findTeachersCount(String teacher_title,String teacher_job,String username);

    UserModel findUserByUsername(UserModel userModel);

    int changeInfo(UserModel userModel);

    UserModel findUserByUid(int uid);
}
