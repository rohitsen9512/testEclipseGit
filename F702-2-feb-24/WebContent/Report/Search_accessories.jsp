<!--Search_accessories.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Reports ></a><a href="#">Search ></a><a href="#">Search Accessories</a>-->
</div>

<div class="commonDiv" id="displaySearchAccessories">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left:42px">Accessory General Search (% For All Accessory)</p></td>
			</tr>
			<tr>
				<td colspan="2">
					<select name="" class="select"  style="width:140px">
						<option value="select">Select</option>
						
					</select>
				</td>
				<td><strong>Enter Accessory  Details</strong></td>
				<td><input type="text" name="enteraccessorydetails"></td>
				<td>
					<p style="width: 85px;margin-left: 13px;float: center;">
					<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displaySearchAccessories','createSearchAccessories','')">search</button>
					</p>
				</td>	
			</tr>
	</table>
</div>

<div class="commonDiv" id="createSearchAccessories" style="display:none;">
	<form action="" name="submitSearchAccessories">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr  style="background-color: blue;">
				<td><strong>S No.</strong></td>
				<td ><strong>Accessory ID</strong></td>
				<td ><strong>Accessory Name</strong></td>
				<td><strong>Accessory Desc</strong></td>
				<td><strong>Serial No.</strong></td>
				<td><strong>Group Name</strong></td>
				<td><strong>Subgroup Name</strong></td>
				<td><strong>Location Name</strong></td>
				<td><strong>SubLocation Name</strong></td>
				<td><strong>Floor Name</strong></td>
				<td><strong>CostCenter Name</strong></td>
				<td><strong>Unit Cost</strong></td>
			</tr>
			<tr>
				<td  colspan="12" ><font color="red"><center>Record(s) not available</center></font></td>
			</tr>
		</table>
	</form>
</div>