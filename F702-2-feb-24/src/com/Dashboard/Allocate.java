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

import com.Common.Common;
import com.google.gson.Gson;

/**
 * Servlet implementation class Dashboard
 */
public class Allocate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement ps=null;
	Connection con=null;
	Statement stmt=null;
	Common cmn = new Common();
    public Allocate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//doPost(request, response);
		Map<String , Integer> getData1 =getData(  request);
		response.setContentType("appliction/json;charset=UTF-8");
		//request.setAttribute("dat", getData1);
	//	JSONArray data = new JSONArray();
	//	data.put(getData1);
	String json= new Gson().toJson(getData1);
		response.getWriter().write(json);
        
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	   
	}
  public  Map<String , Integer> getData (HttpServletRequest request){
	  System.out.println("inside Get Data ");
		
		
		Map<String , Integer> group_Count = new HashMap<String , Integer>();
		
		
		String query = "select count(id_sloc) as Count_of_Subgp ,sbloc.nm_subl from A_Ware_House wh , M_Subloc sbloc " +
					" where wh.id_subl=sbloc.id_sloc and wh.id_loc=1 and id_sloc " +
					"	in (select id_sloc from M_Subloc where id_loc=1) group by sbloc.nm_subl ";
		
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
	  
  }
}
