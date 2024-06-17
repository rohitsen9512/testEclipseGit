<!--  Installed_asset.jsp -->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Reports > </a></a><a href="#">Department Report</a>-->
</div>
<script type="text/javascript">

$(function() {
	
	$('button[name="update"]').addClass('hideButton');
	DropDownDataDisplayForReport('M_Dept','fordept');
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivFordeptReport','CapG',function (status){
		if(status)
		{
			
		}});
			
	
});

</script>
<form action="ReportView/departmentV.jsp" method="POST" target="_new">

<div class="commonDiv" id="displayEmployeeAssetReport">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Department Report</p></td>
			</tr>
			<tr>
					<td><b>Select Department<font color="red">*</font> </b></td>
				<td>
					<select name="id_dept" id="fordept" class="common-validation" data-valid="mand" >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Select the Asset Group<font color="red">*</font></b></td>
				<td>
					<select id="assetDivFordeptReport" name="id_grp"  style="width:140" onChange="DisplaySubDropDownDataForReport(this,'subAssetDivForStoreAssetReport','M_Subasset_Div')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			
			<tr>
				<td><b>Select the Asset Sub Group</b></td>
				<td>
					<select id="subAssetDivForStoreAssetReport" name="id_sgrp"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			
			
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 450px;float: center;">
				<button type="submit" style="float:center;"  class="btn btn-primary">Report</button>
			</p>
</div>
</form>