function SendMail(){
           var t=true;        	
         $(".textfield").each(function(){
	
	if($(".textfield").val() ==''){
		alert('please fill the correct value.');
	t=false;	
	}
	
});
if(t==true){
	
	$('.page-loader-wrapper').show();
$('.btn').attr('disabled');
        	var x = $('#mailForm').serialize();
        	
        		$.post('FreeTrail',x,function (r){
	/*alert(r.data);*/
				if(r.data == 1)
					{
				   window.location = "index.jsp";
					alert("Mail Has been sent to your mail id.");
					
					}
					else if(r.data == 2)
				{
					 
				   alert("Company Already Registered");
				   window.location = "login.jsp";
				}
				else
				{
					$('.page-loader-wrapper').hide();
					alert("Something went wrong Please try again.");
				}
				$('.btn').removeAttr('disabled');
			},'json');
        }

}