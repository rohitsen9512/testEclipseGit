function DisplayAppRFI(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			var dt_frm =$('#dt_frm').val();
			var dt_to =$('#dt_to').val();
			var searchWord = $('input[name="search"').val();
				$.post(servletName,{action : 'Display' , no_req_val : no_req_val,dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><center><strong>RFI Number</strong></center></td>'+
					'<td><center><strong>RFI Date</strong></center></td>'+
					
					'<td><center><strong><a href="#">Approve/Reject </a></strong></center></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_rfi+'</center></td>'+
											'<td><center>'+params.dtrfi+'</center></td>'+
											
											'<td style="width:200px;"><strong><center><a class="alert" href="#" onclick="EditPRApp(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_rfi+')"> Approve/Reject </a></center></strong></td>'+
											'</tr>';
						}
					
					
						$('.'+DisplayTableClass).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="6"><strong><center>No record(s) is available.</center></strong></td></tr>';
						$('.'+DisplayTableClass).html(list);
					}
				
			},'json');
		
			}
});
}

function EditPRApp(servletName,displayContent,createDetails,id)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			//DisplayAssetRPPreview('P_Request_PR','displayPR','createPR',id,'assetPRForDisplay');
	DisplayAssetRP('P_Create_RFI','displayPR','createPR',id,'assetPRForDisplay');
	
	
	/*$('.readbaledata').each(function (){
		
		$(this).attr('readonly', 'readonly');
		if($(this).is("select"))
		{
			$(this).attr("disabled", true);
		}
		
	});*/
	
	$('input[name="dt_rfi"]').attr("disabled", true);
	
	$('button[name="update"]').removeClass('hideButton');
	/*$('#'+displayContent).hide();
	$('#'+createDetails).show();
	$('#DisplayAssetRP').show();*/
	$('#'+displayContent).hide();
	$('#'+createDetails).show();
	$('#createAssetPR').show();
	$('#DisplayAssetRP').hide();
	
	var t=0;
	
				
		$.post(servletName,{action : 'Edit',id : id},function (r){
				
				if(r.data)
					{
					
						for(var i = 0; i < r.data.length ; i++)
						{
							
							
							for (var key in r.data[i])
					        {
								
								if($('select[name='+key+']').is("select") && r.data[i][key] != '')
								{
									
									$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									
								}
								else
								{
									$('input[name='+key+']').val(r.data[i][key]);
								}
								if(key == 'remarks')
								{
									$('textarea[name='+key+']').val(r.data[i][key]);
								}
					        }
							 
						}
						$('input[name="action"]').val("Save");
						$('input[name="id"]').val(id);
						$('input[name="id_rfi"]').val(id);
						$('input[name="no_req_val"]').val(r.data[0].no_req);
						$('input[name="dt_rfi"]').val(r.data[0].dtrfi);
						$('input[name="dt_rfi_start"]').val(r.data[0].dtstartrfi);
						$('input[name="dt_rfi_end"]').val(r.data[0].dtendrfi);
						}
				else
					{
						bootbox.alert("Try again.");
					}
				/*if(t == 1)
				{
					
					ShowRowColumn('Yes' , 'hideRowCol');
				}	
				*/
				
					
		},'json');
		
			
			
		}
	
});
}

function DisplayAssetRP(servletName,displayContent,createDetails,id,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
				DisplayQuestions('P_Create_RFI','displayPR','createPR',id,'assetPRForDisplay');
				
	$.post(servletName,{action : 'DisplayAssetForPR' , id : id},function (r){
		var list= '';
		if(r.data)
			{
				 list= '<tr class="tableHeader info">'+
				 '<td ><strong><center>Model<a href=#></a></center></strong></td>'+
					'<td ><strong><center>Group<a href=#></a></center></strong></td>'+
					'<td ><strong><center>Sub Group<a href=#></a></center></strong></td>'+
					
					'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
					
	
								//'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				var j=0;
				for(var i = 0; i < r.data.length ; i++)
				{
				j=i+1;
				params = r.data[i];
				
	
								
								list = list +	'<td><input style="width: 120px !important;" type="text"  value="'+params.nm_model+'" id="dynItemData'+i+'" name="nm_model'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod(event);" readonly></td>'+
										'<td><center><select style="width: 120px !important;" id="assetDivForInvoice'+i+'" name="id_grp'+i+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')" readonly>'+
										'<option value="">Select</option></select>'+
										'</center></td>'+
										'<td><center><select style="width: 120px !important;" id="subGroupDataForInvoice'+i+'" name="id_sgrp'+i+'" patelUnPrc="'+i+'"  class="common-validation dropsgrp" data-valid=""  style="width:80" readonly>'+
										'<option value="">Select</option></select>'+
										'</center></td>'+
										
										
									 	'<td><center><input type="text"  name="qty'+i+'" value="'+params.qty+'" style="width:60px;;text-align: right;"   class="common-validation" patelUnPrc="'+i+'"   class="patelUnPrc data-valid="num" readonly></center></td>'+

									
											//'<td ><a class="alert" href="#" onclick="DeleteAssetPR(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_rfi_asst+')"> Delete</a></td>'+
						
						'<input type="hidden" name="id_rfi_asst'+i+'" value="'+params.id_rfi_asst+'">'+	
						'<input type="hidden" name="count" value="'+i+'">'+
						
					'</tr>';
					$('select[name=asset_consumable'+i+'] option[value=' + params.asset_consumable + ']').attr('selected',true);
				}
				
				$('input[name="id"]').val(id);
				$('input[name="itemCount"]').val(j);
				$('#accessoryDetails').html(list);
			
				DisplayDropDownDataForGroup('M_Asset_Div','groupDrop','CapG',function (status){
					if(status)
					{
					SubDropDownDataDisplay('0','dropsgrp','M_Subasset_Div',function (status){
						
						
						
						for(var i=0;i<r.data.length;i++)
						{
							params=r.data[i];
							
							for (var key in r.data[i])
					        {
								
									
									if(key=='id_grp')
									{
										$('select[name=id_grp'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
									if(key=='id_sgrp')
									{
										$('select[name=id_sgrp'+i+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
									
					        }
							
						}	
					});
					
					
					}});
				
				
			}
		else
			{
				alert("Try again.");
			}
		
		
		$("#dynItemData0").easyAutocomplete(options);
		$("#dynItemData1").easyAutocomplete(options);
		$("#dynItemData2").easyAutocomplete(options);
		$("#dynItemData3").easyAutocomplete(options);
		$("#dynItemData4").easyAutocomplete(options);
		$("#dynItemData5").easyAutocomplete(options);
		$("#dynItemData6").easyAutocomplete(options);
		$("#dynItemData7").easyAutocomplete(options);
		$("#dynItemData8").easyAutocomplete(options);
		$("#dynItemData9").easyAutocomplete(options);
		$("#dynItemData10").easyAutocomplete(options);
		$("#dynItemData11").easyAutocomplete(options);
		$("#dynItemData12").easyAutocomplete(options);
		$("#dynItemData13").easyAutocomplete(options);
		$("#dynItemData14").easyAutocomplete(options);
		$("#dynItemData15").easyAutocomplete(options);
		$("#dynItemData16").easyAutocomplete(options);
		$("#dynItemData17").easyAutocomplete(options);
		$("#dynItemData18").easyAutocomplete(options);
		$("#dynItemData19").easyAutocomplete(options);
		$("#dynItemData20").easyAutocomplete(options);
		$("#dynItemData21").easyAutocomplete(options);
		$("#dynItemData22").easyAutocomplete(options);
		$("#dynItemData23").easyAutocomplete(options);
		$("#dynItemData24").easyAutocomplete(options);
		$("#dynItemData25").easyAutocomplete(options);
		$("#dynItemData26").easyAutocomplete(options);
		$("#dynItemData27").easyAutocomplete(options);
		$("#dynItemData28").easyAutocomplete(options);
		$("#dynItemData29").easyAutocomplete(options);
		$("#dynItemData30").easyAutocomplete(options);
		
		
	},'json');
	

			}

});

}

function DisplayQuestions(servletName,displayContent,createDetails,id,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	$.post(servletName,{action : 'DisplayQuesForPR' , id : id},function (r){
		var list= '';
		if(r.data)
			{
				 list= '<tr class="tableHeader info">'+
				 '<td style="width: 83%;""><strong><center>Questions<a href=#></a></center></strong></td>'+
					
	
					//'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				var j=0;
				for(var i = 0; i < r.data.length ; i++)
				{
				j=i+1;
				params = r.data[i];
				
	
								
								list = list +	
									 	'<td><center><input type="text"  name="ques'+i+'" value="'+params.ques+'" style="width:100%;text-align: left;"   class="common-validation" patelUnPrc="'+i+'"   class="patelUnPrc" readonly></center></td>'+

									
						//'<td ><a class="alert" href="#" onclick="DeleteQuestion(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_rfi_ques+')"> Delete</a></td>'+
						
						'<input type="hidden" name="id_rfi_question'+i+'" value="'+params.id_rfi_question+'">'+	
						'<input type="hidden" name="count" value="'+i+'">'+
						
					'</tr>';
					
				}
				
				$('input[name="id"]').val(id);
				$('input[name="itemCount"]').val(j);
				$('#questiionDetails').html(list);
			
				
			}
		else
			{
				alert("Try again.");
			}
		
		
		
	},'json');
	

			}

});

}

function ControlAppRFI(action,displayContent,createDetails,formName,servletName,app) {
SessionCheck(function (ses){		
		if(ses)
			{
	
		$(".common-validation").each(function(){
		
			$(".common-validation").removeClass('error');
		});
		$('input[name="dt_req"]').attr("disabled", false);
	
	
	
	if(action=='Cancel')
		{
			$( ".approveRfi" ).trigger( "click" );
		}
	
	if(action == 'Approve'){
		var id = $('input[name="id"]').val();
		$.post('P_Create_RFI',{action : 'Approve',id:id},function (r){
			if(r.data ==1)
			{
				
				bootbox.alert("RFI has been succesflly Approved....");
				$( ".approveRfi" ).trigger( "click" );
			}
		else
			{
				bootbox.alert("Try again.");
			}
			},'json');				
}
	
	if(action == 'Reject'){
		var id = $('input[name="id"]').val();
		$.post('P_Create_RFI',{action : 'Reject',id:id},function (r){
			if(r.data ==1)
			{
				
				bootbox.alert("RFI has been Rejected....");
				$( ".approveRfi" ).trigger( "click" );
			}
		else
			{
				bootbox.alert("Try again.");
			}
			},'json');				
}
	
			}
});
}