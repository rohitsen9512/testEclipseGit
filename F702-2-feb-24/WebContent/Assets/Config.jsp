<!--Edit_from_store.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Assets ></a><a href="#">Edit from Store</a>-->
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
					DisplayDropDownDataForVendor('M_Vendor','vendorDataForEditFromStore','service',function (status){
						if(status)
						{
							DisplayDropDownData("M_Loc","locationForEditFromStore1",function (status){
								if(status)
									{
									DisplayAssetForConfigIT("A_Config_IT");
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
	<input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayAssetForEdit('A_Edit_From_Store')">
</div>

<div id="DisplayEditFromStore">
	<table class="table table-bordered displayDataForEditFromStore">
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

<div id="EditDetailsForEditFromStore">
	<form name="SubmitFormForEditFromStore" id="SubmitFormForEditFromStore">
		<table class="table table-bordered">
				<tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center><strong>Asset  Details</strong></center></td>
				</tr>
					<tr>
						<td ><b>Ware House Id</b></td>
						<td ><input type= "text"  name="id_wh_dyn"   size= "25" value="" readonly="readonly" class="common-validation" data-valid="mand"></td>
						<td ><b>Asset Name</b></td>
						<td ><input type= "text" name="ds_pro" size= "25" value="" readonly="readonly" class="common-validation" data-valid="mand"></td>
					</tr>
					<tr>
						<td ><b>Serial Number</b></td>
						<td ><input type="text"  name="no_mfr" size="25" value=""  class="common-validation" data-valid="mand" onBlur="CheckValWhichAllReadyExit('A_Edit_From_Store' , 'Serial number all ready exit.' , 'no_mfr')"></td>
							</tr>
					
			<tr>
				
				<td><strong>Asset Description</strong></td>
				<td><input type="text" name="ds_asst" value=""></td>
				
			</tr>
			<tr>
				
				<td><strong>Model Number</strong></td>
				<td><input type="text" name="no_model" value="" ></td>
				
			</tr>
		
			<tr>
				<td><strong>Asset Remarks</strong></td>
				<td><input type="text" name="rmk_asst" value=""></td>
				
			</tr>
			<tr>
				<td ><b>AMC / Warranty</b></td>
				<td> 
					<select id="warr_amc" name="warr_amc"  style="width:140" class="common-validation" data-valid="mand" onChange="ShowRowColumn(this , 'amcCommonClass')">
						
						<option value="O">NO</option>
						<option value="A">AMC</option>
						<option value="W">Warranty</option>
						
					</select>
				</td>
				
			</tr>
			
			
			<!-- hide and show for AMC / Warranty -->
			
			<tr class="amcCommonClass">
			<td><b>Start Date<font color="red">*</font></b></td>
			<td><input type="text" name="dt_amc_start" value="" class="amcCommonClass1 grndatepicker"></td>
			<td><b>End Date<font color="red">*</font></b></td>
			<td><input type="text" name="dt_amc_exp" value="" class="amcCommonClass1 amctDatepicker"></td>
			
			</tr>
			
			
			
			
			
			
			
				<input type="hidden" name="id" value="">
				<input type="hidden" name="action" value="Update">
				<tr>
					<td colspan="4">
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlConfigITAsset('Update','EditDetailsForEditFromStore','SearchFromForEditFromstore','DisplayEditFromStore','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlEditFromStore('Cancel','EditDetailsForEditFromStore','SearchFromForEditFromstore','DisplayEditFromStore','')">Cancel</button>
					</td>
				</tr>
		</table>
	</form>
</div>
