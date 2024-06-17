<!--Install_accessories.jsp-->
<div class="commonDiv" style="padding:10px;">
	<!--  <a href="#">Assets ></a><a href="#">Install ></a><a href="#">Install Accessories</a>-->
</div>

<div class="commonDiv" id="displayInstallAccessories">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:206px">Install Accessory</p></td>
			</tr>
			<tr>
				<td colspan="2"><b>Select the Accessory Group<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="selecttheaccessorygroup" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><b>Select the Accessory Sub Group<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="selecttheaccessorysubgroup" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><b>Accessory Type<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="accessorytype" class="select"  style="width:140">
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
					<p style="width:180px;margin-left: 456px;float:center;">
						<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayInstallAccessories','createInstallAccessories','')">Next</button>
					</p>
				
	
</div>

<div class="commonDiv" id="createInstallAccessories" style="display:none;">
	<form action="" name="submitInstallAccessories">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="6" class="tableHeader"><p class="tableHeaderContent" style="margin-left:204px">Installed Assets</p></td>
			</tr>
			<tr>
				<td colspan="2"><b>Select The Accessory<font color="red">*</font></b></td>
				<td colspan="2">
					<select name="selecttheaccessory" class="select"  style="width:140">
						<option value="select">Select</option>
					</select>
					<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayInstallAccessories','createInstallAccessories','')">Next</button>
				</td>
			</tr>
			<tr>
				<td  colspan="4" ><font color="red"><center>Records not available</center></font></td>
			</tr>
		</table>
	</form>
</div>