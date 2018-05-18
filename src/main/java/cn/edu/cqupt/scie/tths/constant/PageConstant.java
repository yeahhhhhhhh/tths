package cn.edu.cqupt.scie.tths.constant;

/**
 * Created by Kevin on 2017/3/28.
 */
public enum PageConstant {
    ARTICLE(10);
    int pageNumber;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    private PageConstant (int pageNumber){
        this.pageNumber = pageNumber;
    }
}
