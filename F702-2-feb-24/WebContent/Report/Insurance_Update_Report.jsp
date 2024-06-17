<!--Addition_deletion.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#" >Assets > </a><a href="#">Insurance Update </a> -->
</div>
<script type ="text/javascript" src="js/depreciationCommon.js"></script>

<script type="text/javascript">

$(function(){
	
	DisplayDropDownDataForGroup('M_Asset_Div','grpDataForInsuranceUpdateReport','CapG',function (status){
		if(status)
		{
			DropdownResultForFinanceYear('finDataForInsuranceUpdateReport');
		}});
	
	
	
});

</script>

<div class="commonDiv" id="displayInsuranceUpdatesReport">
<form action="ReportView/Insurance_Update_Report_View.jsp" method="POST" target="_blank">	
		<table class="table table-bordered  " align="center" style="width:650px;" height="100%" border="1">
			<tr class="tableHeader ">
				<td colspan="4" class="tableHeader"><center><p class="tableHeaderContent">Select Financial Yr & Group</p></center></td>
			</tr>
			<tr>
				<td  style="text-align: left;"><b>Select Financial Year<font color="red">*</font></b></td>
				<td  style="text-align: left;">
					<select name="id_finance" class="common-validation" required data-valid="mand" id="finDataForInsuranceUpdateReport">
						
					</select>
				</td>
			</tr>
			<tr>
				<td  style="text-align: left;"><b>Group<font color="red">*</font></b></td>
				<td  style="text-align: left;">
					<select name="id_grp" class="common-validation" required data-valid="mand" id="grpDataForInsuranceUpdateReport">
						
					</select>
				</td>
			</tr>
			<input type="hidden" name="action" value="getGroupData">
			<tr>
			<td colspan="4">
			<center><button type="submit" class="btn btn-primary">Report</button></center>
			</td>
			</tr>
	  </table>
</form>	
</div>

