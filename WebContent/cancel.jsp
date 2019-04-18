<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%@ page import="com.dapl.payment.openpay.controller;" %> --%>
<jsp:useBean id="orderObj"
	class="com.dapl.payment.openpay.model.OrderInfo" scope="session" />
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Openpay SDK for java</title>

<link rel="stylesheet"
	href="http://localhost:8080/openpay-gateway/css/style.css" type="text/css" media="all" />
</head>
<header>
	<div class="container">

		<a href="#" class="logo"><img
			src="http://localhost:8080/openpay-gateway/images/logo.png" alt=""></a>
		<nav>
			<ul>
				<li><a href="">Your menu 1</a></li>
				<li><a href="">Your menu 2</a></li>
				<li><a href="">Your menu 3</a></li>
				<li><a href="">Your menu 4</a></li>
			</ul>
		</nav>
	</div>
</header>
<body>

	<div id="main" class="site-main">
		<div class="section">
			<div class="container">
				<div class="plan-details">
					<p>
						PlanId: <b><%=request.getParameter("planid")%></b>
					</p>
					<p>
						Payment Status: <b><%=request.getParameter("status")%></b>
					</p>
					<p>
						Orderid: <b><%=request.getParameter("orderid")%></b>
					</p>
				</div>
			</div>
		</div>
	</div>

	<footer id="colophon" class="site-footer" role="contentinfo">

		<div class="copyright">
			<div class="container">
				<p>Copyright 2019 &copy; JavaDevelopment</p>
			</div>
		</div>
	</footer>
</body>
</html>