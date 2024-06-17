
<div class="commonDiv" style="padding:10px;color:blue;">
	<!--  Depreciation > Depreciation >Company Act Monthly & Yearly -->
</div>
<script type="text/javascript">

$(function() {
	
	DepConfigForDiplayData('CA');
});
</script>

<center>
<div class="card" id="displayDepConfigIT">
<form action="" name="displayDepConfigForIT" id="displayDepConfigForCA">
		<table class="table table-bordered" >
			<tr>
				<td colspan="2" class="tableHeader new"><p class="tableHeaderContent" style="margin-left:450px"> Depreciation Act Name - Company Act Monthly & Yearly</p></td>
			</tr>
			
			<tr >
				<td style="width : 550px;"><strong>Method of Depreciation</strong></td>
				<td>SLM - Straight Line Method
				<input type="hidden" name="dep_method" value="SLM">
					<!-- <select  name="dep_method" >
					<option value="SLM">(SLM) - Straight Line Method</option>
					<option  value="WDV">(WDV) - Written down Value</option>
					</select> -->
				</td>
			</tr>
			
			<tr >
				<td><strong>Depreciation Based on</strong></td>
				<td>Rate
				<input type="hidden" name="dep_based_on" value="Rate">
					<!-- <select  name="dep_based_on">
					<option value="Rate">Rate</option>
					<option value="LifeSpan">Life of the Asset</option>
					</select> -->
				</td>
			</tr>
			<tr >
				<td><strong>Capitalization Date</strong></td>
				<td>Installation Date
				<input type="hidden" name="capitalization_date" value="dt_allocate">
					<!--<select name="capitalization_date">
					 <option value="dt_allocate" >Installation Date</option>
					 
					<option value="grndate" >GRN Date</option>
					<option value="Bond_Date" >Date of Bonding</option>
					<option value="dep_date" >Depreciation Date</option>
					
					</select> -->
				</td>
			</tr>
			<tr >
				<td><strong>Components to be considered for Capitalization Value</strong></td>
				<td>Asset Value
				<input type="hidden" name="capitalization_comp1" value="val_asst">
					<!-- <select  name="capitalization_comp1" multiple id="capitalization_comp2">
					<option value="val_asst">assetvalue</option>
					 
					<option value="insurance_charge" >Insurance Charge</option>
					<option value="freight_rate" >freight_rate</option>
					<option value="additional_cost" >additional_cost</option>
					
					<option value="asset_cost" >asset_cost</option>
					
					</select> -->
				</td>
			</tr>
			<tr >
				<td><strong>Start Date of depreciation the date to be considered as the start date of depreciation.</strong></td>
				<td>
					<select  name="dep_start">
					<option value="Actual_Date" >Actual Date</option>
					<option value="First_Day" >First day of the Month</option>
					<option value="First_Day_Next_Month" >First day of the Next Month</option>
					<option value="First_Day_Half_Break" >If first half First day of month else first day of next Month</option>
					</select>
				</td>
			</tr>
			
			<tr >
				<td><strong>Write off Value</strong></td>
				<td><input type="text" size="12" value="" name="write_off_value" class="form-control" data-valid="num" onblur="InsuranceAndFrightCheck('write_off_value')"></td>
			</tr>
			<!-- <tr>
				<td><strong>Stop depreciation at depreciation will not be considered when the asset value reaches this amount</strong></td>
				<td>Rs.<input type="text"  value="" name="dep_stop" class="form-control" data-valid="num" onblur="InsuranceAndFrightCheck('dep_stop')"></td>
			</tr> -->
			<tr>
				<td><strong>No of days to be considered</strong></td>
				<td>
					<select name="dep_no_of_days">
					<option  value="Actual">Actual No. of days</option>
					<option  value="Fixed">Equal for all months</option>
					</select>
				</td>
			</tr>
			<tr>
			<td colspan="2">
			
			<input type="hidden" name="action" value="Save">
			<input type="hidden" name="dep_act_name" value="Company Act">
			<input type="hidden" name="type_id" value="CA">
			
			<button type="button" style="margin-left : 400px;"  class="btn btn-primary" onclick="AddDepreciationConfig('displayDepConfigForCA' , 'CA')">Save</button>
			</td>
			
			</tr>
			
		</table>
	</form>	
	
			
</div>
</center>

