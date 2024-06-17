
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Report ></a></a><a href="#"> Asset ID Report</a>-->
</div>
<script type="text/javascript">

$(function() {
	
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForMaintenanceReport','CapG',function (status){
		if(status)
		{
			
		}});
});
function GetDynamicAssetIdForReport(idForDisplayData,formName)
{
	var t=false;
		var id_grp = $('#assetDivForMaintenanceReport').val();
		var id_sgrp = $('#subGroupDataForMaintenanceReport').val();
		if(id_sgrp != '')
			{
			$.post('Asset_Maintenance',{action : 'DropDownResult' , id_grp : id_grp , id_sgrp : id_sgrp},function (r)
					{
				var list = '<option value=""> Select </option>';
						if(r.data.length > 0)
							{
								t=true;
								for(var i = 0; i < r.data.length ; i++)
								{
									params = r.data[i];
									list = list + '<option value="'+params.id_wh+'"> '+params.id_wh_dyn+'</option>';
								}
								
							}
						if(t)
						{
							$('#'+idForDisplayData).html(list);
							$('#'+idForDisplayData).focus();
						}
					else
						{
							bootbox.alert('No assets available .');
						}
						
					},'json');
			}
		else
			{
				bootbox.alert('Please select sub group.');
				$('#'+idForDisplayData).focus();
				$('#'+idForDisplayData).html('<option value=""> Select </option>');
			}
}



</script>
<center>
<div class="commonDiv" id="displayAssetMaintenanceReport">
	
	<form action="ReportView/Asset_Maintenance_View.jsp" method="POST" target="_new" name="submitDisplayAssetMaintenanceReport" id="submitDisplayAssetMaintenanceReport">
		<table class="commonTable table table-bordered" style="width:600px;">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:179px">Select Asset </p></td>
			</tr>
			<tr>
				<td  style="text-align: right;"><b>Asset Group <font color="red">*</font></b></td>
				<td  style="text-align: left;">
					<select id="assetDivForMaintenanceReport" name="id_grp" required style="width:140" onChange="SubDropDownDataDisplayForReport(this,'subGroupDataForMaintenanceReport','M_Subasset_Div')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td  style="text-align: right;"><b>Asset Sub Group</b></td>
				<td  style="text-align: left;">
					<select id="subGroupDataForMaintenanceReport" name="id_sgrp"  style="width:140" onChange="GetDynamicAssetIdForReport('assetIdForAssetMaintenanceReport','submitDisplayAssetMaintenanceReport')">
						<option value="">Select</option>
						
					</select>
					<input type="hidden" name="action" value="DropDownResult">
				</td>
			</tr>
			<tr>
				<td  style="text-align: right;"><b>Select Asset ID </b></td>
				<td  style="text-align: left;">
					<select name="id_wh"  style="width:140" id="assetIdForAssetMaintenanceReport">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
			<td colspan="2">
			
					<button type="submit" style="float:center;margin-left:300px;"  class="btn btn-primary">Report</button>
				
			</td>
			</tr>
	  </table>
	</form>	
	
</div>

</center>