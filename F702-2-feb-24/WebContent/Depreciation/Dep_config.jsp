<Dep_config.jsp>
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Depreciation ></a><a href="#">Depreciation ></a><a href="#">US GAAP </a> -->
</div>
<script type="text/javascript">

$(function() {
	
	DepConfigForDiplayData('USGAAP');
});
</script>
<center>
<div class="card" id="displayDepConfigIT">
<form action="" name="displayDepConfigForIT" id="displayDepConfigForUSGAAP">
		<table class="table table-bordered" >
			<tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:237px"> Depreciation US GAAP Config</p></td>
			</tr>
			
			<tr >
				<td style="width : 550px;"><strong>Method of Depreciation</strong></td>
				<td>
					<select  name="dep_method" >
					<option value="SLM">(SLM) - Straight Line Method</option>
					<option  value="WDV">(WDV) - Written down Value</option>
					</select>
				</td>
			</tr>
			
			<tr >
				<td><strong>Depreciation Based on</strong></td>
				<td>
					<select  name="dep_based_on">
					<option value="Rate">Rate</option>
					<option value="LifeSpan">Life of the Asset</option>
					</select>
				</td>
			</tr>
			<tr >
				<td><strong>Capitalization Date</strong></td>
				<td>
					<select name="capitalization_date">
					<option value="dt_allocate" >Installation Date</option>
					<option value="grndate" >GRN Date</option>
					<option value="Bond_Date" >Date of Bonding</option>
					<option value="dep_date" >Depreciation Date</option>
					</select>
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
				<td><strong>Components to be considered for Capitalization Value</strong></td>
				<td>
					<select  name="capitalization_comp1" multiple id="capitalization_comp2">
					<option value="insurance_charge" >Insurance Charge</option>
					<option value="freight_rate" >Freight Rate</option>
					<option value="additional_cost" >Additional Cost</option>
					<option value="val_asst">Asset Value</option>
					<option value="asset_cost" >Asset Cost</option>
					
					</select>
				</td>
			</tr>
			<tr >
				<td><strong>Write off Value</strong></td>
				<td>Rs.<input type="text" size="12" value="" name="write_off_value" class="form-control" data-valid="num"></td>
			</tr>
			<tr>
				<td><strong>Stop depreciation at depreciation will not be considered when the asset value reaches this amount</strong></td>
				<td>Rs.<input type="text"  value="" name="dep_stop" class="form-control" data-valid="num"></td>
			</tr>
			<tr>
				<td><strong>No of days to be considered</strong></td>
				<td>
					<select name="dep_no_of_days">
					<option  value="Actual">Actual No. of days</option>
					<option  value="Fixed">Equal for all months</option>
					</select>
				</td>
			</tr>
			<td colspan="2">
			
			<input type="hidden" name="action" value="Save">
			<input type="hidden" name="dep_act_name" value="US GAAP">
			<input type="hidden" name="type_id" value="USG">
			
			<button type="button" style="margin-left : 400px;"  class="btn btn-primary" onclick="AddDepreciationConfig('displayDepConfigForUSGAAP' , 'USGAAP')">Save</button>
			</td>
			
			</tr>
			
		</table>
	</form>	
	
			
</div>
</center>

