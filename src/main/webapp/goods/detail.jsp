<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.row {
	margin: 0px auto;
	width: 960px;	
}
</style>
</head>
<body>
 <div class="container">
    <div class="row">
      <table class="table">
        <tr>
          <td width=30% class="text-center" rowspan="8">
            <img src="${vo.goods_poster }" style="width: 290px;height: 250px">
          </td>
          <td colspan="2">
            <h3>${vo.goods_name }&nbsp;</h3>
          </td>
        </tr>
        <tr>
          <td width=15% style="color:gray">판매가</td>
          <td width=55%>${vo.goods_price }</td>
        </tr>
        <tr>
          <td width=15% style="color:gray">할인</td>
          <td width=55%>${vo.goods_discount }</td>
        </tr>
        <tr>
          <td width=15% style="color:gray">정가</td>
          <td width=55%>${vo.goods_first_price }</td>
        </tr>
        <tr>
          <td width=15% style="color:gray">배송</td>
          <td width=55%>${vo.goods_delivery }</td>
        </tr>
        <tr>
          <td width=15% style="color:gray">조회수</td>
          <td width=55%>${vo.hit }</td>
        </tr>
      
        <tr>
          <td colspan="3">${vo.goods_sub }</td>
        </tr>
        <tr>
          <td colspan="3" class="text-right">
           <a href="../main/main.do" class="btn btn-sm btn-danger">목록</a>
          </td>
        </tr>
      </table>
    </div>
  </div>
</body>
</html>