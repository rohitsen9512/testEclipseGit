<style>
body{
    margin-top:20px;
    color: #1a202c;
    text-align: left;
    background-color: #e2e8f0;    
}
.main-body {
    padding: 15px;
}
.card {
    box-shadow: 0 1px 3px 0 rgba(0,0,0,.1), 0 1px 2px 0 rgba(0,0,0,.06);
}
#message {
  display:none;
  background: #f1f1f1;
  color: #000;
  position: relative;
  /* padding: 10px; */
  margin-top: 5px;
}
#message p {
 /*  padding: 5px 5px; */
  font-size: 18px;
}
.card {
    position: relative;
    display: flex;
    flex-direction: column;
    min-width: 0;
    word-wrap: break-word;
    background-color: #fff;
    background-clip: border-box;
    border: 0 solid rgba(0,0,0,.125);
    border-radius: .25rem;
}

.card-body {
    flex: 1 1 auto;
    min-height: 1px;
    padding: 1rem;
}

.gutters-sm {
    margin-right: -8px;
    margin-left: -8px;
}

.gutters-sm>.col, .gutters-sm>[class*=col-] {
    padding-right: 8px;
    padding-left: 8px;
}
.mb-3, .my-3 {
    margin-bottom: 1rem!important;
}

.bg-gray-300 {
    background-color: #e2e8f0;
}
.h-100 {
    height: 100%!important;
}
.shadow-none {
    box-shadow: none!important;
}
</style>

<div class="container">
<%
		HttpSession session2 = request.getSession();  
	String output="";
	String email="";
	String usertype="";
	String phone="";
	String entity="";
	String logo="";
	
		if(null == session2.getAttribute("UserName"))
		{	
			response.sendRedirect("index.html");
		}	
		else
		{
			String nm_emp = (String)session2.getAttribute("UserName");
			String id_email = (String)session2.getAttribute("id_emp");
			String UserType = (String)session2.getAttribute("UserType");
			String nm_com = (String)session2.getAttribute("nm_com");
			String cont_no = (String)session2.getAttribute("cont_no");
			logo = (String)session2.getAttribute("emp_image");
			
			 
			
			output=nm_emp;
			System.out.print(output);
			email=id_email;
			System.out.print(email);
			usertype=UserType;
			phone=cont_no;
			System.out.print(phone);
			entity=nm_com;
			System.out.print("chanchal tryinghsdvsd!=============!"+logo);
		}
		
	%>
	<script type="text/javascript">
	const togglePassword = document.querySelector('#togglePassword');
	  const password = document.querySelector('#oldPwd');

	  togglePassword.addEventListener('click', function (e) {
	    // toggle the type attribute
	    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
	    password.setAttribute('type', type);
	    // toggle the eye slash icon
	    this.classList.toggle('fa-eye-slash');
	});
	  const togglePassword1 = document.querySelector('#togglePassword1');
	  const password1 = document.querySelector('#newPwd');
	  
	  togglePassword1.addEventListener('click', function (e) {
		    // toggle the type attribute
		    const type = password1.getAttribute('type') === 'password' ? 'text' : 'password';
		    password1.setAttribute('type', type);
		    // toggle the eye slash icon
		    this.classList.toggle('fa-eye-slash');
		});
	  const togglePassword2 = document.querySelector('#togglePassword2');
	  const password2 = document.querySelector('#confirmPwd');

	  togglePassword2.addEventListener('click', function (e) {
	    // toggle the type attribute
	    const type = password2.getAttribute('type') === 'password' ? 'text' : 'password';
	    password2.setAttribute('type', type);
	    // toggle the eye slash icon
	    this.classList.toggle('fa-eye-slash');
	});
	fileID.onchange = function(e){
		  e.preventDefault();
		  var file = this.files[0];
		  if(file.type.indexOf('image/') !== 0) {
		    this.value = null;
		  //  bootbox.alert('Danger!!' ).find('.modal-content').css({'background-color': '#f99', 'font-weight' : 'bold', color: '#F00', 'font-size': '2em', 'font-weight' : 'bold'} );
		  //  bootbox.alert("<center>"+file.name +" has an invalid extention.</center> <center>Valid extension(s): bmp, gif, jpeg, jpg, jpe, jfif, png, webp.</center>");
		 
		    bootbox.alert({
		        message: '<center>'+file.name +' has an invalid extension.</center> <center>Valid extension(s): bmp, gif, jpeg, jpg, jpe, jfif, png, webp.</center>',
		        buttons: {
		          ok: {
		            label: 'OK',
		            	className: 'btn-danger'
		          }
		        }
		      });
		  }
		  else {
		    console.log('valid file');
		    var formData = new FormData();	 
			formData.append('file', $('#fileID').get(0).files[0]);
			
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
				    	
				    		$('input[name="emp_logo"]').val(r.upload_inv);
				    		ChangeUserImg();
				    		bootbox.alert("File uploaded successfully");
				    		
/* 				    		Swal.fire({
				    			  position: 'top',
				    			  icon: 'success',
				    			  title: 'File uploaded successfully.',
				    			  showConfirmButton: false,
				    			  timer: 1500
				    			}) */
				    		/* bootbox.alert({
						        message: '<center>File uploaded successfully.</center>',
						        buttons: {
						          ok: {
						            label: 'OK'
						          }
						        }
						      });*/
				    } 
				},'json');
		  }
		}
	
	/* document.getElementById("fileID").onchange = function () {
	    var reader = new FileReader();

	    reader.onload = function (e) {
	        // get loaded data and render thumbnail.
	        document.getElementById("image").src = e.target.result;
	    };

	    // read the image file as a data URL.
	    reader.readAsDataURL(this.files[0]);
	}; */
function ChangeUserImg(){
		
		var img = $('input[name="emp_logo"]').val();
		//alert(img);
							$.post('M_Emp_User', {action : "Updateimg",id:img},function(r){
								//console.log(r.data);
								if(r.data == 2)
									{
										//alert("image has changed successfully.");
										//session.setAttribute("emp_image", logo);
									
									}
								else if(r.data == 1)
									{
										alert("Please Try Again Later.");
									}
							
							},'json');
							document.getElementById("image2").src="InvoiceScanFile/"+img;
							document.getElementById("image").src="InvoiceScanFile/"+img;
							
						}
</script>
    <div class="main-body">
    
          <!-- Breadcrumb -->
          <!-- <nav aria-label="breadcrumb" class="main-breadcrumb">
            <ol class="breadcrumb">
              <li class="breadcrumb-item">Home</li>
              <li class="breadcrumb-item">User</li>
              <li class="breadcrumb-item" aria-current="page">User Profile</li>
            </ol>
          </nav> -->
          <!-- /Breadcrumb -->
    
          <div class="row gutters-sm">
            <div class="col-md-4 mb-3">
              <div class="card">
                <div class="card-body">
                  <div class="d-flex flex-column align-items-center text-center">
                    <img id="image"  style="min-width: 150px;
    min-height: 100px;
    max-width: 150px;
    max-height: 100px;border: 2px solid #555;margin-right: 20px; border-radius: 10px;"/>
                   <div class="wrapper">
  <div class="file-upload">
    <input style="margin-left: 80px;" type="file" id="fileID" name="file4" accept="image/png, image/gif, image/jpeg ,image/jpg ,image/bmp ,image/jpe ,image/jfif ,image/wepb" />
    <input type="hidden" name="emp_logo" value="" class="form-control">
    
  </div>
   
</div>
                    <div class="mt-3">
                      <h4><%= output %></h4>
                      <p class="text-secondary mb-1"><%= usertype %></p>
                      <p class="text-muted font-size-sm"><%= entity %></p>
                     
                    </div>
                  </div>
                </div>
              </div>
              
            </div>
            <div class="col-md-8">
              <div class="card mb-3">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Full Name</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <%= output %>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Email</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <%= email %>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Mobile</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <%= phone %>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Company</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <%= entity %>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Create New Password</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <a href="#" onclick="ForgotPassword('change_pwd');">Change Password</a>
                    </div>
                  </div>
                 <!--  <hr> -->
                  <!-- <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Change User Image</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <a href="#" onclick="ChangeUserImg()">Upload</a>
                    </div>
                  </div> -->
                  
				 
                
                              </div>
                             
                              <div id="forgetTable" align="center"
				style="display: none; margin-top: 20px;">
				<div id="loginHeader" style="width: 500px;" class="alert alert-info">Change
					Password</div>

				<form action="" name="changePassword" id="changePassword"
					method="post">
					<input name="action" type="hidden" value="ChangePassword">
					<div class="input-prepend" title="current password"
						style="margin:10px 0px 0px 70px;display: inline-flex;">
						<span class="add-on" style="width: 120px;">Current Password</span><input
							autofocus="" class="form-control" style="width: 250px;"
							name="oldPwd" id="oldPwd" data-valid="mand" type="password"><i class="far fa-eye" id="togglePassword" style="margin-left: -30px;margin-top: 11px; cursor: pointer;"></i>
							
					</div>
					<div>
						<p id="currpw" style="width: 922px;display: none" ><font color="red">Current password is required.</font></p>
					</div>
					<div class="clearfix"></div>
					<div class="input-prepend" title="new password"
						style="margin:10px 0px 0px 70px;display: inline-flex;">
						<span class="add-on" style="width: 120px;">New Password</span><input
							autofocus="" class="form-control" style="width: 250px;"
							name="newPwd" id="newPwd" data-valid="mand" type="password"><i class="far fa-eye" id="togglePassword1" style="margin-left: -30px;margin-top: 11px; cursor: pointer;"></i>
					</div>
					<div>
						<p id="newpw"style="width: 922px;display: none"><font color="red">New password is required.</font></p>
					</div>

					<div class="clearfix"></div>
					<div class="input-prepend" title="password"
						style="margin:10px 0px 0px 70px;display: inline-flex;">
						<span class="add-on" style="width: 120px;">Confirm Password</span><input
							autofocus="" class="form-control" style="width: 250px;" name="confirmPwd"
							id="confirmPwd" type="password"
							><i class="far fa-eye" id="togglePassword2" style="margin-left: -30px;margin-top: 11px; cursor: pointer;"></i>
					</div>
					<div class="clearfix"></div>
					<!-- <a id="forgetPass" style="margin-left: 200px;">Forgot Password?</a> -->

					<p class="center">
						<button type="button" style="margin-left: 2.8rem; margin-top:20px;" class="btn btn-primary"
							onclick="ChangePassword();">Change Password</button>
						<button type="button" style="margin-left: 10px; margin-top:20px;"
							class="btn btn-primary" onclick="ForgotPassword('Cancel');">Cancel</button>
					</p>

				</form>
			</div>
						<div id="message">
  <h3>Password must contain the following:</h3>
  <p id="letter" class="invalid">At least <b>lowercase</b> letter</p>
  <p id="capital" class="invalid">At least <b>capital (uppercase)</b> letter</p>
  <p id="number" class="invalid">At least <b>number</b></p>
  <p id="length" class="invalid">Minimum <b>8 characters</b></p>
</div>
              </div>
            </div>
          </div>
        </div>
    </div>
    <script type="text/javascript">

	

function ChangePassword()
	{
		var UserType = '<%= (String)session2.getAttribute("UserType") %>';
	
	
		//alert();
	 	debugger;
	 	ValidationPassword();
		t=checkNewPassword();
		if (t){
			y=ConfirmPasswordCheck();
			if(y){
				if(UserType!=null)
						{	
						var x = $('#changePassword').serialize();
						$.post('M_User_Login',x,function (r){
							console.log(r.data);
							if(r.data == 2)
								{
									alert("Password changed successfully.");
									location.reload(true);
								}
							else if(r.data == 1)
								{
									alert(" Current password didn't match.");
								}
						
						},'json');
			}
			}		
	
		}
	}	
						
						function ForgotPassword(type){
							//alert(type);
							if(type=='change_pwd'){
								$("#mainBody").hide();
							
								$("#forgetTable").show();
							}
						if(type=='Cancel'){
							$("#mainBody").show();
							$("#forgetTable").hide();
							
							}
							
							
						}
					function ConfirmPasswordCheck(){
							
							var cpwd = $('#confirmPwd').val();
							var pwd = $('#newPwd').val();
							var name=$("#oldPwd").val();
							y=true;
							
							$('#confirmPwd').removeClass('error');
							
								if(cpwd != pwd)
									{
										y=false;
										//$("#mainBody").show();
										bootbox.alert('Password and confirm password must be same');
										$('#confirmPwd').addClass('error');
										
									} 
								if(cpwd==name || pwd==name)
									{
										bootbox.alert('You entered the password that is the same as one of the last passwords you used. Please create a new password');
									}
							
								return y;
							}	
					 document.getElementById("image").src="InvoiceScanFile/<%=logo%>";
					//alert(image); 
					
    function ValidationPassword() 
	{
		var name=$("#oldPwd").val();
		var pass=$("#newPwd").val();
		var confirmpass=$("#confirmPwd").val();
		if(name=="")
				{
			$("#currpw").show();
			exit(0);
				}
		else if(pass=="")
			{
			$("#newpw").show();
			exit(0);
			}
		else if(confirmpass=="")
			{
			alert("Confirm password is required.");
			
			exit(0);
			}
		else if(name=="" && pass=="" && confirmpass=="")
			{
			alert("Please Enter the current password, new password and confirm password .");
			exit(0);
			}
	}	
    function checkNewPassword()
    {
    	t=true;
		debugger;	
									var pass=$("#newPwd").val();
    	                            var re = /^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,})$/;
    	                            if(!re.test(pass)){
    		t=false;
    		alert("Password must contains with mix of 8 characters in combination of at least one uppercase, lowercase and digits.");
    		                            /* $('input[name="newPwd"]').val(""); */
    		
    	}
    	return t;
    	                            
 }  
		
</script>
					
			