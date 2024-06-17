<Yearly_depreciation.jsp>
<!-- <div class="commonDiv" style="padding:10px;color:blue;">
	Depreciation >Yearly Depreciation 
</div> -->
<script type="text/javascript">
$(function (){
	
	DisplayDropDownData('D_Dep_Config','typeIdForYearlyDprn',function (status){
		if(status)
		{
			DisplayDropDownDataForGroup('M_Asset_Div','assetDivForYearlyDprn','CapG',function (status){
				if(status)
				{
					
				}});
		}});
	
	DropdownResultForFinanceYear('financeYear');
})

</script>


<div class="card-small" id="displayYearlyDepreciation">
	<form action="" name="SubmitYearlyDprn" id="SubmitYearlyDprn">
	<div class="card-header new">
				<h3 class="card-title1">Yearly Depreciation For Company Act</h3>
			</div>
		<!-- <table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:224px">Yearly Depreciation</p></td>
			</tr> -->
			<table class="table table-bordered " style="width: 100%;">
			<tr>
				<td colspan="2"><b>Select Depreciation Type<font color="red">*</font></b></td>
				<td colspan="2">
					<select id="typeIdForYearlyDprn" name="type_id" style="width:140" class="form-control" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><b>Select Financial Year<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="finYear" id="financeYear"  style="width:140" class="form-control" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><b>Select Group<font color="red">*</font></b></td>
				<td colspan="2" ><strong>All </strong><input  type="checkbox" name="all" value="all" class="yearlyDprnForAction" onClick="selectAll('yearlyDprnForAction','checkAllListForYearlyDprn');">
			</tr>
			<tr>
				<td  colspan="2"></td>
				<td>
					<select multiple="multiple" id="assetDivForYearlyDprn" name="id_grp" class="form-control checkAllListForYearlyDprn" data-valid="mand">
						<option value="">Select</option>
						
					</select>
					<input type="hidden" name="dep_period" value="yearly">
				</td>
			</tr>
		</table>
	
		<br>
	
			<p style="width:180px;margin-left: 457px;float:center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDivForDprn('SubmitYearlyDprn')">Process</button>
			</p>
	</form>
</div>

<div class="commonDiv" id="createYearlyDepreciation" style="display:none;">
	<form action="" name="submitYearlyDepreciation">
	<br>
	<br>
	<br>
	<br>
		<font color="blue"><center>Depreciation Processing Completed</center></font>
	</form>
</div>