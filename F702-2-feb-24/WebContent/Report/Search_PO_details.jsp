<Search_PO_details.jsp>
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Reports ></a><a href="#">Search ></a><a href="#">Search PO details</a>-->
</div>

<div class="commonDiv" id="displaySearchPODetails">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">PO General Search</p></td>
			</tr>
			<tr>
				<td><b>Search List<font color="red">*</font></b></td>
				<td>
					<select name="searchlist" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td style="width: 158px;"><strong>Enter Asset Details</strong></td>
				<td style="width: 213px;"><input type="text" name="enterassetdetails"></td>
			</tr>
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 443px;float: center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displaySearchPODetails','createSearchPODetails','')">search</button>
			</p>
</div>

<div class="commonDiv" id="createSearchPODetails" style="display:none;">
	<form action="" name="submitSearchPODetails">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr  style="background-color: blue;">
				<td><strong>S No.</strong></td>
				<td ><strong>Quotation No.</strong></td>
				<td ><strong>PO No.</strong></td>
				<td><strong>Vendor Name</strong></td>
				<td><strong>Approve Date</strong></td>
			</tr>
			<tr>
				<td><input type="text" name="slno"></td>
				<td><input type="text" name="quotationno"></td>
				<td><input type="text" name="pono"></td>
				<td><input type="text" name="vendorname"></td>
				<td><input type="text" name="approvedate"></td>
			</tr>
			<tr>
				<td  colspan="5" ><font color="red"><center>Record(s) not available</center></font></td>
			</tr>
		</table>
	</form>
</div>

<div class="commonDiv" id="createPrintPODetails" style="display:none;">
	<form action="" name="submitPrintPODetails">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:255px">PO Details</p></td>
			</tr>
			<tr>
				<td><strong>PO Number </strong></td>
				<td><input type="text" name="ponumber:"></td>
				<td><strong>PO Date </strong></td>
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

<div class="commonDiv" id="displayPrintPODetails" style="display:none;">
	<form action="" name="submitPrintPODetails">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:255px">PO Details</p></td>
			</tr>
			<tr>
				<td><strong>PO Number </strong></td>
				<td><input type="text" name="ponumber:"></td>
				<td><strong>PO Date </strong></td>
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
	</form>
</div>