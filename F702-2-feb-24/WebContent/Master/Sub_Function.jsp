<!--Building.jsp-->
<div style="padding:10px;">
	<!--  <a href="#">Admin > </a><a href="#">Sub-Function</a>-->
</div>

<script type="text/javascript">

$(function() {
	
	
		//$('input[name="id_loc"]').hide();
	DisplayData('M_S_Function','displaysubfunction','createsubfunction');
	$('button[name="update"]').addClass('hideButton');
	
	/* DropDownDataDisplay("M_Country","countryDataForBuilding",function (status){
		if(status)
			{
			/* DisplaySubDropDownData('0','subLocDataForFloor','M_Subloc',function (status){
				if(status)
					{
					
					}}); 
			}});
	
 */	
	DropDownDataDisplay("M_Dept","DeptDataForCostCenter");
	
});

</script>



<div id="displaysubfunction">
		<table width="100%" height="100%">
			<tr>
				<td>
				<div  id="displaysubfunction">
	<input type="text" name="searchWord" value="" placeholder="Search......" onkeyup="DisplayData('M_S_Function','displaysubfunction','createsubfunction')">
</div>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displaysubfunction','createsubfunction','submitsubfunction','M_S_Function')">Create Function	</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover subfunctionForDisplay">
						<tr class="new">
								<td><strong>Division</strong></td>
							<td><strong>Location</strong></td>
							<td><strong>Sub Location</strong></td>
							<td><strong>Building Name</strong></td>
							<td><strong>Building Code</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>


<div id="createsubfunction" style="display:none;">
	<form action="" name="submitsubfunction" id="submitsubfunction">	
		<table align="center" width="600px" border="1">
			<tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 230px;">Function Details</p></td>
			</tr>
			
		<!-- 	<tr>
				<td><strong>Division <font color="red">*</font></strong></td>
				<td>
					<select id="divDataForBuilding" class="common-validation" name="id_div" data-valid="mand" onChange="DisplaySubDropDownData(this,'locDataForBuilding','M_Loc')">
						<option value="">Select</option>
					</select>
				</td>
			</tr> -->
			
			<tr>
				<td><strong>Department <font color="red">*</font></strong></td>
				<td>
					<select id="DeptDataForCostCenter" class="common-validation" name="id_dept" data-valid="mand" onChange="DisplaySubDropDownData(this,'id_cc_drop','M_Cost_Center')">
						<option value="">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>BU <font color="red">*</font></strong></td>
				<td>
					<select id="id_cc_drop" class="common-validation" name="id_cc" data-valid="mand">
						<option value="">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Function Name <font color="red">*</font></strong></td>
				<td><input id="nm_floorId" class="common-validation" type="text" name="nm_s_function" data-valid="mand"></td>
			</tr>
			<tr style="display:none">
			 	<td><strong>Building Code <font color="red">*</font></strong></td>
				<td><input id="cd_floorId" class="common-validation" type="text" value="none" name="cd_building" data-valid="mand"></td>
			 	<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
				<input type="hidden" name="id_country" value="1">
			</tr>
			<tr>
				<td colspan="2">
					<button name="save" type="button" style="margin-left:230px;"   class="btn btn-primary" onclick="ControlDiv('Save','displaysubfunction','createsubfunction','submitsubfunction','M_S_Function','' , '')">Save</button>
					<button name="update" type="button" style="margin-left:200px;"   class="btn btn-primary" onclick="ControlDiv('Update','displaysubfunction','createsubfunction','submitsubfunction','M_S_Function','' , '')">Update</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displaysubfunction','createsubfunction','submitsubfunction','M_S_Function')">Back</button>
				</td>
			</tr>
		</table>
	</form>
</div>