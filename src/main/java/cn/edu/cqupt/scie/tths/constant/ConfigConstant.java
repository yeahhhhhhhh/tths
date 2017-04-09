package cn.edu.cqupt.scie.tths.constant;

/**
 * Created by why on 2017/4/7.
 */
public enum ConfigConstant {
    TEACHER_JOB("teacher_job"),
    TEACHER_TITLE("teacher_title"),
    FORCE("force"),
    LEAVE("leave");
    private String config;

    ConfigConstant(String config) {
        this.config = config;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
