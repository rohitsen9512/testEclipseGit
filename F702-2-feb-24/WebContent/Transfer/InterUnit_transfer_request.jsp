 <!--Intraunit_transfer_request.jsp-->

<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<script type="text/javascript">

$(function() {
	$('.vendorHideShowForIUT').hide();
	$('.HideLocAndSubLocForIUT').hide();
	
	DropDownDataDisplay("M_Loc","locDataForEmpUser",function (status){
		if(status)
			{
			DropDownDataDisplay("M_Dept","deptDataForEmpUser",function (status){
				if(status)
					{ 
					}});
			}});
					/* DisplayDropDownData("M_Loc","locDataForInterUTR",function (status){
						if(status)
						{
							GetDropDownDataForAssetOwnerAccess('asstTypDataForInterUTR',function (status){
								if(status)
								{
								}});	
							
						}}); */
	$('#IntraUnitTransferRequestDetails').addClass('hideButton');
	
	
});
$(document).ready(function() {
    $('.select2search').select2();
});
/* function ShowRowColumnForIUT(action)
{
	
	if(action.value == 'Non Returnable')
		{
		$('.vendorHideShowForIUT').hide();
		$('.HideLocAndSubLocForIUT').show();
		$('.rowCol').show();
		
		}
	else if(action.value == 'Returnable')
		{
		
		$('.rowCol').hide();
		$('.HideLocAndSubLocForIUT').hide();
		$('.vendorHideShowForIUT').show();
		}
	else
		{
		$('.rowCol').show();
		$('.vendorHideShowForIUT').hide();
		$('.HideLocAndSubLocForIUT').hide();
		}
	
} */

</script>

<!-- <div class="commonDiv" id="displayAssetForIntraUnitTransferRequestSearch">

</div> -->

<div id="DisplayAssetForIntraUnitTransferRequest">
		<div class="card-small">
			
				<div class="card-header new">
					<h3 class="card-title1">Select Location And Sub Location</h3>
				</div>



				<div class="card-body">



<form name="searchFormForInterUTR" id="searchFormForInterUTR">
<!-- <table class="commonTable" align="center" width="600px" height="100%" border="1"> -->
<table class="table table-bordered ">
			<!-- <tr>
				<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:100px">Select Location And Sub Location</p></td>
			</tr> -->
			<tr>
			<td ><b>Location<font color="red">*</font></b></td>
				<td >
					<select id="locDataForEmpUser" name="id_loc" class="form-control select2search" data-valid="mand"  style="width:140" onChange="DisplaySubDropDownData(this,'slocDataForEmpUser','M_Subloc')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr><tr>	
				<td ><b>Sub Location<font color="red">*</font></b></td>
				<td >
					<select id="slocDataForEmpUser" name="id_sloc" class="form-control select2search" data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'buildingDataForEmpUser','M_Building')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
			<td ><b>Building<font color="red">*</font></b></td>
				<td >
					<select id="buildingDataForEmpUser" name="id_building" class="form-control select2search" data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'floorDataForEmpUser','M_Floor')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr><tr>
			<td ><b>Floor<font color="red">*</font></b></td>
				<td >
					<select id="floorDataForEmpUser" name="id_flr" class="form-control select2search" data-valid="mand"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td>
		
				
				</tr>
				<tr>
				<td ><b>Department<font color="red">*</font></b></td>
					<td><select id="deptDataForEmpUser" name="id_dept" class="form-control " data-valid="mand"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td>
				</tr>
			<!-- 	<tr>
				<td ><b>Store<font color="red"> * </font>:</b></td>
				<td >
					<select id="floorDataTransferR" name="id_flr" class="form-control readbaledata" data-valid="mand"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td>
				
				</tr> -->
			<!-- <tr>
				<td><b>Select Location<font color="red"> * </font>:</b></td>
				<td>
					<select id="locDataForInterUTR" name="id_loc" class="form-control" data-valid="mand"  style="width:140" onChange="DisplaySubDropDownData(this,'subLocDataForInterUTR','M_Subloc')">
					</select>
				</td>
			</tr>
			
			<tr>
				<td><b>Select Sub Location<font color="red"> * </font>:</b></td>
				<td>
					<select id="subLocDataForInterUTR"  name="id_sloc" class="form-control" data-valid="mand"  style="width:140">
						<option value="">Select</option>
						
					</select>
					</tr>
				<tr>
				<td><b>Asset Type<font color="red"> * </font>:</b></td>
				<td>
					<select id="asstTypDataForInterUTR"  name="typ_asst" class="form-control" data-valid="mand"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</tr> -->	
				<tr>
					<td colspan="2">
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlInterUnitTransferRequestAsset('Next','displayAssetForIUTSearch','DisplayAssetForIntraUnitTransferRequest','IntraUnitTransferRequestDetails','')">Next</button>
					</td>
				</tr>	
				
		</table>
</form>
</div>
</div>
</div>

<!-- <input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForInterUnitTransferRequest()">

	<table class="table table-bordered DisplayAssetForIntraUnitTransferRequesting">
		<tr class="info">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Serial Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong><a href="#">Transfer </a></strong></td>
		</tr>
	</table> -->
</div>

<div class="card">
<div id="IntraUnitTransferRequestDetails">
<form name="submitTransferRequest" id="submitTransferRequest">
<input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForInterUnitTransferRequest()">

	<table class="table table-bordered DisplayAssetForIntraUnitTransferRequesting">
		<tr class="info new">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Serial Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong>Transfer</strong></td>
		</tr>
	</table>
</form>	
</div>
	<!-- <form name="submitTransferRequest" id="submitTransferRequest">
		<table class="table table-bordered">
				<tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center><strong>Asset Transfer Request Details</strong></center></td>
				</tr>
				<tr>
					<td ><b>Asset Name :</b></td>
					<td ><input type= "text" name="ds_pro" size= "25" value="" readonly></td>
					<td ><b>Asset Description :</b></td>
					<td ><input type= "text"  name="ds_asst"   size= "25" value="" readonly ></td>
				</tr>
				<tr>
					<td ><b>Asset Serial Number :</b></td>
					<td ><input type="text"  name="mfr" size="25" value="" readonly ></td>
					<td ><b>Group:</b></td>
					<td>
					<input type= "text" name="nm_assetdiv" size= "25" value="" readonly>
					
				</td>
				</tr>
				<tr>
				<td ><b>Current Location :</b></td>
					<td ><input type= "text" name="nm_loc" size= "25" value="" readonly></td>
					<td><b>Current SubLocation  :</b></td>
					<td ><input type= "text" name="nm_subl" size= "25" value="" readonly></td>
				</tr>
				
				<tr><td colspan="4"></td></tr>
				<tr>
					<tr>
					<td><b>Transfer Type<font color = 'Red'> * </font> :</b></td>
					<td>
						<select id="TranTypeForValidation" name = "type_tran"  style="width:140" class="form-control" data-valid="mand" onChange="ShowRowColumnForIUT(this , 'HideLocAndSubLocForIUT')">
							<option value = "" > -- Select --</option>
							<option value = "Non Returnable" >Non Returnable</option>
							<option value = "Returnable" >Returnable</option>
						</select>
					</td>
					<td colspan="2" class="rowCol"></td>
					<td class="vendorHideShowForIUT"><b>Vendor <font color = 'Red'>*</font> :</b></td>
					<td class="vendorHideShowForIUT">
					<select id="vendorForIntraUnitTransferRequest" name="id_ven"   style="width:140">
						<option value="">Select</option>
						
					</select>
					</td>	
				</tr>
				<tr class="HideLocAndSubLocForIUT">
				<td ><b>To Location <font color = 'Red'>*</font> :</b></td>
					<td>
					<select id="locationForIntraUnitTransferRequest" name="id_loc_tran"  style="width:140" onChange="DisplaySubDropDownData(this,'subLocationForIntraUnitTransferRequest','M_Subloc')">
						<option value="">Select</option>
						
					</select>
					</td>	
					<td><b>To SubLocation <font color = 'Red'>*</font> :</b></td>
					<td >
					<select id="subLocationForIntraUnitTransferRequest" name="id_subl_tran"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td>	
				</tr>
				
				
				<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" value="">
				<tr>
					<td colspan="4">
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary interUTBTN" onclick="ControlInterUnitTransferRequestAsset('Save','displayAssetForIntraUnitTransferRequestSearch','DisplayAssetForIntraUnitTransferRequest','IntraUnitTransferRequestDetails','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary interUTBTN" onclick="ControlInterUnitTransferRequestAsset('Cancel','displayAssetForIntraUnitTransferRequestSearch','DisplayAssetForIntraUnitTransferRequest','IntraUnitTransferRequestDetails','')">Cancel</button>
					</td>
				</tr>
		</table>
	</form> -->
</div>
















