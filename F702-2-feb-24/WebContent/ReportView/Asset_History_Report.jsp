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
<title>Asset History Report</title>
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
		                    + "Asset_History_Reportt.jsp.xls");
		 
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
					 id_grp = request.getParameter("id_grp");
					 id_sgrp = request.getParameter("id_sgrp");
					 id_model = request.getParameter("id_model");
					 startdate1=startdate;
					 
					 if(!startdate.equals(""))
						{
						 startdate = dateFormatNeeded.format(userDateFormat.parse(startdate));
						}
				 
				String tempSql = "";
				
				String tempSql1 ="";
				if(!id_grp.equals("All"))
				{
					if(!id_sgrp.equals(""))
					{
						tempSql1 =" and wh.id_grp="+id_grp+" and wh.id_sgrp="+id_sgrp+"";
						if(!id_model.equals(""))
						{
							tempSql1 =" and wh.id_grp="+id_grp+" and wh.id_sgrp="+id_sgrp+" and wh.id_model="+id_model+" ";
						}
						else
						{
							tempSql1 =" and wh.id_grp="+id_grp+" and wh.id_sgrp="+id_sgrp+" ";
						}
					}
					else
					{
						tempSql1 =" and wh.id_grp="+id_grp+"";
					}
				}
				String UserType = (String)session.getAttribute("UserType");
				String id_dept = (String)session.getAttribute("DeptId");
				DtoCommon dtoCommon = new DtoCommon();
				 if(UserType.equals("Super"))
				 {
					 sql="select wh.id_wh_dyn,m.nm_model,e.nm_emp, (replace(convert(VARCHAR, ih.dt_start, 106), ' ', '-')) as dtalloc, l.nm_loc, sl.nm_subl,bul.nm_building,(replace(convert(VARCHAR, ih.dt_de_allocate, 106), ' ', '-')) as dtdealloc from A_ware_house wh, M_Asset_Div ad,M_Subasset_Div sd,M_Model m,A_Iut_History ih , M_Emp_user e, M_Loc l,M_Subloc sl,M_Building bul,M_Floor f where l.id_loc=ih.id_loc and  sl.id_sloc=ih.id_subl and bul.id_building=wh.id_building and e.id_emp_user=ih.to_assign and wh.id_wh=ih.id_wh and wh.id_grp=ad.id_assetdiv and wh.id_sgrp=sd.id_s_assetdiv and wh.id_model=m.id_model and f.id_flr=ih.id_flr and (ih.dt_start >='"+startdate+"' or ih.dt_de_allocate >='"+startdate+"') " +tempSql1+"";
						
				 }
				 else
				 {
					 String tempQuery2 = dtoCommon.MakeTempQuery(UserType ,"wh","wh",request);
					 sql="select wh.id_wh_dyn,m.nm_model,e.nm_emp, (replace(convert(VARCHAR, ih.dt_start, 106), ' ', '-')) as dtalloc, l.nm_loc, sl.nm_subl,bul.nm_building,(replace(convert(VARCHAR, ih.dt_de_allocate, 106), ' ', '-')) as dtdealloc from A_ware_house wh, M_Asset_Div ad,M_Subasset_Div sd,M_Model m,A_Iut_History ih , M_Emp_user e, M_Loc l,M_Subloc sl,M_Building bul,M_Floor f where l.id_loc=ih.id_loc and  sl.id_sloc=ih.id_subl and bul.id_building=wh.id_building and e.id_emp_user=ih.to_assign and wh.id_wh=ih.id_wh and wh.id_grp=ad.id_assetdiv and wh.id_sgrp=sd.id_s_assetdiv and wh.id_model=m.id_model and f.id_flr=ih.id_flr  and (ih.dt_start >='"+startdate+"' or ih.dt_de_allocate >='"+startdate+"') " +tempSql1+" "+tempQuery2+"";
						 
				 }
					 /* sql= "select wh.id_wh_dyn, e.nm_emp, wh.ds_pro,(replace(convert(VARCHAR, ih.dt_start, 106), ' ', '-')) , case when ih.dt_start_tran='' then (replace(convert(VARCHAR, ih.dt_de_allocate, 106), ' ', '-')) else "+
							"(replace(convert(VARCHAR, ih.dt_start_tran, 106), ' ', '-')) END as test, l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr,upload_asset from A_ware_house wh,"+
							"A_Iut_History ih , M_Emp_user e, M_Loc l,M_Subloc sl, M_Floor fl,M_Building bul where l.id_loc=ih.id_loc "+
							" and sl.id_sloc=ih.id_subl and bul.id_building=wh.id_building and e.id_emp_user=ih.to_assign and wh.id_wh=ih.id_wh and fl.id_flr=ih.id_flr "+
							" and ih.dt_start >='"+startdate+"' " +tempSql1+""; 
							
							
				 if(UserType.equals("DEPT"))	
					sql= "select wh.id_wh_dyn, e.nm_emp, wh.ds_pro,(replace(convert(VARCHAR, ih.dt_start, 106), ' ', '-')) , case when ih.dt_start_tran='' then (replace(convert(VARCHAR, ih.dt_de_allocate, 106), ' ', '-')) else "+
							"(replace(convert(VARCHAR, ih.dt_start_tran, 106), ' ', '-')) END as test, l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr,upload_asset from A_ware_house wh,"+
							"A_Iut_History ih , M_Emp_user e, M_Loc l,M_Subloc sl, M_Floor fl,M_Building bul where l.id_loc=ih.id_loc "+
							" and sl.id_sloc=ih.id_subl and bul.id_building=wh.id_building and e.id_emp_user=ih.to_assign and wh.id_wh=ih.id_wh and fl.id_flr=ih.id_flr and wh.id_dept="+id_dept+" "+
							" and ih.dt_start >='"+startdate+"' " +tempSql1+"";
							
							
							
							if(UserType.equals("IT"))
							/* sql= "select wh.id_wh_dyn, e.nm_emp, wh.ds_pro,(replace(convert(VARCHAR, ih.dt_start, 106), ' ', '-')) , case when ih.dt_start_tran='' then (replace(convert(VARCHAR, ih.dt_de_allocate, 106), ' ', '-')) else "+
									"(replace(convert(VARCHAR, ih.dt_start_tran, 106), ' ', '-')) END as test, l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr,upload_asset from A_ware_house wh,"+
									"A_Iut_History ih , M_Emp_user e, M_Loc l,M_Subloc sl, M_Floor fl,M_Building bul where l.id_loc=ih.id_loc "+
									" and sl.id_sloc=ih.id_subl and bul.id_building=wh.id_building and e.id_emp_user=ih.to_assign and wh.id_wh=ih.id_wh and fl.id_flr=ih.id_flr and st_config='Yes'  "+
									" and ih.dt_start >='"+startdate+"' " +tempSql1+""; */
								/*	sql= "select wh.id_wh_dyn, e.nm_emp, wh.ds_pro,(replace(convert(VARCHAR, ih.dt_start, 106), ' ', '-')) , case when ih.dt_start_tran='' then (replace(convert(VARCHAR, ih.dt_de_allocate, 106), ' ', '-')) else "+
											"(replace(convert(VARCHAR, ih.dt_start_tran, 106), ' ', '-')) END as test, l.nm_loc, sl.nm_subl,bul.nm_building,upload_asset from A_ware_house wh,"+
											"A_Iut_History ih , M_Emp_user e, M_Loc l,M_Subloc sl,M_Building bul where l.id_loc=ih.id_loc "+
											" and sl.id_sloc=ih.id_subl and bul.id_building=wh.id_building and e.id_emp_user=ih.to_assign and wh.id_wh=ih.id_wh  "+
											" and ih.dt_start >='"+startdate+"' " +tempSql1+"";*/
									
								/*	if(UserType.equals("RHead"))
										sql= "select wh.id_wh_dyn, e.nm_emp, wh.ds_pro,(replace(convert(VARCHAR, ih.dt_start, 106), ' ', '-')) , case when ih.dt_start_tran='' then (replace(convert(VARCHAR, ih.dt_de_allocate, 106), ' ', '-')) else "+
												"(replace(convert(VARCHAR, ih.dt_start_tran, 106), ' ', '-')) END as test, l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr,upload_asset from A_ware_house wh,"+
												"A_Iut_History ih , M_Emp_user e, M_Loc l,M_Subloc sl, M_Floor fl,M_Building bul where l.id_loc=ih.id_loc "+
												" and sl.id_sloc=ih.id_subl and bul.id_building=wh.id_building and e.id_emp_user=ih.to_assign and wh.id_wh=ih.id_wh and fl.id_flr=ih.id_flr and st_config='Yes'  "+
												" and ih.dt_start >='"+startdate+"' " +tempSql1+"";							
						
									if(UserType.equals("NIT"))
										sql= "select wh.id_wh_dyn, e.nm_emp, wh.ds_pro,(replace(convert(VARCHAR, ih.dt_start, 106), ' ', '-')) , case when ih.dt_start_tran='' then (replace(convert(VARCHAR, ih.dt_de_allocate, 106), ' ', '-')) else "+
												"(replace(convert(VARCHAR, ih.dt_start_tran, 106), ' ', '-')) END as test, l.nm_loc, sl.nm_subl,bul.nm_building, fl.nm_flr,upload_asset from A_ware_house wh,"+
												"A_Iut_History ih , M_Emp_user e,M_Loc l,M_Subloc sl, M_Floor fl,M_Building bul where l.id_loc=ih.id_loc "+
												" and sl.id_sloc=ih.id_subl and bul.id_building=wh.id_building and e.id_emp_user=ih.to_assign and wh.id_wh=ih.id_wh and fl.id_flr=ih.id_flr  "+
												" and typ_asst = '"+UserType+"' and ih.dt_start >='"+startdate+"' " +tempSql1+"";
									
				sql= "select wh.id_wh_dyn, e.nm_emp, wh.ds_pro,(replace(convert(VARCHAR, ih.dt_start, 106), ' ', '-')) , case when ih.dt_start_tran='' then (replace(convert(VARCHAR, ih.dt_de_allocate, 106), ' ', '-')) else "+
						"(replace(convert(VARCHAR, ih.dt_start_tran, 106), ' ', '-')) END as test, l.nm_loc, sl.nm_subl,bul.nm_building,(replace(convert(VARCHAR, wh.dt_alloc, 106), ' ', '-')),upload_asset from A_ware_house wh,"+
						"A_Iut_History ih , M_Emp_user e, M_Loc l,M_Subloc sl,M_Building bul where l.id_loc=ih.id_loc "+
						" and sl.id_sloc=ih.id_subl and bul.id_building=wh.id_building and e.id_emp_user=ih.to_assign and wh.id_wh=ih.id_wh  "+
							" and ih.dt_start >='"+startdate+"' " +tempSql1+"";*/
													
			 								
				//sql="select wh.id_wh_dyn,m.nm_model,e.nm_emp, (replace(convert(VARCHAR, ih.dt_start, 106), ' ', '-')) as dtalloc, l.nm_loc, sl.nm_subl,bul.nm_building,(replace(convert(VARCHAR, ih.dt_de_allocate, 106), ' ', '-')) as dtdealloc from A_ware_house wh, M_Asset_Div ad,M_Subasset_Div sd,M_Model m,A_Iut_History ih , M_Emp_user e, M_Loc l,M_Subloc sl,M_Building bul,M_Floor f where l.id_loc=ih.id_loc and  sl.id_sloc=ih.id_subl and bul.id_building=wh.id_building and e.id_emp_user=ih.to_assign and wh.id_wh=ih.id_wh and wh.id_grp=ad.id_assetdiv and wh.id_sgrp=sd.id_s_assetdiv and wh.id_model=m.id_model and f.id_flr=ih.id_flr and ih.dt_start >='"+startdate+"' " +tempSql1+"";
				//out.println(sql);	
				System.out.println(sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				
			%>
		
			<div class="card">
			<table id="AssetHistoryReport"
		class="table table-bordered table-hover AssetHistoryReport">
		
				<thead><tr>
				    <th><strong><center>S No.</center></strong></th>
				    <th><strong><center>Asset ID </center></strong></th>
					<th><strong><center>Employee Name </center></strong></th>
					<th><strong><center>Asset Name </center></strong></th>
					<th><strong><center>Allocate Date </center></strong></th>
					<!-- <th><strong><center>Start Date </center></strong></th>
					 --><th><strong><center>De-Allocate Date </center></strong></th>
					 <th><strong><center>Location </center></strong></th>
					<th><strong><center>Sub Location </center></strong></th>
					<th><strong><center>Building </center></strong></th>
				<!-- <th><strong><center>Floor </center></strong></th>  -->
					<!-- <th><strong><center>Download File </center></strong></th> -->
					<!-- <th><strong>Cost Center </strong></th>
					 -->
				</tr>	</thead><tbody>
				
			<%
			String dt_end="",dt_allocate="",dt_de_allocate="";
			while(rs.next())
			{
				dt_de_allocate = rs.getString("dtdealloc");
				if(dt_de_allocate == null)
					dt_de_allocate = "---";
				dt_allocate = rs.getString("dtalloc");
				if(dt_allocate.equals("01-Jan-1900"))
					dt_allocate="---";
				%>
					<tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString(1)%></center></td>
					<td><center><%=rs.getString(3)%></center></td>
					<td><center><%=rs.getString(2)%></center></td>
					<td><center><%=dt_allocate%></center></td>
					<%-- <td><center><%=rs.getString(4)%></center></td>
					 --%><td><center><%=dt_de_allocate%></center></td>
					
					<td><center><%=rs.getString(5)%></center></td>
					<td><center><%=rs.getString(6)%></center></td>
					<td><center><%=rs.getString(7)%></center></td> 
					<%-- <td><center><%=rs.getString(9)%></center></td> --%>
					
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
</body>
<%
if(slNo !=0 && name.equals("UAT") || name.equals("TRAIL"))
{
%>
<!-- <button style="margin-left: 400px;" name="UAT" type="button"  class="btn btn-primary" onclick="exportExcel()">Export To Excel</button> -->
<%
}
else if(slNo !=0 && name.equals("PRODUCT"))
{
%>
<%-- <a href="Asset_History_Report.jsp?exportToExcel=YES&startdate=<%=startdate1%>&id_grp=<%=id_grp%>&id_sgrp=<%=id_sgrp%>">
			<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 400px;">Export To Excel</button>
			</a> --%>	

<%}
else
{%>
	<!-- <br>
		<b><center>No record(s) is available.</center></b> -->
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
getButtonsForListView('AssetHistoryReport','Asset_History_Report_List');

</script>
</html>