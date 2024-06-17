<!--Edit_from_store.jsp-->
<script type ="text/javascript" src="All_Js_File/Asset/Damage_asset.js"></script>
<script type="text/javascript">
$(function() {
	    $("#fileID").change(function () {
	   	 var formData = new FormData();	 
	   		formData.append('file', $("#fileID").get(0).files[0]);
	   			$.ajax({
	   			  url: 'Upload_File',
	   			    type: 'POST',
	   			    data: formData,
	   			    async: false,
	   			    cache: false,
	   			    contentType: false,
	   			    dataType: "json",
	   			    processData: false,
	   			    mimeType: "multipart/form-data",
	   			    success: function (r) {
	   			    		$('input[name="upload_po"]').val(r.upload_inv);
	   			     }
	   			},'json'); 
	   });  	    
	    $("#file4ID").change(function () {
		   	 var formData = new FormData();	 
		   		formData.append('file', $("#file4ID").get(0).files[0]);
		   			$.ajax({
		   			  url: 'Upload_File',
		   			    type: 'POST',
		   			    data: formData,
		   			    async: false,
		   			    cache: false,
		   			    contentType: false,
		   			    dataType: "json",
		   			    processData: false,
		   			    mimeType: "multipart/form-data",
		   			    success: function (r) {
		   			    		$('input[name="file_name"]').val(r.upload_inv);
		   			     }
		   			},'json'); 
		  });
	    document.getElementById("file4ID").onchange = function () {
		    var reader = new FileReader();
		    reader.onload = function (e) {
		        // get loaded data and render thumbnail.
		        document.getElementById("image").src = e.target.result;
		    };
		    // read the image file as a data URL.
		    reader.readAsDataURL(this.files[0]);
		};
setTimeout(function() {
	DisplayAssetForEdit("A_Damage_Asset");
}, 10);
$('.insCommonClass1').hide();
	$('.amcCommonClass').hide();
	$('.leaseCommonClass').hide();
	$('.calCommonClass').hide();
	$('#EditDetailsForEditFromStore').hide();
});
</script>
<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!--  Damage Asset -->
						</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Asset</a></li>
						<li class="breadcrumb-item">Damage Asset</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
<div class="card">
		<div id="DisplayEditFromStore">
			<div class="card-body">
				<table id="displayDataForEditFromStore"	class="table table-bordered displayDataForEditFromStore">
	</table>
</div>
</div>
</div>
<div id="EditDetailsForEditFromStore" class="card">
	<form name="SubmitFormForEditFromStore" id="SubmitFormForEditFromStore">
		<table class="table table-bordered">
				<!-- <tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center><strong>Asset  Details</strong></center></td>
				</tr> -->
					<tr>
						<td ><b>Asset ID</b></td>
						<td ><input type= "text"  name="id_wh_dyn"   size= "25" value="" readonly="readonly" class="form-control" data-valid="mand"></td>
						<td ><b>Asset Name</b></td>
						<td ><input type= "text" name="ds_pro" size= "25" value="" readonly="readonly" class="form-control" data-valid="mand"></td>
					</tr>
					<tr>
						<td ><b>Serial No.</b></td>
						<td style="display:none"><input type="text"  name="no_mfr" size="25" value=""  class="form-control" data-valid="mand" ></td>
					<!-- 	<td ><b>AssetTag No :</b></td> -->
						<td  ><input type="text"  name="serial_no" size="25" value=""  class="form-control" data-valid="mand" onBlur="return  CheckValWhichAllReadyExit('A_Damage_Asset' , 'Serial number already exist.' , 'serial_no');"></td>
						<td class="device_status"><b>Device Status</b></td>
					<td class="device_status">
						<select  name = "device_status" class="form-control"   data-valid="mand"  readonly  >
								<!-- <option value = "temporary" >Temporary</option>
								<option value = "permanent" >Permanent</option> -->
								<!-- <option value = "allct_to_emp" >Allocated to employee Permanent</option>
								<option value = "allct_to_emp_temp" >Allocated to employee Temporary</option> -->
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
					<td class="device_status1"><b>Device Status</b></td>
						<td style="display:none" class="device_status1"><input type="text"  name="device_status1"   class="form-control" readonly></td>
					</tr>
				<tr>
				<td><strong>Model No.</strong></td>
				<td><input  type="text" name="mdl_num" value=""    class="form-control" ></td>
				<td><strong>Process Type</strong></td>
				<td><input type="text" name="process_typ" value=""   class="form-control " ></td>
			</tr>
			<tr>
				<td><strong>Storage Type</strong></td>
				<td><input  type="text" name="storeage_typ" value=""    class="form-control" ></td>
				<td><strong>RAM Type</strong></td>
				<td><input type="text" name="ram_typ" value=""    class="form-control " ></td>
			</tr>
				<tr>
				<td><strong>Invoice Number</strong></td>
				<td><input  type="text" name="no_inv" value="" readonly="readonly"  class="form-control" data-valid="mand"></td>
				<td><strong>Invoice Date</strong></td>
				<td><input type="text" name="dt_inv" value=""  disabled="true" class="form-control grndatepicker" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>PO Number</strong></td>
				<td><input type="text" name="no_po" value="" readonly="readonly" class="form-control" data-valid="mand"></td>
				<td><strong>PO Date</strong></td>
				<td><input type="text" name="dt_po" value="" disabled="true" class="form-control grndatepicker" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Attach Image<font color="red">*</font></strong></td>
				<td>
				<img id="image"  style="min-width: 150px;
    min-height: 100px;
    max-width: 150px;
    max-height: 100px;border: 2px solid #555;margin-right: 20px; border-radius: 10px;"/>
				<input id="file4ID" type="file" name="file4" class="form-control" value="" data-valid="mand">
				</td>
				<td><strong>Approx. Cost of Repairing<font color="red">*</font></strong></td>
				<td><input type="text" name="repair_cost" value=""  class="form-control" data-valid="mand"></td>
			</tr>
			<tr>
			<td ><b>Upload Supporting Document</b></td>
				<td><input style="width: 250px;" id="fileID" type="file" name="file1" class="form-control " value="" >
				<a id="download_po"  href="#" download ></a></td>
			</tr>
				<input type="hidden" name="id" value="">
				<input type="hidden" name="action" value="Update">
				 <input type="hidden" name="file_name" value="" class="form-control">
				  <input type="hidden" name="upload_po" value="" >
				<tr>
					<td colspan="4">
						<button name="update" type="button" style="margin-left:450px;"   class="btn btn-primary edits" onclick="ControlEditFromStore('Update','EditDetailsForEditFromStore','SearchFromForEditFromstore','DisplayEditFromStore','')">Send For Approval</button>
						<button name="cancel" type="button"  class="btn btn-primary edits" onclick="ControlEditFromStore('Cancel','EditDetailsForEditFromStore','SearchFromForEditFromstore','DisplayEditFromStore','')">Cancel</button>
					</td>
				</tr>
		</table>
	</form>
</div>
</div>
