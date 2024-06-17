
function ControleDprnReport(formName)
{
	$("#"+formName+" .common-validation").each(function(){
		$(this).removeClass('error');
		
	});
	
	var t = true;
	$("#"+formName+" .common-validation").each(function(){
		if ($(this).val() == '')
		{
			alert('Mandatory field');
			$(this).focus();
			$(this).addClass('error');
			t = false;
			return t;
		}
		
		
	});
	
		return t;
}