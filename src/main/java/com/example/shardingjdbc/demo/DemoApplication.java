package com.example.shardingjdbc.demo;

import com.example.shardingjdbc.demo.mapper.popcorn.OtherTableMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private OtherTableMapper otherTableMapper;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ObjectMapper jsonMapper = new ObjectMapper();

		// 测试数据表分片
		List<Map<String, Object>> otherTableDataList = otherTableMapper.selectByOtherTableData();
		System.err.println("otherTableDataList:");
		System.err.println(jsonMapper.writeValueAsString(otherTableDataList));

		// 测试不需要分片
		// 返回数据错误，数据返回列错误
		List<Map<String, Object>> joinTableList = otherTableMapper.selectByTableAJoinTableBData();
		System.err.println("joinTableList:");
		System.err.println(jsonMapper.writeValueAsString(joinTableList));
	}
}
