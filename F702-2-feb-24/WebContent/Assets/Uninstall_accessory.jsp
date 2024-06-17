<!--Unistall_accessory.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!-- <a href="#">Assets ></a><a href="#">Uninstall ></a><a href="#">Uninstall Accessories</a> -->
</div>

<div class="commonDiv" id="displayUninstallAccessories">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:206px">Uninstall Accessory</p></td>
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
			
			<BR>
			
					<p style="width:180px;margin-left: 457px;float:center;">
						<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayUninstallAccessories','createUninstallAccessories','')">Next</button>
					</p>
				
	
</div>

<div class="commonDiv" id="createUninstallAccessories" style="display:none;">
	<form action="" name="submitUninstallAccessories">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="6" class="tableHeader"><p class="tableHeaderContent" style="margin-left:204px">Uninstall Accessories</p></td>
			</tr>
			<tr>
				<td colspan="2"><b>Select The Asset<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="selecttheasset" class="select"  style="width:140">
						<option value="select">Select</option>
					</select>
					<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','createUninstallAccessories','displayUninstalledAccessories','')">Next</button>
				</td>
			</tr>
			<tr>
				<td  colspan="4" ><font color="red"><center>Records not available</center></font></td>
			</tr>
		</table>
	</form>
</div>

<div class="commonDiv" id="displayUninstalledAccessories" style="display:none;">
	<form action="" name="submitUninstalledAccessories">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="9" class="tableHeader"><p class="tableHeaderContent" style="margin-left:663px">Select Accessory to Uninstall</p></td>
			</tr>
			<tr  style="background-color: blue;">
				<td><strong>S No.</strong></td>
				<td ><strong>Accessory ID</strong></td>
				<td ><strong>Accessory Name</strong></td>
				<td><strong>Serial No.</strong></td>
				<td><strong>Model Number</strong></td>
				<td><strong>Description</strong></td>
				<td><strong>Installation Date</strong></td>
				<td><strong>Accessory Cost</strong></td>
				<td><strong>Check</strong></td>
			</tr>
			<tr>
				<td><input type="text" name="slno"></td>
				<td><input type="text" name="accessoryid"></td>
				<td><input type="text" name="accessoryname"></td>
				<td><input type="text" name="serialnumber"></td>
				<td><input type="text" name="modelnumber"></td>
				<td><input type="text" name="description"></td>
				<td><input type="date" name="installationdate"></td>
				<td><input type="text" name="accessorycost"></td>
				<td><input type="checkbox" name="check"></td>
			</tr>
		</table>
		
		<br>
		<br>
		
		<table border="0">
			<tr>
				<td>
					<p style="width:180px;margin-left: 764px;float:center;">
					<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayUninstalledAccessories','createUninstallAsset','')">Back to store</button>
					</p>
				</td>
			</tr>
		</table>
	</form>
</div>