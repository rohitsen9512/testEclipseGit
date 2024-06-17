package com.Common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dto.Common.AssetOwner;
import dto.Common.UserAccessData;

/**
 * Servlet implementation class UserAccessControle
 */
public class UserAccessControle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action="",userType="",fileName="";
		HttpSession ses = request.getSession();
		JSONObject json=new JSONObject();
		
		action =  request.getParameter("action");
		try
		{
			if(action.equals("LoginAccess"))
			{
				fileName = request.getParameter("fileName");
				System.out.println("hh"+fileName);
				userType = (String) ses.getAttribute("UserType");
				System.out.println(userType);
				UserAccessData uad = new UserAccessData();
				json = uad.GetUserAccessPropertiesData(userType , fileName,request);
			}
			else if(action.equals("AssetOwnerDropDownData"))
			{
				AssetOwner ao = new AssetOwner();
				json = ao.GetAssetOwnerDataFromPropertiesFile();
			}else if(action.equals("SendEmail")){
				UserAccessData uad = new UserAccessData();
				String id_dept = request.getParameter("id_dept");
				json = uad.SendMailAccordingToDepartment(id_dept,request);
			}
			System.out.println(json);
			request.setAttribute("data", json.toString());
			response.getWriter().write(json.toString());
		}
		catch(Exception e)
		{
			System.out.println("Error in User Access Control... "+e);
		}
		
		
	}

}
