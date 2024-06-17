
<script type="text/javascript">

$(function() {
	DisplayData('M_Subasset_Div','displaySubGroup','createSubGroup');
	
	DisplayDropDownDataForGroup("M_Asset_Div","assetDivForSubassetDiv","CapG",function (status){
		if(status)
			{
			
			}});
	
});
jQuery("input#file4ID").change(function () {
	
	 var formData = new FormData();	 
		formData.append('file', $('#file4ID').get(0).files[0]);
		
			$.ajax({
			  url: 'Upload_File',
			    type: 'POST',
			    data: formData,
			    async: false,
			    cache: false,
			    contentType: false,
			    dataType: "json",
			    processData: false,
			    mimeType: "multipart/form-data",
			    success: function (r) {
			    	
			    		$('input[name="file_name"]').val(r.upload_inv);
			    		//bootbox.alert("File Uploaded successfully");
			    }
			},'json'); 
	
}); 

	
</script>
<!-- Content Wrapper. Contains page content -->
  
  <!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1><!-- Sub-Category -->  <button type="button"name="create btn" class="btn btn-primary" onclick="ControlDiv('Create','displaySubGroup','createSubGroup','submitSubGroup','M_Subasset_Div')">Create Sub-Category</button>
				</h1>	
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Category</a></li>
              <li class="breadcrumb-item ">Subgroup</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
<div class="card">
     

<div id="displaySubGroup">
		<div class="card-body">
		<table id="subassetForDisplay"
					class="table table-bordered table-hover subassetForDisplay">
					<thead>
					
						<tr class="new">
							
							<td><strong>Category Name</strong></td>
							<td><strong>Sub_Category Name</strong></td>
							<td><strong>Sub_Category Code</strong></td>
							
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</thead>
					<tbody>
				</table>
			</div>
			<!-- /.card-body -->
		</div>
	</div>
	<!-- /.card -->

<section class="content">
<div id="createSubGroup" style="display:none;">
		<div class="card-small">


<div class="card-header new">
					<h3 class="card-title1">Sub-Category Details</h3>
				</div>
	<form action="" name="submitSubGroup" id="submitSubGroup">
		<table class="table table-bordered ">
						
			<tr>
				<td><strong>Category<font color="red">*</font></strong></td>
				<td>
					<select name="id_assetdiv" autofocus="" id="assetDivForSubassetDiv" class="form-control" data-valid="mand">
							<option value="">Select</option>
							
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Sub-Category<font color="red">*</font></strong></td>
				<td><input id="nm_subgroupId" type="text" name="nm_s_assetdiv" style="text-transform:uppercase" class="form-control" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Sub-Category Code<font color="red">*</font></strong></td>
				<td><input id="cd_subgroupId" type="text" maxlength="5" style="text-transform:uppercase" name="cd_s_assetdiv" class="form-control" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();" ></td>
			</tr>
			<!-- <tr>
				 <td><b>Attachment<font color="red">*</font></b></td>
				<td><input id="file4ID" type="file" name="file4" class="form-control" value="" ></td>
				
				
			</tr> -->
			<tr>
			
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" id="id" value="0">
				<input type="hidden" name="type" value="CapG">
				<input type="hidden" name="file_name" value="" class="form-control">
				
			</tr>
			
			<tr>
				<td colspan="2"><center>
					
						<button name="save" type="button" class="btn btn-primary" onclick="ControlDiv('Save','displaySubGroup','createSubGroup','submitSubGroup','M_Subasset_Div','' , '')">Save</button>
						<button name="update" type="button" class="btn btn-primary" onclick="ControlDiv('Update','displaySubGroup','createSubGroup','submitSubGroup','M_Subasset_Div','' , '')" disabled>Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displaySubGroup','createSubGroup','submitSubGroup','M_Subasset_Div')">Back</button>
					<button name="delete" type="button"  class="btn btn-primary" onclick="DeleteFun('M_Subasset_Div','displaySubGroup','createSubGroup',document.getElementById('id').value)" disabled>Delete</button>
					
				</center></td>
			</tr>
			
		</table>
	</form>
	</div>
	 <!-- /.card-body -->
      </div>
      <!-- /.card -->
    </section>
    <!-- /.content -->
  
<!-- Page specific script -->

  
  
  

</body>
</html>
	