


<script type="text/javascript">

$(function() {
	//DisplayData('M_Company','displayCompany','');
	$('button[name="update"]').addClass('hideButton');
	
	
	EditFun('M_Company','','','');
	
	 $('.datepicker').datepicker({
       	 yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	   
   }); 
	
	
});

file4ID.onchange = function(e){
	
	  e.preventDefault();
	  var file = this.files[0];
	  if(file.type.indexOf('image/') !== 0) {
	    this.value = null;
	    alert(file.name +" has an invalid extention. \nValid extension(s):bmp, gif, jpeg, jpg, jpe, jfif, png, webp.");
	  }
	  else {
	    console.log('valid file');
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
			    	document.getElementById("image").src = "InvoiceScanFile/"+r.upload_inv;
			        document.getElementById("image1").src = "InvoiceScanFile/"+r.upload_inv;
			    		
			    }
			},'json');
	  }
	}


   
   function UpdateCompany(formName)
   {
	   
	   var t=false;
	   t = ValidationForm(formName);
	   if(t)
		   {
			   var x=$('#'+formName).serialize();
			   
			   $.post('M_Company',x,function (r){
				  	
					if(r.data)
						{
						
						bootbox.alert("Company details updated successfully");
						EditFun('M_Company','','','');
						}
			   },'json');
		   
		   }
	   
	   
	   
   }
   
  /*  document.getElementById("file4ID").onchange = function () {
	    var reader = new FileReader();

	    reader.onload = function (e) {
	        // get loaded data and render thumbnail.
	        document.getElementById("image").src = e.target.result;
	        document.getElementById("image1").src = e.target.result;
	    };

	    // read the image file as a data URL.
	    reader.readAsDataURL(this.files[0]);
	}; */
	
	
   
</script>

    <!-- Content Header (Page header) -->
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1><!--  Master Details--></h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Master</a></li>
              <li class="breadcrumb-item">Company Master</li>
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
              <div class="card-header new">
              
                <h3  class="card-title1" >Fill Your Company Details</h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
                
                
                <div id="displayCompany" class = "card-body">
    <form name="submitCompany" id="submitCompany">
		<table class="table table-bordered ">
	      	<!-- <tr class="tableHeader">
	        	<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="font-size: 24px;color: White;margin-left: 399px;">Company Master</p> </td>
	      	</tr> -->
	      	<tr>
		        <td ><b>Company Name<font color="red">*</font></b></td>
		        <td ><input type ="text"name ="nm_com" class="form-control" value="" data-valid="mand"onkeyup=" this.value = this.value.toUpperCase();"></td>
		        <td ><b>Address1<font color="red">*</font></b></td>
		        <td ><input type="text" name ="add1" class="form-control" value="" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
	      	</tr>
	      	<tr>
		        <td ><b>City<font color="red">*</font></b></td>
		        <td ><input type="text" name ="city" class="form-control" value="" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
		       <td ><b>Address2<font color="red">*</font></b></td>
		        <td ><input type="text" name ="add2" class="form-control" value="" data-valid="mand" onkeyup="this.value = this.value.toUpperCase();"></td>
		    </tr>
		     <tr>
		        <td ><b>State</b></td>
		        <td ><input type="text" name ="nm_state" class="form-control" value="" onkeyup="this.value = this.value.toUpperCase();"></td>
		        <!-- <td ><b>City Name  :</b></td>
		        <td ><input type="text" name ="nm_city" class="form-control" value=""></td> -->
		       
		       <td ><b>Email</b></td>
		        <td ><input type="email" name ="mailid" class="form-control" value="" onblur="checkEmail(this.value)" ></td>
		        
		      
		    </tr>
	      	<tr>
		        <td ><b>PIN</b></td>
		        <td ><input type="text" name ="pin" class="form-control" value="" data-valid="num"></td>
		        <td ><b>Country<font color="red">*</font></b></td>
		        <td ><input type="text" name ="country" class="form-control" value="" data-valid="mand"></td>
		    </tr>
		    <tr>
		        <td ><b>FAX</b></td>
		        <td ><input type="text" name ="fax" class="form-control" value=""></td>
		        <td ><b>Phone</b></td>
		        <td ><input type="text" name ="phone" class="form-control" value="" data-valid="num" pattern="\d*" maxlength="10"></td>
		        
		    </tr>
		     <tr>
		        <td ><b>TIN</b></td>
		        <td ><input type="text" name ="tin" class="form-control" value=""></td>
		        <td ><b>GST</b></td>
		        <td ><input type="text" name ="cst" class="form-control" value=""></td>
		        
		    </tr>
		     <tr>
		        <td ><b>PAN</b></td>
		        <td ><input type="text" name ="pan" class="form-control" value=""></td>
		        <td ><b>CIN</b></td>
		        <td ><input type="text" name ="cin" class="form-control" value=""></td>
		        
		    </tr>
		
		    <tr>
		        <td ><b>License Number</b></td>
		        <td ><input type="text" name ="license_no" class="form-control" value=""></td>
		        <td ><b>License Date</b><font color="red">*</font></td>
		        <td ><input type="text" name ="li_enddt" class="form-control datepicker" value="" data-valid="mand" disabled="true"></td>
		       
		        <input type="hidden" name="action" value="Update" >
		        <input type="hidden" name="id" value="0" >
		        <input type="hidden" name="file_name" value="" class="form-control">
		    </tr>
		    <tr>
		        <td><b>Company Logo<font color="red">*</font></b></td>
				<td>
				
				<img id="image"  style="min-width: 150px;
    min-height: 100px;
  max-width: 100px;box-shadow: 0 4px 8px 10px rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
      max-height: 100px;border: 2px solid #555;margin-right: 20px; border-radius: 10px;"/>
				
				<input id="file4ID" type="file" name="file4" class="form-control" value="" accept="image/png, image/gif, image/jpeg ,image/jpg ,image/bmp ,image/jpe ,image/jfif ,image/wepb" />
				
				
				</td>
		       <td ><b>Asset Prefix<font color="red">*</font></b></td>
		        <td ><input type="text" name ="asset_prefix" class="form-control"   value="" data-valid="mand" maxlength = "4" onkeyup="this.value = this.value.toUpperCase();"></td>
		    </tr>
		    <tr>
		        <td height="36" colspan="4" align="center" ><center>
		        	<button type="button"   class="btn btn-primary" onclick="UpdateCompany('submitCompany')">Update</button></center>
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
            
      
