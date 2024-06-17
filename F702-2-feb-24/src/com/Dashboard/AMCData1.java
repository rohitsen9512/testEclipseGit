package com.Dashboard;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Common.Common;
import com.google.gson.Gson;

public class AMCData1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	HttpSession session=null;
	JSONObject jobj = new JSONObject();
	JSONArray jarr = new JSONArray();
	Common cmn = new Common();
    public AMCData1() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        	
        	String strtdt[]={"-04-01","-05-01","-06-01","-07-01","-08-01","-09-01","-10-01","-11-01","-12-01","-01-01","-02-01","-03-01",};
    		String enddt[]={"-04-30","-05-31","-06-30","-07-31","-08-31","-09-30","-10-31","-11-30","-12-31","-01-30","-02-28","-03-30",};
    		String Month[]={"April","May","June","July","August","September","October","November","December","January","February","March",};
        	
        	
    		String strt1 = std_finance.substring (0,4);
			String endt1 = end_finance.substring (0,4);
        	
			for(int q=0;q<=11;q++){
				 if(Month[q]=="January" || Month[q]=="February" || Month[q]=="March"){
					 strt1=endt1;
                 }
				  
				
				
				String sql=" select count (*) as count from A_Ware_House where dt_amc_exp between  '"+strt1+""+strtdt[q]+"' AND '"+strt1+""+enddt[q]+"'  ";	
			System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
			int count=0;
			  JSONObject jobj2 = new JSONObject();
		    	
				   ResultSetMetaData metaData = rs.getMetaData();
				    int columns = metaData.getColumnCount();
				    while(rs.next())
				    {
				    	count=rs.getInt("count")	;
				    	jobj2.put(Month[q],count);
					    
			        }	
				  
			    	 jarr.put(jobj2);
			    	 System.out.println(jobj2);	
					}
        	
			jobj.put("data1", jarr);
			System.out.println(jarr);
				}
				catch(Exception e)
				{
					System.out.println("sql error in A_Install."+e);
				}
        	
        //return jobj;
        	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	   
	}
}
