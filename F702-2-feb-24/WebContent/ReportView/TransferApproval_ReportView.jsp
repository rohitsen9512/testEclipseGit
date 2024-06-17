<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*, java.text.SimpleDateFormat,java.util.*,java.text.DateFormat,com.Common.Common" %>
<%@page import="java.io.InputStream" %>
<%@page import="java.util.Properties" %>
<%@page import="dto.Common.UserAccessData" %>
<%@ page import="dto.Common.DtoCommon" %>
<%
    InputStream stream = UserAccessData.class.getClassLoader().getResourceAsStream("com/Resources/configuration.properties");
    Properties props = new Properties();
    props.load(stream);
    String name = props.getProperty("ProductVersion");
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>transfer Approval Report</title>
<!-- <link href="../CSS/watermark.css" rel="stylesheet" type="text/css">
<script type ="text/javascript" src="../common.js"></script>
<script type ="text/javascript" src="js/jquery1.10.js"></script> -->
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
<div id="PreviewGRNApprovePendingReport" class="watermarked" data-watermark="TRAIL..">
	
		<%
		int slNo    =   0;
		String id_grp = "";
		String id_sgrp = "";
		String sql="";
		String link="";
		String startdate = "" ,startdate1="",enddate="", enddate1="";
		String id_loc = "",id_div="";
		String id_sloc = "";
		String id_flr = "",id_building="";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		 DateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
	     DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");  
	
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "Transfer Request_Report.jsp.xls");
		 
		        }
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				
					 startdate = request.getParameter("startdate");
					 enddate = request.getParameter("enddate");
					 id_grp = request.getParameter("id_grp");
					 id_sgrp = request.getParameter("id_sgrp");
					 startdate1=startdate;
					 enddate1 = enddate;
					 
					 if(!startdate.equals(""))
						{
						 startdate = dateFormatNeeded.format(userDateFormat.parse(startdate));
						}
					 if(!startdate.equals(""))
						{
						 enddate = dateFormatNeeded.format(userDateFormat.parse(enddate));
						}
					 
					 id_loc = request.getParameter("id_loc");
					 id_sloc = request.getParameter("id_sloc");
					 id_flr = request.getParameter("id_flr"); 
					 id_grp = request.getParameter("id_grp");
					 id_sgrp = request.getParameter("id_sgrp");
					 id_building = request.getParameter("id_building");
					
					
					String tempSql = "";
					if(!id_div.equals("All"))
					{
						if(!id_loc.equals(""))
						{
							if(!id_sloc.equals(""))
							{
								if(!id_building.equals(""))
								{
									if(!id_flr.equals(""))
									{
										tempSql =" and ih.id_loc="+id_loc+" and ih.id_subl="+id_sloc+" and ih.id_flr = "+id_flr+"  and ih.id_building = "+id_building+" ";
									}
									else
									{
										tempSql =" and ih.id_loc="+id_loc+" and ih.id_subl="+id_sloc+"  and ih.id_building = "+id_building+" ";
									}
								}else{
									tempSql =" and ih.id_loc="+id_loc+" and ih.id_subl="+id_sloc+"  ";
								}
								//tempSql =" and ih.id_loc="+id_loc+" and ih.id_subl="+id_sloc+"";
							}
							else
							{
								tempSql =" and ih.id_loc="+id_loc+"  ";
							}
						}else{
						
						}
					}
				
				
				String tempSql1 ="",dt_recv="";
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
				String id_dept = (String)session.getAttribute("DeptId");
				DtoCommon dtoCommon = new DtoCommon();				
				/* sql= "select wh.id_wh_dyn, wh.ds_pro,(replace(convert(NVARCHAR, ih.dt_start_tran, 103), ' ', '-')) as dt_start_tran,(replace(convert(VARCHAR, ih.dt_recv, 103), ' ', '-')) as dt_recv, "+
						"cd.nm_div,l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr, cd2.nm_div as tran_div, l2.nm_loc as tran_loc, sl2.nm_subl as tran_subl,bul2.nm_building as tran_building,fl2.nm_flr as tran_flr "+
						"from A_ware_house wh,A_Iut_History ih , M_Loc l,M_Subloc sl, M_Floor fl,M_Building bul,M_Company_Division cd, M_Loc l2,M_Subloc sl2, M_Floor fl2,M_Building bul2,M_Company_Division cd2 "+
						"where l.id_loc=ih.id_loc and sl.id_sloc=ih.id_subl and bul.id_building=ih.id_building and  wh.id_wh=ih.id_wh and fl.id_flr=ih.id_flr and ih.id_div=cd.id_div and l2.id_loc=ih.id_loc_tran and  "+
						"sl2.id_sloc=ih.id_subl_tran and bul2.id_building=ih.id_building_tran and fl2.id_flr=ih.id_flr_tran and cd2.id_div=ih.id_div_tran and type_tran='Non Returnable' "+
						 " and ih.dt_start_tran >='"+startdate+"' and ih.dt_start_tran <='"+enddate+"'" +tempSql +tempSql1+"";
					 */		
					 if(UserType.equals("Super")){				 
						sql="select req_no_transfer as req_no,(replace(convert(NVARCHAR, req_dt_transfer, 103), ' ', '-')) as dt_req,wh.id_wh_dyn, wh.ds_pro,(replace(convert(NVARCHAR, ih.dt_start_tran, 103), ' ', '-')) as dt_start_tran,(replace(convert(VARCHAR, ih.dt_recv, 103), ' ', '-')) as dt_recv,l.nm_loc,  "+
								"  sl.nm_subl,bul.nm_building, fl.nm_flr,    l2.nm_loc as tran_loc, sl2.nm_subl as tran_subl,bul2.nm_building as tran_building,fl2.nm_flr as tran_flr   "+
								"   from A_ware_house wh,A_Iut_History ih , M_Loc l,M_Subloc sl,  M_Floor fl,M_Building bul, M_Loc l2,M_Subloc sl2, "+
								 "   M_Floor fl2,M_Building bul2 where l.id_loc=ih.id_loc and sl.id_sloc=ih.id_subl and bul.id_building=ih.id_building and  "+
								" 	 wh.id_wh=ih.id_wh and fl.id_flr=ih.id_flr 	 and l2.id_loc=ih.id_loc_tran and  sl2.id_sloc=ih.id_subl_tran and  "+
								" 	 bul2.id_building=ih.id_building_tran and fl2.id_flr=ih.id_flr_tran and  (iut_approval>=2 )"+
							 " " +tempSql +tempSql1+"";
					 }
					 else{
						 String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh",request);
						 sql="select req_no_transfer as req_no,(replace(convert(NVARCHAR, req_dt_transfer, 103), ' ', '-')) as dt_req,wh.id_wh_dyn, wh.ds_pro,(replace(convert(NVARCHAR, ih.dt_start_tran, 103), ' ', '-')) as dt_start_tran,(replace(convert(VARCHAR, ih.dt_recv, 103), ' ', '-')) as dt_recv,l.nm_loc,  "+
									"  sl.nm_subl,bul.nm_building, fl.nm_flr,    l2.nm_loc as tran_loc, sl2.nm_subl as tran_subl,bul2.nm_building as tran_building,fl2.nm_flr as tran_flr   "+
									"   from A_ware_house wh,A_Iut_History ih , M_Loc l,M_Subloc sl,  M_Floor fl,M_Building bul, M_Loc l2,M_Subloc sl2, "+
									 "   M_Floor fl2,M_Building bul2 where l.id_loc=ih.id_loc and sl.id_sloc=ih.id_subl and bul.id_building=ih.id_building and  "+
									" 	 wh.id_wh=ih.id_wh and fl.id_flr=ih.id_flr 	 and l2.id_loc=ih.id_loc_tran and  sl2.id_sloc=ih.id_subl_tran and  "+
									" 	 bul2.id_building=ih.id_building_tran and fl2.id_flr=ih.id_flr_tran  and  (iut_approval>=2)"+
								 " " +tempSql +tempSql1+" "+tempQuery2+"";
					 }
				System.out.println(sql);							
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			%>
			<div class="card"> 

		
			 <div class="card-body"> 
			
	<table id="TransferApprovalReport"
		class="table table-bordered table-hover TransferApprovalReport">
		<!-- <table style="border: 1px solid white;" border="1" width="140%" height="100%"> -->
		

				<!-- <tr >
					<td colspan="15" style="background-color: blue;" width="100%"><p  style="background-color: blue; font-size: 24px;color: white;margin-left: 500px;"><strong>Transfer Approval Report</strong></p></td>
				</tr> -->	
			<thead>	<tr>
				    <th><strong><center>S No.  </center></strong></th>
				     <th><strong><center>Request Number </center></strong></th>
					   <th><strong><center>Request Date </center></strong></th>
				   <th><strong><center>Asset ID  </center></strong></th>
					<th><strong><center>Asset Name </center></strong></th>
					
			<!-- 		<th><strong><center>Transfer Date </center></strong></th>
					<th><strong><center>Receive Date </center></strong></th>
			 -->		<th><strong><center>From City Location </center></strong></th>
					<th><strong><center>To City Location </center></strong></th>
					<th><strong><center>From City Sub Location </center></strong></th>
					<th><strong><center>To City Sub Location </center></strong></th>
					<th><strong><center>From Building </center></strong></th>
					<th><strong><center>To Building </center></strong></th>
					<th><strong><center>From Floor </center></strong></th>
					<th><strong><center>To Floor </center></strong></th>
					
					<!-- <th><strong><center>Download File </center></strong></th>
					<th><strong>Cost Center </strong></th>
					 -->
				</tr>	</thead>
				<tbody>
				
			<%
			String dt_end="";
				while(rs.next())
				{
					dt_recv = rs.getString("dt_recv");
					if(dt_recv == null)
						dt_recv = "---";
					
					dt_recv = rs.getString("dt_recv");
					if(dt_recv == null)
						dt_recv = "---";
					
							
				%>
					<tr>
					<td><center><%=++slNo%></center></td>
						<td><center>Request No-<%=rs.getString("req_no")%></center></td>
					<td><center><%=rs.getString("dt_req")%></center></td>
					<td><center><%=rs.getString("id_wh_dyn")%></center></td>
					<td><center><%=rs.getString("ds_pro")%></center></td>
				<%-- 	<td><center><%=rs.getString("dt_start_tran")%></center></td>
					<td><center><%=dt_recv%></center></td>
				 --%>	<td><center><%=rs.getString("nm_loc")%></center></td>
					<td><center><%=rs.getString("tran_loc")%></center></td>
					<td><center><%=rs.getString("nm_subl")%></center></td>
					<td><center><%=rs.getString("tran_subl")%></center></td>
					<td><center><%=rs.getString("nm_building")%></center></td>
					<td><center><%=rs.getString("tran_building")%></center></td>
					<td><center><%=rs.getString("nm_flr")%></center></td>
					<td><center><%=rs.getString("tran_flr")%></center></td>
					
					<%-- <%
					if((rs.getString("upload_asset")) != null) 
					{
						link = rs.getString("upload_asset");
					}
					else
					{
						link = " ";
					}
					%> --%>
					<%-- <td><center><a id="DownloadLinkId" href="downloadInvoiceDetails.jsp?fileName1=<%=link%>" download><%=link%></a></center></td> --%>
					
					<%-- <td><%=rs.getString(9)%></td>
					 --%>
					
					
				</tr>
				<%}
			}
			catch(Exception e)
			{
				//out.println("sql error in grn STORE." + e);
			}
		%>
		</tbody>
	</table>
</div>
</div>
</div>

</body>
<%
if(slNo !=0 && name.equals("UAT") || name.equals("TRAIL"))
{
%>
<!--< button style="margin-left: 400px;" name="UAT" type="button"  class="btn btn-primary" onclick="exportExcel()">Export To Excel</button> -->
<%
}
else if(slNo !=0 && name.equals("PRODUCT"))
{
%>
<%-- <a href="TransferApproval_ReportView.jsp?exportToExcel=YES&startdate=<%=startdate1%>&enddate=<%=enddate1%>&id_building=<%=id_building%>&id_flr=<%=id_flr%>&id_div=<%=id_div%>&id_loc=<%=id_loc%>&id_sloc=<%=id_sloc%>&id_grp=<%=id_grp%>&id_sgrp=<%=id_sgrp%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a>	 --%>

<%}
else
{%>
	<br>
		<!-- <b><center>No record(s) is available.</center></b> -->
<%}
	}%>
	
<%
if(name.equals("PRODUCT")){
%>
<!-- <script language="JavaScript">
		$('#PreviewGRNApprovePendingReport').removeClass('watermarked');
		</script> -->
<%
	}else{
		%>
		<!-- <script>        
		Array.from(document.querySelectorAll('.watermarked')).forEach(function(el) {
		        el.dataset.watermark = (el.dataset.watermark + ' ').repeat(10000);
		    });
		</script> -->
		<%
	}
%>	
<!-- DataTables  Utlity -->
<script type="text/javascript" src="js/data.js"></script>
<script type="text/javascript">
getButtonsForListView('TransferApprovalReport','Transfer_Approval_Report_List');

</script>
</html>