<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${editUser.name}の設定</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="main-contents">

		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="message">
						<li><c:out value="${message}" />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session" />
		</c:if>

		<form action="setting" method="post">
			<br />
			<input name="id" value="${editUser.id}" id="id" type="hidden" />
			<label for="login_id">ログインID</label>
			<input name="login_id" value="${editUser.loginId}" /><br />

			<%--   ↓↓↓type="hidden"で隠す　上書きしない
				編集画面で保持済みのログインIDを上書きせずサーブレットに送信しているだけ
				サーブレットで保持済みの値と新しく入力したログインIDを比較し、同じだったら重複エラー --%>

			<input name="userLoginid" value="${editUser.loginId}" id="userLoginid" type="hidden" />
			<label for="password">パスワード</label>
			<input name="password" type="password" id="password" /><br />
			<label for="check">パスワード（確認用</label>
			<input name="check" type="password" id="check" /><br />
			<label for="name">名称</label>
			<input name="name" value="${editUser.name}" id="name" /><br />
			<label for="branches">支店</label>
			<select name="branchId" id="branchId" size="1">
				<c:forEach var="branch" items="${branches}">
					<c:choose>
						<c:when test="${editUser.branchId == branch.branchId }">
							<option value="${branch.branchId}" selected>${branch.branchName}</option>
						</c:when>
						<c:otherwise>
							<option value="${branch.branchId}">${branch.branchName}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select><br />
			<label for="positions">部署・役職</label>
			<select name="positionId" id="positionId" size="1">
				<c:forEach var="position" items="${positions}">
					<c:choose>
						<c:when test="${editUser.positionId == position.positionId}">
							<option value="${position.positionId}" selected>${position.positionName}</option>
						</c:when>
						<c:otherwise>
							<option value="${position.positionId}">${position.positionName}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select><br />
			<input type="submit" value="編集" /><br />
			<a href="./">戻る</a>
		</form>
		<div class="copyright">Copyright(c)SOEJIMA</div>
	</div>
</body>
</html>