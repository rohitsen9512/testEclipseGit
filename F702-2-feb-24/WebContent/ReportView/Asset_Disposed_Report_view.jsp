<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,java.util.Date,com.Common.Common, java.text.SimpleDateFormat" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Store Asset Reports</title>

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
String id_grp="";
String asset_status = "";
String fin_id="";

        String exportToExcel = request.getParameter("exportToExcel");
        
        if (exportToExcel != null
                && exportToExcel.toString().equalsIgnoreCase("YES")) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "inline; filename="
                    + "Asset_Disposed_Report_view.jsp.xls");
 
        }
    %>


<form action="" method="post">
<div id="PreviewAssetForStoreAsset">
	
		<%
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	
		
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
					 
				 id_grp = request.getParameter("group_name");
				 asset_status = request.getParameter("dispose");
				 fin_id=request.getParameter("fin_year");
				 
				 String Fin_stdate="",Fin_enddate="",fin_years="",Fin_enddate1="",Fin_stdate1="";
				 
				 Statement sm5 = con.createStatement();
				 
					ResultSet rs5 = null;
				 if(!fin_id.equals("All")){
						fin_years = "where id_fincance="+fin_id+"";
					}
					
			//out.print("Select std_finance,end_finance from M_Finance_Year "+ fin_years + " ORDER BY std_finance");
					rs5= sm5.executeQuery("Select std_finance,end_finance,(replace(convert(NVARCHAR, std_finance, 103), ' ', '-')) as std_finance1,(replace(convert(NVARCHAR, end_finance, 103), ' ', '-')) as end_finance1 from M_Finance_Year "+ fin_years + " ORDER BY std_finance");
					while(rs5.next()){
						Fin_stdate=rs5.getString(1);
						Fin_enddate=rs5.getString(2);
						Fin_stdate1=rs5.getString(3);
						Fin_enddate1=rs5.getString(4);
				 
				 
				String tempSql = "";
if(!id_grp.equals("All"))
					
				{
					tempSql =" and ad.asset_group_id="+id_grp+"";
				}
if(asset_status.equals("sold"))
{
				sql="select a.ds_pro,ad.AssetName,ad.asset_description,cc.nm_cc,(replace(convert(NVARCHAR, a.dt_allocate, 103), ' ', '-')) as dt_allocate,a.val_asst,"+
						" (replace(convert(NVARCHAR, ad.adddate, 103), ' ', '-')) as adddate,ad.asset_status,ad.addamt from addition_deletion ad, M_Company_Costcenter cc, A_Ware_House a"+
						" where ad.assetid=a.id_wh and a.id_cc=cc.id_cc  and ad.stadate >=  '"+Fin_stdate+"' and ad.stadate <=  '"+Fin_enddate+"' "+tempSql+"";
				
}
else
{
	sql="select a.ds_pro,ad.AssetName,ad.asset_description,cc.nm_cc,(replace(convert(NVARCHAR, a.dt_allocate, 103), ' ', '-')) as dt_allocate,a.val_asst,"+
			" (replace(convert(NVARCHAR, ad.adddate, 103), ' ', '-')) as adddate,ad.asset_status,ad.addamt from addition_deletion ad, M_Company_Costcenter cc, A_Ware_House a"+
			" where ad.assetid=a.id_wh and a.id_cc=cc.id_cc and ad.asset_status='"+asset_status+"'  and ad.stadate >=  '"+Fin_stdate+"' and ad.stadate <=  '"+Fin_enddate+"' "+tempSql+"";
	//out.print(sql);
}

				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			{%>
			<br>
		<table class="table table-bordered"  id="testTable" border="1" style="width:100%;height:100%;">
		
				<tr>
					<td colspan="10"  style="background-color: blue;"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 25%;">Asset Disposed Report For <%=Fin_stdate1 %> / <%= Fin_stdate1%> Financial Year</p></td>
				</tr>	
				<tr>
				    <td  style="background-color: lavender;"><strong>S No. &nbsp;&nbsp;</strong></td>
					<td  style="background-color: lavender;"><strong>Asset ID &nbsp;&nbsp;</strong></td>
					<td  style="background-color: lavender;"><strong>Asset Name &nbsp;&nbsp;</strong></td>
					<td  style="background-color: lavender;"><strong>Asset Description &nbsp;&nbsp;</strong></td>
					<td  style="background-color: lavender;"><strong>Cost Center &nbsp;&nbsp;</strong></td>
					<td  style="background-color: lavender;"><strong>Allocated Date &nbsp;&nbsp;</strong></td>
					<td  style="background-color: lavender;"><strong>Asset Value &nbsp;&nbsp;</strong></td>
					<td  style="background-color: lavender;"><strong> Date &nbsp;&nbsp;</strong></td>
					<td  style="background-color: lavender;"><strong> Status &nbsp;&nbsp;</strong></td>
					<td  style="background-color: lavender;"><strong> Value &nbsp;&nbsp;</strong></td>					
				</tr>	
				
			<%} 
				while(rs.next())
				{%>
					<tr>
					<td><%=++slNo%></td>
					<td><%=rs.getString(2)%></td>
					<td><%=rs.getString(1)%></td>
					<td><%=rs.getString(3)%></td>
					<td><%=rs.getString(4)%></td>
					<td><%=rs.getString(5)%></td>
					<td><%=rs.getString(6)%></td>
					<td><%=rs.getString(7)%></td>
					<td><%=rs.getString(8)%></td>
					<td><%=rs.getString(9)%></td>
					
					
				</tr>
				<%} 
					}
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
    <td colspan="10">
			<a href="Asset_Disposed_Report_view.jsp?exportToExcel=YES&group_name=<%=id_grp%>&dispose=<%=asset_status%>&fin_year=<%=fin_id%>">
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