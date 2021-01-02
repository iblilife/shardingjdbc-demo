package com.example.shardingjdbc.demo.mapper.popcorn;

import com.example.shardingjdbc.demo.model.popcorn.po.OtherTable;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface OtherTableMapper extends Mapper<OtherTable> {

    List<Map<String, Object>> selectByOtherTableData();

    List<Map<String, Object>> selectByTableAJoinTableBData();
}
