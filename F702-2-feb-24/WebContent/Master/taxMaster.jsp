
<script type="text/javascript">

$(function() {
	DisplayData('M_Tax','displayTax','createTax');
	$('button[name="update"]').addClass('hideButton');
});
function Valcheck(action) 
{
	debugger;
	//var tax1=$("#nm_taxId").val();
	//var tax2=$("#nm_taxId2").val();
	//var taxp1=$("#per_tax1").val();
	//var taxp2=$("#per_tax2").val();
	var tax1=$("#nm_taxId").val().replace(/\s+/g, " ").trim();
	 $("#nm_taxId").val(tax1);
	var tax2=$("#nm_taxId2").val().replace(/\s+/g, " ").trim();
	 $("#nm_taxId2").val(tax2);
	var taxp1=$("#per_tax1").val().replace(/\s+/g, " ").trim();
	 $("#per_tax1").val(taxp1);
	var taxp2=$("#per_tax2").val().replace(/\s+/g, " ").trim();
	 $("#per_tax2").val(taxp2);
	 
	if(tax1=="")   {     
	      $("#tx1n").attr('style', 'display:block;');
		  $("#tx2n").attr('style', 'display:none;');
		  $("#tx1p").attr('style', 'display:none;');
		  $("#tx2p").attr('style', 'display:none;');
		  
	      document.getElementById("minmax2").style.display = "none";
	      document.getElementById("minmax1").style.display = "none";
	}
	else if(tax2==""){
		  $("#tx2n").attr('style', 'display:block;');
		  $("#tx1n").attr('style', 'display:none;');
		   $("#tx1p").attr('style', 'display:none;');
		  $("#tx2p").attr('style', 'display:none;');
		  
	document.getElementById("minmax2").style.display = "none";
	document.getElementById("minmax1").style.display = "none";
	}
	else if(taxp1==""){
		  $("#tx1p").attr('style', 'display:block;');
		  $("#tx1n").attr('style', 'display:none;');
		  $("#tx2n").attr('style', 'display:none;');
		  $("#tx2p").attr('style', 'display:none;');
		  
		  document.getElementById("minmax2").style.display = "none";
		  document.getElementById("minmax1").style.display = "none";
	}
	else if(taxp2==""){
		  $("#tx2p").attr('style', 'display:block;');
		  $("#tx1n").attr('style', 'display:none;');
		  $("#tx2n").attr('style', 'display:none;');
		  $("#tx1p").attr('style', 'display:none;');
		  
		  document.getElementById("minmax2").style.display = "none";
		  document.getElementById("minmax1").style.display = "none";
	}
	 else if (tax1.length < 2 || tax1.length > 70  ) {
		  $("#tx1n").attr('style', 'display:none;');
		  $("#tx2n").attr('style', 'display:none;');
		  $("#tx1p").attr('style', 'display:none;');
		  $("#tx2p").attr('style', 'display:none;');
		    
			document.getElementById("minmax2").style.display = "block";
			document.getElementById("minmax1").style.display = "none";	
		  }
	 else if (tax2.length < 2 || tax2.length > 70  ) {
		  $("#tx1n").attr('style', 'display:none;');
		  $("#tx2n").attr('style', 'display:none;');
		  $("#tx1p").attr('style', 'display:none;');
		  $("#tx2p").attr('style', 'display:none;');
		    
			document.getElementById("minmax1").style.display = "block";
			document.getElementById("minmax2").style.display = "none";	
		  }
	else if(action == 'Save'){
		DataInsertUpdateTax('Save','displayTax','createTax','submitTax','M_Tax');
	}
	else if(action== "Update"){
		ControlDiv('Update','displayTax','createTax','submitTax','M_Tax','Tax name already exist.,,Tax name already exist.','','Tax has updated successfully.','');
		
	}
	}	

function validateTax2(input) {
	  var value = parseFloat(input.value);
	 
	  if (isNaN(value) || value < 0 || value > 100) {
	    alert("Please enter a number between 0 and 100.");
	    input.value = '';  // Clear the input field
	  } else {
	    // Format the input to allow only 2 decimal places
	    input.value = parseFloat(value.toFixed(2));
	  }
	}
	</script>
</script>


<!-- Content Wrapper. Contains page content -->
  
    <!-- Content Header (Page header) -->
    <!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1><!--  Tax -->
					<button type="button" name="create btn"  class="btn btn-primary" onclick="ControlDiv('Create','displayTax','createTax','submitTax','M_Tax')">Create Tax</button>
					</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Master</a></li>
              <li class="breadcrumb-item">Tax</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

<div class="card">

		<div id="displayTax">
			<div class="card-body">
				<table id="taxForDisplay"
					class="table table-bordered table-hover taxForDisplay">
					<thead>

		
		
						<tr class="new">
							<td><strong>Tax Code</strong></td>
							<td><strong>Tax Name</strong></td>
							<td><strong>Percentage</strong></td>
							<td><strong>Modify/ Delete</strong></td>
						</tr>
					</table>
				</div>
				</div>
				
		
	</div>
<section class="content">
		
			<div id="createTax" class="card-small" style="display:none;">
				<div class="card-header new">
					<h3 class="card-title1">Tax Details</h3>
				</div>



				<div class="card-body">


	<form action="" name="submitTax" id="submitTax">
		<table class="table table-bordered ">
			
			
			<tr>
				<td><strong>Tax1 Name<font color="red">*</font></strong></td>
				<td><input id="nm_taxId" class="form-control" type="text" name="nm_tax1" ondrop="return false;" onpaste="return false;" onselectstart="return false;" data-valid="mand" autofocus="" onkeyup="this.value = this.value.toUpperCase();">
				<div id="tx1n" style="display:none;"><strong style="color:red">Tax1 name is required!</strong></div>			
				 <div id="minmax2" style="display:none;"> <label for="nm_taxId" style="color: red;font-weight: normal;">Tax name must be between 2 and 70 characters.</label>
               
				</td>
				<td><strong>Tax2 Name<font color="red">*</font></strong></td>
				<td><input id="nm_taxId2" class="form-control" type="text" name="nm_tax2" ondrop="return false;" onpaste="return false;" onselectstart="return false;" data-valid="mand" autofocus="" onkeyup="this.value = this.value.toUpperCase();">
				<div id="tx2n" style="display:none;"><strong style="color:red">Tax2 name is required!</strong></div>
				 <div id="minmax1" style="display:none;"> <label for="nm_taxId2" style="color: red;font-weight: normal;">Tax name must be between 2 and 70 characters.</label>
               
				</td>
			</tr>
			<!-- <tr>
				<td><strong>Tax Code <font color="red">*</font></strong></td>
				<td><input id="cd_taxId" class="form-control" type="text" name="cd_tax"  data-valid="mand"></td>
				<td><strong>Tax Code <font color="red">*</font></strong></td>
				<td><input id="cd_taxId" class="form-control" type="text" name="cd_tax"  data-valid="mand"></td>
				
				
			</tr> -->
			<tr>
				<td><strong>Tax1 Percentage<font color="red">*</font></strong></td>
				<td><input id="per_tax1" class="form-control" type="text" name="per_tax1" ondrop="return false;" onchange="validateTax2(this);" onpaste="return false;" onselectstart="return false;" data-valid="num" >
				<div id="tx1p" style="display:none;"><strong style="color:red">Tax1 percentage is required!</strong></div>
				</td>
				<td><strong>Tax2 Percentage<font color="red">*</font></strong></td>
				<td><input id="per_tax2" class="form-control" type="text" name="per_tax2" ondrop="return false;" onchange="validateTax2(this);" onpaste="return false;" onselectstart="return false;" data-valid="num" >
				<div id="tx2p" style="display:none;"><strong style="color:red">Tax2 percentage is required!</strong></div>
				</td>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
			</tr>
			<tr>
				<td colspan="4"><center>
					
						<button name="save" type="button" style="margin-left:220px;"   class="btn btn-primary" onclick="Valcheck('Save');">Save</button>
						<button name="update" type="button"   class="btn btn-primary" onclick="Valcheck('Update');">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayTax','createTax','submitTax','M_Tax','','','','')">Back</button>
					
				<center></td>
			</tr>
			
		</table>
	</form>
	</div>
	</div>
	</div>
	</section>