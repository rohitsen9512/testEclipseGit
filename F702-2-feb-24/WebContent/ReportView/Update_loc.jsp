<%@ page language="java" import="java.sql.*,java.util.*,javax.naming.*,javax.sql.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        
        <script language ="javascript" src="script/menuG5FX.js"></script>
    </head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Discrepancy Report</title>

<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="CSS/Common.css" rel="stylesheet" type="text/css">
<link href="CSS/jquery-ui.css" rel="stylesheet" type="text/css">
<script type ="text/javascript" src="js/jquery1.10.js"></script>
<script type ="text/javascript" src="js/jquery-ui.js"></script>
<script type ="text/javascript" src="bootstrap/js/bootstrap.js"></script>
<script type ="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type ="text/javascript" src="BootBox/bootbox.min.js"></script>
 
<body>
<div id="PreviewDiscrepancyReport">
	 <form name = "frm">
		<%
		
			
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				
				String sql="";
				con=Common.GetConnection(request);
				
				String chk[]                =   request.getParameterValues("check");
	        	for(int i=0; i<chk.length;i++){
					String id_loc = request.getParameter("id_loc"+chk[i]);
					String id_subl = request.getParameter("id_subl"+chk[i]);
				    String id_flr = request.getParameter("id_flr"+chk[i]);

					//out.println("update hardware_register set cubicle_id = '"+cubicle+"',facility_id = '"+facility+"'  where ast_id = "+chk[i]+"");
					sql="update A_Ware_House set id_loc="+id_loc+",id_subl="+id_subl+",id_flr = "+id_flr+",allocate=1  where id_wh = "+chk[i]+"";
					out.println(sql);
					ps=con.prepareStatement(sql);
					ps.executeUpdate();
	%>                      <script language="javascript">
	                            alert("Updated Successfully.");
	                           window.location ="WebContent/Report/Discrepancy_report.jsp";
	                        </script>

		<%		}
					
			}catch(Exception e)
			{
				out.println("sql error in DiscrepancyReportview.");
			}
		%>
		
	</form>
</div>
</body>
</html>