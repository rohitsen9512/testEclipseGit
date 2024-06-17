<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,com.Common.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Department Report</title>
<style type="text/css">
.headerTR{
background-color: blue;
}
.headerTD{
background-color: lavender;
}
</style>
<script type ="text/javascript" src="js/jquery1.10.js"></script></head>
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
<div id="PreviewEmployeeAssetReport">
	
		<%
		
		
		        String exportToExcel = request.getParameter("exportToExcel");
		        
		        if (exportToExcel != null
		                && exportToExcel.toString().equalsIgnoreCase("YES")) {
		            response.setContentType("application/vnd.ms-excel");
		            response.setHeader("Content-Disposition", "inline; filename="
		                    + "department_Report.xls");
		 
		        }
		    
		
		String id_dept ="";
			String sql="";
			String id_grp = "";
			String id_sgrp = "";
			try
			{
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement ps=null;
				Connection con=null;
				Statement stmt=null;
				con=Common.GetConnection(request);
				id_dept = request.getParameter("id_dept");
				 id_grp = request.getParameter("id_grp");
				 id_sgrp = request.getParameter("id_sgrp");
				
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
						String id_dept1 = (String)session.getAttribute("DeptId");
						if(UserType.equals("SUPER")){
					if(id_dept.equals("all"))	
						sql="select id_wh_dyn ,ds_pro,mfr,no_mfr, l.nm_loc,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp "+
								"from A_Ware_House wh,  M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Model modl where  l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and modl.id_model=wh.id_model  and fl.id_flr=wh.id_flr and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign " +tempSql1+" ";
					else
						/* sql="select id_wh_dyn ,ds_pro,mfr,no_mfr, l.nm_loc,nm_model,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp "+
								"from A_Ware_House wh,  M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Model modl LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user  where l.id_loc=wh.id_loc and sl.id_sloc=wh.id_subl and modl.id_model=wh.id_model and  fl.id_flr=wh.id_flr and d.id_dept=wh.id_dept and d.id_dept="+id_dept+" " +tempSql1+" ";
						} */
						
						sql=" select id_wh_dyn ,ds_pro,mfr,no_mfr, loc.nm_loc,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,sloc.nm_subl,flr.nm_flr, dep.nm_dept,nm_computer,emp.nm_emp "+
								"  from  A_Ware_House wh "+
								"  LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+ 
								" LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc"+
								" LEFT JOIN M_Model model on wh.id_model=model.id_model "+
								" LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
								" LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
								" LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
								"  where loc.id_loc=wh.id_loc and sloc.id_sloc=wh.id_subl "+ 
								"  and dep.id_dept=wh.id_dept and dep.id_dept="+id_dept+" " +tempSql1+" ";
			}
						
						
						if(UserType.equals("DEPT")){
							if(id_dept.equals("all"))	
								/* sql="select id_wh_dyn ,ds_pro,mfr,no_mfr, l.nm_loc,nm_model,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp "+
										"from A_Ware_House wh,  M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Model modl where allocate=1 and l.id_loc=wh.id_loc and modl.id_model=wh.id_model and sl.id_sloc=wh.id_subl  and fl.id_flr=wh.id_flr and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign and wh.id_dept="+id_dept1+" " +tempSql1+""; */
							
										
										sql=" select id_wh_dyn ,ds_pro,mfr,no_mfr, loc.nm_loc,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,sloc.nm_subl,flr.nm_flr, dep.nm_dept,nm_computer,emp.nm_emp "+
												"  from  A_Ware_House wh "+
												"  LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+ 
												" LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc"+
												" LEFT JOIN M_Model model on wh.id_model=model.id_model "+
												" LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
												" LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
												" LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
												"  where loc.id_loc=wh.id_loc and sloc.id_sloc=wh.id_subl "+ 
												"  and wh.id_dept="+id_dept1+" " +tempSql1+"";
										
										else
											
											
											sql=" select id_wh_dyn ,ds_pro,mfr,no_mfr, loc.nm_loc,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,sloc.nm_subl,flr.nm_flr, dep.nm_dept,nm_computer,emp.nm_emp "+
													"  from  A_Ware_House wh "+
													"  LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+ 
													" LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc"+
													" LEFT JOIN M_Model model on wh.id_model=model.id_model "+
													" LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
													" LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
													" LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
													"  where loc.id_loc=wh.id_loc and sloc.id_sloc=wh.id_subl "+ 
													"  and wh.id_dept="+id_dept1+"  and dep.id_dept="+id_dept+" " +tempSql1+" ";
											
								/* sql="select id_wh_dyn ,ds_pro,mfr,no_mfr, l.nm_loc,nm_model,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp "+
										"from A_Ware_House wh,  M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Model modl where allocate = 1 and l.id_loc=wh.id_loc and modl.id_model=wh.id_model and sl.id_sloc=wh.id_subl and  fl.id_flr=wh.id_flr and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign and wh.id_dept="+id_dept1+"  and d.id_dept="+id_dept+" " +tempSql1+" "; */
								}
						if(UserType.equals("RHead")){
							if(id_dept.equals("all"))	
								
								/* sql="select id_wh_dyn ,ds_pro,mfr,no_mfr, l.nm_loc,nm_model,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp "+
										"from A_Ware_House wh,  M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Model modl where allocate=1 and l.id_loc=wh.id_loc and modl.id_model=wh.id_model and sl.id_sloc=wh.id_subl  and fl.id_flr=wh.id_flr and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign and typ_asst = '"+UserType+"' " +tempSql1+" ";
						 */	
						 
						 sql=" select id_wh_dyn ,ds_pro,mfr,no_mfr, loc.nm_loc,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,sloc.nm_subl,flr.nm_flr, dep.nm_dept,nm_computer,emp.nm_emp "+
									"  from  A_Ware_House wh "+
									"  LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+ 
									" LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc"+
									" LEFT JOIN M_Model model on wh.id_model=model.id_model "+
									" LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
									" LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
									" LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
									"  where allocate = 1 and loc.id_loc=wh.id_loc and sloc.id_sloc=wh.id_subl "+ 
									"  and typ_asst = '"+UserType+"' " +tempSql1+" ";
						 else
								/* sql="select id_wh_dyn ,ds_pro,mfr,no_mfr, l.nm_loc,nm_model,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp "+
										"from A_Ware_House wh,  M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Model modl where allocate = 1 and l.id_loc=wh.id_loc and modl.id_model=wh.id_model and sl.id_sloc=wh.id_subl and  fl.id_flr=wh.id_flr and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign and typ_asst = '"+UserType+"' and d.id_dept="+id_dept+" " +tempSql1+" ";
					 */	
					 
							 sql=" select id_wh_dyn ,ds_pro,mfr,no_mfr, loc.nm_loc,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,sloc.nm_subl,flr.nm_flr, dep.nm_dept,nm_computer,emp.nm_emp "+
										"  from  A_Ware_House wh "+
										"  LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+ 
										" LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc"+
										" LEFT JOIN M_Model model on wh.id_model=model.id_model "+
										" LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
										" LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
										" LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
										"  where allocate = 1 and loc.id_loc=wh.id_loc and sloc.id_sloc=wh.id_subl "+ 
										"  and typ_asst = '"+UserType+"' and dep.id_dept="+id_dept+" " +tempSql1+" ";
						
						}
						if(UserType.equals("IT")){
							if(id_dept.equals("all"))	
								
								/* sql="select id_wh_dyn ,ds_pro,mfr,no_mfr, l.nm_loc,nm_model,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp "+
										"from A_Ware_House wh,  M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Model modl where allocate=1 and l.id_loc=wh.id_loc and modl.id_model=wh.id_model and sl.id_sloc=wh.id_subl  and fl.id_flr=wh.id_flr and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign and typ_asst = '"+UserType+"' " +tempSql1+" ";
						 */	
						 
						 sql=" select id_wh_dyn ,ds_pro,mfr,no_mfr, loc.nm_loc,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,sloc.nm_subl,flr.nm_flr, dep.nm_dept,nm_computer,emp.nm_emp "+
									"  from  A_Ware_House wh "+
									"  LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+ 
									" LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc"+
									" LEFT JOIN M_Model model on wh.id_model=model.id_model "+
									" LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
									" LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
									" LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
									"  where allocate = 1 and loc.id_loc=wh.id_loc and sloc.id_sloc=wh.id_subl "+ 
									"  and typ_asst = '"+UserType+"' " +tempSql1+" ";
						 else
								/* sql="select id_wh_dyn ,ds_pro,mfr,no_mfr, l.nm_loc,nm_model,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp "+
										"from A_Ware_House wh,  M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Model modl where allocate = 1 and l.id_loc=wh.id_loc and modl.id_model=wh.id_model and sl.id_sloc=wh.id_subl and  fl.id_flr=wh.id_flr and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign and typ_asst = '"+UserType+"' and d.id_dept="+id_dept+" " +tempSql1+" ";
					 */	
					 
							 sql=" select id_wh_dyn ,ds_pro,mfr,no_mfr, loc.nm_loc,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,sloc.nm_subl,flr.nm_flr, dep.nm_dept,nm_computer,emp.nm_emp "+
										"  from  A_Ware_House wh "+
										"  LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+ 
										" LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc"+
										" LEFT JOIN M_Model model on wh.id_model=model.id_model "+
										" LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
										" LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
										" LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
										"  where allocate = 1 and loc.id_loc=wh.id_loc and sloc.id_sloc=wh.id_subl "+ 
										"  and typ_asst = '"+UserType+"' and dep.id_dept="+id_dept+" " +tempSql1+" ";
						
						}
						
						if(UserType.equals("NIT")){
							if(id_dept.equals("all"))	
								/* sql="select id_wh_dyn ,ds_pro,mfr,no_mfr, l.nm_loc,nm_model,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp "+
										"from A_Ware_House wh,  M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user e,M_Model modl where allocate=1 and l.id_loc=wh.id_loc and modl.id_model=wh.id_model and sl.id_sloc=wh.id_subl  and fl.id_flr=wh.id_flr and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign and typ_asst = '"+UserType+"' " +tempSql1+"  ";
							 */
							 
							 sql=" select id_wh_dyn ,ds_pro,mfr,no_mfr, loc.nm_loc,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,sloc.nm_subl,flr.nm_flr, dep.nm_dept,nm_computer,emp.nm_emp "+
										"  from  A_Ware_House wh "+
										"  LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+ 
										" LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc"+
										" LEFT JOIN M_Model model on wh.id_model=model.id_model "+
										" LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
										" LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
										" LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
										"  where allocate = 1 and loc.id_loc=wh.id_loc and sloc.id_sloc=wh.id_subl "+ 
										"  and typ_asst = '"+UserType+"' " +tempSql1+"  ";
							 
							 else
								 
								 sql=" select id_wh_dyn ,ds_pro,mfr,no_mfr, loc.nm_loc,nm_model,(replace(convert(VARCHAR, dt_po, 106), ' ', '-')) as dt_po,sloc.nm_subl,flr.nm_flr, dep.nm_dept,nm_computer,emp.nm_emp "+
											"  from  A_Ware_House wh "+
											"  LEFT JOIN M_Emp_User emp on wh.to_assign=emp.id_emp_user "+ 
											" LEFT JOIN M_Loc loc on wh.id_loc=loc.id_loc"+
											" LEFT JOIN M_Model model on wh.id_model=model.id_model "+
											" LEFT JOIN M_Subloc sloc on wh.id_subl=sloc.id_sloc "+
											" LEFT JOIN M_Floor flr on wh.id_flr=flr.id_flr "+
											" LEFT JOIN M_Dept dep on wh.id_dept=dep.id_dept "+
											"  where allocate = 1 and loc.id_loc=wh.id_loc and sloc.id_sloc=wh.id_subl "+ 
											"  and typ_asst = '"+UserType+"' and d.id_dept="+id_dept+" " +tempSql1+" ";
							
								/* sql="select id_wh_dyn ,ds_pro,mfr,no_mfr, l.nm_loc,nm_model,(replace(convert(NVARCHAR, dt_po, 103), ' ', '-')) as dt_po, sl.nm_subl, fl.nm_flr, d.nm_dept,nm_computer,e.nm_emp "+
										"from A_Ware_House wh,  M_Loc l,M_Subloc sl, M_Floor fl ,M_Dept d,M_Emp_user,M_Model modl e where allocate = 1 and l.id_loc=wh.id_loc and modl.id_model=wh.id_model and sl.id_sloc=wh.id_subl and  fl.id_flr=wh.id_flr and d.id_dept=wh.id_dept and e.id_emp_user=wh.to_assign and typ_asst = '"+UserType+"' and d.id_dept="+id_dept+" " +tempSql1+" ";
						 */
						 
						
						
						}
						
						
						
			/* out.print(sql);  */
				
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				int slNo    =   0;
			{%>
		<table style="border: 1px solid white;" border="1" width="100%" height="100%">
		
				<tr >
					<td colspan="9" class="headerTR"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 500px;">Department Report</p></td>
				</tr>	
				<tr>
					<td class="headerTD"><strong><center>S No. &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Asset ID  &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Asset Name &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Serial No. &nbsp;&nbsp;</center></strong></td>
					<!-- <td class="headerTD"><strong><center>Model No. &nbsp;&nbsp;</center></strong></td> -->
					<td class="headerTD"><strong><center>Purchase Date &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Department &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Computer Name &nbsp;&nbsp;</center></strong></td>
					<td class="headerTD"><strong><center>Employee Name &nbsp;&nbsp;</center></strong></td>
				</tr>	
				
			<%} 
				while(rs.next())
				{
					String nm_emp = "";
					if(rs.getString("nm_emp")== null) 
						nm_emp="--";
					else
						nm_emp=rs.getString("nm_emp");
				%><tr>
					<td><center><%=++slNo%></center></td>
					<td><center><%=rs.getString("id_wh_dyn")%></center></td>
					<td><center><%=rs.getString("ds_pro")%></center></td>
					<td><center><%=rs.getString("no_mfr")%></center></td>
					<td><center><%=rs.getString("nm_model")%></center></td>
					<td><center><%=rs.getString("dt_po")%></center></td>
					<td><center><%=rs.getString("nm_dept")%></center></td>
					<td><center><%=rs.getString("nm_computer")%></center></td>
					<td><center><%=nm_emp%></center></td>
				</tr>
				<%} 
			}
			catch(Exception e)
			{
				out.println("sql error in department Report.");
			}
			%>
			<tr>
			<%
	        if (exportToExcel == null) {
	    %>
	    <td colspan="11">
				<a href="departmentV.jsp?exportToExcel=YES&id_dept=<%=id_dept%>&id_grp=<%=id_grp%>&id_sgrp=<%=id_sgrp%>">
				<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 600px;">Export To Excel</button>
				</a>		
				</td>
	    
	    <%
	        }
	    %>
		
	</table>
</div>
</body>
<%} %>
</html>