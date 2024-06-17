<!--Cost_centre_report.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#"> Reports ></a><a href="#">Financial reports ></a><a href="#">Yearly US GAPP</a> -->
</div>

<div class="commonDiv" id="displayYearlyUsGapp">
	
		<table class="commonTable" align="center" width="600px" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 180px;">Select Financial Year</p></td>
			</tr>
			<tr>
				<td><strong>Select Financial Year</strong></td>
				<td >
					<select name="selectfinancialyear" class="select"  style="width:140">
						<option value="select">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<p style="width:180px;margin-left: 161px;float:centre;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayYearlyUsGapp','createYearlyUsGapp','')">Generate Report</button>
					</p>
				</td>
			</tr>
		</table>
</div>

<div class="commonDiv" id="createYearlyUsGapp" style="display:none;">
	<form action="" name="submitYearlyUsGapp" >	
		<br><br><center><b><font color="red" class=navigate>Depreciation Yet To Be Calculated</font></b></center>
	</form>
</div>