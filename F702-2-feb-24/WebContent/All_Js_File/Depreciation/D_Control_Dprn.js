function ControlDivForDprn(formName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var t =true;
	t = ValidationForm(formName);
	$('#assetDivForYearlyDprn').removeClass('error');
	if(t)
		{
		if(formName == 'SubmitYearlyDprn')
			{
				var gg = $('#assetDivForYearlyDprn');
				if(gg.val() == null)
						{
							bootbox.alert("Mandatory Field.");
							$('#assetDivForYearlyDprn').addClass('error');
							$('#assetDivForYearlyDprn').focus();
							t=false;
							exit(0);
						}
			}
		else
			{
				var gg = $('#assetDivForMonthlyDprn');
				if(gg.val() == null)
						{
							bootbox.alert("Mandatory Field.");
							$('#assetDivForMonthlyDprn').addClass('error');
							$('#assetDivForMonthlyDprn').focus();
							t=false;
							exit(0);
						}
			}
		
		}
	if(t)
		{
			var x = $('#'+formName).serialize();
			var ServletName = '';
			var depType = $('#typeIdForYearlyDprn').val();
			if(depType == 'IT')
				ServletName = 'D_Control_Depreciation_It';
			else
				ServletName = 'D_Control_Depreciation';
			
			$.post(ServletName,x,function (r){
				
				if(r.data)
					{
					 	
						bootbox.alert(r.data);
					}
				else
					{
						bootbox.alert("Try again.");
					}
			},'json');
	
		}

			}});

}