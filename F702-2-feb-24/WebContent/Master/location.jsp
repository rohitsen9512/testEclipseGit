<!-- <!--location.jsp-->


<body class="hold-transition sidebar-mini">

	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						Entity
						<button type="button" name="create btn"
							class="btn btn-primary city"
							onclick="ControlDiv('Create','displayLocation','createLocation','submitLocation','M_Loc')">Create
							Entity</button>
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Geographical</a></li>
						<li class="breadcrumb-item">Entity</li>
					</ol>
				</div>
			</div>
		</div>
	</section>


	<div class="card">
    
		<div id="displayLocation">
			<div class="card-body">
			 
				<table id="locationForDisplay"
					class="table table-bordered table-hover locationForDisplay">
					
				</table>
			</div>
		</div>
	</div>

	<section class="content" >
		
			<div id="createLocation" style="display: none;">
			<div class="card-small">
				<div class="card-header">
					<h3 class="card-title1">Entity Details</h3>
				</div>



				<div class="card-body">

					<form action="" name="submitLocation" id="submitLocation">
						<table class="table table-bordered ">
							
							<tr>
								<td><strong>Entity Name <font color="red">*</font>
										
								</strong></td>
								<td><input id="nm_locationId" class="form-control"
									type="text" name="nm_loc" data-valid="mand"onkeyup="this.value = this.value.toUpperCase();"></td>
							</tr>
							<tr>
								<td><strong>Entity Code <font color="red">*</font>
										
								</strong></td>
								<td><input id="cd_locationId" class="form-control"
									type="text" VALUE="NA" name="cd_loc" data-valid="mand" maxlength="5" onkeyup="this.value = this.value.toUpperCase();"></td>
								<input type="hidden" name="action" value="Save">
								<input type="hidden" name="id" id="id" value="0">

							</tr>
							<tr>
								<td colspan="2"><center>
									<button name="save" type="button" class="btn btn-primary"
										onclick="ControlDiv('Save','displayLocation','createLocation','submitLocation','M_Loc','Location name already exist.,,City code already exist.','nm_loc,,cd_loc')">Save</button>
									<button name="update" type="button" class="btn btn-primary"
										onclick="ControlDiv('Update','displayLocation','createLocation','submitLocation','M_Loc','' , '')">Update</button>
									<button name="cancel" type="button" class="btn btn-primary"
										onclick="ControlDiv('Cancel','displayLocation','createLocation','submitLocation','M_Loc')">Back</button>
										<button name="delete" type="button" class="btn btn-primary" onclick="DeleteFun('M_Loc','displayLocation','createLocation',$('#id').val())">Delete</button>
									
										
										
										</center> </td>
							</tr>
						</table>
					</form>
				</div>



			</div>

		</div>
	</section>



	<script type="text/javascript" src="js/masterCommon.js"></script>

	<script type="text/javascript">
		$(function() {

			/*  $("#locationForDisplay").DataTable({
			      "responsive": true, "lengthChange": false, "autoWidth": false,
			      "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"]
			    }).buttons().container().appendTo('#locationForDisplay_wrapper .col-md-6:eq(0)'); */

			DisplayData('M_Loc', 'displayLocation', 'createLocation');
			$('button[name="update"]').addClass('hideButton');

		});
	</script>

</body>
