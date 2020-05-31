package com.cn.controller;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/5/31 0031.
 */
@Controller
@RequestMapping("/es")
public class ElasticSearchController {

    @Autowired
    private TransportClient transportClient;

    /**
     * 查询
     * @param id
     * @return
     */
    @GetMapping("/detail")
    @ResponseBody
    public ResponseEntity detail(String id){

        //查询book索引下，类型为novel 的  指定 id  文档
        GetResponse result = transportClient.prepareGet("book", "novel", id).get();

        if (!result.isExists()){
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(result.getSource(), HttpStatus.OK);
    }

    /**
     * 复合查询
     * @return
     */
    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity info(@RequestParam(name = "author",required = false) String author,
                               @RequestParam(name = "title",required = false) String title,
                               @RequestParam(name = "gt_word_count",defaultValue = "0") Integer gtWordCount,
                               @RequestParam(name = "lt_word_count",required = false) Integer ltWordCount){

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(author)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("author",author));
        }

        if (!StringUtils.isEmpty(title)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("title",title));
        }

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("word_count")
                .from(gtWordCount);

        if (ltWordCount!=null&& ltWordCount>0){
            rangeQueryBuilder.to(ltWordCount);
        }

        //将指定的字段查询和范围查询结合起来
        boolQueryBuilder.filter(rangeQueryBuilder);

        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch("book")
                .setTypes("novel")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .setFrom(0)
                .setSize(10);

        System.out.println(searchRequestBuilder);

        SearchResponse searchResponse = searchRequestBuilder.get();

        List<Map<String,Object>> result = new ArrayList<>();
        for (SearchHit hit:searchResponse.getHits()){
            result.add(hit.getSource());
        }

        return new ResponseEntity(result,HttpStatus.OK);

    }




    /**
     * 新增数据
     * @param title
     * @param word_count
     * @return
     */
    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity add(String title,String word_count){
        try {
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("title", title)
                    .field("word_count", word_count)
                    .endObject();

            IndexResponse result = transportClient.prepareIndex("book", "novel")
                    .setSource(content)
                    .get();

            return new ResponseEntity(result.getId(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity delete(String id){
        DeleteResponse result = transportClient.prepareDelete("book", "novel", id).get();

        return new ResponseEntity(result.getResult().toString(), HttpStatus.OK);
    }


    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity update(@RequestParam(name = "id") String id,
                                 @RequestParam(name = "title",required = false) String title  ){

        UpdateRequest update = new UpdateRequest("book","novel",id);
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();

            if (StringUtils.isEmpty(builder)){
                builder.field("title",title);
            }

            builder.endObject();

            UpdateRequest doc = update.doc(builder);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            UpdateResponse result = transportClient.update(update).get();
            return new ResponseEntity(result.getResult().toString(),HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
