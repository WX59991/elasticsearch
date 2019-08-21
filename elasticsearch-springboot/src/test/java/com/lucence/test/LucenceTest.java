package com.lucence.test;

import com.elasticsearch.elasticsearch.entity.Article;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author wangxia
 * @date 2019/8/16 9:32
 * @Description:
 */
public class LucenceTest {

    /**
     * 创建索引库
     * @throws IOException
     */
    @Test
    public void createIndexDb() throws IOException {
        //创建javaBean对象
        Article article=new Article();
        article.setId(1);
        article.setTitle("培训");
        article.setContent("传智是一家IT培训机构");
        //创建Document对象
        Document document=new Document();

        // 将Article对象中的三个属性值分别绑定到Document对象
        /**
         * 参数一：document对象中的属性名
         * 参数二：document对象中的属性值
         * 参数三：是否将属性值存入由原始记录表中转存入词汇表
         *        Store.YES表示该属性值会存入词汇表
         *        Store.NO表示该属性值不会存入词汇表
         *  参数四：是否将属性值进行分词算法
         *        Index.ANALYZED表示该属性值会进行词汇拆分
         *        Index.NOT_ANALYZED表示该属性值不会进行词汇拆分
         */
        document.add(new StringField("xid",article.getId().toString(),Field.Store.YES));
        document.add(new StringField("title",article.getTitle(),Field.Store.YES));
        document.add(new TextField("content",article.getContent(),Field.Store.YES));
        //创建IndexWriter字符流对象
        //索引存放位置
        Directory directory= FSDirectory.open(Paths.get("C:\\work\\workspace\\IndexDBDBDB"));
        //创建分词器
        //标准分词器
        StandardAnalyzer analyzer=new StandardAnalyzer();
        IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
        //创建IndexWriter字符流对象
        IndexWriter writer=new IndexWriter(directory,iwc);

        //将文档对象写入lucene索引库
        writer.addDocument(document);
        //关闭indexWriter字符流对象
        writer.close();
    }


    /**
     * 全文检索
     * @throws Exception
     */
    @Test
    public void findIndexDb() throws Exception{
        String kewords="培训";
        //从索引文件读取索引
        Directory dic= FSDirectory.open(Paths.get("C:\\work\\workspace\\sentinel-tutorial-master\\elasticsearch-parent\\IndexDBDBDB"));
        IndexReader directory= DirectoryReader.open(dic);

        //传关键分词器
        StandardAnalyzer standardAnalyzer=new StandardAnalyzer();

        //根据索引库创建索引
        IndexSearcher indexSearcher=new IndexSearcher(directory);

        //船舰查询解析器对象
        QueryParser queryParser=new QueryParser("title",standardAnalyzer);

        Query query=queryParser.parse(kewords);
        //获取最大记录数
        int MAX_RECODER=10;
        TopDocs topDocs=indexSearcher.search(query,MAX_RECODER);
        //遍历结果
        for(int i=0;i<topDocs.scoreDocs.length;i++){
            // 取出封装编号和分数的ScoreDoc对象
            ScoreDoc scoreDoc = topDocs.scoreDocs[i];
            // 取出每一个编号，例如:0,1,2
            int no = scoreDoc.doc;
            // 根据编号去索引库中的原始记录表中查询对应的document对象
            Document document = indexSearcher.doc(no);
            // 获取document对象中的三个属性值
            String xid = document.get("xid");
            String xtitle = document.get("title");
            String xcontent = document.get("content");
            System.out.println("id:"+xcontent+"\t title:"+xtitle+"\t content:"+xcontent);
        }
    }
}
