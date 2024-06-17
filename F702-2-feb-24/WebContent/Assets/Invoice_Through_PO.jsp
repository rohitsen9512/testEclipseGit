
<div style="padding:10px;">
	<!--  <a href="#">Asset ></a><a href="#"> Invoice > Update Invoice</a>-->
</div>
<script type ="text/javascript" src="All_Js_File/Asset/Invoice_Through_PO.js"></script>
<script type="text/javascript">

$(function() {
	
	$('.hideRowCol').hide();
	
	
	$( ".invoiceDatepicker1" ).datepicker({
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
	    	  var dt_boe = $(this).val();
	    	  if(dt_inv == '')
	    		  {
	    		  	alert('First filled invoice date.');
	    		  	$('input[name="dt_inv"]').focus();
	    		  	$('input[name="dt_inv"]').addClass('error');
	    		  	$('input[name="dt_inv"]').val('');
	    		  	$(this).val('');
	    		  	exit(0);
	    		  }
	    	  else
	    		  {
	    		  var temp_strt = dt_inv.split("/");
				  var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
						
					var temp_end = dt_boe.split("/");
					var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
						
					dt_inv = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
					dt_boe = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
	    		  
	    		  if(dt_inv > dt_boe)
	    			  {
	    			  	alert('BOE date should be greater or equal to invoice date : '+dt_inv1);
	    			  	$(this).focus();
	    			  	$(this).val('');
	    			  	$(this).addClass('error');
	    			  	exit(0);
	    			  }
	    		  
	    		  }
	    	  
	      }
	    });
	
	
	var currentDate = new Date();
	$( ".datepicker" ).datepicker({
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
	
	DisplayPurchaseOrderForInvoice('Invoice_Through_PO','displayInvoice','createInvoice','','invoiceForDisplay');
	
	$('button[name="update"]').addClass('hideButton');
	$('button[name="save1"]').addClass('hideButton');	
	
	
	/* DisplayDropDownData("M_Loc","locDataForPO",function (status){
	if(status)
		{ */
		DisplayDropDownDataForGroup('M_Asset_Div','assetDivForPO','CapG',function (status){
			if(status)
			{
				DisplayDropDownDataForVendor('M_Vendor','vendorDataForPO','procured',function (status){
				if(status)
				{
				/* DisplayDropDownData('M_Currency','currDataForPO',function (status){
					if(status)
					{ */
						/* DisplayDropDownData('M_Subloc','slocDataForInvoice',function (status){
						if(status)
						{ */
							/* DisplayDropDownData('M_Loc','locDataForPO',function (status){
									if(status)
									{ */
										DropDownDataDisplay('M_Tax','taxDataForInvoiceThroughPO',function (status){
											if(status)
											{
												GetDropDownDataForAssetOwner('typAssetForInvoiceByPO',function (status){
													if(status)
													{
														GetDropDownDataForAssetOwner('typAssetForInvoice',function (status){
															if(status)
															{
																DropDownDataDisplay("M_Division","divDataForInvByPO",function (status){
																	if(status)
																		{
																		
																		}});
															}});	
													}});
											}});		
										
									/* }}); */	
							
						/* }}); */	 
					/* }}); */	
				}});
			}});
		/* }}); */
	
	
});

jQuery("input#fileID").change(function () {
	var formData = new FormData();
    formData.append('file', $('#fileID').get(0).files[0]);
	$.ajax({
	  url: 'Upload_File',
	    type: 'POST',
	    data: formData,
	    async: false,
	    cache: false,
	    contentType: false,
	    dataType: "json",
	    processData: false,
	    success: function (r) {
	    	
	    		$('input[name="upload_inv"]').val(r.upload_inv);
	    		//bootbox.alert("File Uploaded successfully");
	    		
	    	
	    }
	},'json');
});

function checkInvoiceNo(){
	  var t='';
	  t = $('input[name="no_inv"]').val();
	  if(t != '')
		  {
		 	 //CheckValWhichAllReadyExit('A_Invoice' , 'Invoice no all ready exit.' , 'no_inv');
		  }
	 
	
	}

 function CalcTotInvoiceThroughPO(){
		 var unitpricetotal=0.0;
		 var qty1 = $('input[name="qty"]').val();
		 var rem_qty = $('input[name="rem_qty"]').val();
		 $('input[name="qty"]').removeClass('error');
		 if(parseInt(qty1) > parseInt(rem_qty))
			 {
				 alert('Quantity should be less or equal to '+rem_qty+'.');
				 $('input[name="qty"]').addClass('error');
				 $('input[name="qty"]').focus();
				 exit(0);
			 }
		 else
			 {
		$('.ttCount').each(function (){
			$(this).removeClass('error');
			
			var intRegex = /^\d+$/;
			var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;

			var str = $(this).val();
			if(intRegex.test(str) || floatRegex.test(str)) {
				var qty = $('input[name="qty"]').val();
				var unitprice= $('input[name="un_prc"]').val();
				var Duty= $('input[name="duty"]').val();
				var others= $('input[name="othr"]').val();
				var id_tax=$('select[name="id_tax"]').val();
				 if(id_tax != 0)
				{
				calculateInvoiceThroughPOValue(id_tax); 	
				}
			else
				{
					total= qty*unitprice  + parseFloat(Duty) + parseFloat(others);
					$('input[name="tax_per"]').val('0');
				} 
				
				total= qty*unitprice  + parseFloat(Duty) + parseFloat(others);
				$('input[name="tax_per"]').val('0');
				if(qty !=0)
					{
					 unitpricetotal = (total/qty).toFixed(2);
					}
				
				
				
				$('input[name="tt_un_prc"]').val(unitpricetotal);
				$('input[name="tt"]').val(total.toFixed(2));
			}
			else
				{
					alert('Invalid number.');
					$(this).addClass('error');
					$(this).focus();
					exit(0);
				}
		});
		
			 }
		
	}

	 function calculateInvoiceThroughPOValue(id_tax)
	 {
	 var t=true;
	 var unitpricetotal=0.0;
	 
	 	if(id_tax.value != undefined)
	 		{
	 			id_tax =id_tax.value;
	 			if(id_tax == '0')
	 				{
		 				t=false;
		 				$('input[name="tax_per"]').val('0');
	 				}
	 		}
	 		
	 	if(t)
	 		{
	 	$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
	 		
	 		if(r.data.length > 0 )
	 			{
	 			var qty = $('input[name="qty"]').val();
				var unitprice= $('input[name="un_prc"]').val();
				var Duty= $('input[name="duty"]').val();
				var others= $('input[name="othr"]').val();
				 var percent = r.data[0].per_tax;
				 
	 			total= qty*unitprice + ((qty*unitprice)*parseFloat(percent))/100  + parseFloat(Duty) + parseFloat(others);
	 			if(qty !=0)
	 				{
	 				unitpricetotal = (total/qty);
	 				}
	 			 
				
	 			$('input[name="tt"]').val(total.toFixed(2));
				$('input[name="tt_un_prc"]').val(unitpricetotal.toFixed(2));
				$('input[name="tax_per"]').val(percent);
	 			}
	 		
	 		
	 	},'json');
	 	
	 		}
	 	
	 }
	 
</script>
	<div id="displayInvoice">

		<table width="100%" height="100%">
			<tr>
				<td>
				<!-- <div class="commonDiv" id="SearchFromForPO"> -->
					 <input type="text" id="SearchFromForPOValue" name="search" value="" placeholder="Search PO Number..." onkeyup="DisplayPurchaseOrderForInvoice('Invoice_Through_PO','displayInvoice','createInvoice','','invoiceForDisplay');">
				<!-- </div> -->
					<strong>From Date</strong>&nbsp;
					<input id="dt_frm" type="text" name="dt_frm" value="DD-MMM-YYYY" class="common-validation datepicker" data-valid="mand">
					&nbsp;&nbsp;<strong>
					To Date</strong>&nbsp;
					<input id="dt_to" type="text" name="dt_to" value="DD-MMM-YYYY" class="common-validation datepicker" data-valid="mand">
					<button type="button" style="margin-top: -10px;margin-left: 25px;" class="btn btn-primary" onclick="DisplayPurchaseOrderForInvoice('Invoice_Through_PO','displayInvoice','createInvoice','','invoiceForDisplay')">Search </button>
					<!-- <button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlInvoiceThroughPO('Create','displayInvoice','createInvoice','submitInvoice','Invoice_Through_PO')">Create Invoice</button>
					 -->
				</td>
			</tr>
			
			<tr>
				<td>
					<table class="table table-bordered invoiceForDisplay">
						<tr class="new">
							<td><strong>Invoice Number</strong></td>
							<td><strong>Invoice Date</strong></td>
							<td><strong>PO Number</strong></td>
							<td><strong>PO Date</strong></td>
							<td><strong>Quantity</strong></td>
							<td><strong>Vendor</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>
	<form name="submitInvoice" id="submitInvoice" enctype="multipart/form-data">
	<div id="createInvoice" style="display:none;">
	
		<table class="table table-bordered" id="invoiceDetails">
			<tr>
				<td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 350px;">Purchase Order Details</p></td>
			</tr>
			<tr id="InvoiceForPurchaseOrderHideShow">
				<td><strong>Invoice Number<font color="red">*</font></strong></td>
				<td><input id="InvoiceNum" type="text" name="no_inv" value="" class="common-validation" data-valid="mand" onBlur="return checkInvoiceNo()"></td>
				<td><strong>Invoice Date<font color="red">*</font></strong></td>
				<td><input type="text" name="dt_inv" value="" class="common-validation invoiceThroughDatepicker" data-valid="mand"></td>
				
			</tr>
			<tr>
				<td><strong>PO Number<font color="red">*</font></strong></td>
				<td><input type="text" name="no_po" value="" class="common-validation readbaledata" data-valid="mand"></td>
				<td><strong>PO Date<font color="red">*</font></strong></td>
				<td><input type="text" name="dt_po" value="" class="common-validation readbaledata" data-valid="mand"></td>
				
			</tr>
			<!-- <tr>
				<td ><b>Vendor<font color="red"></font></b></td>
				<td >
					<select id="vendorDataForPO" name="id_ven"  style="width:140" class="common-validation readbaledata" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
				<td ><b>currency<font color="red">*</font></b></td>
				<td><input type="text" name="" value="INR" class="common-validation" data-valid="mand" readonly></td>
								<input type="hidden" name="id_curr" value="1">	
				 
				 <td ><b>currency<font color="red">*</font></b></td>
				 <td >
					<select id="currDataForPO" name="id_curr" class="common-validation readbaledata" data-valid="mand"  style="width:140">
						<option value="2">INR</option>
						
					</select>
				</td>
			</tr> -->
			<tr>
			<td ><b>Division Name<font color="red">*</font></b></td>
				<td >
					<select id="divDataForInvByPO" name="id_div" class="common-validation" data-valid="mand"  style="width:140" onChange="DisplaySubDropDownData(this,'locDataForInvoice','M_Loc')">
						<option value="">Select</option>
						
					</select>
				</td>
				<td ><b>Region<font color="red">*</font></b></td>
				<td >
					<select id="locDataForInvoice" name="id_loc" class="common-validation" data-valid="mand"  style="width:140" onChange="DisplaySubDropDownData(this,'slocDataForInvoice','M_Subloc')">
						<option value="">Select</option>
						
					</select>
				</td>
				
			</tr>
			<tr>
			<td ><b>State<font color="red">*</font></b></td>
				<td >
					<select id="slocDataForInvoice" name="id_subl" class="common-validation" data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'buildingDataForInvoice','M_Building')">
						<option value="">Select</option>
						
					</select>
				</td>
			<td ><b>City<font color="red">*</font></b></td>
				<td >
					<select id="buildingDataForInvoice" name="id_building" class="common-validation" data-valid="mand"  style="width:140" onChange="DisplaySubDropDownData(this,'floorDataForInvoice','M_Floor')">
						<option value="">Select</option>
						
					</select>
				</td>
				
				</tr>
			 <tr>
				<!--<td ><b>Bonded<font color="red">*</font></b></td>
				<td >
					<select id="bdIdForAddTostore" name="bd" class="common-validation" data-valid="mand"  style="width:140" onChange="ShowRowColumn(this , 'hideRowCol')">
						<option value="No">No</option>
						<option value="Yes">Yes</option>
						
						
					</select>
				</td> -->
				<td ><b>Store<font color="red">*</font></b></td>
				<td >
					<select id="floorDataForInvoice" name="id_flr" class="common-validation" data-valid="mand"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td>
				<td ><b>Vendor<font color="red">*</font></b></td>
				<td >
					<select id="vendorDataForPO" name="id_ven"  style="width:140" class="common-validation readbaledata" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
				
				
			</tr>
			<tr>
			<td><strong>Upload Scan File</strong></td>
				<td><input id="fileID" type="file" name="file" class="common-validation " value=""></td>
				<td colspan="2"></td>
			</tr>
			<tr class="aa hideRowCol">
				<td><b>BOE No<font color="red">*</font></b></td>
				<td><input type="text" name="no_boe" value="" class="common-hideShow" class="common-validation"></td>
				<td><strong>BOE Date<font color="red">*</font></strong></td>
				<td><input type="text" name="dt_boe" value="" class="common-hideShow invoiceDatepicker1" class="common-validation"></td>
				
			</tr>
			<!-- <tr class="aa hideRowCol">
				<td><strong>DE Bond Date<font color="red">*</font></strong></td>
				<td><input type="text" name="de_dt_bd" value="" class="common-hideShow datepicker"></td>
				<td><strong>Expiry Date<font color="red">*</font></strong></td>
				<td><input type="text" name="dt_exp" value="" class="common-hideShow datepicker"></td>
				
			</tr> -->
			</table>
		
	</div>
	
	
	
	<div id="createAccessory" style="display:none;">
		<form name="submitAccessory" id="submitAccessory" enctype="multipart/form-data">
			<table class="table table-bordered" id="accessoryDetails">
		<input type="hidden" name="typ_po" value="">
			<tr>
				<td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 350px;">Asset Details</p></td>
			</tr>
			<tr>
			
				<!-- <td><strong>Asset<font color="red">*</font></strong></td>
				<td><select name="nm_acc_asset" class="common-validation resetAcc" data-valid="mand"  style="width:140">
						
						<option value="Asset">Asset</option>
						
						
					</select>
				</td> -->
				<td ><strong>Group<font color="red">*</font></strong></td>
				<td>
					<select id="assetDivForPO" name="id_grp" class="common-validation resetAcc" data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'subGroupDataForInvoice','M_Subasset_Div')">
							<option value="">Select</option>
							
						</select>
				</td>
				<td><strong>Subgroup<font color="red">*</font></strong></td>
				<td>
					<select id="subGroupDataForInvoice" name="id_sgrp" class="common-validation resetAcc" data-valid="mand"  style="width:140" onChange="GenerateAssetName(); SubDropDownDataDisplay(this,'modelDataForInvoice','M_Model'); ">
							<option value="">Select</option>
							
						</select>
				</td>
				
			</tr>
			<!-- <tr>
				
				<td><strong>Model</strong></td>
				<td><input type="text" name="model" value="" readonly="readonly" class="resetAcc common-validation" ></td>
			</tr> -->
			<tr>
				<td><strong>Model<font color="red">*</font></strong></td>
				<td>
					<select id="modelDataForInvoice" name="id_model" class="common-validation resetAcc"   style="width:140" >
							<option value="">Select</option>
							
						 </select>
				</td>
					<td><strong>Manufacturer/Publisher <font color="red">*</font></strong></td>
				<td><input type="text" name="mfr" value=""  class="common-validation resetAcc" data-valid="mand" ></td>
				
				</tr>	
				
			<tr>
				
				<!-- <td><strong>Manufacturer</strong></td>
				<td><input type="text" name="mfr" value="" readonly="readonly"  class="resetAcc common-validation""></td>
				 -->
				<td><strong>Asset Name <font color="red">*</font></strong></td>
				<td><input type="text" name="ds_pro" value="" readonly="readonly" class="common-validation resetAcc" data-valid="mand"></td>
			
			<td><strong>Asset Owner <font color="red">*</font></strong></td>
				<td><select name="typ_asst" id="typAssetForInvoice"  style="width:140" class="common-validation resetAcc" data-valid="mand" onChange="ControCongiguration(this)">
						<option value="">Select</option>
						<option value="IT">Configuration</option>
						<option value="ITS">Configuration</option>
						<option value="NIT">Non Configuration</option>
						
					</select>
				</td>
				<!-- <td><strong>Asset Type:<font color="red">*</font></strong></td>
				<td><select name="typ_asst" id="typAssetForInvoiceByPO"  style="width:140" class="common-validation readbaledata" data-valid="mand">
						<option value="">Select</option>
						<option value="IT">IT</option>
						<option value="NIT">Non IT</option>
						
					</select>
				</td> -->
			</tr>
			<tr>
				<td><strong>Configurable<font color="red">*</font></strong></td>
					<td id="config">
					<select name="config" id="ConfigurableId"  style="width:140" class="common-validation resetAcc" class="common-validation readbaledata" data-valid="mand" onChange="SetConfigValue(this)">
						
						<option value="Yes">Yes</option>
						<option value="No">No</option>
						
					</select>
					<input type="hidden" name="st_config" value="Yes">
				</td>
				<td><strong>Remarks<font color="red">*</font></strong></td>
				<td><input type="text" name="mfr" value=""  class="common-validation resetAcc" data-valid="mand" ></td>
				
				
			</tr>
			<tr>
				<td><strong>Tax</strong></td>
				<td>
				<select id="taxDataForInvoiceThroughPO" name="id_tax" class="" style="width:140" onChange="CalcTotInvoiceThroughPO()">
						<option value="">Select</option>
						
						
					</select>
				</td>
			<td><strong>Tax Percentage</strong></td>
				<td><input type="text" name="tax_per" value="0" class="resetAcc1" readonly></td>
				
			</tr>
			<tr>
			<td><strong>Quantity<font color="red">*</font></strong></td>
				<td><input type="text" name="qty" value="1" class="common-validation ttCount resetAcc" data-valid="num" onBlur="return CalcTotInvoiceThroughPO()"></td>
				
				<td><strong>Unit Price<font color="red">*</font></strong></td>
				<td><input id="unitPrice" type="text" name="un_prc" value="0.0" readonly="readonly" class="common-validation ttCount resetAcc" data-valid="num" onBlur="return CalcTotInvoiceThroughPO()"></td>
				
			</tr>
			
			<tr>
				<td><strong>Duty<font color="red">*</font></strong></td>
				<td><input type="text" name="duty" value="0" class="common-validation ttCount resetAcc1" data-valid="num" onBlur="return CalcTotInvoiceThroughPO()"></td>
			<td><strong>Others<font color="red">*</font></strong></td>
				<td><input type="text" name="othr" value="0" class="common-validation ttCount resetAcc1" data-valid="num" onBlur="return CalcTotInvoiceThroughPO()"></td>
			</tr>
			<tr>
				
				<td><strong>Total Unit Price<font color="red">*</font></strong></td>
				<td><input type="text" name="tt_un_prc" value=""  class="common-validation ttCount resetAcc resetAcc1" data-valid="num" readonly="readonly"></td>
				
				<td><strong>Total Price<font color="red">*</font></strong></td>
				<td><input type="text" name="tt" value=""  class="common-validation ttCount resetAcc1 resetAcc" data-valid="num" readonly="readonly"></td>
				
			</tr>
			
					
				
			<tr>
				<td colspan="4">
					
					<input type="hidden" name="action" value="Save" class="common-validation">
					<input type="hidden" name="rem_qty" value="" class="common-validation">
					<input type="hidden" name="upload_inv" value="" class="common-validation">
					<input id="id" type="hidden" name="id" value="0" class="common-validation">
					<input id="invoiceId" type="hidden" name="id_po_asst" value="" class="common-validation">
					<input type="hidden" name="nm_acc_asset" value="Asset" class="common-validation">
					<input type="hidden" name="id_subl" value="" class="common-validation">
					<input type="hidden" name="id_loc" value="" class="common-validation">
					<input type="hidden" name="id_ven" value="" class="common-validation">
					<input type="hidden" name="id_curr" value="" class="common-validation">
					
					<input type="hidden" name="id_grp" value="" class="common-validation">
					<input type="hidden" name="id_sgrp" value="" class="common-validation">
					<input type="hidden" name="id_tax" value="" class="common-validation">
					<input type="hidden" name="typ_asst" value="" class="common-validation">
					
					
						<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlInvoiceThroughPO('Save','displayInvoice','createInvoice','submitInvoice','Invoice_Through_PO')">Save</button>
						<!-- 
						<button name="save1" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlInvoiceThroughPO('Save','displayInvoice','createInvoice','submitInvoice','Invoice_Through_PO')">Save</button>
						 -->
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlInvoiceThroughPO('Update','displayInvoice','createInvoice','submitInvoice','Invoice_Through_PO')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlInvoiceThroughPO('Cancel','displayInvoice','createInvoice','submitInvoice','Invoice_Through_PO')">Cancel</button>
					
				</td>
			</tr>
			
		</table>
	</form>
	</div>
</form>
	<div id="displayAccessory" style="display:none;">
		
		<table width="100%" height="100%">
			
			<tr>
				<td>
					<table class="table table-bordered accessoryForDisplay">
						<tr class="new">
							<td><strong>Invoice Number</strong></td>
							<td><strong>Invoice Date</strong></td>
							<td><strong>PO Number</strong></td>
							<td><strong>PO Date</strong></td>
							<td><strong>Quantity</strong></td>
							<td><strong>Vendor</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>
	
