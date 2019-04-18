package com.dapl.payment.openpay.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PaymentRequestController
 */
public class PaymentRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public PaymentRequestController() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstName = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		System.out.println("User name is----------->" + firstName + " " + lastname);
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// doGet(request, response);

		String firstName = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		System.out.println("User name is----------->" + firstName + " " + lastname);
		// String planId
		String planId = "";

		NewOpenPayOrder orderClient = new NewOpenPayOrder();
		planId = orderClient.createOrder();
		OnlineOrderCapturePayment onlineOrderCapturePayment = new OnlineOrderCapturePayment();
		if (!planId.equals("")) {
			onlineOrderCapturePayment.capturePayment(planId);
		}
		// String formUrl = "https://retailer.myopenpay.com.au/WebSalesTraining";

		String formUrl = "https://websales.training.myopenpay.co.uk";

		String JamCallbackURL = "http://localhost:8080/openpay-gateway/callback.jsp";
		String JamCancelURL = "http://localhost:8080/openpay-gateway/cancel.jsp";
		String JamFailURL = "http://localhost:8080/openpay-gateway/faliure.jsp";

		
		String url = formUrl + "?JamPlanID=" + planId
				+ "&JamAuthToken=90000000000000005|ec2d8205-7ea8-408b-885c-81a8168ebe21" + "&JamCallbackURL="
				+ JamCallbackURL + "&JamCancelURL=" + JamCancelURL + "&JamFailURL=" + JamFailURL;

		System.out.println(url);
		response.sendRedirect(url);
	}

}
