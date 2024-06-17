
<script type="text/javascript">
DisplayData('M_Vendor','displayVendor','createVendor');
$(function() {
	
	$( ".datepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	});
	
	$('button[name="update"]').addClass('hideButton');
	
	
	
});
$('#downloadmsme').click(function(e) {
	
	
    e.preventDefault();  //stop the browser from following
    window.location.href = 'InvoiceScanFile/MSME_Declaration.docx';
	
});
$('#downloadtds').click(function(e) {
	
	
    e.preventDefault();  //stop the browser from following
    window.location.href = 'InvoiceScanFile/TDS_Letter.DOCX';
	
});
function openSendLinkDiv()
{
	//alert("send ");
	$("#sendLink").show();
	//$("#displayVendor").hide();
	}
function ControlDiv1(action){
	 if(action=='send')
{
	alert("link send successfully...")
	$( ".vendorMaster" ).trigger( "click" );
}
	if(action=='cancel')
	$( ".vendorMaster" ).trigger( "click" );
}

/* DisplayDropDownDataForGroup("M_Asset_Div","groupDataForVendor","CapG",function (status){
	if(status)
		{
		
		}}); */
jQuery("input#file4ID1").change(function () {
	
	 var formData = new FormData();	 
		formData.append('file', $('#file4ID1').get(0).files[0]);
		
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
			    	
			    		$('input[name="file_name1"]').val(r.upload_inv);
			    		//bootbox.alert("File Uploaded successfully");
			    }
			},'json'); 
			
	//$( "#submitForUploadData" ).trigger( "click" );
}); 
jQuery("input#file4ID2").change(function () {
	
	 var formData = new FormData();	 
		formData.append('file', $('#file4ID2').get(0).files[0]);
		
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
			    	
			    		$('input[name="file_name2"]').val(r.upload_inv);
			    		//bootbox.alert("File Uploaded successfully");
			    }
			},'json'); 
			
	//$( "#submitForUploadData" ).trigger( "click" );
}); 
jQuery("input#file4ID3").change(function () {
	
	 var formData = new FormData();	 
		formData.append('file', $('#file4ID3').get(0).files[0]);
		
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
			    	
			    		$('input[name="file_name3"]').val(r.upload_inv);
			    		//bootbox.alert("File Uploaded successfully");
			    }
			},'json'); 
			
	//$( "#submitForUploadData" ).trigger( "click" );
}); 
jQuery("input#file4ID4").change(function () {
	
	 var formData = new FormData();	 
		formData.append('file', $('#file4ID4').get(0).files[0]);
		
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
			    	
			    		$('input[name="file_name4"]').val(r.upload_inv);
			    		//bootbox.alert("File Uploaded successfully");
			    }
			},'json'); 
			
	//$( "#submitForUploadData" ).trigger( "click" );
}); 


	
	function mailtoVendor(){
		$('.mailbutton').attr('disabled','disabled');
		var mail_ven_to = $('input[name="mail_ven_to').val();
		var currentUrl = window.location.href;
		console.log(currentUrl);
		var checkmail = mail_ven_to.includes("@");
		
		
		 if(mail_ven_to != '' && checkmail==true) {
		 $.post('M_Vendor',{action : 'SendMailTOVendor', id:mail_ven_to,currentUrl:currentUrl},function (r){
			
				
					if(r.data=='0'){
						bootbox.alert("Email Has been sent to vendor");
						$( ".vendorMaster" ).trigger( "click" );
						 $('.mailbutton').removeAttr('disabled');
					}
					else{
						bootbox.alert("Try Again");
						 
					}
					
				
		 },'json'); 
		 }
		 
		 else{
			 $('.mailbutton').removeAttr('disabled');
			 bootbox.alert("Invalid Email ID.");
			 
		 }
		 
		
	}
</script>





<!-- Content Wrapper. Contains page content -->
  
    <!-- Content Header (Page header) -->
    <!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1><!--  Vendor--> 
					<button type="button" name="create btn"   class="btn btn-primary" onclick="ControlDiv('Create','displayVendor','createVendor','submitVendor','M_Vendor')">Create Vendor</button>
					</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Master</a></li>
              <li class="breadcrumb-item">Vendor</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

<div class="card">

		<div id="displayVendor">
			<div class="card-body">
				<table id="vendorForDisplay"
					class="table table-bordered table-hover vendorForDisplay">
					<thead>

		
					
						<tr class="new">
							<td><strong>Vendor Name</strong></td>
							<td><strong>Vendor Code</strong></td>
							<td><strong>Type Of Vendor</strong></td>
							<td><strong>Contact Person Name</strong></td>
							<td><strong>Phone Number</strong></td>
							<td><strong>Email ID</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
						</thead>
					</table>
				</div></div>
	</div>
	
	<section class="content">
		<div class="card">
			<div id="createVendor" style="display:none;">
				<div class="card-header new">
					<h3 class="card-title1">Vendor Details</h3>
				</div>



				<div class="card-body">
	
	
	
	
	<form name="submitVendor" id="submitVendor">
	<table class="table table-bordered ">
		
		<tr>
			<td><b>Vendor Name<font color="red">*</font></b></td>
			<td ><input id="nm_vendorId" type="text"  name="nm_ven" class="form-control" data-valid="mand" autofocus="" value=""></td>
			<td ><b>Vendor Code<font color="red">*</font></b></td>
			<td ><input id="cd_vendorId" type="text"  name="cd_ven"  value=""  class="form-control" data-valid="mand" readonly></td>
		</tr>
		<tr>
			<td ><b>Address 1<font color="red">*</font></b></td>
			<td ><input type="text"  name="add1" value=""  class="form-control" data-valid="mand" ></td>

			<td ><b>Address 2</b></td>

			<td ><input type="text"  name="add2" value="" class="form-control" ></td>

			
		</tr>
		<tr>
			<td ><b>Address3</b></td>
			<td ><input type="text"  name="add3" value=""  class="form-control" ></td>
			<!-- <td><b>Type Of Vendor<font color="red">* </font>:</b></td>
			<td>
				<input type ="checkbox" name="procured" value="Procured" ><b>Procured </b>
				<input type ="checkbox" name="service" value="Service" ><b>Service </b>
			
			</td> -->
			<td ><b>City<font color="red">*</font></b></td>
			<td ><input type="text"  name="city" value="" class="form-control" data-valid="mand" ></td>

			
		</tr>
		<tr>
			<td ><b>State<font color="red">*</font></b></td>

			<td ><input type="text"  name="state" value=""  class="form-control" data-valid="mand" ></td>

			<td ><b>Postal Code<font color="red">*</font></b></td>

			<td ><input type="text"  name="pin" value="" class="form-control" data-valid="mand"></td>

			
		</tr>
		<tr>
			<td ><b>Country<font color="red">*</font></b></td>

			<td ><input type="text"  name="country" value=""  class="form-control" data-valid="mand" ></td>

			<td ><b>Telephone<font color="red">*</font></b></td>

			<td ><input type="text"  name="phone" value="" class="form-control" data-valid="num" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" maxlength="10" ></td>

		</tr>
		<tr>
       		<td ><b>Mobile No<font color="red">*</font></b></td>

			<td ><input type="text"  name="ct_mob" value="" class="form-control" data-valid="num" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" maxlength="10"></td>
			<td ><b>Email ID<font color="red">*</font></b></td>

    		<td ><input type="text"  name="mailid" value="" class="form-control" data-valid="mand" pattern="/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/" required></td>
    		</tr>
		<tr>
		    <td ><b>GST No.<font color="red">*</font></b></td>

			<td ><input type="text"  name="fax" value=""  class="form-control" data-valid="mand"></td>
		    
			<td ><b>TIN Number<font color="red">*</font> </b></td>

      		<td ><input type="text"  name="tin"  class="form-control" data-valid="mand"></td>
			
			
		</tr>
		<tr>
			<td ><b>CIN Number<font color="red">*</font></b></td>

			<td ><input type="text"  name="kst" value=""  class="form-control" data-valid="mand"></td>

			
			<td ><b>MSME Number<font color="red">*</font></b></td>

			<td ><input type="text"  name="cst"  value="" class="form-control" data-valid="mand"></td>

			
		</tr>
		<tr>
			<td ><b>PAN Number<font color="red">*</font></b></td>

			<td ><input type="text"  name="pan"  value=""
			 class="form-control" data-valid="mand"></td>

			
			<td ><b>TAN<font color="red">*</font></b></td>

			<td ><input type="text"  name="servicetax" value=""  class="form-control" data-valid="mand"></td>

			
		</tr>
		
		<tr>
			<td ><b>Nature Of Business<font color="red">*</font></b></td>

			<td ><input type="text"  name="nature_bus"  value=""
			 class="form-control" data-valid="mand"></td>

			
			<td ><b>Year of Inc./ in business since<font color="red">*</font></b></td>

			<td ><input type="text"  name="bus_yr" value=""  class="form-control" data-valid="num" data-valid="mand"></td>

			
		</tr>
		<tr>
			<td ><b>Payment Terms<font color="red">*</font></b></td>

			<td ><input type="text"  name="pay_terms"  value=""
			 class="form-control" data-valid="mand" ></td>

			
			<td ><b>Invoice and Payment Currency<font color="red">*</font></b></td>

			<td ><input type="text"  name="pay_curr" value=""  class="form-control" data-valid="mand"></td>

			
		</tr>
		<!-- <tr>
				<td><strong>Group<font color="red">*</font></strong></td>
				
				<td>	
				<select id="groupDataForVendor" class="form-control" name="id_grp" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subgroupDataForVendor','M_Subasset_Div')">
						<option value="">Select</option>
						
					</select>
					
				</td>
			<td><strong> Sub Group<font color="red">*</font></strong></td>
				<td>
					<select multiple="multiple" id="subgroupDataForVendor" class="form-control" name="selected_id_sgroup" data-valid="mand">
						
						
					</select>
				</td>	
				
		</tr> -->
		<tr>
 			<td colspan="4" style="background-color:#3f6791;"><p style="font-size: 28px;background-color:#3f6791;color: white;margin-left: 300px;">Each Certificate Have 25 Score</p></td>
 		</tr>
 		
		<tr> 
		<td><b>MSME Declaration</b></td>
				<td><input id="file4ID1" type="file" name="file1" class="form-control" value="" ><h5 style="text">Download  Format For MSME Declaration <a id="downloadmsme" href="" >Click To Download</a></h5></td>
				
				<td><b>TDS-Letter for ITR-206AB</b></td>
				<td><input id="file4ID2" type="file" name="file2" class="form-control" value="" ><h5 style="text">Download  Format For TDS-Letter for ITR-206AB <a id="downloadtds" href="" >Click To Download</a></h5></td>
		</tr>
		<tr>
		<td><b>Start Date /</b><b>End Date</b></td>
		<td><input type="text" name="dt_exp_dlvr" value="" class="form-control readbaledata datepicker">
		 <input type="text" name="dt_exp_dlvr" value="" class="form-control readbaledata datepicker">
		</td>
		<td><b>Start Date /</b><b>End Date</b></td>
		<td><input type="text" name="dt_exp_dlvr" value="" class="form-control readbaledata datepicker">
		 <input type="text" name="dt_exp_dlvr" value="" class="form-control readbaledata datepicker">
		</td>
		</tr>
		
		<tr>
		<td><b>CMMI Level 5</b></td>
				<td><input id="file4ID3" type="file" name="file3" class="form-control" value="" ></td>
				<td><b>ISO 9000 Certificate</b></td>
				<td><input id="file4ID4" type="file" name="file4" class="form-control" value="" ></td>
		</tr>
		<tr>
		<td><b>Start Date /</b><b>End Date</b></td>
		<td><input type="text" name="dt_exp_dlvr" value="" class="form-control readbaledata datepicker"  >
		 <input type="text" name="dt_exp_dlvr" value="" class="form-control readbaledata datepicker"  >
		</td>
		<td><b>Start Date /</b><b>End Date</b></td>
		<td><input type="text" name="dt_exp_dlvr" value="" class="form-control readbaledata datepicker" >
		 <input type="text" name="dt_exp_dlvr" value="" class="form-control readbaledata datepicker"  >
		</td>
		</tr>
		
    
    	<tr>
 			<td colspan="4" style="background-color: #3f6791;"><p style="font-size: 28px;background-color: #3f6791;color: white;margin-left: 300px;">Bank Account Details</p></td>
 		</tr>
 		<tr>
			<td  ><b>IFSC Code<font color="red">*</font></b></td>

			<td ><input type="text"  name="ifsc" value="" class="form-control" data-valid="mand"></td>
			<td  ><b>Account Number<font color="red">*</font></b></td>

			<td ><input type="text"  name="acc_no" value="" class="form-control" data-valid="mand"></td>
			
		</tr>
		<tr>
			<td  ><b>Name on Account<font color="red">*</font> </b></td>

			<td ><input type="text"  name="nm_account" value="" class="form-control" data-valid="mand"></td>
			<td  ><b>Bank Name<font color="red">*</font></b></td>

			<td ><input type="text"  name="nm_bank" value="" class="form-control" data-valid="mand"></td>
			
		</tr>
		<tr>
			<td  ><b>Bank Branch</b></td>

			<td ><input type="text"  name="nm_branch" value="" class="form-control" ></td>
			<td  ><b>MICR Code<font color="red">*</font> </b></td>

			<td ><input type="text"  name="micr_code" value="" class="form-control" data-valid="mand"></td>
			
		</tr>
		<tr>
			<td  ><b>Address<font color="red">*</font></b></td>

			<td ><input type="text"  name="add_bank" value="" class="form-control" data-valid="mand"></td>
			<td  ><b>Country<font color="red">*</font></b></td>

			<td ><input type="text"  name="country_bank" value="" class="form-control" data-valid="mand"></td>
			
		</tr>
		<tr>
			<td  ><b>City<font color="red">*</font></b></td>

			<td ><input type="text"  name="city_bank" value="" class="form-control" data-valid="mand"></td>
			<td  ><b>State<font color="red">*</font></b></td>

			<td ><input type="text"  name="state_bank" value="" class="form-control" data-valid="mand"></td>
			
		</tr>
		<tr>
			<td  ><b>Postal Code<font color="red">*</font></b></td>

			<td ><input type="text"  name="postal_code_bank" value="" class="form-control" data-valid="mand"></td>
			<td  ><b>Telephone number<font color="red">*</font></b></td>

			<td ><input type="text"  name="telephone_bank" value="" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" class="form-control" data-valid="mand" maxlength="10"></td>
			
		</tr>
		<tr>
			<td  ><b>Fax Number<font color="red">*</font></b></td>

			<td colspan="3"><input type="text"  name="fax_bank" value="" class="form-control" data-valid="mand"></td>
			
			
		</tr>
		<tr>
			<td  colspan="4" width="50%" style="background-color: #3f6791;"><p style="font-size: 28px; background-color: #3f6791;color: white;margin-left:300px;">Details of Contact Person</p></td>
			
		</tr>
		<tr>
			<td  ><b>Name</b></td>

			<td ><input type="text"  name="nm_contact" value="" class="form-control" ></td>
			<td ><b>Designation</b></td>

			<td ><input type="text"  name="ct_desig" value="" class="form-control"></td>
			
		</tr>
		
		<tr>
		
			<td ><b>Phone Number</b></td>
			<td ><input type="text"  name="ct_phone" value="" class="form-control" ></td>
			<td ><b>Email ID<font color="red">*</font></b></td>

    		<td ><input type="text"  name="ct_mailid" value="" class="form-control" data-valid="mand" pattern="/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/" required></td>
		</tr>
		
		<tr>
       		
			
    		<td ><b>Password</b></td>
			<td colspan="3"><input type="text"  name="password"  value="" class="form-control"></td>
			
				
		</tr>
		
		<tr>
       		
			<td ><b>Company URL</b></td>
			<td colspan="3"><input type="text"  name="url"  value="" class="form-control"></td>
			
					<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" id="id" value="0">
					<input type="hidden" name="file_name1" value="" class="form-control">
					<input type="hidden" name="file_name2" value="" class="form-control">
					<input type="hidden" name="file_name3" value="" class="form-control">
					<input type="hidden" name="file_name4" value="" class="form-control">
		</tr>

		<tr>
			<td  colspan="4">
				<button name="save" type="button" style="margin-left:450px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayVendor','createVendor','submitVendor','M_Vendor','Vendor name already exist.,,Vendor code already exist.' , 'nm_ven,,cd_ven');">Save</button>
				<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayVendor','createVendor','submitVendor','M_Vendor','' , '')">Update</button>
				<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayVendor','createVendor','submitVendor','M_Vendor');">Back</button>
				<button name="delete" type="button" class="btn btn-primary" onclick="DeleteFun('M_Vendor','displayVendor','createVendor',document.getElementById('id').value)">Delete</button>	
			
			</td>
		</tr>

		</table>
	</form>
	</div>
	
	</div>
	</div>
	</section>
	
	