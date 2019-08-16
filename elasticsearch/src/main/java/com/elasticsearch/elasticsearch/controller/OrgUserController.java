package com.elasticsearch.elasticsearch.controller;

import com.elasticsearch.elasticsearch.entity.User;
import com.elasticsearch.elasticsearch.service.OrgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangxia
 * @date 2019/8/9 17:37
 * @Description:
 */
@RestController
@RequestMapping("/")
public class OrgUserController {

    @Autowired
    private OrgUserService orgUserService;


    @GetMapping("/createIndex")
    public String createIndex(String index,String type){
        if(orgUserService.createIndexIfNotExist(index,type)){
            return "创建成功";
        }
        return "已经存在创建失败";
    }


    @PostMapping("/batchAddUser")
    public String batchAddUser(List<User> users){
        orgUserService.batchAddUser(users);
        return "添加成功";
    }

    @PostMapping("/addUser")
    public String addUser(User user){
        orgUserService.addUser(user);
        return "添加成功";
    }

    @PostMapping("/deleteUSerById")
    public String deleteUSerById(String id){
        orgUserService.deleteUSerById(id);
        return "删除成功";
    }

    @PostMapping("/deleteUSerByQuery")
    public String deleteUSerByQuery(User user){
        orgUserService.deleteUSerByQuery(user);
        return "删除成功";
    }

    @PostMapping("/updateUserById")
    public String updateUserById(User user){
        orgUserService.updateUserById(user);
        return "更新成功";
    }

    @PostMapping("/queryByUserName")
    public List<User> queryByUserName(User user){
        return orgUserService.queryByUserName(user);
    }


}
