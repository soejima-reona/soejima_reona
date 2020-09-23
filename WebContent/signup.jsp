<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー登録</title>
</head>
<body>
	<div class="main-contents">
		<c:if test="${not empty errorMessages}">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="message">
						<li><c:out value="${message}" />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session" />
		</c:if>
		<form action="signup" method="post">
			<br /> <label for="name">ログインID</label> <input name="login_id"
				value="${login_id}" id="login_id" /><br /> <label for="password">パスワード</label>
			<input name="password" type="password" id="password" /><br /> <label
				for="check">パスワード（確認用）</label> <input name="check" type="password"
				id="check" /><br /> <label for="name">名称</label><input name="name"
				value="${name}" id="name" /><br /> <label for="branches">支店</label>
			<select name="branches" id="branches" size="1">
				<c:forEach var="br" items="${branches}">

					<c:if test="${br.branchId == branch}">
						<option value="${br.branchId}" selected>${br.branchName}</option>
					</c:if>
					<c:if test="${br.branchId != branch}">
						<option value="${br.branchId}">${br.branchName}</option>
					</c:if>
				</c:forEach>
			</select><br /> <label for="positions">部署・役職</label> <select name="positions"
				id="positions" size="1">
				<c:forEach var="po" items="${positions}">

					<c:if test="${po.positionId == position }">
						<option value="${po.positionId}" selected>${po.positionName}</option>
					</c:if>
					<c:if test="${po.positionId != position }">
						<option value="${po.positionId}">${po.positionName}</option>
					</c:if>
				</c:forEach>
			</select><br /> <br /> <input type="submit" value="新規登録" /><br /> <a
				href="./">戻る</a>
		</form>
		<div class="copyright">Copyright(c)SOEJIMA</div>
	</div>
</body>
</html>