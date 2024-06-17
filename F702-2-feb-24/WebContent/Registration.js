function ControlOTP(action,formName,servletName) {
	/*SessionCheck(function (ses){
		
			if(ses)
				{
				*/	
		
		if(action=='GenerateOTP')
			{
				var ct_mailid = $('input[name="ct_mailid').val();
			var chevkmail= ct_mailid.includes("@");
			//alert(chevkmail);
			 if(chevkmail==true)
				{
				DataInsertUpdateForCustomer(formName,servletName);
				}
				else
				{
					alert("Please Fill Correct Mail-id!!!")
				}
				
				
			}
		
		//		}
	//});
	}

function DataInsertUpdateForCustomer(formName,servletName)
{

	t=false;
	/*t = ValidationForm(formName);
		if(t)
		{*/
		//alert(formName);
			var x = $('#'+formName).serialize();
			
			$.post(servletName,x,function (r){
				
				if(r.data == 1)
					{
					alert("OTP has successfully sent...Please Check Your mail");
					//window.location = "Login.jsp";
					$("#loginTable").hide();
					$("#forgetTable").hide();
					$("#registerTable").hide();
					$("#ResetTable").show();
					}
					else
					{
					alert("Partner already registered");
					}
				
				
			},'json');
		
			//}
}

function ControlLogin(action,formName,servletName) {
	/*SessionCheck(function (ses){
		
			if(ses)
				{
				*/	
		
		//alert("hello");
		if(action=='CheckEmail')
			{
			
				DataInsertUpdateForLogin(formName,servletName);
				
			}
		
		//		}
	//});
	}

 
	
function DataInsertUpdateForLogin(formName,servletName)
{
//SessionCheck(function (ses){
		
		/*if(ses)
			{*/
			//alert("hiii");
	t=false;
	/*t = ValidationForm(formName);
		if(t)
		{*/
		
			var x = $('#'+formName).serialize();
			
			$.post(servletName,x,function (r){
				
				if(r.data==1)
					{
					alert("Thank you!!!. Please reset your password in next step...");
					//$(".addClient").trigger("click");
					$("#loginTable").hide();
					$("#forgetTable").hide();
					$("#registerTable").hide();
					$("#ResetTable").hide();
					$("#ResetPassTable").show();
					}
				else
					{
						alert("OTP is incorrect...Please try again");
					}
				$('.req').removeAttr('disabled');
				
			},'json');
		
		//}
			//}
//});
}


function ControlReset(action,formName,servletName) {
	/*SessionCheck(function (ses){
		
			if(ses)
				{
				*/	
		
		//alert("hello");
		
		if(action=='UpdatePassword')
			{
				var mail=$('input[name="ct_mailid"]').val();
				DataInsertUpdateForReset(formName,servletName,mail);
				
			}
		
		//		}
	//});
	}
	
	function DataInsertUpdateForReset(formName,servletName,mail)
{
//SessionCheck(function (ses){
		
		/*if(ses)
			{*/
			//alert("hiii");
	t=false;
	/*t = ValidationForm(formName);
		if(t)
		{*/
		
		$('input[name="mail1"]').val(mail);
			var x = $('#'+formName).serialize();
			//console.log(x);
			
			$.post(servletName,x,function (r){
				
				if(r.data==1)
					{
					alert("Congrats!!! your password has been reset...Please Go back to previous Srceen For Login");
					//window.location = "Master/vendorMaster.jsp";
					}
				else
					{
						alert("Try Again");
					}
				$('.req').removeAttr('disabled');
				
			},'json');
		
		//}
			//}
//});
}




/*function SendMail2(){
	debugger;
	ValidateEmail();
	var name=$("#user").val();
	var mail_id=$("#mail_id").val();
	var database=$("#compname").val();
	if(name=="" && mail_id=="")
	
			 {
			 	$("#hideshowlabel3").attr('style', 'display:block;');
				$("#hideshowlabel4").attr('style', 'display:block;');
			 }
	else if(mail_id=="")
			 {
			 	$("#hideshowlabel4").attr('style', 'display:block;');
			 }
	else if(name=="")
	    	 {
	    	 	$("#hideshowlabel3").attr('style', 'display:block;');
	    	 }
	else if (name!="" && mail_id=="")
			 {
				$("#hideshowlabel4").attr('style', 'display:block;');
				$("#hideshowlabel3").attr('style', 'display:none;');
				
			 }
	else{
		if(name!="" && mail_id!="")	
		$.post('M_User_Login',{action : 'ForgotPassword',name : name,mail_id:mail_id},function (r){
					
			if(r.data == 1)
						{
						console.log(r.data);
						alert("We weren't able to find this email in our records. Please check the email you've entered and try again..");
						$('.span2').addClass('error');
						}
			else
			{
				alert("Check Your Email\nWe have sent a password recovery instruction to your email.");
				$('.span2').addClass('error');
				$('#forgetTable').hide();
				$('#loginTable').show();
			}
				},'json');
}
}
*/

/*function ValidateEmail()
{	
	
	var mail_id=$("#mail_id").val();
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	
	if(mail_id!="")
	{
		if(mail_id.match(mailformat))
		{
			console.log("1");
		}
		else
		{
			document.getElementById("invemail").innerHTML="Invalid email address. e.g. example@email.com";
			document.getElementById("hideshowlabel4").setAttribute("style", "display:block");
			$("#mail_id").val('');
			$("#mail_id").focus();
		}
	}
	else
	{
		console.log("hihi");
	}

}  */
