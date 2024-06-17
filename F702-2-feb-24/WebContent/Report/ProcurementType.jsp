<!--  LoacationAsset.jsp -->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Reports ></a><a href="#">Search ></a><a href="#">Procurement Type Report - Assets</a>-->
</div>
<script type="text/javascript">

$(function (){
	
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForStoreAssetLReport','CapG',function (status){
		if(status)
		{
			
		}});
	
});

</script>
<form action="ReportView/Location_Asset_Report.jsp" method="POST" target="_new">

<div class="commonDiv" id="displayStoresAsset">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Procurement Type Report - Assets</p></td>
			</tr>
			<tr>
				<td><b>Select the Asset Group<font color="red">*</font></b></td>
				<td>
					<select id="assetDivForStoreAssetLReport" name="id_grp"  style="width:140" >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			
			<tr>

				<td><b>Select Type of Import<font color="red">*</font></b></td>
				<td>
					<select  name="typ_proc" class="common-validation select" data-valid="mand"  style="width:140">
						<option value="a">Outright Purchase</option>
						<option value="b">Free of Cost</option>
						<option value="b">Loan Basis</option>
						
						
					</select>
				</td>
			</tr>
			
			<tr>
				<td ><b>Type of Asset<font color="red">*</font></b></td>
				<td >
					<select  name="allocate" class="common-validation select" data-valid="mand"  style="width:140">
						<option value="a">Installed Asset</option>
						<option value="b">Store Asset</option>
						
						
					</select>
				</td>
			</tr>
			
			
			
			
			
			
			
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 443px;float: center;">
				<button type="submit" style="float:center;"  class="btn btn-primary" onclick="return ControleLocation()">Report</button>
			</p>
</div>
</form>
