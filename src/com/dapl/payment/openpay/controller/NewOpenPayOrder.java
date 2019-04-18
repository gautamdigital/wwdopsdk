package com.dapl.payment.openpay.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;

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

public class NewOpenPayOrder {

	public String createOrder() {
		String planId = "";
		try {
			//String url = "https://retailer.myopenpay.com.au/ServiceTraining/JAMServiceImpl.svc/NewOnlineOrder";
			String url = "https://integration.training.myopenpay.co.uk/JamServiceImpl.svc/NewOnlineOrder";
			
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			// add header
			// post.setHeader("User-Agent", USER_AGENT);
			post.setHeader(HttpHeaders.CONTENT_TYPE, "application/xml");

			String input = generateXml();
			HttpEntity stringEntity = new StringEntity(input, ContentType.APPLICATION_XML);
			post.setEntity(stringEntity);

			HttpResponse response = client.execute(post);
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + post.getEntity());
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());
			JSONObject xmlJSONObj = XML.toJSONObject(result.toString());
			System.out.println(xmlJSONObj.toString());
			Object p = xmlJSONObj.getJSONObject("ResponseNewOnlineOrder").get("PlanID");
			planId = String.valueOf(p);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return planId;
	}

	private String generateXml() {

		String input = "";
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			

			// root element
			Element rootElement = doc.createElement("NewOnlineOrder");
			doc.appendChild(rootElement);

			// jamauthtoken element
			Element jamauthtoken = doc.createElement("JamAuthToken");
			rootElement.appendChild(jamauthtoken);
			jamauthtoken.appendChild(doc.createTextNode("90000000000000005|ec2d8205-7ea8-408b-885c-81a8168ebe21"));
			

			// jamtoken element
			//Element jamtoken = doc.createElement("JamToken");
			//rootElement.appendChild(jamtoken);
			//jamtoken.appendChild(doc.createTextNode("90000000000000005"));

			// purchaseprice element
			Element purchaseprice = doc.createElement("PurchasePrice");
			rootElement.appendChild(purchaseprice);
			purchaseprice.appendChild(doc.createTextNode("250.00"));

			// PlanCreationType element
			Element plancreationtype = doc.createElement("PlanCreationType");
			rootElement.appendChild(plancreationtype);
			plancreationtype.appendChild(doc.createTextNode("Pending"));

			// RetailerOrderNo element
			Element retailerOrderNo = doc.createElement("RetailerOrderNo");
			rootElement.appendChild(retailerOrderNo);
			retailerOrderNo.appendChild(doc.createTextNode("O3562342"));

			// ChargeBackCount element
			//Element chargeBackCount = doc.createElement("ChargeBackCount");
			//rootElement.appendChild(chargeBackCount);
			//chargeBackCount.appendChild(doc.createTextNode("0"));

			// CustomerQuality element
		//	Element customerQuality = doc.createElement("CustomerQuality");
			//rootElement.appendChild(customerQuality);
			//customerQuality.appendChild(doc.createTextNode("1"));

			// FirstName element
			Element firstName = doc.createElement("FirstName");
			rootElement.appendChild(firstName);
			firstName.appendChild(doc.createTextNode("John"));

			// OtherNames element
			Element otherNames = doc.createElement("OtherNames");
			rootElement.appendChild(otherNames);
			otherNames.appendChild(doc.createTextNode("Arthur"));

			// FamilyName element
			Element familyName = doc.createElement("FamilyName");
			rootElement.appendChild(familyName);
			familyName.appendChild(doc.createTextNode("Smith"));

			// Email element
			Element email = doc.createElement("Email");
			rootElement.appendChild(email);
			email.appendChild(doc.createTextNode("johnsmith@somewhere.com"));

			// DateOfBirth element
			Element dateOfBirth = doc.createElement("DateOfBirth");
			rootElement.appendChild(dateOfBirth);
			dateOfBirth.appendChild(doc.createTextNode("10May 1980"));

			// Gender element
			Element gender = doc.createElement("Gender");
			rootElement.appendChild(gender);
			gender.appendChild(doc.createTextNode("M"));

			// PhoneNumber element
			Element phoneNumber = doc.createElement("PhoneNumber");
			rootElement.appendChild(phoneNumber);
			phoneNumber.appendChild(doc.createTextNode("0419 303 999"));

			// ResAddress1 element
			Element resAddress1 = doc.createElement("ResAddress1");
			rootElement.appendChild(resAddress1);
			resAddress1.appendChild(doc.createTextNode("61 Wellfield Road"));

			// ResAddress2 element
			Element resAddress2 = doc.createElement("ResAddress2");
			rootElement.appendChild(resAddress2);
			resAddress2.appendChild(doc.createTextNode(""));

			// ResSuburb element
			Element resSuburb = doc.createElement("ResSuburb");
			rootElement.appendChild(resSuburb);
			resSuburb.appendChild(doc.createTextNode("Roath"));

			// ResState element
			Element resState = doc.createElement("ResState");
			rootElement.appendChild(resState);
			resState.appendChild(doc.createTextNode("Cardiff"));

			// ResPostCode element
			Element resPostCode = doc.createElement("ResPostCode");
			rootElement.appendChild(resPostCode);
			resPostCode.appendChild(doc.createTextNode("CF24 3DG"));

			// DelAddress1 element
			Element delAddress1 = doc.createElement("DelAddress1");
			rootElement.appendChild(delAddress1);
			delAddress1.appendChild(doc.createTextNode("61 Wellfield Road"));

			// DelAddress2 element
			Element delAddress2 = doc.createElement("DelAddress2");
			rootElement.appendChild(delAddress2);
			delAddress2.appendChild(doc.createTextNode(""));

			// DelSuburb element
			Element delSuburb = doc.createElement("DelSuburb");
			rootElement.appendChild(delSuburb);
			delSuburb.appendChild(doc.createTextNode("Roath"));

			// DelState element
			Element delState = doc.createElement("DelState");
			rootElement.appendChild(delState);
			delState.appendChild(doc.createTextNode("Cardiff"));

			// DelPostCode element
			Element delPostCode = doc.createElement("DelPostCode");
			rootElement.appendChild(delPostCode);
			delPostCode.appendChild(doc.createTextNode("CF24 3DG"));

			
			
			
			// BasketData element
			Element basketdata = doc.createElement("BasketData");
			rootElement.appendChild(basketdata);

			// setting attribute to element
			// Attr attr = doc.createAttribute("company");
			// attr.setValue("Ferrari");
			// supercar.setAttributeNode(attr);

			// BasketData element
			//Element basketitem = doc.createElement("BasketItem");
			//basketdata.appendChild(basketitem);

			// carname element
			//Element itemname = doc.createElement("ItemName");
			// Attr attrType = doc.createAttribute("type");
			// attrType.setValue("formula one");
			// carname.setAttributeNode(attrType);
			//itemname.appendChild(doc.createTextNode("Ferrari 101"));
			//basketitem.appendChild(itemname);

			//Element itemgroup = doc.createElement("ItemGroup");
			// Attr attrType1 = doc.createAttribute("type");
			// attrType1.setValue("sports");
			// carname1.setAttributeNode(attrType1);
			//itemgroup.appendChild(doc.createTextNode("Ferrari 202"));
			//basketitem.appendChild(itemgroup);
			
			  
			  
			  
			  
			  // Itemcode Element
			 // Element itemcode = doc.createElement("Itemcode");
			 // itemcode.appendChild(doc.createTextNode("1234567890"));
			 // basketitem.appendChild(itemcode);
			  
			  // ItemGroupcode 
			  //Element itemgropucode = doc.createElement("ItemGroupcode");
			 // itemgropucode.appendChild(doc.createTextNode("F123"));
			 // basketitem.appendChild(itemgropucode);
			  
			  // ItemRetailUnitPrice
			  // Element itemretailunitprice = doc.createElement("ItemRetailUnitPrice");
			  //itemretailunitprice.appendChild(doc.createTextNode("123.00"));
			 // basketitem.appendChild(itemretailunitprice);
			  
			  // ItemQty
			  // Element itemqty = doc.createElement("ItemQty");
			 // itemqty.appendChild(doc.createTextNode("4"));
			///  basketitem.appendChild(itemqty);
			  
			  // ItemRetailCharge 
			 // Element itemretailcharge = doc.createElement("ItemRetailCharge");
			  //itemretailcharge.appendChild(doc.createTextNode("122.03"));
			  //basketitem.appendChild(itemretailcharge);
			 
			  
			  
			  
			// TenderTypes element
			//Element tendertypes = doc.createElement("TenderTypes");
			//rootElement.appendChild(tendertypes);

			// TenderType element
			//Element tendertype = doc.createElement("TenderType");
			//tendertypes.appendChild(tendertype);

			// Tender
			//Element tender = doc.createElement("Tender");
			//tender.appendChild(doc.createTextNode("Name"));
			//tendertype.appendChild(tender);

			// Amount
			//ement amount = doc.createElement("Amount");
		//amount.appendChild(doc.createTextNode("100.00"));
		//tendertype.appendChild(amount);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
			input = writer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;

	}

	public static void main(String[] args) {

	}

}
