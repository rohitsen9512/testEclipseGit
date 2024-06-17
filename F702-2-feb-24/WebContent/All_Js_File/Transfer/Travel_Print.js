
function DisplayAssetForTravelPrint()
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
				$.post('T_Travel_Print',{action : 'Display' ,searchWord : searchWord} , function (r){
	
					var list= '<tr class="tableHeader info">'+
					'<td><strong>Asset ID</strong></td>'+
					'<td style="width: 390px;"><strong>Asset Name</strong></td>'+
					'<td><strong>Serial Number</strong></td>'+
					'<td><strong>Asset Description</strong></td>'+
					'<td><strong><a href="#">Preview </a></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.ds_pro == '') ds_pro = '-'; else ds_pro = params.ds_pro;
						if(params.no_mfr == '') no_mfr = '-'; else no_mfr = params.no_mfr;
						if(params.ds_asst == '') ds_asst = '-'; else ds_asst = params.ds_asst;
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+ds_pro+'</center></td>'+
						'<td><center>'+no_mfr+'</center></td>'+
						'<td><center>'+ds_asst+'</center></td>'+
						'<td><strong><a href="Transfer/Travel_Print_Preview.jsp?id_trvl='+params.id_trvl+'&id_wh='+params.id_wh+'" target="_new"> Preview</a></strong></td>'+
						'</tr>';
						}
					
					
						$('.DisplayAssetForTravelPrint').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available..</strong></center></td></tr>';
						$('.DisplayAssetForTravelPrint').html(list);
					}
					
				
			},'json');
			}});

}

