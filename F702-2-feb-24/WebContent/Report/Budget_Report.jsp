<!--  Installed_asset.jsp -->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Reports ></a><a href="#">MIS Report ></a><a href="#">Employee Assigned Detail</a> -->
</div>
<script type="text/javascript">

$(function (){
	DropdownResultForFinanceYear('financeDataId');
});

</script>
<form action="ReportView/Budget_Report.jsp" method="POST" target="_new">

<div class="commonDiv" id="displayEmployeeAssetReport">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Budget Report</p></td>
			</tr>
			<tr>
				<td><b>Select the Financial Year<font color="red">*</font></b></td>
				<td>
					<select id="financeDataId" name="id_finance"  style="width:140" required>
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			
			<tr>
				<td><b>Ad-Hoc<font color="red">*</font></b></td>
				<td>
					<select id="adhoc" name="adhoc"  style="width:140" required>
						<option value="">Select</option>
						<option value="Yes">YES</option>
						<option value="NO">NO</option>
						
					</select>
				</td>
			</tr>
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 443px;float: center;">
				<button type="submit" style="float:center;"  class="btn btn-primary">Report</button>
			</p>
</div>
</form>