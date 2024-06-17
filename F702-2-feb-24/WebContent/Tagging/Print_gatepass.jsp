<!--Print_gatepass.jsp  -->

<script type="text/javascript">

$(function (){
var currentDate = new Date();
	$('.datepicker').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd",
	      autoSize: true
	    });
	$('input[name="dt_to"]').datepicker("setDate", currentDate);
	currentDate.setDate(currentDate.getDate() - 1);
	$('input[name="dt_frm"]').datepicker("setDate", currentDate);
	
	DisplayAssetForPrintgatePass('T_Print_Gate_Pass','displayAssetForPrintGatePass');
	
});	
</script>	
<div class="commonDiv" id="displayPrintGatepass">
	
		<table width="100%" height="100%">
			<tr>
				<td>
				
					<strong>From Date</strong>&nbsp;
					<input  type="text" name="dt_frm" value="" class="common-validation datepicker" >
					&nbsp;&nbsp;<strong>
					To Date</strong>&nbsp;
					<input  type="text" name="dt_to" value="" class="common-validation datepicker" >
					&nbsp;<strong>Type&nbsp;</strong>
					 <select name="type_tran" class="common-validation" >
						<option value="">Select</option>
						<option value="Permanent">Permanent</option>
						<option value="Temporary">Temporary</option>
						
					</select>
					<button type="button" style="margin-top: -10px;margin-left: 10px;" class="btn btn-primary" onclick="DisplayAssetForPrintgatePass('T_Print_Gate_Pass','displayAssetForPrintGatePass')">Search </button>
				
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered displayAssetForPrintGatePass">
						<tr>
						</tr>
					</table>
				</td>
			</tr>
		</table>
			
</div>
