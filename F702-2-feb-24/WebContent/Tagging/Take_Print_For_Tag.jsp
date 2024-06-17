<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tag Print details</title>
<script type="text/javascript">
function PrintMe(elem) {
	// document.all.print.style.visibility="hidden";
	 window.open();
		
}

</script>

</head>
<body>

		<%
		String s="";
			String sql="";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				ResultSet rs2=null;
				ResultSet rs3=null;
				PreparedStatement ps=null;
				PreparedStatement ps2=null;
				PreparedStatement ps3=null;
				Connection con=null;
				Statement stmt=null;
				String temp="";
				con=Common.GetConnection(request);
				String chk[] = request.getParameterValues("selectTagPrint");
				String nm_computer="";
				String nm_comp="";
				String phone="";
				  sql="select * from M_Company";	
				System.out.println("stuff is------"+ sql);
				ps=con.prepareStatement(sql);
				rs1=ps.executeQuery();
				if(rs1.next())
				{
					nm_comp=rs1.getString("nm_Com");
					phone=rs1.getString("phone");
				}  
				for(int i=0; i<chk.length;i++)
            	{
					
    				System.out.println("temp is------"+ temp);
    				sql="select  nm_product,UPPER(serial_no)  from S_I_Ware_House where   id_iwh="+chk[i]+"";
    				
    				System.out.println(sql);
    				ps=con.prepareStatement(sql);
    				rs=ps.executeQuery();
    				if(rs.next())
    				{
           
				%>
				
<div  style="page-break-after:always">

	<table  border="0" cellpadding ="0" cellspacing="0">
					<tr>	
						
						<td style="border-top-style: none;font-family:Verdana;font-size: 15px;">
						<table  border="0" cellpadding ="0" cellspacing="0">
						<%-- <tr><td colspan="2">&nbsp;&nbsp;<strong><%=rs.getString("nm_div")%></strong></td></tr> --%>
						 
                       
						
						   <tr><td>&nbsp;&nbsp;<strong><%=nm_comp %></td><td> </strong> </td></tr>
							<tr><td>&nbsp;&nbsp;<strong><%=rs.getString(1) %></td><td> </strong> </td></tr>
							
							 
						 
							<tr><td>&nbsp;&nbsp;<strong><%=rs.getString(2) %></td><td> </strong> </td></tr>
							
						 <tr><td>&nbsp;&nbsp;<strong><%=phone%></td><td> </strong> </td></tr>
						
						
						
						<tr><td colspan="2">&nbsp;&nbsp;<font face="'Free 3 of 9'" size="8">*<%=rs.getString(2) %>*</font></td></tr>
						
						</table>
						</td>
					</tr>	
		</table>
</div>
<br>
				<%} 
				
            	
            	}
			}
			catch(Exception e)
			{
				out.println("sql error in T_Tagg_Print." +e);
			}
		%>
		
	
	<!-- <input type="button" value="Print" name="print" onclick="PrintMe()" style="margin-left: 500px;font-family: Trebuchet MS; font-size: 11px; font-weight: bold; border-style: solid" MS",verdana; font-weight:bold;   font-size: 11px;    color: #444444;padding: 0px;">
 -->
</body>
</html>