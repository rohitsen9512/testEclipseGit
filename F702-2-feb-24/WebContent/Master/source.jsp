<!-- <!--location.jsp-->


<body class="hold-transition sidebar-mini">

	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						Source
						<button type="button" name="create btn"
							class="btn btn-primary city"
							onclick="ControlDiv('Create','displaysrc','createsrc','submitsrc','M_Source')">Create
							Source</button>
					</h1>
				</div>
			<!-- 	<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						
						<li class="breadcrumb-item">Source</li>
					</ol>
				</div> -->
			</div>
		</div>
	</section>


	<div class="card">
    
		<div id="displaysrc">
			<div class="card-body">
			 
				<table id="srcForDisplay"
					class="table table-bordered table-hover srcForDisplay">
					
				</table>
			</div>
		</div>
	</div>

	<section class="content" >
		
			<div id="createsrc" style="display: none;">
			<div class="card-small">
				<div class="card-header">
					<h3 class="card-title1">Source Details</h3>
				</div>



				<div class="card-body">

					<form action="" name="submitsrc" id="submitsrc">
						<table class="table table-bordered ">
							
							<tr>
								<td><strong>Source Name <font color="red">*</font>
										
								</strong></td>
								<td><input id="nm_srcId" class="form-control"
									type="text" name="nm_src" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
							</tr>
							<tr>
								<td><strong>Source Code <font color="red">*</font>
										
								</strong></td>
								<td><input id="cd_srcId" class="form-control"
									type="text" VALUE="NA" name="cd_src" data-valid="mand" maxlength="" onkeyup="this.value = this.value.toUpperCase();"></td>
								<input type="hidden" name="action" value="Save">
								<input type="hidden" name="id" id="id" value="0">

							</tr>
							<tr>
								<td colspan="2"><center>
									<button name="save" type="button" class="btn btn-primary"
										onclick="ControlDiv('Save','displaysrc','createsrc','submitsrc','M_Source','Source name already exist.,,Source code already exist.','nm_src,,cd_src')">Save</button>
									<button name="update" type="button" class="btn btn-primary"
										onclick="ControlDiv('Update','displaysrc','createsrc','submitsrc','M_Source','' , '')">Update</button>
									<button name="cancel" type="button" class="btn btn-primary"
										onclick="ControlDiv('Cancel','displaysrc','createsrc','submitsrc','M_Source')">Back</button>
										<!-- <button name="delete" type="button" class="btn btn-primary" onclick="DeleteFun('M_Loc','displaysrc','createsrc',$('#id').val())">Delete</button> -->
									
										
										
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

			DisplayData('M_Source', 'displaysrc', 'createsrc');
			$('button[name="update"]').addClass('hideButton');

		});
	</script>

</body>
