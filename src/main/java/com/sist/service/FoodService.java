package com.sist.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sist.vo.FoodVO;

public interface FoodService {

	public List<FoodVO> foodListData(@Param("start") int start, @Param("end") int end);
	public int foodTotalPage();
	public FoodVO foodDetailData(int no);
}
