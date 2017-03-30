package cn.edu.cqupt.scie.tths.controller;

import cn.edu.cqupt.scie.tths.model.ArticleModel;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.service.IArticleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Kevin on 2017/3/29.
 */
@Controller
@RequestMapping("/article_user")
public class ArticleHandelController {
    @Resource
    private IArticleService articleService;

    @ResponseBody
    @RequestMapping(value = "/publish" ,method = RequestMethod.POST)
    public ResponseJson publishArticle (
            @Param("uid") int uid,
            @Param("cid") int cid,
            @Param("title") String title,
            @Param("profile") String profile,
            @Param("body") String body
    ){
        ArticleModel articleModel = new ArticleModel();
        articleModel.setUid(uid);
        articleModel.setCid(cid);
        articleModel.setTitle(title);
        articleModel.setProfile(profile);
        articleModel.setBody(body);
        return this.articleService.saveNewArticle(articleModel);
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseJson updateArticle(
            @Param("title") String title,
            @Param("profile") String profile,
            @Param("body") String body
    ){
        ArticleModel articleModel = new ArticleModel();
        articleModel.setBody(body);
        articleModel.setTitle(title);
        articleModel.setProfile(profile);
        return this.articleService.updateArticle(articleModel);
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ResponseJson deleteArticle(
            @Param("id") int id
    ){
        return this.articleService.deleteArticle(id);
    }
}
