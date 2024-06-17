
function ControlInterTransferAsset(action , DisplayDiv , HideDiv , FormName , id , type_tran)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Cancel')
	{
		/*DisplayAssetForInterTransfer();
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();*/
		//$( ".interunittransfer_event" ).trigger( "click" );
		window.location = $('.interunittransfer_event').attr('href');
	}
	
	else if(action =='Preview')
		{
			$('#'+HideDiv).hide();
			$('#'+DisplayDiv).show();
			//InterTransferFun(action , DisplayDiv , HideDiv , FormName , id , type_tran);
			DisplayRequestedAssetsForFinalInterTransfer(id);
		}
	
	else if(action == "Update")
	{
		$('input[name="dt_tran"]').removeClass('error');
			var dt_tran =$('input[name="dt_tran"]').val();
			
			if(dt_tran == '')
				{
					bootbox.alert('Mandatory Field');
					$('input[name="dt_tran"]').focus();
					$('input[name="dt_tran"]').addClass('error');
					exit(0);
				}
			$('.txx').attr('disabled','disabled');

			
			$.post('T_Inter_Transfer',{action : 'Update', req_no : id , dt_tran : dt_tran },function (r){
				
				if(r.data > 0)
					{
					bootbox.alert("Updated successfully.");
						/*DisplayAssetForInterTransfer();
						
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();*/
					//$( ".interunittransfer_event" ).trigger( "click" );
					window.location = $('.interunittransfer_event').attr('href');
					}
				else
				{
					bootbox.alert("Try again.");
				}
				$('.txx').removeAttr('disabled');
			},'json');
			
	}
			}});
}

function DisplayRequestedAssetsForFinalInterTransfer(req_no)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	
	$.post('T_Transfer',{action : 'Preview' ,req_no : req_no} , function (r){
		
		var list= '<tr><td colspan="4" class="tableHeader"><center><h3>Asset Details For Relocation</h3></center></td></tr>'+
			'<tr class="tableHeader info">'+
		'<td><strong><center>Asset ID</center></strong></td>'+
		'<td style="width: 390px;"><strong><center>Asset Name</center></strong></td>'+
		'<td><strong><center>Serial Number</center></strong></td>'+
		'<td><strong><center>Asset Description</center></strong></td>'+
	'</tr>';
			
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				list = list + '<tr class="success">'+
				'<td><center>'+params.id_wh_dyn+'</center></td>'+
				'<td><center>'+params.ds_pro+'</center></td>'+
				'<td><center>'+params.serial_no+'</center></td>'+
				'<td><center>'+params.ds_asst+'</center></td>'+
				'</tr>';
				}
			
				list = list +'<tr>'+
				'<td colspan="4">'+
				'<p><input type="hidden" name="req_no" value="'+req_no+'">'+
				'<b>Transfer Date <font color="red"> * </font>:</b>'+
				'<input type="text" name="dt_tran" value="" class="iutDatepicker" id="dt_tranID">'+
					'&nbsp;&nbsp;&nbsp;&nbsp;<button name="save" type="button" style="margin-left:140px;"   class="btn btn-primary txx" onclick="ControlInterTransferAsset(\'Update\',\'DisplayAssetForTransfer\',\'TransferDetails\',\'Transfer\','+req_no+')">Transfer</button>'+
					'&nbsp;&nbsp;&nbsp;&nbsp;<button name="cancel" type="button"  class="btn btn-primary txx" onclick="ControlInterTransferAsset(\'Cancel\',\'DisplayAssetForTransfer\',\'TransferDetails\',\'Transfer\','+req_no+')">Cancel</button>'+
				'</p>'+
			'</td>'+
		'</tr>';
				$('#displayAssetForRequestedAssetsFinalInterTransfer').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="4"><strong><center>No Transfer Request Available</center></strong></td></tr>';
				$('#displayAssetForRequestedAssetsFinalInterTransfer').html(list);
			}
		$( ".iutDatepicker" ).datepicker({
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "dd/mm/yy",
		      autoSize: true,
		      altFormat: "dd/mm/yy",
		      defaultDate: new Date(),
		      onSelect: function(selected,evnt) {
			    	
		    	  var req_no = $('input[name="req_no"]').val();
		    	var dt_tran = $('input[name="dt_tran"]').val();
		    	$('input[name="dt_tran"]').removeClass('error');
		    	
		    $.post('T_Inter_Transfer',{action : 'CheckDate' , dt_tran : dt_tran ,req_no : req_no },function (r){
				    	if(r.data == 2)
		    			{
		    			alert('Transfer Date  should be greater or equal to grn date '+r.dt_iut);
		    				$('#dt_tranID').focus();
		    				$('#dt_tranID').val('');
		    				$('#dt_tranID').addClass('error');
		    				exit(0);
		    			}
		    		else if(r.data == 3)
					{
					alert('Transfer Date  should be greater or equal to de allocated date '+r.dt_iut);
						$('#dt_tranID').focus();
						$('#dt_tranID').val('');
						$('#dt_tranID').addClass('error');
						exit(0);
					}
		    },'json');
		    }
		});
		
	},'json');

			}});
}

function InterTransferFun(action , DisplayDiv , HideDiv , FormName , id , tranType)
{
	SessionCheck(function (ses){		
		if(ses)
			{		
		$.post('T_Inter_Transfer',{action : 'Edit' , id : id , tranType : tranType},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
							for (var key in r.data[i])
					        {
								
									$('input[name='+key+']').val(r.data[i][key]);
								
					        }
							 
						}
						$('input[name="id"]').val(id);
						$('input[name="id_wh"]').val(r.data[0].id_wh);
						
						if(tranType == 'Temporary')
						{
						
						$('.hideLocAdnSubLocForIUTApprov').hide();
						$('.hideTrnType').hide();
						$('.RowColHideIUTApprov').hide();
						$('.hideIfPermanet').show();
						$('.showTrnType').show();
						$('.HideShowVenForIUTApprov').show();
						
						}
					else
						{
						$('.showTrnType').hide();
						$('.RowColHideIUTApprov').hide();
						$('.hideIfPermanet').hide();
						$('.hideLocAdnSubLocForIUTApprov').show();
						
						}
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
		},'json');
			}});
}


function DisplayAssetForInterTransfer()
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
	$.post('T_Inter_Transfer',{action : 'Display' ,searchWord : searchWord} , function (r){
	
		
			var list= '<tr class="new ">'+
			'<td><strong><center>Request Number</center></strong></td>'+
			'<td><strong><center>Request Date</center></strong></td>'+
			'<td><strong><center>Approved By</center></strong></td>'+
			'<td style="width: 150px;"><strong><center>Preview</center></strong></td>'+
		'</tr>';
			
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>Request No-'+params.req_no+'</center></td>'+
									'<td><center>'+params.dt_req+'</center></td>'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><strong><center><a class="alert" href="#" onclick="ControlInterTransferAsset(\'Preview\',\'TransferDetails\',\'DisplayAssetForTransfer\',\'Transfer\','+params.req_no+',\''+params.type_tran+'\')"> Transfer </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.DisplayAssetForTransferring').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="6"><strong><center>No Asset Available for transfer </center></strong></td></tr>';
				$('.DisplayAssetForTransferring').html(list);
			}
		
		
	},'json');
			}});

}



