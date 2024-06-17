<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Tagging ></a><a href="#">Receive Asset ></a> -->
</div>

<script type="text/javascript">
$(function (){
	
$('#DisplayAssetDataForReceiveAsset').hide();
$('#DisplayAssetDataForReceive').hide();
var currentDate = new Date();
$('.datepicker').datepicker({
	 yearRange: '1985:2025',
	changeMonth: true,
    changeYear: true,
    dateFormat: "yy-mm-dd",
    autoSize: true
  });
$('input[name="dt_to"]').datepicker("setDate", currentDate);
currentDate.setMonth(currentDate.getMonth() - 1);
$('input[name="dt_frm"]').datepicker("setDate", currentDate);

$('.datepickerReceiveAsset').datepicker({
	yearRange: '1985:2025',  
	changeMonth: true,
      changeYear: true,
      dateFormat: "yy-mm-dd",
      autoSize: true,
      onSelect: function(selected,evnt) {
    	
    	  var dt_recv = $(this).val();
    	  var id = $('input[name="id_iut"]').val();
    
    	$(this).removeClass('error');
    $.post('Receive_Asset',{action : 'CheckDate' , dt_recv : dt_recv ,id:id},function (r){
    		
    		if(r.data == 2)
    			{
    			
    			alert('Validity date should be greater or equal to transfer date '+r.dt_iut);
    				$('#dt_recvID').focus();
    				$('#dt_recvID').val('');
    				$('#dt_recvID').addClass('error');
    				exit(0);
    			}
    		
    },'json');
      }
});



DisplayDropDownData("M_Emp_User","holderID",function (status){
	if(status)
	{
		DisplayDropDownData("M_Emp_User","auth_personID",function (status){
			if(status)
			{
				DisplayDropDownData("M_Emp_User","req_byID",function (status){
					if(status)
					{
						
					}});
			}});
	}});



});
</script>

<center>
<div id="displayReceiveGatepass" class="commonDiv">
<form action="" name="SearchForGatePass1" id="SearchForGatePass1">
<table class="table table-bordered" style="width :60%">
			<tr>
					<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:130px">Select Date And Type</p></td>
			</tr>
			<tr>
				<td><strong>From Date</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
					<input id="dt_frm_For_GP" type="text" name="dt_frm" value="" class="common-validation datepicker" data-valid="mand">
					</td>
			</tr>
			<tr>
				<td><strong>To Date</strong>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
					<input id="dt_to_For_GP" type="text" name="dt_to" value="" class="common-validation datepicker" data-valid="mand">
				</td>
			</tr>
			<tr>
				<td><strong>Type</strong>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
					 <select name="type_tran" class="common-validation" data-valid="mand">
						<option value="">Select</option>
						<option value="Temporary">Temporary</option>
						<option value="Permanent">Permanent</option>
						<!-- <option value="reexport">Re-Export</option> -->
					</select>
				</td>
				<input type="hidden" name="action" value="Display">
			</tr>
			<tr>
					<td colspan="2">
						<button name="next" type="button" style="margin-left:300px;" class="btn btn-primary" onclick="ControlDivForReceivAsset('Next','DisplayAssetDataForReceive','displayReceiveGatepass','SearchForGatePass1','')">Next</button>
					</td>
				</tr>
		</table>
		
</form>		
	</div>

</center>
<div class="commonDiv" id="ReceiveGatepass">

<div id="DisplayAssetDataForReceive">

<table class="table table-bordered DisplayAssetDataForReceiveAssetClass">
						<tr>
						</tr>
					</table>
</div>

<div id="DisplayAssetDataForReceiveAsset">

	<form action="" name="submitReciveAsset" id="submitReciveAsset">	
	
		<table class="table table-bordered commonTable" align="center" width="80%" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:370px">Asset Details For Receive</p>
				</td>
			</tr>
			<tr>
				<td><strong>Asset ID</strong></td>
				<td><input type="text" name="id_wh_dyn" value="" readonly></td>
				<td><strong>Asset Name</strong></td>
				<td><input type="text" name="ds_pro" value="" readonly></td>
			</tr>
			<tr>
				<td><strong>Serial Number</strong></td>
				<td><input type="text" name="no_mfr" value="" readonly></td>
				<td><strong>Holder</strong></td>
				<td>
				 <select id="holderID" name="holder" disabled>
						<option value="">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				
				<td><strong>Authorizing Person</strong></td>
				<td>
				<select id="auth_personID" name="auth_person" disabled>
						<option value="">Select</option>
					</select>	
				</td>
				<td><strong>Requested by</strong></td>
				<td>
				<select id="req_byID" name="req_by" disabled>
						<option value="">Select</option>
					</select>	
				</td>
			</tr>
			<tr>
				
				<td><strong>Validity of Gate Pass</strong> </td>
				<td><input type="text" name="dt_gp_vldty" value="" readonly></td>
				<td><strong>Receive Date<font color="red">*</font></strong></td>
				<td><input id="dt_recvID" type="text" name="dt_recv" value="" class="common-validation datepickerReceiveAsset" data-valid="mand"></td>
			</tr>
			<input type="hidden" name="action" value="Save">
			<input type="hidden" name="id_iut" value="">
			<input type="hidden" name="id_gp" value="">
			<input type="hidden" name="id_wh" value="">
			<tr>
				<td colspan="4">
					<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlDivForReceivAsset('Save','DisplayAssetDataForReceive','DisplayAssetDataForReceiveAsset','submitReciveAsset','')">Save</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDivForReceivAsset('Cancel','DisplayAssetDataForReceive','DisplayAssetDataForReceiveAsset','submitReciveAsset','')">Cancel</button>
					
				</td>
			</tr>
		</table>
		
		<br>
		<br>
		
	</form>
</div>
</div>