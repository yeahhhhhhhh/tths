package cn.edu.cqupt.scie.tths.model.json;

/**
 * Created by why on 2017/7/29.
 */
public class EUditorJson {
    private String state;
    private String url;

    public EUditorJson() {
    }

    public EUditorJson(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
