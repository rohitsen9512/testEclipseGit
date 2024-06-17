
<%@ page language="java" import="java.sql.*,javax.naming.*,java.util.*,java.io.*,com.Common.Common,java.text.DecimalFormat" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consolidated CA Report</title>
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

	try{
		Connection con		=	null;
		Statement sm1		=	null;
		Statement sm2		=	null;
		ResultSet rs1		=	null;
		ResultSet rs2		=	null;

		con=Common.GetConnection(request);
		sm1					=	con.createStatement();
		sm2					=	con.createStatement();

		String fin_years	=	request.getParameter("fin_years");
		String actNameType	=	"CAWDV";
		Vector vecGroup=new Vector();
		
		DecimalFormat df = new DecimalFormat("##.##");
		
		
		
		
		
		 String exportToExcel = request.getParameter("exportToExcel");
	        
	        if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "inline; filename="
	                    + "Consolidated_ca_report.xls");
	 
	        }
	        

		String finyearst	=	"";
		String finyeared	=	"";
		String finyearst1	=	"";
		String finyeared1	=	"";

		//out.println("Select fin_stdate,fin_enddate from finyear_master where finyear_id="+fin_years+"");

		rs1=sm1.executeQuery("Select std_finance,end_finance,(replace(convert(NVARCHAR, std_finance, 103), ' ', '-')) as std_finance1,(replace(convert(NVARCHAR, end_finance, 103), ' ', '-')) as end_finance1 from M_Finance_Year where id_fincance="+fin_years+"");

		if(rs1.next()){
			finyearst=rs1.getString(1);
			finyeared=rs1.getString(2);
			finyearst1=rs1.getString(3);
			finyeared1=rs1.getString(4);
		}
		String strFullCompName="",strFullAddressOne="",strFullAddressTwo="",strFullCity="",strFullPIn="";

		rs1=sm1.executeQuery("select nm_com,add1,add2,city,pin from M_Company");
		if(rs1.next())
		{
			strFullCompName		=rs1.getString("nm_com");
			strFullAddressOne	=rs1.getString("add1");
			strFullAddressTwo	=rs1.getString("add2");
			strFullCity			=rs1.getString("city");
			strFullPIn			=rs1.getString("pin");
		}
		rs1=sm1.executeQuery("select id_assetdiv,nm_assetdiv from M_Asset_Div    order by nm_assetdiv");
		while(rs1.next())
		{
			vecGroup.addElement(rs1.getString(1));
			vecGroup.addElement(rs1.getString(2));
		}
		
%>
	<body>
		<form name="form" action="consolidated_ca_reports_xls.jsp" method="post" target="_blank">
		
		<center>
		<%-- <br><br>
		<p align="center"><b><font size="2"><%=strFullCompName%></font></b><br>
		<%=strFullAddressOne%>,<%=strFullAddressTwo%>,<%=strFullCity%>,<%=strFullPIn%></p> --%>
		<table border="1"  width="100%">
		<tr>
			<td class="headerTR" colspan="12"><center><p class="tableHeaderContent" style="font-size: 24px;color: white;">Consolidated Total For The Year <%=finyearst1%> To <%=finyeared1%></p></center></td>
		</tr>
		<tr>
			<td nowrap class="title" rowspan="3">					</td>
			<td nowrap class="title" colspan="5"><center>Asset Value(RS.)</center></td>
			<td nowrap colspan="5" class="title"><center>Depreciation</center></td>
			<td nowrap rowspan="3" class="title"><center>WDV</center>	</td>
		</tr>
		<tr>
			<td nowrap rowspan="2" class="title">	Opening Blance	</td>
			<td nowrap rowspan="2" class="title">	Addition		</td>
			<td nowrap rowspan="2" class="title">	Deletion		</td>
			<td nowrap rowspan="2" class="title">	Written off		</td>
			<td nowrap rowspan="2" class="title">	Total			</td>
			<td nowrap rowspan="2" class="title">	As on <%=finyearst1%>	</td>
			<td nowrap colspan="4" class="title"><center>	For the period <%=finyearst1%> to <%=finyeared1%> </center></td>
		</tr>
		<tr >
			<td nowrap class="title">	Opening		</td>
			<td nowrap class="title">	Additions	</td>
			<td nowrap class="title">	Deductions	</td>
			<td nowrap class="title">	Total		</td>
		</tr>
<%
		double tot_asset_opening=0.0,tot_asset_total=0.0,tot_depreciated_opening=0.0,tot_depreciated_addition=0.0,tot_asset_addition=0.0,tot_depreciated_total=0.0d,t_asst_del=0.0,t_writeoff=0.0,t_dep_del=0.0,t_asondate=0.0,tot_asset_deletions=0.0,tot_AsOnDate=0.0,tot_depreciated_deletion=0.0,tot_writeOff=0.0,tot_wdv=0.0;
		double t_asst_ope=0.0,t_asst_add=0.0,t_dep_add=0.0,t_dep_open=0.0;
		int intCheck=0;
		String code="",name="";
		int i=1;

		for(int j=0;j<vecGroup.size();j=j+2 )
		{
			code=vecGroup.elementAt(j)+"";
			name=vecGroup.elementAt(j+1)+"";
			double asset_addition=0.0,asset_opening=0.0,depreciated_addition=0.0,depreciated_opening=0.0,asset_total=0.0,depreciated_total=0.0,asset_deletions=0.0;
			double AsOnDate=0.0,depreciated_deletion=0.0,wdv=0.0,writeOff=0.0;
			rs1=sm1.executeQuery("select round(sum(asset_opening),2) as asset_opening,round(sum(asset_addition),2) as asset_addition,round(sum(asset_deletion),2) as asset_deletion,round(sum(asset_total),2) as asset_total,round(sum(asondate),2) as asondate,round(sum(depreciated_opening),2) as depreciated_opening,round(sum(depreciated_addition),2) as depreciated_addition,round(sum(depreciated_deletion),2) as depreciated_deletion,round(sum(depreciated_total),2) as depreciated_total,round(sum(wdv),2) as wdv,round(sum(writeoff),2) as writeoff from stline_depreciation where group_id='"+code+"' and startdate ='"+finyearst+"'");
			if(rs1.next()){
				asset_opening			=rs1.getDouble("asset_opening");
				asset_addition			=rs1.getDouble("asset_addition");
				asset_deletions			=rs1.getDouble("asset_deletion");
				asset_total				=rs1.getDouble("asset_total");
				AsOnDate				=rs1.getDouble("asondate");
				depreciated_opening		=rs1.getDouble("depreciated_opening");
				depreciated_addition	=rs1.getDouble("depreciated_addition");
				depreciated_deletion	=rs1.getDouble("depreciated_deletion");
				depreciated_total		=rs1.getDouble("depreciated_total");
				wdv						=rs1.getDouble("wdv");
				writeOff				=rs1.getDouble("writeoff");
				//out.println(asset_opening+" "+asset_addition)+" "+asset_deletions)+" "+asset_total)+" "+AsOnDate)+" "+depreciated_opening)+" "+depreciated_addition)+" "+depreciated_total)+" "+wdv)+" "+writeOff));

%>				<tr>
					<td class="righttd" nowrap><b><%=name%></b></td>
					<input type="hidden" name="name<%=i%>" value="<%=name%>">
					<td class="lefttd">	<%=df.format(asset_opening)%>		</td>
					<input type="hidden" name="asset_opening<%=i%>" value="<%=df.format(asset_opening)%>">
					<td class="lefttd">	<%=df.format(asset_addition)%>		</td>
					<input type="hidden" name="asset_addition<%=i%>" value="<%=df.format(asset_addition)%>">
					<td class="lefttd">	<%=df.format(asset_deletions)%>		</td>
					<input type="hidden" name="asset_deletions<%=i%>" value="<%=df.format(asset_deletions)%>">
					<td class="lefttd">	<%=df.format(writeOff)%>				</td>
					<input type="hidden" name="writeOff<%=i%>" value="<%=df.format(writeOff)%>">
					<td class="lefttd">	<%=df.format(asset_total)%>			</td>
					<input type="hidden" name="asset_total<%=i%>" value="<%=df.format(asset_total)%>">
					<td class="lefttd">	<%=AsOnDate%>				</td>
					<input type="hidden" name="AsOnDate<%=i%>" value="<%=AsOnDate%>">
					<td class="lefttd">	<%=df.format(depreciated_opening)%>	</td>
					<input type="hidden" name="depreciated_opening<%=i%>" value="<%=df.format(depreciated_opening)%>">
					<td class="lefttd">	<%=df.format(depreciated_addition)%>	</td>
					<input type="hidden" name="depreciated_addition<%=i%>" value="<%=df.format(depreciated_addition)%>">
					<td class="lefttd">	<%=df.format(depreciated_deletion)%>	</td>
					<input type="hidden" name="depreciated_deletion<%=i%>" value="<%=df.format(depreciated_deletion)%>">
					<td class="lefttd">	<%=df.format(depreciated_total)%>	</td>
					<input type="hidden" name="depreciated_total<%=i%>" value="<%=df.format(depreciated_total)%>">
					<td class="lefttd">	<%=df.format(wdv)%>					</td>
					<input type="hidden" name="wdv<%=i%>" value="<%=df.format(wdv)%>">
				</tr>
<%			}

			
			asset_total=asset_opening+asset_addition-asset_deletions;
			//depreciated_total=depreciated_opening+AsOnDate+depreciated_addition-depreciated_deletion;
			//out.println(depreciated_addition);
			//out.println(depreciated_total);
			//wdv=asset_total-depreciated_total;
			tot_asset_opening			=	tot_asset_opening + asset_opening;
			tot_asset_addition			=	tot_asset_addition + asset_addition;
			tot_asset_deletions			=	tot_asset_deletions + asset_deletions;
			tot_asset_total				=	tot_asset_total + asset_total;
			tot_AsOnDate				=	tot_AsOnDate + AsOnDate ;
			tot_depreciated_opening		=	tot_depreciated_opening + depreciated_opening;
			tot_depreciated_addition	=	tot_depreciated_addition + depreciated_addition;
			tot_depreciated_deletion	=	tot_depreciated_deletion + depreciated_deletion;
			tot_depreciated_total		=	tot_depreciated_total + depreciated_total;
			tot_wdv						=	tot_wdv + wdv;
			tot_writeOff				=	tot_writeOff + writeOff;
			i++;
			}
		try{ con.close();} catch(Exception e){}
%>		<tr>
			<input type="hidden" name="length" value="<%=i%>">
			<td class="righttd" nowrap><b>	Grand Total										</b></td>
			<td class="lefttd" nowrap><b>	<%=df.format(tot_asset_opening)%>		</b></td>
			<input type="hidden" name="tot_asset_opening" value="<%=df.format(tot_asset_opening)%>">
			<td class="lefttd" nowrap><b>	<%=df.format(tot_asset_addition)%>		</b></td>
			<input type="hidden" name="tot_asset_addition" value="<%=df.format(tot_asset_addition)%>">
			<td class="lefttd" nowrap><b>	<%=df.format(tot_asset_deletions)%>		</b></td>
			<input type="hidden" name="tot_asset_deletions" value="<%=df.format(tot_asset_deletions)%>">
			<td class="lefttd" nowrap><b>	<%=df.format(tot_writeOff)%>				</b></td>
			<input type="hidden" name="tot_writeOff" value="<%=df.format(tot_writeOff)%>">
			<td class="lefttd" nowrap><b>	<%=df.format(tot_asset_total)%>			</b></td>
			<input type="hidden" name="tot_asset_total" value="<%=df.format(tot_asset_total)%>">
			<td class="lefttd" nowrap><b>	<%=tot_AsOnDate%>				</b></td>
			<input type="hidden" name="tot_AsOnDate" value="<%=tot_AsOnDate%>">
			<td class="lefttd" nowrap><b>	<%=df.format(tot_depreciated_opening)%>	</b></td>
			<input type="hidden" name="tot_depreciated_opening" value="<%=df.format(tot_depreciated_opening)%>">
			<td class="lefttd" nowrap><b>	<%=df.format(tot_depreciated_addition)%>	</b></td>
			<input type="hidden" name="tot_depreciated_addition" value="<%=df.format(tot_depreciated_addition)%>">
			<td class="lefttd" nowrap><b>	<%=df.format(tot_depreciated_deletion)%>	</b></td>
			<input type="hidden" name="tot_depreciated_deletion" value="<%=df.format(tot_depreciated_deletion)%>">
			<td class="lefttd" nowrap><b>	<%=df.format(tot_depreciated_total)%>	</b></td>
			<input type="hidden" name="tot_depreciated_total" value="<%=df.format(tot_depreciated_total)%>">
			<td class="lefttd" nowrap><b>	<%=df.format(tot_wdv)%>					</b></td>
			<input type="hidden" name="tot_wdv" value="<%=df.format(tot_wdv)%>">
		</tr>
		<%
	        if (exportToExcel == null) {
	    %>
		<tr>
			<td  colspan="12" class="centertd">
				<a href="consolidated_ca_reports.jsp?exportToExcel=YES&fin_years=<%=fin_years%>">
				<button name="cancel" type="button"  class="btn btn-primary" style="margin-left: 600px;">Export To Excel</button>
				</a></td>
				
		</tr>

<%
	        }
	    %>
		</table>
		<center>

		<input type="hidden" name="fin_years" value="<%=fin_years%>">
		<input type="hidden" name="actNameType1" value="<%=actNameType%>">

		</p>
<%	 }catch (Exception e){out.println(e);}

%>


</form>
</body>
<%} %>
</html>

