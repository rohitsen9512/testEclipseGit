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
public class IT_DeptartmentWise extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	HttpSession session=null;
	Common cmn = new Common();
    public IT_DeptartmentWise() {
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
		String id_dept = "";
		String tempSql = "";
		id_dept = request.getParameter("id_emp_user");
		
		System.out.println("id  = "+id_dept);
		
			if(!id_dept.equals("")){
			tempSql =" and  wh.to_assign="+id_dept+"";
			}
			
		
		Map<String , Integer> group_Count = new HashMap<String , Integer>();
		
		//id_sloc = request.getParameter("id_sloc");
        String UserType = (String) session.getAttribute("UserType");
        String dept = (String) session.getAttribute("DeptId");
        if(UserType.equals("SUPER")){
        	
        	query = "select count(id_sgrp) as Count_of_Subgp ,subg.nm_s_assetdiv from A_Ware_House wh ,M_Subasset_Div subg " +
 " where wh.id_sgrp=subg.id_s_assetdiv and typ_asst='IT' "+tempSql+" group by subg.nm_s_assetdiv " ;
        	
        	
        }
        if(UserType.equals("DEPT")){
        	query = "select count(id_sgrp) as Count_of_Subgp ,subg.nm_s_assetdiv from A_Ware_House wh ,M_Subasset_Div subg" +
        			" where wh.id_sgrp=subg.id_s_assetdiv and typ_asst='IT' and wh.id_dept="+dept+"  "+tempSql+" group by subg.nm_s_assetdiv ";
		    
        }
       if(UserType.equals("IT")){
    	   query = "select count(id_sgrp) as Count_of_Subgp ,subg.nm_s_assetdiv from A_Ware_House wh ,M_Subasset_Div subg " +
       			" where wh.id_sgrp=subg.id_s_assetdiv and typ_asst = '"+UserType+"' "+tempSql+" group by subg.nm_s_assetdiv ";
	
        }
       if(UserType.equals("NIT")){
    	   query = "select count(id_sgrp) as Count_of_Subgp ,subg.nm_s_assetdiv from A_Ware_House wh ,M_Subasset_Div subg " +
       			" where wh.id_sgrp=subg.id_s_assetdiv and typ_asst = '"+UserType+"' "+tempSql+" group by subg.nm_s_assetdiv ";
	
        }
       
       query = "select count(id_sgrp) as Count_of_Subgp ,subg.nm_emp from A_Ware_House wh ,M_Emp_User subg " +
      			" where wh.to_assign=subg.id_emp_user  "+tempSql+" group by subg.nm_emp ";
	
        System.out.println("queRYQuery IT_DeptartmentWise  ="+query);
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