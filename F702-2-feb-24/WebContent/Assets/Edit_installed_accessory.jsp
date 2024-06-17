<!--Edit_installed_accessory.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Assets ></a><a href="#">Edit Install ></a><a href="#">Edit Installed Accessories</a> -->
</div>

<div class="commonDiv" id="displayEditInstalledAccessories">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:206px">Install Accessory</p></td>
			</tr>
			<tr>
				<td colspan="2"><b>Select the Accessory Group<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="selecttheassetgroup" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><b>Select the Accessory Sub Group<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="selecttheassetsubgroup" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><b>Accessory Type<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="assettype" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><b>Location<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="location" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><b>Sub Location<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="sublocation" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
		</table>
			
			<br>
			
					<p style="width:180px;margin-left: 460px;float:center;">
						<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayEditInstalledAccessories','createEditInstalledAccessories','')">Next</button>
					</p>
				
	
</div>

<div class="commonDiv" id="createEditInstalledAccessories" style="display:none;">
	<form action="" name="submitEditInstalledAccessories">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="9" class="tableHeader"><p class="tableHeaderContent" style="margin-left:227px">Installed Accessories</p></td>
			</tr>
			<tr  style="background-color: blue;">
				<td><strong>S No.</strong></td>
				<td ><strong>Accessory ID</strong></td>
				<td ><strong>Accessory Name</strong></td>
				<td><strong>Serial No.</strong></td>
				<td><strong>Accessory Description</strong></td>
				<td><strong>Type of Procurement</strong></td>
				<td ><strong>Added To Asset</strong></td>
				<td ><strong>Installation Date</strong></td>
				<td ><strong>Check</strong></td>
			</tr>
			<tr>
				<td  colspan="9" ><font color="red"><center>Records not available</center></font></td>
			</tr>
		</table>
	</form>
</div>