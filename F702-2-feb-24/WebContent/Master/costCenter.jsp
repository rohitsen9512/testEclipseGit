

<script type="text/javascript">

$(function() {
	
	DisplayData('M_Cost_Center','displayCostCenter','createCostCenter');
	
	$('button[name="update"]').addClass('hideButton');
	
	DropDownDataDisplay("M_Dept","DeptDataForCostCenter");
});


</script>

<!-- Content Wrapper. Contains page content -->
  
    <!-- Content Header (Page header) -->
    <!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1><!--  Cost Center -->
					<button type="button" name="create btn"  class="btn btn-primary" onclick="ControlDiv('Create','displayCostCenter','createCostCenter','submitCostCenter' , 'M_Cost_Center')">Create Cost-Center</button>
					</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Master</a></li>
              <li class="breadcrumb-item">CostCenter</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>


  
         
     


		<div class="card">

		<div id="displayCostCenter">
			<div class="card-body">
				<table id="costCenterForDisplay"
					class="table table-bordered table-hover costCenterForDisplay">
					<thead>
			
			
					
						<tr class="new">
							
							<td><strong>Department Name</strong></td>
							<td><strong>BU Code</strong></td>
							<td><strong>BU Name</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</div>
				</div>
				</div>
				
		
	<section class="content">
	<div id="createCostCenter" style="display:none;">
		<div class="card-small">
			
				<div class="card-header new">


<h3 class="card-title1">Cost Center Details</h3>
				</div>



				<div class="card-body">
	<form  name="submitCostCenter" id="submitCostCenter">	
		<table class="table table-bordered ">
			
			 
			<tr>
				<td><strong>Cost Center/Project Name<font color="red">*</font></strong></td>
				<td><input id="nm_costcenterId" type="text" name="nm_cc" value="" class="form-control" data-valid="mand"></td>
				
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" id="id" value="0">
			</tr>
			<tr>
				<td colspan="2"><center>
					
						<button name="save" type="button"  class="btn btn-primary" onclick="ControlDiv('Save','displayCostCenter','createCostCenter','submitCostCenter' , 'M_Cost_Center','Costcenter name already exist.,,Costcenter code already exist.' , 'nm_cc,,')">Save</button>
						<button name="update" type="button"    class="btn btn-primary" onclick="ControlDiv('Update','displayCostCenter','createCostCenter','submitCostCenter','M_Cost_Center','Costcenter name already exist.' , 'nm_cc')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayCostCenter','createCostCenter','submitCostCenter' , 'M_Cost_Center')">Back</button>
					<button name="delete" type="button"  class="btn btn-primary" onclick="DeleteFun('M_Cost_Center','displayCostCenter','createCostCenter',document.getElementById('id').value)">Delete</button>
					
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
  
<!-- Page specific script -->
<script>
 
</script>
  
  
  

</body>
</html>
	