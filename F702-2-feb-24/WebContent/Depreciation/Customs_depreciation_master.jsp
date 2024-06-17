<Customs_depreciation_master.jsp>
<div class="commonDiv" style="padding:10px;">
	 <!--   <a href="#">Depreciation ></a><a href="#">Customs Depreciation Master ></a>-->
</div>

<div class="commonDiv" id="displayCustomsDepreciationMaster">
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:206px">Custom Depreciation</p></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: right;"><b>Select Type<font color="red">*</font></b></td>
				<td colspan="2" style="text-align: left;">
					<select name="selecttype" class="select"  style="width:140">
						<option value="select">Select</option>
						
					</select>
				</td>
			</tr>
	  </table>
	  
	  <br>
	  
		<p style="width:180px;margin-left: 461px;float:center;">
			<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','displayCustomsDepreciationMaster','createCustomsDepreciationMaster','')">Next</button>
		</p>
</div>



<div class="commonDiv" id="createCustomsDepreciationMaster" style="display:none;">
	<form action="" name="submitCustomsDepreciationMaster">	
	
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr>
				<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left:260px">Customs Depreciation Master</p></td>
			</tr>
			<tr  style="background-color: blue;">
				<td><strong>Quarter</strong></td>
				<td ><strong>Computers</strong></td>
				<td><strong>Computers Cumulative</strong></td>
				<td><strong>Add</strong></td>
			</tr>
			<tr>
				<td><input class="common-validation" type="text" name="Quarter" data-valid="mand"></td>
				<td><input class="common-validation" type="text" name="Computers" data-valid="mand"></td>
				<td><input type="text" name="computerscumulative"></td>
				<td>
					<p style="width: 94px;margin-left: 19px;float: center;">
						<button type="button" style="float:center;"  class="btn btn-primary" onclick="ControlDiv('Create','createCustomsDepreciationMaster','createCustomsDepreciationMasters','')">Add</button>
					</p>
				</td>
			</tr>
		</table>
		
		<br>
		<br>
		
		<table class="commonTable" align="center" width="600px" height="100%" border="1">
			<tr  style="background-color: blue;">
				<td><strong>Quarter</strong></td>
				<td ><strong>Computers</strong></td>
				<td><strong>Computers Cumulative</strong></td>
				<td><strong>select</strong></td>
			</tr>
			<tr>
				<td><input class="common-validation" type="text" name="Quarter" data-valid="mand"></td>
				<td><input class="common-validation" type="text" name="Computers" data-valid="mand"></td>
				<td><input type="text" name="computerscumulative"></td>
				<td><input class="common-validation" type="checkbox" name="select" data-valid="mand"></td>
			</tr>
		</table>
	</form>
</div>


