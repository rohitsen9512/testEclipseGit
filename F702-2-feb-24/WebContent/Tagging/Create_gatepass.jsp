<!--Create_gatepass.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Tagging ></a><a href="#">Create Gate Pass ></a>-->
</div>

<script type="text/javascript">
$(function (){
	
$('#DisplayAssetDataForCreateGatePass').hide();
$('#DisplayAssetDataForView').hide();

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

$('.datepickerGatePass').datepicker({
	yearRange: '1985:2025',  
	changeMonth: true,
      changeYear: true,
      dateFormat: "yy-mm-dd",
      autoSize: true,
      onSelect: function(selected,evnt) {
    	
    	  var dt_gp_vldty = $(this).val();
    	  var id = $('input[name="id_iut"]').val();
    
    	$(this).removeClass('error');
    $.post('T_Create_Gate_Pass',{action : 'CheckDate' , dt_gp_vldty : dt_gp_vldty ,id:id},function (r){
    		
    		if(r.data == 2)
    			{
    			
    			alert('Validity date should be greater or equal to transfer date '+r.dt_iut);
    				$('#dt_gp_vldtyID').focus();
    				$('#dt_gp_vldtyID').val('');
    				$('#dt_gp_vldtyID').addClass('error');
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
<div id="displayCreateGatepass" class="commonDiv">
<form action="" name="SearchForGatePass" id="SearchForGatePass">
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
						<button name="next" type="button" style="margin-left:300px;" class="btn btn-primary" onclick="ControlDivForGatePass('Next','CreateGatepass','displayCreateGatepass','SearchForGatePass','')">Next</button>
					</td>
				</tr>
		</table>
		
</form>		
	</div>

</center>
<div class="commonDiv" id="CreateGatepass">

<div id="DisplayAssetDataForView">

<table class="table table-bordered DisplayAssetDataForCreateGatePassClass">
						<tr>
						</tr>
					</table>
</div>

<div id="DisplayAssetDataForCreateGatePass">

	<form action="" name="submitCreateGatepass" id="submitCreateGatepass">	
	
		<table class="table table-bordered commonTable" align="center" width="80%" height="100%" border="1">
			<tr>
				<td><strong>Asset ID</strong></td>
				<td><input type="text" name="id_wh_dyn" value="" readonly></td>
				<td><strong>Asset Name</strong></td>
				<td><input type="text" name="ds_pro" value="" readonly></td>
			</tr>
			<tr>
				<td><strong>Serial Number</strong></td>
				<td><input type="text" name="no_mfr" value="" readonly></td>
				<td><strong>Asset Type</strong></td>
				<td><input type="text" name="typ_asst" value="" readonly></td>
			</tr>
			<tr>
				<td><strong>Holder</strong><font color="red">*</font></td>
				<td>
				 <select id="holderID" name="holder" class="common-validation" data-valid="mand">
						<option value="">Select</option>
					</select>
				</td>
				<td><strong>Authorizing Person</strong><font color="red">*</font></td>
				<td>
				<select id="auth_personID" name="auth_person" class="common-validation" data-valid="mand">
						<option value="">Select</option>
					</select>	
				</td>
			</tr>
			<tr>
				<td><strong>Requested by</strong><font color="red">*</font></td>
				<td>
				<select id="req_byID" name="req_by" class="common-validation" data-valid="mand">
						<option value="">Select</option>
					</select>	
				</td>
				<td><strong>Validity of Gate Pass</strong><font color="red">*</font></td>
				<td><input id="dt_gp_vldtyID" type="text" name="dt_gp_vldty" value="" class="common-validation datepickerGatePass" data-valid="mand" ></td>
			</tr>
			<input type="hidden" name="action" value="Save">
			<input type="hidden" name="id_iut" value="">
			<input type="hidden" name="type_tran" value="">
			<input type="hidden" name="id_wh" value="">
			<tr>
				<td colspan="2">
					<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlDivForGatePass('Save','DisplayAssetDataForView','DisplayAssetDataForCreateGatePass','submitCreateGatepass','')">Save</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDivForGatePass('Cancel','DisplayAssetDataForView','DisplayAssetDataForCreateGatePass','submitCreateGatepass','')">Cancel</button>
					
				</td>
			</tr>
		</table>
		
		<br>
		<br>
		
	</form>
</div>
</div>