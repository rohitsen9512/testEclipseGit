<%@ page language="java" import="java.sql.*,javax.naming.*,javax.sql.*,java.io.*,java.util.Date, java.text.SimpleDateFormat,com.Common.Common" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gate Pass</title>
<script type ="text/javascript" src="jquery1.10.js"></script>
<style type="text/css">
.headerTR{
background-color: blue;
}
.headerTD{
background-color: lavender;
}
.tableFormatHeader{
border-collapse: collapse;
border-color: #FFFFFF;
font-family: monospace;
font-size: 20px;
padding: 2px;
}




.tableFormat {
border-collapse: collapse;
// border-color: #FFFFFF;
font-family: monospace;
font-size: 17px;
padding: 2px;
}
#PreviewInstalledAssetReport
{
width: 80%;
border: 1px solid;
}
</style>
<script type="text/javascript">
function printPg()
{
$('#printPgId').hide();
window.print();



}



</script>
</head>
<%
String usertype = (String) session.getAttribute("UserName");
if(usertype == null) {
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


String req_no = "",holder="",auth_person="",req_by="",type_tran="",id_wh_dyn="";
String id_wh = "",id_iut="",nm_holder="",nm_auth_person="",nm_req_by="",ds_pro="",reqDate="";
Date currDate = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
String sql="";
String frmst="",frmloc="",frmstr="",frmdept="",tost="",toloc="",tostr="",nm_company="Atlus AMS";
String frmdiv="",frmctry="",frmlocc="",todept="",todiv="",toctry="",tolocc="";
String tran_type="";
try
{
ResultSet rs=null;
ResultSet rs1=null;
PreparedStatement ps=null;
Connection con=null;
Statement stmt=null;
con=Common.GetConnection(request);
req_no = request.getParameter("req_id");

sql="select distinct(req_no),dt_req,emp.nm_emp as reqBy,empAprv.nm_emp as apprvBy,upper(iut.type_tran) from A_Ware_House wh,A_Iut_History iut,M_Emp_User emp, M_Emp_User empAprv where (allocate=1 or allocate=2) and "+
" wh.id_wh= iut.id_wh and (iut_approval =2 OR iut_approval =3 OR iut_approval =5) and type_tran !='Intra' and emp.id_emp_user=wh.req_by and empAprv.id_emp_user=iut.tran_aprov_by and acc_asst=0 and req_no="+req_no+" ";
System.out.println(sql);
ps=con.prepareStatement(sql);
rs=ps.executeQuery();
if(rs.next())
{
reqDate = rs.getString("dt_req");
auth_person = rs.getString("apprvBy");
req_by = rs.getString("reqBy");
tran_type = rs.getString(5);
}

sql="select fs.nm_subl as frmst,fl.nm_building as frmloc,fss.nm_flr as frmstr,fd.nm_dept from M_Subloc fs,M_Building fl,M_Floor fss,M_Dept fd,A_Iut_History ai where fs.id_sloc=ai.id_subl and fl.id_building=ai.id_building and fss.id_flr=ai.id_flr and fd.id_dept=ai.id_dept and ai.id_iut=any(select id_iut from A_Iut_History where  req_no_transfer="+req_no+")";

System.out.println(sql);
ps=con.prepareStatement(sql);
rs=ps.executeQuery();
if(rs.next())
{

frmst = rs.getString(1);
frmloc = rs.getString(2);
frmstr = rs.getString(3);
frmdept = rs.getString(4);
}

sql="select ts.nm_subl as tost,tl.nm_building as toloc,tss.nm_flr as tostr,td.nm_dept from M_Subloc ts,M_Building tl,M_Floor tss,M_Dept td,A_Iut_History ai where ts.id_sloc=ai.id_subl_tran and tl.id_building=ai.id_building_tran and tss.id_flr=ai.id_flr_tran and td.id_dept=ai.id_dept_tran and ai.id_iut=any(select id_iut from A_Iut_History where req_no_transfer="+req_no+")";

System.out.println(sql);
ps=con.prepareStatement(sql);
rs=ps.executeQuery();
if(rs.next())
{
	tost = rs.getString(1);
	toloc = rs.getString(2);
	tostr = rs.getString(3);
	todept = rs.getString(4);
}

sql="Select * from M_Company";
System.out.println(sql);
ps=con.prepareStatement(sql);
rs=ps.executeQuery();
if(rs.next())
{
nm_company = rs.getString("nm_com");
}

}
catch(Exception e)
{
out.println("sql error in Print gate Pass."+e.toString());
}
%>



<center>
<br><br>
<div id="PreviewInstalledAssetReport">
<table border="0" width="100%" align="center" class="tableFormatHeader">
<tr><td colspan="2"><center><b><%=nm_company %></b><br></center></td>
<%-- <td style="float:right;"><span><b>TO:-</b><%=sdf.format(currDate)%></span></td> --%>
</tr>
<tr><td></td></tr><tr><td></td></tr>
				<tr><td colspan="2"><center><b><%=tran_type %> GATE PASS</b><br></center></td></tr>
				<tr><td></td></tr>	
				<tr><td></td></tr>	
				<tr><td></td></tr>	
				<tr><td><span><b>FROM:-</b><br></span><span><b>---------------</b><br></span><span><b><%=frmstr%></b><br></span><span><b><%=frmloc%></b><br></span><span><b><%=frmst%></b><br></span><span><b><%=frmdept%></b><br></span></td><td style="float:right;"><span><b>TO:-</b><br></span><span><b>---------------</b><br></span><span><b><%=tostr%></b><br></span><span><b><%=toloc%></b><br></span><span><b><%=tost%></b><br></span><span><b><%=todept%></b><br></span></td></tr>
		</table>
		<br><br>

<table width="100%" align="center" border="1" class="tableFormatHeader">
<tr>
<td ><strong>Requested By</strong></td>
<td><%=req_by%></td>
<td ><strong>Requested date</strong></td>
<td><%=reqDate%></td>
</tr>
<tr><td colspan="4"></td></tr>
<tr><td colspan="4"></td></tr>
<tr><td colspan="4"></td></tr>
<tr><td colspan="4"></td></tr>
<tr>
<td ><strong>Approved By</strong></td>
<td><%=auth_person%></td>
<td ><strong>Request Number</strong></td>
<td>Request No-<%=req_no%></td>
</tr>

<tr><td colspan="4"></td></tr>
<tr><td colspan="4"></td></tr>
<tr><td colspan="4"></td></tr>
<tr><td colspan="4"></td></tr>
<tr><td colspan="4"></td></tr>
<tr><td colspan="4"></td></tr>


</table>

<table width="100%" align="center" border="1" class="tableFormat">
<tr class="new">
<td><center><strong>S No. </strong></center></td>
<td ><center><strong>Ware House ID</strong></center></td>
<td ><center><strong>Asset Name</strong></center></td>
<td ><center><strong>Serial Number</strong></center></td>
<td ><center><strong>Model</strong></center></td>
<!-- <td ><center><strong>GEP Asset Tag</strong></center></td> -->
<td ><center><strong>Manufacturer</strong></center></td>
<!-- <td ><center><strong>Host Name</strong></center></td> -->
</tr>

<%
ResultSet rs1=null;
PreparedStatement ps=null;
Connection con=null;
con=Common.GetConnection(request);
sql="select id_wh_dyn,ds_pro,wh.mfr,nm_model,appNo,serial_no from A_Ware_House wh,M_Model mm where mm.id_model=wh.id_model and req_no="+req_no+"";
System.out.println(sql);
ps=con.prepareStatement(sql);
rs1=ps.executeQuery();
int slNo=0;
while(rs1.next())
{%>
<tr>
<td><center><%=++slNo%></center></td>
<td><center><%=rs1.getString(1)%></center></td>
<td><center><%=rs1.getString(2)%></center></td>
<td><center><%=rs1.getString(6)%></center></td>

<td><center><%=rs1.getString(4)%></center></td>
<td><center><%=rs1.getString(3)%></center></td>
<!-- <td><center>--</center></td> -->
<%-- <td><center><%=rs1.getString(8)%></center></td> --%>

</tr>
<%}%>

</table>



<br><br><br>
<span style="float: left;">Signature1 .......................................</span>
<span style="float: right;">Signature2 .......................................</span>
<br><br><br>

</div>
</center>
</body>
<%} %>
</html>