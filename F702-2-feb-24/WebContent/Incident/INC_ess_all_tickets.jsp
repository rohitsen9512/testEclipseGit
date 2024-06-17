<!--Edit_from_store.jsp-->


<script type="text/javascript">

jQuery("input#file4ID1").change(function () {
	var filename="";
	var files = $("#file4ID1").get(0).files;
    var fileData = new FormData();
//
    for (var i = 0; i < files.length; i++) {
    	//
        
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
	
	$(".Activity").click(function(){
        $(".activityMessage").toggle(1000);
    });
getapi();
//getasseetapi();
	/* DisplayDropDownDataForGroup('M_Asset_Div','requestPrAssetGroup','CapG',function (status){
		if(status)
		{ */
			DropDownDataDisplay('M_Group','depDataForNewTicket',function (status){
				if(status)
				{
					DisplayDropDownData("M_Emp_User","empForNewTicketHD",function (status){
						if(status)
						{
							DisplayDropDownData('M_Designation','id_design',function (status){
								if(status)
								{
							ControlIncidentEss('Mytickets','DisplayAllTicket','EditDetailsForMyIncident','SubmitFormForEditMyIncidents','New_Incident','displayDataForYourAllTicket','','');
							
							if(status)
							{
							listToBeParent();
							}
			
								}}); 
					}});
				
			}});
		 
/* }}); */
						
	
	
});

$('#EditDetailsForMyIncident').hide();
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
				
					<h1><button class="btn btn-primary" onclick="back('Myincidents')"> <i class="fas fa-chevron-left"></i></button>
					
						<!--  Incident-->
						
					</h1>
					
				</div>
				<div class="col-sm-6">
					
				<div class=float-sm-right id="reopen_div" style="display:none;margin-left:400px;">
												<button type="button"  class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal"><i class="fas fa-paperclip"></i></button>
								
								 <button name="Reopen" type="button" id="Reopen_btn" class="btn btn-primary Reopen" onclick="ControlIncident('Reopen','DisplayAllTicket','EditDetailsForMyIncident','SubmitFormForEditMyIncidents','New_Incident','','','')">Reopen</button>
				</div>
				<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h3 class="modal-title">Attachments</h3>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
         
				<!-- <td><input id="file4ID1" type="file" name="file1" style="margin-left: 5px; width: 300px;" class="form-control" value="" > -->
					<!-- <td><input type="file" id="file4ID1" name="file1" multiple> -->
				<input type="file" id="file4ID1" name="file1" multiple />
				
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal" onclick="ControlIncident('Upload','DisplayAllTicket','EditDetailsForMyIncident','SubmitFormForEditMyIncidents','New_Incident','','','')">Upload</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>	
					</div>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>

<div class="spanner">
  <div class="loader"></div>
  <p>Ticket is being Updating, please be patient.</p>
</div>
<div id="DisplayAllTicket" class="card">
	<table class="table table-bordered displayDataForYourAllTicket" id="displayDataForYourAllTicket">
		<tr class="info">
			
		</tr>
	</table>
</div>

<div id="EditDetailsForMyIncident">
								 
<div class="card">
	<form name="SubmitFormForEditMyIncidents" id="SubmitFormForEditMyIncidents">
	<div>
			<jsp:include page="EditIncidentDiv.jsp" />	
		</div>
		<input type="hidden" name="action" id="action"value="">
		<input type="hidden" name="file_name1" value="" class="form-control">
</form>
		</div>
		</div>
