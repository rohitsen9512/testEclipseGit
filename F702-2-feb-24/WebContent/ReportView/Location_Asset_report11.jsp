

<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*, java.text.SimpleDateFormat,com.Common.Common" %>
<%@ page import="dto.Common.DtoCommon" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- <link href="WebContent/CSS/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"> -->

<title>Master Asset Report</title>
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
		
		String id_loc = "",id_div="";
		String id_sloc = "";
		String id_flr = "";
		String id_grp = "";
		String id_sgrp = "";
		String allocate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
		
		String Group_id="",fin_id="";
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Location_Asset_report.xls");
		 
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
				
				 id_div = request.getParameter("id_div");
				 id_loc = request.getParameter("id_loc");
				 id_sloc = request.getParameter("id_sloc");
				 id_flr = request.getParameter("id_flr"); 
				 id_grp = request.getParameter("id_grp");
				 id_sgrp = request.getParameter("id_sgrp");
				
				
				
				String tempSql = "";
				if(!id_div.equals("all"))
				{
					if(!id_loc.equals(""))
					{
						if(!id_sloc.equals(""))
						{
							if(!id_flr.equals(""))
							{
								tempSql =" and wh.id_loc="+id_loc+" and wh.id_subl="+id_sloc+" and wh.id_flr = "+id_flr+" and wh.id_div="+id_div+"";
							}
							else
							{
								tempSql =" and wh.id_loc="+id_loc+" and wh.id_subl="+id_sloc+" and wh.id_div="+id_div+"";
							}
							//tempSql =" and wh.id_loc="+id_loc+" and wh.id_subl="+id_sloc+"";
						}
						else
						{
							tempSql =" and wh.id_loc="+id_loc+" and wh.id_div="+id_div+" ";
						}
					}else{
						tempSql =" and wh.id_div="+id_div+"";
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
				String UserType = (String)session.getAttribute("UserType");
				//if(UserType.equals("SUPER"))
					sql="select id_wh_dyn, ds_pro, mfr, no_mfr, l.nm_loc, sl.nm_subl, f.nm_flr,qty_asst, dt_allocate,val_asst,wh.to_assign,wh.serial_no,wh.no_po,no_model,nm_model,wh.allocate ,wh.no_inv,(replace(convert(NVARCHAR, wh.dt_inv, 103), ' ', '-')) as dt_inv,nm_assetdiv,typ_asst,nm_s_assetdiv, nm_model, wh.no_bd,nm_building,wh.appNo,nm_div,mst.nm_strg from A_Ware_House wh,M_Loc l,"+
        					" M_Subloc sl ,M_Company_Division cd ,M_Asset_Div ad,M_Subasset_Div sd, M_Floor f, M_building mb,M_Model mm,M_Storage mst where mm.id_model= wh.id_model and l.id_loc=wh.id_loc  and sl.id_sloc=wh.id_subl and ad.id_assetdiv=wh.id_grp and sd.id_s_assetdiv=wh.id_sgrp and mb.id_building=wh.id_building and wh.id_flr=f.id_flr and cd.id_div=wh.id_div and mst.id_strg=wh.id_storage "+tempSql+" " +tempSql1+" ";
					
					//"select id_wh_dyn, ds_pro, mfr, no_mfr,l.nm_loc, sl.nm_subl, fl.nm_flr,dt_allocate,val_asst,nm_ven,wh.to_assign,wh.serial_no,wh.no_po,no_model,wh.allocate from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl , M_Vendor v where l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and fl.id_flr=wh.id_flr and v.id_ven=wh.id_ven_proc "+tempSql+" " +tempSql1+" ";
				
					/* else
						sql="select id_wh_dyn, ds_pro, mfr, no_mfr,l.nm_loc, sl.nm_subl, f.nm_flr,qty_asst, dt_allocate,val_asst,wh.to_assign,wh.serial_no,wh.no_po,no_model,wh.allocate ,wh.no_inv,(replace(convert(NVARCHAR, wh.dt_inv, 103), ' ', '-')) as dt_inv,nm_assetdiv,nm_model,typ_asst,nm_s_assetdiv, f.nm_flr, wh.no_bd,nm_building,wh.appNo from A_Ware_House wh,M_Loc l,"+
	        					" M_Subloc sl,M_Asset_Div ad,M_Subasset_Div sd, M_Floor f where l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and ad.id_assetdiv=wh.id_grp and sd.id_s_assetdiv=wh.id_sgrp and wh.id_flr=f.id_flr "+tempSql+" " +tempSql1+" ";
					 */
					//"select id_wh_dyn, ds_pro, mfr, no_mfr,l.nm_loc, sl.nm_subl, fl.nm_flr,dt_allocate,val_asst,nm_ven,wh.to_assign,wh.serial_no,wh.no_po,no_model,wh.allocate from A_Ware_House wh,M_Loc l,M_Subloc sl, M_Floor fl , M_Vendor v where l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and fl.id_flr=wh.id_flr and v.id_ven=wh.id_ven_proc and typ_asst = '"+UserType+"' "+tempSql+" " +tempSql1+" ";
				System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			
				
			{%>
	<table class="table" style="border: 1px solid white;" border="1" width="150%">
		
				<tr >
					<td colspan="22" style="background-color: blue;"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">Master Asset Report</p></td>
				</tr>	
				<tr>
				    <td style="background-color: lavender;"><strong><center>S No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Asset ID &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Group Name &nbsp;&nbsp;</center></strong></td>
					<!-- <td style="background-color: lavender;"><strong><center>Asset Group :&nbsp;&nbsp;</center></strong></td> -->
					<td style="background-color: lavender;"><strong><center>Sub Group Name &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Asset Manufacture &nbsp;&nbsp;</center></strong></td>
					<!-- <td style="background-color: lavender;"><strong><center>Model No. &nbsp;&nbsp;</center></strong></td> -->
					<td style="background-color: lavender;"><strong><center>Quantity &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Serial No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Tag No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>App No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Division &nbsp;&nbsp;</center></strong></td>
					
					<td style="background-color: lavender;"><strong><center>Region &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>State &nbsp;&nbsp;</center></strong></td>
				    <td style="background-color: lavender;"><strong><center>City &nbsp;&nbsp;</center></strong></td>
				    <td style="background-color: lavender;"><strong><center>Store Name &nbsp;&nbsp;</center></strong></td>
				    <td style="background-color: lavender;"><strong><center>Asset Entry Store Name &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Status &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>PO No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Invoice No. &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>Invoice Date &nbsp;&nbsp;</center></strong></td>
					<!-- <td style="background-color: lavender;"><strong><center>Bond NO &nbsp;&nbsp;</center></strong></td> -->
					<td style="background-color: lavender;"><strong><center>Asset Value &nbsp;&nbsp;</center></strong></td>
					<td style="background-color: lavender;"><strong><center>IT/NON-IT/OTHERS &nbsp;&nbsp;</center></strong></td>
				</tr>	
				
			<%} 
				while(rs.next())
				{
					String status = "In Store";
				if(rs.getString("allocate").equals("1"))
					status = "Allocated";
				%>
				<tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString("id_wh_dyn")%></center></td>
					<td><center><%=rs.getString("nm_assetdiv")%><center></td>
					<%-- <td><center><%=rs.getString("ds_pro")%><center></td> --%>
					<td><center><%=rs.getString("nm_s_assetdiv")%><center></td>
					<td><center><%=rs.getString("mfr")%><center></td>
					<td><center><%=rs.getString("nm_model")%><center></td>
					<td><center><%=rs.getString("qty_asst")%><center></td>
					<td><center><%=rs.getString(4)%><center></td>
					<td><center><%=rs.getString("serial_no")%><center></td>
				<td><center><%=rs.getString("appNo")%><center></td>
				<td><center><%=rs.getString("nm_div")%><center></td>
					<td><center><%=rs.getString(5)%><center></td>
					<td><center><%=rs.getString(6)%><center></td>
					<td><center><%=rs.getString("nm_building")%><center></td>
					<td><center><%=rs.getString(7)%><center></td>
					<td><center><%=rs.getString("nm_strg")%><center></td>
					<td><center><%=status%><center></td>
					<td><center><%=rs.getString("no_po")%><center></td>
					<td><center><%=rs.getString("no_inv")%><center></td>
					<td><center><%=rs.getString("dt_inv")%><center></td>
					<%-- <td><center><%=rs.getString("no_bd")%><center></td> --%>
					<td><center><%=rs.getString("val_asst")%><center></td>
					<td><center><%=rs.getString("typ_asst")%><center></td>
				</tr>
				<%}
				}
				catch(Exception e)
				{
					out.println("sql error in Employee Asset View." +e);
				}
			
			%>			
			
		</table>
				
			
		
</div>
	
</body>
<a href="Location_Asset_report1.jsp?exportToExcel=YES&id_flr=<%=id_flr%>&id_div=<%=id_div%>&id_loc=<%=id_loc%>&id_sloc=<%=id_sloc%>&id_grp=<%=id_grp%>&id_sgrp=<%=id_sgrp%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 500px;">Export To Excel</button>
			</a>	
			<%} %>
</html>

