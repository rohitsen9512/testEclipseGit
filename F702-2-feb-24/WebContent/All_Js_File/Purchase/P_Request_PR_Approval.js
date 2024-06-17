
function ControlDivForPurchaseRequestPRApproval(action , DisplayDiv , HideDiv , FormName , id,approv_Status,non_budget)
{
	
SessionCheck(function (ses){		
	if(ses)
	{
		if(action =='Cancel')
		{
			$( ".approvalRequest" ).trigger( "click" );
			$('#'+DisplayDiv).hide();
			$('#'+HideDiv).show();
		}
		else if(action =='Modify')
		{
			EditAcceptRejectPurchaseRequestPRApprovalFun(action , DisplayDiv , HideDiv , FormName , id,approv_Status,non_budget);
			
		}
		else if(action =='Accepted' || action =='Rejected')
		{
			var t=false;
			var temp=0;
			
				$('.approvalPRForSelectAll').each(function(){
					
					$('input[name="qty'+$(this).val()+'"]').removeClass('error');
					$('input[name="un_prc'+$(this).val()+'"]').removeClass('error');
					$('input[name="tot_prc'+$(this).val()+'"]').removeClass('error');
					
					if(this.checked)
					{
						t=true;
						
						val = $('input[name="qty'+$(this).val()+'"]').val();
						if(val == '')
							{
								bootbox.alert("Please enter quantity.");
								$('input[name="qty'+$(this).val()+'"]').addClass('error');
								$('input[name="qty'+$(this).val()+'"]').focus();
								t=false;
								exit(0);
							}
						val = $('input[name="un_prc'+$(this).val()+'"]').val();
						if(val == '')
						{
							bootbox.alert("Please enter the unit price.");
							$('input[name="un_prc'+$(this).val()+'"]').addClass('error');
							$('input[name="un_prc'+$(this).val()+'"]').focus();
							t=false;
							exit(0);
						}
						val = $('input[name="tot_prc'+$(this).val()+'"]').val();
						if(val == '')
						{
							bootbox.alert("Total price can not be empty please refresh your page.");
							$('input[name="tot_prc'+$(this).val()+'"]').addClass('error');
							$('input[name="tot_prc'+$(this).val()+'"]').focus();
							t=false;
							exit(0);
						}
						temp=1;
					}
					
					
				});
				if(temp == 0)
					{
						bootbox.alert("Please select at least one request then proceed.");
						t=false;
					}
			
			if(t)
				{
					$('input[name="AcceptRejectStatus"]').val(action);
					var x = $('#SubmitRequestApprovalDetailsForApproval').serialize();
					$('.prBtnForDissable').attr('disabled','disabled');
					$.post('P_Request_PR_Approval',x,function (r){
						
						if(r.data > 0)
							{
								bootbox.alert(action +" Successfully.");
								$( ".approvalRequest" ).trigger( "click" );
								/*$('#'+DisplayDiv).hide();
								$('#'+HideDiv).show();*/
							}
						else
						{
							bootbox.alert("Try again.");
						}
						$('.prBtnForDissable').removeAttr('disabled');	
					},'json');
				}
		}

	}
});



}




function EditAcceptRejectPurchaseRequestPRApprovalFun(action , DisplayDiv , HideDiv , FormName , id,approv_Status,non_budget)
{
	SessionCheck(function (ses){		
		if(ses)
			{
				
				
		$.post('P_Request_PR_Approval',{action : 'Edit' , id : id,approv_Status:approv_Status,non_budget:non_budget},function (r){
				
				if(r.data)
					{
						
		
						
						
						var bud="";
						var badtyp= r.data[0].budget_type;
						
						if(badtyp=='budget'){
							bud='<option value = "non_budget" >Non-Budget</option>'+
								'<option value = "budget" selected >Budget</option>'+
								'<option value = "adhoc" >Ad-Hoc</option>';
								
						}
						else if(badtyp=='adhoc')
						{
							bud='<option value = "non_budget" >Non-Budget</option>'+
								'<option value = "budget" >Budget</option>'+
								'<option value = "adhoc" selected >Ad-Hoc</option>';
						}
						else{
							bud='<option value = "non_budget" selected >Non-Budget</option>'+
								'<option value = "budget" >Budget</option>'+
								'<option value = "adhoc" >Ad-Hoc</option>';
							
						}
						
					var list='<tr class="info">'+
						/*'<td colSpan="8" style="background-color: blue;">'+
							'<select  name = "budget_type" class="common-validation"   data-valid="mand" >'+
								''+bud+''+
								
					
						'</select>'+ 
					'</td>'+*/	
						
						'<input type="hidden" name="id_bu" value="'+r.data[0].id_bu+'">'+
					 '<tr class="info">'+
					'<td colSpan="12" style="background-color: blue;"><p style="font-size: 15px;background-color: blue;color: white;margin-left: 0px;">Approve Requested Assets : <font color="chartreuse">'+r.data[0].no_req+'</font>, Requested By : <font color="chartreuse">'+r.data[0].nm_emp+'</font> , Requested Date : <font color="chartreuse">'+r.data[0].dt_req+'</font></td>'+
					'</tr>'+
						'<tr class="info">'+
						'<td ><b><center>Model Name</center></b></td>';
					/*	'<td ><b><center>Model Code</center></b></td>'+*/
					  if(approv_Status=='RM'){
					 list +='<td ><b><center>Group</center></b></td>'+
					'<td ><b><center>Sub Group</center></b></td>';
					}
					
					  list +=	'<td style="width: 55px;"><b><center>Qty<font color="red"> * </font></center></b></td>'+
						'<td style="width: 85px;"><b><center>Unit Price<font color="red"> * </font></center></b></td>'+
						'<td style="width: 85px;"><b><center>Total Price<font color="red"> * </font></center></b></td>';
						 if(approv_Status!='waiting'){
					list +=		'<td style="width: 85px;"><b><center> Budget Type<font color="red"> * </font></center></b></td>'+
						
						'<td style="width: 85px;"><b><center>Allocate Budget<font color="red"> * </font></center></b></td>'+
						'<td style="width: 85px;"><b><center>Budget Utilized<font color="red"> * </font></center></b></td>'+
					'<td style="width: 85px;"><b><center>Budget Remaining<font color="red"> * </font></center></b></td>';
					}
					 
						//'<td style="width: 85px;"><b><center>Budget Type<font color="red"> * </font></center></b></td>'+
					list +=		'<td ><b><center>Remarks</center></b></td>'+
						'<td style="width: 130px;"><strong><center><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'approvalPRForSelectAll\')"> Check </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'approvalPRForSelectAll\')"> Uncheck </a></center></strong></td>'+
			       '</tr>';
						for(var i = 0; i < r.data.length ; i++)
						{
							
						params = r.data[i];
						var remarks='';
						 if(params.approv_remarks == '' || params.approv_remarks == undefined) remarks='';else remarks=params.approv_remarks;
						
						 list +='<tr class="success">'+
					         '<input type="hidden" name="id_prod'+params.id_req_asst+'" value="'+r.data[i].id_prod+'">';
                              if(approv_Status=='RM'){
	
	 	 list +='<td ><center><input type="text" class="common-validation" data-valid="mand" name="nm_model'+params.id_req_asst+'" value="'+params.model+'" style="width: 100px;"></center></td>'+
							
						/*		'<td ><b><center>'+params.nm_model+'</center></b></td>'+*/
							/*	'<td ><b><center>'+params.cd_model+'</center></b></td>'+*/
							'<td><center><select style="width: 120px !important;" id="assetDivForInvoice'+i+'" name="id_grp'+params.id_req_asst+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
					'<option value="">Select</option></select>'+
			'</center></td>'+
			'<td><center><select style="width: 120px !important;" id="subGroupDataForInvoice'+i+'" onchange="checkbudgetwithitem(event);" name="id_sgrp'+params.id_req_asst+'" patelUnPrc="'+params.id_req_asst+'"  class="common-validation dropsgrp" data-valid=""  style="width:80" >'+
					'<option value="">Select</option></select>'+
					'</center></td>';
							
	
                         }
else{
	
	 	 list +='<td ><center><input type="text"  readonly class="common-validation" data-valid="mand" name="nm_model'+params.id_req_asst+'" value="'+params.model+'" style="width: 100px;"></center></td>'+
							
						/*		'<td ><b><center>'+params.nm_model+'</center></b></td>'+*/
							/*	'<td ><b><center>'+params.cd_model+'</center></b></td>'+*/
							
						'<input type="hidden" name="id_grp'+params.id_req_asst+'" value="'+params.id_assetdiv+'">'+
						'<input type="hidden" name="id_sgrp'+params.id_req_asst+'" value="'+params.id_s_assetdiv+'">';
							
						/*	'<td style="dispaly:none"><center><select style="width: 120px !important; " id="assetDivForInvoice'+i+'" name="id_grp'+params.id_req_asst+'" patelUnPrc="'+i+'"  class="common-validation  groupdrop" data-valid=""  style="width:80" onChange="SubDropDownDataDisplay(this,\'subGroupDataForInvoice'+i+'\',\'M_Subasset_Div\')">'+
					'<option value="">Select</option></select>'+
			'</center></td>'+
			'<td style="dispaly:none"> <center><select style="width: 120px !important;" id="subGroupDataForInvoice'+i+'" onchange="checkbudgetwithitem(event);" name="id_sgrp'+params.id_req_asst+'" patelUnPrc="'+i+'"  class="common-validation dropsgrp" data-valid=""  style="width:80" >'+
					'<option value="">Select</option></select>'+
					'</center></td>';*/
							
}
             
							
							 list +=	'<td ><center><input type="text" class="common-validation" data-valid="mand" name="qty'+params.id_req_asst+'" value="'+params.qty+'" style="width: 40px;" onBlur="return CalcTotalForApproval('+params.id_req_asst+')"></center></td>'+
								'<td ><center><input type="text" class="common-validation" data-valid="mand" name="un_prc'+params.id_req_asst+'" value="'+params.un_prc+'" style="width: 85px;" onBlur="return CalcTotalForApproval('+params.id_req_asst+')"></center></td>'+
								'<td ><center><input type="text" class="common-validation" data-valid="mand" name="tot_prc'+params.id_req_asst+'" value="'+params.tot_prc+'" style="width: 85px;" readonly><center></td>';
								 if(approv_Status!='waiting'){
							 list +='<td><select  style="width: 120px;" name = "budget_type'+params.id_req_asst+'" patelUnPrc="'+params.id_req_asst+'"  class="common-validation"   data-valid="mand" >'+
								
								'<option value = "non_budget" >Non-Budget</option>'+
								'<option value = "budget" >Budget</option>'+
								
					
						'</select>'+
					'</td>'+
					       '	<td id="budg_allo'+params.id_req_asst+'" patelUnPrc="'+params.id_req_asst+'" ><b><center>'+params.budg_allo+'</center></b></td>'+
								'	<td id="budg_util'+params.id_req_asst+'" patelUnPrc="'+params.id_req_asst+'" ><b><center>'+params.budg_util+'</center></b></td>'+
								'	<td id="budg_rem'+params.id_req_asst+'" patelUnPrc="'+params.id_req_asst+'" ><b><center>'+params.budg_rem+'</center></b></td>';
							
							}
							 list +=	'<td ><center><input type="text" name="remarks'+params.id_req_asst+'" value="'+remarks+'"></center></td>'+
								'<td ><center><input type="checkbox" name="approvalPR" class="approvalPRForSelectAll" value="'+params.id_req_asst+'" style="width: 30px;"/></center></td>'+
					         '</tr>';
							
						}
						list +='<tr>'+
							'<td colspan="8">'+
						'<input type="button" class="btn btn-primary prBtnForDissable" style="margin-left: 350px;"  value="Approve" onClick="return ControlDivForPurchaseRequestPRApproval(\'Accepted\', \'requestApprovalDetailsForApproval\' , \'displayRequestForApproval\' , \'\',\'\',\'\')">&nbsp;&nbsp;'+
						'<input type="button" class="btn btn-primary prBtnForDissable"  value="Reject" onClick="return ControlDivForPurchaseRequestPRApproval(\'Rejected\', \'requestApprovalDetailsForApproval\' , \'displayRequestForApproval\' , \'\',\'\',\'\')">&nbsp;&nbsp;'+
						'<input type="button" class="btn btn-primary prBtnForDissable"  value="Cancel" onClick="return ControlDivForPurchaseRequestPRApproval(\'Cancel\', \'requestApprovalDetailsForApproval\' , \'displayRequestForApproval\' , \'\',\'\',\'\')"></td>'+	
						'</tr>';
						
						$('input[name="id_req"]').val(id);
						
						$('.approvalDetailsForApproval').html(list);
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
						
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				/*if(r.budget)
					{
					var list2='<tr class="info">'+
									'<td  colSpan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 300px;">Budget Allocated For the Current Year</td>'+
								'</tr>'+
								'<tr class="info">'+
								'	<td ><b><center>Sub-Function</center></b></td>'+
								'	<td ><b><center>Budget Allocated</center></b></td>'+
								'	<td ><b><center>Budget Utilized</center></b></td>'+
								'	<td ><b><center>Budget Remaining</center></b></td>'+
								'</tr>'+
								'<tr class="success">'+
								'	<td ><b><center>'+r.budget[0].nm_cc+'</center></b></td>'+
								'	<td ><b><center>'+r.budget[0].budg_allo+'</center></b></td>'+
								'	<td ><b><center>'+r.budget[0].budg_util+'</center></b></td>'+
								'	<td ><b><center>'+r.budget[0].budg_rem+'</center></b></td>'+
								'</tr>';
					$('.BudgetInfoForApproval').html(list2);
					}*/
			/*		var list2='<tr class="info">'+
									'<td  colSpan="5" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 300px;">Budget Allocated For the Current Year</td>'+
								'</tr>'+
								'<tr class="info">'+
								'	<td ><b><center>Sub-Function</center></b></td>'+
								'	<td ><b><center>Model</center></b></td>'+
								'	<td ><b><center>Budget Allocated</center></b></td>'+
								'	<td ><b><center>Budget Utilized</center></b></td>'+
								'	<td ><b><center>Budget Remaining</center></b></td>'+
								'</tr>';
					if(r.budget.length > 0)
					{
				
				for(var i = 0; i < r.budget.length ; i++)
				{
				
				params = r.budget[i];
				
				
				list2 = list2 + '<tr class="success">'+
									'	<td ><b><center>'+params.nm_cc+'</center></b></td>'+
									'	<td ><b><center>'+params.nm_model+'</center></b></td>'+
								'	<td ><b><center>'+params.budg_allo+'</center></b></td>'+
								'	<td ><b><center>'+params.budg_util+'</center></b></td>'+
								'	<td ><b><center>'+params.budg_rem+'</center></b></td>'+
							  '</tr>';
				}
			
			
			//	$('.BudgetInfoForApproval').html(list2);
			
			}
		else
			{
			list2 +='<tr><td colspan="5"><strong><center>No Budget found for particular Sub-Function.</center></strong></td></tr>';
			$('.BudgetInfoForApproval').html(list2);
			}*/
				
			DisplayDropDownDataForGroup('M_Asset_Div','groupDrop','CapG',function (status){
					if(status)
					{
						
					SubDropDownDataDisplay('0','dropsgrp','M_Subasset_Div',function (status){
						
						
						
						for(var i=0;i<r.data.length;i++)
						{
							params=r.data[i];
							
							
							if(params.budg_allo=='0'){
							$('select[name="budget_type'+params.id_req_asst+'"]').val('non_budget').attr('selected',true); 
						}
						else{
							$('select[name="budget_type'+params.id_req_asst+'"]').val('budget').attr('selected',true); 
						}
							for (var key in r.data[i])
					        {
								
									
									if(key=='id_grp')
									{ 
										$('select[name=id_grp'+r.data[i].id_req_asst+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
									if(key=='id_sgrp')
									{
										$('select[name=id_sgrp'+r.data[i].id_req_asst+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
									
					        }
							
						}	
					});
					
					
					}});	
		},'json');
			}});
}




function checkbudgetwithitem(event) {
	
	var domElement =$(event.target || event.srcElement);
     var id_bu = $('input[name="id_bu"]').val();
	var id_sgrp = $('select[name="id_sgrp'+domElement.attr('patelUnPrc')+'"]').val();
	// alert(id_sgrp+'zsdcfcvxxvc'+id_bu);
	 $.post('P_Request_PR_Approval',{action : 'budgetCheck', id:id_sgrp,id_bu:id_bu},function (r){
			
			if(r.data)
				{
			 			
				 $('#budg_allo'+domElement.attr('patelUnPrc')+'').text(r.data[0].budg_allo); 
			 	 $('#budg_util'+domElement.attr('patelUnPrc')+'').text(r.data[0].budg_util); 
				 $('#budg_rem'+domElement.attr('patelUnPrc')+'').text(r.data[0].budg_rem); 
				if(r.data[0].budg_allo=='0'){
							$('select[name="budget_type'+domElement.attr('patelUnPrc')+'"]').val('non_budget').attr('selected',true); 
						}
						else{
							$('select[name="budget_type'+domElement.attr('patelUnPrc')+'"]').val('budget').attr('selected',true); 
						}
				}
			
	 },'json');
	    
	
	
	
}




function DisplayRequestForApproval(IdForDisplayResult,approv_Status, callback)
{
	var msg='';
	if(approv_Status == 'waiting')
		msg='1st level';
	else if(approv_Status == 'RM')
			msg='2nd level';
		else if(approv_Status == 'IT')
			msg='Non Budget';
		else if(approv_Status == 'CEO')
			msg='Non Budget';
			
	var t=false;

			var list = '<tr>'+
				'<td colspan="6" class="tableHeader">'+
				'<p class="tableHeaderContent" style="margin-left: 35%;">Approval Pending For '+msg+'</p></td>'+
				//'<p class="tableHeaderContent" style="margin-left: 35%;">Request Pending For Approval</p></td>'+
				
				'</tr>';
			
				$.post('P_Request_PR_Approval',{action : 'Display',approv_Status:approv_Status},function (r){
					
					 list += '<tr class="tableHeader info">'+
					 	'<td><center><strong>Request Number</strong></center></td>'+
						'<td><center><strong>Requested Date</strong></center></td>'+
						'<td><center><strong>Requested By </strong></center></td>'+
						'<td><center><strong>Sub-Function  </strong></center></td>'+
						
					'<td style="width:160px;"><center><strong><a href="#">Approve/Reject </a></center></td>'+
				'</tr>';
					 t=true;
				if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
											'<td><center>'+params.no_req+'</center></td>'+
											'<td><center>'+params.dt_req+'</center></td>'+
											'<td><center>'+params.nm_emp+'</center></td>'+
											'<td><center>'+params.nm_bu+'</center></td>';
											if(approv_Status=='IT' || approv_Status=='CEO')
										list = list +	'<td><center><a class="alert" href="#" onclick="ControlDivForPurchaseRequestPRApproval(\'Modify\' , \'requestApprovalDetailsForApproval\' , \'displayRequestForApproval\' , \'\' ,'+params.id_req+',\''+approv_Status+'\',\'non_budget\')"> Approve/Reject </a></center>';
											else
									list = list +			'<td><center><a class="alert" href="#" onclick="ControlDivForPurchaseRequestPRApproval(\'Modify\' , \'requestApprovalDetailsForApproval\' , \'displayRequestForApproval\' , \'\' ,'+params.id_req+',\''+approv_Status+'\',\'budget\')"> Approve/Reject </a></center>'+
										
											'</tr>';
						}
					
					
						$('#'+IdForDisplayResult).html(list);
					
					}
				
				else
					{
						list +='<tr><td colspan="5"><strong><center>No record(s) is available.</center></strong></td></tr>';
						$('#'+IdForDisplayResult).html(list);
					}
				callback(t);
			},'json');
		
			
}




