<!--Cost_centre_report.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#"> Reports ></a><a href="#">Financial reports ></a><a href="#">Cost Center Report</a>-->
</div>

<div class="commonDiv" id="displayCostCentreReport">
	
		<table class="commonTable" align="center" width="600px" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 163px;">Cost Center Wise Report</p></td>
			</tr>
			<tr>
				<td><strong>Select Month</strong></td>
				<td >
					<select name="selectmonth" class="select"  style="width:140">
						<option value="select">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Select Year</strong></td>
				<td >
					<select name="selectyear" class="select"  style="width:140">
						<option value="select">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Select Group</strong></td>
				<td >
					<select name="selectgroup" class="select"  style="width:140">
						<option value="select">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Select Cost Center</strong></td>
				<td >
					<select name="selectcostcenter" class="select"  style="width:140">
						<option value="select">Select</option>
					</select>
				</td>
			</tr>
		</table>
			
			<br>
			
					<p style="width:180px;margin-left: 357px;float:centre;">
						<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayCostCentreReport','createCostCentreReport','')">Go</button>
					</p>
</div>

<div class="commonDiv" id="createCostCentreReport" style="display:none;">
	<form action="" name="submitCostCentreReport" >	
		<br><br><center><b><font color="red" class=navigate>Depreciation Yet To Be Calculated</font></b></center>
	</form>
</div>