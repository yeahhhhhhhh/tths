package cn.edu.cqupt.scie.tths.dao;

import cn.edu.cqupt.scie.tths.model.MsgModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by why on 2017/5/30.
 */
@Repository("msgDao")
public interface IMsgDao {
    List<MsgModel> findMsgList(@Param("begin") int begin, @Param("listCount") int listCount);

    int findMsgCount();
}
