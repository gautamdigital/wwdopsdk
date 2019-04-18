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

@WebServlet("/onlineOrderDispatchURL")
public class OnlineOrderDispatchPlan extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("-----IN Order Dispatch------> " + request.getParameter("planId"));
		String resp = onlineOrderDispatchPlan(request.getParameter("planId"));
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(resp);

	}

	public String onlineOrderDispatchPlan(String planId) {

		StringBuffer result = null;

		System.out.println("-----IN Online Order Dispatch------> " + planId);
		try {
			//String url = "https://retailer.myopenpay.com.au/ServiceTraining/JAMServiceImpl.svc/OnlineOrderDispatchPlan";
			String url = "https://integration.training.myopenpay.co.uk/JamServiceImpl.svc/OnlineOrderDispatchPlan";

			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			// add header
			// post.setHeader("User-Agent", USER_AGENT);
			post.setHeader(HttpHeaders.CONTENT_TYPE, "application/xml");

			String input = generateOnlineOrdXrDispatchPlan(planId);
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

			System.out.println("Response online order dispatchplan --- > " + result.toString());
//			JSONObject xmlJSONObj = XML.toJSONObject(result.toString());
//			System.out.println(xmlJSONObj.toString());
//			Object p = xmlJSONObj.getJSONObject("ResponseNewOnlineOrder").get("PlanID");

//			planId = String.valueOf(p);
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return result.toString();

	}

	public static String generateOnlineOrdXrDispatchPlan(String planId) {

		String input = "";
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			// root element
			Element rootElement = doc.createElement("OnlineOrderDispatchPlan");
			doc.appendChild(rootElement);

			// jamauthtoken element
			Element jamauthtoken = doc.createElement("JamAuthToken");
			rootElement.appendChild(jamauthtoken);
			jamauthtoken.appendChild(doc.createTextNode("90000000000000005|ec2d8205-7ea8-408b-885c-81a8168ebe21"));

			// planId element
			Element PlanID = doc.createElement("PlanID");
			rootElement.appendChild(PlanID);
			PlanID.appendChild(doc.createTextNode(planId));

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
