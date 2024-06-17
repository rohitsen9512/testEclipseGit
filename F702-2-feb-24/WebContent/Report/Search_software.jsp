<Search_software.jsp>
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Reports ></a><a href="#">Search ></a><a href="#">Search Software</a> -->
</div>

<div class="commonDiv" id="displaySearchSoftware">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Software General Search</p></td>
			</tr>
			<tr>
				<td colspan="2">
					<select name="" class="select"  style="width:140px">
						<option value="select">Select</option>
						
					</select>
				</td>
				<td><strong>Enter Software  Details</strong></td>
				<td style="width: 158px;"><input type="text" name="entersoftwaredetails"></td>
				<td>
					<p style="width: 85px;margin-left: 13px;float: center;">
					<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displaySearchSoftware','createSearchSoftware','')">search</button>
					</p>
				</td>	
			</tr>
	</table>
</div>

<div class="commonDiv" id="createSearchSoftware" style="display:none;">
	<form action="" name="submitSearchSoftware">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr  style="background-color: blue;">
				<td><strong>S No.</strong></td>
				<td ><strong>Software ID</strong></td>
				<td ><strong>Software Name</strong></td>
				<td><strong>software Desc</strong></td>
				<td><strong>Serial No.</strong></td>
				<td><strong>Status</strong></td>
				<td><strong>Assigned TO</strong></td>
				<td><strong>Group Name</strong></td>
				<td><strong>Subgroup Name</strong></td>
				<td><strong>Location Name</strong></td>
				<td><strong>Sublocation Name</strong></td>
				<td><strong>Cubicle Name</strong></td>
				<td><strong>Costcenter Name</strong></td>
				<td><strong>Unit Cost</strong></td>
			</tr>
			<tr>
				<td  colspan="14" ><font color="red"><center>Record(s) not available</center></font></td>
			</tr>
		</table>
	</form>
</div>