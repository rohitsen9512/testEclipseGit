<section class="content">
		<div class="card-small">
			<div id="enduserdash">
				<div class="card-header">
					<h3 class="card-title1">Tickets</h3>
				</div>



				<div class="card-body">

					<form action="" name="submitLocation" id="submitLocation">
						<table class="table table-bordered ">
							
							
							<tr>
								<td colspan="2">
									<button name="save" type="button" style="margin-left: 200px;"
										class="btn btn-primary"
										onclick="ForgotPassword('change_pwd');">Raise An Issue</button>
									<button name="update" type="button" style="margin-left: 200px;"
										class="btn btn-primary"
										onclick="doSubmit()">My Tickets</button>
									
										
										
										 </td>
							</tr>
						</table>
					</form>
					
					<div id="forgetTable" align="center"
				style="display: none; margin-top: 20px;">
				

				<form name="submitTicket" id="submitTicket">
	<div id="createPR">
	
		<table class="table table-bordered" id="invoiceDetails">
			<tr>
				<td colspan="4" class="tableHeader"><h3 class="tableHeaderContent">Request Details</h3></td>
			</tr>
			<tr>
			<td><strong>Ticket Number<font color="red">*</font></strong></td>
			<td>
			
			<input type="text" name="ticket_no" value="" readonly="readonly" class="form-control readbaledata" data-valid="mand" ></td>
			<td><strong>Requested date<font color="red">*</font></strong></td>
			<td>
			<input type="text" name="dt_req" value="" readonly="readonly" class="form-control datepicker readbaledata" data-valid="mand" >
			</td>
			</tr>
			<tr>
				<td ><b>Impact<font color="red">*</font></b></td>
					<td>
						<select name="urgency" class="form-control readbaledata" data-valid="mand" onchange="SetPriority()"> 
								<option value="3">3-Low</option>
								 <option value="2">2- Medium</option>
								 <option value="1">1-High</option>
						</select> 
					</td>	
					<td><strong>Email Id (, Separated)</strong></td>
					<td>
					
					<input type="text" name="email_ids" value=""  class="form-control readbaledata"></td>
					
				</tr>
				<tr>
				
				<td><strong>Phone Number<font color="red">*</font></strong></td>
				<td><input type="text" name="ph_no" class="form-control readbaledata" data-valid="mand" ></td>
				<td colspan="2"></td>
				</tr>
				
				<tr>
				<td><strong>Short Description<font color="red">*</font></strong></td>
				<td colspan="3"><textarea  rows="4" cols="50" name="short_description" style="margin: 0px 0px 10px;width: 547px; height: 98px;" data-valid="mand" class="form-control readbaledata"></textarea></td>
				
				
				</tr>
			<tr style="display:none;">
			
				<td><b>Work Notes</b></td>
				  <td colspan="3"><textarea style="margin-left: 0px;margin-right: 0px;width: 530px;height: 65px;"  name ="work_note" class="form-control readbaledata"></textarea> </td>
			
			</tr> 

					<tr>
				<td colspan="4">
					<input type="hidden" name="action" value="Save">
					<input type="hidden" name="assign_to" value="">
					<input type="hidden" name="assign_grp" value="">
					<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary req" onclick="ControlHelpDeskEss('Save','','','submitTicket','Ess_New_Ticket','')">Submit</button>
				</td>
			</tr>
			
			</table>
	</div>
	
</form>	



			</div>
			<!-- /.card body -->

		</div>
		<!-- /.card -->
	</section>
	<!-- /.content -->

	<script type="text/javascript">
	function ForgotPassword(type){
		//alert(type);
		if(type=='change_pwd'){
			//$("#mainBody").hide();
		
			$("#forgetTable").show();
		}
	if(type=='Cancel'){
		$("#mainBody").show();
		$("#forgetTable").hide();
		
		}
		
		
	}
	function doSubmit()
	{
		location.href("ess_all_tickets.jsp","_self");
	}
	$(function() {
		
		
		
		var currentDate = new Date();
	$( ".datepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      
	    });
	$('input[name="dt_req"]').datepicker("setDate", currentDate);

	ControlHelpDeskEss('Onload','','','','Ess_New_Ticket','','','');
	
	}
	);
	
						</script>