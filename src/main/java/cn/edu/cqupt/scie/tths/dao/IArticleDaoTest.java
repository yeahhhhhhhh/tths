package cn.edu.cqupt.scie.tths.dao;

import cn.edu.cqupt.scie.tths.dao.impl.mybatis.ArticleMapper;
import cn.edu.cqupt.scie.tths.model.ArticleModel;
import cn.edu.cqupt.scie.tths.model.page.Page;
import cn.edu.cqupt.scie.tths.util.TimeUtil;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static cn.edu.cqupt.scie.tths.util.TimeUtil.getTimestamp;
import static org.junit.Assert.*;

/**
 * Created by Kevin on 2017/3/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/conf/spring/app/tths-dao.xml",
                        "classpath*:/conf/spring/app/tths-mybatis.xml",
                        "classpath*:/conf/spring/web/tths-servlet.xml"})
public class IArticleDaoTest {
    @Resource
    private IArticleDao articleDao;
    @org.junit.Test
    public void saveArticle() throws Exception {
        ArticleModel articleModel= new ArticleModel();
        articleModel.setUid(1);
        articleModel.setCid(1);
        articleModel.setTitle("1325");
        articleModel.setProfile("8786");
        articleModel.setBody("hfahkj");
        articleModel.setStatus(0);
        articleModel.setTimeUpdate(getTimestamp());
        articleDao.saveArticle(articleModel);
        System.out.println(articleDao.getArticle(articleModel.getId()));
    }

    @org.junit.Test
    public void updateArticle() throws Exception {
        ArticleModel articleModel= new ArticleModel();
        articleModel.setId(333);
        articleModel.setUid(1);
        articleModel.setCid(1);
        articleModel.setTitle("1325");
        articleModel.setProfile("8786");
        articleModel.setBody("交45645间");
        articleModel.setStatus(0);
        articleModel.setTimeUpdate(getTimestamp());
        articleDao.updateArticle(articleModel);
        System.out.println(articleDao.getArticle(articleModel.getId()));
    }

    @org.junit.Test
    public void deleteArticle() throws Exception {
        articleDao.deleteArticle(334);

    }

    @org.junit.Test
    public void getArticle() throws Exception {

    }

    @org.junit.Test
    public void getArticleList() throws Exception {
        Page page = new Page();
        page.setDbIndex(0);
        page.setDbNumber(10);
        System.out.println(articleDao.getArticleList(0,0,"IT",page));
    }

    @org.junit.Test
    public void getArticleListCount() throws Exception {
        System.out.println(articleDao.getArticleListCount(0,0,"IT"));
    }

}