package cn.edu.cqupt.scie.tths.model.json;

import cn.edu.cqupt.scie.tths.model.group.PageGroup;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Created by why on 2017/3/23.
 */
public class PageJson<E> {
    //当前页
    @Min(value = 1,message = "当前页必须大于0",groups = {PageGroup.class})
    private int nowPage;
    //每页显示条数
    @Min(value = 1,message = "每页显示条数必须大于0",groups = {PageGroup.class})
    private int listCount;
    //总页数
    private int pageCount;
    //总条数
    private int allCount;
    //信息list
    private List<E> pageList;

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getListCount() {
        return listCount;
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<E> getPageList() {
        return pageList;
    }

    public void setPageList(List<E> pageList) {
        this.pageList = pageList;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }
}
