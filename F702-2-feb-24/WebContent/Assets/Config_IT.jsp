<!--Edit_from_store.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Assets > </a><a href="#">IT Configuration > </a><a href="#">Configuration IT</a> -->
</div>

<script type="text/javascript">

$(function() {
	
	$( ".datepicker" ).datepicker({
		
		
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function(selected,evnt) {
	    	  $(this).removeClass('error');
	    	  var dt_config=$(this).val();
	    	  var id = $('input[name="id_wh"]').val();
	    	  if(dt_config == '')
	    		  {
	    		  	alert('Fill configure date.');
	    		  	$('input[name="dt_config"]').focus();
	    		  	$('input[name="dt_config"]').addClass('error');
	    		  	$('input[name="dt_config"]').val('');
	    		  	$(this).val('');
	    		  	exit(0);
	    		  } else {
		    		  
	    			    $.post('A_Config_IT',{action : 'CheckDate' , dt_grn : dt_config ,id:id},function (r){
	    		    		
	    		    		if(r.data == 2)
	    		    			{
	    		    			
	    		    			alert('Configuration date should be greater or equal to invoice date '+r.dt_grn);
	    		    			$('input[name="dt_config"]').focus();
	    		    			$('input[name="dt_config"]').val('');
	    		    			$('input[name="dt_config"]').addClass('error');
	    		    				exit(0);
	    		    			}
	    		    		if(r.data == 3)
	    	    			{
	    	    			
	    	    			alert('Configuration Date should be greater or equal to Uninstalled date '+r.dt_grn);
	    	    			$('input[name="dt_config"]').focus();
	    	    			$('input[name="dt_config"]').val('');
	    	    			$('input[name="dt_config"]').addClass('error');
	    	    				exit(0);
	    	    			}
	    		    		if(r.data == 0)
	    	    			{
	    	    			
	    	    			alert('Allocation Date should be greater or equal to travel receive date '+r.dt_grn);
	    	    			$('input[name="dt_config"]').focus();
	    	    			$('input[name="dt_config"]').val('');
	    	    			$('input[name="dt_config"]').addClass('error');
	    	    				exit(0);
	    	    			}
	    		    		
	    		    },'json');
		    		  }
	    	 
	    	  
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
	
	
DisplayDropDownData("M_Emp_User","userForEditFromStore",function (status){
	if(status)
	{
		DisplayDropDownData('M_Currency','currDataForEditFromStore',function (status){
			if(status)
			{
				DropDownDataDisplay('M_Model','modelDataForConfigIt',function (status){
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
					}});
					}});
			}});
			
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
jQuery("input#Config_fileID").change(function () {
	
	 /* if(typeof FormData == "undefined"){
		var formData = [];							
		formData.push("files[]", $conv(this)[0]);
		}
		else{
			var formData = new FormData();	 
			formData.append("files[]", $conv(this).prop("files")[0]);
			//formData.append('file', $('#fileID').get(0).files[0]);
		} */
	 var formData = new FormData();	 
		//formData.append("files[]", $conv(this).prop("files")[0]);
		formData.append('file', $('#Config_fileID').get(0).files[0]);
		
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
			    	
			    		$('input[name="Config_file"]').val(r.upload_inv);
			    		//bootbox.alert("File Uploaded successfully");
			    }
			},'json'); 
			
	//$( "#submitForUploadData" ).trigger( "click" );
			
}); 


</script>

<div class="commonDiv" id="SearchFromForEditFromstore">
	<input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayAssetForConfigIT('A_Config_IT')">
</div>

<div id="DisplayEditFromStore">
	<table class="table table-bordered displayDataForEditFromStore">
		<tr class="info new">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Serial Number</strong></td>
			<td><strong>Model Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong>Edit/Allocate</strong></td>
		</tr>
	</table>
</div>

<div id="EditDetailsForEditFromStore">
	<form name="SubmitFormForITConfig" id="SubmitFormForITConfig">
		<table class="table table-bordered">
				<tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center><strong>Asset  Configuration</strong></center></td>
				</tr>
					<tr>
						<td ><b>Asset Id</b></td>
						<td ><input type= "text"  name="id_wh_dyn"   size= "25" value="" readonly="readonly" class="common-validation" data-valid="mand"></td>
						<td ><b>Asset Name</b></td>
						<td ><input type= "text" name="ds_pro" size= "25" value="" readonly="readonly" class="common-validation" data-valid="mand"></td>
					</tr>
					<tr>
						<td ><b>Serial Number</b></td>
						<td ><input type="text"  name="no_mfr" size="25" value=""  readonly="readonly" class="common-validation" data-valid="mand" onBlur="CheckValWhichAllReadyExit('A_Edit_From_Store' , 'Serial number all ready exit.' , 'no_mfr')"></td>
						
						<td><strong>Model<font color="red">*</font></strong></td>
				<td>
					<select id="modelDataForConfigIt" name="id_model" class="common-validation" data-valid="mand" readonly="readonly" style="width:140" >
							<option value="">Select</option>
							
						 </select>
				</td>
						<!-- <td><strong>Model Number</strong></td>
				<td><input type="text" name="no_model" value="" ></td> -->
						
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
			<td><strong>Asset Description</strong></td>
				<td><input type="text" name="ds_asst" value=""></td>
				<td><strong>Asset Remarks</strong></td>
				<td><input type="text" name="rmk_asst" value=""></td>
				
				
				
			</tr>
			
			
			
			<tr>
			<td><strong>Configuration Expected Date<font color="red">*</font></strong></td>
				<td><input type="text" name="dt_config" id="dt_config" value="" class="common-validation datepicker" data-valid="mand"></td>
							<td ><b>AMC / Warranty</b></td>
				<td >
					<select id="warr_amc" name="warr_amc"  style="width:140" class="common-validation" data-valid="mand" onChange="ShowRowColumn(this , 'amcCommonClass')">
						
						<option value="O">NO</option>
						<option value="A">AMC</option>
						<option value="W">Warranty</option>
						
					</select>
				</td>
				
			</tr>
			<tr class="amcCommonClass">
			<td><b>Start Date<font color="red">*</font></b></td>
			<td><input type="text" name="dt_amc_start" value="" class="amcCommonClass1 grndatepicker"></td>
			<td><b>End Date<font color="red">*</font></b></td>
			<td><input type="text" name="dt_amc_exp" value="" class="amcCommonClass1 amctDatepicker"></td>
			
			</tr>
			<!--  <tr> 
			<td ><b>Computer Name<font color="red"> *</font></b></td>
						<td ><input type= "text" name="nm_com" size= "25" value="" readonly="readonly" class="common-validation" data-valid="mand"></td> 
			</tr>-->
			<tr>
				<!-- <td><strong>Department</strong></td>
				<td><input type="text" name="dept" value=""></td> -->
				<td ><b>Computer Name<font color="red">*</font></b></td>
						<td ><input type= "text" name="nm_com" size= "25" value=""  class="common-validation" data-valid="mand"></td> 
			
			<td></td><td></td>
			 <!--<td><strong>Upload Configuration Certificate</strong></td>
				<td><input id="Config_fileID" type="file" name="Config_file1" class="common-validation " value=""></td>
				 <td colspan=2"></td> -->
			
			</tr>
			
			
				<input type="hidden" name="id_wh" value="">
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="Config_file" class="common-validation " value="">
				<input type="hidden" name="dtInv" value="">
				<tr>
					<td colspan="4">
						<button name="install" type="button" style="margin-left:450px;"   class="btn btn-primary config" onclick="ControlConfigITAsset('Save','SubmitFormForITConfig','EditDetailsForEditFromStore','SearchFromForEditFromstore','DisplayEditFromStore','')">Configure</button>
						<button name="cancel" type="button"  class="btn btn-primary config" onclick="ControlConfigITAsset('Cancel','SubmitFormForITConfig','EditDetailsForEditFromStore','SearchFromForEditFromstore','DisplayEditFromStore','')">Cancel</button>
					</td>
				</tr>
				
		</table>
	</form>
</div>
