<%@ page language="java" import="java.sql.*,java.util.*,javax.naming.*,javax.sql.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        
        <script language ="javascript" src="script/menuG5FX.js"></script>
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
        
        
    </head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Discrepancy Report</title>
<style type="text/css">
.headerTR{
background-color: blue;
}
.headerTD{
background-color: lavender;
}
</style>

<!-- <script type ="text/javascript" src="js/jquery1.10.js"> -->
<script type="text/javascript">
    window.location.reload();
    
}
</script>

 <script language="javascript">
 $(function() {
	 
		DisplaySubDropDownData('id_subl','floorForDiscrepancyReportview','M_Floor');

	});
	function fn()
	{
		document.frm.action='discrepancyReportExpert.jsp';
		document.frm.target='_new';
		document.frm.submit();
	}
	

	
	function UpdateFloorForMisplacedAsset(){
		var t=0;
		$('.checkForRegularize').each(function(){
			
			
			$('input[name="id_flr_Update'+$(this).val()+'"]').removeClass('error');
			if(this.checked)
				{	
				
					var val = $('select[name="id_flr_Update'+$(this).val()+'"]').val();
					if(val == '')
						{
						t=0;
							alert("Please Select the floor.");
							$('select[name="id_flr_Update'+$(this).val()+'"]').addClass('error');
							$('select[name="id_flr_Update'+$(this).val()+'"]').focus();
							exit(0);
						}
				
					t=1;
					
				}
			
		});
	
		if(t == 0)
		{
			alert("Please select atleast one asset.");
		}
		else
		{
			var x = $('#submitRegularize').serialize();
			
			$.post('../T_Discrepancy',x,function (r)
					{
				
						if(r.data == 1)
							{
							alert('Updated Successfully.');
							}
						else
							{
						alert('Somthing went wrong. Please try again.');
							}
						
					},'json');
		}
	}
	
	</script>
	
	
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
<div id="PreviewDiscrepancyReport">
	
		<%
		String update = request.getParameter("update");
		String exportToExcel = request.getParameter("exportToExcel");
        
        if (exportToExcel != null
                && exportToExcel.toString().equalsIgnoreCase("YES")) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "inline; filename="
                    + "Discripency_ReportView.xls");
 
        }
        String id_loc ="";
		String id_subl = "";
		String id_flr = "";
		String yr = "";
		String period = "";	
        
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				Statement stmt1=null;
				con=Common.GetConnection(request);
				
				 id_loc = request.getParameter("id_loc");
				 id_subl = request.getParameter("id_subl");
				 id_flr = request.getParameter("id_flr");
				 yr = request.getParameter("yr");
				 period = request.getParameter("period");
				boolean flag        = false;
				boolean flag1        = false;
				String sql="";				
				
				//sql="select orig.id_wh_dyn,allocate,orig.ds_pro,orig.ds_asst,orig.nm_loc,mis.nm_loc ,orig.nm_subl,mis.nm_subl,orig.nm_flr,mis.nm_flr, orig.id_flr,orig.id_wh from (select id_wh_dyn,allocate,ds_pro,ds_asst,isnull(nm_loc,'Store') as nm_loc,isnull(nm_subl,'Store') as nm_subl,isnull(nm_flr,'Store') as nm_flr,hr.id_loc,hr.id_subl,hr.id_flr,hr.id_wh from A_Ware_House hr left join M_Loc lm on hr.id_loc = lm.id_loc left join M_Subloc slm on hr.id_subl = slm.id_sloc left join M_Floor fm on hr.id_flr = fm.id_flr  where acc_asst ='0') orig right outer join (select id_wh_dyn,nm_loc,nm_subl,nm_flr,id_loc,id_sloc,yr,period,fm.id_flr from T_Asset_Verification av left join M_Loc lm on av.loc = lm.id_loc left join M_Subloc slm on av.subloc = slm.id_sloc left join M_Floor fm on av.id_flr = fm.id_flr ) mis on Orig.id_wh_dyn = mis.id_wh_dyn where mis.period = '"+period+"' and yr = "+yr+" and mis.loc = "+id_loc+" and mis.subloc = "+id_subl+" and mis.id_flr="+id_flr+" and ((orig.id_loc <> mis.loc) or (orig.id_subl <> mis.subloc) or (orig.id_flr <> mis.id_flr))";
				sql="select orig.id_wh_dyn,allocate,orig.ds_pro,orig.ds_asst,orig.nm_loc,mis.nm_loc , "+
						" orig.nm_subl,mis.nm_subl,orig.nm_flr,mis.nm_flr, orig.id_flr,orig.id_wh, orig.id_loc as oloc, mis.id_loc as mloc, orig.id_subl as osloc, mis.id_sloc as msloc, orig.id_flr as oflr,mis.id_flr as mflr from "+ 
						" (select id_wh_dyn,allocate,ds_pro,ds_asst,isnull(nm_loc,'Store') as nm_loc,isnull(nm_subl,'Store') as "+ 
						" nm_subl,isnull(nm_flr,'Store') as nm_flr,hr.id_loc,hr.id_subl,hr.id_flr,hr.id_wh from A_Ware_House hr "+
						" left join M_Loc lm on hr.id_loc = lm.id_loc left join M_Subloc slm on hr.id_subl = slm.id_sloc "+
						" left join M_Floor fm on hr.id_flr = fm.id_flr where acc_asst ='0') orig right outer join "+
						" (select id_wh_dyn,nm_loc,nm_subl,nm_flr,av.id_loc,av.id_sloc,yr,period,fm.id_flr from T_Asset_Verification av "+ 
						" left join M_Loc lm on av.id_loc = lm.id_loc left join M_Subloc slm on av.id_sloc = slm.id_sloc left join "+
						" M_Floor fm on av.id_flr = fm.id_flr ) mis on Orig.id_wh_dyn = mis.id_wh_dyn where mis.period = '"+period+"' "+
						" and yr = "+yr+" and mis.id_loc = "+id_loc+" and mis.id_sloc = "+id_subl+" and mis.id_flr="+id_flr+" and ((orig.id_loc <> mis.id_loc) or "+ 
						" (orig.id_subl <> mis.id_sloc) or (orig.id_flr <> mis.id_flr))";
				//out.print(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			%>
			<div class="card-body"> 
		<form action="" name="submitRegularize" id="submitRegularize">	
	<table id="DiscrepencyReport1"
		class="table table-bordered table-hover DiscrepencyReport1">
			
		<!-- <table style="border: 1px solid white;" border="1" width="100%" height="100%"> -->
		
			<thead>	<tr>
					<th colspan="11"><center><p class="tableHeaderContent" style="margin: 0px;font-size: 24px;font-weight: 700;color: grey;">Misplaced Asset Details</p></center></th>
				</tr>	
				
				<tr>
					<th><center><b>S No.</b></center></th>
					<th><center><b>Asset ID</b></center></th>
					<th><center><b>Asset Name</b></center></th>
					<th><center><b>Asset Desc</b></center></th>
					<th><center><b>Assigned Location</b></center></th>
					<th><center><b>Current Location</b></center></th>
					<th><center><b>Assigned Sub Location</b></center></th>
					<th><center><b>Current Sub Location</b></center></th>
					<th><center><b>Assigned Floor</b></center></th>
					<th><center><b>Current Floor</b></center></th>
					 <th><center><b>Action</b></center></th> 
					
				</tr></thead><tbody>
<%
				while(rs.next()){
					flag = true;
%>
				<tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString(1)%></center></td>
					<td><center><%=rs.getString(3)%></center></td>
					<td><center><%=rs.getString(4)%></center></td>
					<td><center><%=rs.getString(5)%></center></td>
					<td><center><%=rs.getString(6)%></center></td>
					<td><center><%=rs.getString(7)%></center></td>
					<td><center><%=rs.getString(8)%></center></td>
					<td><center><%=rs.getString(9)%></center></td>
					<td><center><%=rs.getString(10)%></center></td>
					
					 <input type="hidden" name="id_loc<%=rs.getString(12)%>" value="<%=id_loc%>">
					<input type="hidden" name="id_subl<%=rs.getString(12)%>" value="<%=id_subl%>">
					<input type="hidden" name="id_flr<%=rs.getString(12)%>" value="<%=id_flr%>">
					<input type="hidden" name="id_flrs" value="<%=rs.getString(12)%>"> 
					
					<%-- <input type="hidden" name="o_id_loc" value="<%=rs.getString(13)%>">
					<input type="hidden" name="o_id_subl" value="<%=rs.getString(15)%>">
					<input type="hidden" name="o_id_flr" value="<%=rs.getString(17)%>">
					<input type="hidden" name="m_id_loc" value="<%=rs.getString(14)%>">
					<input type="hidden" name="m_id_subl" value="<%=rs.getString(16)%>">
					<input type="hidden" name="m_id_flr" value="<%=rs.getString(18)%>">
					<input type="hidden" name="id_wh" value="<%=rs.getString(12)%>"> --%>
					 
					<td>
						<center>
							<a href="Discrepancy_ReportView.jsp?update=yes&id_wh=<%=rs.getString(12)%>&m_id_loc=<%=rs.getString(14)%>&m_id_subl=<%=rs.getString(16)%>&m_id_flr=<%=rs.getString(18)%>&id_loc=<%=id_loc%>&id_subl=<%=id_subl%>&yr=<%=yr%>&period=<%=period%>&id_flr=<%=id_flr%>">
							<input type="button" value="Update" width="100px" onclick="refreshPage()">
							</a>
						</center>
					</td> 
					
				</tr>
				
<%
			}
				if(flag==false){
%>
					<!-- <tr>
						<td  colspan="11"><center><b><font color="red">No Misplaced Assets</font></b></center></td>
				   </tr> -->
				   
			</tbody>
				   </table>
				
				</form>
				</div>
<%
			}
				
				
				
				else{
%>
				
<%			}
			
			

			String sql1="select distinct hr.id_wh_dyn,hr.ds_pro, hr.ds_asst, "+
					" nm_loc,nm_subl,nm_emp,b.nm_building,f.nm_flr,substring(hr.id_wh_dyn,0,patindex('%-%', hr.id_wh_dyn)), "+
					" convert(int,substring(hr.id_wh_dyn,patindex('%-%', hr.id_wh_dyn)+1,100)) "+
					" from A_Ware_House hr,M_Loc lm, "+
					" M_Subloc slm,M_Building b,M_floor f,M_Emp_User em,T_Asset_Verification av "+
					" where  hr.id_loc = lm.id_loc and hr.id_subl = slm.id_sloc and hr.id_building=b.id_building and hr.id_flr=f.id_flr and "+
					" hr.to_assign = em.id_emp_user and acc_asst = '0' and hr.allocate = 1 "+
					" and hr.id_loc = "+id_loc+" and hr.id_subl = "+id_subl+" and hr.id_flr="+id_flr+" and av.yr = "+yr+" and av.period = '"+period+"' "+
					" and hr.id_wh_dyn not in (select id_wh_dyn from  "+
					" T_Asset_Verification where yr="+yr+" and period = '"+period+"' ) and status_repair <> 1 and st_trvl not in(1,2) "+
					" order by substring(hr.id_wh_dyn,0,patindex('%-%', hr.id_wh_dyn)),"+
					" convert(int,substring(hr.id_wh_dyn,patindex('%-%', hr.id_wh_dyn)+1,100))";
			
					//out.print("sql1="+sql1);
					ps=con.prepareStatement(sql1);
			rs=ps.executeQuery();
			 slNo    =   0;
%>
		<br><br>
		<div class="card-body"> 
			
	<table id="DiscrepencyReport2"
		class="table table-bordered table-hover DiscrepencyReport2">
		<!-- <table class="table1" style="border: 1px solid white;" border="1"  width="100%"> -->
			<thead><tr>
					<th colspan="11" class="headerTR"><center><p class="tableHeaderContent" style="margin: 0px;font-size: 24px;font-weight: 700;color: white;">Missing Asset Details</p></center></th>
			</tr>	
			<tr>
				<th><center><b>S No.</b></center></th>
				<th><center><b>Asset ID</b></center></th>
				<th><center><b>Asset Name</b></center></th>
				<th><center><b>AssetDesc</b></center></th>
				<th><center><b>Location</b></center></th>
				<th><center><b>Sub Location</b></center></th>
				<th><center><b>Building</b></center></th>
				<th><center><b>Floor</b></center></th>
				<th><center><b>Assigned To</b></center></th>
			</tr></thead><tbody>
<%
			while(rs.next()){
				flag1 = true;
%>
				<tr>
					<td><center> <%=++slNo%> </center></td>
					<td><center>  <%=rs.getString(1)%></center></td>
					<td><center>  <%=rs.getString(2)%></center></td>
					<td><center>  <%=rs.getString(3)%></center></td>
					<td><center>  <%=rs.getString(4)%></center></td>
					<td><center>  <%=rs.getString(5)%></center></td>
					<td><center>  <%=rs.getString(7)%></center></td>
					<td><center>  <%=rs.getString(8)%></center></td>
					<td><center>  <%=rs.getString(6)%></center></td>
				</tr>
	<%
				}
			if(flag1==false){
%>
				<!-- <tr>
					<td  colspan="7"><center><b><font color="red">No Missing Assets</font></b></center></td>
				</tr> -->
				
<%
			}else{
%>
</tbody>
				</table>
			
			<BR>
<%
			}
			if(flag !=false || flag1!=false){
%>
				<input type = "hidden" name = "id_loc"    value = "<%=id_loc%>">
				<input type = "hidden" name = "id_subl" value = "<%=id_subl%>">
				<input type = "hidden" name = "yr"        value = "<%=yr%>">
				<input type = "hidden" name = "period"      value = "<%=period%>">
				<input type = "hidden" name = "id_flr"      value = "<%=id_flr%>">
<%

			}

			
			}catch(Exception e)
			{
				out.println("sql error in DiscrepancyReportview.");
			}
		%>
		

</div>
</body>

			
			<%
	        if (exportToExcel == null) {
	    %>
	    <br>
				<%-- <a href="Discrepancy_ReportView.jsp?exportToExcel=YES&id_loc=<%=id_loc%>&id_subl=<%=id_subl%>&yr=<%=yr%>&period=<%=period%>&id_flr=<%=id_flr%>">
				<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 600px;">Export To Excel</button>
				</a> --%>		
				
	    
	    <%
	        }
	    %>
<%} %>

			<%
				Connection con=null;
				String update=request.getParameter("update");
				if(update != null && update.toString().equalsIgnoreCase("yes")){
					String id_wh="",m_id_loc="",m_id_sloc="",m_id_flr="";
					id_wh = request.getParameter("id_wh");
					m_id_loc = request.getParameter("m_id_loc");
					m_id_sloc = request.getParameter("m_id_subl");
					m_id_flr = request.getParameter("m_id_flr");
					String query = "update A_Ware_House set id_loc="+m_id_loc+", id_subl="+m_id_sloc+", id_flr="+m_id_flr+" where id_wh="+id_wh;
					con=Common.GetConnection(request);
					PreparedStatement ps1=con.prepareStatement(query);
					int i = ps1.executeUpdate();
				}
			%>
<!-- DataTables  Utlity -->
<script type="text/javascript" src="js/data.js"></script>
<script type="text/javascript">
getButtonsForListView('DiscrepencyReport1','Discrepency_Report1_List');
getButtonsForListView('DiscrepencyReport2','Discrepency_Report2_List');
</script>
</html>