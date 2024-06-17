function DisplaySubmitRFI(servletName,displayContent,createDetails,no_req_val,DisplayTableClass)
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
					'<td><center><strong>Partner</strong></center></td>'+
					
					'<td><center><strong><a href="#">Submit </a></strong></center></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_rfi+'</center></td>'+
											'<td><center>'+params.dtrfi+'</center></td>'+
											'<td><center>'+params.nm_ven+'</center></td>'+
											'<td style="width:200px;"><strong><center><a class="alert" href="#" onclick="EditPR(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_launch_rfi+')"> Submit </a></center></strong></td>'+
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


function EditPR(servletName,displayContent,createDetails,id)
{
SessionCheck(function (ses){
		
		if(ses)
			{
			//DisplayAssetRPPreview('P_Request_PR','displayPR','createPR',id,'assetPRForDisplay');
	DisplayAssetRP('Submit_RFI','displayPR','createPR',id,'assetPRForDisplay');
	
	
	/*$('.readbaledata').each(function (){
		
		$(this).attr('readonly', 'readonly');
		if($(this).is("select"))
		{
			$(this).attr("disabled", true);
		}
		
	});*/
	
	$('input[name="dt_rfi"]').attr("disabled", true);
	$('button[name="save"]').addClass('hideButton');
	$('button[name="save1"]').addClass('hideButton');
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
						$('input[name="id_rfi"]').val(r.data[0].id_rfi);
						$('input[name="no_req_val"]').val(r.data[0].no_req);
						$('input[name="dt_rfi"]').val(r.data[0].dtrfi);
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
				DisplayQuestions('Submit_RFI','displayPR','createPR',id,'assetPRForDisplay');
				
	$.post(servletName,{action : 'DisplayAssetForPR' , id : id},function (r){
		var list= '';
		if(r.data)
			{
				 list= '<tr class="tableHeader info">'+
				 '<td ><strong><center>Model<a href=#></a></center></strong></td>'+
					'<td ><strong><center>Group<a href=#></a></center></strong></td>'+
					'<td ><strong><center>Sub Group<a href=#></a></center></strong></td>'+
					
					'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
					
	
							'</tr>';
				var j=0;
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
	
								
								list = list +	'<td><input style="width: 120px !important;" type="text"  value="'+params.nm_model+'" id="dynItemData'+i+'" name="nm_model'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" readonly></td>'+
										'<td><input style="width: 120px !important;" type="text"  value="'+params.nm_assetdiv+'" id="dynItemData'+i+'" name="nm_assetdiv'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" readonly></td>'+
										
										'<td><input style="width: 120px !important;" type="text"  value="'+params.nm_s_assetdiv+'" id="dynItemData'+i+'" name="nm_s_assetdiv'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" readonly></td>'+
										
										
									 	'<td><center><input type="text"  name="qty'+i+'" value="'+params.qty+'" style="width:60px;;text-align: right;"   class="common-validation" patelUnPrc="'+i+'"   class="patelUnPrc data-valid="num" readonly></center></td>'+

									
						
						'<input type="hidden" name="id_rfi_asset'+i+'" value="'+params.id_rfi_asset+'">'+
						'<input type="hidden" name="id_prod'+i+'" value="'+params.id_model+'">'+
						'<input type="hidden" name="id_grp'+i+'" value="'+params.id_assetdiv+'">'+
						'<input type="hidden" name="id_sgrp'+i+'" value="'+params.id_s_assetdiv+'">'+	
						'<input type="hidden" name="count" value="'+i+'">'+
						
					'</tr>';
					$('select[name=asset_consumable'+i+'] option[value=' + params.asset_consumable + ']').attr('selected',true);
				}
				
				$('input[name="id"]').val(id);
				$('input[name="itemCount"]').val(i);
				$('#accessoryDetails').html(list);
			
				
			}
		else
			{
				alert("Try again.");
			}
		
		
		
		
		
	},'json');
	

			}

});

}
function DisplayQuestions(servletName,displayContent,createDetails,id,DisplayTableClass)
{
SessionCheck(function (ses){
		
		if(ses)
			{
				createAnswer("New",function (status){
			if(status)
			{
			}});
	$.post(servletName,{action : 'DisplayQuesForPR' , id : id},function (r){
		var list= '';
		if(r.data)
			{
				 list= '<tr class="tableHeader info">'+
				 '<td style="width: 83%;""><strong><center>Questions<a href=#></a></center></strong></td>'+
					
	
				'</tr>';
				var j=0;
				for(var i = 0; i < r.data.length ; i++)
				{
				j=i+1;
				params = r.data[i];
				
	
								
								list = list +	
									 	'<td><center><input type="text"  name="ques'+i+'" value="'+params.ques+'" style="width:95%;text-align: left;"   class="common-validation" patelUnPrc="'+i+'"   class="patelUnPrc" readonly></center></td>'+

						'<input type="hidden" name="id_rfi_question'+i+'" value="'+params.id_rfi_question+'">'+	
						
						
					'</tr>';
					
				}
				
				$('input[name="id"]').val(id);
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

function createAnswer(action,callback){
	
	if(action == 'New')
	{
		var list ='<tr>'+
		'<td  style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 500px;">Answer Details</p></td></tr>'+ 
	'<tr class="tableHeader info">'+
		//'<td style="width:400px;"><strong><center>Material Type<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Reply Of Questions<a href=#></a></center></strong></td>'+
		'</tr>';
		
		for(var i = 0; i < 3 ; i++)
		{
			list+='<tr>'+
			
		 	'<td><center><input type="text"  name="ans'+i+'" style="width:100%;text-align: left;"   class="common-validation" patelUnPrc="'+i+'" ></center></td>'+

			
			
			'<input type="hidden" name="count1" value="'+i+'">'+		
		'</tr>';
			
		}
		
		$('input[name="itemCount1"]').val('3');
		//$('input[name="grand_tot1"]').val('0.00');
		
		$('#answerDetails').html(list);
		
	
	}else{
		var i=$('input[name="itemCount1"]').val();
			
		var list='<tr>'+
		
		
	 	'<td><center><input type="text"  name="ans'+i+'" style="width:100%;;text-align: left;"  class="common-validation" patelUnPrc="'+i+'"  ></center></td>'+

	 	'<input type="hidden" name="count1" value="'+i+'">'+		
			'</tr>';
		$('input[name="itemCount1"]').val(parseInt(i) +1);
	$('#answerDetails').append(list);
	
	}
	
		
	callback(true);	
	
}

function dispalyLineItemDynamically(action,callback){
	
	
		var i=$('input[name="itemCount"]').val();
			
		var list='<tr>'+
		/*'<input type="hidden" name="ConfigurableId'+i+'" value="No">'+	*/
		
		/*'<td><center>'+
		'<select  name="id_prod'+i+'" id="id_prod'+i+'" patelUnPrc="'+i+'" taxName="" class="common-validation prod" >'+
		'</select>'+
		'</center></td>'+*/
		'<td><input style="width: 120px !important;" type="text" id="dynItemData'+i+'" name="nm_prod'+i+'" patelUnPrc="'+i+'"  class="common-validation resetAcc" onchange="changeEventHandlerprod1(event);"></td>'+
		'<td><select style="width: 120px !important;" id="assetDivForInvoice'+i+'" name="id_grp'+i+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
		'<option value="">Select</option></select>'+
		'</td>'+
		'<td><select style="width: 120px !important;" id="subGroupDataForInvoice'+i+'" name="id_sgrp'+i+'" patelUnPrc="'+i+'"  class="common-validation " data-valid=""  style="width:80" >'+
		'<option value="">Select</option></select>'+
		'</td>'+
		
		
	 	'<td><center><input type="text"  name="qty'+i+'" style="width:60px;;text-align: right;"  value="0" class="common-validation" patelUnPrc="'+i+'"  onBlur="return CalcTotal(\'qty\')" class="patelUnPrc data-valid="num"></center></td>'+

		/*'<td><center><input type="text" name="un_prc'+i+'" patelUnPrc="'+i+'" value="0.00" style="width: 60px;text-align: right;" onBlur="return CalcTotal(\'un_prc\')" class=" common-validation"    ></center></td>'+
	
	       '<td><center><input type="text"  name="tot_prc'+i+'" value="0.0" patelUnPrc="'+i+'" style="width:60px;text-align: right;" class="common-validation commonTotal" readonly></center></td>'+
		*/	//'
	 	'<input type="hidden" name="count" value="'+i+'">'+	
	'<input type="hidden" name="id_prod'+i+'"  patelUnPrc="'+i+'" value="">'+	
			'</tr>';
		$('input[name="itemCount"]').val(parseInt(i) +1);
	$('#accessoryDetails').append(list);
	/*DropDownDataDisplay("M_Tax","id_tax"+i,function (status){
		if(status)
		{
			
				
			
		}});*/
	DisplayDropDownDataForGroup("M_Asset_Div","assetDivForInvoice"+i,"CapG",function (status){
		if(status)
		{
			
		}});
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
	
	
	callback(true);	
	
	
}

function ControlSubmitRFI(action,displayContent,createDetails,formName,servletName) {
SessionCheck(function (ses){		
		if(ses)
			{
	
		$(".common-validation").each(function(){
		
			$(".common-validation").removeClass('error');
		});
		$('input[name="dt_req"]').attr("disabled", false);
	
	
	
	if(action=='Cancel')
		{
			$( ".submitRfi" ).trigger( "click" );
		}
	
	
	
	if(action=='Save' || action=='Update')
		{
			if(action == 'Save')
				{
				$('button[name="save1"]').removeClass('hideButton');
				$('input[name="action"]').val('Save');
				}
			else
				{
					$('input[name="action"]').val('Update');
				}
			
			DataInsertUpdateForRFI("Cancel",displayContent,createDetails,formName,servletName);
			
		}
	
			}
});
}

function DataInsertUpdateForRFI(action,displayContent,createDetails,formName,servletName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	t=false;
	t = ValidationForm(formName);
		if(t)
		{
			$('.req').attr('disabled','disabled');
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
			var x = $('#'+formName).serialize();
			
			$.post(servletName,x,function (r){
				
				if(r.data)
					{
					//$('#createAssetPR').hide();
					$('input[name="no_req_val"]').val(r.no_rfi);
						bootbox.alert("RFI Successfully Submitted...");
			           
						$( ".submitRfi" ).trigger( "click" );
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				$('.req').removeAttr('disabled');
				
			},'json');
		
		}
			}
});
}