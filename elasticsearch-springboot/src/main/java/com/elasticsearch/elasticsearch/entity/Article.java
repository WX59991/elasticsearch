package com.elasticsearch.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author wangxia
 * @date 2019/8/12 17:15
 * @Description:
 */
@Data
//@Document 文档对象(索引信息，文档类型)
@Document(indexName = "blog3",type = "article")
public class Article{

    //@id 文档主键 唯一标识
    @Id
    //Field 每个文档的字段配置(类型  ，是否分词， 是否存储，分词器)
    //index：是否设置分词 analyzer：存储时使用的分词器 searchAnalyze：搜索时使用的分词器  store：是否存储  type: 数据类型
    @Field(store = true,index = false,type = FieldType.Integer)
    private Integer id;
    @Field(index=true,analyzer="ik_max_word",store=true,searchAnalyzer="ik_smart",type = FieldType.Text)
    private String title;
    @Field(index=true,analyzer="ik_max_word",store=true,searchAnalyzer="ik_smart",type = FieldType.Text)
    private String content;

}
