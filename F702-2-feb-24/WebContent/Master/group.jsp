<script type="text/javascript" src="js/masterCommon.js"></script>
<script type="text/javascript" src="common.js"></script>
<script type="text/javascript">




$(function() {
	
	DisplayData('M_Asset_Div','displayGroup','createGroup');
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
			    		
			    }
			},'json'); 
	
});  


function checksaleprice() {
	/// how to get the value
	debugger;
	var alphabeticRegex = /^[^A-Za-z]+$/;
	
	var tot=(parseFloat($('input[name="un_prc_assetdiv"').val()));
	var x = $("#un_prc_groupId").val();
	var number = Number(x);
	if(tot<0 || x=="")
		{
			alert('Please enter valid number.');
			$("#un_prc_groupId").val('');
			$("#un_prc_groupId").focus();
			
		}
	else if(isNaN(tot) || isNaN(number)) {
        alert('Please enter valid number.');
		$("#un_prc_groupId").val('');
		$("#un_prc_groupId").focus();
	}
	
}


</script>
<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!--  Category  --> <button type="button" class="btn btn-primary" name="create btn"
						onclick="ControlDiv('Create','displayGroup','createGroup','submitGroup','M_Asset_Div')">Create Product</button>
						
						</h1>
          </div>
         
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Product</a></li>
						<!-- <li class="breadcrumb-item">Group</li> -->
					</ol>
				</div>
			</div>
			 </div>
		<!-- /.container-fluid -->
	</section>

    <!-- Main content -->
    <div class="card">
     
<div id="displayGroup">
<div class="card-body">
		
		<table id="assetForDisplay"
					 class="table table-bordered table-hover assetForDisplay">
						
			</table>
	</div>
			<!-- /.card-body -->
		</div>
	</div>
	<!-- /.card -->

<section class="content">
<div id="createGroup" style="display:none;">
		<div class="card-small">

<div class="card-header new">
					<h3 class="card-title1">Product Details</h3>
				</div>
				<div class="card-body">
	<form action="" name="submitGroup" id="submitGroup">
		<table class="table table-bordered ">
			
			
			<tr>
				<td><strong>Product<font color="red">*</font></strong></td>
				<td><input id="nm_groupId" class="form-control" type="text" name="nm_assetdiv" style="text-transform:uppercase" data-valid="mand" value="" onkeyup="this.value = this.value.toUpperCase();" ></td>
			</tr>
	
	
	        	
			<tr>
				<td><strong>Type Product<font color="red">*</font></strong></td>
					<td><select name="type_grp" class="form-control" data-valid="mand">
						<option value="">Select</option>
						<option value="TYPE-PRODUCT">TYPE PRODUCT</option>
						<option value="TYPE-CYLINDER">Type Cylinder</option>
							<option value="TYPE-OXYGEN">Type OXYGEN</option>
					</select></td>
					</tr>
	
			<tr>
				<td><strong>Product Description<font color="red">*</font></strong></td>
				<td><input id="ds_groupId" class="form-control" type="text"  style="text-transform:uppercase"  name="ds_assetdiv" data-valid="mand" value="NA" onkeyup="this.value = this.value.toUpperCase();" ></td>
				
				
			</tr >
			
			<tr>
				<td><strong>Manufacture<font color="red">*</font></strong></td>
				<td><input id="mfr_groupId" class="form-control" type="text"  style="text-transform:uppercase"  name="mfr_assetdiv" data-valid="mand" value="NA" onblur="CheckSpace('mfr_assetdiv')" onkeyup="this.value = this.value.toUpperCase();"  ></td>
				
				
			</tr>
			<tr>
				<td><strong>HSN/SAC-Code<font color="red">*</font></strong></td>
				<td><input id="hsn_groupId" class="form-control" type="text"  style="text-transform:uppercase"  name="hsn_cd_assetdiv" data-valid="mand" value="NA" onblur="CheckSpace('hsn_cd_assetdiv')" onkeyup="this.value = this.value.toUpperCase();" ></td>
				
				
			</tr>
			 <tr>
				<td><strong>Product Prefix<font color="red">*</font></strong></td>
				<td><input id="prifix_groupId" class="form-control" type="text"  style="text-transform:uppercase"  name="asset_prod_prefix" data-valid="mand" value="NA" onblur="CheckSpace('asset_prod_prefix')" onkeyup="this.value = this.value.toUpperCase();"></td>
				
				
			</tr> 
			<tr>
				<td><strong>Sale Price<font color="red">*</font></strong></td>
				<td><input id="un_prc_groupId" class="form-control" type="text"  style="text-transform:uppercase"  name="un_prc_assetdiv" value="0.00"  onchange="checksaleprice();" data-valid="mand" value="" ></td>
				
				
			</tr>
			<!-- <tr>
				 <td><b>Attachment<font color="red">*</font></b></td>
				<td><input id="file4ID" type="file" name="file4" class="form-control" value="" ></td>
				
				
			</tr> -->
			<tr>
				<!-- <td><strong>Category Description :</strong></td>
				<td><input class="form-control" type="text" name="ds_assetdiv" value=""></td> -->
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" id="id"value="0">
				<input type="hidden" name="type" value="CapG">
				<input type="hidden" name="file_name" value="" class="form-control">
			</tr>
			
			<tr>
				<td colspan="2"><center>
					
						<button name="save" type="button"  class="btn btn-primary" onclick="ControlDiv('Save','displayGroup','createGroup','submitGroup','M_Asset_Div','Product name already exist.,,Product Prifix already exist.' , 'nm_assetdiv,,asset_prod_prefix')">Save</button>
						<button name="update" type="button"  class="btn btn-primary" onclick="ControlDiv('Update','displayGroup','createGroup','submitGroup','M_Asset_Div','' , '')" >Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayGroup','createGroup','submitGroup','M_Asset_Div')">Back</button>
					<!-- <button name="delete" type="button"  class="btn btn-primary" onclick="DeleteFun('M_Asset_Div','displayGroup','createGroup',document.getElementById('id').value)" disabled>Delete</button> -->
					
				</center></td>
			</tr>
			
		</table>
	</form>
	</div>
	 <!-- /.card-body -->
      </div>
      <!-- /.card -->
      </div>
    </section>
    <!-- /.content -->

<!-- Page specific script -->

  
  
  

</body>
</html>
	