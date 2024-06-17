 	
	<%@ page import="java.util.Calendar, java.text.SimpleDateFormat"%>
	<%@ page language="java" import="java.sql.*,javax.naming.*,java.text.DecimalFormat,javax.sql.*,java.io.*,java.util.*,com.Common.Common" %>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Generating Report</title>
	
	<script type ="text/javascript" src="js/jquery1.10.js"></script>
	
	</head>
	<%
		String usertype=(String) session.getValue("UserName");
		if(usertype==null)
		{
	%>
			<script language="JavaScript">
				alert("Session Expired. Please Login");
				parent.location.href="../index.html";
			</script>
	<%
		}
		else
		{
	%>
	
	<body>
	
	<form name="showfrom" action="monthly_fa_excel_reports.jsp" method="Post"target="_new">
	<%
	String Month="",Group_id="",Year="";
	SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
	
	        String exportToExcel = request.getParameter("exportToExcel");
	        
	        if (exportToExcel != null
	                && exportToExcel.toString().equalsIgnoreCase("YES")) {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "inline; filename="
	                    + "Monthly_Fa_Report_view.xls");
	 
	        }
	   
	
	    DecimalFormat df = new DecimalFormat("0.00");
		ResultSet rs=null;
		Connection con=null;
		Statement sm=null;
	
		Connection con1 = null;
		Statement sm1 = null;
		Statement smw = null;
		ResultSet rs1 = null;
	
		Connection con2 = null;
		Statement sm2 = null;
		ResultSet rs2 = null;
		ResultSet rsw = null;
	
		Statement sm3 = null;
		ResultSet rs3 = null;
		try
		{
			con=Common.GetConnection(request);
				 
			sm=con.createStatement();
			smw=con.createStatement();
			sm1=con.createStatement();
			sm2=con.createStatement();
			sm3=con.createStatement();
		}
		catch (Exception e)	{
		  System.out.println(e.getMessage());
		}
	
			String Act_Name="",Group_Name="",Group_code="",Fin_stdate="",Fin_enddate="",Fin_stdate1="",Fin_enddate1="",Fin_lastdate="",startyear="",bondstatus="",assetid="",sold="",bondno="-",bonddate="-",vend_name="-",locationname="-",locationid="-",vou_date="",instl_date="",cc_name="-",employee_name="-",staDate="",displayasset="",asset_bond_no="",costcent="",costcenterId="";
			int slno=0,currentMonthEndDate,LastYear;
	
			double asst_ope=0.0;
			double asst_add=0.0;
			double asst_del=0.0;
			double writeoff=0.0;
			double asst_tot=0.0;
			double asondate=0.0;
			double dep_open=0.0;
			double dep_add=0.0;
			double dep_del=0.0;
			double dep_tot=0.0;
			double wdv=0.0;
	
			double t_asst_ope=0.0;
			double t_asst_add=0.0;
			double t_asst_del=0.0;
			double t_writeoff=0.0;
			double t_asst_tot=0.0;
			double t_asondate=0.0;
			double t_dep_open=0.0;
			double t_dep_add=0.0;
			double t_dep_del=0.0;
			double t_dep_tot=0.0;
			double t_wdv=0.0;
			double profit_loss=0.0;
			double t_profit_loss=0.0;
	
	
	
			 Month=request.getParameter("monthForFa");
			int mon=Integer.parseInt(Month);
	
			 Year=request.getParameter("yearForFa");
			 Group_id=request.getParameter("groupForFa");
		
		if (mon >3 & mon <=12)
	{
		LastYear=Integer.parseInt(Year);
			
			
	}
	else{
	LastYear=Integer.parseInt(Year)-1;
			
		
	}
	
			
			//String strLocation=request.getParameter("location");
	
	
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(Year),Integer.parseInt(Month)-1,1);
			currentMonthEndDate = cal.getActualMaximum(cal.DATE);
	
			Vector monthname= new Vector();
			Vector monthvalue= new Vector();
	
			monthname.addElement("");
			monthname.addElement("January");
			monthname.addElement("February");
			monthname.addElement("March");
			monthname.addElement("April");
			monthname.addElement("May");
			monthname.addElement("June");
			monthname.addElement("July");
			monthname.addElement("August");
			monthname.addElement("September");
			monthname.addElement("October");
			monthname.addElement("November");
			monthname.addElement("December");
			Fin_lastdate=LastYear+"-04-01";
			startyear=LastYear+"-04-30";
			Fin_stdate=Year+"-"+Month+"-01";
			Fin_enddate=Year+"-"+Month+"-"+currentMonthEndDate+"";
			Fin_stdate1="01/"+Month+"/"+Year;
			Fin_enddate1=currentMonthEndDate+"/"+Month+"/"+Year;
			String strMonthName=monthname.elementAt(mon)+"";
	
	
	
			 String grpCondn = "";
				    String locCondn = "";
				                			if (!Group_id.equals("All")) {
				                			 {
				                			grpCondn = " and hr.id_grp = " + Group_id
				                						+ "";
				                						}
	            			}
			
			
			
			
		try{
	
			rs=null;
			String text_="",costcenter_name="";
			//String costcenter=request.getParameter("costcenter");
			//String c_pass=costcenter;
	
			/*if(costcenter.equals("all"))
				costcent="";
			else{
				rs=sm.executeQuery("select div_name from division_master where div_id='"+costcenter+"'");
				if(rs.next())
					text_="For Division "+rs.getString(1);
					costcent=" and hr.div_id='"+costcenter+"'";
					//costcenter=" and costcenter_id='"+costcenter+"'";
	
			}*/
	
			//out.println("Select distinct hr.assetid from hardware_register hr,month_depreciation sd,division_master cc where hr.assetid=sd.assetid and hr.group_id='"+Group_code+"' and  sd.enddate <= convert(datetime,'"+Fin_enddate+"',120)  and sd.type_id='CASLM'");
			rs=sm.executeQuery("Select distinct hr.id_wh_dyn from A_Ware_House hr,month_depreciation sd where hr.id_wh_dyn=sd.assetid  "+ grpCondn +
			" and  sd.enddate <= convert(datetime,'"+Fin_enddate+"',120)  and sd.type_id='CASLM'");//+costcent
			
			//out.print("Select distinct hr.id_wh_dyn from A_Ware_House hr,month_depreciation sd where hr.id_wh_dyn=sd.assetid  "+ grpCondn +
					//"and  sd.enddate <= convert(datetime,'"+Fin_enddate+"',120)  and sd.type_id='CASLM'");
			if(rs.next())
			{
	
		/*rsw=smw.executeQuery("Select distinct hr.assetid from hardware_register hr,month_depreciation sd,division_master cc where hr.assetid=sd.assetid and hr.group_id='"+Group_code+"' and  sd.enddate <= convert(datetime,'"+Fin_enddate+"',120)  and sd.type_id='CASLM'");//+costcent
		while(rsw.next())
		{*/
			//costcenterId=rsw.getString(1);
			//costcenter_name=rsw.getString(2);
			slno=0;
	%>
	
	<table  width="100%" height="100%" border="1">
	<tr >
	
	<td colspan="24" style="background-color: blue;"><p style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">Asset Report for the  Month <b><%=strMonthName%>/ <%=Year%> <%=text_%></b></p></td>
	<%
	
	%>
	</tr>
	<tr>
	<td  nowrap rowspan="3" class="title"><b>S No.</b></td>
	<td  nowrap rowspan="3" class="title"><b>Asset ID</b></td>
	
	<td  nowrap rowspan="3" class="title"><b>Installation Date</b></td>
	<td  nowrap rowspan="3" class="title"><b>Vendor Name</b></td>
	<td  nowrap rowspan="3" class="title"><b>Asset Name</b></td>
	<td  nowrap rowspan="3" class="title"><b>Invoice_Number</b></td>
	<td  nowrap rowspan="3" class="title"><b>Po_Number</b></td>
	<td  nowrap rowspan="3" class="title"><b>Remarks</b></td>
	<td  nowrap rowspan="3" class="title"><b>Qty</b></td>
	<!-- <td  nowrap rowspan="3" class="title"><b>Model No.</b></td> -->
	<td  nowrap rowspan="3" class="title"><b>Serial No.</b></td>
	<td  nowrap rowspan="3" class="title"><b>Day Diff</b></td>
	<!--td  nowrap colspan="2" class="title"><b>Bond </b></td-->
	<td  nowrap rowspan="3" class="title"><b>Region</b></td>
	<td  nowrap rowspan="3" class="title"><b>Cost Center</b></td>
	
	
	<td  nowrap colspan="4" class="title"><b>AssetValue</b></td>
	<td  nowrap colspan="5" class="title"><b>Depreciation</b></td>
	<td  nowrap rowspan="3" class="title"><b>WDV</b></td>
	</tr>
	<tr>
	<!--td  nowrap rowspan="2" class="title"><b>No.</b></td>
	<td  nowrap rowspan="2" class="title"><b>Date</b></td-->
	<td  nowrap rowspan="2" class="title"><b>Opening Balance</b></td>
	<td  nowrap rowspan="2" class="title"><b>Addition</b></td>
	<td  nowrap rowspan="2" class="title"><b>Deletion</b></td>
	<!--td  nowrap rowspan="2" class="title"><b>Written Off /<br> Sold Out</b></td>
	<td  nowrap rowspan="2" class="title"><b>Profit/Loss<br> if Sold Out</b></td-->
	<td  nowrap rowspan="2" class="title"><b>Total</b></td>
	<td  nowrap rowspan="2" class="title"><b>Opening Accumulated <br> Depreciation </b></td>
	<td  nowrap colspan="4" class="title"><b>For the period <%=Fin_stdate1%> to <%=Fin_enddate1%></b></td>
	</tr>
	<tr>
	<td  nowrap class="title"><b>Depreciation For YTD</b></td>
	<td  nowrap class="title"><b>Opening</b></td>
	<td  nowrap class="title"><b>Deductions</b></td>
	<td  nowrap rowspan="1" class="title"><b>Closing Accumulated  Depreciation</b></td>
	</tr>
	<%
		String str2 = "Select distinct sd.ast_id,sd.Assetid,hr.bd as bond,no_po as ponumber,no_inv as invoicenumber,hr.remarks,(replace(convert(NVARCHAR, hr.dt_allocate, 103), ' ', '-')) as dt_allocate,datediff(day,convert(datetime,hr.dt_allocate,101),CONVERT(datetime,'"+Fin_enddate+"',101)+1) as day_diff,id_ven_proc as procured_vendor_id,id_cc as costcenter_id,ds_pro as assetname,qty_asst as assetqty,no_model as model_no,no_mfr as serialno,bd as assetbond,hr.id_loc as loc_id,to_assign as assignedto from A_Ware_House hr,month_depreciation sd where hr.id_wh_dyn=sd.assetid "+ grpCondn +
				"and  dt_allocate <= convert(datetime,'"+Fin_enddate+"',120)  and sd.type_id='CASLM'";
		//out.print(str2);
			rs1=sm1.executeQuery(str2);//
		while(rs1.next())
		{
			String ast_id=rs1.getString("ast_id");
			assetid=rs1.getString("assetid");
			asset_bond_no=rs1.getString("bond");
			
			
			boolean soldStatus=false;
			rs2=sm2.executeQuery("select * from addition_deletion where  asset_status ='sold' and stadate between '"+Fin_stdate+"' and '"+Fin_enddate+"' and assetid='"+ast_id+"'");
			//out.print("select * from addition_deletion where  asset_status ='sold' and stadate between '"+Fin_stdate+"' and '"+Fin_enddate+"' and assetid='"+ast_id+"'");
			if(rs2.next())
			{
				soldStatus=true;
			}
			rs2=sm2.executeQuery("select * from month_depreciation where assetid='"+assetid+"'and startdate='"+Fin_stdate+"'   and type_id='CASLM' ");
			if(rs2.next())
			{
	
	%>
	<tr <%if(soldStatus)out.println("bgcolor=yellow");%>>
	<td nowrap class="righttd"><%=(++slno)%></td>
	<td nowrap class="righttd"><%=assetid%></td>
	
	<td nowrap class="righttd"><%=rs1.getString("dt_allocate")%></td>
	<%
		rs3=sm3.executeQuery("Select nm_ven as vendor_name from M_Vendor where id_ven='"+rs1.getString("procured_vendor_id")+"' ");
		if(rs3.next())
		{
			vend_name=rs3.getString("vendor_name");
		}
	%>
	<td nowrap class="righttd"><%=vend_name%></td>
	<td nowrap class="righttd"><%=rs1.getString("assetname")%></td>
	
	<td nowrap class="righttd"><%=rs1.getString("invoicenumber")%></td>
	<td nowrap class="righttd"><%=rs1.getString("ponumber")%></td>
	<td nowrap class="righttd"><%=rs1.getString("remarks")%></td>
	
	<td nowrap class="righttd"><%=rs1.getString("assetqty")%></td>
	<td nowrap class="righttd"><%=rs1.getString("model_no")%></td>
	<td nowrap class="righttd"><%=rs1.getString("serialno")%></td>
	<td nowrap class="righttd"><%=rs1.getString("day_diff")%></td>
	
	<!--td nowrap class="righttd"><%="bondno"%></td>
	<td nowrap class="righttd"><%="bonddate"%></td-->
	<%
		locationid=rs1.getString("loc_id");
		rs2=null;
		rs2=sm2.executeQuery("Select nm_loc as loc_name from M_Loc where id_loc='"+locationid+"' ");
		if(rs2.next())
		{
			locationname=rs2.getString("loc_name");
		}
		rs2=null;
			rs2=sm2.executeQuery("Select cd_cc as costcenter_code from M_Company_Costcenter where id_cc='"+rs1.getString("costcenter_id")+"'");
			if(rs2.next())
			{
				cc_name=rs2.getString(1);
		}
	%>
	<td nowrap class="righttd"><%=locationname%></td>
	<td nowrap class="righttd"><%=cc_name%></td>
	
	<%
		/*rs2=null;
		rs2=sm2.executeQuery("Select div_name from division_master where div_id='"+rs1.getString("div_id"))+"' ");
		if(rs2.next())
		{
			cc_name=rs2.getString(1);
		}*/
	%>
	<!-- td nowrap class="righttd"><=cc_name%></td -->
	<%
		rs2=null;
		rs2=sm2.executeQuery("select round(asset_opening,2) as asset_opening,round(asset_addition,2) as asset_addition,round(asset_deletion,2) as asset_deletion,round(writeoff,2) as writeoff,round(asset_total,2) as asset_total,round(depreciated_total,2) as depreciated_total,round(wdv,2) as wdv from month_depreciation where assetid='"+assetid+"'and startdate='"+Fin_stdate+"'  and type_id='CASLM'");
		if(rs2.next())
		{
			asst_ope=rs2.getDouble("asset_opening");
	
			asst_add=rs2.getDouble("asset_addition");
			asst_del=rs2.getDouble("asset_deletion");
			writeoff=rs2.getDouble("writeoff");
			asst_tot=rs2.getDouble("asset_total");
			dep_tot=rs2.getDouble("depreciated_total");
			wdv=rs2.getDouble("wdv");
	
	
	}
	
	rs2=sm2.executeQuery("select round(asondate,2) as asondate from month_depreciation where assetid='"+assetid+"'and startdate='"+Fin_lastdate+"'  and type_id='CASLM'");
		if(rs2.next())
		{
	
			asondate=rs2.getDouble("asondate");
	
		}
	
	rs2=sm2.executeQuery("select sum(round(depreciated_opening,2)) as depreciated_opening,sum(round(depreciated_addition,2)) as depreciated_addition,sum(round(depreciated_deletion,2)) as depreciated_deletion from month_depreciation where assetid='"+assetid+"'and enddate between '"+startyear+"' and  '"+Fin_enddate+"'  and type_id='CASLM'");
		if(rs2.next())
		{
	
			dep_open=rs2.getDouble("depreciated_opening");
			dep_add=rs2.getDouble("depreciated_addition");
			dep_del=rs2.getDouble("depreciated_deletion");
			
	
			//profit_loss=rs2.getDouble(12);
			//t_profit_loss+=profit_loss;
			//asst_tot=asst_tot+profit_loss;
	
			/*if(soldStatus)
			{
				asst_tot=0.00;
				//asst_del=asst_ope+asst_add-asst_del;
				dep_del=asondate+dep_open+t_dep_add;
			}*/
	
			//dep_tot=asondate+dep_open+dep_add-dep_del;
			t_asst_ope +=asst_ope;
			t_asst_add +=asst_add;
			t_asst_del +=asst_del;
			t_writeoff +=writeoff;
			t_asst_tot +=asst_tot;
			t_asondate +=asondate;
			t_dep_open +=dep_open;
			t_dep_add +=dep_add;
			t_dep_del +=dep_del;
			t_dep_tot +=dep_tot;
	
			if((asst_tot-dep_tot)<1)
			wdv=0;
			/*else
			wdv=(asst_tot-dep_tot);*/
			t_wdv+=wdv;
	%>
	<td nowrap class="lefttd"><%=asst_ope%></td>
	<td nowrap class="lefttd"><%=asst_add%></td>
	<td nowrap class="lefttd"><%=asst_del%></td>
	<!--td nowrap class="lefttd"><%=writeoff%></td>
	<td nowrap class="lefttd"><%=profit_loss%></td-->
	<td nowrap class="lefttd"><%=asst_tot%></td>
	<td nowrap class="lefttd"><%=asondate%></td>
	<td nowrap class="lefttd"><%=dep_open%></td>
	<td nowrap class="lefttd"><%=dep_add%></td>
	<td nowrap class="lefttd"><%=dep_del%></td>
	<td nowrap class="lefttd"><%=dep_tot%></td>
	<td nowrap class="lefttd"><%=wdv%></td>
	</tr>
	<%
		/*if((t_asst_tot-t_dep_tot)<1)
				t_wdv=0;
			else
			t_wdv=(t_asst_tot-t_dep_tot);
		*/
		}
		}
	
		}
	%>
			<tr>
				<td nowrap class="lefttd" colspan="14"><b>Total:</b></td>
				<td nowrap class="lefttd"><b><%=df.format(t_asst_ope)%></b></td>
				<td nowrap class="lefttd"><b><%=df.format(t_asst_add)%></b></td>
				<td nowrap class="lefttd"><b><%=df.format(t_asst_del)%></b></td>
				<!--td nowrap class="lefttd"><b><%=df.format(t_writeoff)%></b></td>
				<td nowrap class="lefttd"><b><%=df.format(t_profit_loss)%></b></td-->
				<td nowrap class="lefttd"><b><%=df.format(t_asst_tot)%></b></td>
				<td nowrap class="lefttd"><b><%=df.format(t_asondate)%></b></td>
				<td nowrap class="lefttd"><b><%=df.format(t_dep_open)%></b></td>
				<td nowrap class="lefttd"><b><%=df.format(t_dep_add)%></b></td>
				<td nowrap class="lefttd"><b><%=df.format(t_dep_del)%></b></td>
				<td nowrap class="lefttd"><b><%=df.format(t_dep_tot)%></b></td>
				<td nowrap class="lefttd"><b><%=df.format(t_wdv)%></b></td>
			</tr>
			</table><br>
	<%
			t_asst_ope=0.0;
			t_asst_add=0.0;
			t_asst_del=0.0;
			t_writeoff=0.0;
			t_profit_loss=0.0;
			t_asst_tot=0.0;
			t_asondate=0.0;
			t_dep_open=0.0;
			t_dep_add=0.0;
			t_dep_del=0.0;
			t_dep_tot=0.0;
			t_wdv=0.0;
			//}
			if(slno!=0){
	%>
	<table border="0" class ="table1" align="center" width="100%">
	<tr>
	<td class="centertd" >
	<!--  <input class="button" name="ee" type="submit" value="Export to Excel"> -->	
	</td>
	</tr>
	</table>
	<%
	
	}
	%>
	
	<%
	if(slno!=0)
	{
		if (exportToExcel == null) {
	%>
	
	<br>
	<a href="Monthly_Fa_Report_view.jsp?exportToExcel=YES&monthForFa=<%=Month%>&groupForFa=<%=Group_id%>&yearForFa=<%=Year%>">
				<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
				</a>
	<br>
	<b>Note :</b> <font color ="teal">The Assets marked with Yellow Color indicates <b> Sold Out </b> or <b>Written Off</b>.</font>
	<%
	}
	}
	%>
	</center>
	<input type="hidden" name="Month" value="<%=Month%>">
	<input type="hidden" name="Year" value="<%=Year%>">
	<input type="hidden" name="group_names" value="<%=Group_id%>">
	
	</form>
	<%
		}
		else
		{
	%>
			<br><br><center><b><font color="red" class=navigate>Depreciation Yet To Be Calculated</font></b></center>
	<%
		}
		}
		catch(Exception e)
		{
			out.println("test"+e);
		}
		//try{con.close();}catch(Exception e){out.println("Error"+e);}
		//try{con1.close();}catch(Exception e){out.println("Error1"+e);}
		//try{con2.close();}catch(Exception e){out.println("Error2"+e);}
		}
	%>
	
	</body>
	</html>