<!--  Stores_accessories.jsp -->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Reports ></a><a href="#">Search ></a><a href="#">Stores Accessories</a>-->
</div>

<div class="commonDiv" id="displayStoresAccessories">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="2" class="tableHeader"><p class="tableHeaderContent" style="margin-left:166px">Stores Report - Accessories</p></td>
			</tr>
			<tr>
				<td><b>Select the Accessories Group<font color="red">*</font></b></td>
				<td>
					<select name="selecttheassetgroup" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><b>Select the Accessories Sub Group<font color="red">*</font></b></td>
				<td>
					<select name="selecttheassetsubgroup" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
		</table>
		
		<br>
		
			<p style="width: 85px;margin-left: 443px;float: center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayStoresAccessories','createStoresAccessories','')">Next</button>
			</p>
</div>

<div class="commonDiv" id="createStoresAccessories" style="display:none;">
	<form action="" name="submitStoresAsset">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="10" class="tableHeader"><p class="tableHeaderContent" style="margin-left:232px">Stores Inventory for null Group</p></td>
			</tr>
			<tr  style="background-color: blue;">
				<td><strong>S No.</strong></td>
				<td ><strong>Accessory ID</strong></td>
				<td ><strong>Accessory Name</strong></td>
				<td><strong>Accessory Description</strong></td>
				<td><strong>Accessory Remarks</strong></td>
				<td><strong>Manufacturer</strong></td>
				<td><strong>Accessory Model</strong></td>
				<td><strong>Accessory Serial No.</strong></td>
				<td><strong>Unit Cost(RS.)</strong></td>
			</tr>
			<tr>
				<td  colspan="12" ><font color="red"><center>Record(s) not available</center></font></td>
			</tr>
		</table>
		
		<br>
		
			<p style="width: 125px;margin-left: 443px;float: center;">
				<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayStoresAsset','createStoresAsset','')">Export To Excel</button>
			</p>
	</form>
</div>