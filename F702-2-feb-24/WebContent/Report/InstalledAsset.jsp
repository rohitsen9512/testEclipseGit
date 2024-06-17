<!-- Installed_asset.jsp -->
<div class="commonDiv" style="padding:10px;color:blue;">
	<!--  MIS Data > Asset Report > Installed Asset-->
</div>
<script type="text/javascript">

$(function (){
	
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForStoreAssetReport','CapG',function (status){
		if(status)
		{
			
		}});
});

</script>
<form action="ReportView/Installed_Asset_Report.jsp" method="POST" target="_new">

<div class="commonDiv" id="displayInstalledAssetReport">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Installed Report - Assets</p></td>
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