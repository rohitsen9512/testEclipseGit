<!--Mail_master.jsp-->


<script type="text/javascript">

$(function() {
	//DisplayData('M_Mail','displayMail','');
	$('button[name="update"]').addClass('hideButton');
	
	
	EditFun('M_Mail_Config','','','');
	
	
	
	
});

   
   function UpdateMail(formName)
   {
	   
	   var t=false;
	   t = ValidationForm(formName);
	   if(t)
		   {
			   var x=$('#'+formName).serialize();
			   
			   $.post('M_Mail_Config',x,function (r){
				  	
					if(r.data)
						{
						
						bootbox.alert("Mail details updated successfully");
						EditFun('M_Mail_Config','','','');
						}
			   },'json');
		   
		   }
	   
	   
	   
   }
   
   function ConfirmPasswordCheck(){
		
		var cpwd = $('#cpwdConfirm').val();
		var pwd = $('#pwdConfirm').val();
		t=true;
		$('#cpwdConfirm').removeClass('error');
		
			if(cpwd != pwd)
				{
					t=false;
					bootbox.alert('Password should be same');
					$('#cpwdConfirm').addClass('error');
					
				}
		
			return t;
		}

   
</script>
 <!-- Content Wrapper. Contains page content -->
  
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1><!--  Mail Configuration --></h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Master</a></li>
              <li class="breadcrumb-item">Mail Configuration</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="card">
        <div class="card-header new">
          <h3 class="card-title1">Mail-Configuration</h3>
        </div>
        <!-- /.card-header -->
        <div class="card-body">
          

<div id="displayMail">
    <form name="submitMail" id="submitMail">
		<table class="table table-bordered ">
	      	<!-- < <tr class="tableHeader">
	        	<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="font-size: 24px;color: white;margin-left: 399px;">Mail Master</p> </td>
	      	</tr> -->
	      	<tr>
		        <td ><b>Mail ID<font color="red">*</font></b></td>
		        <td ><input type ="text"name ="mail_id" class="form-control" value="" data-valid="mand"></td>
		       <td ><b>Password<font color="red">*</font></b></td>
				  <td ><input type="password" id="pwdConfirm" class="form-control"  name ="mail_pwd" data-valid="mand" ></td>
			  </tr>
			  <tr>
				  <td ><b>Confirm Password<font color="red">*</font></b></td>
				  <td ><input id="cpwdConfirm" class="form-control" type=password name ="mail_pwd"  data-valid="mand" onBlur="return ConfirmPasswordCheck()"></td>
		        <td ><b>Host Name<font color="red">*</font></b></td>
		        <td ><input type="text" name ="nm_host" class="form-control" value="" data-valid="mand"></td>
		      
		    </tr>
		     <tr>
		        <td ><b>Port No<font color="red">*</font></b></td>
		        <td ><input type="text" name ="no_port" class="form-control" value="" data-valid="mand"></td>
		        
		         <td ><b>Days prior to AMC/Warranty end<font color="red">*</font></b></td>
		        <td ><input type="text"  name ="no_days" class="form-control" value="" onkeypress="return (event.charCode !=8 && event.charCode ==0 || (event.charCode >= 48 && event.charCode <= 57))" data-valid="mand"></td> 
		       
		      
		    </tr>
		    
		    <tr>
		        <td ><b>Support Mail</b></td>
		        <td ><input type="text" name ="support_mail" class="form-control" value="" ></td>
		        
		    </tr>
	      	
		<tr  >
	        	<td colspan="4" class="new"><p class="tableHeaderContent" style="font-size: 24px;margin-left: 275px;">Helpdesk Mail Configuration Details</p> </td>
	      	</tr>
	      	<tr>
		        <td><b>Email ID<font color="red">*</font></b></td>
		        <td><input type ="text" name ="helpdesk_nm_mail" class="form-control" value="" data-valid="email"></td>
		        <td><b>Password<font color="red">*</font></b></td>
		        <td><input type="password" name ="helpdesk_pwd" class="form-control" value="" data-valid="mand"></td>
	      	</tr>
	      	<tr>
		        <td><b>Host Name<font color="red">*</font></b></td>
		        <td><input type="text" name ="helpdesk_nm_host" class="form-control" value="" data-valid="mand"></td>
		        <td><b>Port No<font color="red">*</font></b></td>
		        <td><input type="text" name ="helpdesk_nm_port" class="form-control" value="" data-valid="mand"></td>
		    </tr>
		
		    <tr>
		       
		       
		        <input type="hidden" name="action" value="Update" >
		        <input type="hidden" name="id" value="0" >
		       
		    </tr>
		   
		    <tr>
		        <td height="36" colspan="4" align="center" ><center>
		        	<button type="button"   class="btn btn-primary" onclick="UpdateMail('submitMail')">Update</button></center>
				</td>
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
  
  
  
  

</body>
</html>
 