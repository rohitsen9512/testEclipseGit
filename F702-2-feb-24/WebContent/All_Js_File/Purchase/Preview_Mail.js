function DisplayPreviewDetails(DispDiv , HideDiv , no_ind,id_ven)
{
	
	$.post('Preview_Mail',{action : 'DisplayPreviewDetails',no_ind:no_ind,id_ven:id_ven},function (r){
		var pin='',pin1='';
		if(r.venDetails[0].pin == 'undefined' || r.venDetails[0].pin == '')
			pin = '';else pin = ' - '+r.venDetails[0].pin;
		if(r.company[0].pin == 'undefined' || r.company[0].pin == '')
			pin1 = ''; else pin1 = ' - '+r.company[0].pin;
		
		
		var list='<tr>'+
					'<td>'+
						'<table align="center" width="100%">'+
						'<tr style="border-top:none;">'+
							'<td width="20%" style="border-left-style: none;"><img src="InvoiceScanFile/'+r.company[0].file_name+'"><font size="2"><b><br><b style="font-size: larger;">'+r.company[0].nm_com+'</b><br>'+r.company[0].add1+'<br>'+r.company[0].add2+'<br>'+r.company[0].city + pin1+'<br>'+r.company[0].country+'</b></font></td>'+
							'<td width="40%" style="border-left-style: none;"><p><center><font size="3"><b>ENQUIRY</b></font></center></p></td>'+
							'<td width="40%" style="border-left-style: none;"><strong style="margin-right:;"><p align="left"><font size="2"><b><br><b style="font-size: larger;">'+r.venDetails[0].nm_ven+'</b><br>'+r.venDetails[0].add1+'<br>'+r.venDetails[0].add2+'<br>'+r.venDetails[0].city + pin+'<br>'+r.venDetails[0].country+'</b></font></td>'+
						'</tr>'+
						'</table>'+
					'</td>'+
				'</tr>'+
		
		'<tr >'+
			'<td style="border-bottom-style: hidden;border-top-style: hidden;">'+
				'<table align="center" width="100%">'+
				'<tr style="border-bottom-style: hidden;">'+
					'<td style="border-left-style: none;" colspan="2"><strong>Our Ref : '+no_ind+'</strong></td>'+
					//'<td style="border-left-style: none;"><strong>Enquiry No : '+r.year+'/'+r.id_quot+'</strong></td>'+
				'</tr>'+
		
				'<tr style="border-bottom-style: hidden;">'+
				'<td style="border-left-style: none;" colspan="2"><strong>Subject : Request for Quotation</strong></td>'+
					//'<td style="border-left-style: none;"><strong>Enquiry Date : '+r.todaydate+'</strong></td>'+
				'</tr>'+
				
				'<tr>'+
					'<td colspan="4" style="border-left-style: none;"><b>NOTE :</b>Please submit your most competitive offer for the supply of the following items furnishing all the required details in the format as under</td>'+
				'</tr>'+
				'</table>'+
			'</td>'+
		'</tr>'+

		'<tr ><td ><table width="100%" class="table table-bordered" border="1"><tr>'+
			'<td><strong>Asset Name</strong></td>'+
			//'<td><strong>Description</strong></td>'+
			'<td><strong>Quantity</strong></td>'+
		'</tr>';
		if(r.data.length > 0)
			{
					
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];	
				
				list += '<tr>'+
					'<td>'+params.nm_model+'</td>'+
					//'<td>'+params.description+'</td>'+
					'<td style="width:150px;">'+params.req_qty+'</td>'+
					
				'</tr>';
				}
				var arr = r.quot_t_c.split('\n');
				var quot_t_c='';
				for(var k=0;k<arr.length;k++)
					{
					quot_t_c += arr[k]+'<br>';
					}
				
				/*list += '</td><tr>'+
				'<table width="100%" class="table"><tr><td><b>Term & Conditions</b></td></tr><tr><td>'+quot_t_c+'</td></tr></table>';
				*/
			var	temp = '<button type="button" style="margin-left: 400px;" class="btn btn-primary" onClick="printPg(\'#createPreview\')">Print </button>&nbsp;&nbsp;';
			/* '	<button type="button" class="btn btn-primary" onclick="ControlPreviewQuotation(\'Cancel\',\'displayPreviewQuotation\',\'createPreview\',\'\')">Email </button>';
				*/
			
				$('.previewMailForQuotaion').html(list);
			$('#buttonForPrintMail').html(temp);
				
			}
		else
			{
			
			list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.previewMailForQuotaion').html(list);
			}
		
		
		
	},'json');
	
	$('#'+DispDiv).show();
	$('#'+HideDiv).hide();

}

function DisplayPreviewQuotation(servletName,displayContent,createDetails)
{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
	$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
		
		var list= '<tr>'+
		'<td colspan="5" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 400px;">Preview Quotation details</p></td>'+
		'</tr>'+
			'<tr class="tableHeader info">'+
		'<td><center><strong>Vendor Name</strong></center></td>'+
		'<td><center><strong>Indent No</strong></center></td>'+
		'<td><center><strong>Requested Date</strong></center></td>'+
		'<td><center><strong>Requested By</strong></center></td>'+
		'<td style="width:105px;"><center><a href="#"><strong>Preview</strong></a></center></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.no_ind+'</center></td>'+
									'<td><center>'+params.dt_req_quot+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><center><a class="alert" href="#" onclick="DisplayPreviewDetails(\'createPreview\',\'displayPreviewQuotation\',\''+params.no_ind+'\','+params.id_ven+')">Preview</a></center></td>'+
							  '</tr>';
				}
			
				$('.previewQuotatioForDisplay').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.previewQuotatioForDisplay').html(list);
			}
		
	},'json');
}