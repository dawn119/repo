package com.by.common.bean;

import java.util.HashMap;
import java.util.Map;

public class PageMap extends HashMap {
    int page = 1;
    int pagesize = 10;

    public PageMap(Map map) {
        if (null == map) {
            put("page", page);
            put("pageSize", pagesize);
            return;
        }
        //1.将map中的所有参数 存入当前的类型
        putAll(map);
        //判断map中是否有page
        if (map.containsKey("page")) {
            //更新当前成员的page
            page = Integer.parseInt(map.get("page").toString());
        } else {
            //将默认值存储到当前类型中
            put("page", page);
        }
        //判断map中是否有pagesize
        if (map.containsKey("pageSize")) {
            pagesize = Integer.parseInt(map.get("pageSize").toString());
        } else {
            put("pageSize", pagesize);
        }
    }

    public int getPage() {
        return page;
    }

    public int getPagesize() {
        return pagesize;
    }
}
