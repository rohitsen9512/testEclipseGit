<Discrepancy_report.jsp>
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Tagging ></a><a href="#">Discrepancy Report ></a> -->
</div>

<div class="commonDiv" id="displayDiscrepancyReport">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:191px">Discrepency Report</p></td>
			</tr>
			<tr>
				<td><b>Location<font color="red">*</font></b></td>
				<td>
					<select name="location" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
				<td><b>Select Sub Location<font color="red">*</font></b></td>
				<td>
					<select name="selectsublocation" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Facility<font color="red">*</font></b></td>
				<td>
					<select name="facility" class="select"  style="width:140">
						<option value="select">Select</option>
					</select>
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td><b>Year<font color="red">*</font></b></td>
				<td>
					<select name="year" class="select"  style="width:140">
						<option value="select">Selec</option>
					</select>
				</td>
				<td><b>Year<font color="red">*</font></b></td>
				<td>
					<select name="year" class="select"  style="width:140">
						<option value="select">Select</option>
					</select>
				</td>
			</tr>
	</table>
	
	<br>
	
				<p style="width:180px;margin-left: 446px;float:center;">
					<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayDiscrepancyReport','createDiscrepancyReport','')">Next</button>
				</p>
</div>

<div class="commonDiv" id="createDiscrepancyReport" style="display:none;">
	<form action="" name="submitDiscrepancyReport">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="13" class="tableHeader"><p class="tableHeaderContent" style="margin-left:492px">Misplaced Asset Details</p></td>
			</tr>
			<tr  style="background-color: blue;">
				<td><strong>S No.</strong></td>
				<td ><strong>Asset Id</strong></td>
				<td ><strong>Asset Name</strong></td>
				<td><strong>Asset Desc</strong></td>
				<td><strong>Assigned Loc</strong></td>
				<td><strong>CurrentLoc</strong></td>
				<td><strong>AssignedSub Loc</strong></td>
				<td><strong>Current SubLoc</strong></td>
				<td><strong>Assigned Facility</strong></td>
				<td><strong>Current Facility</strong></td>
				<td><strong>Assigned Cubicle</strong></td>
				<td><strong>Current Cubicle</strong></td>
				<td><strong>Select</strong></td>
			</tr>
			<tr>
				<td  colspan="13" ><font color="red"><center>Records not available</center></font></td>
			</tr>
		</table>
		
		<br>
		<br>
		
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="7" class="tableHeader"><p class="tableHeaderContent" style="margin-left:176px">Missing Asset Details</p></td>
			</tr>
			<tr  style="background-color: blue;">
				<td><strong>S No.</strong></td>
				<td ><strong>Asset Id</strong></td>
				<td ><strong>Asset Name</strong></td>
				<td><strong>AssetDesc</strong></td>
				<td><strong>Location</strong></td>
				<td><strong>SubLocation</strong></td>
				<td><strong>AssignedTo</strong></td>
			</tr>
			<tr>
				<td  colspan="7" ><font color="red"><center>Records not available</center></font></td>
			</tr>
		</table>
		
	</form>
</div>