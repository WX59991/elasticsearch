package com.elasticsearch.elasticsearch.service;

import com.elasticsearch.elasticsearch.dao.ElasticOperationDao;
import com.elasticsearch.elasticsearch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangxia
 * @date 2019/8/9 17:22
 * @Description:
 */
@Component
public class OrgUserService {


    @Autowired
    private ElasticOperationDao elasticOperationDao;

    private String index = "es";
    //6.0的版本不允许一个index下面有多个type，并且官方说是在接下来的7.0版本中会删掉type
    private String type = "user";


    public void afterPropertiesSet() throws Exception{
        elasticOperationDao.createIndexIfNotExist(index,type);
    }

    public void batchAddUser(List<User> users){
        if(users!=null && users.size()>0) {
            return ;
        }
        for(User user :users) {
            elasticOperationDao.addDocumentToBulkProcessor(index, type, user);
        }
    }

    public void addUser(User user){
        elasticOperationDao.addDocument(index,type,user);
    }

    public void deleteUSerById(String id){
        elasticOperationDao.deleteDocumentById(index,type,id);
    }

    public void deleteUSerByQuery(User user){
        elasticOperationDao.deleteDocumentByQuery(index,type,user);
    }

    public void updateUserById(User user){
        String id = user.getId();
        user.setId(null);
        elasticOperationDao.updateDocument(index, type,id, user);
    }

    public List<User> queryByUserName(User user){
        return elasticOperationDao.queryDocumentByParam(index, type, user,User.class);
    }

    public boolean createIndexIfNotExist(String index, String type) {
       return elasticOperationDao.createIndexIfNotExist(index,type);
    }

}
