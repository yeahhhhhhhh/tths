package cn.edu.cqupt.scie.tths.dao;

import cn.edu.cqupt.scie.tths.model.AdminModel;
import cn.edu.cqupt.scie.tths.model.UserModel;
import org.springframework.stereotype.Repository;

/**
 * Created by why on 2017/3/30.
 */
@Repository("adminDao")
public interface IAdminDao {
    AdminModel findAdminByUsername(AdminModel adminModel);

    int addNewTeachers(UserModel userModel);

    void addTeacherType(String typeName, String newType);

    int deleteTeacherType(String typeName, String jobStr);

    void addLoginLog(AdminModel adminByUsername);

    void addLoginCount(AdminModel adminByUsername);
}
