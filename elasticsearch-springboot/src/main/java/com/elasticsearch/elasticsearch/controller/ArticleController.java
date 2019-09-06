package com.elasticsearch.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.elasticsearch.elasticsearch.entity.Article;
import com.elasticsearch.elasticsearch.search.MyPage;
import com.elasticsearch.elasticsearch.search.StringSearchParam;
import com.elasticsearch.elasticsearch.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangxia
 * @date 2019/8/14 16:27
 * @Description:
 */
@RestController
@RequestMapping("/article")
public class ArticleController {



    @Autowired
    private ArticleService articleService;

    @GetMapping("/createIndex")
    public String createIndex() {
        return articleService.createIndex();
    }


    @PostMapping("/save")
    public String save(Article article) {
        articleService.save(article);
        return "保存成功";
    }

    @PostMapping("/findDataByStringQuery")
    public JSONObject findDataByStringQuery(@RequestBody StringSearchParam stringSearchParam) {
        MyPage myPage = articleService.findDataByStringQuery(stringSearchParam);
        return JSONObject.parseObject(JSON.toJSONString(myPage));
    }

    @PostMapping("/findQrticleByBoolQuery")
    public JSONObject findQrticleByBoolQuery(Article article){
        Page page=articleService.findQrticleByBoolQuery(article);
        return  JSONObject.parseObject(JSON.toJSONString(page));
    }

    @PostMapping("/findByTitle")
    public JSONObject findByTitle(Article article){
        return JSONObject.parseObject(JSON.toJSONString(articleService.findByTitle(article)));
    }

}
