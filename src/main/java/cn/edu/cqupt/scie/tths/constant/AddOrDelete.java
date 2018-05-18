package cn.edu.cqupt.scie.tths.constant;

/**
 * Created by why on 2017/4/7.
 */
public enum AddOrDelete {
    ADD("add"),
    DELETE("delete");
    private String addOrDeleteStr;

    AddOrDelete(String addOrDeleteStr) {
        this.addOrDeleteStr = addOrDeleteStr;
    }

    public String getAddOrDeleteStr() {
        return addOrDeleteStr;
    }

    public void setAddOrDeleteStr(String addOrDeleteStr) {
        this.addOrDeleteStr = addOrDeleteStr;
    }
}
