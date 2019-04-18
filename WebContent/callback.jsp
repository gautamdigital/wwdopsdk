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
	href="http://localhost:8080/openpay-gateway/css/style.css"
	type="text/css" media="all" />
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
	<%
		orderObj.setPlanId(request.getParameter("planid"));
		orderObj.setOrderId(request.getParameter("orderid"));

		if (null != orderObj && null != orderObj.getPlanId()) {
			com.dapl.payment.openpay.controller.OnlineOrderCapturePayment onlineOrderCapturePayment = new com.dapl.payment.openpay.controller.OnlineOrderCapturePayment();
			onlineOrderCapturePayment.capturePayment(orderObj.getPlanId());
		} else {
			out.println("JamPlanID null");

		}
	%>


	<div id="main" class="site-main">
		<div class="section">
			<div class="container">
				<div class="plan-details">

					<p>
						PlanId :<%
						out.print(orderObj.getPlanId());
					%>
					</p>
					<p>
						OrderId :<%
						out.print(orderObj.getOrderId());
					%>
					</p>
					<p class="orderstatus" style="display: none;"></p>
					<p class="orderapprovestatus" style="display: none;"></p>
					<p class="Planstatus" style="display: none;"></p>
					<p class="orderdispatchstatus" style="display: none;"></p>
				</div>
				<div class="form-buttom">
					<form action="" id="myform" name="myform" method="post">					
						<input type="hidden" name="planId"
							value="<%out.print(orderObj.getPlanId());%>" /> <input
							type="button" value="Order Status" onclick="orderStatus()">
					</form>


					<div id="ex1" class="modal">
						<p>Refund Amount</p>
						<form action="" id="myrefundform" name="myrefundform"
							method="post">
							<p class="refunfstatus" style="display: none;"></p>
							<p>
								PlanId :<%
								out.print(orderObj.getPlanId());
							%>
							</p>
							<p>
								Amount: <input type="text" id="reductionPrice"
									name="reductionPrice">
							</p>

							<input type="hidden" id="planId1" name="planId1"
								value="<%out.print(orderObj.getPlanId());%>" /> <input
								type="button" onclick="refundAmount()" value="Submit Refund">

						</form>
						<a href="#" rel="modal:close" style="display: none;">Close</a>
					</div>

					<!-- Link to open the modal -->
					<p class="refund-popup">
						<a href="#ex1" rel="modal:open">Refund</a>
					</p>

					<form action="" id="dispatchform" name="dispatchform" method="post">
						<p>
							<input type="hidden" name="planId" id="planId"
								value="<%out.print(orderObj.getPlanId());%>" />
						</p>

						<input type="button" onclick="orderDispatch()"
							value="Order Dispatch">
					</form>
					<div id="ex2" class="modal">
					<p>Fraud Alert</p>
						<form action="" id="fraudAlertform" name="fraudAlertform" method="post">
						<p class="fraudalertstatus" style="display: none;"></p>
							<p>
								<input type="hidden" name="planId2" id="planId2"
									value="<%out.print(orderObj.getPlanId());%>" />
							</p>
							
							<p>
								<input type="text" name="details" id="details"
									value="" />
									<input type="button" onclick="fraudAlert()"
								value="Submit">
							</p>
	
							
						</form>
					</div>
					<p class="fruadalert-popup">
						<a href="#ex2" rel="modal:open">Fraud Alert</a>
					</p>
					
					<form action="" id="minMaxform" name="minMaxform" method="post">					
						<input type="button" value="Check Min Max Purchase Price" onclick="checkMinMaxPrice()">
					</form>
					<div id="ex3" class="modal">
					<p> Min Max Purchase Price Checking </p>					
					    <p class="minmaxpricestatus" style="display: none;"></p>
						<p>MinPrice: <span class="minprice" style="dispaly:none;"></span></p>
						<p>MaxPrice: <span class="maxprice"  style="dispaly:none;"></span></p>
					</div>
					
					
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

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>

	<!-- jQuery Modal -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css"
		type="text/css" media="all" />

	<script>
		function refundAmount() {

			var planId = $("#planId1").val();
			var reductionPrice = $("#reductionPrice").val();
			if (reductionPrice != '') {
				reductionPrice = reductionPrice;
			} else {
				reductionPrice = "0.00";
			}
			$.ajax({
						url : '${pageContext.request.contextPath}/onlineOrderRefundURL',
						type : 'POST',
						data : {
							planId : planId,
							reductionPrice : reductionPrice
						},
						success : function(msg) {
							var xml = msg,
							xmlDoc = $.parseXML(xml),
							$xml = $(xmlDoc),
							$status = $xml.find("status");
							$reason = $xml.find("reason");
							//alert($status.text());   
							if ($status.text() == 0) {
								$('.refunfstatus').html('Refund Successfully').show().delay(3000).fadeOut();
							} else if ($reason.text()) {
								$('.refunfstatus').html($reason.text()).show().delay(3000).fadeOut();
							} else {
								$('.refunfstatus').hide();
							}
						}
					});
		}

		function orderDispatch() {

			var planId = $("#planId").val();
			$.ajax({
						url : '${pageContext.request.contextPath}/onlineOrderDispatchURL',
						type : 'POST',
						data : {
							planId : planId,
						},						
						success : function(msg) {
							var xml = msg,
							xmlDoc = $.parseXML(xml),
							$xml = $(xmlDoc),
							$status = $xml.find("status");
							$reason = $xml.find("reason");
							//alert($status.text());   
							if ($status.text() == 0) {
								$('.orderdispatchstatus').html('Dispatch Successfully').show().delay(3000).fadeOut();
							} else if ($reason.text()) {
								$('.orderdispatchstatus').html($reason.text()).show().delay(3000).fadeOut();
							} else {
								$('.orderdispatchstatus').hide();
							}
							
							
						}
					});
		}

		function orderStatus() {

			var planId = $("#planId").val();
			$.ajax({
						url : '${pageContext.request.contextPath}/onlineOrderStatusURL',
						type : 'POST',
						data : {
							planId : planId,
						},						
						success : function(msg) {
							var xml = msg,
							xmlDoc = $.parseXML(xml),
							$xml = $(xmlDoc),
							$status = $xml.find("status");
							$reason = $xml.find("reason");
							$OrderStatus = $xml.find("OrderStatus");
							$PlanStatus = $xml.find("PlanStatus");
							//alert($status.text());   
							if ($status.text() == 0) {
								$('.orderstatus').html('Successful').show().delay(3000).fadeOut();
								$('.orderapprovestatus').html($OrderStatus.text()).show().delay(3000).fadeOut();
								$('.Planstatus').html($PlanStatus.text()).show().delay(3000).fadeOut();
								
								
							} else if ($reason.text()) {
								$('.orderstatus').html($reason.text()).show().delay(3000).fadeOut();
							} else {
								$('.orderstatus').hide();
							}
						}
					});
		}
		
		
		function fraudAlert() {

			var planId = $("#planId2").val();
			var details = $("#details").val();
						
			if (details != '') {
				details = details;
			
			$.ajax({
						url : '${pageContext.request.contextPath}/onlineOrderFraudAlertURL',
						type : 'POST',
						data : {
							planId : planId,
							details : details
						},
						success : function(msg) {
							var xml = msg,
							xmlDoc = $.parseXML(xml),
							$xml = $(xmlDoc),
							$status = $xml.find("status");
							$reason = $xml.find("reason");
							//alert($status.text());   
							if ($status.text() == 0) {
								$('.fraudalertstatus').html('Successfully lodged').show().delay(3000).fadeOut();
							} else if ($reason.text()) {
								$('.fraudalertstatus').html($reason.text()).show().delay(3000).fadeOut();
							} else {
								$('.fraudalertstatus').hide();
							}
						}
					});
			
			} else {
				alert('please enter some value');
			}
		}
		
		function checkMinMaxPrice() {			
			$.ajax({
						url : '${pageContext.request.contextPath}/onlineMinMaxPurchasePriceURL',
						type : 'POST',
						data : {},						
						success : function(msg) {
							var xml = msg,
							xmlDoc = $.parseXML(xml),
							$xml = $(xmlDoc),
							$status = $xml.find("status");
							$reason = $xml.find("reason");
							$MinPrice = $xml.find("MinPrice");
							$MaxPrice = $xml.find("MaxPrice");						   
							$('#ex3').modal('show');
							
							if ($status.text() == 0) {
								$('.minmaxpricestatus').html('Success').show();
								$('.minprice').html($MinPrice.text()).show();
								$('.maxprice').html($MaxPrice.text()).show();
								
							} else if ($reason.text()) {
								$('.minmaxpricestatus').html($reason.text()).show();
								$('.minprice').hide();
								$('.maxprice').hide();
							} else {
								$('.minmaxpricestatus').hide();
								$('.minprice').hide();
								$('.maxprice').hide();
							}
							
						}
					});
		}

	</script>
</body>
</html>