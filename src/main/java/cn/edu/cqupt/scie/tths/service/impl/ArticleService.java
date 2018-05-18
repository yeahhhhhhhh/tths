package cn.edu.cqupt.scie.tths.service.impl;

import cn.edu.cqupt.scie.tths.constant.ArticleConstant;
import cn.edu.cqupt.scie.tths.constant.PageConstant;
import cn.edu.cqupt.scie.tths.constant.StatusCodeConstant;
import cn.edu.cqupt.scie.tths.dao.IArticleDao;
import cn.edu.cqupt.scie.tths.model.ArticleModel;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.model.page.Page;
import cn.edu.cqupt.scie.tths.service.IArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static cn.edu.cqupt.scie.tths.util.ResponseHandelUtil.listHandel;

/**
 * Created by Kevin on 2017/3/28.
 */
@Service
public class ArticleService implements IArticleService{
    @Resource
    private IArticleDao articleDao;


    private ResponseJson responseByArticleCheck(ArticleModel articleModel){
        if(articleModel!=null){
            ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
            responseJson.setBody(articleModel);
            return responseJson;
        }else {
            return new ResponseJson(StatusCodeConstant.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseJson saveNewArticle(ArticleModel articleModel) {
        articleDao.saveArticle(articleModel);
        articleModel = articleDao.getArticle(articleModel.getId());
        return responseByArticleCheck(articleModel);
    }

    @Override
    public ResponseJson updateArticle(ArticleModel articleModel) {
        articleDao.updateArticle(articleModel);
        articleModel = articleDao.getArticle(articleModel.getId());
        return responseByArticleCheck(articleModel);
    }

    @Override
    public ResponseJson deleteArticle(int articleId) {
        return articleDao.deleteArticle(articleId)==1?
                new ResponseJson(StatusCodeConstant.OK):
                new ResponseJson(StatusCodeConstant.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseJson getArticle(int articleId) {
        ArticleModel articleModel = articleDao.getArticle(articleId);
        if(articleModel!=null){
            ResponseJson responseJson = new ResponseJson(StatusCodeConstant.OK);
            responseJson.setBody(articleModel);
            return responseJson;
        }else {
            return new ResponseJson(StatusCodeConstant.NOT_FOUND);
        }
    }

    @Override
    public ResponseJson getArticleListByUid(int uid, int currentPage) {
        int totalNumber = articleDao.getArticleListCount(
                uid,
                ArticleConstant.ALL_CID.getStatus(),
                "");
        Page page = this.getPageOfArticles(currentPage,totalNumber);
        List<ArticleModel> articleModels = articleDao.getArticleList(
                uid,
                ArticleConstant.ALL_CID.getStatus(),
                "",
                page
        );
        return listHandel(articleModels, page);
    }

    @Override
    public ResponseJson getArticleListByCid(int cid, int currentPage) {
        int totalNumber = articleDao.getArticleListCount(
                ArticleConstant.ALL_UID.getStatus(),
                cid,
                "");
        Page page = this.getPageOfArticles(currentPage,totalNumber);
        List<ArticleModel> articleModels = articleDao.getArticleList(
                ArticleConstant.ALL_UID.getStatus(),
                cid,
                "",
                page
        );
        return listHandel(articleModels, page);
    }

    @Override
    public ResponseJson getArticleListBySearch(String searchString, int currentPage) {
        int totalNumber = articleDao.getArticleListCount(
                ArticleConstant.ALL_UID.getStatus(),
                ArticleConstant.ALL_CID.getStatus(),
                searchString);
        Page page = this.getPageOfArticles(currentPage,totalNumber);
        List<ArticleModel> articleModels = articleDao.getArticleList(
                ArticleConstant.ALL_UID.getStatus(),
                ArticleConstant.ALL_CID.getStatus(),
                searchString,
                page
        );
        return listHandel(articleModels, page);
    }

    private Page getPageOfArticles(int currentPage, int totalNumber){
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setTotalNumber(totalNumber);
        page.setPageNumber(PageConstant.ARTICLE.getPageNumber());

        page.count();
        return page;
    }
}
