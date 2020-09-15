package com.pc.dubboprovider.test;

import com.google.common.cache.CacheLoader;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 测试包依赖
 *
 * @author pengchao
 * @date 15:20 2020-09-10
 */
@Data
public class Test {

    public void test() {
        CacheLoader cacheLoader = new CacheLoader() {
            @Override
            public Object load(Object o) throws Exception {
                return null;
            }
        };


        StringUtils.isNoneBlank("");
    }
}
