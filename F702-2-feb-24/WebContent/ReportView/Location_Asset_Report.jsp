

<%@page import="java.util.Calendar"%>
<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*, java.text.SimpleDateFormat,com.Common.Common,java.text.SimpleDateFormat,java.text.DateFormat,java.util.Date" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Master Asset Report</title>

<link href="WebContent/CSS/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

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
<div id="PreviewAssetForLocAsset">
	
		<%
		String startdate = "",startdate1 = "";
		String enddate = "",enddate1 = "";
		String warr_amc = "";
		String id_loc = "";
		String id_sloc = "";
		String id_building="";
		String id_flr = "";
		String id_grp = "";
		String id_sgrp = "";
		String allocate = "";
		String dt_amc_start = "" ;
		String dt_amc_exp ="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int years = 0;
	      int months = 0;
	      int days = 0;
		//Date date = sdf.parse("");
		Date d =new Date();
		//out.print(sdf.format(new Date()));d
		String today = sdf.format(d) ;
	    //  out.print(sdf.format(d));
		 /* Date a = new Date();
		 Date b = new Date();
	    out.print( sdf.format(b));    */ 
	     Calendar cal1 = Calendar.getInstance();
	    Calendar cal2 = Calendar.getInstance();
		String Group_id="",fin_id="";
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Location_Asset_Report.xls");
		 
		        }
		
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				 warr_amc = request.getParameter("warr_amc");
				 id_loc = request.getParameter("id_loc");
				 id_sloc = request.getParameter("id_sloc");
				 id_building = request.getParameter("id_building");
				 id_flr = request.getParameter("id_flr");
				 id_grp = request.getParameter("id_grp");
				 id_sgrp = request.getParameter("id_sgrp");
				 startdate = request.getParameter("startdate");
				 enddate = request.getParameter("enddate");
				 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
			     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd"); 
			    
				/*  startdate1 = startdate;
				 enddate1 = enddate;
				 if(!startdate.equals(""))
					{
					 startdate = dateFormatNeeded.format(userDateFormat.parse(startdate));
					}
				 if(!enddate.equals(""))
					{
					 enddate = dateFormatNeeded.format(userDateFormat.parse(enddate));
					} */
				
					String tempSql2 = "";
					if(!id_loc.equals("all"))
					{
						if(!id_sloc.equals(""))
						{
							if(!id_building.equals(""))
							{
								if(!id_flr.equals(""))
								{
								tempSql2 =" and wh.id_loc="+id_loc+" and wh.id_subl="+id_sloc+" and wh.id_building = "+id_building+" and wh.id_flr = "+id_flr+"";
								}
								else
								{
									tempSql2 ="and wh.id_loc="+id_loc+" and wh.id_subl="+id_sloc+" and wh.id_building = "+id_building+"";
								}
							}
							else
							{
								tempSql2 ="and wh.id_loc="+id_loc+" and wh.id_subl="+id_sloc+"";
							}
							
						}
						else
						{
							tempSql2 ="and wh.id_loc="+id_loc+"";
						}
					}
					
	
					String tempSql3 ="";
					if(!id_grp.equals("All"))
					{
						if(!id_sgrp.equals(""))
						{
							tempSql3 =" and wh.id_grp="+id_grp+" and wh.id_sgrp="+id_sgrp+"";
						}
						else
						{
							tempSql3 =" and wh.id_grp="+id_grp+"";
						}
					}
				
					
				
				String tempSql = "";
				if(!id_loc.equals("all"))
				{
					if(!id_sloc.equals(""))
					{
						if(!id_building.equals(""))
						{
							if(!id_flr.equals(""))
							{
								tempSql =" and wh.id_loc="+id_loc+" and wh.id_subl="+id_sloc+" and wh.id_building="+id_building+" and wh.id_flr = "+id_flr+"";
							}
							else
							{
								tempSql ="and wh.id_loc="+id_loc+" and wh.id_subl="+id_sloc+" and wh.id_building="+id_building+"";
							}
						
						}
						else
						{
							tempSql ="and wh.id_loc="+id_loc+" and wh.id_subl="+id_sloc+"";
						}
					}
					else
					{
						tempSql =" and wh.id_loc="+id_loc+"";
					}
				}
				String tempSql1 ="";
				if(!id_grp.equals("All"))
				{
					if(!id_sgrp.equals(""))
					{
						tempSql1 =" and wh.id_grp="+id_grp+" and wh.id_sgrp="+id_sgrp+"";
					}
					else
					{
						tempSql1 =" and wh.id_grp="+id_grp+"";
					}
				}
				
				
				
				
				
				String tempSql4 = "";
				if(!id_loc.equals("all"))
				{
					if(!id_sloc.equals(""))
					{
						if(!id_building.equals(""))
						{
							if(!id_flr.equals(""))
							{
								tempSql4 =" and wh.id_loc="+id_loc+" and wh.id_subl="+id_sloc+" and wh.id_building="+id_building+" and wh.id_flr = "+id_flr+"";
							}
							else
							{
								tempSql4 ="and wh.id_loc="+id_loc+" and wh.id_subl="+id_sloc+" and wh.id_building="+id_building+"";
							}
						}
						else
						{
							tempSql4 ="and wh.id_loc="+id_loc+" and wh.id_subl="+id_sloc+"";
						}
					}
					else
					{
						tempSql4 =" and wh.id_loc="+id_loc+"";
					}
				}
				String tempSql5 ="";
				if(!id_grp.equals("All"))
				{
					if(!id_sgrp.equals(""))
					{
						tempSql5 =" and wh.id_grp="+id_grp+" and wh.id_sgrp="+id_sgrp+"";
					}
					else
					{
						tempSql5 =" and wh.id_grp="+id_grp+"";
					}
				}
				
				
				
				
				
				String UserType = (String)session.getAttribute("UserType");
				
				String id_dept = (String)session.getAttribute("DeptId");
				if(UserType.equals("SUPER"))
					sql="select st_config,allocate,id_wh_dyn,ds_pro,nm_assetdiv,mfr,nm_model,no_mfr,nm_emp,ds_asst,(replace(convert(VARCHAR, wh.dt_po, 106), ' ', '-')) as dt_po,"+
					" (replace(convert(VARCHAR, wh.dt_inv, 106), ' ', '-')) as dt_inv,nm_loc,nm_subl,nm_building,nm_flr,(replace(convert(VARCHAR, wh.dt_alloc, 106), ' ', '-')) as dt_alloc,val_asst,nm_ven,wh.serial_no,no_po,no_inv,nm_computer, "+
							" nm_dept,warr_amc,(replace(convert(VARCHAR, dt_amc_start, 103), ' ', '-')) dt_amc_start, "+
							" (replace(convert(NVARCHAR, dt_amc_exp, 103), ' ', '-')) dt_amc_exp,nm_strg from A_Ware_House wh "+
						"	LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+
						"	LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc "+
						"	LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
						"	LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
						"	LEFT JOIN M_Building bul on wh.id_building=bul.id_building  "+
						"	LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
						"	LEFT JOIN M_Vendor ven on wh.id_ven_proc=ven.id_ven "+
						"	LEFT JOIN M_Asset_Div asst on wh.id_grp=asst.id_assetdiv "+
						"	LEFT JOIN M_Model model on wh.id_model=model.id_model  "+
						" LEFT JOIN M_Storage strg on wh.id_storage=strg.id_strg where wh.id_ven_proc=ven.id_ven  "+tempSql2+" " +tempSql3+"";
				
					//"select id_wh_dyn, ds_pro, mfr, no_mfr,l.nm_loc, sl.nm_subl, fl.nm_flr,dt_allocate,val_asst,nm_ven,wh.to_assign,wh.serial_no,wh.no_po,no_model,wh.allocate from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl , M_Vendor v where l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and fl.id_flr=wh.id_flr and v.id_ven=wh.id_ven_proc "+tempSql+" " +tempSql1+" ";
				 if(UserType.equals("DEPT"))
					sql="select st_config,allocate,id_wh_dyn,ds_pro,nm_assetdiv,mfr,nm_model,to_assign,no_mfr,nm_emp,ds_asst,(replace(convert(NVARCHAR, wh.dt_po, 103), ' ', '-')) as dt_po,"+
					" (replace(convert(NVARCHAR, wh.dt_inv, 103), ' ', '-')) as dt_inv,nm_loc,nm_subl,nm_building,nm_flr,(replace(convert(NVARCHAR, wh.dt_alloc, 103), ' ', '-')) as dt_alloc,val_asst,nm_ven,wh.serial_no,no_po,no_inv,nm_computer, "+
							" nm_dept,warr_amc,(replace(convert(NVARCHAR, dt_amc_start, 103), ' ', '-')) dt_amc_start, "+
							" (replace(convert(NVARCHAR, dt_amc_exp, 103), ' ', '-')) dt_amc_exp,nm_strg from A_Ware_House wh "+
						"	LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+
						"	LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc "+
						"	LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
						"	LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
						"	LEFT JOIN M_Building bul on wh.id_building=bul.id_building  "+
						"	LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
						"	LEFT JOIN M_Vendor ven on wh.id_ven_proc=ven.id_ven "+
						"	LEFT JOIN M_Asset_Div asst on wh.id_grp=asst.id_assetdiv "+
						"	LEFT JOIN M_Model model on wh.id_model=model.id_model  "+
						" LEFT JOIN M_Storage strg on wh.id_storage=strg.id_strg where wh.id_model=model.id_model and wh.id_dept="+id_dept+" "+tempSql4+" " +tempSql5+"";
					
				 if(UserType.equals("IT"))
					sql="select st_config,allocate,id_wh_dyn,ds_pro,nm_assetdiv,mfr,nm_model,no_mfr,nm_emp,ds_asst,(replace(convert(NVARCHAR, wh.dt_po, 103), ' ', '-')) as dt_po,"+
					" (replace(convert(NVARCHAR, wh.dt_inv, 103), ' ', '-')) as dt_inv,nm_loc,nm_subl,nm_building,nm_flr,(replace(convert(NVARCHAR, wh.dt_alloc, 103), ' ', '-')) as dt_alloc,val_asst,nm_ven,wh.serial_no,no_po,no_inv,nm_computer, "+
							" nm_dept,warr_amc,(replace(convert(NVARCHAR, dt_amc_start, 103), ' ', '-')) dt_amc_start, "+
							" (replace(convert(NVARCHAR, dt_amc_exp, 103), ' ', '-')) dt_amc_exp,nm_strg from A_Ware_House wh "+
						"	LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+
						"	LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc "+
						"	LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
						"	LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
						"	LEFT JOIN M_Building bul on wh.id_building=bul.id_building  "+
						"	LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
						"	LEFT JOIN M_Vendor ven on wh.id_ven_proc=ven.id_ven "+
						"	LEFT JOIN M_Asset_Div asst on wh.id_grp=asst.id_assetdiv "+
								"	LEFT JOIN M_Model model on wh.id_model=model.id_model  "+
						"   LEFT JOIN M_Storage strg on wh.id_storage=strg.id_strg where wh.id_model=model.id_model and typ_asst = '"+UserType+"'  "+tempSql+" " +tempSql1+" ";
					
				 if(UserType.equals("NIT"))
						sql="select st_config,allocate,id_wh_dyn,ds_pro,nm_assetdiv,mfr,nm_model,to_assign,no_mfr,nm_emp,ds_asst,(replace(convert(NVARCHAR, wh.dt_po, 103), ' ', '-')) as dt_po,"+
								" (replace(convert(NVARCHAR, wh.dt_inv, 103), ' ', '-')) as dt_inv,nm_loc,nm_subl,nm_building,nm_flr,(replace(convert(NVARCHAR, wh.dt_alloc, 103), ' ', '-')) as dt_alloc,val_asst,nm_ven,wh.serial_no,no_po,no_inv,nm_computer, "+
								" nm_dept,warr_amc,(replace(convert(NVARCHAR, dt_amc_start, 103), ' ', '-')) dt_amc_start, "+
								" (replace(convert(NVARCHAR, dt_amc_exp, 103), ' ', '-')) dt_amc_exp,nm_strg from A_Ware_House wh "+
							"	LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+
							"	LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc "+
							"	LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
							"	LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
							"	LEFT JOIN M_Building bul on wh.id_building=bul.id_building  "+
							"	LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
							"	LEFT JOIN M_Vendor ven on wh.id_ven_proc=ven.id_ven "+
							"	LEFT JOIN M_Asset_Div asst on wh.id_grp=asst.id_assetdiv "+
							"	LEFT JOIN M_Model model on wh.id_model=model.id_model  "+
							"   LEFT JOIN M_Storage strg on wh.id_storage=strg.id_strg where wh.id_model=model.id_model and typ_asst = '"+UserType+"' "+tempSql+" " +tempSql1+" ";
						
				 
				//out.print(sql);
					//"select id_wh_dyn, ds_pro, mfr, no_mfr,l.nm_loc, sl.nm_subl, fl.nm_flr,dt_allocate,val_asst,nm_ven,wh.to_assign,wh.serial_no,wh.no_po,no_model,wh.allocate from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl , M_Vendor v where l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and fl.id_flr=wh.id_flr and v.id_ven=wh.id_ven_proc and typ_asst = '"+UserType+"' "+tempSql+" " +tempSql1+" ";
 		
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			
				
			{%>
	<table class="table" border="1" width="300%" height="100%">
		
				<tr >
					<td colspan="30" style="background-color: blue;" ><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">Master Asset Report</p></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong><center>S No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Asset ID &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Asset Name &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Group Name &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Status &nbsp;&nbsp;</center></strong></td>
						<td style="background-color: lavender;"><strong><center>Storage Name &nbsp;&nbsp;</center></strong></td>
					<!-- <td style="background-color: lavender;"><strong><center>Model No. &nbsp;&nbsp;</center></strong></td> -->
					<td style="background-color: lavender;"><strong><center>Asset Manufacturer &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Serial No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Employee Name &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Purchase Date &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Invoice Date &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Location &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Sub Location &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Building &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Floor &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Allocation Date &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Vendor Name &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>SAP No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>PO No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Invoice No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Asset Value &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Department Name &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Computer Name &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Warranty Type &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Start date &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>End date &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center> Warranty Status &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center> Asset Aging  &nbsp;&nbsp;</center></strong></td>
					
				</tr>	
				
			<%} 
			
			///////////////////////////////////////
			
			
			/////////////////////////////////
				while(rs.next())
				{
					String warr="",nm_emp="",nm_flr="";
							int aging=0;
							String maging="";
					String status = "In Store";
				if(rs.getString("allocate").equals("1"))
					status = "Installed";
				if(rs.getString("allocate").equals("5"))
					status = "Store";
				if(rs.getString("allocate").equals("4"))
					status = " In Configuration IT";
				if(rs.getString("allocate").equals("7"))
					status = " In To Be Sold / To Be Disposed";
				if(rs.getString("allocate").equals("6"))
					status = " In Under Repair";
				
				if(rs.getString("st_config").equals("No") && ( rs.getString("allocate").equals("0") || rs.getString("allocate").equals("2")))
				{
					
						status=" Store";
				}  
				  if(rs.getString("st_config").equals("Yes") && ( rs.getString("allocate").equals("0") || rs.getString("allocate").equals("2")))   
					{
						
							status="In Send For IT Configuration";
					}
	
				
						
				
				if(rs.getString("warr_amc").equals("W"))
					warr_amc = "Warranty";
				if(rs.getString("warr_amc").equals("A"))
					warr_amc = "AMC";
				if(rs.getString("warr_amc").equals("O"))
					warr_amc = "--";
				if(rs.getString("dt_amc_start").equals("01/01/1900"))
					dt_amc_start = "--";
				else
					dt_amc_start=rs.getString("dt_amc_start");
				
				if(rs.getString("dt_amc_exp").equals("01/01/1900"))
					dt_amc_exp = "--";
				else
				{
					dt_amc_exp=rs.getString("dt_amc_exp");
				if(sdf.parse(dt_amc_exp).before(sdf.parse(today))){
					warr = "Not  In warranty ";
				     }
				else
					warr = " In warranty ";
				}
				if(rs.getString("nm_emp")== null) 
					nm_emp="--";
				else
					nm_emp=rs.getString("nm_emp");
				
				if(rs.getString("nm_flr")== null) 
					nm_flr="--";
				else
					nm_flr=rs.getString("nm_flr");
				
				
				
////////////////////////////////////////////////////////////////
if(!rs.getString("dt_amc_start").equals("01/01/1900")){
long currentTime = System.currentTimeMillis();
Calendar now = Calendar.getInstance();
now.setTimeInMillis(currentTime);

Calendar startDate = Calendar.getInstance();
startDate.setTimeInMillis(sdf.parse(dt_amc_start).getTime());

years = now.get(Calendar.YEAR) - startDate.get(Calendar.YEAR);
int currMonth = now.get(Calendar.MONTH) + 1;
int birthMonth = startDate.get(Calendar.MONTH) + 1;
//Get difference between months
months = currMonth - birthMonth;
//if month difference is in negative then reduce years by one and calculate the number of months.
if (months < 0)
{
years--;
months = 12 - birthMonth + currMonth;
if (now.get(Calendar.DATE) < startDate.get(Calendar.DATE))
months--;
} else if (months == 0 && now.get(Calendar.DATE) < startDate.get(Calendar.DATE))
{
years--;
months = 11;
}
//Calculate the days
if (now.get(Calendar.DATE) > startDate.get(Calendar.DATE))
days = now.get(Calendar.DATE) - startDate.get(Calendar.DATE);
else if (now.get(Calendar.DATE) < startDate.get(Calendar.DATE))
{
int todays = now.get(Calendar.DAY_OF_MONTH);
now.add(Calendar.MONTH, -1);
days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - startDate.get(Calendar.DAY_OF_MONTH) + todays;
} else
{
days = 0;
if (months == 12)
{
years++;
months = 0;
}
}

}
else{
years=0;
months=0;
days=0;
}
			    
				
				%>
					<tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString("id_wh_dyn")%></center></td>
					<td><center><%=rs.getString("ds_pro")%></center></td>
					<td><center><%=rs.getString("nm_assetdiv")%></center></td>
					<td><center><%=status%></center></td>
					<td><center><%=rs.getString("nm_strg")%></center></td>
					<td><center><%=rs.getString("nm_model")%></center></td>
					<td><center><%=rs.getString("mfr")%></center></td>
					<td><center><%=rs.getString("no_mfr")%></center></td>
					<td><center><%=nm_emp%></center></td>
					<td><center><%=rs.getString("dt_po")%></center></td>
					<td><center><%=rs.getString("dt_inv")%></center></td>
					<td><center><%=rs.getString("nm_loc")%></center></td>
					<td><center><%=rs.getString("nm_subl")%></center></td>
					<td><center><%=rs.getString("nm_building")%></center></td>
					<td><center><%=nm_flr%></center></td>
					<td><center><%=rs.getString("dt_alloc")%></center></td>
					<td><center><%=rs.getString("nm_ven")%></center></td>
					<td><center><%=rs.getString("serial_no")%></center></td>
					<td><center><%=rs.getString("no_po")%></center></td>
					<td><center><%=rs.getString("no_inv")%></center></td>
					<td><center><%=rs.getString("val_asst")%></center></td>
					<td><center><%=rs.getString("nm_dept")%></center></td>
					<td><center><%=rs.getString("nm_computer")%></center></td>
					<td><center><%=warr_amc%></center></td>
					<td><center><%=dt_amc_start%></center></td>
					<td><center><%=dt_amc_exp%></center></td>
					
					<td><center><%=warr%></center></td>
					<td><center><%=years%>.<%=months%> </center></td>
					
				</tr>
				<%}
				}
				catch(Exception e)
				{
					out.println("sql error in Master report.");
				}
			
			%>			
			
		</table>
				
			
		
</div>
	
</body>
<a href="Location_Asset_Report.jsp?exportToExcel=YES&id_loc=<%=id_loc%>&id_sloc=<%=id_sloc%>&id_building=<%=id_building%>&id_flr=<%=id_flr%>&id_grp=<%=id_grp%>&id_sgrp=<%=id_sgrp%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 500px;">Export To Excel</button>
			</a>	
			<%} %>
</html>