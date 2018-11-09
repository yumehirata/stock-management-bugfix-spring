<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/common.jsp"  %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<div class="container">
	<h3>書籍情報登録画面</h3>
		<div class="span8">
		<div class="row">
		<table class="table table-striped">
		<form:form action="${pageContext.request.contextPath}/book/insert" modelAttribute="bookResisterForm" enctype="multipart/form-data">
				  <tr>
			    <th>
			      書籍名
			    </th>
			    <td>
					<form:errors path="name" cssStyle="color:red" element="div"/>
			    	<form:input path="name"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      著者
			    </th>
			    <td>
			    	<form:errors path="author" cssStyle="color:red" element="div"/>
			        <form:input path="author"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      出版社
			    </th>
			    <td>
			    	<form:errors path="publisher" cssStyle="color:red" element="div"/>
			      	<form:input path="publisher"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      価格
			    </th>
			    <td>
			    	<form:errors path="price" cssStyle="color:red" element="div"/>
			        <form:input path="price"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      ISBNコード
			    </th>
			    <td>
			    	<form:errors path="isbncode" cssStyle="color:red" element="div"/>
			       	<form:input path="isbncode"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      発売日
			    </th>
			    <td>
			    	<form:errors path="saledate" cssStyle="color:red" element="div"/>
			       <form:input path="saledate"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      説明
			    </th>
			    <td>
			    	<form:errors path="explanation" cssStyle="color:red" element="div"/>
			        <form:textarea path="explanation"/>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      画像
			    </th>
			    <td>
			       <input type="file" name="imageFile" accept="image/*" multiple>
			    </td>
			  </tr>
			  <tr>
			    <th>
			      在庫数
			    </th>
			    <td>
			    	<form:errors path="stock" cssStyle="color:red" element="div"/>
					<form:input path="stock"/>
			    </td>
			  </tr>
			  <tr>
			  	<td>
			  	</td>
			  	<td>
			  		<input type="submit" value="新規登録"/>
			  	</td>
			  </tr>
			  </form:form>
			</table>
			<a href="${pageContext.request.contextPath}/book/list">一覧へ戻る</a>
	</div>
	</div>
	</div>
</body>
</html>