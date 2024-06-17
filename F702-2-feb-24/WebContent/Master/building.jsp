<!--Building.jsp-->




<!-- Content Wrapper. Contains page content -->

<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>
					<!--  Building --> 
					<button type="button" class="btn btn-primary" name="create btn"
						onclick="ControlDiv('Create','displayBuilding','createBuilding','submitBuilding','M_Building')">Create Building</button>
				</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Geographical</a></li>
					<li class="breadcrumb-item">Building</li>
				</ol>
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>

<div class="card">




	<div id="displayBuilding">

		<div class="card-body">
			<table id="buildingForDisplay"
				class="table table-bordered table-hover buildingForDisplay">
				<thead>
					<tr class="new">
						<!-- <td><strong>Division</strong></td> -->
						<td><strong>Location</strong></td>
						<td><strong>Sub Location</strong></td>
						<td><strong>Building Name</strong></td>
						<!-- <td><strong>Building Code</strong></td>
						<td><strong><a href="#">Modify </a><a href="#">/
									Delete</a></strong></td> -->
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- /.card-body -->
</div>
<!-- /.card -->
<section class="content">
		<div id="createBuilding" style="display: none;">
		<div class="card-small">
			<div class="card-header new">
				<h3 class="card-title1">Sub-location Details</h3>
			</div>
			<div class="card-body">
				<form action="" name="submitBuilding" id="submitBuilding">


					<table class="table table-bordered " style="width: 100%;">

						<tr>
							<td><strong>Location<font color="red">*</font> 
							</strong></td>
							<td><select id="locDataForBuilding" class="form-control"
								name="id_loc" data-valid="mand"
								onChange="DisplaySubDropDownData(this,'subLocDataForBuilding','M_Subloc')">
									<option value="">Select</option>
							</select></td>
						</tr>
						<tr>
							<td><strong> Sub Location<font color="red">*</font>
									
							</strong></td>
							<td><select id="subLocDataForBuilding" class="form-control"
								name="id_sloc" data-valid="mand">
									<option value="">Select</option>
							</select></td>
						</tr>
						<tr>
							<td><strong>Building Name<font color="red">*</font>
									
							</strong></td>
							<td><input id="nm_floorId" class="form-control" type="text"
								name="nm_building" data-valid="mand"></td>
						</tr>
						<tr style="display: none">
							<td><strong>Building Code<font color="red">*</font>
									
							</strong></td>
							<td><input id="cd_floorId" class="form-control" type="text"
								name="cd_building" value="NA" data-valid="mand"></td>
							<input type="hidden" name="action" value="Save">
							<input type="hidden" name="id" id="id" value="0">
							<input type="hidden" name="id_country" value="1">
						</tr>
						<tr>
							<td colspan="2"><center>
								<button name="save" type="button" class="btn btn-primary"
									onclick="ControlDiv('Save','displayBuilding','createBuilding','submitBuilding','M_Building','' , '')">Save</button>
								<button name="update" type="button" class="btn btn-primary"
									onclick="ControlDiv('Update','displayBuilding','createBuilding','submitBuilding','M_Building','' , '')">Update</button>
								<button name="cancel" type="button" class="btn btn-primary"
									onclick="ControlDiv('Cancel','displayBuilding','createBuilding','submitBuilding','M_Building')">Back</button>
							<button name="delete" type="button" class="btn btn-primary"
									onclick="DeleteFun('M_Building','displayBuilding','createBuilding',document.getElementById('id').value)">Delete</button>
							
							
							</center></td>
						</tr>
					</table>
				</form>
			</div>



		</div>
		<!-- /.card body -->

	</div>
	<!-- /.card -->
</section>







<!-- Page specific script -->
<script type="text/javascript" src="js/masterCommon.js"></script>
<script type="text/javascript">
	$(function() {

		DisplayData('M_Building', 'displayBuilding', 'createBuilding');
		$('button[name="update"]').addClass('hideButton');

		DropDownDataDisplay("M_Loc", "locDataForBuilding", function(status) {
			if (status) {

			}
		});

	});
</script>




</body>
</html>
