<!--subLocation.jsp-->





<!-- Content Wrapper. Contains page content -->
<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>
					<!-- Sub-Locations  -->
					<button type="button" class="btn btn-primary" name="create btn"
						onclick="ControlDiv('Create','displaySubLocation','createSubLocation','submitSubLocation','M_Subloc')">Create
						City</button>
				</h1>
			</div>
			<div class="col-sm-6">
				<!-- <ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Geographical</a></li>
					<li class="breadcrumb-item">City</li>
				</ol> -->
			</div>
		</div>
	</div>
	<!-- /.container-fluid -->
</section>
<div class="card">




	<div id="displaySubLocation">
		<div class="card-body">
			<table id="SublocationForDisplay"
				class="table table-bordered table-hover SublocationForDisplay">
				<thead>
					<tr class="new">
						
						<th><strong>City Name</strong></th>
						

						<th><strong>Modify /
									Delete</strong></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- /.card-body -->
</div>
<!-- /.card -->


<section class="content">
	
		<div id="createSubLocation" style="display: none;">
		<div class="card-small">
			<div class="card-header new">
				<h3 class="card-title1">City Details </h3>
			</div>


			<div class="card-body">
				<form action="" name="submitSubLocation" id="submitSubLocation">

					<table class="table table-bordered " style="width: 100%;">
					 	<tr>
							<td><strong>Entity<font color="red">*</font>
							</strong></td>
							<td><select id="locDataForSubLoc" class="form-control"
								name="id_loc" data-valid="mand">
									<option value="">Select</option>
							</select></td>
						</tr> 

						<tr>
							<td><strong>City Name<font color="red">*</font>
									
							</strong></td>
							<td><input id="nm_sublocationId" class="form-control"
								type="text" name="nm_subl" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
						</tr>
                           
                           <tr>
							<td><strong>City code<font color="red">*</font>
									
							</strong></td>
							<td><input id="nm_sublocationId" class="form-control"
								type="text" name="cd_subl" data-valid="mand"onkeyup="this.value = this.value.toUpperCase();"></td>
						</tr>
                           <tr>
							<td><strong>GSTIN NO. <font color="red">*</font>
									
							</strong></td>
							<td><input id="nm_sublocationId" class="form-control"
								type="text" name="gstin_no_city" value="NA" data-valid="mand"onkeyup="this.value = this.value.toUpperCase();"></td>
						</tr>
						<tr>
							<td><strong>DL NO.<font color="red">*</font>
									
							</strong></td>
							<td><input id="nm_sublocationId" class="form-control"
								type="text" name="dl_no_city" value="NA" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
						</tr>
                          	<tr>
							<td><strong>Office Address<font color="red">*</font>
									
							</strong></td>
							<td><input id="office_add" class="form-control"
								type="text" name="office_add" value="NA" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
						</tr> 
						<tr>
							<td colspan="2"><center><input type="hidden" name="action"
								value="Save"> <input type="hidden" name="id_country"
								value="1"> <input type="hidden" name="title"
								id="Sub-location"> 
								<input type="hidden" name="id" id="id" value="0">
								<button name="save" type="button" class="btn btn-primary"
									onclick="ControlDiv('Save','displaySubLocation','createSubLocation','submitSubLocation','M_Subloc','' , '')">Save</button>
								<button name="update" type="button" class="btn btn-primary"
									onclick="ControlDiv('Update','displaySubLocation','createSubLocation','submitSubLocation','M_Subloc','' , '')">Update</button>
								<button name="cancel" type="button" class="btn btn-primary"
									onclick="ControlDiv('Cancel','displaySubLocation','createSubLocation','submitSubLocation','M_Subloc')">Back</button>
                                <button name="delete" type="button" class="btn btn-primary"
									onclick="DeleteFun('M_Subloc','displaySubLocation','createSubLocation',document.getElementById('id').value)">Delete
									</button>

							
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
<!-- /.content -->



<script type="text/javascript" src="js/masterCommon.js"></script>

<script type="text/javascript">

$(function() {
	$('button[name="update"]').addClass('hideButton');
	DisplayData('M_Subloc','displaySubLocation','createSubLocation');
	
	

	DropDownDataDisplay("M_Loc","locDataForSubLoc",function (status){
		if(status)
			{
			
			}});  
});
</script>



