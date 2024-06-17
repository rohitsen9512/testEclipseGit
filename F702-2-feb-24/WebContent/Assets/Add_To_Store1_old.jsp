<!--Create_Grn.jsp-->

<div class="commonDiv" style="padding:10px;">
	<!--   <a href="#"> Assets > </a><a href="#"> Add To Store</a> -->
</div>
<!-- <b style="display:none;padding-left:50%;" id="browseFile">
 (In .xls Formate )<input type="file"  name="file" id="fileID"> 
</b>		

</div>
 -->
<script>

$(function (){
	


		jQuery("#fileID").change(function () {
			
			 var formData = new FormData();	 
				//formData.append("files[]", $conv(this).prop("files")[0]);
				formData.append('file', $('#fileID').get(0).files[0]);
				
					$.ajax({
					  url: 'Upload_Delete_File',
					    type: 'POST',
					    data: formData,
					    async: false,
					    cache: false,
					    contentType: false,
					    dataType: "json",
					    processData: false,
					    mimeType: "multipart/form-data",
					    success: function (r) {
					    	
					    		//$('input[name="upload_inv"]').val(r.upload_inv);

					    		$.post('ReadExcel',{filePath : r.upload_inv},function (r)
										{
					    			if(r.data != 2)
					    				{
											if(r.data.length > 0)
												{
												for(var i = 0; i < r.data.length ; i++)
													{
													for (var key in r.data[i])
														{
															$('input[name='+key+']').val(r.data[i][key]);
														}
													}
												for(var i = 0; i < r.sapNo.length ; i++)
												{
												for (var key in r.sapNo[i])
													{
														$('input[name='+key+']').val(r.sapNo[i][key]);
													}
												}
												}
											else
												{
													alert('Please check the uploaded file it was empty.');
												}
											
					    				}
					    			else
					    				{
					    					alert('Please check the uploaded file row should not be empty.');
					    				}
										},'json');
					    }
					},'json'); 
					
			//$( "#submitForUploadData" ).trigger( "click" );
					
		}); 
	
	
	$('#serialNo').hide();
	$('#AddToStore').hide();
	$('.leaseAddToStoreCommonClass').hide();
	$('.amcAddToStoreCommonClass').hide();
	$('.insAddToStoreCommonClass').hide();
	$('.calAddToStoreCommonClass').hide();
	
	
	$( ".DCDatepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function(selected,evnt) {
	    	  $(this).removeClass('error');
	    	  var dt_inv=$('input[name="dt_inv"]').val();
	    	  var dt_inv1=$('input[name="dt_inv"]').val();
	    	  var dt_grn = $(this).val();
	    	  
	    	  var temp_strt = dt_inv.split("/");
			  var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
					
				var temp_end = dt_grn.split("/");
				var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
					
				dt_inv = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
				dt_grn = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
	    	  
	    		  if(dt_inv > dt_grn)
	    			  {
	    			  	alert('DC date should be greater or equal to Invoice date : '+dt_inv1);
	    			  	$(this).focus();
	    			  	$(this).val('');
	    			  	$(this).addClass('error');
	    			  	exit(0);
	    			  }
	    		  
	    		  
	    	  
	      }
	    });

	
var currentDate = new Date();
	
	$('.grndatepicker').each(function () {
        
            $(this).removeClass('hasDatepicker');
            
    });
	$('.grndatepicker').datepicker({
		yearRange: '1985:2025',  
		changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	    });
	$('input[name="dt_to"]').datepicker("setDate", currentDate);
	currentDate.setMonth(currentDate.getMonth() - 1);
	$('input[name="dt_frm"]').datepicker("setDate", currentDate);
	
	
	DisplayInvoiceForAddToStore('A_Add_To_Store1','displayInvoiceForStore');
	 DisplayDropDownData('M_Currency','currDataForAddToStore',function (status){
			if(status)
			{ 
				DropDownDataDisplay('M_Model','modelDataForAddToStore',function (status){
					if(status)
					{ 
				
						DisplayDropDownDataForVendor('M_Vendor','vendorDataForAddToStore','service',function (status){
							if(status)
							{
								DisplayDropDownData('typAssetForInvoice',function (status){
									if(status)
									{
											DisplayDropDownDataForStorage('M_StorageDetail','storageDataForAddToStore',function (status){
											if(status)
											{
											}});	 
									}});	
							}});
							}
								});
							
					
			 }
			}); 
	 
	 $( ".amctDatepicker" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      onSelect: function(selected,evnt) {
		    	  $(this).removeClass('error');
		    	  $('input[name="dt_amc_start"]').removeClass('error');
		    	  var dt_amc_start=$('input[name="dt_amc_start"]').val();
		    	  var dt_amc_start1=$('input[name="dt_amc_start"]').val();
		    	  var dt_end = $(this).val();
		    	  if(dt_amc_start == '')
		    		  {
		    		  	alert('First filled start  date.');
		    		  	$('input[name="dt_amc_start"]').focus();
		    		  	$('input[name="dt_amc_start"]').addClass('error');
		    		  	$('input[name="dt_amc_start"]').val('');
		    		  	$(this).val('');
		    		  	exit(0);
		    		  }
		    	  else
		    		  {
		    		  
		    		  var temp_strt = dt_amc_start.split("/");
					  var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
							
						var temp_end = dt_end.split("/");
						var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
							
						dt_amc_start = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
						dt_end = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
		    		  
		    		  if(dt_amc_start > dt_end)
		    			  {
		    			  	alert('End date should be greater or equal to start date : '+dt_amc_start1);
		    			  	$(this).focus();
		    			  	$(this).val('');
		    			  	$(this).addClass('error');
		    			  	exit(0);
		    			  }
		    		  
		    		  }
		    	  
		      }
		    });
	
	 $( ".leasedatepicker" ).datepicker({
			yearRange: '1985:2025',
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      onSelect: function(selected,evnt) {
		    	  $(this).removeClass('error');
		    	  $('input[name="std_lease"]').removeClass('error');
		    	  var std_lease=$('input[name="std_lease"]').val();
		    	  var std_lease1=$('input[name="std_lease"]').val();
		    	  var dt_end = $(this).val();
		    	  if(std_lease == '')
		    		  {
		    		  	alert('First filled start  date.');
		    		  	$('input[name="std_lease"]').focus();
		    		  	$('input[name="std_lease"]').addClass('error');
		    		  	$('input[name="std_lease"]').val('');
		    		  	$(this).val('');
		    		  	exit(0);
		    		  }
		    	  else
		    		  {
		    		  
		    		  var temp_strt = std_lease.split("/");
					  var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
							
						var temp_end = dt_end.split("/");
						var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
							
						std_lease = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
						dt_end = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
		    		  
		    		  if(std_lease > dt_end)
		    			  {
		    			  	alert('End date should be greater or equal to start date : '+std_lease1);
		    			  	$(this).focus();
		    			  	$(this).val('');
		    			  	$(this).addClass('error');
		    			  	exit(0);
		    			  }
		    		  
		    		  }
		    	  
		      }
		    });
		
	 
	
	$('.grndatepicker1').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function(selected,evnt) {
	    	
	    	  var dt_grn = $('#dtGrnForInvoice').val();
	    	 
	    	var id_inv_m = $('input[name="id_inv_m"]').val();
	    	$('#dtGrnForInvoice').removeClass('error');
	    $.post('Add_To_Store1',{action : 'CheckDate' , dt_grn : dt_grn ,id_inv_m : id_inv_m},function (r){
	    		
	    		if(r.data == 0)
	    			{
	    			alert('Receive Date should be greater than invoice date.'+r.dt_inv);
	    				$('#dtGrnForInvoice').focus();
	    				$('#dtGrnForInvoice').val('');
	    				$('#dtGrnForInvoice').addClass('error');
	    				exit(0);
	    			}
	    		
	    },'json');
	      }
	});	

	
	
	
	
});
function CalcTotPriceForAddToStore()
{
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	$('input[name="cst_asst_add"]').removeClass('error');
	var str = $('input[name="cst_asst_add"]').val();
	
	if(str != '' || str != undefined)
		{
			if(intRegex.test(str) || floatRegex.test(str)) {
				
				var addPrice = $('input[name="cst_asst_add"]').val();
				var tt_un_prc = $('input[name="cst_asst"]').val();
				//var ex_rate = $('input[name="ex_rate"]').val();
				
				var totPrice = parseFloat(addPrice) + parseFloat(tt_un_prc);
				
				$('input[name="val_asst"]').val(totPrice.toFixed(2));
			}
			else
			{
				alert('Invalid number.');
				$('input[name="cst_asst_add"]').addClass('error');
				$('input[name="cst_asst_add"]').focus();
				exit(0);
			}
		}
	
	
}



</script>
<div id="displayInvoiceForStore" class="commonDiv">

		<table width="100%" height="100%">
			<tr>
				<td>
				
					<strong>From Date</strong>&nbsp;
					<input  type="text" name="dt_frm" value="DD-MMM-YYYY" class="common-validation grndatepicker" >
					&nbsp;&nbsp;<strong>
					To Date</strong>&nbsp;
					<input  type="text" name="dt_to" value="DD-MMM-YYYY" class="common-validation grndatepicker" >
					
					<button type="button" style="margin-top: -10px;margin-left: 10px;" class="btn btn-primary" onclick="DisplayInvoiceForAddToStore('A_Add_To_Store1','displayInvoiceForStore')">Search </button>
				
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered displayInvoiceForStore">
						<tr class="new">
							<td><strong>Invoice Number</strong></td>
							<td><strong>Invoice Date</strong></td>
							<td><strong>PO Number</strong></td>
							<td><strong>PO Date</strong></td>
							<td><strong>Quantity</strong></td>
							<td><strong>Vendor</strong></td>
							<td><strong>Add to store</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>
	
	<div id="serialNo" display="none">
	<form name="SerialId" id="SerialId">
		
	</form>
	
</div>

	<div id="AddToStore">
	<form name="AddToWH" id="AddToWH">
		<input type="hidden" name="id_inv" value="">
		<input type="hidden" name="id_inv_m" value="">
		<input type="hidden" name="qty" value="">
		<input type="hidden" name="action" value="Save">
		<input type="hidden" name="SerialVal" value="">
		<input type="hidden" name="appNo" value="">
		<input type="hidden" name="sapNo" value="">
		<input type="hidden" name="id_grp" value="">
		<input type="hidden" name="id_model" value="">
		<input type="hidden" name="id_sgrp" value="">
		<input type="hidden" name="id_loc" value="">
		<input type="hidden" name="id_flr" value="">
		<input type="hidden" name="id_subl" value="">
		<input type="hidden" name="id_building" value="">
		<!-- <input type="hidden" name="id_storage" value=""> -->
		<input type="hidden" name="id_grn" value="">
		<input type="hidden" name="no_grn" value="">
		<input type="hidden" name="dt_grn" value="">
		<input type="hidden" name="no_dc" value="">
		<input type="hidden" name="dt_dc" value="">
		<input type="hidden" name="qty_recv" value="">
		<input type="hidden" name="tt_un_prc" value="">
		<input type="hidden" name="nm_acc_asset" value="">
		<input type="hidden" name="total_prc" value="">
		<input type="hidden" name="id_ven_proc" value="">
		<input type="hidden" name="dt_inv" value="">
		<input type="hidden" name="dt_po" value="">
		<input type="hidden" name="id_curr" value="">
		<input type="hidden" name="typ_asst" value="">
		<input type="hidden" name="id_dept" value="">
		<input type="hidden" name="st_config" value="">
		<input type="hidden" name="id_div" value="">
		<!-- <input type="hidden" name="no_bd" value="">
		<input type="hidden" name="dt_bd" value=""> -->
		<input type="hidden" name="bd" value="">
		
		
	
		<table class="table table-bordered  AddToStoreDisplay">
			<tr class="tableHeader tableHeaderContent">
				<td colspan="4"><center><strong> Add Asset To Store</strong></center></td>
			</tr>
			<tr>
				<td><strong>Invoice Number <font color="red">*</font></strong></td>
				<td><input  type="text" name="no_inv" value=""  readonly="readonly" class="common-validation" data-valid="mand"></td>
				<td><strong>Invoice Date <font color="red">*</font></strong></td>
				<td><input type="text" name="dt_inv" value="" readonly="readonly" disabled="true" class="common-validation grndatepicker" data-valid="mand"></td>
				
			</tr>
			<tr>
				<td><strong>PO Number <font color="red">*</font></strong></td>
				<td><input type="text" name="no_po" value="" readonly="readonly" class="common-validation" data-valid="mand"></td>
				<td><strong>PO Date <font color="red">*</font></strong></td>
				<td><input type="text" name="dt_po" value="" readonly="readonly" disabled="true" class="common-validation grndatepicker" data-valid="mand"></td>
				
			</tr>
			
			<tr>
				<td ><b>Service Vendor</b></td>
				<td >
					<select id="vendorDataForAddToStore" name="id_service_ven" style="width:140" >
						<option value="">Select</option>
						
					</select>
				</td>
				<td><strong>Additional Cost</strong></td>
				<td><input type="text" name="cst_asst_add" value="0" class="common-validation" data-valid="num" onBlur="return CalcTotPriceForAddToStore()"></td>
				
				<!-- <td ><b>currency<font color="red"> * </font>:</b></td>
				<td><input type="text" name="" value="INR" class="common-validation" data-valid="mand" readonly></td>
				<input type="hidden" name="id_curr" value="1">	
				<td >
					<select id="currDataForAddToStore" name="id_curr" class="common-validation" data-valid="mand" disabled="true">
						<option value="">Select</option>
						
					</select>
				</td>  -->
			</tr>
			<tr>
				<td><strong>Manufacturer</strong></td>
				<td><input type="text" name="mfr" value="" ></td>
				<td><strong>Model<font color="red">*</font></strong></td>
				<td>
					<select id="modelDataForAddToStore" name="id_model" class="common-validation resetAcc"  readonly="readonly" disabled="true"  style="width:140" >
							<option value="">Select</option>
							
						 </select>
				</td>
				<!-- <td><strong>Model Number :</strong></td>
				<td><input type="text" name="no_model" value="" ></td> -->
				
			</tr>
			<tr>
				<td><strong>Asset Description</strong></td>
				<td><input type="text" name="ds_asst" value=""></td>
				<!--  <td><strong>Remarks:</strong></td>
				<td><input type="text" name="remarks" value=""></td>-->
				<td><strong>Asset Remarks</strong></td>
				<td><input type="text" name="rmk_asst" value=""></td>
			</tr>
			<tr>
				<td><strong>Asset Name<font color="red">*</font></strong></td>
				<td><input type="text" name="ds_pro" value="" readonly="readonly" class="common-validation" data-valid="mand"></td>
				
			<td><strong>Asset Owner<font color="red">*</font></strong></td>
				<td><select name="typ_asst" id="typAssetForInvoice"  style="width:140"  class="common-validation resetAcc" data-valid="mand" onChange="DisplaySubDropDownData(this,'storageDataForAddToStore','M_StorageDetail')">
						<option value="">Select</option>
						<option value="IT">IT Software</option>
						<option value="ITS">IT Hardware</option>
						
					</select>
				</td>
				
				<tr>
				<td ><b>Storage</b></td>
				<td >
					<select id="storageDataForAddToStore" name="id_storage" style="width:140" >
						<option value="">Select</option>
						
					</select>
				</td>
				<td ><b>AMC / Warranty<font color="red">*</font></b></td>
				<td >
					<select id="warr_amc1" name="warr_amc"  style="width:140" onChange="ShowRowColumn(this , 'amcAddToStoreCommonClass')" class="common-validation" data-valid="mand">
						
						<option value="O">NO</option>
						<option value="A">AMC</option>
						<option value="W">Warranty</option>
						
					</select>
				</td>
				<tr class="amcAddToStoreCommonClass">
			<td><b>Start Date<font color="red">*</font></b></td>
			<td><input type="text" name="dt_amc_start" value="" class="amcAddToStoreCommonClass1 grndatepicker"></td>
			<td><b>End Date<font color="red">*</font></b></td>
			<td><input type="text" name="dt_amc_exp" value="" class="amcAddToStoreCommonClass1 amctDatepicker"></td>
			
				</tr>
				<tr><td ><b>Lease Status<font color="red">*</font></b></td>
				<td >
					<select id="st_lease1" name="st_lease"  style="width:140" onChange="ShowRowColumn(this , 'leaseAddToStoreCommonClass')">
						
						<option value="NUL">Not under lease</option>
						<option value="UL">Under lease</option>
						
					</select>
				</td>
				<td ><b>Type of Procurement<font color="red">*</font></b></td>
				<td >
					<select  name="typ_proc" class="common-validation select" data-valid="mand"  style="width:140">
						<option value="" >Select</option>
						<option value="OP">Outright Purchase</option>
						<option value="LB">Loan Basis</option>
						<option value="FOC">Free Of Cost</option>
						
						
					</select>
				</td>
			
			<tr class="aa leaseAddToStoreCommonClass">
				<td ><b>Lease Start Date<font color="red">*</font></b></td>
				<td ><input type="text" name="std_lease" value="" class="leaseCommonClass leaseAddToStoreCommonClass1 grndatepicker"></td>
				<td><b>Lease End Date<font color="red">*</font></b></td>
				<td><input type="text" name="endt_lease" value="" class="leaseCommonClass leaseAddToStoreCommonClass1 leasedatepicker"></td>
			</tr>
			<!-- hide and show for AMC / Warranty -->
			
				
				
				<tr><td ><b>Taggable<font color="red">*</font></b></td>
				<td >
					<select id="tag" name="tag"  style="width:140" class="common-validation" data-valid="mand">
						<option value="">Select</option>
						<option value="Yes">Yes</option>
						<option value="No">No</option>
					</select>
				</td>
			
			
			
			<td><strong>Total Unit Price<font color="red">*</font></strong></td>
				<td><input type="text" name="cst_asst" value="" readonly="readonly" class="common-validation" data-valid="num"></td>
				</tr>
			<tr>	<td><strong>Net Value<font color="red">*</font></strong></td>
				<td><input type="text" name="val_asst" value="" readonly="readonly" class="common-validation" data-valid="num"></td>
				
			</tr>
			<!-- <tr>
				<td><strong>Asset Owner<font color="red">*</font></strong></td>
				<td><select name="typ_asst" id="typAssetForInvoice"  style="width:140"  class="common-validation resetAcc" data-valid="mand">
						<option value="">Select</option>
						<option value="IT">IT</option>
						<option value="NIT">Non IT</option>
						
					</select>
				</td>
				
			</tr> -->
			<tr>
				<td colspan="4">
					<button name="save" type="button" style="margin-left:500px;"   class="btn btn-primary store" onclick="ControlStore('Save','displayInvoiceForStore','serialNo','AddToStore','')">Save</button>
					<button name="cancel" type="button"  class="btn btn-primary store" onclick="ControlStore('Back','displayInvoiceForStore','serialNo','AddToStore','')">Cancel</button>
				</td>
			</tr>
		</table>
	</form>
</div>

	
	
	