package cn.edu.cqupt.scie.tths.model;

/**
 * Created by Kevin on 2017/3/26.
 */
public class ArticleModel {
    private int id;
    private int uid;
    private int cid;
    private String name;
    private String title;
    private String profile;
    private String body;
    private int status;
    private long timeUpdate;
    private int comment;
    private int count;
    private int star;
    private int top;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(long timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "ArticleModel{" + "id=" + id + ", uid=" + uid + ", cid=" + cid + ", name='" + name + '\'' + ", title='" + title + '\'' + ", profile='" + profile + '\'' + ", body='" + body + '\'' + ", status=" + status + ", timeUpdate=" + timeUpdate + ", comment=" + comment + ", count=" + count + ", star=" + star + ", top=" + top + '}';
    }
}
