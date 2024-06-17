
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Transfer ></a><a href="#">Travel </a>-->
</div>

<script type="text/javascript">

$('#TravelRequestDetails').hide();

$(function() {
	DisplayAssetForTravelRequest();
	$('.datepicker').datepicker({
		yearRange: '1985:2025', 
		changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd",
	      autoSize: true,
	      onSelect: function(selected,evnt) {
	    	  $('#dt_st_trvlID').removeClass('error');
	    	  if($('#dt_st_trvlID').val() == '')
	    		  {
		    		    alert('Travel start date should not be empty.');
		  				$('#dt_st_trvlID').focus();
		  				$('#dt_st_trvlID').val('');
		  				$('#dt_st_trvlID').addClass('error');
		  				$('.datepicker').val('');
		  				exit(0);
	    		  }
	    	  else
	    		  {
	    		  if($('#dt_st_trvlID').val() > $(this).val())
	    			  {
			    		  alert('Travel end date should be greater or equal to travel start date');
			  				$(this).focus();
			  				$(this).val('');
			  				$(this).addClass('error');
			  				exit(0);
	    		 	 }
	    		  }
	    	  
	      }
	    });
	
	$('.datepickerForTravel').datepicker({
		yearRange: '1985:2025',  
		changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd",
	      autoSize: true,
	      onSelect: function(selected,evnt) {
	    	
	    	  var dt_st_trvl = $('#dt_st_trvlID').val();
	    	  var id = $('input[name="id"]').val();
	    
	    	$('#dt_st_trvlID').removeClass('error');
	    $.post('T_Travel_Request',{action : 'CheckDate' , dt_st_trvl : dt_st_trvl ,id:id},function (r){
	    		
	    		if(r.data == 0)
	    			{
	    			
	    			alert('Travel start date should be greater or equal to allocated date '+r.dt_alloc);
	    				$('#dt_st_trvlID').focus();
	    				$('#dt_st_trvlID').val('');
	    				$('#dt_st_trvlID').addClass('error');
	    				exit(0);
	    			}
	    		
	    },'json');
	      }
	});
	
});
</script>

<div class="commonDiv" id="displayAssetForTravelRequestSearch">
	<input type="text" name="search" value="" placeholder="Search" onkeyup="DisplayAssetForTravelRequest()">
</div>


<div id="DisplayAssetForTravelRequest">
	<table class="table table-bordered DisplayAssetForTravelRequesting">
		<tr class="info new">
			<td><strong>Asset ID</strong></td>
			<td><strong>Asset Name</strong></td>
			<td><strong>Serial Number</strong></td>
			<td><strong>Asset Description</strong></td>
			<td><strong>Transfer </strong></td>
		</tr>
	</table>
</div>


<div id="TravelRequestDetails">
	<form name="submitTravelRequest" id="submitTravelRequest">
		<table class="table table-bordered">
				<tr class="tableHeader tableHeaderContent">
					<td colspan="4"><center><strong>Travel Request Details</strong></center></td>
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
				<td><b>Travel Start Date <font color="red">*</font></b></td>
					<td>
						<input id="dt_st_trvlID" type= "text" name="dt_st_trvl" size= "25" value="" class="common-validation datepickerForTravel" data-valid="mand">
					</td>
					<td><b>Expected Return Date <font color="red">*</font></b></td>
					<td>
						<input type= "text" name="dt_expc_trvl" size= "25" value="" class="common-validation datepicker" data-valid="mand">
					</td>
				</tr>
					<tr>
				<td><strong>Remarks</strong></td>
				<td colspan="3">
					<textarea name="remarks" cols="150" rows="2" style="margin: 0px 0px 10px;width: 599px;height: 60px;" value="">
					</textarea>
				</td>
				
			</tr>
				<input type="hidden" name="action" value="Save">
					<input type="hidden" name="id" value="">
				<tr>
					<td colspan="4">
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary TrvlUTBTN" onclick="ControlTravelRequestAsset('Save','displayAssetForTravelRequestSearch','DisplayAssetForTravelRequest','TravelRequestDetails','')">Request</button>
						<button name="cancel" type="button"  class="btn btn-primary TrvlUTBTN" onclick="ControlTravelRequestAsset('Cancel','displayAssetForTravelRequestSearch','DisplayAssetForTravelRequest','TravelRequestDetails','')">Cancel</button>
					</td>
				</tr>
		</table>
	</form>
</div>


