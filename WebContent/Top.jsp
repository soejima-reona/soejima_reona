<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>ホーム画面</title>
	</head>
	<body>
	<center>
		<div class="main-contents">
			<div class="header">
				<a href="signup">ユーザー新規登録</a>
			</div>

			<c:if test="${ not empty user}">
				<div class="profile">
					<div class="name"><h2><c:out value="${users.name}"/></h2></div>
					<div class="account">
						<c:out value="${users.account}"/>
					</div>
					<div class="account">
						<c:out value="${users.description}"/>
					</div>
				</div>
			</c:if>

			<table border="1">
  			<tr>
    			<th>ログインID</th>
      			<th>名称</th>
      			<th>支店名</th>
      			<th>部署・役職名</th>
      			<th>アカウントの状態</th>
  			</tr>
  			<c:forEach var="user" items="${users}">

  			<tr>
    			<td>${user.loginId}</td>
    			<td>${user.name}</td>
    			<td>${user.branch}</td>
    			<td>${user.position}</td>
    			<td>
    			<c:if test="${user.stop == 0}">停止中</c:if>
    			<c:if test="${user.stop == 1}">稼働中</c:if></td>

    		<td><center><form action="stopped" method="post"><br />
    		<input name="id" value="${user.id}" id="id" type="hidden"/>

    		<c:if test="${user.stop == 0}">
    			<input class="btn1" value="復活" type="submit" onclick="return confirmProceed('復活');"/>
				<input name="stopped" value=1 id="stopped" type="hidden"/>
			</c:if>

			<c:if test="${user.stop == 1}">
				<input class="btn2" value="停止" type="submit" onclick="return confirmProceed('停止');"/>
				<input name="stopped" value=0 id="stopped" type="hidden"/>
			</c:if>

    		</form></center></td>
			<td><a href="setting?id=${user.id}">編集</a></td>
 			</tr>
 			</c:forEach>
			</table>

			<script>
				function confirmProceed(behavior) {
					return confirm(behavior + 'しますか');
				}
			</script>

			<div class="copyright">Copyright(c)SOEJIMA</div>
		</div>
	</center>
	</body>
</html>