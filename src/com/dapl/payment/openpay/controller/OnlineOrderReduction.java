package com.dapl.payment.openpay.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@WebServlet("/onlineOrderRefundURL")
public class OnlineOrderReduction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//String status = null;
		//String reason = null;
		//String success= null;
		// System.out.println("-----IN Order Reduction Cotroller------> " +
		// request.getParameter("planId"));
		String resp = onlineOrderReduction(request.getParameter("planId"),
				Double.parseDouble(request.getParameter("reductionPrice")));
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(resp);
		//JSONObject xmlJSONObj = XML.toJSONObject(resp);
		// System.out.println(xmlJSONObj.toString());
		//Object p = xmlJSONObj.getJSONObject("ResponseOnlineOrderReduction").get("status");
		//status = String.valueOf(p);				
		//Object r = xmlJSONObj.getJSONObject("ResponseOnlineOrderReduction").get("reason");
		//reason = String.valueOf(r);	
		
		/*
		 * System.out.println("RRRRRR"+reason); System.out.println("SSSSSS"+status);
		 * if(status.toString()=="0") { success= "Refunded";
		 * System.out.println(success); }else { System.out.println(reason); }
		 */
		
		
	}

	public String onlineOrderReduction(String planId, double reductionPrice) {
		StringBuffer result = null;
		System.out.println("-----IN Online Order Reduction------> " + planId);
		try {
			//String url = "https://retailer.myopenpay.com.au/ServiceTraining/JAMServiceImpl.svc/OnlineOrderReduction";
			String url = "https://integration.training.myopenpay.co.uk/JamServiceImpl.svc/OnlineOrderReduction";
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			// add header
			// post.setHeader("User-Agent", USER_AGENT);
			post.setHeader(HttpHeaders.CONTENT_TYPE, "application/xml");

			String input = generateonlineOrderReductionXml(planId, reductionPrice);
			HttpEntity stringEntity = new StringEntity(input, ContentType.APPLICATION_XML);
			post.setEntity(stringEntity);

			HttpResponse response = client.execute(post);
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + post.getEntity());
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println("Response online order reduction --- > " + result.toString());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return result.toString();

	}

	public static String generateonlineOrderReductionXml(String planId, double reductionPrice) {

		String input = "";
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			// root element
			Element rootElement = doc.createElement("OnlineOrderReduction");
			doc.appendChild(rootElement);

			// jamauthtoken element
			Element jamauthtoken = doc.createElement("JamAuthToken");
			rootElement.appendChild(jamauthtoken);
			jamauthtoken.appendChild(doc.createTextNode("90000000000000005|ec2d8205-7ea8-408b-885c-81a8168ebe21"));

			// jamtoken element
			Element jamtoken = doc.createElement("JamToken");
			rootElement.appendChild(jamtoken);
			jamtoken.appendChild(doc.createTextNode("90000000000000005"));

			// planId element
			Element PlanID = doc.createElement("PlanID");
			rootElement.appendChild(PlanID);
			PlanID.appendChild(doc.createTextNode(planId));
			
			System.out.println("pppppppppp"+reductionPrice);

			if (reductionPrice == 0.0) {

				// NewPurchasePrice element
				Element newPurchasePrice = doc.createElement("NewPurchasePrice");
				rootElement.appendChild(newPurchasePrice);
				newPurchasePrice.appendChild(doc.createTextNode("0.00"));

				// ReducePriceBy element
				Element reducePriceBy = doc.createElement("ReducePriceBy");
				rootElement.appendChild(reducePriceBy);
				reducePriceBy.appendChild(doc.createTextNode("0.00"));

				// FullRefund element
				Element fullRefund = doc.createElement("FullRefund");
				rootElement.appendChild(fullRefund);
				fullRefund.appendChild(doc.createTextNode("True"));

			} else {

				// NewPurchasePrice element
				Element newPurchasePrice = doc.createElement("NewPurchasePrice");
				rootElement.appendChild(newPurchasePrice);
				newPurchasePrice.appendChild(doc.createTextNode("0.00"));

				// ReducePriceBy element
				Element reducePriceBy = doc.createElement("ReducePriceBy");
				rootElement.appendChild(reducePriceBy);
				reducePriceBy.appendChild(doc.createTextNode(String.valueOf(reductionPrice)));

				// FullRefund element
				Element fullRefund = doc.createElement("FullRefund");
				rootElement.appendChild(fullRefund);
				fullRefund.appendChild(doc.createTextNode("False"));

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
			input = writer.toString();
			System.out.println("request parameter");
			System.out.println(input);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;

	}
}
