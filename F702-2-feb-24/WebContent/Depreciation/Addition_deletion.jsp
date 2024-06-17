
<div class="commonDiv" style="padding:10px;color:blue;">
	<!--  Depreciation >Addition/Deletion -->
</div> 

<script type="text/javascript">

$(function() {
	
	
	$('#selectAssetIdAdditionDeletion').hide();
	$('#createAdditionDeletiondetails').hide();
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForAdditionDeletion','CapG',function (status){
		if(status)
		{
			DropdownResultForFinanceYear('financeYearForAdditionDeletion');
		}});
	
	
	
	$( ".datepicker" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	    });
	//$('input[name="dt_to"]').datepicker("setDate", currentDate);
});


function CheckNegativityForAdditionDeletion()
{
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	$('input[name="add_amt"]').removeClass('error');
	var str = $('input[name="add_amt"]').val();
	if(str != '')
	{
		if(intRegex.test(str) || floatRegex.test(str)) {
			
		}
		else
			{
				alert('Invalid number.');
				$('input[name="add_amt"]').addClass('error');
				$('input[name="add_amt"]').focus();
				
			}
	}
}


</script>


<div class="card-small" id="displayAdditionDeletion">
	
	<form action="" name="submitDisplayAdditionDeletion" id="submitDisplayAdditionDeletion">
		
		<div class="card-header new">
				<h3 class="card-title1">Addition & Deletion</h3>
			</div>
		<table class="table table-bordered" style="width:100%;">
			<!-- <tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:179px">Addition & Deletion</p></td>
			</tr> -->
			<tr>
				<td><b>Select Financial Year<font color="red">*</font></b></td>
				<td>
					<select name="id_finYear"  style="width:140" id="financeYearForAdditionDeletion" class="form-control" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Asset Group<font color="red">*</font></b></td>
				<td>
					<select id="assetDivForAdditionDeletion" name="id_grp" class="form-control" data-valid="mand" style="width:140" onChange="SubDropDownDataDisplay(this,'subGroupDataForAdditionDeletion','M_Subasset_Div')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Asset SubGroup<font color="red">*</font></b></td>
				<td>
					<select id="subGroupDataForAdditionDeletion" name="id_sgrp" class="form-control" data-valid="mand" style="width:140" >
						<option value="">Select</option>
						
					</select>
					<input type="hidden" name="action" value="DropDownResult">
				</td>
			</tr>
			<tr>
			<td colspan="2">
			
					<button type="button" style="float:center;margin-left:300px;"  class="btn btn-primary" onclick="ControlAdditionDeletion('Next','selectAssetIdAdditionDeletion','displayAdditionDeletion','createAdditionDeletiondetails')">Next</button>
				
			</td>
			</tr>
	  </table>
	</form>	
	
</div>


<div class="card-small" id="selectAssetIdAdditionDeletion">
	<form action="" name="submitAdditionDeletion" id="submitAdditionDeletion">
		<table class="commonTable table table-bordered" style="width:100%;">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:209px">Select ASSETID</p></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: right;"><b>Asset ID<font color="red">*</font></b></td>
				<td colspan="2" style="text-align: left;">
					<select name="assetid"  style="width:140" id="assetIdForAdditionDeletion">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
			<td colspan="4">
				<button type="button" style="float:center;margin-left:300px;"  class="btn btn-primary" onclick="ControlAdditionDeletion('Next1','createAdditionDeletiondetails','displayAdditionDeletion','selectAssetIdAdditionDeletion')">Next</button>
			
			</td>
			</tr>
	  </table>
	</form>
</div>

<div class="card" id="createAdditionDeletiondetails" style="display:none;">
	<form action="" name="submitAdditionDeletiondetails" id="submitAdditionDeletiondetails">	
	
	<input type="hidden" name="action" value="Save">
	<input type="hidden" name="fineId" value="">
	<input type="hidden" name="asset_group_id" value="">
	<input type="hidden" name="asset_subgroup_id" value="">
	<input type="hidden" name="asset_vendor_id" value="">
	<input type="hidden" name="assetid" value="">
	<input type="hidden" name="assetid1" value="">
	<input type="hidden" name="asset_description" value="">
	
	
		<table class="commonTable table table-bordered" align="center" width="800px" height="100%">
			<tr>
				<td colspan="8" class="tableHeader"><p class="tableHeaderContent" style="margin-left:210px">Register Of Fixed Assets</p></td>
			</tr>
			<tr>
				<td><strong>Asset ID</strong></td>
				<td><input type="text" name="id_wh_dyn" value="" readonly></td>
				<td ><strong>Asset Name</strong></td>
				<td><input type="text" name="ds_pro" value="" readonly></td>
			</tr>
			<tr>
				<td><strong>Asset Description</strong></td>
				<td><input type="text" name="ds_asst" value="" readonly></td>
				<td><strong>Installation Date</strong></td>
				<td><input type="text" name="dt_allocate" value="" readonly></td>
			</tr>
			<tr>
				<td><strong>Location</strong></td>
				<td><input type="text" name="nm_loc" value="" readonly></td>
				<td><strong>Sub Location</strong></td>
				<td><input type="text" name="nm_subl" value="" readonly></td>
			</tr>
			<tr>
				<td><strong>Assigned To</strong></td>
				<td><input type="text" name="nm_emp" value="" readonly></td>
				<td><strong>Asset Value</strong></td>
				<td><input type="text" name="val_asst" value="" readonly></td>
			</tr>
		</table>
	
		<table class="commonTable table table-bordered" align="center" width="800px" height="100%">
			<tr id="addDelHeader">

			</tr>
			<tr>
				<td class="lefttd" class ="text"><b>Select Addition / Deletion</b><font color="red">*</font></td>
				<td>
				<select name="status" class="form-control" data-valid="mand">
					<option value="">Select</option>
					<option value="add">ADDITION</option>
					<option value="delete">DELETION</option>
					<option value="sold">SOLD</option>
					<!-- <option value="writeoff">WRITE OFF</option>
					<option value="scrap">SCRAP</option>
					<option value="lost">LOST</option> -->
				</select>
				</td>
			</tr>

			<tr>
				<td><b>Addition/Deletion/Sold/Written Off/Recovery Date</b><font color="#FF0000">*</font></td>
				<td><input type="text" name="add_date" size="15" class="form-control datepicker" data-valid="mand"></td>
			</tr>
			<tr>
				<td><b>Addition/Deletion/Sold/Recovery Amount</b><font color="#FF0000">*</font></td>
				<td><input type="text" name="add_amt" value="" class="form-control" data-valid="mand" onBlur="CheckNegativityForAdditionDeletion()"></td>
			
			</tr>
			<tr>
				<td><b>Addition/Deletion Remarks</b></td>
				<td>
				<textarea cols ="30" rows="3" name ="add_det"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlAdditionDeletion('Save','displayAdditionDeletion','selectAssetIdAdditionDeletion','createAdditionDeletiondetails')">Save</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlAdditionDeletion('Back','selectAssetIdAdditionDeletion','displayAdditionDeletion','createAdditionDeletiondetails')">Cancel</button>
				</td>
			</tr>
	  </table>
	</form>
</div>
	