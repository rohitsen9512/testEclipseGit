<!--Install_asset.jsp-->
<style> 
.select2-results__options{ font-size:12px !important;}
.select2-container .select2-selection--single {font-size:12px;}
</style>
<!-- <script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>

<script type ="text/javascript" src="All_Js_File/Asset/A_Install.js"></script>
<script type ="text/javascript" src="common.js"></script> -->
<script type="text/javascript">

$(function() {
	 $('button[name="update"]').addClass('hideButton');
	 /*$('#InstallDetails').addClass('hideButton'); */
	DisplayData('M_Group','displayGroup','createGroup');
	
}); 

</script>



<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<div class="col-sm-6">
					<h1><!--  Group --><button type="button" name="create btn" class="btn btn-primary" onclick="ControlDiv('Create','displayGroup','createGroup','submitgroup','M_Group')">Create Group</button>
					</h1>
          </div>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Master</a></li>
						<li class="breadcrumb-item">Group Assignment</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>




		<div id="displayGroup" class="card">
			<div class="card-body">
				<table id="MgroupForDisplay"
					class="table table-bordered table-hover MgroupForDisplay">
					<thead>
						<tr class="new">
							<th><strong>Group Name</strong></th>
							<th><strong>Group Code</strong></th>
							
						</tr>
					</thead>
					
				</table>
			</div>
			<!-- /.card-body -->
		</div>
	
	<!-- /.card -->

	<section class="content">
		
			<div id="createGroup" class="card-small" style="display: none;">
				<div class="card-header new">
					<h3 class="card-title1">Group Details</h3>
				</div>



				<div class="card-body">

					<form action="" name="submitgroup" id="submitgroup">
						<table class="table table-bordered ">
							
							<tr>
								<td><strong>Group Name<font color="red">*</font>
										
								</strong></td>
								<td><input id="nm_grpId" class="form-control"
									type="text" name="nm_grp" data-valid="mand"></td>
							</tr>
							<tr>
								<td><strong>Group Code<font color="red">*</font>
										
								</strong></td>
								<td><input id="cd_grpId" class="form-control"
									type="text" VALUE="NA" name="cd_grp" data-valid="mand"></td>
								<input type="hidden" name="action" value="Save">
								<input type="hidden" name="id" id="id" value="0">

							</tr>
							<tr>
								<td colspan="2"><center>
									<button name="save" type="button" class="btn btn-primary"
										onclick="ControlDiv('Save','displayGroup','createGroup','submitgroup','M_Group','Group name already exist.','nm_grp')">Save</button>
									<button name="update" type="button" class="btn btn-primary"
										onclick="ControlDiv('Update','displayGroup','createGroup','submitgroup','M_Group','' , '')">Update</button>
									<button name="cancel" type="button" class="btn btn-primary"
										onclick="ControlDiv('Cancel','displayGroup','createGroup','submitgroup','M_Group')">Back</button>
										<button name="delete" type="button" class="btn btn-primary" onclick="DeleteFun('M_Group','displayGroup','createGroup',$('#id').val())">Delete</button>
									
										
										
										</center> </td>
							</tr>
						</table>
						</form>
						
	
	<div class="card" >
<div class="card-body">
<div class="commonDiv" id="displayEmpForBulkInstallSearch">
	<input type="text" name="search" value="" placeholder="Search" onkeyup="EditEmpFun('M_Group','','',$('#id').val())">
	
	

</div>
      <form name="bulkinstallEmp" id="bulkinstallEmp">
		<div id="DisplayEmpForBulkInstall"style="display: none;height:300px;overflow:auto;">
		
		<input type="hidden" name="action" value="SaveEMPGroup">
	
	
		
		<input type="hidden" name="id" value="">
		<input type="hidden" name="id_group" value="">
		<input type="hidden" name="upload_asset" value="" class="form-control">
		<table class="table table-bordered DisplayEmpForBulkInstall">
			
		</table>
		</div>
	</form>
</div>
</div>
</div>
</div>



			</div>
</div>
</div>
</div>
