<!--Edit_from_store.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Assets > </a><a href="#">Physical Asset Verification</a>-->
</div>

<script type="text/javascript">

$(function() {

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
						DisplayDropDownData("M_Loc","locationForEditFromStore1",function (status){
							if(status)
								{
								DisplayDropDownDataForStorage('M_StorageDetail','storageDataForEditToStore',function (status){
									if(status)
									{
										DisplayAssetForPhysicalAssetVerification("Physical_asset_verification");
									}});
								}});
								}
						});
					}
					});
			}		
		});
			
	}});

	$('.grndatepicker').each(function () {
		
        if ($(this).hasClass('hasDatepicker')) {
            $(this).removeClass('hasDatepicker');
        } 
         $(this).datepicker({
        	yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	    });
    });
	
	$('.insCommonClass1').hide();
	$('.amcCommonClass').hide();
	$('.leaseCommonClass').hide();
	$('.calCommonClass').hide();
	
	$('#EditDetailsForEditFromStore').hide();
	
	
});

function CalcTotPriceEditFromStore()
{var intRegex = /^\d+$/;
var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
$('input[name="cst_asst_add"]').removeClass('error');
var str = $('input[name="cst_asst_add"]').val();

if(str != '' || str != undefined)
	{
		if(intRegex.test(str) || floatRegex.test(str)) {
			
			var addPrice = $('input[name="cst_asst_add"]').val();
			var tt_un_prc = $('input[name="cst_asst"]').val();
			
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

<div class="commonDiv" id="SearchFromForEditFromstore">
	<input type="text" name="search" value="" placeholder="Type asset tag here...." onkeyup="DisplayAssetForEdit('A_Edit_From_Store','A_Ware_House')">
</div>

<div id="DisplayEditFromStore">
<form name="PhysicalAssetVerification" id="PhysicalAssetVerification">
	<table class="table table-bordered displayDataForEditFromStore">
		<tr class="info new">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Serial Number</strong></td>
			<td><strong>Model Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong>Edit/Install </strong></td>
		</tr>
	</table>
	
	<input type="hidden" name="id" value="">
	<input type="hidden" name="action" value="Update">
				
</form>
</div>

<div id="EditDetailsForEditFromStore">
	<form name="SubmitFormForEditFromStore" id="SubmitFormForEditFromStore">
		<table class="table table-bordered">
				<tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center><strong>Asset  Details</strong></center></td>
				</tr>
					<tr>
						<td ><b>Asset ID</b></td>
						<td ><input type= "text"  name="id_wh_dyn"   size= "25" value="" readonly="readonly" class="common-validation" data-valid="mand"></td>
						<td ><b>Asset Name</b></td>
						<td ><input type= "text" name="ds_pro" size= "25" value="" readonly="readonly" class="common-validation" data-valid="mand"></td>
					</tr>
					<tr>
						<td ><b>Serial Number</b></td>
						<td ><input type="text"  name="no_mfr" size="25" value=""  class="common-validation" data-valid="mand" onBlur="return  CheckValWhichAllReadyExit('A_Edit_From_Store' , 'Serial number already exist.' , 'no_mfr');"></td>
						<td ><b>AssetTag No</b></td>
						<td ><input type="text"  name="serial_no" size="25" value=""  class="common-validation" data-valid="mand"></td>
					</tr>
					<!--  
					<tr>
						<td ><b>Asset Remarks</b></td>
						<td ><input type="text"  name="rmk_asst" style="width:136" value=""  class="common-validation"></td>
						<td ><b>Asset Description</b></td>
						<td ><input type="text"  name="ds_asst"  style="width:136"   value="" readonly="readonly" class="common-validation"></td>
					</tr>
					-->
					<tr>
	
					<td><b>Location</b></td>
					<td>
						<select id="locationForEditFromStore1" name = "id_loc" class="common-validation" readonly="readonly" data-valid="mand" onChange="DisplaySubDropDownData(this,'subLocationForEditFromStore1','M_Subloc')">
								<option value = "" >Select</option>
						</select>
					</td>
					<td><b>Sub Location</b></td>
					<td>
						<select id="subLocationForEditFromStore1" name = "id_subl" class="common-validation" readonly="readonly" data-valid="mand">
								<option value = "" >Select</option>
						</select>
					</td>
				</tr>
				<tr>
				<td><strong>Invoice Number</strong></td>
				<td><input  type="text" name="no_inv" value="" readonly="readonly"  class="common-validation" data-valid="mand"></td>
				<td><strong>Invoice Date</strong></td>
				<td><input type="text" name="dt_inv" value=""  disabled="true" class="common-validation grndatepicker" data-valid="mand"></td>
				
			</tr>
			<tr>
				<td><strong>PO Number</strong></td>
				<td><input type="text" name="no_po" value="" readonly="readonly" class="common-validation" data-valid="mand"></td>
				<td><strong>PO Date</strong></td>
				<td><input type="text" name="dt_po" value="" disabled="true" class="common-validation grndatepicker" data-valid="mand"></td>
				
			</tr>
			<tr>
				<td ><b>Service Vendor</b></td>
				<td >
					<select id="vendorDataForEditFromStore" name="id_service_ven"  style="width:140" >
						<option value="">Select</option>
						
					</select>
				</td>
				<td><strong>Asset Description</strong></td>
				<td><input type="text" name="ds_asst" value=""></td>
				<!-- <td ><b>currency<font color="red"> * </font>:</b></td>   -->
				<!-- <td><input type="text" name="" value="INR" class="common-validation" data-valid="mand" readonly></td>
				<input type="hidden" name="id_curr" value="1">	 -->
			<!--  	<td >
					<select id="currDataForEditFromStore" name="id_curr" disabled="true" class="common-validation" data-valid="mand">
						<option value="">Select</option>
					</select>
				</td> -->
			</tr>
			<tr>
				<td><strong>Model<font color="red">*</font></strong></td>
				<td>
					<select Disabled id="modelDataForEditFromStore" name="id_model" class="common-validation" data-valid="mand" readonly="readonly" style="width:140" >
							<option value="">Select</option>
							
						 </select>
				</td>
				<!-- <td><strong>Model Number :</strong></td>
				<td><input type="text" name="no_model" value=""  class="common-validation"></td> -->
				<td ><b>Taggable  <font color="red">*</font></b></td>
				<td >
					<select id="tag" name="tag"  style="width:140" class="common-validation" data-valid="mand">
						<option value="">Select</option>
						<option value="Yes">Yes</option>
						<option value="No">No</option>
					</select>
				</td>
			</tr>
		<!--  	<tr>
				<td><strong>Asset Description:</strong></td>
				<td><input type="text" name="ds_asst" value=""></td>
				<td><strong>Remarks:</strong></td>
				<td><input type="text" name="remarks" value=""></td>
				
			</tr>  
			-->
			<tr>
				<td><strong>Asset Remarks </strong></td>
				<td><input type="text" name="rmk_asst" value=""></td>
				<td ><b>Type of Procurement<font color="red">*</font></b></td>
				<td >
					<select  name="typ_proc" class="common-validation" data-valid="mand"  style="width:140">
						<option value="" >Select</option>
						<option value="OP">Outright Purchase</option>
						<option value="LB">Loan Basis</option>
						<option value="FOC">Free Of Cost</option>
						
						
					</select>
				</td>
			</tr>
			<tr>
				<td ><b>AMC / Warranty </b></td>
				<td >
					<select id="warr_amc" name="warr_amc"  style="width:140" class="common-validation" data-valid="mand" onChange="ShowRowColumn(this , 'amcCommonClass')">
						
						<option value="O">NO</option>
						<option value="A">AMC</option>
						<option value="W">Warranty</option>
						
					</select>
				</td>
				<td ><b>Lease Status<font color="red">*</font></b></td>
				<td >
					<select id="st_lease" name="st_lease" class="common-validation" data-valid="mand"  style="width:140" onChange="ShowRowColumn(this , 'leaseCommonClass')">
						
						<option value="NUL">Not under lease</option>
						<option value="UL">Under lease</option>
						
					</select>
				</td>
			</tr>
			<tr class="aa leaseCommonClass">
				<td ><b>Lease Start Date <font color="red">*</font></b></td>
				<td ><input type="text" name="std_lease" value="" class="leaseCommonClass leaseCommonClass1 grndatepicker"></td>
				<td><b>Lease End Date<font color="red">*</font></b></td>
				<td><input type="text" name="endt_lease" value="" class="leaseCommonClass leaseCommonClass1 leasedatepicker"></td>
			</tr>
			
			<!-- hide and show for AMC / Warranty -->
			
			<tr class="amcCommonClass">
			<td><b>Start Date<font color="red">*</font></b></td>
			<td><input type="text" name="dt_amc_start" value="" class="amcCommonClass1 grndatepicker"></td>
			<td><b>End Date<font color="red">*</font></b></td>
			<td><input type="text" name="dt_amc_exp" value="" class="amcCommonClass1 amctDatepicker"></td>
			
			</tr>
			
			
			<tr>
				<!-- <td ><b>Insurance</b></td>
				<td >
					<select id="ins" name="ins"  style="width:140" onChange="ShowRowColumn(this , 'insCommonClass1')" class="common-validation" data-valid="mand">
						
						<option value="Yes">Yes</option>
						<option value="No">No</option>
						
					</select>
				</td> -->
				<td><strong>Additional Cost</strong></td>
				<td><input type="text" name="cst_asst_add" value="0" class="common-validation" data-valid="num" onBlur="return CalcTotPriceEditFromStore()"></td>
				
				<td><strong>Total Unit Price</strong></td>
				<td><input type="text" name="cst_asst" value="" readonly="readonly" class="common-validation" data-valid="num"></td>
				
			</tr>
			
			<!-- hide and show tr -->
			
			<!-- <tr class="aa insCommonClass1">
				<td ><b>Insurance vender <font color="red">*</font></b></td>
				<td >
					<select id="insVendorForEditfromStore" name="ins_ven"  style="width:140" class="insCommonClass2">
						<option value="">Select</option>
					</select>
				</td>
				<td><b>Insurance Policy No  <font color="red">*</font></b></td>
				<td><input type="text" name="no_ins_policy" value="" class="insCommonClass2"></td>
			</tr>
			<tr class="aa insCommonClass1">
				<td ><b>Insurance Start Date <font color="red">*</font></b></td>
				<td ><input type="text" name="stdt_ins" value="" class="insCommonClass2 grndatepicker"></td>
				<td><b>Insurance End Date<font color="red">*</font></b></td>
				<td><input type="text" name="endt_ins" value="" class="insCommonClass2 grndatepicker"></td>
			</tr> -->
			<tr>
			<!--  	<td><strong>Exchange Rate <font color="red">*</font></strong></td>
				<td><input type="text" name="ex_rate" value="" class="common-validation" data-valid="num" onBlur="return CalcTotPriceEditFromStore(),InsuranceAndFrightCheck('ex_rate')"></td>
				-->
				<td><b>Stock Room <font color="red">*</font></b></td>
				<td>
					<select id="storageDataForEditToStore" name="id_storage" class="common-validation" data-valid="mand">
					<option value = "" >Select</option>
					</select>
				</td>
				
				<td><strong>Net Value</strong></td>
				<td><input type="text" name="val_asst" value="" readonly="readonly" class="common-validation" data-valid="num"></td>
				
			</tr>
			<!-- <tr>
			<td ><b>Calibration</b></td>
				<td >
					<select id="cal" name="calb"  style="width:140" onChange="ShowRowColumn(this , 'calCommonClass')">
						<option value="No">No</option>
						<option value="CAL">CAL</option>
						<option value="PM">PM</option>
					</select>
				</td>
				<td colspan="2"></td>
			</tr>
				hide and show for Calibration
			
			<tr class="calCommonClass">
			<td><b>Calibration Expiry Date<font color="red">*</font></b></td>
			<td><input type="text" name="dt_calb" value="" class="calCommonClass1 grndatepicker"></td>
			<td colspan="2"></td>
			</tr> -->
				<input type="hidden" name="id" value="">
				<input type="hidden" name="action" value="Update">
				<tr>
					<td colspan="4">
						<button name="update" type="button" style="margin-left:450px;"   class="btn btn-primary edits" onclick="ControlEditFromStore('Update','EditDetailsForEditFromStore','SearchFromForEditFromstore','DisplayEditFromStore','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary edits" onclick="ControlEditFromStore('Cancel','EditDetailsForEditFromStore','SearchFromForEditFromstore','DisplayEditFromStore','')">Cancel</button>
					</td>
				</tr>
		</table>
	</form>
</div>
