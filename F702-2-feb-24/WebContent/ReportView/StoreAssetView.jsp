<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Store Asset Reports</title>
<!-- <link href="WebContent/CSS/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"> -->
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
<%
String id_grp="",id_sgrp="";
        String exportToExcel = request.getParameter("exportToExcel");
        
        if (exportToExcel != null
                && exportToExcel.toString().equalsIgnoreCase("YES")) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "inline; filename="
                    + "Store_Asset_Report.xls");
 
        }
    %>


<form action="" method="post">
<div id="PreviewAssetForStoreAsset">
	
		<%
		
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
					 
				 id_grp = request.getParameter("id_grp");
				 id_sgrp = request.getParameter("id_sgrp");
				String tempSql = "";
if(!id_grp.equals("All"))
					
				{
					if(!id_sgrp.equals(""))
					{
						tempSql =" and wh.id_grp="+id_grp+" and wh.id_sgrp="+id_sgrp+"";
					}
					else
					{
						tempSql =" and wh.id_grp="+id_grp+"";
					}
				}
				String UserType = (String)session.getAttribute("UserType");
				String id_dept = (String)session.getAttribute("DeptId");
				String tempQuery="";
				String FlrId = (String)session.getAttribute("FlrId");
				if(!UserType.equals("SUPER"))
				if(!FlrId.equals(""))
				{
					String id_flr1[] = FlrId.split(",");
					for(int i=0 ; i < id_flr1.length ; i++)
					{
						if(tempQuery.equals(""))
						{
							tempQuery = " and (wh.id_flr = "+id_flr1[i]+"";
						}
						else
						{
							tempQuery += " or wh.id_flr = "+id_flr1[i]+"";
						}
					}
					tempQuery += ")";
				}
				
				//if(UserType.equals("SUPER"))
					sql="select id_wh_dyn,ds_pro,no_mfr,ds_asst, mfr,nm_model,l.nm_loc, sl.nm_subl,bul.nm_building, val_asst,nm_dept,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) dt_po ,nm_div,nm_flr from A_Ware_House wh ,M_Loc l,M_Subloc sl,M_Dept de,M_Model modl,M_Building bul ,M_Floor f,M_Company_Division cd where (allocate = 0 or allocate = 2) and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building and wh.id_model=modl.id_model and wh.id_dept=de.id_dept and f.id_flr=wh.id_flr and cd.id_div=wh.id_div "+tempSql+" "+tempQuery+" ";
				/* if(UserType.equals("DEPT"))
					sql="select id_wh_dyn,ds_pro,no_mfr,ds_asst, mfr,nm_model,l.nm_loc, sl.nm_subl,bul.nm_building, val_asst,nm_dept,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) dt_po from A_Ware_House wh ,M_Loc l,M_Subloc sl,M_Dept de,M_Model modl,M_Building bul where (allocate = 0 or allocate = 2) and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building and wh.id_dept=de.id_dept and wh.id_model=modl.id_model and wh.id_dept="+id_dept+" "+tempSql+"";
				
				if(UserType.equals("IT"))
					sql="select id_wh_dyn,ds_pro,no_mfr,ds_asst, mfr,nm_model,l.nm_loc, sl.nm_subl,bul.nm_building, val_asst,nm_dept,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) dt_po from A_Ware_House wh ,M_Loc l,M_Subloc sl,M_Dept de,M_Model modl,M_Building bul where (allocate = 0 or allocate = 2) and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building and wh.id_dept=de.id_dept and wh.id_model=modl.id_model and st_config='Yes' "+tempSql+"";
				
				if(UserType.equals("NIT"))
					sql="select id_wh_dyn,ds_pro,no_mfr,ds_asst, mfr,nm_model,l.nm_loc, sl.nm_subl,bul.nm_building, val_asst,nm_dept,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) dt_po from A_Ware_House wh ,M_Loc l,M_Subloc sl,M_Dept de,M_Model modl,M_Building bul where (allocate = 0 or allocate = 2) and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and bul.id_building=wh.id_building and wh.id_dept=de.id_dept and wh.id_model=modl.id_model and typ_asst = '"+UserType+"' "+tempSql+"";
				 */
//out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			{%>
		<table class="table table-bordered" style="border: 1px solid white;" border="1" id="testTable"   width="200%" height="100%">
		
				<tr>
					<td colspan="15"  style="background-color: blue;"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;">Store Asset Report</p></td>
				</tr>	
				<tr>
				    <td  style="background-color: lavender;"><strong><center>S No. &nbsp;&nbsp;</center></strong></td>
					<td  style="background-color: lavender;"><strong><center>Asset ID &nbsp;&nbsp;</center></strong></td>
					<td  style="background-color: lavender;"><strong><center>Asset Name &nbsp;&nbsp;</center></strong></td>
					<td  style="background-color: lavender;"><strong><center>Serial No. &nbsp;&nbsp;</center></strong></td>
					<td  style="background-color: lavender;"><strong><center>Purchase Date &nbsp;&nbsp;</center></strong></td>
					<td  style="background-color: lavender;"><strong><center>Asset Description &nbsp;&nbsp;</center></strong></td>
					<td  style="background-color: lavender;"><strong><center>Manufacturer &nbsp;&nbsp;</center></strong></td>
					<!-- <td  style="background-color: lavender;"><strong><center>Model No. &nbsp;&nbsp;</center></strong></td> -->
					<td  style="background-color: lavender;"><strong><center>Division &nbsp;&nbsp;</center></strong></td>
					<td  style="background-color: lavender;"><strong><center>Region &nbsp;&nbsp;</center></strong></td>
					<td  style="background-color: lavender;"><strong><center>State &nbsp;&nbsp;</center></strong></td>
							<td  style="background-color: lavender;"><strong><center>City &nbsp;&nbsp;</center></strong></td>
							<td  style="background-color: lavender;"><strong><center>Store &nbsp;&nbsp;</center></strong></td>		
					<td  style="background-color: lavender;"><strong><center>Department &nbsp;&nbsp;</center></strong></td>					
					<td  style="background-color: lavender;"><strong><center>UnitCost in RS &nbsp;&nbsp;</center></strong></td>
				</tr>	
				
			<%} 
				while(rs.next())
				{%>
					<tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString(1)%></center></td>
					<td><center><%=rs.getString(2)%></center></td>
					<td><center><%=rs.getString(3)%></center></td>
					<td><center><%=rs.getString(12)%></center></td>
					<td><center><%=rs.getString(4)%></center></td>
					
					<td><center><%=rs.getString(5)%></center></td>
					
					<td><center><%=rs.getString(6)%></center></td>
					<td><center><%=rs.getString(13)%></center></td>
					<td><center><%=rs.getString(7)%></center></td>
					<td><center><%=rs.getString(8)%></center></td>
					<td><center><%=rs.getString(9)%></center></td>
					<td><center><%=rs.getString(14)%></center></td>
					<td><center><%=rs.getString(11)%></center></td>
					<td><center><%=rs.getString(10)%></center></td>
					
					
				</tr>
				<%} 
			}
			catch(Exception e)
			{
				out.println("sql error in StoreAssetView.");
			}
		%>
		<tr>
		<%
        if (exportToExcel == null) {
    %>
    <td colspan="15">
			<a href="StoreAssetView.jsp?exportToExcel=YES&id_grp=<%=id_grp%>&id_sgrp=<%=id_sgrp%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 600px;">Export To Excel</button>
			</a>		
			</td>
    
    <%
        }
    %>
			
		</tr>
	</table>
</div>
</form>
</body>
<%} %>
</html>