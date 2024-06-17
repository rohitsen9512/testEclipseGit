<!--Floor.jsp-->


<script type="text/javascript">

$(function() {
	
	
	DisplayData('M_Floor','displayFloor','createFloor');
	$('button[name="update"]').addClass('hideButton');
	
	DropDownDataDisplay("M_Loc","locDataForFloor",function (status){
		if(status)
			{
			
			}});
	
	/* DropDownDataDisplay("M_Country","countryDataForFloor",function (status){
		if(status)
			{
			}}); */
});

</script>

<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!--  Floor -->
						<button type="button" name="create btn"
							class="btn btn-primary city"
							onclick="ControlDiv('Create','displayFloor','createFloor','submitFloor','M_Floor')">Create
							Floor</button>
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Geographical</a></li>
						<li class="breadcrumb-item">Floor</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
<div class="card">

		<div id="displayFloor">
			<div class="card-body">
				<table id="floorForDisplay"
			 class="table table-bordered table-hover floorForDisplay">
		
			<!-- <tr>
				<td>
				<div  id="displayFloor">
	<input type="text" name="searchWord" value="" placeholder="Search......" onkeyup="DisplayData('M_Floor','displayFloor','createFloor')">
</div>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayFloor','createFloor','submitFloor','M_Floor')">Create Floor</button>
					</p>
				</td>
			</tr> -->
			
					
						<tr class="new">
						<td><strong>Country</strong></td>
							<td><strong>Location</strong></td>
							<td><strong>Floor</strong></td>
							<td><strong>Block Name</strong></td>
							<td><strong>Block Code</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				
		</div>
		</div>
		
</div>

<section class="content">
<div id="createFloor" style="display:none;">
		<div class="card-small">
			
				<div class="card-header new">
					<h3 class="card-title1">Floor Details</h3>
				</div>



				<div class="card-body">

	<form action="" name="submitFloor" id="submitFloor">	
		<table class="table table-bordered ">
			<!-- <tr>
				<td colspan="2" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 230px;">Floor Details</p></td>
			</tr> -->
			<!-- <tr>
				<td><strong>Division <font color="red">*</font></strong></td>
				<td>
					<select id="divDataForFlr" class="form-control" name="id_div" data-valid="mand" onChange="DisplaySubDropDownData(this,'locDataForFloor','M_Loc')">
						<option value="">Select</option>
					</select>
				</td>
				
			</tr> -->
			<tr>
				<td><strong>Location<font color="red">*</font></strong></td>
				<td>
					<select id="locDataForFloor" class="form-control" name="id_loc" data-valid="mand" onChange="DisplaySubDropDownData(this,'subLocDataForFloor','M_Subloc')">
						<option value="">Select</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Sub Location<font color="red">*</font></strong></td>
				<td>
					<select id="subLocDataForFloor" class="form-control" name="id_sloc" data-valid="mand" onChange="SubDropDownDataDisplay(this,'buildingDataForFloor','M_Building')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><strong> Building<font color="red">*</font></strong></td>
				<td>
					<select id="buildingDataForFloor" class="form-control" name="id_building" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Floor Name<font color="red">*</font></strong></td>
				<td><input id="nm_floorId" class="form-control" type="text" name="nm_flr" data-valid="mand"></td>
			</tr>
			<tr style="display:none">
			 	<td><strong>Floor Code<font color="red">*</font></strong></td>
				<td><input id="cd_floorId" class="form-control" type="text" value="NA" name="cd_flr" data-valid="mand"></td>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id"id="id" value="0">
				<input type="hidden" name="id_country" value="1">
			</tr>
			<tr>
				<td colspan="2"><center>
					<button name="save" type="button" class="btn btn-primary" onclick="ControlDiv('Save','displayFloor','createFloor','submitFloor','M_Floor','' , '')">Save</button>
					<button name="update" type="button"   class="btn btn-primary" onclick="ControlDiv('Update','displayFloor','createFloor','submitFloor','M_Floor','' , '')">Update</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayFloor','createFloor','submitFloor','M_Floor')">Back</button>
				<button name="delete" type="button" class="btn btn-primary" onclick="DeleteFun('M_Floor','displayFloor','createFloor',$('#id').val())">Delete</button>
									
				</center></td>
			</tr>
		</table>
	</form>
</div>
</div>
</div>
</section>
