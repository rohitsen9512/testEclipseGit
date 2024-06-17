
function ControlReceiveTransferAsset(action , DisplayDiv , HideDiv , FormName , id , type_tran)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Cancel')
	{
		/*DisplayAssetForReceiveTransferedAsset();
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();*/
		//$( ".receiveTransfredAsset" ).trigger( "click" );
		window.location = $('.receiveTransfredAsset_event').attr('href');
	}
	
	else if(action =='Preview')
		{
			$('#'+HideDiv).hide();
			$('#'+DisplayDiv).show();
			DisplayRequestedAssetsForReceiveTransfer(id);
			
			//ReceiveTransferFun(action , DisplayDiv , HideDiv , FormName , id , type_tran);
			
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
			else
				{
				$('.IUTABTN').attr('disabled','disabled');

				
				
					$.post('Transfer_Receive_Asset',{action : 'Update', req_no : id , dt_tran : dt_tran },function (r){
						
						if(r.data > 0)
							{
							bootbox.alert("Updated successfully.");
								/*DisplayAssetForReceiveTransferedAsset();
								
								$('#'+HideDiv).hide();
								$('#'+DisplayDiv).show();*/
							//$( ".receiveTransfredAsset" ).trigger( "click" );
							window.location = $('.receiveTransfredAsset_event').attr('href');
							}
						else
						{
							bootbox.alert("Try again.");
						}
						$('.IUTABTN').removeAttr('disabled');
					},'json');
				}
			
	}
			}});
}


function DisplayRequestedAssetsForReceiveTransfer(req_no)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	
	$.post('T_Inter_Accept_Reject',{action : 'Preview' ,req_no : req_no} , function (r){
		
		var list= '<tr><td colspan="4" class="tableHeader"><h3 style="margin-left: 360px;">Asset Details For Receive</h3></td></tr>'+
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
				'<p style="margin-left: 302px;">'+
				'<p><input type="hidden" name="req_no" value="'+req_no+'">'+
				'<b>Receive Date <font color="red"> * </font>:</b>'+
				'<input type="text" name="dt_tran" value="" class="form-control iutDatepicker" style="width:200px; display:inline;" id="dt_tranID">'+
				'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button name="save" type="button"  class="btn btn-primary IUTABTN" onclick="ControlReceiveTransferAsset(\'Update\',\'DisplayAssetForReceiveAsset\',\'ReceiveDetails\',\'ReceiveAssetTransfer\','+req_no+')">Receive</button>'+
				'&nbsp;&nbsp;&nbsp;&nbsp;<button name="cancel" type="button"  class="btn btn-primary IUTABTN" onclick="ControlReceiveTransferAsset(\'Cancel\',\'DisplayAssetForReceiveAsset\',\'ReceiveDetails\',\'ReceiveAssetTransfer\','+req_no+')">Cancel</button>'+
				'</p>'+
			'</td>'+
		'</tr>';
				$('#displayAssetForReceiveAssets').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="4"><strong><center>No Transfer Request Available</center></strong></td></tr>';
				$('#displayAssetForReceiveAssets').html(list);
			}
		$('.iutDatepicker').datepicker({
			yearRange: '1985:2025',  
			changeMonth: true,
		      changeYear: true,
		      dateFormat: "yy-mm-dd",
		      autoSize: true,
		      onSelect: function(selected,evnt) {
		    	
		    	  var dt_tran = $(this).val();
		    	  var req_no = $('input[name="req_no"]').val();
			    	$('input[name="dt_tran"]').removeClass('error');
			    	
		    	$(this).removeClass('error');
		    $.post('Transfer_Receive_Asset',{action : 'CheckDate' , dt_tran : dt_tran ,req_no:req_no},function (r){
		    		
		    		if(r.data == 1)
		    			{
		    			alert('Receive Date should be greater or equal to last transfer date '+r.dt_iut);
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


function ReceiveTransferFun(action , DisplayDiv , HideDiv , FormName , id , tranType)
{
	SessionCheck(function (ses){		
		if(ses)
			{		
		$.post('Transfer_Receive_Asset',{action : 'Edit' , id : id , tranType : tranType},function (r){
				
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


function DisplayAssetForReceiveTransferedAsset()
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
	
	$.post('Transfer_Receive_Asset',{action : 'Display' ,searchWord : searchWord} , function (r){
	
		
			var list= '<tr class="new">'+
			'<td><strong><center>Request Number</center></strong></td>'+
			'<td><strong><center>Request Date</center></strong></td>'+
			'<td><strong><center>Transfer Date</center></strong></td>'+
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
									'<td><center>'+params.dt_start_tran+'</center></td>'+
									'<td><strong><center><a class="alert" href="#" onclick="ControlReceiveTransferAsset(\'Preview\',\'ReceiveDetails\',\'DisplayAssetForReceiveAsset\',\'ReceiveAssetTransfer\','+params.req_no+',\''+params.type_tran+'\')"> Preview </a></center></strong></td>'+
									'</tr>';
				}
			
				$('.DisplayAssetForTransferring').html(list);
			}
		
		else
			{
				list +='<tr><td colspan="6"><strong><center>No asset available for receive. </center></strong></td></tr>';
				$('.DisplayAssetForTransferring').html(list);
			}
		
		
	},'json');
			}});

}



