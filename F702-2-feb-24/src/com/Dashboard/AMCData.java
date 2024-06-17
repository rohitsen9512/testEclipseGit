package com.Dashboard;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Common.Common;
import com.google.gson.Gson;

/**
 * Servlet implementation class Dashboard
 */
public class AMCData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	HttpSession session=null;
	Common cmn = new Common();
    public AMCData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		String query="";
		System.out.println("");
	
		Map<String , Integer> amc_count = new HashMap<String , Integer>();
		String id_grp = "";
		 String std_finance=(String) session.getAttribute("std_finance");
			String end_finance=(String) session.getAttribute("end_finance");
		String tempSql = "";
		query="select count (*) as count from A_Ware_House where dt_amc_exp between  '"+std_finance+"' AND '"+end_finance+"' ";
     System.out.println("queRYQuery group ="+query);
        try {
			con = cmn.GetConnection(request);
			ps = con .prepareStatement(query);
			rs =  ps.executeQuery();
			
			while (rs.next()) {
				 amc_count.put(rs.getString(2),rs.getInt(1));
			}
			System.out.println("AMC Count=============================="+amc_count);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//doPost(request, response);
		//Map<String , Integer> getData1 =getData();
		response.setContentType("appliction/json;charset=UTF-8");
		//request.setAttribute("dat", getData1);
		//JSONArray data = new JSONArray();
	//	data.put(amc_count);
	    String json= new Gson().toJson(amc_count);
	    System.out.println("value of json =="+json);
		response.getWriter().write(json);
        
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	   
	}
  /*public  Map<String , Integer> getData (){
	  System.out.println("inside Get Data ");
		
		
		Map<String , Integer> amc_count = new HashMap<String , Integer>();
		
		
		String query = "select count(id_sgrp) as Count_of_Subgp ,sgp.nm_s_assetdiv from A_Ware_House wh , M_Subasset_Div sgp  "+
					" where wh.id_sgrp=sgp.id_s_assetdiv and id_grp=1 and id_sgrp in (select id_s_assetdiv from M_Subasset_Div where id_assetdiv=1) group by sgp.nm_s_assetdiv";
		
		try {
			con = cmn.GetConnection(request);
			ps = con .prepareStatement(query);
			rs =  ps.executeQuery();
			
			while (rs.next()) {
				 amc_count.put(rs.getString(2),rs.getInt(1));
			}
			System.out.println("Group Count=============================="+amc_count);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return amc_count;
	  
  }*/
}
