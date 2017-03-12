package cn.edu.cqupt.scie.tths.model;

import java.util.Date;

/**
 * Created by why on 2017/3/11.
 */
public class FileModel {
    private int fid;
    private int uid;
    private String fileUploader;
    private String fileName;
    private String realName;
    private Date uploadTime;
    private String type;
    private int length;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFileUploader() {
        return fileUploader;
    }

    public void setFileUploader(String fileUploader) {
        this.fileUploader = fileUploader;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "fid=" + fid +
                ", uid=" + uid +
                ", fileUploader='" + fileUploader + '\'' +
                ", fileName='" + fileName + '\'' +
                ", realName='" + realName + '\'' +
                ", uploadTime=" + uploadTime +
                ", type='" + type + '\'' +
                ", length=" + length +
                '}';
    }
}
