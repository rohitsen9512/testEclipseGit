<!--Intraunit_transfer_request.jsp-->

<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<script type="text/javascript">

$(function() {
	//DisplayAssetForIntraUnitTransferRequest();
	
	DropDownDataDisplay("M_Loc","locDataForIUTR",function (status){
		if(status)
		{
			GetDropDownDataForAssetOwnerAccess('asstTypDataForIntraUTR',function (status){
				if(status)
				{
				}});	
		}});
	
	$('#IntraUnitTransferRequestDetails').addClass('hideButton');
	$('#DisplayAssetForIntraUnitTransferRequest').addClass('hideButton');
	
});
$(document).ready(function() {
    $('.select2search').select2();
});
</script>

<!-- <div class="commonDiv" id="displayAssetForIntraUnitTransferRequestSearch">
	<input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForIntraUnitTransferRequest()">
</div> -->

<section class="content">
		
<div id="displayAssetForIUTSearch">
<form name="searchFormForIUTR" id="searchFormForIUTR">
<div class="card-small">
<div class="card-header new">
					<h3 class="card-title1">Select Location & Sub Location</h3>
				</div>
				<div class="card-body">
				<table class="table table-bordered ">
							
<!-- <table class="commonTable" align="center" width="600px" height="100%" border="1">

			<tr>
				<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:100px">Select Location And Sub Location</p></td>
			</tr> -->
			
			<tr>
				<td><b>Select Location<font color="red">*</font></b></td>
				<td>
					<select id="locDataForIUTR" name="id_loc" class="form-control select2search" data-valid="mand"  style="width:140" onChange="DisplaySubDropDownData(this,'subLocDataForIUTR','M_Subloc')">
						
						
					</select>
				</td>
			</tr>
			
			<tr>
				<td><b>Select Sub Location<font color="red">*</font></b></td>
				<td>
					<select id="subLocDataForIUTR"  name="id_sloc" class="form-control select2search" data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'buildingDataForEmpUser','M_Building')">
						<option value="">Select</option>
						
					</select>
					</tr>
					<tr>
			<td ><b>Building<font color="red">*</font></b></td>
				<td >
					<select id="buildingDataForEmpUser" name="id_building" class="form-control select2search" data-valid="mand"  style="width:140" >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
				
				<!-- <tr>
				<td><b>Asset Type<font color="red">*</font></b></td>
				<td>
					<select id="asstTypDataForIntraUTR"  name="typ_asst" class="form-control" data-valid="mand"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</tr>	 -->
					<td colspan="2">
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlIntraUnitTransferRequestAsset('Next','displayAssetForIUTSearch','DisplayAssetForIntraUnitTransferRequest','IntraUnitTransferRequestDetails','')">Next</button>
					</td>
				</tr>	
		</table>
		</div>
		</div>
</form>
</div>




</section>

<div class="card">
<div id="DisplayAssetForIntraUnitTransferRequest">
<!-- <input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayAssetForIntraUnitTransferRequest()"> -->
	<table id="DisplayAssetForIntraUnitTransferRequesting"
					class="table table-bordered table-hover DisplayAssetForIntraUnitTransferRequesting">
	<!-- <table class="table table-bordered DisplayAssetForIntraUnitTransferRequesting"> -->
		<tr class="info new">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Serial Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong>Transfer </strong></td>
		</tr>
	</table>
</div>
</div>



<div id="IntraUnitTransferRequestDetails">
	<form name="submitTransferRequest" id="submitTransferRequest">
		<table class="table table-bordered">
				<tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center><strong>Asset Transfer Request Details</strong></center></td>
				</tr>
				<tr>
					<td ><b>Asset Name</b></td>
					<td ><input type= "text" name="ds_pro" size= "25" value="" readonly></td>
					<td ><b>Asset Description</b></td>
					<td ><input type= "text"  name="ds_asst"   size= "25" value="" readonly ></td>
				</tr>
				<tr>
					<td ><b>Asset Serial Number</b></td>
					<td ><input type="text"  name="mfr" size="25" value="" readonly ></td>
					<td ><b>Group</b></td>
					<td>
					<input type= "text" name="nm_assetdiv" size= "25" value="" readonly>
					
				</td>
				</tr>
				<tr><td colspan="4"></td></tr>
				<tr>
					<td ><b>Current Location</b></td>
					<td >
					<input type= "text" name="nm_loc" size= "25" value="" readonly>
					
				</td>
				<td><b>Current SubLocation</b></td>
					<td>
					<input type= "text" name="nm_subl" size= "25" value="" readonly>
					
				</td>
				
				</tr>
				<tr>
					<td><b>Current Facility</b></td>
					<td>
						<input type= "text" name="nm_flr" size= "25" value="" readonly>
					</td>
					<td><b>To Facility<font color = 'Red'>*</font></b></td>
					<td>
						<select id="floorForIntraUnitTransferRequest" name = "id_flr_tran"  style="width:140" class="form-control" data-valid="mand">
							<option value = "" >Select</option>
						</select>
					</td>
				</tr>
				<!-- <tr>
					<td><b>Current Cost Center :</b></td>
					<td>
						<input type= "text" name="nm_cc" size= "25" value="" readonly>
					</td>
					<td><b>To Cost Center <font color = 'Red'> * </font>:</b></td>
					<td>
						<select id="costCenterForIntraUnitTransferRequest" name = "id_cc_tran"  style="width:140" class="form-control" data-valid="mand">
										<option value = "" > -- Select --</option>
						</select>
					</td>
					
				</tr> -->
				<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" value="">
					<input type= "hidden" name="id_loc_tran" size= "25" value="" >
					<input type= "hidden" name="id_subl_tran" size= "25" value="" >
				<tr>
					<td colspan="4">
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary IntraUTR" onclick="ControlIntraUnitTransferRequestAsset('Save','displayAssetForIntraUnitTransferRequestSearch','DisplayAssetForIntraUnitTransferRequest','IntraUnitTransferRequestDetails','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary IntraUTR" onclick="ControlIntraUnitTransferRequestAsset('Cancel','displayAssetForIntraUnitTransferRequestSearch','DisplayAssetForIntraUnitTransferRequest','IntraUnitTransferRequestDetails','')">Cancel</button>
					</td>
				</tr>
		</table>
	</form>
</div>
















