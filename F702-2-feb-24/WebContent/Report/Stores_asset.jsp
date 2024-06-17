<!--  Stores_asset.jsp -->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">MIS Data > </a><a href="#">Asset Report > </a><a href="#">Store Asset</a>-->
</div>
<script type="text/javascript">

$(function (){
	
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForStoreAssetReport','CapG',function (status){
		if(status)
		{
			
		}});
});

</script>
<form action="ReportView/StoreAssetView.jsp" method="POST" target="_new">

<div class="commonDiv" id="displayStoresAsset">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:170px">Store Report - Assets</p></td>
			</tr>
			<tr>
				<td><b>Select the Asset Group<font color="red">*</font></b></td>
				<td>
					<select id="assetDivForStoreAssetReport" name="id_grp"  style="width:140" onChange="DisplaySubDropDownDataForReport(this,'subAssetDivForStoreAssetReport','M_Subasset_Div')">
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
		
			<p style="width: 85px;margin-left: 500px;float: center;">
				<button type="submit" style="float:center;"  class="btn btn-primary" onclick="return ControleStoreAsset()">Report</button>
			</p>
</div>
</form>
<div class="commonDiv" id="createStoresAsset" style="display:none;">
	<form action="" name="submitStoresAsset">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="10" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Stores Inventory for Building Group</p></td>
			</tr>
			<tr  style="background-color: blue;">
				<td><strong>S No.</strong></td>
				<td ><strong>Asset ID</strong></td>
				<td ><strong>Asset Name</strong></td>
				<td><strong>Asset Description</strong></td>
				<td><strong>Asset Remarks</strong></td>
				<td><strong>Manufacturer</strong></td>
				<td><strong>Asset Model</strong></td>
				<td><strong>Asset Serial No.</strong></td>
				<td><strong>Unit Cost(RS.)</strong></td>
				<td><strong>Po_Date</strong></td>
			</tr>
			<tr>
				<td  colspan="12" ><font color="red"><center>Record(s) not available</center></font></td>
			</tr>
		</table>
		
		<br>
		
			<p style="width: 125px;margin-left: 443px;float: center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayStoresAsset','createStoresAsset','')">Export To Excel</button>
			</p>
	</form>
</div>