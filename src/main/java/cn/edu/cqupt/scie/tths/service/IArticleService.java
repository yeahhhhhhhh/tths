package cn.edu.cqupt.scie.tths.service;

import cn.edu.cqupt.scie.tths.model.ArticleModel;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;

/**
 * Created by Kevin on 2017/3/28.
 */
public interface IArticleService {
    ResponseJson saveNewArticle (ArticleModel articleModel);

    ResponseJson updateArticle (ArticleModel articleModel);

    ResponseJson deleteArticle(int articleId);

    ResponseJson getArticle (int articleId);

    ResponseJson getArticleListByUid (int uid,int currentPage);

    ResponseJson getArticleListByCid (int cid,int currentPage);

    ResponseJson getArticleListBySearch (String searchString, int currentPage);
}
