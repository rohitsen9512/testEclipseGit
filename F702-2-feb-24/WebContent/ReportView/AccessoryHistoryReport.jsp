<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*, java.text.SimpleDateFormat,java.util.*,java.text.DateFormat,com.Common.Common" %>
<%@ page import="dto.Common.DtoCommon" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Accessories History Report</title>

<!-- <script type ="text/javascript" src="js/jquery1.10.js"></script> -->
<!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome Icons -->
  <link rel="stylesheet" href="../plugins/fontawesome-free/css/all.min.css">
  <!-- overlayScrollbars -->
  <link rel="stylesheet" href="../plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
   
  <!-- Theme style -->
  <link rel="stylesheet" href="../dist/css/adminlte.min.css">
 
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
 <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="../CSS/home.css" rel="stylesheet" type="text/css">
<link href="../CSS/jquery-ui.css" rel="stylesheet" type="text/css">
<!-- DataTables CSS -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.4/css/jquery.dataTables.min.css">
  <link rel="stylesheet" href="../plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
  <link rel="stylesheet" href="../plugins/datatables-buttons/css/buttons.bootstrap4.min.css">

<!-- jQuery -->
<script src="../plugins/jquery/jquery.min.js"></script> 
  <script type ="text/javascript" src="../js/jquery1.10.js"></script>
<script type ="text/javascript" src="../js/jquery-ui.js"></script>

<!-- Bootstrap 4 -->
<script src="../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- AdminLTE for demo purposes -->
<!-- <script src="../dist/js/demo.js"></script>  -->
<!-- DataTables  & Plugins -->
<script src="../plugins/datatables/jquery.dataTables.min.js"></script>
<script src="../plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
<script src="../plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
<script src="../plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
<script src="../plugins/datatables-buttons/js/dataTables.buttons.min.js"></script>
<script src="../plugins/datatables-buttons/js/buttons.bootstrap4.min.js"></script>
<script src="../plugins/jszip/jszip.min.js"></script>
<script src="../plugins/pdfmake/pdfmake.min.js"></script>
<script src="../plugins/pdfmake/vfs_fonts.js"></script>
<script src="../plugins/datatables-buttons/js/buttons.html5.min.js"></script>
<script src="../plugins/datatables-buttons/js/buttons.print.min.js"></script>
<script src="../plugins/datatables-buttons/js/buttons.colVis.min.js"></script>
<!-- overlayScrollbars -->
<script src="../plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>

<!-- AdminLTE App -->
<script src="../dist/js/adminlte.min.js"></script>
<!-- Page specific script -->
<!-- bs-custom-file-input -->
<script src="../plugins/bs-custom-file-input/bs-custom-file-input.min.js"></script>
<!-- jQuery Mapael -->
<script src="../plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
<script src="../plugins/raphael/raphael.min.js"></script>
<script src="../plugins/jquery-mapael/jquery.mapael.min.js"></script>
<script src="../plugins/jquery-mapael/maps/usa_states.min.js"></script>
<script>
function search_table(){
	  // Declare variables 
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("search");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("table_id");
	  tr = table.getElementsByTagName("tr");

	  // Loop through all table rows, and hide those who don't match the search query
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td") ; 
	    for(j=0 ; j<td.length ; j++)
	    {
	      let tdata = td[j] ;
	      if (tdata) {
	        if (tdata.innerHTML.toUpperCase().indexOf(filter) > -1) {
	          tr[i].style.display = "";
	          break ; 
	        } else {
	          tr[i].style.display = "none";
	        }
	      } 
	    }
	  }
	}
</script>

</head>
<%
   String usertype = (String) session.getAttribute("UserName");
int UserId = (int)session.getAttribute("UserId");	
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
<div id="PreviewGRNApprovePendingReport">
	
		<%
		int slNo    =   0;
		String id_grp = "";
		String id_sgrp = "";
		String id_model = "";
		String sql="";
		String link="";
		String startdate = "" ,startdate1="";
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
	
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Accessory_History_Report.jsp.xls");
		 
		        }
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
					 id_model = request.getParameter("id_model");
					 
					 
					 
				
				String tempSql1 ="";
				if(!id_grp.equals("All"))
				{
					if(!id_sgrp.equals(""))
					{
						tempSql1 =" and awh1.id_grp="+id_grp+" and awh1.id_sgrp="+id_sgrp+"";
						if(!id_model.equals(""))
						{
							tempSql1 =" and awh1.id_grp="+id_grp+" and awh1.id_sgrp="+id_sgrp+" and awh1.id_model="+id_model+" ";
						}
						else
						{
							tempSql1 =" and awh1.id_grp="+id_grp+" and awh1.id_sgrp="+id_sgrp+" ";
						}
					}
					else
					{
						tempSql1 =" and awh1.id_grp="+id_grp+"";
					
					}
				}
				String UserType = (String)session.getAttribute("UserType");
				String id_dept = (String)session.getAttribute("DeptId");
				DtoCommon dtoCommon = new DtoCommon();
				if(UserType.equals("Super"))
				{
					sql = "select (replace(convert(NVARCHAR, ah.dt_link, 103), ' ', '-')) as dt_link,(replace(convert(NVARCHAR, ah.dt_dlink, 103), ' ', '-')) as dt_dlink,awh1.id_wh,awh1.id_wh_dyn,awh1.ds_pro,awh1.serial_no,grp.nm_assetdiv,sbgrp.nm_s_assetdiv,awh2.serial_no as asset_serial,awh2.id_wh as asset_id,awh2.id_wh_dyn as asset_cd,awh2.ds_pro as asset_nm from A_Ware_House awh1,A_Ware_House awh2,M_Asset_Div grp,M_Subasset_Div sbgrp,A_Accesssory_History ah where awh1.id_grp = grp.id_assetdiv and awh1.id_sgrp = sbgrp.id_s_assetdiv  and ah.id_parent = awh2.id_wh and  ah.id_accessory = awh1.id_wh "+tempSql1+"";
					
				}
				else
				{
					String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"awh1","awh1",request);
					sql = "select (replace(convert(NVARCHAR, ah.dt_link, 103), ' ', '-')) as dt_link,(replace(convert(NVARCHAR, ah.dt_dlink, 103), ' ', '-')) as dt_dlink,awh1.id_wh,awh1.id_wh_dyn,awh1.ds_pro,awh1.serial_no,grp.nm_assetdiv,sbgrp.nm_s_assetdiv,awh2.serial_no as asset_serial,awh2.id_wh as asset_id,awh2.id_wh_dyn as asset_cd,awh2.ds_pro as asset_nm from A_Ware_House awh1,A_Ware_House awh2,M_Asset_Div grp,M_Subasset_Div sbgrp,A_Accesssory_History ah where awh1.id_grp = grp.id_assetdiv and awh1.id_sgrp = sbgrp.id_s_assetdiv  and ah.id_parent = awh2.id_wh and  ah.id_accessory = awh1.id_wh "+tempQuery2+" "+tempSql1+"";
					
				}
				System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			%>
	<div class="card">
	<table id="AccessoryHistoryReport"
		class="table table-bordered table-hover AccessoryHistoryReport">
		<!-- <table id="table_id" style="border: 1px solid white;" border="1" width="120%" height="100%">
		

				<tr >
					<th colspan="11" style="background-color: blue;" width="100%"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 100px;"><strong>Accessory History Report</strong></p></td>
					
				</tr>	 -->
			<thead>	<tr>
				    <th><strong><center>S No. </center></strong></th>
				    <th><strong><center>Accessory ID </center></strong></th>
					<th><strong><center>Accessory Name </center></strong></th>
					<th><strong><center>Asset ID </center></strong></th>
					<th><strong><center>Asset Name </center></strong></th>
					<th><strong><center>Accessory Serial No. </center></strong></th>
					<th><strong><center>Asset Serial No. </center></strong></th>
					<th><strong><center>Group Name </center></strong></th>
					<th><strong><center>Sub Group Name </center></strong></th>
					<th><strong><center>Link Date </center></strong></th>
					<!-- <th><strong><center>Start Date </center></strong></th>-->
					<th><strong><center>De-Link Date </center></strong></th>
					<!--  <th><strong><center>Location </center></strong></th>
					<th><strong><center>Sub Location </center></strong></th>
					<th><strong><center>Building </center></strong></th> -->
					<!-- <th><strong><center>Floor </center></strong></th>
					<th><strong><center>Download File </center></strong></th>
					<th><strong>Cost Center </strong></th>-->
					 
				</tr>	
				</thead><tbody>
			<%
			String link_date="",dlink_date="";
			while(rs.next())
			{
			
				link_date = rs.getString("dt_link");
				if(link_date == null)
					link_date = "---";
				dlink_date = rs.getString("dt_dlink");
				if(dlink_date == "1900-01-01" || dlink_date == null  || dlink_date.equals(null))
					dlink_date = "---";
					
			
				%>
					<tr >
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString("id_wh_dyn")%></center></td>
					<td><center><%=rs.getString("ds_pro")%></center></td>
					<td><center><%=rs.getString("asset_cd")%></center></td>
					<td><center><%=rs.getString("asset_nm")%></center></td>
					<td><center><%=rs.getString("serial_no")%></center></td>
					<td><center><%=rs.getString("asset_serial")%></center></td>
					<td><center><%=rs.getString("nm_assetdiv")%></center></td>
					<td><center><%=rs.getString("nm_s_assetdiv")%></center></td>
					<td><center><%=link_date%></center></td>
					<td><center><%=dlink_date%></center></td>
				
					
					
				</tr>
				<%}
			}
			catch(Exception e)
			{
				out.println("sql error in Accessory Detail");
			}
		%>
		</tbody>
	</table>

</div>
</body>
<%
if(slNo !=0)
{
%>
<%-- <a href="AccessoryHistoryReport.jsp?exportToExcel=YES&id_model=<%=id_model%>&id_grp=<%=id_grp%>&id_sgrp=<%=id_sgrp%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a> --%>	

<%}
else
{%>
	<br>
		<!--  <b><center>No record(s) is available.</center></b>-->
<%}
	}%>
	
	<!-- DataTables  Utlity -->
<script type="text/javascript" src="js/data.js"></script>
<script type="text/javascript">
getButtonsForListView('AccessoryHistoryReport','Accessory_History_Report_List');

</script>
</html>