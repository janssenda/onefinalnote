package com.ofn.dao.interfaces;

import com.ofn.model.Page;

import java.util.List;
import java.util.Map;

public interface PageDao {

    List<Page> getAllPages();
    Page addPage(Page page);
    boolean updatePage(Page page);
    boolean removePage(int pageId);

    Page getPage(int pageId);
    Map<Integer, String> getLinks();

}
