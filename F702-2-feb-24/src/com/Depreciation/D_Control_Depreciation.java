package com.Depreciation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.Common.Common;
import com.LibraryForDprn.TransferHardwareRegisterSLM;
import com.LibraryForDprn.TransferHardwareRegisterSLMLOA;
import com.LibraryForDprn.TransferHardwareRegisterWDV;
import com.LibraryForDprn.USTransferHardwareRegisterSLM;
import com.LibraryForDprn.Year_TransferHardwareRegisterWDV;


public class D_Control_Depreciation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResultSet rs = null;
	ResultSet rs1 = null;
	PreparedStatement ps = null;
	PreparedStatement ps1 = null;
	Connection con = null;
	Statement stmt = null;
	Statement stmt1 = null;
	JSONObject jobj = new JSONObject();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String year	="",Month="",dep_typ="",dep_period="",finid="";
		
		try{
			if (request.getParameter("yearForDprn") != null) {
				year = request.getParameter("yearForDprn");
			}
			if (request.getParameter("monthForDprn") != null) {
				Month = request.getParameter("monthForDprn");
			}
			if (request.getParameter("type_id") != null) {
				dep_typ = request.getParameter("type_id");
			}
			if (request.getParameter("dep_period") != null) {
				dep_period = request.getParameter("dep_period");
			}
			if (request.getParameter("finYear") != null) {
				finid = request.getParameter("finYear");
			}
		String groupStr					=	"-";
		
		String [] GroupArr				=	request.getParameterValues("id_grp");
		
		String startdate				=	""+year+"-"+Month+"-01";
		String enddate					=	"";
		
		con = Common.GetConnection(request);
		stmt = con.createStatement();
		stmt1 = con.createStatement();
		
try
{
		if(dep_period.equals("yearly")){
			rs							=	stmt.executeQuery("select std_finance,end_finance from M_Finance_Year where id_fincance="+finid+"");
			if(rs.next()){
				startdate				=	rs.getString(1);
				enddate					=	rs.getString(2);
			}
			
		}
		else
		{
			rs								=	stmt.executeQuery("select dateadd(month,1,convert(datetime,'"+startdate+"',101))-1");
			if(rs.next()){
				enddate						=	rs.getString(1);
				
			}
		}
}
catch(Exception e)
{
	System.out.println("hi" + e);
}
		ArrayList Groups				=	new ArrayList();
		for(int i=0;i<GroupArr.length;i++){
			Groups.add(GroupArr[i]);
			if(groupStr.equals("-")){
				groupStr="'"+GroupArr[i]+"'";
			}else{
				groupStr+=",'"+GroupArr[i]+"'";
			}
		}
		if(groupStr.equals("-")){
			groupStr="'0'";
		}


		if(isfreezed(startdate,dep_period,con,stmt,rs)){
			//System.out.println("<script language='javascript'>hideMessage2()</script>");
			jobj.put("data", "Selected period or a later period is frozen.");
		}else if(isdepAllow(dep_typ,GroupArr,startdate,dep_period,dep_period,con,stmt,rs,stmt1,rs1)){
			//System.out.println("<script language='javascript'>hideMessage3()</script>");
			jobj.put("data", "Depreciation For the previous Period is not processed.");
		}else{
			//Control Depreciation
			if(dep_period.equals("yearly")){
				stmt.executeUpdate("delete from stline_depreciation where enddate >= '" + enddate + "' and group_id in("+groupStr+")");
				
			}
			else
			{
				stmt.executeUpdate("delete from month_depreciation where enddate >= '" + enddate + "' and group_id in("+groupStr+")");
			}
			//stmt.executeUpdate("delete from CONTROL_DEPRECIATION where enddate >= '"+enddate+"' and group_id in("+groupStr+") AND type_id='"+dep_typ+"'");
			for(int i=0;i<GroupArr.length;i++){
				stmt.executeUpdate("insert into CONTROL_DEPRECIATION(group_id,startdate,enddate,type_id,yer_month) values('"+GroupArr[i]+"','"+startdate+"','"+enddate+"','"+dep_typ+"','"+dep_period+"')");
			}
			//Ends here

			if(dep_typ.equals("CA")){
				String iswdv="";
				String dep_based_on="";
				rs=stmt.executeQuery("select * from dep_param where type_id='CA'");
				if(rs.next()){
					iswdv			=rs.getString("dep_method");
					dep_based_on	=rs.getString("dep_based_on");
				}
				if(iswdv.equals("SLM")){
					if(dep_based_on.equals("Rate")){
						TransferHardwareRegisterSLM obj	=new TransferHardwareRegisterSLM();
						obj.transfer(startdate,enddate,dep_period,Groups,  request);
					}else if(dep_based_on.equals("LifeSpan")){
						TransferHardwareRegisterSLMLOA obj	=new TransferHardwareRegisterSLMLOA();
						obj.transfer(startdate,enddate,dep_period,Groups,  request);
					}
				}else{
					if(dep_period.equals("yearly")){
						Year_TransferHardwareRegisterWDV obj	=new Year_TransferHardwareRegisterWDV();
						obj.transfer(startdate,enddate,dep_period,Groups,"CA",  request);
					}
					else
					{
						TransferHardwareRegisterWDV obj	=new TransferHardwareRegisterWDV();
						obj.transfer(startdate,enddate,dep_period,Groups,  request);
					}
				}
			}else if(dep_typ.equals("USG")){
					USTransferHardwareRegisterSLM obj	=new USTransferHardwareRegisterSLM();
					obj.transfer(startdate,enddate,dep_period,Groups,  request);
			}
			jobj.put("data", "Depreciation Processing Completed...");
			
		}
	}
	catch(Exception e){
		System.out.println("Errot  "+e);
	}
		
		request.setAttribute("data", jobj.toString());
		response.getWriter().write(jobj.toString());
}
	
public boolean isfreezed(String startdate,String year_month,Connection con2,Statement stmt2,ResultSet rs2) throws Exception{
		boolean status=false;
		if(year_month.equals("yearly")){
			rs2=stmt2.executeQuery("select * from yearly_freeze where startdate >='"+startdate+"'");
			if(rs2.next()){
				status=true;
			}
		}else if(year_month.equals("monthly")){
			rs2=stmt2.executeQuery("select * from monthly_freeze where startdate >='"+startdate+"'");
			if(rs2.next()){
				status=true;
			}
		}

	return status;
}
public boolean isdepAllow(String dep_typ,String [] GroupArr,String startdate,String year_month,String dep_period,Connection con1,Statement stmt,ResultSet rs,Statement stmt1,ResultSet rs1) throws Exception{
		boolean status=false;
		boolean isdepallow=true;
		if(dep_period.equals("yearly")){
			for(int i=0;i<GroupArr.length;i++){
				rs=stmt.executeQuery("select * from CONTROL_DEPRECIATION where startdate=dateadd(year,-1,convert(datetime,'"+startdate+"',101)) and group_id='"+GroupArr[i]+"' AND type_id='"+dep_typ+"' and yer_month='"+dep_period+"'");
				if(!rs.next()){
					rs1=stmt1.executeQuery("select * from CONTROL_DEPRECIATION where startdate < convert(datetime,'"+startdate+"',101) and group_id='"+GroupArr[i]+"' AND type_id='"+dep_typ+"' and yer_month='"+dep_period+"'");
					if(rs1.next()){
						status=true;
						break;
					}else{
						status=false;
					}
				}
			}
		}else if(dep_period.equals("monthly")){
			for(int i=0;i<GroupArr.length;i++){
				rs=stmt.executeQuery("select * from CONTROL_DEPRECIATION where startdate=dateadd(month,-1,convert(datetime,'"+startdate+"',101)) and group_id='"+GroupArr[i]+"' AND type_id='"+dep_typ+"' and yer_month='"+dep_period+"'");
				if(!rs.next()){
					rs1=stmt1.executeQuery("select * from CONTROL_DEPRECIATION where startdate < convert(datetime,'"+startdate+"',101) and group_id='"+GroupArr[i]+"' AND type_id='"+dep_typ+"' and yer_month='"+dep_period+"'");
					if(rs1.next()){
						status=true;
						break;
					}else{
						status=false;
					}
				}
			}
		}
	return status;
}
}
