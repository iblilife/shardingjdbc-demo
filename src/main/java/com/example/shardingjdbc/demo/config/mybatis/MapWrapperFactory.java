package com.example.shardingjdbc.demo.config.mybatis;

import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;
import java.util.Objects;

/**
 * @ClassName MapWrapperFactory
 * @Description 针对Mybatis resultType 为 Map时候 下划线字段自动转换驼峰命名 处理
 * @Author iblilife@163.com
 * @Date 2019/5/16 11:26
 */
public class MapWrapperFactory implements ObjectWrapperFactory {
    @Override
    public boolean hasWrapperFor(Object object) {
        return Objects.nonNull(object) && object instanceof Map;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new MyMapWrapper(metaObject, (Map) object);
    }

    static class MyMapWrapper extends MapWrapper {
        MyMapWrapper(MetaObject metaObject, Map<String, Object> map) {
            super(metaObject, map);
        }

        @Override
        public String findProperty(String name, boolean useCamelCaseMapping) {
            if (useCamelCaseMapping) {
                return StrUtil.toCamelCase(name);
            }
            return name;
        }
    }
}
