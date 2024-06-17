<!--Detailed_PO_reports.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Reports ></a><a href="#">Search ></a><a href="#">Detailed PO Reports</a>-->
</div>

<div class="commonDiv" id="displayDetailedPOReports">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:216px">PO Report</p></td>
			</tr>
			<tr>
				<td><strong>Start Date</strong></td>
				<td><input type="date" name="startdate"></td>
			</tr>
			<tr>
				<td><strong>End Date</strong></td>
				<td><input type="date" name="enddate"></td>
			</tr>
			<tr>
				<td><b>Select Vendor<font color="red">*</font></b></td>
				<td>
					<select name="selectvendor" class="select"  style="width:140px">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Select Division <font color="red">*</font></b></td>
				<td>
					<select name="selectdivision " class="select"  style="width:140px">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Asset Name<font color="red">*</font></b></td>
				<td>
					<select name="assetname" class="select"  style="width:140px">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Status<font color="red">*</font></b></td>
				<td>
					<select name="status " class="select"  style="width:140px">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
	</table>
	
	<br>
	
			<p style="width: 85px;margin-left: 457px;float: center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayDetailedPOReports','createDetailedPOReports','')">search</button>
			</p>
</div>

<div class="commonDiv" id="createDetailedPOReports" style="display:none;">
	<form action="" name="submitDetailedPOReports">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr  style="background-color: blue;">
				<td><strong>S No.</strong></td>
				<td ><strong>Quotation No.</strong></td>
				<td ><strong>PO No.</strong></td>
				<td><strong>Vendor Name</strong></td>
				<td><strong>Approve Date</strong></td>
			</tr>
			<tr>
				<td  colspan="5" ><font color="red"><center>Record(s) not available</center></font></td>
			</tr>
		</table>
	</form>
</div>

<div class="commonDiv" id="createPrintDetailedPOReports" style="display:none;">
	<form action="" name="submitPrintDetailedPOReports">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:255px">PO Details</p></td>
			</tr>
			<tr>
				<td><strong>PO Number</strong></td>
				<td><input type="text" name="ponumber:"></td>
				<td><strong>PO Date</strong></td>
				<td><input type="date" name="podate:"></td>
			</tr>
			<tr>
				<td ><strong>Quotation Number</strong></td>
				<td><input type="text" name="quotationnumber"></td>
				<td ><strong>Quotation Date</strong></td>
				<td><input type="date" name="quotationdate"></td>
			</tr>
			<tr>
				<td><strong>Division</strong></td>
				<td><input type="text" name="division"></td>
				<td><strong>Vendor Name</strong></td>
				<td><input type="text" name="vendorname"></td>
			</tr>
			<tr>
				<td><strong>PO Amount</strong></td>
				<td><input type="text" name="poamount"></td>
				<td><strong>Amount Paid</strong></td>
				<td><input type="text" name="amountpaid"></td>
			</tr>
		</table>
		
		<br>
		
			<p style="width: 121px;margin-left: 443px;float: center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','createPrintPODetails','displayPrintPODetails','')">Print Preview</button>
			</p>
	</form>
</div>