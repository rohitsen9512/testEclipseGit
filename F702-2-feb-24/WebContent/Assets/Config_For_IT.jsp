<!--Install_asset.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Assets > </a><a href="#">IT Configuration > </a><a href="#">Send For IT Configuration</a> -->
</div>

<script type="text/javascript">

$(function() {
	
	
	var currentDate = new Date();
	$( ".datepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	    });
	
	
	$('button[name="update"]').addClass('hideButton');
	$('#InstallDetails').addClass('hideButton');
	
	$('.datepickerForInstall').datepicker({
		yearRange: '1985:2025',  
		changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function(selected,evnt) {
	    	
	    	  var dt_grn = $('#dt_allocId').val();
	    	  var id = $('input[name="id"]').val();
	    	var id_grn = $('input[name="id_grn"]').val();
	    	$('#dt_allocId').removeClass('error');
	    $.post('A_Install',{action : 'CheckDate' , dt_grn : dt_grn ,id_grn : id_grn,id:id},function (r){
	    		
	    		if(r.data == 2)
	    			{
	    			
	    			alert('Allocation Date should be greater or equal to GRN date '+r.dt_grn);
	    				$('#dt_allocId').focus();
	    				$('#dt_allocId').val('');
	    				$('#dt_allocId').addClass('error');
	    				exit(0);
	    			}
	    		if(r.data == 3)
    			{
    			
    			alert('Allocation Date should be greater or equal to Un Installed date '+r.dt_grn);
    				$('#dt_allocId').focus();
    				$('#dt_allocId').val('');
    				$('#dt_allocId').addClass('error');
    				exit(0);
    			}
	    		if(r.data == 0)
    			{
    			
    			alert('Allocation Date should be greater or equal to travel receive date '+r.dt_grn);
    				$('#dt_allocId').focus();
    				$('#dt_allocId').val('');
    				$('#dt_allocId').addClass('error');
    				exit(0);
    			}
	    		
	    },'json');
	      }
	});	
	
	
		DisplayDropDownData("M_Subloc","subLocationForInstall",function (status){
			if(status)
			{
				DropDownDataDisplay("M_Cost_Center","costCenterForSingleInstall",function (status){
					if(status)
					{
						DisplayAssetForConfigureForIT("A_Conifg_For_IT");
					}});
		}});
	
	
});
</script>


<div class="commonDiv" id="displayAssetForInstallSearch">
	<input type="text" name="search" value="" placeholder="Search......" onkeyup="DisplayAssetForConfigureForIT('A_Conifg_For_IT')">
</div>
<div id="DisplayAssetForInstall">
	<table class="table table-bordered DisplayAssetForInstallation1">
		<tr class="info new">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Model Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong>Install It</strong></td>
		</tr>
	</table>
</div>


<!--  <div id="InstallDetails">
<form name="InstallToUser" id="InstallToUser">
	<table class="table table-bordered">
			<tr class="tableHeader tableHeaderContent">
				<td colspan="4"><center><strong>Asset Installation Details</strong></center></td>
			</tr>
				<tr>
					<td ><b>Asset ID</b></td>
					<td ><input type= "text"  name="id_wh_dyn"   size= "25" value="" readonly class="common-validation" data-valid="mand"></td>
					<td ><b>Asset Name</b></td>
					<td ><input type= "text" name="ds_pro" size= "25" value="" readonly class="common-validation"></td>
				</tr>
				<tr>
					<td ><b>Serial Number</b></td>
					<td ><input type="text"  name="no_mfr" size="25" value="" readonly class="common-validation"></td>
					<td ><b>Manufacturer</b></td>
					<td ><input type="text"  name="mfr" size="25" value="" readonly class="common-validation"></td>
				</tr>
				<tr>
					<td ><b>Asset Remarks</b></td>
					<td ><textarea rows="2" name="rmk_asst" cols="17"  style="width:136" value =""  class="common-validation"></textarea></td>
					<td ><b>Asset Description</b></td>
					<td ><textarea rows="2" name="ds_asst" cols="17"  style="width:136"   value =""  class="common-validation"></textarea></td>
				</tr>
				<tr>
					<td><b>Allocation Date<font color = 'Red'>*</font></b></td>
					<td><input id="dt_allocId" type=  "text" name=  "dt_alloc" value="" size="21" class="common-validation datepickerForInstall" data-valid="mand"></td>
					<td><b>Assign To<font color = 'Red'>*</font></b></td>
					<td> <select id="userForInstall" name = "to_assign" class="common-validation" data-valid="mand">
						<option value = "" > -- Select --</option>
					</select></td>			
				</tr>
				<tr>

				<td><b>City<font color = 'Red'>*</font></b></td>
				<td>
					<select id="locationForInstall" name = "id_loc" onChange="DisplaySubDropDownData(this,'subLocationForInstall','M_Subloc')" class="common-validation" data-valid="mand">
							<option value = "" > -- Select --</option>
					</select>
				</td>
				<td><b>Unit<font color = 'Red'>*</font></b></td>
				<td>
					<select id="subLocationForInstall" name = "id_subl" onChange="DisplaySubDropDownData(this,'floorForInstall','M_Floor')" class="common-validation" data-valid="mand">
							<option value = "" > -- Select --</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Block<font color = 'Red'>*</font></b></td>
				<td>
					<select id="floorForInstall" name = "id_flr"  style="width:140" class="common-validation" data-valid="mand">
						<option value = "" > -- Select --</option>
					</select>
				</td>
				<td><b>Cost Center<font color = 'Red'>*</font></b></td>
				<td><select id="costCenterForSingleInstall" name = "id_cc"  style="width:140" class="common-validation"  data-valid="mand" >
									<option value = "" > -- Select --</option>
				</select></td> 
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="">
				<input type="hidden" name="id_grn" value="">
				
			</tr>
			<tr>
				<td colspan="4">
					<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary installUnInstall" onclick="ControlInstallAsset('Save','displayAssetForInstallSearch','DisplayAssetForInstall','InstallDetails','')">Install</button>
					<button name="cancel" type="button"  class="btn btn-primary installUnInstall" onclick="ControlInstallAsset('Cancel','displayAssetForInstallSearch','DisplayAssetForInstall','InstallDetails','')">Cancel</button>
				</td>
			</tr>
	</table>
</form>
</div>
-->















