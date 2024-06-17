package com.Depreciation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.Common.Common;
import com.LibraryForDprn.Year_TransferHardwareRegisterWDV;
import com.LibraryForDprn.itDepreciationCalculation;

public class D_Control_Depreciation_It extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JSONObject json = new JSONObject();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
try{
		String depAct="",depStDt="",stryear = "";
		
		if (request.getParameter("type_id") != null) {
			depAct = request.getParameter("type_id");
		}
		if (request.getParameter("finYear") != null) {
			depStDt = request.getParameter("finYear");
		}
		String [] GroupArr				=	request.getParameterValues("id_grp");
		
		stryear = depStDt;
	String dep_period="yearly";
	String startdate="",enddate="";

		

		ArrayList Groups				=	new ArrayList();
		for(int i=0;i<GroupArr.length;i++){
			Groups.add(GroupArr[i]);
		}
	String groupStr					=	"-";
	for(int i=0;i<Groups.size();i++){
		if(groupStr.equals("-")){
			groupStr="'"+Groups.get(i)+"'";
		}else{
			groupStr+=",'"+Groups.get(i)+"'";
		}
	}
	if(groupStr.equals("-")){
		groupStr="'0'";
	}

		
			Connection con				=	null;
			Statement st				=	null;
			Statement stmt				=	null;
			Statement st1				=	null;
			Statement st2				=	null;
			ResultSet rs				=	null;
			ResultSet rs1=null;
			ResultSet rs2=null;

				con						= 	Common.GetConnection(request);
				st						=	con.createStatement();
				stmt					=	con.createStatement();
				st1						=	con.createStatement();
				st2						=	con.createStatement();
			
			
		String finType="";
		String prevYear = "";
		String curYear = "";
		int depFlag = 0;
		String displayYear="";

	if(depAct.equals("UK")){
	finType="1";
	}else{
	finType="0";
	}

	

	 String query="";

	 query="select std_finance,end_finance,dateadd(YY,-1,std_finance) from M_Finance_Year where id_fincance='"+ stryear+"'";
	 rs=st.executeQuery(query);
	 if(rs.next())
	 {
	   //displayYear = Format.displayDate(rs.getString(1)) +" To "+ Format.displayDate(rs.getString(2));
	   prevYear = rs.getString(3);
	   curYear = rs.getString(1);
	 }

	 
	 System.out.println("depAct: " + depAct);
	if(depAct.equals("IT")){
		rs=st.executeQuery("select * from it_depreciation where group_id in("+groupStr+")");
		if(rs.next()){
			rs1=st1.executeQuery("select * from it_depreciation where group_id in("+groupStr+") and startdate < convert(datetime,'" + prevYear + "',101)");
			if(rs1.next()){
				rs2=st2.executeQuery("select * from it_depreciation where group_id in("+groupStr+") and startdate = convert(datetime,'" + prevYear + "',101)");
				if(!rs2.next()){
					depFlag = 1;
				}
			}
		}

	}

	rs=st.executeQuery("select Startdate,Enddate from t_freeze WHERE Startdate >= convert(datetime,'" + curYear + "',101) and status='" + finType + "'");
	if(rs.next()){
		depFlag = 2;
	}


	rs=st.executeQuery("select Startdate,Enddate from t_freeze WHERE fin_year_id='" + stryear + "' and status='" + finType + "'");
	if(rs.next()){
		json.put("data", "Sorry, You cannot process depreciation, as this financial year is freezed.");
	}else if(depFlag==1){
		json.put("data", "Sorry, You cannot process depreciation, as you have not processed for previous year.");
	}else if(depFlag==2){
		json.put("data", "Sorry, You cannot process depreciation, as next financial year is freezed.");
	}else{

	if(dep_period.equals("yearly")){
		rs							=	stmt.executeQuery("select convert(datetime,std_finance,101),convert(datetime,end_finance,101) from M_Finance_Year where id_fincance="+stryear+"");
		if(rs.next()){
			startdate				=	rs.getString(1);
			enddate					=	rs.getString(2);
		}
	}
	stmt.executeUpdate("delete from it_depreciation where enddate >= '" + enddate + "' and group_id in("+groupStr+")");
	//stmt.executeUpdate("delete from it_depreciation where enddate >= '" + enddate + "' and group_id in("+groupStr+")");
	itDepreciationCalculation 	  	obj4  		= new itDepreciationCalculation();
	Year_TransferHardwareRegisterWDV obj	=new Year_TransferHardwareRegisterWDV();
	obj4.calculateITDep(startdate,enddate,Groups,  request);
	obj.transfer(startdate,enddate,dep_period,Groups,"IT",  request);
	json.put("data", "Depreciation Processing Completed...");
			//System.out.println("<script language='javascript'>hideMessage()</script>");

}
	}catch(Exception e)
	{
		System.out.println("Error in this page.."+e);
	}
	
request.setAttribute("data", json.toString());
response.getWriter().write(json.toString());
		
	}

}
