function DisplayDepreciation()
{
	SessionCheck(function (ses){		
		if(ses)
			{
	$.post('D_Depreciation_Master',{action : 'Display'},function (r){
	
	var list= '<tr><td colspan="5" class="tableHeader "></td></tr>'+
	'<tr class="tableHeader info new ">'+
				'<td><strong><center>S No.</center></strong></td>'+
				'<<td><strong><center>Asset Groups</center></strong></td>'+
				'<<td><strong><center>InCome Tax Act(WDV)<font color="red">*</font></center></strong></td>'+
				'<td><strong><center>Company Act(SLM)<font color="red">*</font></center></strong></td>'+
				
				//'<td><strong>US GAAP(in Years)</strong></td>'+
				
			'</tr>';

		if(r.data)
		{
			//var aa='',bb='',cc='';
			
			for(var i = 0; i < r.data.length ; i++)
			{
				
			params = r.data[i];
			
//			if(params.iwdv != 'undefined')
//				{aa = '';}else{aa = params.iwdv;}
//			if(params.cslm != 'undefined')
//			{bb = '';}else{bb = params.cslm;}
//			if(params.usgaap != 'undefined')
//			{cc = '';}else{cc = params.usgaap;}
//				
				
			list = list + '<tr class="success">'+
								'<td><center>'+(i+1)+'</center></td>'+
								'<td><center>'+params.nm_assetdiv+'</center><input type="hidden" name="assetgroups" value="'+params.id_assetdiv+'"></td>'+
								'<td><input class="common-validation" data-valid="num" type="text" name="inCometaxact" value="'+params.iwdv+'"></td>'+
								'<td><input class="common-validation" data-valid="num" type="text" name="companyact" value="'+params.cslm+'"></td>'+
								//'<td><input class="common-validation" data-valid="num" type="text" name="usgaap" value="'+params.usgaap+'"></td>'+
								'</tr>';
			}
		
			
			$('.displayDepreciationMasterTable').html(list);
		
		}
	else
		{
			list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.displayDepreciationMasterTable').html(list);
		}
	
		$('.common-validation').each(function (){
			if($(this).val() == 'undefined')
				{
				$(this).val('');
				}
			
		});
		
		
	},'json');
			}});
}

function ControlDivForDepreceation(formName)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{var t =true;
	$('.common-validation').each(function (){
		$(this).removeClass('error');
		if($(this).val() == '')
			{
			alert('Mandatory field');
			$(this).addClass('error');
			$(this).focus();
			t=false;
			exit(0);
			}
		
		var str = $(this).val();
		var str = '1' + str;
	    var str = str.replace(/[\(\)\.\-\/\ ]/g, '');
	    
	        if (parseInt(str) != str) {
	        	t=false;
	            alert('Invalid number');
	            $(this).addClass('error');
				$(this).focus();
	            exit(0);
	        }
		
	});
	
	if(t)
		{
			var x = $('#'+formName).serialize();
			
			$.post('D_Depreciation_Master',x,function (r){
				
				if(r.data > 0)
					{
					 	DisplayDepreciation();
						alert("Inserted Successfully .");
					}
				else
					{
						alert("Try again.");
					}
			},'json');
	
		}
			}});
}
