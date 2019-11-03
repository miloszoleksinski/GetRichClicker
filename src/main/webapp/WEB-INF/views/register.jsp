<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, user-scalable=no"/>
<style>
	*{ margin: 0; padding: 0; box-sizing: border-box;}
	body{ background-color: #f2f2f2; }
	a{ text-decoration: none; color: white; }
	.error{ color: red; } 
	a:hover{ color: #e6ac00; transition: all 0.1s linear 0s; }
	.inline{ display: inline; }
	.spaceBetweenInput{ margin: 6px; }
	.inputField{ margin: 4px 0;}
	
	.navbar{ position: fixed; top: 0; width: 100%; height: 40px; line-height: 40px; background-color: #333333; padding: 0 50px; }
	.navbarHeight{ height: 40px; }
	.navbarLogo{ display: inline-block; width: 230px; font-size: 1.3em; font-weight: bold; color: white; text-align: left; }
	.navbarOptions{ float: right; display: flex; width: 200px; }
	.navbarOption{ flex: 1; color: white; font-size: 1.1em; font-weight: bold; text-align: right; }
	.optionLink:hover{ cursor: pointer; }
	
	.mainDiv{ text-align: center; padding: 40px 0; }
	.title{ padding: 0 0 10px 0; font-size: 2em;  }
	
	.submitButton{ color: black; margin: 4px; padding: 4px 8px; border: 0.2px solid black; transition: all 0.1s linear 0s; }
	.submitButton:hover{ color: white; background-color: black; cursor: pointer; }
	
	.footer{ height: 40px; line-height: 40px; position: fixed; bottom: 0; width: 100%; background-color: #333333; color: white; text-align: center; }
</style>
<title>Register</title>
</head>
<body>
	<div class="navbar">
		<a class="navbarLogo" title="GET RICH CLICKER" href="/GetRichClicker/">GET RICH CLICKER</a>
		<sec:authorize access="isAnonymous()">
			<div class="navbarOptions">
				<div class="navbarOption"><a title="Login" class="optionLink" href="login">Login</a></div>
				<div class="navbarOption"><a title="Register" class="optionLink" href="register">Register</a></div>
			</div>
		</sec:authorize>
	</div>
	<div class="navbarHeight"></div>

	<div class="mainDiv" >
		<springForm:form method="POST" modelAttribute="userModel" action="registered">
			<h2 class="title">Sign Up</h2>
				
			<c:if test="${not empty mailError}"><div class="error">${mailError}</div><h3 class="spaceBetweenInput"></h3></c:if>
			<c:if test="${not empty nameError}"><div class="error">${nameError}</div><h3 class="spaceBetweenInput"></h3></c:if>
			<c:if test="${not empty registered}"><div class="error">${registered}</div><h3 class="spaceBetweenInput"></h3></c:if>
				
			<springForm:errors path="username" cssClass="error" />
			<h3></h3>
			<springForm:input class="inputField" path="username" placeholder="Username" />
			<h3></h3>
				
			<springForm:errors path="password" cssClass="error" />
			<h3></h3>
			<springForm:password class="inputField" path="password" placeholder="Password" />
			<h3></h3>
			<springForm:errors path="matchingPassword" cssClass="error" />
			<h3></h3>
			<springForm:password class="inputField" path="matchingPassword" placeholder="Confirm Password" />
			<h3></h3>
				
			<springForm:errors path="email" cssClass="error" />
			<h3></h3>
			<springForm:input class="inputField" path="email" name="email" placeholder="Email" />
			<h3></h3>
			<input class="submitButton" type="submit" value="Submit">
		</springForm:form>
	</div>
	
	<div class="footer">Get Rich Clicker</div>
</body>
</html>