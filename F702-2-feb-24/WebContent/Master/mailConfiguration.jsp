<!--Company_master.jsp-->


<script type="text/javascript">

$(function() {
	//DisplayData('MailConfgr','displayCompany','');
	$('button[name="update"]').addClass('hideButton');
	
	
	EditFun('MailConfiguration','','','');
	
	$('.datepicker').datepicker({
       	 yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd",
	      autoSize: true
	   
   });
	
	
});
   
   
   function UpdateMailConfgr(formName)
   {
	   
	   var t=false;
	   t = ValidationForm(formName);
	   if(t)
		   {
			   var x=$('#'+formName).serialize();
			   
			   $.post('MailConfiguration',x,function (r){
				  	
					if(r.data)
						{
						
						bootbox.alert("Updated successfully");
						EditFun('MailConfgr','','','');
						}
			   },'json');
		   
		   }
	   
	   
	   
   }
</script>

 <!-- Content Header (Page header) -->
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>Master Details</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Master</a></li>
              <li class="breadcrumb-item active">Company Master</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>
    
<section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-12">
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">Fill Your Company Details</h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
              
<div id="displayCompany">
    <form name="submitMailConfgr" id="submitMailConfgr">
		<table class="table table-bordered ">
	      	<tr class="tableHeader">
	        	<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 399px;">Mail Configuration Details</p> </td>
	      	</tr>
	      	<tr>
		        <td><b>Email Id <font color="red">*</font></b></td>
		        <td><input type ="text" name ="nm_mail" class="common-validation" value="" data-valid="email"></td>
		        <td><b>Password<font color="red">*</font></b></td>
		        <td><input type="text" name ="pwd" class="common-validation" value="" data-valid="mand"></td>
	      	</tr>
	      	<tr>
		        <td><b>Host Name<font color="red">*</font></b></td>
		        <td><input type="text" name ="nm_host" class="common-validation" value="" data-valid="mand"></td>
		        <td><b>Port No <font color="red">*</font></b></td>
		        <td><input type="text" name ="nm_port" class="common-validation" value="" data-valid="mand"></td>
		    </tr>
	      	
		    <input type="hidden" name="action" value="Update" >
		        <input type="hidden" name="id" value="0" >
		    <tr>
		        <td height="36" colspan="4" align="center" ><center>
		        	<button type="button"   class="btn btn-primary" onclick="UpdateMailConfgr('submitMailConfgr')">Update</button></center>
				</td>
	      	</tr>
	   	</table>
   	</form>
 </div>
     
              </div>
              <!-- /.card-body -->
            </div>
            </div>
            </div>
            </div>
            </section>
            