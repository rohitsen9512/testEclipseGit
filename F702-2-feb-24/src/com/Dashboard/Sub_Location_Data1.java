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

import dto.Common.DtoCommon;

/**
 * Servlet implementation class Dashboard
 */
public class Sub_Location_Data1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	HttpSession session=null;
	Common cmn = new Common();
    public Sub_Location_Data1() {
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
		String id_loc = "";
		String typ_asst = "";
		String tempSql = "";
		String tempSql1 = "";
		String tempSql2="";
		id_loc = request.getParameter("id_loc");
		typ_asst = request.getParameter("typ_asst");
		System.out.println("id  = "+typ_asst);
		
			if(!id_loc.equals("all")){
			tempSql =" and  wh.id_loc="+id_loc+"";
			}
			
			if(!typ_asst.equals("All")){
				tempSql1 =" and  wh.typ_asst='"+typ_asst+"'";
				}
		
		Map<String , Integer> group_Count = new HashMap<String , Integer>();
		
		//id_sloc = request.getParameter("id_sloc");
        String UserType = (String) session.getAttribute("UserType");
        String id_dept = (String) session.getAttribute("DeptId");
        int UserId = (int)session.getAttribute("UserId");
        DtoCommon dtoCommon = new DtoCommon();
        if(UserType.equals("Super"))
        {
        	query = "select count(id_sgrp) as Count_of_Subgp ,subg.nm_s_assetdiv from A_Ware_House wh ,M_Subasset_Div subg " +
        			 " where  wh.id_sgrp=subg.id_s_assetdiv  "+tempSql+" "+tempSql1+" "+tempSql2+" group by subg.nm_s_assetdiv " ;
        			        	
        }
        else
        {
        	String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh", request);
        	query = "select count(id_sgrp) as Count_of_Subgp ,subg.nm_s_assetdiv from A_Ware_House wh ,M_Subasset_Div subg " +
       			 " where wh.id_sgrp=subg.id_s_assetdiv  "+tempSql+" "+tempSql1+" "+tempSql2+" "+tempQuery2+" group by subg.nm_s_assetdiv " ;
       		
        }
        	
        	
        System.out.println("queRYQuery IT Sublocation_Location_1  ="+query);
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
