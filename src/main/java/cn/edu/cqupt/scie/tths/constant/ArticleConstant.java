package cn.edu.cqupt.scie.tths.constant;

/**
 * Created by Kevin on 2017/3/26.
 */
public enum ArticleConstant {
    REMAIN(-1),//无视或者保持原装
    SAVED(0),//保存但是不发布（对外公开）
    PUBLISH(1),//发布（对外公开）
    ALL_UID(0),//所有用户
    ALL_CID(0);//所有课题

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private ArticleConstant (int status){
        this.status = status;
    }
}
