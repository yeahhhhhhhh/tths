package cn.edu.cqupt.scie.tths.controller;

import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import cn.edu.cqupt.scie.tths.service.IArticleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Kevin on 2017/3/29.
 */
@Controller
public class ArticlesSearchController {
    @Resource
    private IArticleService articleService;

    @ResponseBody
    @RequestMapping(value = "/article{id}", method = RequestMethod.GET)
    public ResponseJson getArticle (
            @PathVariable("id") int id
    ){
        return this.articleService.getArticle(id);
    }

    @RequestMapping(value = "/user{uid}/articles", method = RequestMethod.GET)
    public String getArticleListByUid (
            @PathVariable("uid") int uid
    ){
        return "redirect:/user"+uid+"/articles/1";
    }


    @ResponseBody
    @RequestMapping(value = "/user{uid}/articles/{currentPage}", method = RequestMethod.GET)
    public ResponseJson getArticleListByUid (
            @PathVariable("uid") int uid,
            @PathVariable("currentPage") int currentPage
    ){
        return this.articleService.getArticleListByUid(uid,currentPage);
    }

    @RequestMapping(value = "/category{cid}/articles", method = RequestMethod.GET)
    public String getArticleListByCid (
            @PathVariable("cid") int cid
    ){
        return "redirect:/category"+cid+"/articles/1";
    }
    @ResponseBody
    @RequestMapping(value = "/category{cid}/articles/{currentPage}", method = RequestMethod.GET)
    public ResponseJson getArticleByCid (
            @PathVariable("cid") int cid,
            @PathVariable("currentPage") int currentPage
    ){
        return this.articleService.getArticleListByCid(cid,currentPage);
    }

    @ResponseBody
    @RequestMapping(value = "/search/articles/{currentPage}", method = RequestMethod.GET)
    public ResponseJson getArticleByTitle (
            @Param("searchString") String searchString,
            @PathVariable("currentPage") int currentPage
    ){
        return this.articleService.getArticleListBySearch(searchString,currentPage);
    }

}
