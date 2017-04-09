package cn.edu.cqupt.scie.tths.constant;

/**
 * Created by why on 2017/4/2.
 */
public enum FileConstant {
    HEAD_IMG("head_img"),//上传头像
    FILE("file");//上传普通文件

    private String headImgOrFile;

    FileConstant(String headImgOrFile) {
        this.headImgOrFile = headImgOrFile;
    }

    public String getHeadImgOrFile() {
        return headImgOrFile;
    }

    public void setHeadImgOrFile(String headImgOrFile) {
        this.headImgOrFile = headImgOrFile;
    }
}
