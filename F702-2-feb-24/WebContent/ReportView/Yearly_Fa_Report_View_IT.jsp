<%@ page import="java.util.Calendar, java.text.SimpleDateFormat,com.Common.Common"%>
<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Generating Report</title>

<script type ="text/javascript" src="js/jquery1.10.js"></script>

</head>
<%
   String usertype = (String) session.getAttribute("UserName");
   if(usertype == null)	{
%>
		<script language="JavaScript">
			alert("Session Expired. Please Login");
			parent.location.href="../index.html";
		</script>
<%
	}else{
%>
	<body>
	<form name="showfrom" action="showReportCAExcel.jsp" method="post" target="_new">
<%
	try{
		 
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		
		String Group_id="",fin_id="";
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Yearly_Fa_Report_View_IT.xls");
		 
		        }
		   
		
		Connection con = null;
		Statement sm = null;
		ResultSet rs = null;

		Connection con1 = null;
		Statement sm1 = null;
		ResultSet rs1 = null;

		Connection con2 = null;
		Statement sm2 = null;
		ResultSet rs2 = null;

		Connection con3 = null;
		Statement sm3 = null;
		ResultSet rs3 = null;

		Connection con4 = null;
		Statement sm4 = null;
		ResultSet rs4 = null;

		Connection con5 = null;
		Statement sm5 = null;
		ResultSet rs5 = null;


		String Act_Name="",Group_Name="",Group_code="",Fin_stdate="",Fin_enddate="",subgroupnamenew="",groupnamenew="";
		String Prev_fin_enddate="",bondstatus="",assetid="",assetid2="",sold="",Fin_stdate1="",Fin_enddate1="";
		String bondno="-",bonddate="-",vend_name="-",locationname="-",locationid="-";
		String vou_date="",instl_date="",cc_name="-",employee_name="-",staDate="";
		String displayasset="",bond_date="";
		int slno=0;

		double asst_ope=0.0;
		double asst_add=0.0;
		double asst_del=0.0;
		double writeoff=0.0;
		double asst_tot=0.0;
		double asondate=0.0;
		double dep_open=0.0;
		double dep_add=0.0;
		double dep_del=0.0;
		double deptot=0.0;
		double wdv=0.0;
		double t_asst_add=0.0;
		double t_asst_del=0.0;
		double t_writeoff=0.0;
		double t_asondate=0.0;
		double t_dep_open=0.0;
		double t_dep_add=0.0;
		double t_dep_del=0.0;
		double t_dep_tot=0.0;
		double t_deptot=0.0;
		double t_wdv=0.0;
		double profit_loss=0.0;

		try{
			
			con=Common.GetConnection(request);
		  
		  sm=con.createStatement();
		  sm1=con.createStatement();
		  sm2=con.createStatement();
		  sm3=con.createStatement();
		  sm4=con.createStatement();
		  sm5=con.createStatement();

		}
		catch (Exception e){ }

		 Group_id=request.getParameter("group_name");
		 fin_id=request.getParameter("fin_year");
		String location="";//request.getParameter("location");
		String actNameType="CAWDV";
		String fin_years ="";
		
		 String grpCondn = "";
		  String grpCondn1 = "";
					    String locCondn = "";
					                			if (!Group_id.equals("All")) {
					                			 {
					                			grpCondn = " and group_id = " + Group_id
					                						+ "";
					                				grpCondn1 = " and hr.id_grp = " + Group_id
					                				+ "";
					                						}
		            			}
		
		
		
		if(!fin_id.equals("All")){
			fin_years = "where id_fincance="+fin_id+"";
		}
		
//out.print("Select std_finance,end_finance from M_Finance_Year "+ fin_years + " ORDER BY std_finance");
		rs5= sm5.executeQuery("Select std_finance,end_finance,(replace(convert(NVARCHAR, std_finance, 103), ' ', '-')) as std_finance1,(replace(convert(NVARCHAR, end_finance, 103), ' ', '-')) as end_finance1 from M_Finance_Year "+ fin_years + " ORDER BY std_finance");
		if(rs5.next()){
			Fin_stdate=rs5.getString(1).toString();
			Fin_enddate=rs5.getString(2).toString();
			Fin_stdate1 = rs5.getString(3).toString();
			Fin_enddate1 =rs5.getString(4).toString();
	  }

%>
<center>
<%
		rs5=null;
//out.print("Select std_finance,end_finance from M_Finance_Year "+ fin_years + " ORDER BY std_finance");
		rs5=sm5.executeQuery("Select std_finance,end_finance,(replace(convert(NVARCHAR, std_finance, 103), ' ', '-')) as std_finance1,(replace(convert(NVARCHAR, end_finance, 103), ' ', '-')) as end_finance1 from M_Finance_Year "+ fin_years + " ORDER BY std_finance");
		while(rs5.next()){
			Fin_stdate=rs5.getString(1).toString();
			Fin_enddate=rs5.getString(2).toString();
			Fin_stdate1 = rs5.getString(3).toString();
			Fin_enddate1 =rs5.getString(4).toString();

%>
<br><br><br><table border="1" class ="table1" width="100%" >
<tr>
	<td style="background-color: blue;" width="100%" class="title" colspan ="28"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">IT Act Asset Report for  <b><%=Fin_stdate1 %> to <%=Fin_enddate1 %></b> Financial Year </p></td>
</tr>
<tr>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>S No.</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>Asset ID</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>Installation Date</b></td>

	<!-- td  nowrap rowspan="3" class="title"><b>Installation No.</b></td -->
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>Vendor Name</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>Asset Name</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>Invoice Number</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>PO Number</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>Remarks</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>Qty</b></td>
	<!-- <td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>Model No.</b></td> -->
	
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>Group Name</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>SubGroup Name</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>Serial No.</b></td>
	
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>State</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>City</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>Store</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>Cost Center</b></td>
	<td  nowrap colspan="4" class="title" style="background-color: lavender;"><b>AssetValue</b></td>
	<td  nowrap colspan="5" class="title" style="background-color: lavender;"><b>Depreciation</b></td>
	<td  nowrap rowspan="3" class="title" style="background-color: lavender;"><b>WDV</b></td>
</tr>
<tr>
	<td  nowrap rowspan="2" class="title" style="background-color: lavender;"><b>Opening Balance</b></td>
	<td  nowrap rowspan="2" class="title" style="background-color: lavender;"><b>Addition</b></td>
	<td  nowrap rowspan="2" class="title" style="background-color: lavender;"><b>Deletion</b></td>
	<!-- td  nowrap rowspan="2" class="title"><b>Written Off /<br> Sold Out</b></td>
	<td  nowrap rowspan="2" class="title"><b>Profit/Loss<br> if Sold Out</b></td-->
	<td  nowrap rowspan="2" class="title" style="background-color: lavender;"><b>Grand Total</b></td>
	<td  nowrap rowspan="2" class="title" style="background-color: lavender;"><b>Accumulated <br> Depreciation </b></td>
	<td  nowrap colspan="4" class="title" style="background-color: lavender;"><b>For the period	<%=Fin_stdate1%> to <%=Fin_enddate1%></b></td>
</tr>
<tr>
	<td  nowrap class="title" style="background-color: lavender;"><b>Additions</b></td>
	<td  nowrap class="title" style="background-color: lavender;"><b>Opening</b></td>
	<td  nowrap class="title" style="background-color: lavender;"><b>Deductions</b></td>
	<td  nowrap class="title" style="background-color: lavender;"><b>Total</b></td>
</tr>
<%
		rs=null;
		//rs=sm.executeQuery("Select * from month_depreciation where startdate>='"+Fin_stdate+"' and group_id='"+Group_code+"' ");
		rs=sm.executeQuery("Select * from it_depreciation where startdate='"+Fin_stdate+"' "+ grpCondn +
		" ");
		if(rs.next()){

%>


<%
	try{
		double doubleAssetOpeningTot=0.0;
		double doubleAssetAddition=0.0;
		double doubleAssetDeletion=0.0;
		double doubleAssetWriteOff=0.0;
		double doubleAssetprofitLoss=0.0;
		double doubleAssetTotal=0.0;
		double doubleDepOpenTot=0.0;
		double doubleDepAddTot=0.0;
		double doubleDepDedTot=0.0;
		double doubleDeprecTot=0.0;
		double doubleDepAccTot=0.0f;
		double doubleDepwdv=0.0;
		double doubleWDV=0.0;

		/* out.print("Select  sd.ast_id,sd.assetid,hr.bd as bond,hr.id_grp as group_id,hr.id_sgrp as subgroup_id,no_inv as invoicenumber,no_po as ponumber,hr.remarks as asset_remarks,dt_allocate as instl_date,id_ven_proc as procured_vendor_id,ds_pro as assetname,qty_asst as assetqty,no_model as model_no,no_mfr as serialno,bd as assetbond,id_loc as loc_id,id_loc as subloc_id,id_flr as facility_id,to_assign as assignedto,id_cc as costcenter_id,allocate as installed,sd.asset_opening,sd.asset_addition,sd.asset_deletion,sd.asset_total,sd.asondate,sd.depreciated_opening,sd.depreciated_addition,sd.depreciated_deletion,sd.depreciated_total,sd.wdv,sd.writeoff,hr.id_sgrp as subgroup_id,substring(sd.assetid,0,charindex('-',sd.assetid)),cast(substring(sd.assetid,charindex('-',sd.assetid)+1,len(sd.assetid)) as int) from A_Ware_House hr,it_depreciation sd where hr.id_wh_dyn=sd.assetid "+ grpCondn1 +
				"and sd.type_id='"+actNameType+"' and sd.startdate='"+Fin_stdate+"'  and  dt_allocate !='1900-01-01 00:00:00.000' order by substring(sd.assetid,0,charindex('-',sd.assetid)),cast(substring(sd.assetid,charindex('-',sd.assetid)+1,len(sd.assetid)) as int)");
		 */
		rs1=sm1.executeQuery("Select  sd.ast_id,sd.assetid,hr.bd as bond,hr.id_grp as group_id,hr.id_sgrp as subgroup_id,no_inv as invoicenumber,no_po as ponumber,hr.remarks as asset_remarks,(replace(convert(NVARCHAR, dt_allocate, 103), ' ', '-')) as instl_date,id_ven_proc as procured_vendor_id,ds_pro as assetname,qty_asst as assetqty,no_model as model_no,no_mfr as serialno,bd as assetbond,id_loc as loc_id,id_loc as subloc_id,id_flr as facility_id,to_assign as assignedto,id_cc as costcenter_id,allocate as installed,sd.asset_opening,sd.asset_addition,sd.asset_deletion,sd.asset_total,sd.asondate,sd.depreciated_opening,sd.depreciated_addition,sd.depreciated_deletion,sd.depreciated_total,sd.wdv,sd.writeoff,hr.id_sgrp as subgroup_id,hr.id_building,hr.id_subl,hr.id_flr from A_Ware_House hr,it_depreciation sd where hr.id_wh_dyn=sd.assetid "+ grpCondn1 +
		"and sd.type_id='"+actNameType+"' and sd.startdate='"+Fin_stdate+"'  and  dt_allocate !='1900-01-01 00:00:00.000' ");
//out.print("Select  sd.ast_id,sd.assetid,hr.bd as bond,hr.id_grp as group_id,hr.id_sgrp as subgroup_id,no_inv as invoicenumber,no_po as ponumber,hr.remarks as asset_remarks,dt_allocate as instl_date,id_ven_proc as procured_vendor_id,assetname,ds_pro as assetqty,no_model as model_no,no_mfr as serialno,bd as assetbond,id_loc as loc_id,id_loc as subloc_id,id_flr as facility_id,to_assign as assignedto,id_cc as costcenter_id,allocate as installed,sd.asset_opening,sd.asset_addition,sd.asset_deletion,sd.asset_total,sd.asondate,sd.depreciated_opening,sd.depreciated_addition,sd.depreciated_deletion,sd.depreciated_total,sd.wdv,sd.writeoff,hr.id_sgrp as subgroup_id,substring(sd.assetid,0,charindex('-',sd.assetid)),cast(substring(sd.assetid,charindex('-',sd.assetid)+1,len(sd.assetid)) as int) from A_Ware_House hr,it_depreciation sd where hr.id_wh_dyn=sd.assetid "+ grpCondn1 +
		//"and sd.type_id='"+actNameType+"' and sd.startdate='"+Fin_stdate+"'  and  instl_date !='1900-01-01 00:00:00.000' order by substring(sd.assetid,0,charindex('-',sd.assetid)),cast(substring(sd.assetid,charindex('-',sd.assetid)+1,len(sd.assetid)) as int)");
		//rs1=sm1.executeQuery("select from it_depreciation where group_id='"+Group_id+"' and startdate ='"+finyearst+"'");

	while(rs1.next())
	{
		double t_asst_tot=0.0;
		double dep_tot_loc=0.0;
		double dep_tot=0.0;
		double accDepTot=0.0;
		t_asondate=0.0;
		double t_asst_ope=0.0;
		boolean soldStatus=false;
		String ast_id=rs1.getString("ast_id");
		assetid=rs1.getString("assetid");
		
		
		int InstalledStatus=Integer.parseInt(rs1.getString("installed"));
		rs2=sm2.executeQuery("select * from addition_deletion where  asset_status  in('sold','writeoff') and stadate between '"+Fin_stdate+"' and '"+Fin_enddate+"' and assetid='"+ast_id+"'");
		if(rs2.next())
		{
			soldStatus=true;
		}
%>
<tr <%if(soldStatus)out.println("bgcolor=yellow");%>>
	<td nowrap class="righttd"><%=++slno%></td>
	<td nowrap class="righttd"><%=assetid%></td>
	<td nowrap class="righttd"><%=(rs1.getString("instl_date"))%></td>
<%
	rs2=null;
	rs2=sm2.executeQuery("Select nm_ven as vendor_name from M_Vendor where id_ven='"+rs1.getString("procured_vendor_id")+"' ");
	if(rs2.next()){
		vend_name=rs2.getString("vendor_name");
	}
	
	rs2=null;
		rs2=sm2.executeQuery("Select nm_assetdiv as group_name from M_Asset_Div where id_assetdiv='"+rs1.getString("group_id")+"' ");
		if(rs2.next()){
			groupnamenew=rs2.getString("group_name");
	}
	
	
	rs2=null;
		rs2=sm2.executeQuery("Select nm_s_assetdiv as subgroup_name from M_Subasset_Div where id_s_assetdiv='"+rs1.getString("subgroup_id")+"' ");
		if(rs2.next()){
			subgroupnamenew=rs2.getString("subgroup_name");
	}
	
	bondstatus=rs1.getString("bond");

%>	<td nowrap class="righttd"><%=vend_name%></td>
	<td nowrap class="righttd"><%=rs1.getString("assetname")%></td>
	
	<td nowrap class="righttd"><%=rs1.getString("invoicenumber")%></td>
	<td nowrap class="righttd"><%=rs1.getString("ponumber")%></td>
	<td nowrap class="righttd"><%=rs1.getString("asset_remarks")%></td>

	<td nowrap class="righttd"><%=rs1.getString("assetqty")%></td>
	<td nowrap class="righttd"><%=rs1.getString("model_no")%></td>
	<td nowrap class="righttd"><%=groupnamenew%></td>
	<td nowrap class="righttd"><%=subgroupnamenew%></td>
	<td nowrap class="righttd"><%=rs1.getString("serialno")%></td>
	

<%
	String subloc="-",cubicle="-";
	rs2=sm2.executeQuery("Select nm_building  from M_Building where id_building='"+rs1.getString("id_building")+"' ");
	if(rs2.next()){
		subloc=rs2.getString("nm_building");
	}
	
	rs2=sm2.executeQuery("Select nm_subl as subloc_name from M_Subloc where id_sloc='"+rs1.getInt("id_subl")+"'");
	if(rs2.next())
		locationname=rs2.getString(1);
		//subloc=rs2.getString(1);
	
	rs2=sm2.executeQuery("Select nm_flr as facility_name from M_Floor where id_flr='"+rs1.getInt("id_flr")+"'");
	if(rs2.next()){
		cubicle=rs2.getString(1);
	}
	

	rs2=null;
	rs2=sm2.executeQuery("Select cd_cc as costcenter_code from M_Company_Costcenter where id_cc='"+rs1.getString("costcenter_id")+"' ");
	if(rs2.next())
	{
		cc_name=rs2.getString(1);
	}
%>	<td nowrap class="righttd"><%=locationname%></td>
	<td nowrap class="righttd"><%=subloc%></td>
	<td nowrap class="righttd"><%=cubicle%></td>
	<td nowrap class="righttd"><%=cc_name%></td>
	<td nowrap class="lefttd"><%=rs1.getString("asset_opening")%></td>
	<td nowrap class="lefttd"><%=rs1.getString("asset_addition")%></td>
	<td nowrap class="lefttd"><%=rs1.getString("asset_deletion")%></td>
	<td nowrap class="lefttd"><%=rs1.getString("asset_total")%></td>
	<td nowrap class="lefttd"><%=rs1.getString("asondate")%></td>
	<td nowrap class="lefttd"><%=rs1.getString("depreciated_opening")%></td>
	<td nowrap class="lefttd"><%=rs1.getString("depreciated_addition")%></td>
	<td nowrap class="lefttd"><%=rs1.getString("depreciated_deletion")%></td>
	<td nowrap class="lefttd"><%=rs1.getString("depreciated_total")%></td>
	<td nowrap class="lefttd"><%=rs1.getString("wdv")%></td>


<%	}
	rs1=sm1.executeQuery("Select sum(sd.asset_opening) as asset_opening,sum(sd.asset_addition) as asset_addition,sum(sd.asset_deletion) as asset_deletion,sum(sd.asset_total) as asset_total,sum(sd.asondate) as asondate,sum(sd.depreciated_opening) as depreciated_opening,sum(sd.depreciated_addition) as depreciated_addition,sum(sd.depreciated_deletion) as depreciated_deletion,sum(sd.depreciated_total) as depreciated_total,sum(sd.wdv) as wdv,sum(sd.writeoff) as writeoff from A_Ware_House hr,it_depreciation sd where hr.id_wh_dyn=sd.assetid "+ grpCondn1 +
	"and sd.type_id='"+actNameType+"' and sd.startdate='"+Fin_stdate+"'  and  dt_allocate !='1900-01-01 00:00:00.000'");
	if(rs1.next()){
%>
<tr>
	<td nowrap class="lefttd" colspan="17"><b>Total:</b></td>
	<td nowrap class="lefttd"><b><%=rs1.getString("asset_opening")%></b></td>
	<td nowrap class="lefttd"><b><%=rs1.getString("asset_addition")%></b></td>
	<td nowrap class="lefttd"><b><%=rs1.getString("asset_deletion")%></b></td>
	<td nowrap class="lefttd"><b><%=rs1.getString("asset_total")%></b></td>
	<td nowrap class="lefttd"><b><%=rs1.getString("asondate")%></b></td>
	<td nowrap class="lefttd"><b><%=rs1.getString("depreciated_opening")%></b></td>
	<td nowrap class="lefttd"><b><%=rs1.getString("depreciated_addition")%></b></td>
	<td nowrap class="lefttd"><b><%=rs1.getString("depreciated_deletion")%></b></td>
	<td nowrap class="lefttd"><b><%=rs1.getString("depreciated_total")%></b></td>
	<td nowrap class="lefttd"><b><%=rs1.getString("wdv")%></b></td>
</tr>

<%
	}
	}
	catch(Exception e)
	{
		out.println("test"+e);
	}
%>

<%
	}
	else
	{
%>
		<tr>
		<td nowrap colspan=28 class="centertd"><b><font color="red" class=navigate>Depreciation Yet To Be Calculated For <%=Fin_stdate1%> To <%=Fin_enddate1%> </font></b></td>
		</tr>
<%
	}
%>
	</table>
<%
}
	%>
	
	</center>

</form>
</body>

<br>
		<a href="Yearly_Fa_Report_View_CA.jsp?exportToExcel=YES&group_names=<%=Group_id%>&fin_years=<%=fin_id%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a>		
	<br>
	<b>Note</b> <font color ="teal">The Assets marked with Yellow Color indicates <b> Sold Out </b> or <b>Written Off</b>.</font>
	


<%
try{con.close();}catch(Exception e){out.println(e);}
	try{con1.close();}catch(Exception e){}
	try{con2.close();}catch(Exception e){}
}
catch(Exception e)
{

	out.println(e);
}


	}

%>
<%!
	double rounding(double total)
	{
		double x=total;
		int i=(int)x;
		if((int)(x+0.11)!=i)
		{
			x=i+1;


		}
		return x;
	}

%>
</html>