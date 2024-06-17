<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Procurement Type Report</title><style type="text/css">
.headerTR{
background-color: blue;
}
.headerTD{
background-color: lavender;
}
</style>
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
  <link rel="stylesheet" href="../plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
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
<div id="PreviewAssetForProcAsset">
	
		<%
		
			String sql="";
		String typ_proc = "",allocate="";
		String id_grp = "";
	
		String Group_id="",fin_id="";
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Procurement_Type_Report.xls");
		 
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
				 typ_proc = request.getParameter("typ_proc");
				 allocate = request.getParameter("allocate");
				int t=0;
								
				String tempSql = "";
				
				if(!id_grp.equals("All"))
				{
						
						tempSql =" and wh.id_grp="+id_grp+"";
						
				}
				
				if (allocate.equals("1") || allocate.equals("2") || allocate.equals("3") )	
					
				{
					t=1;
					if(allocate.equals("1") || allocate.equals("3"))
					{
						sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,no_model,l.nm_loc, sl.nm_subl, val_asst, cc.nm_cc from A_Ware_House wh, M_Loc l,M_Subloc sl,  M_Company_Costcenter cc where allocate = "+allocate+" and typ_proc = '"+typ_proc+"' and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and cc.id_cc=wh.id_cc"+tempSql;
					}
					else
					{
						sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,no_model,l.nm_loc, sl.nm_subl, val_asst,wh.id_cc from A_Ware_House wh, M_Loc l,M_Subloc sl where allocate = "+allocate+" and typ_proc = '"+typ_proc+"' and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl"+tempSql;
					}
				}
				
				else
				{
					t=2;
					sql="select id_wh_dyn, ds_pro,no_mfr,ds_asst, mfr,no_model,l.nm_loc, sl.nm_subl, val_asst from A_Ware_House wh, M_Loc l,M_Subloc sl where allocate = "+allocate+" and typ_proc = '"+typ_proc+"' and l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl "+tempSql;	
				}
				
		
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			%>
			<div class="card">
		<div class="card-body">
		
		<table id="proreport"
		class="table table-bordered table-hover proreport">
		<!-- <table border="1" width="100%" height="100%"> -->
		
			<thead>	
			<tr>
				<%
					if(t == 1)
					{%>
					<th colspan="11"><p style="font-size: 24px;color: Black;margin-left: 500px;">Procurement Type Report - <%=typ_proc%></p></th>
					<%} 
					else
					{%>				
					<th colspan="10" ><p  style="font-size: 24px;color: black;margin-left: 500px;">Procurement Type Report - <%=typ_proc%></p></th>
				<%} %>
				</tr>	
				<tr >
				
					<th><strong>S No. &nbsp;&nbsp;</strong></th>
					<th><strong>Asset ID &nbsp;&nbsp;</strong></th>
					<th><strong>Asset Name &nbsp;&nbsp;</strong></th>
					<th><strong>Serial No. &nbsp;&nbsp;</strong></th>
					<th><strong>Asset Description &nbsp;&nbsp;</strong></th>
					<th><strong>Manufacturer &nbsp;&nbsp;</strong></th>
					<!-- <th><strong>Model No. &nbsp;&nbsp;</strong></th> -->
					<th><strong>City &nbsp;&nbsp;</strong></th>
					<th><strong>Unit &nbsp;&nbsp;</strong></th>					
					<th><strong>Unit Cost in RS &nbsp;&nbsp;</strong></th>
					<%
					if(t == 1)
					{%>
					<th><strong>Cost Center &nbsp;&nbsp;</strong></th>
					<%} %>
					
				</tr>	</thead><tbody>
				
			<% 
				while(rs.next())
				{%>
					<tr>
					<td><%=++slNo%></td>
					<td><%=rs.getString(1)%></td>
					<td><%=rs.getString(2)%></td>
					<td><%=rs.getString(3)%></td>
					<td><%=rs.getString(4)%></td>
					<td><%=rs.getString(5)%></td>
					<td><%=rs.getString(6)%></td>
					<td><%=rs.getString(7)%></td>
					<td><%=rs.getString(8)%></td>
					<td><%=rs.getString(9)%></td>
				 <% 
					if(t == 1)
					{%>
					<td><%=rs.getString(10)%></td> 
					<%} %>
					
				</tr>
				<%}
			}
			catch(Exception e)
			{
				out.println("sql error in StoreAssetView.");
			}
		
		
		
		%></tbody>
		
	</table>
</div>
</div>
</body>
<%} %>
<!-- DataTables  Utlity -->
<script type="text/javascript" src="js/data.js"></script>
<script type="text/javascript">
getButtonsForListView('proreport','Procurement_Report_List');

</script>
</html>