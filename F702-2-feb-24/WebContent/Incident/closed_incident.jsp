<!--Edit_from_store.jsp-->
<script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<style>
.disabled{
disabled: disabled;
}
</style>
<%HttpSession session2 = request.getSession(); %>
	

<script type="text/javascript">



//getasseetapi();

getapi();
jQuery("input#file4ID1").change(function () {
	var filename="";
	var files = $("#file4ID1").get(0).files;
    var fileData = new FormData();

    for (var i = 0; i < files.length; i++) {
    	
        
         var formData = new FormData();	 
 		formData.append('file', $('#file4ID1').get(0).files[i]);
        
		
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
			    	
			    	if(i==0){
			        	filename=r.upload_inv;
			        }
			        else{
			        	filename+=","+r.upload_inv;
			        }
			   
			    $('input[name="file_name1"]').val(filename);
			    	
			    		/* $('input[name="file_name1"]').val(filename); */
			    		
			    }
			},'json'); 
    }	

}); 
$(function() {
	var UserType = '<%= (String)session2.getAttribute("UserType") %>';

	
	
	
	/* if(UserType == 'SUPER'){
		
		$("#reopenState").prop("disabled", false);
	}else{
		$("#reopenState").prop("disabled", true);
	}
		 */
	
	$(".Activity").click(function(){
        $(".activityMessage").toggle(1000);
    });
	/* DisplayDropDownDataForGroup('M_Asset_Div','requestPrAssetGroup','CapG',function (status){
		if(status)
		{ */
			DropDownDataDisplay('M_Dept','depDataForNewTicket',function (status){
				if(status)
				{
					DisplayDropDownData("M_Emp_User","empForNewTicketHD",function (status){
						if(status)
						{
							DisplayDropDownData("M_Emp_User","empForAssignToHD",function (status){
								if(status)
								{
									DisplayDropDownData('M_Designation','id_design',function (status){
										if(status)
										{
		ControlIncident('List','DisplayClosedIncident','EditDetailsForClosedIncident','SubmitFormForEditClosedIncidents','Closed_Incident','displayDataForEditClosedIncident','displayDataForEditClosedIncident','');
		if(status)
		{
		listToBeParent();
		}
								}});
						}});
					
				 }}); 
			 
}});


	$('.grndatepicker').each(function () {
		
        if ($(this).hasClass('hasDatepicker')) {
            $(this).removeClass('hasDatepicker');
        } 
         $(this).datepicker({
        	yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd",
	      autoSize: true
	    });
    });

	$('#EditDetailsForClosedIncident').hide();
	
	
});
jQuery(document).ready(function() {
	jQuery('.tabs .tab-links a').on('click', function(e) {
		var currentAttrValue = jQuery(this).attr('href');

		// Show/Hide Tabs
		jQuery('.tabs ' + currentAttrValue).show().siblings().hide();

		// Change/remove current tab to active
		jQuery(this).parent('li').addClass('active').siblings().removeClass('active');

		e.preventDefault();
	});
});

</script>
<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1><button class="btn btn-primary" onclick="back('closed')"> <i class="fas fa-chevron-left"></i></button>
					
						<!--  Incident-->
						<button type="button" name="create btn"
							class="btn btn-primary city"
							onclick="GotoCreateIncident()">Create
							Incident</button>
					</h1>
					
				</div>
				<div class="col-sm-6">
				
					<div class="float-sm-right" id="btnshowhide" style="display:none">
					<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary update" onclick="ControlIncident('Update','DisplayClosedIncident','EditDetailsForClosedIncident','SubmitFormForEditClosedIncidents','All_Incident','','','')">Update</button>
						<!-- <button name="cancel" type="button"  class="btn btn-primary update" onclick="ControlIncident('Cancel','DisplayClosedIncident','EditDetailsForClosedIncident','SubmitFormForEditClosedIncidents','Closed_Incident','','','')">Cancel</button>
				   <button name="delete" type="button"  class="btn btn-primary delete" onclick="ControlIncident('Delete','DisplayClosedIncident','EditDetailsForClosedIncident','SubmitFormForEditClosedIncidents','Closed_Incident','','','')">Delete</button>
					-->
					 <button name="Reopen" type="button" id="Reopen" class="btn btn-primary Reopen" onclick="ControlIncident('Reopen','DisplayClosedIncident','EditDetailsForClosedIncident','SubmitFormForEditClosedIncidents','Closed_Incident','','','')">Reopen</button>
					
					
					</div>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>


<div class="card">
<div id="DisplayClosedIncident">
<div class="card-body">
				<table id="displayDataForEditClosedIncident"
					class="table table-bordered table-hover displayDataForEditClosedIncident">

	
		<tr class="info">
			
		</tr>
	</table>
</div>
</div>
</div>
<div class="spanner">
  <div class="loader"></div>
  <p>Ticket is being Updating, please be patient.</p>
</div>
<div id="EditDetailsForClosedIncident">
<div class="card">
	<form name="SubmitFormForEditClosedIncidents" id="SubmitFormForEditClosedIncidents">
	<div>
			<jsp:include page="EditIncidentDiv.jsp" />	
		</div>
		<input type="hidden" name="id" value="">
						<input type="hidden" name="action" id="action" value="Update">
						<input type="hidden" name="file_name1" value="" class="form-control">
</form>
		</div>
	
<br>
<table>
			
			
			
			<tr>
					
					<td colspan="4">
						
						<button name="update" type="button"  class="btn btn-primary update" onclick="ControlIncident('Update','DisplayClosedIncident','EditDetailsForClosedIncident','SubmitFormForEditClosedIncidents','All_Incident','','','')">Update</button>
						<!-- <button name="cancel" type="button"  class="btn btn-primary update" onclick="ControlIncident('Cancel','DisplayClosedIncident','EditDetailsForClosedIncident','SubmitFormForEditClosedIncidents','Closed_Incident','','','')">Cancel</button>
					  <button name="delete" type="button"  class="btn btn-primary delete" onclick="ControlIncident('Delete','DisplayClosedIncident','EditDetailsForClosedIncident','SubmitFormForEditClosedIncidents','Closed_Incident','','','')">Delete</button>
					-->
					</td>
				</tr>
			
			</table>
			
	
	
	
<hr>

<div class="card">
<jsp:include page="RelatedInformationDiv.jsp" />
</div>
</div>	
