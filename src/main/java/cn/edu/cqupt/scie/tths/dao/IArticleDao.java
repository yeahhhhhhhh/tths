package cn.edu.cqupt.scie.tths.dao;

import cn.edu.cqupt.scie.tths.model.ArticleModel;
import cn.edu.cqupt.scie.tths.model.page.Page;

import java.util.List;

/**
 * Created by Kevin on 2017/3/26.
 */
public interface IArticleDao {
    int saveArticle (ArticleModel articleModel);

    int updateArticle(ArticleModel articleModel);

    int deleteArticle(int articleId);

    ArticleModel getArticle (int id);

    List<ArticleModel> getArticleList (int uid, int cid, int statue,String searchString, Page page);

    int getArticleListCount (int uid, int cid, int statue,String searchString);
}
