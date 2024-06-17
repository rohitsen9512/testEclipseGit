function ControlIndentForDelete(action ,DispDiv , HideDiv , id_ind)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action == 'Preview')
		{
			DisplayIndentForDeleteRequest('Delete_Indent',DispDiv,HideDiv,id_ind);
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
				bootbox.alert("Please select at least one request then proceed.");
			}
		else
			{
			id_ind = $('input[name="id_ind"]').val();
				DeletePrRequestFromIndent('Delete_Indent',DispDiv,HideDiv,id_ind_asst,id_req,id_ind);
				$('#'+DispDiv).show();
				$('#'+HideDiv).hide();
			}
		
	}
	

			}});
}


function DeletePrRequestFromIndent(servletName,DispDiv,HideDiv,id_ind_asst,id_req,id_ind)
{
				bootbox.confirm("Are you sure?", function(result) {
				if(result)
				{
				
					$.post(servletName,{action : 'Delete',id : id_ind_asst,id_req:id_req},function (r){
						
						if(r.data == 1)
						{
							DisplayIndentForDeleteRequest('Delete_Indent',DispDiv,HideDiv,id_ind);
						}
						if(r.data == 2)
						{
							bootbox.confirm("This is the last record of this indent if you delete it then indent also will be delete.", function(result1) {
								if(result1)
								{
									$.post(servletName,{action : 'Delete',id : id_ind_asst ,id_req:id_req, confirm : 'Yes'},function (r){
										
										if(r.data == 1)
											{
												$( ".deleteIndent" ).trigger( "click" );
											}
									},'json');
								}
								
							});
						}
						
					},'json');
				}
	});
}


function DisplayIndentForDeleteRequest(servletName,DispDiv,HideDiv,id_ind)
{
	
	$.post(servletName,{action : 'Display',id:id_ind},function (r){
		
		
		var list= '<tr>'+
				'<td colspan="9" class="tableHeader">'+
				'<p class="tableHeaderContent" style="margin-left: 35%;">Requested Details For Delete</p></td>'+
				'</tr>'+
			'<tr class="tableHeader info">'+
		'<td><center><strong>Request No</strong></center></td>'+
		'<td><center><strong>Requestor</strong></center></td>'+
		 '<td ><strong><center>Model<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Group<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Sub Group<a href=#></a></center></strong></td>'+
		'<td><center><strong>Qty</strong></center></td>'+
		'<td><center><strong>Unit Price</strong></center></td>'+
		'<td><center><strong>Total Price</strong></center></td>'+
		'<td style="width:120px;"><strong><center><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'prRequestForIndentSelectAll\')"> Check All </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'prRequestForIndentSelectAll\')"> Uncheck All</a></center></strong></td>'+
		'<input type="hidden" name="id_ind" value="'+id_ind+'">'+
		'</tr>';
		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list  += '<tr class="success">'+
									'<td><center>'+params.no_req+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><center>'+params.nm_model+'</center></td>'+
									'<td><center>'+params.nm_assetdiv+'</center></td>'+
									'<td><center>'+params.nm_s_assetdiv+'</center></td>'+
									'<td><center>'+params.qty+'</center></td>'+
									'<td><center>'+params.un_prc+'</center></td>'+
									'<td><center>'+params.tot_prc+'</center></td>'+
									'<td><strong><center><input type="checkbox" name="deleteIndent" class="prRequestForIndentSelectAll" id_req='+params.id_req+' value="'+params.id_ind_asst+'"/></center></strong></td>'+
							  '</tr>';
				}
				list +='<tr>'+
							'<td colspan="9">'+
							'<input type="button" class="btn btn-primary" style="margin-left: 350px;"  value="Delete" onClick="return ControlIndentForDelete(\'Delete\',\'PreviewIndentForDelete\',\'displayDeleteIndent\','+params.id_req+')">&nbsp;&nbsp;'+
							'<input type="button" class="btn btn-primary"  value="Cancel" onClick="return ControlIndentForDelete(\'Cancel\',\'displayDeleteIndent\',\'PreviewIndentForDelete\',\'\')"></td>'+	
						'</tr>';
				
				$('.DisplayIndentForDelete').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="9"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.DisplayIndentForDelete').html(list);
			}
		
	},'json');
}

function DisplayIndentForDelete(servletName,displayContent,createDetails)
{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
	$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
		
		
		var list= '<tr class="tableHeader info">'+
		'<td><strong><center>RFQ No</center></strong></td>'+
		'<td><strong><center>RFQ Date</center></strong></td>'+
		'<td><strong><center>Created By</center></strong></td>'+
		'<td style="width:105px;"><center><a href="#"><strong>Preview</strong></a></center></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.no_ind+'</center></td>'+
									'<td><center>'+params.dt_ind+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><center><a class="alert" href="#" onclick="ControlIndentForDelete(\'Preview\',\'PreviewIndentForDelete\'\,\'displayDeleteIndent\','+params.id_ind+')">Preview</a></center></td>'+
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
