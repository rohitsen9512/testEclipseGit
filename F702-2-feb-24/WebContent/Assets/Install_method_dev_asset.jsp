<!--Install_method_dev_asset.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Assets ></a><a href="#">Install ></a><a href="#">Install Method Dev Asset</a>-->
</div>

<div class="commonDiv" id="displayInstallMethodAsset">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:189px">Install Assets</p></td>
			</tr>
			<tr>
				<td colspan="2"><b>Select THe Asset Group<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="selecttheassetgroup" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><b>Select the Asset Sub Group<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="selecttheassetsubgroup" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><b>Asset Type<font color="red">*</font></b></td>
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
			
					<p style="width:180px;margin-left: 467px;float:center;">
						<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayInstallMethodAsset','createInstallMethodAsset','')">Next</button>
					</p>
				
	
</div>


<div class="commonDiv" id="createInstallMethodAsset" style="display:none;">
	<form action="" name="submitInstallMethodAsset">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="6" class="tableHeader"><p class="tableHeaderContent" style="margin-left:204px">Assets In The Store</p></td>
			</tr>
			<tr  style="background-color: blue;">
				<td><strong>S No.</strong></td>
				<td ><strong>Asset ID</strong></td>
				<td ><strong>Asset Name</strong></td>
				<td><strong>Serial No.</strong></td>
				<td><strong>Model Number</strong></td>
				<td><strong>Asset Description</strong></td>
			</tr>
			<tr>
				<td  colspan="6" ><font color="red"><center>Records not available</center></font></td>
			</tr>
		</table>
	</form>
</div>