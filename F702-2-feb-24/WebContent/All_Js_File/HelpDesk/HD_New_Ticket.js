function getResult(){
	SessionCheck(function (ses){		
		if(ses)
			{
			var HideDiv = 'EditDetailsForAllTicket',DisplayDiv='DisplayAllTicket',ClassNameToDisplayData='displayDataForEditAllTicket';
			var search_state = $('select[name="search_state"]').val();
			var search_id_grp = $('select[name="search_id_grp"]').val();
			var search_id_sgrp = $('select[name="search_id_sgrp"]').val();
			
			var typeOfResult = 'Search_All';
			var tcount=0;
			
				$.post('New_Ticket',{action : 'List' ,search_id_sgrp:search_id_sgrp,search_id_grp:search_id_grp,search_state : search_state,typeOfResult:typeOfResult} , function (r){
	
					var list= '<tr class="new">'+
					'<td><strong><center>Ticket Number</center></strong></td>'+
					'<td><strong><center>Priority</center></strong></td>'+
					'<td><strong><center>State</center></strong></td>'+
					'<td><strong><center>Request By</center></strong></td>'+
					/*'<td><strong><center>Assigned To</center></strong></td>'+
					'<td><strong><center>Product Name</center></strong></td>'+*/
					'<td style="width: 105px;"><strong><center>Modify</center></strong></td>'+
				'</tr>';
					
					
				if(r.data.length > 0)
					{
					tcount = r.data[0].tcount;
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						priority = params.priority;
						if(priority == 1) priority='1-High'; else if(priority == 2) priority='2-Medium'; else priority='3-Low';
						list = list + '<tr class="success">'+
						'<td><center>'+params.ticket_no+'</center></td>'+
						'<td><center>'+priority+'</center></td>'+
						'<td><center>'+params.state+'</center></td>'+
						'<td><center>'+params.reqEmp+'</center></td>'+
						/*'<td><center>'+params.agnEmp+'</center></td>'+
						'<td><center>'+params.nm_prod+'</center></td>'+*/
						'<td><strong><a class="alert" href="#" onclick="ControlHelpDesk(\'Edit\',\''+HideDiv+'\',\''+DisplayDiv+'\',\''+ClassNameToDisplayData+'\',\'New_Ticket\','+params.id_ticket+','+params.id_grp+','+params.id_sgrp+','+params.assign_grp+')"> <i class="fas fa-edit"></i> </a></strong></td>'+
						'</tr>';
						}
					}
					else
						list +='<tr rowspan="2"><td colspan="7"><center><strong>No record(s) is available.</strong></center></td></tr>';
				
				$('.displayDataForEditAllTicket').html(list);					
				$('#tcount').html(tcount);			
			},'json');
		
			}});
	
}

function ticketDashboard(){
	$.post('New_Ticket',{action : 'Ticket_Dashboard'} , function (r){
		
		var tcount=0,state='';
		
	if(r.data.length > 0)
		{
		
			for(var i = 0; i < r.data.length ; i++)
			{
				params = r.data[i];
				tcount = params.totcount;
				state  = params.state;
				
				if(state =='New')
					$('#pending').html(tcount);	
				if(state =='Active')
					$('#inprogress').html(tcount);	
				if(state =='Awaiting User Info')
					$('#awaitingInfo').html(tcount);	
				if(state =='Closed')
					$('#closed').html(tcount);	
				if(state =='Closed as in complete')
					$('#incomplete').html(tcount);	
				if(state =='Cancelled')
					$('#cancelled').html(tcount);	
					
			}
		}
		else{
			$('#pending').html(tcount);	
			$('#inprogress').html(tcount);	
			$('#awaitingInfo').html(tcount);
			$('#closed').html(tcount);	
			$('#incomplete').html(tcount);	
			$('#cancelled').html(tcount);
		}


	
			
},'json');
	
}

function ControlHelpDesk(action , DisplayDiv , HideDiv , formName , servletName , id,id1,id2,assign_grp)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='List')
	{
		DisplayTickets(formName, DisplayDiv , HideDiv,id);
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
	}
	else if(action =='Cancel')
	{
		DisplayTickets(formName, DisplayDiv , HideDiv,id);
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
	}
	else if(action =='Edit')
	{
		EditTicket(action , DisplayDiv , HideDiv , formName , id,id1,id2,assign_grp);
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
	}
	else if(action =='Onload')
		{
		$.post('New_Ticket',{action : 'NextTicketNumber'},function (r){
			if(r.data)
				{
					$('input[name="ticket_no"]').val(r.data);
				}
		},'JSON');
		}
	
	else if(action == "Save")
	{
		var t=false;
		t = ValidationForm(formName);
		if(t){
			$('.req').attr('disabled','disabled');
		var x = $('#'+formName).serialize();
		$.post(servletName,x,function (r){
				if(r.data > 0)
					{
					bootbox.alert("Updated Successfully.");
						/*DisplayTickets();
						
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();*/
						window.location = $('.Create_Ticket_event').attr('href');
				
					}
				else
				{
					bootbox.alert("Something went wrong. Please try again.");
				}
				$('.req').removeAttr('disabled');
			},'json');
			 
	}
	}
	else if(action == "Update")
	{
		var t=false;
		t=ValidationForm(formName);
		if(t)
			{
			$('.update').attr('disabled','disabled');
				var x =$('#'+formName).serialize();
				$.post('New_Ticket',x,function (r)
						{
					
							if(r.data)
								{
								bootbox.alert('Updated successfully.');
									$( "."+servletName ).trigger( "click" );
								}
							else
								{
									bootbox.alert('Please try again.');
								}
								$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
							$('.update').removeAttr('disabled');
						},'json');
				
		
			}
		
	}
	
	
	
	
			}});
}





function DisplayTickets(formName, DisplayDiv , HideDiv,ClassNameToDisplayData)
{
	SessionCheck(function (ses){		
		if(ses)
			{
			var searchWord = $('input[name="search"]').val();
			var typeOfResult = '';
			if(ClassNameToDisplayData == 'displayDataForNewTicket')
				typeOfResult='Assigned_To';
			else if(ClassNameToDisplayData == 'displayDataForEditInProgressTicket')
				typeOfResult='Inprogress';
			else if(ClassNameToDisplayData == 'displayDataForEditAllTicket')
				typeOfResult='All';
			else 
				typeOfResult='Closed';
			
				$.post('New_Ticket',{action : 'List' ,searchWord : searchWord,typeOfResult:typeOfResult} , function (r){
	
					var list= '<tr class="new ">'+
					'<td ><strong><center>Ticket Number</center></strong></td>'+
					'<td><strong><center>Priority</center></strong></td>'+
					'<td><strong><center>State</center></strong></td>'+
					'<td><strong><center>Request By</center></strong></td>'+
					/*'<td><strong><center>Assigned To</center></strong></td>'+
					'<td><strong><center>Product Name</center></strong></td>'+*/
					'<td style="width: 105px;"><strong><center>Modify</center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						priority = params.priority;
						if(priority == 1) priority='1-High'; else if(priority == 2) priority='2-Medium'; else priority='3-Low';
						list = list + '<tr class="success">'+
						'<td><center>'+params.ticket_no+'</center></td>'+
						'<td><center>'+priority+'</center></td>'+
						'<td><center>'+params.state+'</center></td>'+
						'<td><center>'+params.reqEmp+'</center></td>'+
						/*'<td><center>'+params.agnEmp+'</center></td>'+
						'<td><center>'+params.nm_prod+'</center></td>'+*/
						'<td><strong><a class="alert" href="#" onclick="ControlHelpDesk(\'Edit\',\''+HideDiv+'\',\''+DisplayDiv+'\',\''+ClassNameToDisplayData+'\',\'New_Ticket\','+params.id_ticket+','+params.id_grp+','+params.id_sgrp+','+params.assign_grp+')"> <i class="fas fa-edit"></i> </a></strong></td>'+
						'</tr>';
						}
					}
					else
						list +='<tr rowspan="2"><td colspan="7"><center><strong>No record(s) is available.</strong></center></td></tr>';
				
				$('.'+ClassNameToDisplayData).html(list);					
				
			},'json');
		
			}});
}

function EditTicket(action , DisplayDiv , HideDiv , FormName , id,id_grp,id_sgrp,assign_grp)
{
	SessionCheck(function (ses){		
		if(ses)
			{	
			if(id_grp =='' || id_grp==undefined){
				
				$.post('New_Ticket',{action : 'Edit' , id : id},function (r){
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
									
						        }
							}
		var activity='';
		
		if(r.history)
		{
			for(var i = 0; i < r.history.length ; i++)
			{
				params = r.history[i];
				activity +='<div class="activityMessage" style="width: 550px;border: 1px solid #bdc0c4;border-radius: 8px;color: #455464;padding: 16px;font: 13px;border-left-width: thick;">'+
						'<span>Updated By : '+params.nm_emp+'</span><span style="float: right;">Date : '+params.dt_comment+'</span><br><br>'+
						'<p>'+params.work_note+'</p>'+
						'</div>';
			}
		}
		if(activity !=''){
			$('.activityLogs').html(activity);
		}			
		if(assign_grp!=undefined){		
		SubDropDownDataDisplay(assign_grp,'empForAssignToHD','M_GroupAssign',function (status){
						if(status)
						{
							
							$('select[name=assign_to] option[value=' + r.data[0].assign_to + ']').attr('selected',true);
							}});				
						
				}			
							
							
							$('input[name=id]').val(id);
							$('#'+HideDiv).hide();
							$('#'+DisplayDiv).show();
							var uploadFile='';
						if(r.data[0].file_name1 != undefined)
							uploadFile = r.data[0].file_name1;
							var download='<a href="downloadInvoiceDetails.jsp?fileName1='+uploadFile+'">'+uploadFile+'</a> ';
							
							document.getElementById("FileName").innerHTML =download;
						}
					else
						{
							bootbox.alert("Try again.");
						}
					
			},'json');
			
			}else
			DropDownDataDisplay("M_Group","depDataForNewTicket",function (status){
							if(status)
							{
					SubDropDownDataDisplay(assign_grp,'empForAssignToHD','M_GroupAssign',function (status){
						if(status)
						{
	SubDropDownDataDisplay(id_grp,'requestPrAssetSubGroup','M_Subasset_Div',function (status){
	if(status)
		{
		DisplaySubDropDownData(id_sgrp,'proNameForReqPR','A_Add_To_Store',function (status){
			if(status)
				{
		$.post('New_Ticket',{action : 'Edit' , id : id},function (r){
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
								
					        }
						}
	var activity='';
	
	if(r.history)
	{
		for(var i = 0; i < r.history.length ; i++)
		{
			params = r.history[i];
			activity +='<div class="activityMessage" style="width: 550px;border: 1px solid #bdc0c4;border-radius: 8px;color: #455464;padding: 16px;font: 13px;border-left-width: thick;">'+
					'<span>Updated By : '+params.nm_emp+'</span><span style="float: right;">Date : '+params.dt_comment+'</span><br><br>'+
					'<p>'+params.work_note+'</p>'+
					'</div>';
		}
	}
	if(activity !=''){
		$('.activityLogs').html(activity);
	}					
					
					
						
						
						
						$('input[name=id]').val(id);
						$('#'+HideDiv).hide();
						$('#'+DisplayDiv).show();
						
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
		},'json');
			}});
	}});
}});
}});
}});	
}



function SetPriority(){
	var urgency = $('select[name="urgency"]').val();
	var impact = $('select[name="impact"]').val();	
	
	if(urgency ==1 && impact==1){
		$('input[name="priority"]').val('1');	
		$('input[name="priority1"]').val('1-Critical');	
	}else if((urgency ==1 && impact==2) || (urgency ==2 && impact==1)){
		$('input[name="priority"]').val('2');	
		$('input[name="priority1"]').val('2-High');	
	}else if((urgency ==1 && impact==3) || (urgency ==3 && impact==1) || (urgency ==2 && impact==2)){
		$('input[name="priority"]').val('3');	
		$('input[name="priority1"]').val('3-Moderate');	
	} else if((urgency ==2 && impact==3) || (urgency ==3 && impact==2)){
		$('input[name="priority"]').val('4');	
		$('input[name="priority1"]').val('4-Low');	
	}else if(urgency ==3 && impact==3){
		$('input[name="priority"]').val('5');	
		$('input[name="priority1"]').val('5-Planning');	
	}
}



