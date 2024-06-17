<!--Edit_from_store.jsp-->

<script type="text/javascript"
		src="All_Js_File/Store/store.js"></script>
<script type="text/javascript">
	$(function() {
		
		DisplayInStock('S_Add_To_Stock', 'invstockForDisplaytd');
	
	});
	
</script>
<script>
</script>
      <!-- Content Wrapper. Contains page content -->
  <!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
			<!-- 	<div class="col-sm-6">
					<h1>
						 Designation
						<button type="button" name="create btn"
							 class="btn btn-primary" onclick="ControlDiv('Create','displayDesignation','createDesignation','submitDesignation','M_Designation')">Create Designation</button>
					
					</h1>
				</div> -->
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Stock</a></li>
						<li class="breadcrumb-item">In Stock</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
  <div class="card">
<!--   <div>
  Filter:
  <select name="filter_stk" class="form-control readbaledata" style="width:140px;display: inline;" data-valid="mand" onchange="DisplayInStock('S_Add_To_Stock', 'invstockForDisplaytd')"> 
						 <option value="All"> All</option>
						 <option value="sta"> State</option>
						 <option value="city"> City</option>
						 
				</select>
				<div> -->
	<div id="displayInStock">
		<div class="card-body">
			<!-- 	<table width="100%" height="100%">
			 	<tr>
				<td colspan="4">
				 	<td><strong>From Date</strong></td>
					<td><input id="dt_frm" type="text" name="dt_frm" value=""
						class="form-control datepicker" data-valid="mand"></td>
					<td><strong>&nbsp;&nbsp;To Date</strong></td>
					<td><input id="dt_to" type="text" name="dt_to" value=""
						class="form-control datepicker" data-valid="mand"></td> 
					<td>
						<button type="button" style="margin-top: 10px; margin-left: 25px;"
							class="btn btn-primary"
							onclick="DisplayInStock('S_Add_To_Stock','displayInStock','','','invstockForDisplaytd')">Search
						</button>
					</td>
				</tr> 
				<tr></tr>
			</table> -->
			<table id="invstockForDisplaytd"
				class="table table-bordered table-hover invstockForDisplaytd">
				
					<tr class="new">
						<td><strong>Product Name</strong></td>
						<td><strong>Available Quantity</strong></td>
					
						<td><strong>City</strong></td>
						
					</tr>
				
			</table>
		</div>
	</div>
</div>  



<!-- 
  <div class="card">
        
                   
                   <div id="displayInStock">
                   <div class="card-body">
				<table id="invstockForDisplaytd"
					class="table table-bordered table-hover invstockForDisplaytd">
					<thead>
                   
					
						<tr class="new">
							<td><strong>Product Name</strong></td>
						<td><strong>Available Quantity</strong></td>
						<td><strong>State</strong></td>
						<td><strong>City</strong></td>
						</tr>
					</table>
			</div>
		 -->
	</div></div>
