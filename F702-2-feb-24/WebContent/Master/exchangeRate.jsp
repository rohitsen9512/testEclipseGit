<!--location.jsp-->
<div style="padding:10px;">
	<!-- <a href="#">Master ></a><a href="#"> Exchange Rate</a> -->
</div>

<script type="text/javascript">

$(function() {
	DisplayData('M_Exchange_Rate','displayExchangeRate','createExchangeRate');
	$('button[name="update"]').addClass('hideButton');
	
	
         $('.datepicker').datepicker({
        	 yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	    });
	
});

DisplayDropDownData('M_Currency','currForExchangeRate',function (status){
	if(status)
		{
		
		}});
		
function checkCurrencyExits(id_curr)
{
	$.post('M_Exchange_Rate',{action : 'CheckExitsVal',id_curr : id_curr.value},function (r){
		
		if(r.data ==0)
			{
			alert('You have already \'Echange Rate\' of this currency. Please choose anothere one.');
			}
	
	},'json');
}
		
</script>

<div id="displayExchangeRate">
		
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayExchangeRate','createExchangeRate','submitExchangeRate','M_Exchange_Rate')">Create Exchange Rate</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover exchangeRateForDisplay">
						<tr class="new">
							<td><strong>Location Name</strong></td>
							<td><strong>Location Code</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createExchangeRate" style="display:none;">
	<form action="" name="submitExchangeRate" id="submitExchangeRate">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 170px;">Exchange Rate Details</p></td>
			</tr>
			<tr>
				<td><strong>Currency <font color="red">*</font></strong></td>
				<td>
				<select id="currForExchangeRate" name="id_curr" onchange="checkCurrencyExits(this)">
				<option value="">Select</option>
				</select>
				</td>
			</tr>
			<tr style="display:none;">
				<td><strong>Date<font color="red">*</font></strong></td>
				<td><input  class="common-validation datepicker" type="text" name="month_year" value="01/01/1900" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Rate<font color="red">*</font></strong></td>
				<td><input  class="common-validation" type="text" name="rate" data-valid="num" onblur="InsuranceAndFrightCheck('rate')"></td>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
			</tr>
			<tr>
				<td colspan="2">
						<button name="save" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Save','displayExchangeRate','createExchangeRate','submitExchangeRate','M_Exchange_Rate','','')">Save</button>
						<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displayExchangeRate','createExchangeRate','submitExchangeRate','M_Exchange_Rate','','')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayExchangeRate','createExchangeRate','submitExchangeRate','M_Exchange_Rate')">Back</button>
					</td>
			</tr>
		</table>
	</form>
</div>