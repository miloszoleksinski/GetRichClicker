<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, user-scalable=no"/>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<title>Home</title>
<style>
	html{ height: 100%; overflow: hidden; }
	*{ margin: 0; padding: 0; box-sizing: border-box;}
	body{ background-color: #cccccc; height: 100%; }
	a{ text-decoration: none; color: white; }
	a:hover{ color: #e6ac00; transition: all 0.1s linear 0s; }
	.inline{ display: inline; }
	.displayNone{ display: none; }
	.displayBlock{ display: block; }
	
	.navbar{ position: fixed; top: 0; width: 100%; height: 40px; line-height: 40px; background-color: #333333; padding: 0 50px; z-index: 3; }
	.navbarLogo{ display: inline-block; width: 230px; font-size: 1.3em; font-weight: bold; color: white; text-align: left; }
	.navbarOptions{ float: right; display: flex; width: 200px; }
	.navbarOption{ flex: 1; color: white; font-size: 1.1em; font-weight: bold; text-align: right; }
	.optionLink:hover{ cursor: pointer; }
	
	.mainDiv{ margin-top: 40px; width: 100%; height: 100%; display: flex; position: relative; }
	.leftSide{ width: 30%; height: 100%; position: fixed; border-right: 12px solid #333333; background-image: url("resources/images/coinsBackground2.png"); -webkit-touch-callout:none;-webkit-user-select:none;-khtml-user-select:none;-moz-user-select:none;-ms-user-select: none;-o-user-select:none;user-select:none; -webkit-user-drag: none;-khtml-user-drag: none;-moz-user-drag: none; -o-user-drag: none;user-drag: none; }
	.coinInfo{ height: 100px; width: 100%; background-color: rgba(100, 100, 100, 0.5); color: white; font-size: 1.3em; font-weight: bold; text-align: center; }
	.counterCoins{ height: 50px; line-height: 50px; font-size: 1.7em; }
	.counterCps{ font-size: 1.5em; }
	
	.middleSide{ width: 45%; height: 100%; position: fixed; left: 30%; rigt: 25%; z-index: 1; }
	.resetDiv{ height: 100px; width: 300px; line-height: 100px; border: 1px solid black; margin: auto; text-align: center; }
	.resetDiv:hover{ cursor: pointer; background-color: crimson; color: white; }
	.rightSide{ width: 25%; height: 100%; border-left: 12px solid #333333; position: fixed; right: 0; background-color: grey; }
	
	.businessListDiv{ width: 100%; height: calc(70% - 160px); overflow-y: scroll; } /*height: calc(100% - 140px);  */
	.storeTitle{ color: white; font-weight: bold; font-size: 1.5em; text-align: center; height: 80px; line-height: 80px; border-bottom: 2px solid black; border-top: 2px solid black; }
	.storeTable{ display: flex; height: 60px; background-color: grey; border-bottom: 2px solid black; font-weight: bold; text-align: center; }
	.storeTable:hover{ cursor: pointer;  filter: brightness(110%); }
	.storeTable:active{ filter: brightness(85%); }
	.tableFlex{ flex: 1; display: flex; flex-direction: column; }
	.tableTitle{ color: white; font-size: 2em; line-height: 30px; -webkit-touch-callout:none;-webkit-user-select:none;-khtml-user-select:none;-moz-user-select:none;-ms-user-select: none;-o-user-select:none;user-select:none; }
	.tableNumber{ flex: 1; font-size: 2.2em; color: white; line-height: 60px; -webkit-touch-callout:none;-webkit-user-select:none;-khtml-user-select:none;-moz-user-select:none;-ms-user-select: none;-o-user-select:none;user-select:none; }
	.tablePrice{ font-size: 1.2em; -webkit-touch-callout:none;-webkit-user-select:none;-khtml-user-select:none;-moz-user-select:none;-ms-user-select: none;-o-user-select:none;user-select:none; }
	.notEnough{ color: #ff0000; }
	.enough{ color: #8cff1a; }
	
	.upgradesDiv{ height: 30%; padding: 10px; border-top: 12px solid #333333; display: flex; flex-wrap: wrap; }
	.upgradeDiv{ height: 60px; width: 100px; background-color: #cccccc; border: 2px solid #333333; margin: 5px; -webkit-touch-callout:none;-webkit-user-select:none;-khtml-user-select:none;-moz-user-select:none;-ms-user-select: none;-o-user-select:none;user-select:none; -webkit-user-drag: none;-khtml-user-drag: none;-moz-user-drag: none; -o-user-drag: none;user-drag: none; }
	.upgradeDiv:hover{ cursor: pointer; }
	.upgradeTitle{ font-size: 1.1em; font-weight: bold; text-align: center; line-height: 53px; }
	
	.afterHoverInfo{ position: absolute; top: 0px; left: 0; pointer-events: none; background-color: #00001a; border: 3px solid #b38600; color: white; width: 100%; padding: 5px; height: 82px; z-index: 11; text-align: center; }
	.upgradeInfoWindow{ display: flex; margin-bottom: 5px; padding-bottom: 2px; font-size: 1.1em; font-weight: bold; border-bottom: 1px solid #664d00; }
	/* upgrade */
	.upgradeInfoTitle{ flex: 1; padding-left: 5px; text-align: left; }
	.upgradeInfoPrice{ flex: 1; padding-right: 5px; text-align: right; }
	/* business */
	.cpsTitle{ flex: 1; padding-left: 5px; text-align: left; }
	.cpsInfoPrice{ flex: 1; padding-right: 5px; text-align: right; }
	.infoCps{ padding: 0; margin: 0; display: inline; color: #d9d9d9; }
	/* both */
	.infoDescription{ color: #d9d9d9; }
	
	.coinDiv{ width: 300px; height: 300px; border-radius: 50%; margin: 50px auto 0; }
	.coinPng{ height: 100%; width: 100%; border-radius: 50%; transition: all 0.1s ease; -webkit-touch-callout:none;-webkit-user-select:none;-khtml-user-select:none;-moz-user-select:none;-ms-user-select: none;-o-user-select:none;user-select:none; -webkit-user-drag: none;-khtml-user-drag: none;-moz-user-drag: none; -o-user-drag: none;user-drag: none; }
	.coinPng:hover{ cursor: pointer; filter: brightness(110%); }
	.coinPng:active{ width: 284px; height: 284px; margin: 8px 0 0 8px; filter: brightness(80%); }
	
	.footer{ position: fixed; bottom: 0; height: 40px; line-height: 40px; width: 100%; background-color: #333333; color: white; text-align: center; }
	
	@media (max-width: 1500px) 
	{
		.leftSide{ width: 40%; }
		.middleSide{ width: 30%; left: 40%; rigt: 30%; }
		.rightSide{ width: 30%; }
    }
    
    @media (max-width: 1200px) 
	{
		.leftSide{ width: 55%; }
		.middleSide{ width: 0; left: 55%; rigt: 45%; }
		.rightSide{ width: 45%; }
    }
    
    @media (max-width: 1000px) 
	{
		.navbar{ height: 80px; }
		.navbarOptions{ flex-direction: column; width: 100%; }
		.navbarOption{ width: 100%; text-align: center; }
		.navbarLogo{ width: 100%; text-align: center; }
	
		.mainDiv{ flex-direction: column; margin-top: 80px; }
		.leftSide{ width: 100%; height: 70%; position: static; border: none; }
		.middleSide{ height: 0%; position: static;  }
		.rightSide{ width: 100%; position: static; border: none; }
		.businessListDiv{ height: calc(100% - 180px); }
		.afterHoverInfo{ top: 50%; }
		
		.coinDiv{ width: 200px; height: 200px; margin: 30px auto 0; }
		.coinPng:active{ width: 184px; height: 184px; margin: 8px 0 0 8px; filter: brightness(80%); }
    }
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
					<div class="navbarOption"><a title="Logout" href="javascript:document.getElementById('logout').submit()">Logout</a></div>
				</c:if>
			</sec:authorize>
		</div>
	</div>
	<div class="heightOfBar"></div>
	
	<div class="mainDiv">
		<div class="leftSide">
			<div class="coinInfo">
				<p class="counterCoins" id="counterCoinsID">Coins: ${userCoins}</p>
				<p class="counterCps" id="counterCpsID">per second: ${userCps}</p>
			</div>
			<div class="coinDiv">
				<img class="coinPng" onClick="addCoin()" src='<c:url value="/resources/images/clickedCoin2.png"/>'>
			</div>
		</div>
		<div class="middleSide">
			<div class="resetDiv" onClick="startFromScratch()">START FROM SCRATCH</div>
		</div>
		<div class="rightSide">
			<h2 class="storeTitle">STORE</h2>
				<div class="businessListDiv">
					<c:forEach items="${businessList}" var="business" varStatus="myIndex">
						<div class="storeTable" onmouseleave="hideHoverInfo('afterHoverInfo${myIndex.index}ID')" onmouseover="showHoverInfo('afterHoverInfo${myIndex.index}ID')" onClick="buyBusiness('${business.title}')">
							<div class="tableFlex">
								<h3 class="tableTitle">${business.title}</h3>
								<p id="tablePrice${myIndex.index}ID" class="tablePrice">${businessPrice[myIndex.index]}</p>
							</div>
							<p class="tableNumber" id="tableNumber${myIndex.index}ID"> ${userBusinessList[myIndex.index].boughtNumber}</p>
						</div>
						<div id="afterHoverInfo${myIndex.index}ID" class="afterHoverInfo displayNone">
							<div class="upgradeInfoWindow">
								<p class="cpsTitle">${business.title}</p>
								<p id="infoPrice${myIndex.index}ID" class="cpsInfoPrice">price: ${businessPrice[myIndex.index]}</p>
							</div>
							<div class="infoDescription">each ${business.title} produces <p class="infoCps" id="infoCps${myIndex.index}ID">${businessCps[myIndex.index]}</p> coins per second.</div>
						</div>
					</c:forEach>
				</div>
				<div class="upgradesDiv" id="upgradesDivID">
					<c:forEach items="${upgrades}" var="upgrade" varStatus="i">
						<div onmouseleave="hideHoverInfo('afterHoverUpdatesInfo${i.index}ID')" onmouseover="showHoverInfo('afterHoverUpdatesInfo${i.index}ID')" onClick="buyUpgrade('${upgrade.title}',${i.index})" class="upgradeDiv" id="upgradeDiv${i.index}ID">
							<h3 class="upgradeTitle">${upgrade.title}</h3>
							<div id="afterHoverUpdatesInfo${i.index}ID" class="afterHoverInfo displayNone">
								<div class="upgradeInfoWindow">
									<p class="upgradeInfoTitle">${upgrade.title}</p>
									<p id="upgradePrice${i.index}ID" class="upgradeInfoPrice">price: ${upgradePrices[i.index]}</p>
								</div>
								<p class="infoDescription">${upgrade.operation}</p>
							</div>
						</div>
					</c:forEach>
				</div>
		</div>
	</div>
	
	<div class="footer">Get Rich Clicker</div>
</body>
<script>
	function showHoverInfo(infoID)
	{
		var hoverInfo = document.getElementById( infoID );
		hoverInfo.className = "afterHoverInfo displayBlock";
	}
	
	function hideHoverInfo(infoID)
	{
		var hoverInfo = document.getElementById( infoID );
		hoverInfo.className = "afterHoverInfo displayNone";
	}

	function addCoin()
	{
		$.ajax({
	        url : '/GetRichClicker/addCoin',
	     	cache : false,
	        success: function(data)
	        {
	        	var counterModel = JSON.parse( data );
	        	
	        	$('#counterCoinsID').html( "Coins: " + coinsAndCps[0] );
	        	$('#counterCpsID').html( "per second: " + coinsAndCps[1] );
	        }
	    });
	}
	
	function buyBusiness(businessTitle)
	{
		var dataObj = {businessTitle : businessTitle};
		$.ajax({
	        url : '/GetRichClicker/buyBusiness',
	        data : dataObj,
	     	cache : false,
	        success: function(data)
	        {
	        	var userBusiness = JSON.parse( data );
	        	
	        	var i;
	        	for(i=0; i<userBusiness.length; i++)
	        	{
	        		$('#tableNumber'+i+'ID').html( userBusiness[i].boughtNumber );
	        	}
	        	
	        	updateUser();
	        	updatePrice();
	        }
	    });
	}
	
	function buyUpgrade(titleOfUpgrade, id)
	{
		var dataObj = {upgradeTitle : titleOfUpgrade};
		$.ajax({
	        url : '/GetRichClicker/buyUpgrade',
	        data : dataObj,
	     	cache : false,
	        success: function(data)
	        {
	        	var userBusiness = JSON.parse( data );
	        	
	        	var i;
	        	for(i=0; i<userBusiness.length; i++)
	        	{
	        		$('#infoCps'+i+'ID').html( userBusiness[i] );
	        	}
	        	
	        	updateUser();
	        	updatePrice();
	        	updateUpgrade(userBusiness, id)
	        }
	    });
	}
	
	function updateUser()
	{
		$.ajax({
	        url : '/GetRichClicker/updateUser',
	     	cache : false,
	        success: function(data)
	        {
	        	var coinsAndCps = JSON.parse( data );
	        	$('#counterCoinsID').html( "Coins: " + coinsAndCps[0] );
	        	$('#counterCpsID').html( "per second: " + coinsAndCps[1] );
	        }
	    });
	}
	
	function updateUpgrade()
	{
		location.reload();
	}
	
	function updatePrice()
	{
		$.ajax({
	        url : '/GetRichClicker/updatePrice',
	     	cache : false,
	        success: function(data)
	        {
	        	var business = JSON.parse( data );
	        	var i;
	        	for(i=0; i<business.length; i++)
	        	{
	        		$('#tablePrice'+i+'ID').html( business[i] );
	        		$('#infoPrice'+i+'ID').html( "price: " + business[i] );
	        	}
	        }
	    });
	}
	
	function startFromScratch()
	{
		$.ajax({
	        url : '/GetRichClicker/startFromScratch',
	     	cache : false,
	        success: function(data)
	        {
	        	location.reload();
	        }
	    });
	}
	
	function formatNumber(num) 
	{
		  return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1 ')
	}

	
	// ------------------------------------- INTERVALS ------------------------------------- \\
	
	$(function(){ setInterval(canAffordBusiness, 100); });
	
	window.onload = function() { canAffordBusiness(); };
	
	function canAffordBusiness() 
	{
		$.ajax({
	        url : '/GetRichClicker/canAffordBusiness',
	     	cache : false,
	        success: function(data)
	        {
	        	var affordableBusiness = JSON.parse( data );
	        	var i;
	        	for( i=0; i<affordableBusiness.length; i++)
	        	{
	        		var businessPrice = document.getElementById("tablePrice"+i+"ID");
	        		var infoPrice = document.getElementById("infoPrice"+i+"ID");
	        		if( affordableBusiness[i] == true )
	        		{ 
	        			businessPrice.className = "tablePrice enough";
	        			infoPrice.className = "cpsInfoPrice enough";
	        		}
	        		else{
	        			businessPrice.className = "tablePrice notEnough";
	        			infoPrice.className = "cpsInfoPrice notEnough"; 
	        		}
	        	}
	        }
	    });
	}
	
	$(function(){ setInterval(canAffordUpgrade, 100); });
	
	window.onload = function() { canAffordUpgrade(); };
	
	function canAffordUpgrade() 
	{
		$.ajax({
	        url : '/GetRichClicker/canAffordUpgrade',
	     	cache : false,
	        success: function(data)
	        {
	        	var affordableUpgrades = JSON.parse( data );
	        	var i;
	        	for( i=0; i<affordableUpgrades.length; i++)
	        	{
	        		var updatePrice = document.getElementById("upgradePrice"+i+"ID");
	        		if( affordableUpgrades[i] == true )
	        		{ 
	        			updatePrice.className = "upgradeInfoPrice enough";
	        		}
	        		else{
	        			updatePrice.className = "upgradeInfoPrice notEnough"; 
	        		}
	        	}
	        }
	    });
	}
	
	$(function(){ setInterval(addCoinsPerSecond, 100); });
	
	function addCoinsPerSecond() 
	{
		$.ajax({
	        url : '/GetRichClicker/updateCoinsPerSecond',
	     	cache : false,
	        success: function(data)
	        {
	        	var coins = JSON.parse( data );
	        	$('#counterCoinsID').html( "Coins: " + coins );
	        }
	    });
	}
	
</script>
</html>
