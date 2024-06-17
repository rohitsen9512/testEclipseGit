<!--user.jsp-->


<style>
.disableHide{
display:none;
}

</style>
<!-- <style>
      .easy-autocomplete-container { max-height: 250px; overflow: auto; overflow-y: auto; overflow-x:hidden; }
    .select2-results__options{ font-size:12px !important;}
.select2-container .select2-selection--single {font-size:12px;}
    </style>
    
    <script src='bootstrap/js/select2.min.js' type='text/javascript'></script>
<link href='bootstrap/css/select2.min.css' rel='stylesheet' type='text/css'>
<script src="Autocomp/jquery.easy-autocomplete.min.js"></script> 
<link rel="stylesheet" href="Autocomp/easy-autocomplete.min.css"> 
<link rel="stylesheet" href="Autocomp/easy-autocomplete.themes.min.css"> 

<script type="text/javascript">
$("#empForUser").select2();

</script> -->
<script type ="text/javascript" src="js/userMaster.js"></script>
<script type="text/javascript">


$('#userEmail').attr('disabled','disabled');

$(function() {
	
	
	hideShowDisableDate($('input[name="status_user"]').val());
	$('button[name="update"]').addClass('hideButton');
	DisplayDropDownData('M_Emp_User','empForUser' , function(status){
		if(status)
			{
				DisplayDropDownData('M_Emp_User','managerData' , function(status){
					if(status)
					{
					DisplayDropDownData('M_Dept','deptForEmp',function (status){
						if(status)
						{
							DisplayData('M_User_Login','displayUser','createUser');
						}});
					/* DisplayDropDownData("M_Loc","locDataForUser",function (status){
						if(status)
							{ */
							DropDownDataDisplay("M_Division","divDataForUser",function (status){
								if(status)
									{

									DropDownResultLoginProvision('M_User_Type','usertypeForEmpUser',function (status){
										if(status)
											{
											
											}}); 
									}});
							/* }});
					 */
					
					}});
			
			}});
	
});


/* function DataForCreatingUser()
{
	$.post('M_Emp_User',{action : 'DisplayEmpForLogin'},function (r){
			if(r.data)
				{
					var list= '<option value=""> Select</option>';
					for(var i = 0; i < r.data.length ; i++)
					{
					for (var key in r.data[i])
			        {
						id=r.data[i][key];
						break;
			        }
					for (var key in r.data[i])
			        {
						val=r.data[i][key];
			        }
					list = list + '<option value="'+id+'"> '+val+'</option>';
					}
					$('#empForUser').html(list);
				}
			else
				{
					bootbox.alert("Try again.");
				}
			
		},'json');
	} */
	
	
	
	function Valcheck() 
	{
		debugger;
		
		var empForUser=$("#empForUser").val();
		var id_usertype=$("#usertypeForEmpUser").val();
		var userEmail=$("#userEmail").val();
		var nm_login=$("#nm_login").val();
		var pwdConfirm=$("#pwdConfirm").val();
		var cpwdConfirm=$("#cpwdConfirm").val();
		if(empForUser==""||empForUser==undefined)
		{
	      $("#emp_user").attr('style', 'display:block;');
	    
		}
		 if(id_usertype==""||id_usertype==undefined)
			{
		      $("#id_usertyp").attr('style', 'display:block;');
		     
			}
		 if(userEmail)
		 {
		    atpos = userEmail.indexOf("@");
          dotpos = userEmail.lastIndexOf(".");
               if (atpos < 1 || (dotpos - atpos < 2)) {
   	  $("#useEmail").attr('style', 'display:block;');
   	  
                  } 
		
		}
	
		 if(nm_login==""){
			  $("#nmlogin").attr('style', 'display:block;');
		 }
		 if(username.length < 2 || username.length > 15)
		 {
			 
		 }
		 
		 if(pwdConfirm==""){
			  $("#passConfirm").attr('style', 'display:block;');
			 
		}
		 if(cpwdConfirm==""){
			  $("#cpassConfirm").attr('style', 'display:block;');
			 
		}
		if(cpwdConfirm!=""&&pwdConfirm!=""&&nm_login!=""&&userEmail!=""&&userEmail!=""&&empForUser!="")
			{
			 
			DataInsertUpdate("Save",'displayUser','createUser','submitUser','M_User_Login','Login name already exist.','nm_login','Login provision has created succesfully.');
			}
	}
		
	
	function getUserType() { 
		/* alert("Hello"); */
		debugger;
		var id_usertype = $('select[name="id_usertype"]').val();
		
	    $.post('M_User_Type',{action : 'getUserType',id_usertype:id_usertype},function (r){
			debugger;
			if(r.data)
				{
					$('input[name="type_user"]').val(r.data[0].nm_usertype);
					

				}
			
			
		},'json');
	    
	  }
function CheckUsername(){
	var username = $('#nm_login').val();
	
	if(username.length < 2 || user.name.length < 15 ){
		$('#nm_login').val('');
		bootbox.alert("Login name must be between 2 and 15 characters.");
	}
}

function ValidationPassword() 
{
	debugger;
var pass=$("#pwdConfirm").val().trim();
var confirmpass=$("#cpwdConfirm").val().trim();

if(pass=="")
{
	$('#pwdConfirm').addClass('error');
	$("#pwdConfirm").val('');
	$("#pwdConfirm").focus();
}
else if(confirmpass=="")
{
	$('#cpwdConfirm').addClass('error');
	$("#cpwdConfirm").val('');
	$("#cpwdConfirm").focus();
}
else if(pass=="" && confirmpass=="")
{
alert("Please Enter the new password and confirm password .");
$('#cpwdConfirm').addClass('error');
$('#pwdConfirm').addClass('error');
}
 function checkNewPassword()
{ t=true;
var pass=$("#pwdConfirm").val();
var re = /^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,})$/;

}Â 	 

}

/* function checkNewPassword()
{ t=true;
var pass=$("#pwdConfirm").val();
          var re = /^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,})$/;
        
Â Â Â Â Â Â if(!re.test(pass)){
           t=false; */
 /*  alert("Password must contains with mix of 8 characters in combination of at least one uppercase, lowercase and digits.");
Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â 
}
return t;
Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â Â  
}Â 	 */


function ValidateCat(action)
{
	debugger;
	var nm_Cat1=$("#empForUser").val().replace(/\s+/g, " ").trim();
	 $("#empForUser").val(nm_Cat1);
	 
	 var nm_Cat2=$("#userEmail").val().replace(/\s+/g, " ").trim();
	 $("#userEmail").val(nm_Cat2);
	 
	 var nm_Cat3=$("#empForUser1").val().replace(/\s+/g, " ").trim();
	 $("#empForUser1").val(nm_Cat3);
	 
	 var nm_Cat4=$("#pwdConfirm").val().replace(/\s+/g, " ").trim();
	 $("#pwdConfirm").val(nm_Cat4);
	 
	 var nm_Cat5=$("#cpwdConfirm").val().replace(/\s+/g, " ").trim();
	 $("#cpwdConfirm").val(nm_Cat5);
	 
	 var nm_Cat6=$("#usertypeForEmpUser").val().replace(/\s+/g, " ").trim();
	 $("#usertypeForEmpUser").val(nm_Cat6);
	 
	 if(nm_Cat1=="" && nm_Cat2=="" && nm_Cat3=="" && nm_Cat4=="" && nm_Cat5=="" && nm_Cat6=="")
		{
				
				document.getElementById("hideshowlabel0").style.display = "block";
				document.getElementById("hideshowlabel1").style.display = "block";
				document.getElementById("hideshowlabel2").style.display = "block";
				document.getElementById("hideshowlabel3").style.display = "block";
				document.getElementById("hideshowlabel4").style.display = "block";
				document.getElementById("hideshowlabel5").style.display = "block";
				
				document.getElementById("minmax2").style.display = "none";
				
				}
	 else if(nm_Cat1=="")
		{
		 document.getElementById("hideshowlabel0").style.display = "block";
			document.getElementById("hideshowlabel1").style.display = "none";
			document.getElementById("hideshowlabel2").style.display = "none";
			document.getElementById("hideshowlabel3").style.display = "none";
			document.getElementById("hideshowlabel4").style.display = "none";
			document.getElementById("hideshowlabel5").style.display = "none";
			
			document.getElementById("minmax2").style.display = "none";
		}
	 else if(nm_Cat2=="")
		{
		 document.getElementById("hideshowlabel0").style.display = "none";
			document.getElementById("hideshowlabel1").style.display = "block";
			document.getElementById("hideshowlabel2").style.display = "none";
			document.getElementById("hideshowlabel3").style.display = "none";
			document.getElementById("hideshowlabel4").style.display = "none";
			document.getElementById("hideshowlabel5").style.display = "none";
			
			document.getElementById("minmax2").style.display = "none";
		}
	 else if(nm_Cat3=="")
		{
		 document.getElementById("hideshowlabel0").style.display = "none";
			document.getElementById("hideshowlabel1").style.display = "none";
			document.getElementById("hideshowlabel2").style.display = "block";
			document.getElementById("hideshowlabel3").style.display = "none";
			document.getElementById("hideshowlabel4").style.display = "none";
			document.getElementById("hideshowlabel5").style.display = "none";
			
			document.getElementById("minmax2").style.display = "none";
		}
	 else if(nm_Cat4=="")
		{
		 document.getElementById("hideshowlabel0").style.display = "none";
			document.getElementById("hideshowlabel1").style.display = "none";
			document.getElementById("hideshowlabel2").style.display = "none";
			document.getElementById("hideshowlabel3").style.display = "block";
			document.getElementById("hideshowlabel4").style.display = "none";
			document.getElementById("hideshowlabel5").style.display = "none";
			
			document.getElementById("minmax2").style.display = "none";
		}
	 else if(nm_Cat5=="")
		{
		 document.getElementById("hideshowlabel0").style.display = "none";
			document.getElementById("hideshowlabel1").style.display = "none";
			document.getElementById("hideshowlabel2").style.display = "none";
			document.getElementById("hideshowlabel3").style.display = "none";
			document.getElementById("hideshowlabel4").style.display = "block";
			document.getElementById("hideshowlabel5").style.display = "none";
			
			document.getElementById("minmax2").style.display = "none";
		}
	 else if(nm_Cat6=="")
		{
		 document.getElementById("hideshowlabel0").style.display = "none";
			document.getElementById("hideshowlabel1").style.display = "none";
			document.getElementById("hideshowlabel2").style.display = "none";
			document.getElementById("hideshowlabel3").style.display = "none";
			document.getElementById("hideshowlabel4").style.display = "none";
			document.getElementById("hideshowlabel5").style.display = "block";
			
			document.getElementById("minmax2").style.display = "none";
		}
	 else if (nm_Cat3.length < 2 || nm_Cat3.length > 15  ) {
		 document.getElementById("hideshowlabel0").style.display = "none";
			document.getElementById("hideshowlabel1").style.display = "none";
			document.getElementById("hideshowlabel2").style.display = "none";
			document.getElementById("hideshowlabel3").style.display = "none";
			document.getElementById("hideshowlabel4").style.display = "none";
			document.getElementById("hideshowlabel5").style.display = "none";
			
			document.getElementById("minmax2").style.display = "block";
		  }
		
	else	{
		Emailsave(action);
	}	  

function Emailsave(action)
{	
	debugger;
	y=checkNewPassword();
	t=checkEmail();
	if (t && y)
	{
		if(action == 'Save')
		 {
			ControlDiv('Save','displayUser','createUser','submitUser','M_User_Login','Login name is already exists','nm_login','Login provision has created successfully.');
		     }
		else{
			ControlDiv('Update','displayUser','createUser','submitUser','M_User_Login','Login name is already exists','','Login provision has updated successfully.');
		    }
	}
}
}
function checkEmail()
{
	t=true;
	var str=$("#userEmail").val();
	/* var str=$("#id_emp").val(); */
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(!re.test(str)){
    	 t=false;
   
    //bootbox.alert("Please enter a valid email address");
    /*$('input[name="id_email"]').val("");*/
    }
    return t;
    
}
/* function CheckUsername(){
var username = $('#nm_login').val();
debugger;
if(username.length <2){
	$('#nm_login').val('');
	bootbox.alert("Log in Name is greater than 2 Charachter");
}
} */

function validateInput(input) {
var inputValue = input.value;

// Remove leading and trailing spaces
inputValue = inputValue.trim();

var validCharacters = /^[a-zA-Z0-9]+$/;
if (!validCharacters.test(inputValue)) {

    bootbox.alert("Please provide valid character");
    $(input).addClass('error');
    return false;
} 
 else {

    $(input).removeClass('error');
    return true;
}
CheckUsername(); 
}

function checkNewPassword()
{
	y=true;	
	var pass=$("#pwdConfirm").val();
	var re = /^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,})$/;
	if(pass ==""){
		y=false;
		//alert("Password is required.");
		                            /* $('input[name="newPwd"]').val(""); */
		
	}
	                            else if(!re.test(pass)){
		y=false;
		alert("Password must contains with mix of 8 characters in combination of at least one uppercase, lowercase and digits.");
		                            /* $('input[name="newPwd"]').val(""); */
		
	}
	return y;
	                            
}  
	


function hideShowDisableDate(val){
		if(val == 'undefined' || val ==1){
			$('.disableDate').hide();
			$('.enableDate').show();
			$('input[name="dt_disable"]').removeAttr("data-valid");
		}else{
			$('.disableDate').show();
			$('.enableDate').hide();
			$('input[name="dt_disable"]').attr("data-valid", "mand");
			
			var currentDate = new Date();
			$( ".disabledatepicker" ).datepicker({
				yearRange: '1985:2025',
			      changeMonth: true,
			      changeYear: true,
			      dateFormat: "dd/mm/yy",
			      autoSize: true,
			      altFormat: "dd/mm/yy",
			      minDate: 0,
			      beforeShow: function(input, inst) {
			            // Disable keyboard input and pasting
			            $(input).prop("readonly", true);
			        },
			    });
			
			$('.disabledatepicker').datepicker("setDate", new Date());
			// $('input[name="dt_deliver"]').datepicker("setDate", currentDate);
			   
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
				bootbox.alert('Password and confirm password must be same.');
				$('#cpwdConfirm').addClass('error');
				
			}
	
		return t;
	}

const togglePassword = document.querySelector('#togglePassword');
const password = document.querySelector('#pwdConfirm');

togglePassword.addEventListener('click', function (e) {
  // toggle the type attribute
  const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
  password.setAttribute('type', type);
  // toggle the eye slash icon
  this.classList.toggle('fa-eye-slash');
});

const togglePassword1 = document.querySelector('#togglePassword1');
const password1 = document.querySelector('#cpwdConfirm');

togglePassword1.addEventListener('click', function (e) {
  // toggle the type attribute
  const type = password1.getAttribute('type') === 'password' ? 'text' : 'password';
  password1.setAttribute('type', type);
  // toggle the eye slash icon
  this.classList.toggle('fa-eye-slash');
});


function validateInput1(id) {
    var inputElement = document.getElementById(id);
    var inputValue = inputElement.value.trim();

    // Replace characters that are not alphanumeric, dash, special characters, or forward slash
     var sanitizedValue = inputValue.replace(/[^a-zA-Z0-9\/.'"]/g, '');

    // Remove spaces between characters

    if (sanitizedValue !== inputValue) {
       // bootbox.alert("Please enter valid characters in the field (i.e., alphabets, numbers, dash, special characters).");
        inputElement.value = sanitizedValue.toUpperCase();
        return;
    }

    inputElement.value = sanitizedValue.toUpperCase();
}

</script>

<!-- Content Wrapper. Contains page content -->
  
  <!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!--  Login Provision-->
						<button type="button" name="create btn"
							 class="btn btn-primary" onclick="ControlDiv('Create','displayUser','createUser','submitUser','M_User_Login')">Create Login Provision</button>
					
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="#">Master</a></li>
						<li class="breadcrumb-item">User</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>


    <!-- Main content -->
    
	<div class="card">

		<div id="displayUser">
			<div class="card-body">
				<table id="userForDisplay"
					class="table table-bordered table-hover userForDisplay">
					<thead>
     



		
			
					
						<tr class="new">
							<td><strong>Employee Name</strong></td>
							<td><strong>Login Name</strong></td>
							<td><strong>User Type</strong></td>
							<td><strong>Status</strong></td>
							<td><strong>Modify/Delete</strong></td>
						</tr>
					</table>
				</div>
				
				</div>

</div>

	<section class="content">
		
<div id="createUser" class="card" style="display: none">
<div class="card-header new">
					<h3 class="card-title1">User Details</h3>
				</div>
<div class="card-body">
	<form action="" name="submitUser" id="submitUser">
		<table class="table table-bordered ">
				<td ><b>Employee Initials<font color="red">*</font></b></td>
				<td >
					<select id="empForUser" class="form-control" name="id_emp_user" data-valid="mand" style="width:140" onBlur="DisplayLogin('M_User_Login','displayUser','createUser','nm_emp');">
						<option value="">Select</option>
				</select>
				<div id="hideshowlabel0" style="display:none;"> <label for="empForUser" style="color: red;font-weight: normal;">Please select the Employee .</label>
                                   </div>		
				</td>
				<td ><b>Email<font color="red">*</font></b></td>
				<td ><input  id="userEmail" type="text"  class="form-control"  name ="id_email"  data-valid="email" disabled >
				<div id="hideshowlabel1" style="display:none;"> <label for="userEmail" style="color: red;font-weight: normal;">Please provide valid email address .</label>
                                   </div>
                </td>
				
			  </tr>
			  
			  <tr>
				<td><strong>Login Name<font color="red">*</font></strong></td>
				<td><input id="empForUser1" class="form-control" type="text" name="nm_login" style="text-transform:uppercase" onkeyup="validateInput1('empForUser1')" readonly="readonly" value="">
				<div id="hideshowlabel2" style="display:none;"> <label for="empForUser1" style="color: red;font-weight: normal;" >Please provide the Login name.</label>
                                   </div>
                 <div id="minmax2" style="display:none;"> <label for="empForUser1" style="color: red;font-weight: normal;">Login name must be between 2 and 15 characters.</label>
                </div>
                  </td>
                  <td ><b>Password<font color="red">*</font></b></td>
				  <td>
				   <div class="password-container">
				  <input id="pwdConfirm" class="form-control "  type=password name ="pwd" data-valid="mand" >
				  <i class="far fa-eye" id="togglePassword"  style="margin-left: -1px;margin-top: 8px; cursor: pointer"></i>
                  </div>
                  <div id="hideshowlabel3" style="display:none;"> <label for="pwdConfirm" style="color: red;font-weight: normal;">Password is required .</label>
                                   </div>
				  </td>
			  </tr>
			  <tr>
				  <td ><b>Confirm Password<font color="red">*</font></b></td>
				  <td >
				   <div class="password-container">
				  <input id="cpwdConfirm" class="form-control" type=password name ="pwd" data-valid="mand" onBlur="return ConfirmPasswordCheck()">
				 <i class="far fa-eye" id="togglePassword1"  style="margin-left: -1px;margin-top: 8px; cursor: pointer"></i>
				  <div id="hideshowlabel4" style="display:none;"> <label for="cpwdConfirm" style="color: red;font-weight: normal;">Confirm password is required .</label>
                                   </div>
				  </td>
			  
			  <td><b>User Type<font color="red">*</font></b></td>
				<td>
					<select class="form-control" name="id_usertype" id="usertypeForEmpUser" data-valid="mand" onchange="getUserType();">
						<option value="">Select</option>
					</select>
				<div id="hideshowlabel5" style="display:none;"> <label for="usertypeForEmpUser" style="color: red;font-weight: normal;">Please select the User Type .</label>
                                   </div>	
				</td>
				<!--  <td ><input  style="width: 200px;display:none;" class="form-control" type=text name ="id_usertype" id="usertypeDataForUser"  ></td> -->
			<!-- <td><b>User Type <font color="red">*</font> :</b></td>
				<td>
					<select name="type_user"  class="form-control" data-valid="mand">
						<option value="">Select</option>
						<option value="SUPER">SUPER</option>
						<option value="PROCHEAD">PROCUREMENT ADMIN</option>
						<option value="ADMIN">ADMIN</option>
						<option value="ITENGINEER"> IT ENGINEER</option>
						<option value="PROCLEAD">PROCUREMENT LEAD</option>
                        <option value="FINANCE">FINANCE MANAGER</option>
						<option value="FINANCECONTROLER">FINANCE CONTROLLER</option>
						<option value="DEPT">Department</option>
					<option value="SHead">Store Head</option>
					
						<option value="IT">IT</option>
						<option value="NIT">NON IT</option> 
					</select>
				</td> -->
			 </tr>
			  <!-- <tr class="hideShow" style="display:none;">
			   <td ><b>Manager<font color="red">*</font> :</b></td>
				<td >
					<select name="id_mngr" id="managerData" style="width:140" class="form-control">
						
					</select>
				</td>
			 </tr> -->
			  
			  <tr>
				<td ><b>Status<font color="red">*</font></b></td>
				<td>
				<input id="Enabled" name="status_user" type="radio" value="1" onchange="hideShowDisableDate(1)">Enabled<br>
				<input id="Disabled" name="status_user" type="radio" value="0" onchange="hideShowDisableDate(0)">Disabled
				<input type="hidden" name="action" value="Save">
				<input type="hidden" name="id" value="0">
				</td>
				<td id="disableDate" class="disableDate disableHide" ><b>Disable Date<font color="red">*</font></b></td>
				<td class="disableDate disableHide" >
				<input id="dt_disable" type="text" name="dt_disable" value="" class="form-control disabledatepicker" style=background-color:white; readonly data-valid="mand">
				</td>
				<td colspan="2" class="enableDate"></td>
			  </tr>
			 <input type="hidden" name="type_user" value="Save">
			  <!-- <tr>
			  <td ><b>Division Name<font color="red">*</font></b></td>
				<td >
					<select id="divDataForUser" name="id_div" class="form-control" data-valid="mand"  style="width:140" onChange="DisplaySubDropDownData(this,'locDataForInvoice','M_Loc')">
						<option value="">Select</option>
						
					</select>
				</td>
				<td ><b>Region<font color="red">*</font></b></td>
				<td >
					<select id="locDataForInvoice" name="id_loc" class="form-control" data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'floorDataForUserPermission','M_Country')">
						<option value="">Select</option>
						
					</select>
				</td>
				
				
				
				<td ><b>State<font color="red">*</font></b></td>
				<td >
					<select id="slocDataForInvoice" name="id_subl" class="form-control" data-valid="mand"  style="width:140" onChange="SubDropDownDataDisplay(this,'buildingDataForInvoice','M_Building')">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr> -->
			
			<!-- <tr>
			
			<td ><b>Store<font color="red"></font></b></td>
				<td colspan="3">
					<select id="floorDataForUserPermission" name="id_flr1" multiple style="width: 400px;height: 100px;" class="form-control" data-valid="mand"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td>
			</tr> -->
			
			<!-- <tr>
			<td ><b>City<font color="red">*</font></b></td>
				<td >
					<select id="buildingDataForInvoice" name="id_building" class="form-control" data-valid="mand"  style="width:140" onChange="DisplaySubDropDownData(this,'floorDataForInvoice','M_Floor')">
						<option value="">Select</option>
						
					</select>
				</td>
				<td ><b>Store<font color="red">*</font></b></td>
				<td >
					<select id="floorDataForInvoice" name="id_flr" class="form-control" data-valid="mand"  style="width:140">
						<option value="">Select</option>
						
					</select>
				</td>
				</tr> -->
			 <tr>
			  
			  <tr>
				<td colspan="4"><center>
				 <button name="save" type="button" style="margin-left:220px;"   class="btn btn-primary" onclick="ValidateCat('Save');">Save</button>
					<!-- <button name="save" type="button"   class="btn btn-primary loginBtn" onclick="ControlDiv('Save','displayUser','createUser','submitUser','M_User_Login','Login name already exist.','nm_login')">Save</button> -->
					<button name="update" type="button"  class="btn btn-primary loginBtn" onclick="ValidateCat('Update')">Update</button>
					<button name="cancel" type="button"  class="btn btn-primary loginBtn" onclick="ControlDiv('Cancel','displayUser','createUser','submitUser','M_User_Login')">Back</button>
				</center></td>	
			  </tr>
		</table>
	</form>	
</div>
 <!-- /.card-body -->
      </div>
      <!-- /.card -->
    </section>
    <!-- /.content -->


<!-- Page specific script -->

  
  

</body>
</html>
