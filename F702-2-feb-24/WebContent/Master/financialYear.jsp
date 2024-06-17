

<script type="text/javascript">

$(function() {
    $("#finYeardatepicker").datepicker({
    	yearRange: '1985:2025',
    	 changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
        onSelect: function(dateText, instance) {
        	 date = $.datepicker.parseDate(instance.settings.dateFormat, dateText, instance.settings);
             
             /*  $('input[name="stdt_first"]').val($.datepicker.formatDate("yy-mm-dd", new Date(date)));
              date.setDate(date.getDate() - 1);
              
              date.setMonth(date.getMonth() + 12);
              $('input[name="endt_second"]').val($.datepicker.formatDate("yy-mm-dd", new Date(date)));
              $('input[name="end_finance"]').val($.datepicker.formatDate("yy-mm-dd", new Date(date)));
              
              date.setMonth(date.getMonth() - 6);
              $('input[name="endt_first"]').val($.datepicker.formatDate("yy-mm-dd", new Date(date)));
             
              date.setDate(date.getDate() + 1);
              $('input[name="stdt_second"]').val($.datepicker.formatDate("yy-mm-dd", new Date(date))); */
              $('input[name="stdt_first"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
              date.setDate(date.getDate() - 1);
              
              date.setMonth(date.getMonth() + 12);
              $('input[name="endt_second"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
              $('input[name="end_finance"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
              
              date.setMonth(date.getMonth() - 6);
              date.setDate(date.getDate() - 1);
              $('input[name="endt_first"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
             
              date.setDate(date.getDate() + 1);
              $('input[name="stdt_second"]').val($.datepicker.formatDate("dd/mm/yy", new Date(date)));
            
        }
    });
   
    
    DisplayData('M_Financial_Year','displayFinancialYear','createFinancialYear');
	$('button[name="update"]').addClass('hideButton');
    
});

</script>
<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!--  Financial Year-->
						<button type="button" name="create btn"
							class="btn btn-primary" onclick="ControlDiv('Create','displayFinancialYear','createFinancialYear','submitFinancialYear','M_Financial_Year')">Create Financial Year</button>
					
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Master</a></li>
						<li class="breadcrumb-item">Financial Year</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	<div class="card">
<div id="displayFinancialYear">
	<div class="card-body">
	<div class="message">Your update was successful</div>
				<table id="dataForFinanceYear"
					class="table table-bordered table-hover dataForFinanceYear">
					
					<thead>
		
			
					
					
					
			
			
					
						<tr class="success new">
							<td colspan="2"> <strong>Financial Year</strong>
								<table align="center" style="margin-bottom: -8px;">
									<tr>
										<td style="border-left: 0px;">Start Date</td>
										<td >End Date</td>
									</tr>
								</table>
							</td>
							<td colspan="2"><strong>First Half Year</strong>
								<table align="center" style="margin-bottom: -8px;">
									<tr>
										<td style="border-left: 0px;">Start Date</td>
										<td >End Date</td>
									</tr>
								</table>
							</td>
							<td colspan="2"><strong>Second Half Year</strong>
								<table align="center" style="margin-bottom: -8px;">
									<tr>
										<td style="border-left: 0px;">Start Date</td>
										<td >End Date</td>
									</tr>
								</table>
							</td>
							<td colspan="3"><strong>Manipulation</strong>
								<table align="center" style="margin-bottom: -8px;">
									<tr>
										<td style="border-left: 0px;"><strong>Modify</strong></td>
										<td style="border-left: 0px;"><strong>Delete</strong></td>
										<td style="border-left: 0px;"><strong>Current</strong></td>
									</tr>
								</table>
								
							</td>
						</tr>
						
					</table>
				</div>
		
	</div>
	</div>

<section class="content">
<div id="createFinancialYear" style="display:none;">
<div class="card-small">
<div class="card-header new">
					<h3 class="card-title1">Financial Year Details</h3>
				</div>
	<form action="" name="submitFinancialYear" id="submitFinancialYear">	
		<table class="table table-bordered ">
			<tr>
				<td><strong>Start Date<font color="red">*</font></strong></td>
				<td><input id="finYeardatepicker" class="form-control common-check" data-valid="mand" type="text" name="std_finance" data-valid="mand" value="" ></td>
			</tr>
			<tr>
				<td><strong>End Date</strong></td>
				<td><input class=" form-control common-check" type="text" name="end_finance"  value="" readonly="readonly"></td>
				
			</tr>
			<tr>
				<td colspan="2" style="background-color:#3f6791;"><p style="font-size: 28px;color: white;margin-left: 250px;">First Half Year</p></td>
			</tr>
			<tr>
				<td><strong>Start Date</strong></td>
				<td><input class="form-control common-check" type="text"  name="stdt_first" value="" readonly="readonly" ></td>
			</tr>
			<tr>
				<td><strong>End Date</strong></td>
				<td><input class="form-control common-check"  type="text" readonly name="endt_first"  value="" ></td>
				
			</tr>
			<tr>
				<td colspan="2" style="background-color: #3f6791;"><p style="font-size: 28px;color: white;margin-left: 250px;">Second Half Year</p></td>
			</tr>
			<tr>
				<td><strong>Start Date</strong></td>
				<td><input class="form-control common-check" type="text" readonly name="stdt_second" value="" ></td>
				
				
			</tr>
			<tr>
				<td><strong>End Date</strong></td>
				<td><input  type="text" readonly name="endt_second"  class="form-control common-check"  value="" ></td>
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
				
			</tr>
			</table>
	</form>
			<tr>
				<td colspan="2"><center>
					
						<button name="save" type="button"  class="btn btn-primary" onclick="ControlDiv('Save','displayFinancialYear','createFinancialYear','submitFinancialYear','M_Financial_Year','Financial year all ready exit.' , 'std_finance')">Save</button>
						<button name="update" type="button"  class="btn btn-primary" onclick="ControlDiv('Update','displayFinancialYear','createFinancialYear','submitFinancialYear','M_Financial_Year','Financial year all ready exit.' , 'std_finance')">Update</button>
						<button type="cancel"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayFinancialYear','createFinancialYear','submitFinancialYear','M_Financial_Year')">Back</button>
					
				</center></td>
			</tr>
			
		
</div>
</div>
</section>