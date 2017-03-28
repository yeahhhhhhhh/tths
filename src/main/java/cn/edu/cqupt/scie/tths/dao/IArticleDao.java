package cn.edu.cqupt.scie.tths.dao;

import cn.edu.cqupt.scie.tths.model.ArticleModel;
import cn.edu.cqupt.scie.tths.model.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin on 2017/3/26.
 */
@Repository
public interface IArticleDao {
    int saveArticle (ArticleModel articleModel);

    int updateArticle(ArticleModel articleModel);

    int deleteArticle(int articleId);

    ArticleModel getArticle (int id);

    List<ArticleModel> getArticleList (@Param("uid") int uid,
                                       @Param("cid") int cid,
                                       @Param("searchString") String searchString,
                                       @Param("page") Page page);

    int getArticleListCount (@Param("uid") int uid,
                             @Param("cid") int cid,
                             @Param("searchString") String searchString);
}
