function ControlIndentForDelete(action ,DispDiv , HideDiv , id_ind)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action == 'Preview')
		{
			DisplayIndentForDeleteRequest('Pending_Indent_Report',DispDiv,HideDiv,id_ind);
			$('#'+DispDiv).show();
			$('#'+HideDiv).hide();
		}
	if(action == 'Cancel')
	{
		$('#'+DispDiv).show();
		$('#'+HideDiv).hide();
	}
	if(action == 'Delete')
	{
		var t=false;
		var id_ind_asst="0",id_req='0';
		$('.prRequestForIndentSelectAll').each(function(){
			if(this.checked)
			{
				t=true;
				if(id_ind_asst == '0')
					id_ind_asst = $(this).val();
					else
						id_ind_asst +='Patel'+ $(this).val();
				if(id_req == '0')
					id_req = $(this).attr("id_req");
					else
						id_req +=','+ $(this).attr("id_req");
			}
			
		});
		if(!t)
			{
				bootbox.alert("Please select at least one request then procced.");
			}
		else
			{
			id_ind = $('input[name="id_ind"]').val();
				DeletePrRequestFromIndent('Pending_Indent_Report',DispDiv,HideDiv,id_ind_asst,id_req,id_ind);
				$('#'+DispDiv).show();
				$('#'+HideDiv).hide();
			}
		
	}
	

			}});
}


function DisplayIndentForDeleteRequest(servletName,DispDiv,HideDiv,id_ind)
{
	
	$.post(servletName,{action : 'Display',id:id_ind},function (r){
		
		
		var list= '<tr>'+
				'<td colspan="9" class="tableHeader">'+
				'<p class="tableHeaderContent" style="margin-left: 35%;">Pending Indent Report</p></td>'+
				'</tr>'+
			'<tr class="tableHeader info">'+
		'<td><center><strong>Request No</strong></center></td>'+
		'<<td><center><strong>Requestor</strong></center></td>'+
		'<td><center><strong>Group</strong></center></td>'+
		'<td><center><strong>Subgroup</strong></center></td>'+
		'<td><center><strong>Manufacturer</strong></center></td>'+
		'<td><center><strong>Model</strong></center></td>'+
		'<td><center><strong>Qty</strong></center></td>'+
		'<td><center><strong>Unit Price</strong></center></td>'+
		'<td><center><strong>Total Price</strong></center></td>'+
		'</tr>';
		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list  += '<tr class="success">'+
									'<td><center>'+params.no_req+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><center>'+params.nm_assetdiv+'</center></td>'+
									'<td><center>'+params.nm_s_assetdiv+'</center></td>'+
									'<td><center>'+params.mfr+'</center></td>'+
									'<td><center>'+params.model+'</center></td>'+
									'<td><center>'+params.qty+'</center></td>'+
									'<td><center>'+params.un_prc+'</center></td>'+
									'<td><center>'+params.tot_prc+'</center></td>'+
							  '</tr>';
				}
				
				
				$('.DisplayIndentForDelete').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="9"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.DisplayIndentForDelete').html(list);
			}
		
	},'json');
}

function DisplayIndentForPreview(servletName,displayContent,createDetails)
{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to},function (r){
		
		
		var list= '<tr class="tableHeader info">'+
		'<td><strong>Request No</strong></td>'+
		'<td><strong>Request Date</strong></td>'+
		'<td><strong>Indent No</strong></td>'+
		'<td><strong>Indent Date</strong></td>'+
		'<td><strong>Requester</strong></td>'+
		'<td><strong>Requested For</strong></td>'+
		'<td><strong>PO Status</strong></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.no_req+'</center></td>'+
									'<td><center>'+params.dt_req+'</center></td>'+
									'<td><center>'+params.no_ind+'</center></td>'+
									'<td><center>'+params.dt_ind+'</center></td>'+
									'<td><center>'+params.requestor+'</center></td>'+
									'<td><center>'+params.requestorFor+'</center></td>'+
									'<td><center>'+params.st_approv+'</center></td>'+
							  '</tr>';
				}
			
				$('.DisplayDeleteIndentForPreview').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.DisplayDeleteIndentForPreview').html(list);
			}
		
	},'json');
}
