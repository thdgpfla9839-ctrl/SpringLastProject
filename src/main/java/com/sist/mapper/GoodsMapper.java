package com.sist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sist.vo.FoodVO;
import com.sist.vo.GoodsVO;

public interface GoodsMapper {

	// 최근에는 이 방식으로 많이 사용 => 인라인뷰
		@Select("SELECT no,goods_name,goods_sub,goods_price,goods_discount,goods_first_price,goods_delivery,goods_poster,hit,num "
				+"FROM (SELECT no,goods_name,goods_sub,goods_price,goods_discount,goods_first_price,goods_delivery,goods_poster,hit,rownum as num "
				+"FROM (SELECT no,goods_name,goods_sub,goods_price,goods_discount,goods_first_price,goods_delivery,goods_poster,hit "
				+"FROM goods_all ORDER BY no ASC)) "
				+"WHERE num BETWEEN #{start} AND #{end}")
		
		// 변수 두개를 집어넣을 떄는 이 방식으로 
		public List<GoodsVO> goodsListData(@Param("start") int start, @Param("end") int end);
		
		@Select("SELECT CEIL(COUNT(*)/12.0) FROM goods_all")
		public int goodsTotalPage();
		
		@Select("SELECT no,goods_name,goods_sub,goods_price,goods_discount,goods_first_price,goods_delivery,goods_poster,hit "
				+"FROM goods_all "
				+"WHERE no=#{no}")
		public GoodsVO goodsDetailData(int no);
}
