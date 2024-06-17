function DisplayAssetForPrintgatePass(servletName , ClassNameForDisplayData)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var dt_frm =$('input[name="dt_frm"]').val();
			var dt_to =$('input[name="dt_to"]').val();
			var type_tran =$('select[name="type_tran"]').val();
			
				$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,type_tran:type_tran},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Asset Id</strong></td>'+
					'<td><strong>Asset Name</strong></td>'+
					'<td><strong>Created Date</strong></td>'+
					'<td><strong>Validity</strong></td>'+
					'<td><strong>Transfer Type</strong></td>'+
					
					'<td><strong><a href="#">Preview</a></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.id_wh_dyn+'</center></td>'+
											'<td><center>'+params.ds_pro+'</center></td>'+
											'<td><center>'+params.dt_create+'</center></td>'+
											'<td><center>'+params.dt_gp_vldty+'</center></td>'+
											'<td><center>'+params.type_tran+'</center></td>'+
											'<td><strong><a href="Tagging/Print_Gate_Pass_Preview.jsp?id_iut='+params.id_iut+'&id_wh='+params.id_wh+'&id_gp='+params.id_gp+'&type_tran='+params.type_tran+'" target="_new"> Preview </a></strong></td>'+
											'</tr>';
						}
					
						$('.'+ClassNameForDisplayData).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
						$('.'+ClassNameForDisplayData).html(list);
					}
				
			},'json');
			}});
}