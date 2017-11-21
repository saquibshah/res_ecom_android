package com.example.res_e_com;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import model.ModelMenu;
import model.ModelRestaurant;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utility.WebserviceSettings;

import db.SqlHandler;

import android.content.Context;
import android.util.Log;

public class WebServiceSendOrder {
	private static SqlHandler sqlHandler;
	// Namespace of the Webservice - can be found in WSDL

	// private static String NAMESPACE = "http://example.prgguru.com/";
	private static String NAMESPACE = "http://test";
	// Webservice URL - WSDL File location
//	private static String URL = "http://192.168.1.4:8080/ComplexData/services/restaurantImpl?wsdl";
	private static String URL = WebserviceSettings.webServiceIpAddress+"/ComplexData/services/restaurantImpl?wsdl";
	// SOAP Action URI again Namespace + Web method name
	// private static String SOAP_ACTION = "http://example.prgguru.com/";
	private static String SOAP_ACTION = "http://test/";

	public static String invokeHelloWorldWS(String user, String cart,
			String deliveryType, String webMethName, Context cxt) {
		sqlHandler = new SqlHandler(cxt);
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		/* send premitive type */
		// Property which holds input parameters
		PropertyInfo sayHelloPI = new PropertyInfo();
		// Set Name
		sayHelloPI.setName("user");
		// sayHelloPI.setName("celsius");
		// Set Value
		// name="jun,jun@yahoo.com,dhaka;1,3;1,2";
		sayHelloPI.setValue(user);
		// Set dataType
		sayHelloPI.setType(String.class);
		// Add the property to request object
		request.addProperty(sayHelloPI);

		// cart paramaeter
		PropertyInfo cartPI = new PropertyInfo();
		// Set Name
		cartPI.setName("cart");
		// sayHelloPI.setName("celsius");
		// Set Value
		// name="jun,jun@yahoo.com,dhaka;1,3;1,2";
		cartPI.setValue(cart);
		// Set dataType
		cartPI.setType(String.class);
		// Add the property to request object
		request.addProperty(cartPI);

		// set delivery type parameter
		// cart paramaeter
		PropertyInfo deliverPI = new PropertyInfo();
		// Set Name
		deliverPI.setName("delivery_type");
		// sayHelloPI.setName("celsius");
		// Set Value
		// name="jun,jun@yahoo.com,dhaka;1,3;1,2";
		deliverPI.setValue(deliveryType);
		// Set dataType
		deliverPI.setType(String.class);
		// Add the property to request object
		request.addProperty(deliverPI);
		/* send premitive typeends */
		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		// for object retrieval
		envelope.addMapping(NAMESPACE, "ModelRestaurant",
				new ModelRestaurant().getClass());
		// invoke primitive type
		try {
			// Invoke web service
			androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to resTxt variable static variable
			resTxt = response.toString();

		}

		// invoke object
		// try {
		// // Invoke web service
		// androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
		// // Get the response
		// List<restaurant> list = new ArrayList<restaurant>();
		// SoapObject response = (SoapObject) envelope.getResponse();
		// SoapObject soapMedicinesDTOList = (SoapObject)
		// response.getProperty("restaurant");
		// int itemCount = soapMedicinesDTOList.getPropertyCount();
		// for (int i = 0; i < itemCount; i++) {
		// restaurant item = new restaurant();
		// SoapObject soapMedicinesDTO = (SoapObject)
		// soapMedicinesDTOList.getProperty(i);
		//
		// //mine
		// Log.d("T","print "+i);
		// item.id=
		// Integer.parseInt(soapMedicinesDTO.getProperty(0).toString());
		// item.name=soapMedicinesDTO.getProperty(0).toString();
		// Log.d("T","id is "+item.id);
		// Log.d("T","name is "+item.name);
		// //mine ends
		// // if (soapMedicinesDTO. hasProperty("name")) {
		// // item.setName(soapMedicinesDTO. getPropertyAsString("name"));
		// // }
		// //
		// // if (soapMedicinesDTO.hasProperty("utility")) {
		// // item.setUtility(soapMedicinesDTO.getPropertyAsString("utility"));
		// // }
		// // bla bla bla
		//
		// list.add(item);
		// }
		// Assign it to resTxt variable static variable
		// resTxt = response.toString();
		// resTxt = "hi";
		//
		// }
		// retrieve single custom object
		// try
		// {
		// restaurant item = new restaurant();
		// androidHttpTransport.call(SOAP_ACTION, envelope);
		// SoapObject response = (SoapObject)envelope.getResponse();
		// // item.setId(id)=
		// Integer.parseInt(response.getProperty(0).toString());
		// item.setName(response.getProperty(1).toString());
		// // Log.d("T","id is "+item.);
		// Log.d("T","name is "+item.getName());
		// resTxt = item.getName();
		//
		//
		// }//retrieve single custom object ends

		// retrieve list of object starrts
		// try
		// {
		// List<ModelRestaurant> list = new ArrayList<ModelRestaurant>();
		// androidHttpTransport.call(SOAP_ACTION, envelope);
		// /** contains all arrPhongTro objects */
		// SoapObject response = (SoapObject) envelope.bodyIn;
		// // SoapObject response = (SoapObject) envelope.getResponse();
		//
		// /** lists property count */
		// final int intPropertyCount = response.getPropertyCount();
		//
		// /** loop */
		// for (int i = 0; i < intPropertyCount; i++) {
		// /** temp SoapObject */
		// SoapObject responseChild = (SoapObject) response.getProperty(i);
		//
		// /** temp PhongTro object */
		// ModelRestaurant temp = new ModelRestaurant();
		//
		// temp.setId(Integer.parseInt(responseChild.getProperty("id").toString()));
		// temp.setResName(responseChild.getProperty("resName").toString());
		// temp.setTitle(responseChild.getProperty("title")
		// .toString());
		// temp.setDeliveryCharge(Double.parseDouble(responseChild.getProperty("deliveryCharge")
		// .toString()));
		// /** Adding temp PhongTro object to list */
		// list.add(temp);
		// }
		//
		// sqlHandler.insertResDownloadedData(list);
		// }//retrieve list of object ends
		catch (Exception e) {
			// Print error
			e.printStackTrace();
			// Assign error message to resTxt
			Log.d("T", e.getMessage());
			resTxt = e.getMessage() + e.getClass().getName();
		}

		// call webservice to get all menus
		// Create request
		// request = new SoapObject(NAMESPACE, "sendMenuListToClient");
		// /* send object type*/
		// /*
		// // Property which holds input parameters
		// PropertyInfo sayHelloPI = new PropertyInfo();
		// // Set Name
		// sayHelloPI.setName("name");
		// restaurant res=new restaurant();
		// res.setId(1);
		// res.setAddress("ban");
		// res.setName("jun");
		// // sayHelloPI.setName("celsius");
		// // Set Value
		// sayHelloPI.setValue(res);
		// // Set dataType
		// sayHelloPI.setType(res.getClass());
		// // Add the property to request object
		// request.addProperty(sayHelloPI);
		// */
		// /*send object type ends */
		//
		// /* send premitive type */
		// // Property which holds input parameters
		// sayHelloPI = new PropertyInfo();
		// // Set Name
		// sayHelloPI.setName("user");
		// // sayHelloPI.setName("celsius");
		// // Set Value
		// name="jun,jun@yahoo.com,dhaka;1,3;1,2";
		// sayHelloPI.setValue(name);
		// // Set dataType
		// sayHelloPI.setType(String.class);
		// // Add the property to request object
		// request.addProperty(sayHelloPI);
		//
		// /* send premitive typeends */
		// // Create envelope
		// envelope = new SoapSerializationEnvelope(
		// SoapEnvelope.VER11);
		// // Set output SOAP object
		// envelope.setOutputSoapObject(request);
		// // Create HTTP call object
		// androidHttpTransport = new HttpTransportSE(URL);
		//
		// //for object retrieval
		// envelope.addMapping(NAMESPACE, "ModelMenu",
		// new ModelMenu().getClass());
		// invoke primitive type
		// try {
		// // Invoke web service
		// androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
		// // Get the response
		// SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
		// // Assign it to resTxt variable static variable
		// resTxt = response.toString();
		//
		// }

		// invoke object
		// try {
		// // Invoke web service
		// androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
		// // Get the response
		// List<restaurant> list = new ArrayList<restaurant>();
		// SoapObject response = (SoapObject) envelope.getResponse();
		// SoapObject soapMedicinesDTOList = (SoapObject)
		// response.getProperty("restaurant");
		// int itemCount = soapMedicinesDTOList.getPropertyCount();
		// for (int i = 0; i < itemCount; i++) {
		// restaurant item = new restaurant();
		// SoapObject soapMedicinesDTO = (SoapObject)
		// soapMedicinesDTOList.getProperty(i);
		//
		// //mine
		// Log.d("T","print "+i);
		// item.id=
		// Integer.parseInt(soapMedicinesDTO.getProperty(0).toString());
		// item.name=soapMedicinesDTO.getProperty(0).toString();
		// Log.d("T","id is "+item.id);
		// Log.d("T","name is "+item.name);
		// //mine ends
		// // if (soapMedicinesDTO. hasProperty("name")) {
		// // item.setName(soapMedicinesDTO. getPropertyAsString("name"));
		// // }
		// //
		// // if (soapMedicinesDTO.hasProperty("utility")) {
		// // item.setUtility(soapMedicinesDTO.getPropertyAsString("utility"));
		// // }
		// // bla bla bla
		//
		// list.add(item);
		// }
		// Assign it to resTxt variable static variable
		// resTxt = response.toString();
		// resTxt = "hi";
		//
		// }
		// retrieve single custom object
		// try
		// {
		// restaurant item = new restaurant();
		// androidHttpTransport.call(SOAP_ACTION, envelope);
		// SoapObject response = (SoapObject)envelope.getResponse();
		// // item.setId(id)=
		// Integer.parseInt(response.getProperty(0).toString());
		// item.setName(response.getProperty(1).toString());
		// // Log.d("T","id is "+item.);
		// Log.d("T","name is "+item.getName());
		// resTxt = item.getName();
		//
		//
		// }//retrieve single custom object ends

		// retrieve list of object starrts
		// try
		// {
		// List<ModelMenu> list = new ArrayList<ModelMenu>();
		// androidHttpTransport.call(SOAP_ACTION, envelope);
		// /** contains all arrPhongTro objects */
		// SoapObject response = (SoapObject) envelope.bodyIn;
		// // SoapObject response = (SoapObject) envelope.getResponse();
		//
		// /** lists property count */
		// final int intPropertyCount = response.getPropertyCount();
		//
		// /** loop */
		// for (int i = 0; i < intPropertyCount; i++) {
		// /** temp SoapObject */
		// SoapObject responseChild = (SoapObject) response.getProperty(i);
		//
		// /** temp PhongTro object */
		// ModelMenu temp = new ModelMenu();
		//
		// temp.setId(Integer.parseInt(responseChild.getProperty("id").toString()));
		// temp.setName(responseChild.getProperty("name").toString());
		// temp.setIngredient(responseChild.getProperty("ingredient")
		// .toString());
		// temp.setPrice(Double.parseDouble(responseChild.getProperty("price")
		// .toString()));
		// temp.setCatagory_id(Integer.parseInt(responseChild.getProperty("catagory_id").toString()));
		// temp.setRestaurant_id(Integer.parseInt(responseChild.getProperty("restaurant_id").toString()));
		// /** Adding temp PhongTro object to list */
		// list.add(temp);
		// }
		//
		// sqlHandler.insertMenuDownloadedData(list);
		// }//retrieve list of object ends
		// catch (Exception e) {
		// //Print error
		// e.printStackTrace();
		// //Assign error message to resTxt
		// Log.d("T",e.getMessage());
		// resTxt = e.getMessage()+e.getClass().getName();
		// }
		// Return resTxt to calling object
		return resTxt;
	}
}
