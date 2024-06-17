

function DisplayPreviewRequest(servletName,displayContent,createDetails)
{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
	$.post(servletName,{action : 'DisplayRequestForPrint',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
		
		var list= '<tr class="tableHeader">'+
		'<td colspan="5"><center><strong><p class="tableHeaderContent" style="">PR For Print</p></strong></center></td>'+'</tr>'+
		'<td><center><strong>Request Number</strong></center></td>'+
		'<td><center><strong>Request Date</strong></center></td>'+
		'<td><center><strong>Requested By </strong></center></td>'+
		'<td><center><strong>Sub-Function </strong></center></td>'+
		'<td><center><strong><a href="#">Preview </a></strong></center></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
				'<td><center>'+params.no_req+'</center></td>'+
				'<td><center>'+params.reqdt+'</center></td>'+
				'<td><center>'+params.nm_emp+'</center></td>'+
				'<td><center>'+params.nm_dept+'</center></td>'+
				'<td style="width:150px;"><strong><center><a class="alert" href="#" onclick="DisplayPreviewDetails(\'createPreview\',\'displayPreviewQuotation\',\''+params.id_req+'\','+params.id_ven+')"> Preview </a></center></strong></td>'+
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

function DisplayPreviewDetails(DispDiv , HideDiv , id_req,id_ven)
{
	$('#'+DispDiv).show();
	$('#'+HideDiv).hide();
	
	$.post('P_Request_PR',{action : 'DisplayRequestForPreview',id:id_req,id_ven:id_ven},function (r){
		
		var list='<tr>'+
						'<td width="20%" style=""><img style="height:100px;"src="InvoiceScanFile/'+r.company[0].file_name+'"><center><font size="2"><b><br><b style="font-size: larger;">Purchase Request Print/E-mail Preview</b></font></center> <b style="float:right;">Date : '+r.todaydate+'</b></td>'+
				'</tr>'+
		'<tr >'+
			'<td style="border-bottom-style: hidden;border-top-style: hidden;">'+
				'<table align="center" width="100%">'+
					'<tr style="border-bottom-style: hidden;">'+
						'<td style="border-left-style: none;" colspan="2"><strong>PR Number : '+r.data[0].no_req+'</strong></td>'+
						'<td style="border-left-style: none;"><strong>PR Date : '+r.data[0].reqdt+'</strong></td>'+
					'</tr>'+
					'<tr style="border-bottom-style: hidden;">'+
						'<td style="border-left-style: none;" colspan="2"><strong> Sub-Function : '+r.data[0].nm_dept+'</strong></td>'+
						'<td style="border-left-style: none;"><strong>Created By :'+r.data[0].nm_emp+' </strong></td>'+
					'</tr>'+
				'</table>'+
			'</td>'+
		'</tr>'+
		'<tr ><td>'+
		'<table width="100%" class="table table-bordered" border="1"><tr>'+
				'<td><strong>Model Name</strong></td>'+
				'<td><strong>Model Code</strong></td>'+
				'<td><strong>Order Qty </strong></td>'+
				'<td><strong>Unit Price </strong></td>'+
				'<td><strong>Total Price</strong></td>'+
			
			'</td>'+
		'</tr>';
		
				
		if(r.data.length > 0)
		{
				var tot=0;
			for(var i = 0; i < r.data.length ; i++)
			{
				params = r.data[i];	
				tot=tot+parseFloat(params.tot_prc);
				list += '<tr>'+
					'<td>'+params.nm_model+'</td>'+
					'<td>'+params.cd_model+'</td>'+
					'<td>'+params.qty+'</td>'+
					'<td>'+params.un_prc+'</td>'+
					'<td>'+params.tot_prc+'</td>'+
				'</tr>';
			}
		list += '<tr>'+
					'<td colspan="3"></td>'+
					
					'<td>Grand Total</td>'+
					'<td>'+tot+'</td>'+
				'</tr>';
			list +='</table></td></tr>'+
					'<tr>'+
						'<td style="border-bottom-style: hidden;border-top-style: hidden;">'+
						'<table align="center" width="100%" style="margin-top: 30px;">'+
							/*'<tr style="border-bottom-style: hidden;">'+
								'<td style="border-left-style: none;line-height: 100px;" ><strong>Proposer : ................................</strong></td>'+
								'<td style="border-left-style: none;line-height: 100px;"><strong>Vetted By : ................................</strong></td>'+
								'<td style="border-left-style: none;line-height: 100px;"><strong>Approved By : ................................</strong></td>'+
							'</tr>'+
							'<tr style="border-bottom-style: hidden;">'+
								'<td style="border-left-style: none;" ><strong>Signature 1 : ................................</strong></td>'+
								'<td style="border-left-style: none;"><strong>Signature 2 : ................................</strong></td>'+
								'<td style="border-left-style: none;"><strong>Signature 3 : ................................</strong></td>'+
							'</tr>'+*/
						'</table>'+
					'</td>'+
				'</tr>';	
			
		var	temp = '<button type="button" style="margin-left: 400px;" class="btn btn-primary" onClick="printPg(\'#createPreview\')">Print </button>&nbsp;&nbsp;';
		
		$('.previewIndentForPrint').html(list);
		$('#buttonForPrintMail').html(temp);
			
		}else
			{
				list +='<tr><td><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.previewIndentForPrint').html(list);
			}
		
	},'json');
	
	

}


