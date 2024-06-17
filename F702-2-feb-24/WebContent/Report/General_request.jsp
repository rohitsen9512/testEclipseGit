<!--  General_request.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#"> Reports ></a><a href="#">MIS reports ></a><a href="#">General Request</a> -->
</div>

<div class="commonDiv" id="displayGeneralRequest">
	
		<table class="commonTable" align="center" width="600px" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 170px;">Quotation Between Dates</p></td>
			</tr>
			<tr>
				<td><strong>Start Date</strong></td>
				<td><input type="date" name="startdate" readonly></td>
			</tr>
			<tr>
				<td><strong>End Date</strong></td>
				<td><input type="date" name="enddate" readonly></td>
			</tr>
			<tr>
				<td><input type="radio" value="allemployees" name="allemployees"><strong>All Employees</strong></td>
				<td><input type="radio" value="selectemployee" name="allemployees"><strong>Select Employee</strong></td>
			</tr>
			<tr>
			<td>
			</td>
			<td >
					<select name="" class="select"  style="width:140" multiple>
						<option value="select"></option>
			</select>
			</td>	
			</tr>
			<tr>
				<td><strong>Select The Request</strong></td>
				<td >
					<select name="selecttherequest" class="select"  style="width:140">
						<option value="select">Select</option>
			</select>
			</td>
			</tr>
		</table>
			
			<br>
			
					<p style="width:180px;margin-left: 357px;float:centre;">
						<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayGeneralRequest','createGeneralRequest','')">Next</button>
					</p>
				
		
</div>
	
<div class="commonDiv" id="createGeneralRequest" style="display:none;">
		<form action="" name="submitGeneralRequest" >	
		<table class="commonTable" align="center" width="600px" border="1">
			<tr>
				<td colspan="12" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 276px;">Asset Request Report Between</p></td>
			</tr>
			<tr style="background-color: blue;">
				<td ><strong>S No.	</strong></td>
				<td><strong>Request No.</strong></td>
				<td><strong>Requested By</strong></td>
				<td><strong>Division</strong></td>
				<td><strong>Asset Name	</strong></td>
				<td><strong>Asset Description</strong></td>
				<td><strong>Quantity</strong></td>
				<td><strong>Request Date</strong></td>
				<td><strong>Reporting Manager Status</strong></td>
				<td><strong>ITM/Admin Manager Status</strong></td>
				<td><strong>Finance Manager Status</strong></td>
				<td><strong>Director Status</strong></td>
			</tr>
			<tr>
				<td  colspan="12" ><font color="red"><center>Records not available</center></font></td>
			</tr>
		</table>
	</form>
</div>
