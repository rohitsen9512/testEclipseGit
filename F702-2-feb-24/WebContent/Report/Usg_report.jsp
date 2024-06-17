<!--Usg_report.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#"> Reports ></a><a href="#">Financial reports ></a><a href="#">USG Report</a>-->
</div>

<div class="commonDiv" id="displayUsgReport">
	
		<table class="commonTable" align="center" width="600px" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 132px;">USGAPP Monthly FA Report</p></td>
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
				<td colspan="4">
					<p style="width:180px;margin-left: 105px;float:centre;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayUsgReport','createUsgReport','')">Go</button>
					</p>
				</td>
			</tr>
		</table>
</div>

<div class="commonDiv" id="createUsgReport" style="display:none;">
	<form action="" name="submitUsgReport" >	
		<br><br><center><b><font color="red" class=navigate>Depreciation Yet To Be Calculated</font></b></center>
	</form>
</div>