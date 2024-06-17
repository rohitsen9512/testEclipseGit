<Monthly_depreciation.jsp>
<!-- <div class="commonDiv" style="padding:10px;color:blue;">
	Depreciation >Monthly Depreciation 
</div> -->

<script type="text/javascript">
$(function (){
	
	//DisplayDropDownData('D_Dep_Config','typeIdForMonthlyDprn','');
	DropDownForDeprnType('D_Dep_Config','DataForYearDprn','YearOnly');
	DisplayDropDownDataForGroup('M_Asset_Div','assetDivForMonthlyDprn','CapG',function (status){
		if(status)
		{
			
		}});
	
})

</script>


<div class="card-small" id="displayMonthlyDepreciation">
<form action="" name="SubmitMonthlyDprn" id="SubmitMonthlyDprn">	
<div class="card-header new">
				<h3 class="card-title1">Monthly Depreciation For Company Act</h3>
			</div>
		<!-- <table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:100px">Monthly Depreciation For Company Act</p></td>
			</tr> -->
			<table class="table table-bordered " style="width: 100%;">
			<tr>
				<td colspan="2"><b>Select Depreciation Type<font color="red">*</font></b></td>
				<td colspan="2">
				<select name="type_id"  style="width:140" class="form-control" data-valid="mand">
						<option value="CA">Company Act</option>
						
					</select>
				
				<!--  
					<select id="typeIdForMonthlyDprn" name="type_id"  style="width:140" class="form-control" data-valid="mand">
						<option value="">Select</option>
						
					</select>
					-->
				</td>
			</tr>
			<tr>
				<td colspan="2"><b>Select Year & Month<font color="red">*</font></b></td>
				<td colspan="2">
					<select id="DataForYearDprn" name="yearForDprn" style="width:140" class="form-control" data-valid="mand">
						<option value="">Select</option>
						
					</select>
					<select name="monthForDprn"  style="width:151" class="form-control" data-valid="mand">
						<option value="">Select</option>
						<option value="01">January</option>
						 <option value="02">February</option>
						 <option value="03">March</option>
						 <option value="04">April</option>
						 <option value="05">May</option>
						 <option value="06">June</option>
						 <option value="07">July</option>
						 <option value="08">August</option>
						 <option value="09">September</option>
						 <option value="10">October</option>
						 <option value="11">November</option>
						 <option value="12">December</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><b>Select Group<font color="red">*</font></b></td>
				<td colspan="2" ><strong>All </strong><input  class="form-control monthDprnForAction" data-valid="mand" type="checkbox" name="all" onClick="selectAll('monthDprnForAction','checkAllListForMonthDprn');">
			</tr>
			<tr>
				<td  colspan="2"></td>
				<td>
					<select id="assetDivForMonthlyDprn" multiple="multiple" name="id_grp" class="form-control checkAllListForMonthDprn" data-valid="mand">
						<option value=""></option>
						
					</select>
					<input type="hidden" name="dep_period" value="monthly">
				</td>
			</tr>
		</table>
	
		<br>
	
			<p style="width:180px;margin-left: 457px;float:center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDivForDprn('SubmitMonthlyDprn')">Process</button>
			</p>
	</form>
</div>

<div class="card" id="createMonthlyDepreciation" style="display:none;">
	<form action="" name="submitMonthlyDepreciation">
	<br>
	<br>
	<br>
	<br>
		<font color="blue"><center>Depreciation Processing Completed</center></font>
	</form>
</div>