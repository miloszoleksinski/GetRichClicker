<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, user-scalable=no"/>
<title>Access Denied</title>
<style>
	*{ margin: 0; padding: 0; box-sizing: border-box;}
	body{ background-color: #f2f2f2; }
	a{ text-decoration: none; color: white; }
	a:hover{ color: #e6ac00; transition: all 0.1s linear 0s; }
	.inline{ display: inline; }
	
	.navbar{ position: fixed; top: 0; width: 100%; height: 40px; line-height: 40px; background-color: #333333; padding: 0 50px; }
	.navbarHeight{ height: 40px; }
	.navbarLogo{ display: inline-block; width: 230px; font-size: 1.3em; font-weight: bold; color: white; text-align: left; }
	.navbarOptions{ float: right; display: flex; width: 200px; }
	.navbarOption{ flex: 1; color: white; font-size: 1.1em; font-weight: bold; text-align: right; }
	.optionLink:hover{ cursor: pointer; }
	
	.footer{ height: 40px; line-height: 40px; position: fixed; bottom: 0; width: 100%; background-color: #333333; color: white; text-align: center; }
</style>
</head>
<body>
	<div class="navbar">
		<a class="navbarLogo" title="GET RICH CLICKER" href="/GetRichClicker/">GET RICH CLICKER</a>
		<div class="navbarOptions">
			<sec:authorize access="isAnonymous()">
				<div class="navbarOption"><a title="Login" class="optionLink" href="login">Login</a></div>
				<div class="navbarOption"><a title="Register" class="optionLink" href="register">Register</a></div>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<form id="logout" action="/GetRichClicker/logout" method="post" >
	 				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
				<c:if test="${pageContext.request.userPrincipal.name != null}">
					<a class="navbarOption" title="Logout" href="javascript:document.getElementById('logout').submit()">Logout</a>
				</c:if>
			</sec:authorize>
		</div>
	</div>
	<div class="navbarHeight"></div>
	Access Denied

	<div class="footer">Get Rich Clicker</div>
</body>
</html>