package com.sist.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sist.vo.FoodVO;
import com.sist.vo.GoodsVO;

public interface GoodsService {

	public List<GoodsVO> goodsListData(@Param("start") int start, @Param("end") int end);
	public int goodsTotalPage();
	public GoodsVO goodsDetailData(int no);
}
