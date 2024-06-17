<!--Model.jsp-->


<script type="text/javascript">

$(function() {
	
	
		
	DisplayData('M_Model','displayModel','createModel');
	$('button[name="update"]').addClass('hideButton');
	
	DisplayDropDownDataForGroup('M_Asset_Div','groupDataForModel','CapG',function (status){
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
					<h1><!--  Asset/Model--> <button type="button"  name="create btn" class="btn btn-primary" onclick="ControlDiv('Create','displayModel','createModel','submitModel','M_Model')">Create Asset/Model</button>
					</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item">Asset/Model</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>
<div class="card">
     

<div id="displayModel">
		
			<div class="card-body">
			<table id="ModelForDisplay"
					class="table table-bordered table-hover ModelForDisplay">
					<thead>
						<tr class="new">
							<td><strong>Group</strong></td>
							<!-- <td><strong>Sub Group</strong></td> -->
							<td><strong>Item Name</strong></td>
							<td><strong>Item Code</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
						</thead>
					</table>
				
</div>
</div>
</div>
<section class="content">
<div id="createModel" style="display:none;">
<div class="card-small">
<div class="card-header new">
					<h3 class="card-title1">Asset/Model Details</h3>
				</div>
				<div class="card-body">
	<form action="" name="submitModel" id="submitModel">	
		<table class="table table-bordered ">
			
			<tr>
				<td><strong>Asset/Model Type<font color="red">*</font></strong></td>
				<td>
					<select class="form-control" name="typ_asst" data-valid="mand">
						<option value="IT">IT</option>
						<option value="NON-IT">NON IT</option>
				        <option value="Software">SOFTWARE</option>
						<option value="accessories">ACCESSORIES</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td><strong>Category<font color="red">*</font></strong></td>
				<td>
 					<select id="groupDataForModel" class="form-control" name="id_assetdiv" data-valid="mand" onChange="SubDropDownDataDisplay(this,'subgroupDataForModel','M_Subasset_Div')">
					
 				<option value="">Select</option>
						
					</select>
					
				</td>
				
			</tr>
			<tr>
				<td><strong>Sub-Category<font color="red">*</font></strong></td>
				<td>
					<select id="subgroupDataForModel" class="form-control" name="id_s_assetdiv" data-valid="mand">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr> 
			<tr>
				<td><strong>Assets/Model Name<font color="red">*</font></strong></td>
				<td><input id="nm_ModelId" class="form-control" type="text" name="nm_model" maxlength="100" data-valid="mand"></td>
			</tr>
			<tr>
				<!-- <td><strong>Assets/Model Code <font color="red">*</font> </strong></td>
				<td><input id="cd_ModelId" class="form-control" type="text" name="cd_model" data-valid="mand"></td> -->
				
				
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" id="id" value="0">
				<input type="hidden" name="file_name" value="" class="form-control">
			</tr>
			<tr>
				<td><strong>Manufacturer<font color="red">*</font></strong></td>
				<td><input id="mfr" class="form-control" type="text" name="mfr" data-valid="mand"></td>
			</tr>
			<tr>
				<td><strong>Description<font color="red">*</font></strong></td>
				<td><input id="ds_asst" class="form-control" type="text" name="ds_asst" data-valid="mand"></td>
			</tr>
			<!-- <tr>
			 <td><b>Attachment<font color="red">*</font></b></td>
				<td><input id="file4ID" type="file" name="file4" class="form-control" value="" ></td>
			</tr> -->
			<tr>
				<td colspan="2"><center>
					<button name="save" type="button"  class="btn btn-primary" onclick="ControlDiv('Save','displayModel','createModel','submitModel','M_Model','Model name already exist.,,Model Code already exist.' , 'nm_model,,cd_model')">Save</button>
					<button name="update" type="button"   class="btn btn-primary" onclick="ControlDiv('Update','displayModel','createModel','submitModel','M_Model','Model name already exist.' , '')" disabled>Update</button>
					<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayModel','createModel','submitModel','M_Model')">Back</button>
				<button name="delete" type="button"  class="btn btn-primary" onclick="DeleteFun('M_Model','displayModel','createModel',document.getElementById('id').value)" disabled>Delete</button>
				
				</center></td>
			</tr>
		</table>
	</form>
</div>
</div>
 <!-- /.card-body -->
      </div>
      <!-- /.card -->
    </section>
    <!-- /.content -->
 
<!-- Page specific script -->

  
  
  

</body>
</html>
