<!--Edit_from_store.jsp-->
<script type ="text/javascript" src="All_Js_File/Asset/A_Edit_From_Store.js"></script>
<script type="text/javascript">
$(function() {
 DisplayDropDownData("M_Emp_User","userForEditFromStore",function (status){
	if(status)
	{
		DisplayDropDownData('M_Currency','currDataForEditFromStore',function (status){
			if(status)
			{
				DropDownDataDisplay('M_Model','modelDataForEditFromStore',function (status){
					if(status)
					{
				DisplayDropDownDataForVendor('M_Vendor','vendorDataForEditFromStore','service',function (status){
					if(status)
					{
						DropDownDataDisplay("M_Loc","locationForEditFromStore1",function (status){
							if(status)
								{
								DisplayDropDownDataForStorage('M_StorageDetail','storageDataForEditToStore',function (status){
									if(status)
									{
									}});
								}});
								}
						});
					}
					});
			}		
		});
	}}); 
setTimeout(function() {
	DisplayAssetForEdit("A_Edit_From_Store");
}, 10);
	$('.insCommonClass1').hide();
	$('.amcCommonClass').hide();
	$('.leaseCommonClass').hide();
	$('.calCommonClass').hide();
	$('#EditDetailsForEditFromStore').hide();
});
</script>
<div class="card">
	<div id="DisplayEditFromStore">
<div class="card-body">
<div class="card">
<table id="displayDataForEditFromStore"
	 class="table table-bordered table-hover displayDataForEditFromStore">
		 <tr class="info new">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Serial Number</strong></td>
			<td><strong>Model Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong>Edit/Install</strong></td>
		</tr>
	</table>
</div>
</div>
</div>
<section class="content">
		<div class="card">
<div id="EditDetailsForEditFromStore">
	<form name="SubmitFormForEditFromStore" id="SubmitFormForEditFromStore">
		<table class="table table-bordered">
				<!-- <tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center><strong>Asset  Details</strong></center></td>
				</tr> -->
					<tr>
						<td ><b>Asset ID <font color="red">*</font></b></td>
						<td ><input type= "text"  name="id_wh_dyn"   size= "25" value="" readonly="readonly" class="form-control" data-valid="mand"></td>
						<td ><b>Asset Name</b></td>
						<td ><input type= "text" name="ds_pro" size= "25" value=""  class="form-control" data-valid="mand"></td>
					</tr>
					<tr>
					<td ><b>Asset Ref. Number</b></td>
				<td ><input type="text"  name="appNo" size="25" value=""  class="form-control" data-valid="mand" ></td>
						<td ><b>Serial Number</b></td>
						<td  ><input type="text"  name="serial_no" size="25" value=""  class="form-control" data-valid="mand" onBlur="return  CheckValWhichAllReadyExit('A_Edit_From_Store' , 'Serial number already exist.' , 'serial_no');"></td>
					</tr>
					<tr>
				<td class="device_status1"><b>Device Status</b></td> 
						<td style="display:none" class="device_status1"><input type="text"  name="device_status1"   class="form-control" readonly></td>
				<td class="device_status"><b>Device Status</b></td>
					<td class="device_status">
						<select  name = "device_status" class="form-control"   data-valid="mand"    >
								<!-- <option value = "temporary" >Temporary</option>
								<option value = "permanent" >Permanent</option> -->
								 <option hidden value = "allct_to_emp" >Allocated to employee Permanent</option>
								<option hidden value = "allct_to_emp_temp" >Allocated to employee Temporary</option> 
								<option hidden value = "link_to_asset" >Linked to Asset</option> 
								<option value = "under_service" >Under service </option>
								<option value = "not_workin" >Not Working </option>
								<option value = "in_store" >In Store </option>
								<option value = "temporary_laptop" >Temporary Laptop</option>
								<option value = "buyback" >Buy back </option>
								<option value = "physical_dmg_mjr" >Physical Damage (Major)</option>
								<option value = "physical_dmg_mnr" >Physical Damage (Minor) </option>		
								<option value = "scraped" >Scrapped / disposed </option>
								<option value = "advance_laptop" >Advance Laptop </option>
								<option value = "refreshment_working" >Refreshment - working </option>
								<option value = "refreshment_faulty" >Refreshment - Faulty </option>
						</select>
					</td>
				<td id="process1" style="display:none;"><strong>Process type</strong></td>
				<td id="process2" style="display:none;"><input type="text" name="process_typ" value=""   class="form-control " ></td>
			</tr>
			<tr id="storage_ram" style="display:none;">
				<td ><strong>Storage Type</strong></td>
				<td><input  type="text" name="storeage_typ" value=""    class="form-control" ></td>
				<td><strong>RAM Type</strong></td>
				<td><input type="text" name="ram_typ" value=""    class="form-control " ></td>
			</tr>
				<tr>
				<td><strong>Asset Remarks</strong></td>
				<td><input type="text" name="rmk_asst" class="form-control" value=""></td>
				<td><strong>Invoice Number</strong></td>
				<td><input  type="text" name="no_inv" value="" readonly="readonly"  class="form-control" data-valid="mand"></td>
				</tr>
				<tr>
				<td><strong>Invoice Date</strong></td>
				<td><input type="text" name="dt_inv" value=""  disabled="true" class="form-control grndatepicker" data-valid="mand"></td>
				<td><strong>PO Number</strong></td>
				<td><input type="text" name="no_po" value="" readonly="readonly" class="form-control" data-valid="mand"></td>
			</tr>
			<tr>
					<td><strong>PO Date</strong></td>
				<td><input type="text" name="dt_po" value="" disabled="true" class="form-control grndatepicker" data-valid="mand"></td>
				<td ><b>Service Vendor</b></td>
				<td >
					<select id="vendorDataForEditFromStore" name="id_service_ven"class="form-control"  style="width:140" >
						<option value="">Select</option>
					</select>
			</tr>
			<tr>
				</td>
				<td><strong>Asset Description</strong></td>
				<td><input type="text" name="ds_asst" class="form-control" value=""></td>
				<td ><b>Taggable<font color="red">*</font></b></td>
				<td >
					<select id="tag" name="tag"  style="width:140" class="form-control" data-valid="mand">
						<option value="">Select</option>
						<option value="Yes">Yes</option>
						<option value="No">No</option>
					</select>
				</td>
			</tr>
			<tr>
				<td ><b>Type of Procurement<font color="red">*</font></b></td>
				<td >
					<select  name="typ_proc" class="form-control" data-valid="mand"  style="width:140">
						<option value="" >Select</option>
						<option value="OP">Outright Purchase</option>
						<option value="LB">Loan Basis</option>
						<option value="FOC">Add-On</option>
					</select>
				</td>
					<td ><b>AMC / Warranty</b></td>
				<td >
					<select id="warr_amc" name="warr_amc"  style="width:140" class="form-control" data-valid="mand" onChange="ShowRowColumn(this , 'amcCommonClass')">
						<option value="O">NO</option>
						<option value="A">AMC</option>
						<option value="W">Warranty</option>
					</select>
				</td>
			</tr>
			<tr>
			</tr>
			<tr>
				<td ><b>Lease Status<font color="red">*</font></b></td>
				<td >
					<select id="st_lease" name="st_lease" class="form-control" data-valid="mand"  style="width:140" onChange="ShowRowColumn(this , 'leaseCommonClass')">
						<option value="NUL">Not under lease</option>
						<option value="UL">Under lease</option>
					</select>
				</td>
				<td><strong>Additional Cost</strong></td>
				<td><input type="text" name="cst_asst_add" value="0" class="form-control" data-valid="num" onBlur="return CalcTotPriceEditFromStore()"></td>
			</tr>
			<tr class="aa leaseCommonClass">
				<td ><b>Lease Start Date <font color="red">*</font></b></td>
				<td ><input type="text" id="leasecYeardatepicker" name="std_lease" value="" class="form-control leaseCommonClass leaseCommonClass1 grndatepicker"></td>
				<td><b>Lease End Date<font color="red">*</font></b></td>
				<td><input type="text" name="endt_lease" value="" class="form-control leaseCommonClass leaseCommonClass1 leasedatepicker"></td>
			</tr>
			<!-- hide and show for AMC / Warranty -->
			<tr class="amcCommonClass">
			<td><b>AMC/Warranty Start Date<font color="red">*</font></b></td>
			<td><input type="text" id="amcYeardatepicker" name="dt_amc_start" value="" class="form-control common-check"></td>
			<td><b>AMC/Warranty End Date<font color="red">*</font></b></td>
			<td><input type="text" name="dt_amc_exp" value="" class="form-control amcCommonClass1 amctDatepicker"></td>
			</tr>
			<tr>
				<td><strong>Total Unit Price</strong></td>
				<td><input type="text" name="cst_asst" value="" readonly="readonly" class="form-control" data-valid="num"></td>
				<td><strong>Net Value</strong></td>
				<td><input type="text" name="val_asst" value="" readonly="readonly" class="form-control" data-valid="num"></td>
			</tr>
			<tr style="display:none;">
			<td><strong>Model No. </strong></td>
				<td><input  type="text" name="mdl_num" value="NA"    class="form-control" ></td>
			</tr>
			<tr>
				<input type="hidden" name="id" value="">
				<input type="hidden" name="action" value="Update">
				<tr>
					<td colspan="4">
						<button name="update" type="button" style="margin-left:450px;"   class="btn btn-primary edits" onclick="ControlEditFromStore('Update','EditDetailsForEditFromStore','SearchFromForEditFromstore','DisplayEditFromStore','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary edits" onclick="ControlEditFromStore('Cancel','EditDetailsForEditFromStore','SearchFromForEditFromstore','DisplayEditFromStore','')">Back</button>
					</td>
				</tr>
		</table>
	</form>
	 <table class="table table-bordered displayTrackHistory">
	</table>
	</div>
	</div>
	</section>
</div>
