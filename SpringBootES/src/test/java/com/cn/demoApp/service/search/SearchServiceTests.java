package com.cn.demoApp.service.search;

import com.cn.demoApp.ApplicationTests;
import com.cn.demoApp.service.ServiceMultiResult;
import com.cn.demoApp.web.form.RentSearch;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;



/**
 *
 */
public class SearchServiceTests extends ApplicationTests {

    @Autowired
    private ISearchService searchService;

    /**
     *  insert  or  update
     */
    @Test
    public void testIndex() {
        Long targetHouseId = 15L;
        searchService.index(targetHouseId);
    }

    /**
     * delete
     */
    @Test
    public void testRemove() {
        Long targetHouseId = 15L;

        searchService.remove(targetHouseId);
    }

    @Test
    public void testQuery() {
        RentSearch rentSearch = new RentSearch();
        rentSearch.setCityEnName("bj");
        rentSearch.setStart(0);
        rentSearch.setSize(10);
        rentSearch.setKeywords("国贸");
        ServiceMultiResult<Long> serviceResult = searchService.query(rentSearch);
        Assert.assertTrue(serviceResult.getTotal() > 0);
    }
}
