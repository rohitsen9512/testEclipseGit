<!--Floor.jsp-->
<div style="padding:10px;">
	<!--  <a href="#">Admin > </a><a href="#">Sub-Function </a>-->
</div>

<script type="text/javascript">

$(function() {
	
	
	DisplayData('M_BU','displaybu','createbu');
	$('button[name="update"]').addClass('hideButton');
	DropDownDataDisplay("M_Dept","DeptDataForCostCenter");
	 
	
	/* DropDownDataDisplay("M_Country","countryDataForFloor",function (status){
		if(status)
			{
			}}); */
});

</script>


<div id="displaybu">
		<table width="100%" height="100%">
			<tr>
				<td>
				<div  id="displaybu">
	<input type="text" name="searchWord" value="" placeholder="Search......" onkeyup="DisplayData('M_BU','displaybu','createbu')">
</div>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displaybu','createbu','submitbu','M_BU')">Create Sub-Function</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover buForDisplay">
						<tr class="new">
						<td><strong>Country</strong></td>
							<td><strong>Location</strong></td>
							<td><strong>Floor</strong></td>
							<td><strong>Block Name</strong></td>
							<td><strong>Block Code</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>


<div id="createbu" style="display:none;">
	<form action="" name="submitbu" id="submitbu">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 230px;">Sub-Function Details</p></td>
			</tr>
			<!-- <tr>
				<td><strong>Division <font color="red">*</font></strong></td>
				<td>
					<select id="divDataForFlr" class="common-validation" name="id_div" data-valid="mand" onChange="DisplaySubDropDownData(this,'locDataForFloor','M_Loc')">
						<option value="">Select</option>
					</select>
				</td>
				
			</tr> -->
			<tr>
				<td><strong> Department<font color="red">*</font></strong></td>
				<td>
					<select id="DeptDataForCostCenter" class="common-validation" name="id_dept" data-valid="mand" onChange="DisplaySubDropDownData(this,'nmfunction','M_Cost_Center')">
						<option value="">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>BU <font color="red">*</font></strong></td>
				<td>
					<select id="nmfunction" class="common-validation" name="id_cc" data-valid="mand" onChange="DisplaySubDropDownData(this,'subfunction','M_S_Function')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Function <font color="red">*</font></strong></td>
				<td>
					<select id="subfunction" class="common-validation" name="id_s_function" data-valid="mand" >
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Sub-Function<font color="red">*</font></strong></td>
				<td><input id="nmsubfunction" class="common-validation" type="text" name="nm_bu" data-valid="mand"></td>
			</tr>
			<tr>
			<!-- 	<td><strong>Floor Code <font color="red">*</font> </strong></td>
				<td><input id="cd_floorId" class="common-validation" type="text" name="cd_flr" data-valid="mand"></td>
			 -->	<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
				<input type="hidden" name="id_country" value="1">
			</tr>
			<tr>
				<td colspan="2">
					<button name="save" type="button" style="margin-left:230px;"   class="btn btn-primary" onclick="ControlDiv('Save','displaybu','createbu','submitbu','M_BU','' , '')">Save</button>
					<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displaybu','createbu','submitbu','M_BU','' , '')">Update</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displaybu','createbu','submitbu','M_BU')">Back</button>
				</td>
			</tr>
		</table>
	</form>
</div>