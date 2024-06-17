
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Transfer ></a><a href="#">Travel > Receive Asset</a>-->
</div>

<script type="text/javascript">

$('#TravelReceiveDetails').hide();

$(function() {
	DisplayAssetForTravelReceive();
	 $('.datepicker').datepicker({
			yearRange: '1985:2025',  
			changeMonth: true,
		      changeYear: true,
		      dateFormat: "yy-mm-dd",
		      autoSize: true,
		      onSelect: function(selected,evnt) {
		    	
		    	  var dt_st_trvl = $(this).val();
		    	  var id = $('input[name="id"]').val();
		    
		    	$(this).removeClass('error');
		    $.post('Travel_Receive_Asset',{action : 'CheckDate' , dt_st_trvl : dt_st_trvl ,id:id},function (r){
		    		if(r.data == 0)
		    			{
		    			alert('Receive date should be greater or equal to Transfer start date '+r.dt_st_trvl);
		    				$('#dt_retrn_trvlID').focus();
		    				$('#dt_retrn_trvlID').val('');
		    				$('#dt_retrn_trvlID').addClass('error');
		    				exit(0);
		    			}
		    		
		    },'json');
		      }
		});
		
	
});
</script>

<div class="commonDiv" id="displayAssetForTravelReceiveSearch">
	<input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForTravelReceive()">
</div>


<div id="DisplayAssetForTravelReceive">
	<table class="table table-bordered DisplayAssetForTravelReceive">
		<tr class="info new">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Serial Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong>Transfer</strong></td>
		</tr>
	</table>
</div>


<div id="TravelReceiveDetails">
	<form name="submitTravelReceive" id="submitTravelReceive">
		<table class="table table-bordered">
				<tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center><strong>Travel Receive Asset Details</strong></center></td>
				</tr>
				<tr>
				<td ><b>Asset Id</b></td>
					<td ><input type="text"  name="id_wh_dyn" size="25" value="" readonly ></td>
					<td ><b>Asset Name</b></td>
					<td ><input type= "text" name="ds_pro" size= "25" value="" readonly></td>
					
				</tr>
				<tr>
					
					<td ><b> Employee Name</b></td>
					<td >
					<input type= "text" name="nm_emp" size= "25" value="" readonly>
					<td ><b> Employee ID</b></td>
					<td >
					<input type= "text" name="cd_emp" size= "25" value="" readonly>
					</td>
				</tr>
				
				<tr>
				<td><b>Travel Start Date</b></td>
					<td>
						<input type= "text" name="dt_st_trvl" size= "25" value="" readonly>
					</td>
					<td><b>Expected Return Date</b></td>
					<td>
						<input type= "text" name="dt_expc_trvl" size= "25" value="" readonly>
					</td>
				</tr>
				<tr>
				<td><b>Receive Date <font> color="red">*</font></b></td>
					<td>
						<input id="dt_retrn_trvlID" type= "text" name="dt_retrn_trvl" size= "25" value="" class="common-validation datepicker" data-valid="mand">
					</td>
					<td colspan="2"></td>
				</tr>
					<!-- <tr>
				<td><strong>Remarks</strong></td>
				<td colspan="3">
					<textarea name="remarks" cols="150" rows="2" style="margin: 0px 0px 10px;width: 599px;height: 60px;" value="">
					</textarea>
				</td>
				
			</tr> -->
				<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" value="">
					<input type="hidden" name="id_wh" value="">
				<tr>
					<td colspan="4">
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlTravelReceiveAsset('Save','displayAssetForTravelReceiveSearch','DisplayAssetForTravelReceive','TravelReceiveDetails','')">Receive</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlTravelReceiveAsset('Cancel','displayAssetForTravelReceiveSearch','DisplayAssetForTravelReceive','TravelReceiveDetails','')">Cancel</button>
					</td>
				</tr>
		</table>
	</form>
</div>


