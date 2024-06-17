package com.Dashboard;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

import dto.Common.DtoCommon;

import com.Common.Common;

/**
 * Servlet implementation class Dashboard
 */
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	HttpSession session=null;
	Common cmn = new Common();
    public Dashboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		String query="",addqr="",addqr1="";
		
		Map<String , Integer> group_Count = new HashMap<String , Integer>();
		/*String id_grp = request.getParameter("id_grp");*/
		 String id_sgrp ="",id_sgrp1="";
		 String id_grp = "";
			
			String tempSql = "";
			id_grp = request.getParameter("id_grp");
			System.out.println(id_grp);
			if(id_grp!=null) {
				if(id_grp.equals("") || id_grp.equals(null) ){
				tempSql ="";
				}
				else {
				tempSql =" and wh.id_grp="+id_grp+"";
				}
				}
		 if(request.getParameter("group")!=null) {
			 id_sgrp=request.getParameter("group");
			 if(!id_sgrp.equals("")) {
				 addqr="and wh.id_grp="+id_sgrp+"";
			 }
			 
		 }
		 if(request.getParameter("subgrp")!=null) {
			 id_sgrp1=request.getParameter("subgrp");
			 if(!id_sgrp1.equals("")) {
				 addqr1="and wh.id_sgrp="+id_sgrp1+"";
			 }
			 
		 }
		 
        String UserType = (String) session.getAttribute("UserType");
        String id_dept = (String) session.getAttribute("DeptId");
        int UserId = (int)session.getAttribute("UserId");
        DtoCommon dtoCommon = new DtoCommon();
        if(UserType.equals("Super")){
		    if(!id_sgrp.equals("")) {
		    	query = "SELECT COUNT(id_sgrp) as Count_of_Subgp,sgrp.nm_s_assetdiv from A_Ware_House wh,M_Subasset_Div sgrp " +
						" where wh.id_sgrp=sgrp.id_s_assetdiv "+tempSql+"  "+addqr+"  group by nm_s_assetdiv";
		     
			 }
		    
		    if(!id_sgrp1.equals("")) {
		    	query = "SELECT COUNT(wh.id_model) as Count_of_Subgp,sgrp.nm_model from A_Ware_House wh,m_model sgrp " +
						" where wh.id_model=sgrp.id_model "+tempSql+"  "+addqr1+"  group by nm_model";
		    
		    	
		    }
		    if(id_sgrp1.equals("") && id_sgrp.equals("")) 
		    {
		    	query = "SELECT COUNT(id_sgrp) as Count_of_Subgp,sgrp.nm_s_assetdiv from A_Ware_House wh,M_Subasset_Div sgrp " +
						" where wh.id_sgrp=sgrp.id_s_assetdiv "+tempSql+"  group by nm_s_assetdiv";
		    
		    }
        }
        else
        {
        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType,"wh","wh", request);
        	if(!id_sgrp.equals("")) {
		    	query = "SELECT COUNT(id_sgrp) as Count_of_Subgp,sgrp.nm_s_assetdiv from A_Ware_House wh,M_Subasset_Div sgrp " +
						" where  wh.id_sgrp=sgrp.id_s_assetdiv "+tempSql+"  "+addqr+" "+tempQuery2+"  group by nm_s_assetdiv";
		     
			 }
		    
		    if(!id_sgrp1.equals("")) {
		    	query = "SELECT COUNT(wh.id_model) as Count_of_Subgp,sgrp.nm_model from A_Ware_House wh,m_model sgrp " +
						" where  wh.id_model=sgrp.id_model "+tempSql+"  "+addqr1+" "+tempQuery2+"  group by nm_model";
		    
		    	
		    }
		    if(id_sgrp1.equals("") && id_sgrp.equals("")) 
		    {
		    	query = "SELECT COUNT(id_sgrp) as Count_of_Subgp,sgrp.nm_s_assetdiv from A_Ware_House wh,M_Subasset_Div sgrp " +
						" where wh.id_dept='"+id_dept+"' and wh.id_sgrp=sgrp.id_s_assetdiv "+tempSql+" "+tempQuery2+"  group by nm_s_assetdiv";
		    
		    }
        }
		 
        
        System.out.println("queRYQuery ="+query);
        try {
			con = cmn.GetConnection(request);
			ps = con .prepareStatement(query);
			rs =  ps.executeQuery();
			
			while (rs.next()) {
				 group_Count.put(rs.getString(2),rs.getInt(1));
			}
			System.out.println("Group Count=============================="+group_Count);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//doPost(request, response);
		//Map<String , Integer> getData1 =getData();
		response.setContentType("appliction/json;charset=UTF-8");
		//request.setAttribute("dat", getData1);
		//JSONArray data = new JSONArray();
	//	data.put(group_Count);
	    String json= new Gson().toJson(group_Count);
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
		
		
		Map<String , Integer> group_Count = new HashMap<String , Integer>();
		
		
		String query = "select count(id_sgrp) as Count_of_Subgp ,sgp.nm_s_assetdiv from A_Ware_House wh , M_Subasset_Div sgp  "+
					" where wh.id_sgrp=sgp.id_s_assetdiv and id_grp=1 and id_sgrp in (select id_s_assetdiv from M_Subasset_Div where id_assetdiv=1) group by sgp.nm_s_assetdiv";
		
		try {
			con = cmn.GetConnection(request);
			ps = con .prepareStatement(query);
			rs =  ps.executeQuery();
			
			while (rs.next()) {
				 group_Count.put(rs.getString(2),rs.getInt(1));
			}
			System.out.println("Group Count=============================="+group_Count);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return group_Count;
	  
  }*/
}
