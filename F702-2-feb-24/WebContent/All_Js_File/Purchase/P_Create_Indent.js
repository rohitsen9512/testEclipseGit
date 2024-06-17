function changeEventHandlerprodIndent(event) {
	var domElement =$(event.target || event.srcElement);
   alert('You like ' + event.target.value + ' ice cream.');
	//var nm_prod =event.target.value;
	
	var nm_prod = $('input[name="nm_model'+domElement.attr('patelUnPrc')+'"]').val();
	console.log(nm_prod);
	 $.post('M_Model',{action : 'GetModeldetails', id:nm_prod},function (r){
			
			if(r.data.length >0)
				{
					var id_assetdiv=r.data[0].id_s_assetdiv;
					if(id_assetdiv!=0)
					{
				console.log('pritesh'+r.data[0].id_s_assetdiv);
				$('select[name=id_grp1'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_assetdiv + ']').attr('selected',true);
				SubDropDownDataDisplay(r.data[0].id_assetdiv,'id_sgrp1'+domElement.attr('patelUnPrc')+'','M_Subasset_Div',function (status){
					
					$('select[name=id_sgrp1'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_s_assetdiv + ']').attr('selected',true);
					$('input[name="id_grp'+domElement.attr('patelUnPrc')+'"]').val(r.data[0].id_assetdiv);
					$('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val(r.data[0].id_model);
					$('input[name="id_sgrp'+domElement.attr('patelUnPrc')+'"]').val(r.data[0].id_s_assetdiv);
					$('select[name=id_grp1'+domElement.attr('patelUnPrc')+']').attr('disabled','disabled');
				$('select[name=id_sgrp1'+domElement.attr('patelUnPrc')+']').attr('disabled','disabled');
					
					
				});
				//document.getElementById('assetDivForInvoice'+domElement.attr('patelUnPrc')+'').trigger("change");
				//$('select[name=id_grp'+domElement.attr('patelUnPrc')+']').attr('disabled','disabled');
				//$('select[name=id_sgrp'+domElement.attr('patelUnPrc')+']').attr('disabled','disabled');
					}
				}
				else
				{
					$('select[name=id_grp1'+domElement.attr('patelUnPrc')+']').removeAttr('disabled','disabled');
				$('select[name=id_sgrp1'+domElement.attr('patelUnPrc')+']').removeAttr('disabled','disabled');
				}
			
	 },'json');
	    
	
	
	
}
function getGroupID(event,ids) {
	var domElement =$(event.target || event.srcElement);
	var id=ids.value;
	console.log(id);
	$('input[name="id_grp'+domElement.attr('patelUnPrc')+'"]').val(id);
	
}
function getSubGroupID(event,ids) {
	var domElement =$(event.target || event.srcElement);
	var id=ids.value;
	console.log(id);
	$('input[name="id_sgrp'+domElement.attr('patelUnPrc')+'"]').val(id);
	
}
function ControlCreateIndent(action,DisplayDiv,HideDiv,id_rfq_creat)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Next')
	{
		var t=true;
		var id_req="0";
		$('.prRequestForSelectAll').each(function(){
			if(this.checked)
			{
				t=true;
				if(id_req == '0')
					id_req = $(this).val();
					else
						id_req +='Patel'+ $(this).val();
			}
			
		});
		if(!t)
			{
				bootbox.alert("Please select at least one request then procced.");
			}
		else
			{
				/*alert(id_rfq_creat);
				$.post('Create_Indent',{action : 'CheckBudget',ids : id_rfq_creat},function (r){
			
			if(r.data==1)
				{*/
				var id_grp=0;
				$.post('Create_Indent',{action : 'CreateIndent' ,id_req:id_rfq_creat},function (r){
				var list='<tr class="success"><td colspan="7">No record(s) is available.</td></tr>';
				if(r.data.length > 0)
					{
						
					 list='<tr>'+
								'<td colspan="9" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 350px;">Material Details For Making PR</p></td>'+
							'</tr>'+
							'<tr class="info">'+
							 '<td ><strong><center>Model<a href=#></a></center></strong></td>'+
								/*'<td ><strong><center>Group<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Sub Group<a href=#></a></center></strong></td>'+
							*/	'<td><center><strong>Indent Req</strong></center></td>'+
								'<td ><strong><center>Quantity<a href=#></a></center></strong></td>'+
								'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
								'<td ><strong><center> Grand Total<a href=#></a></center></strong></td>'+
								//'<td ><strong><center> Budget Type<a href=#></a></center></strong></td>'+
								'<td style="width:120px;"><strong><center><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'prRequestItemForIndentSelectAll\')"> Check All </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'prRequestItemForIndentSelectAll\')"> Uncheck All</a></center></strong></td>'+
								
							'</tr>';
					for(var i = 0; i < r.data.length ; i++)
					{
						
						params=r.data[i];
						
						list +='<tr class="success">'+
									'<td><input style="width: 200px ;" type="text" id="dynItemData'+id_req_asst+'" name="nm_model'+params.id_req_asst+'" value="'+params.nm_model+'" patelUnPrc="'+params.id_req_asst+'"  class="common-validation resetAcc" readonly onchange="changeEventHandlerprodIndent(event);" ></td>'+

									//'<td><center>'+params.nm_model+'</center></td>'+
								/*	'<td><center>'+
				'<select style="width: 100px;" name="id_grp1'+params.id_req_asst+'"" patelUnPrc="'+params.id_req_asst+'" id="id_grp1'+i+'" class="common-validation groupdrop" onchange="SubDropDownDataDisplay(this,\'id_sgrp1'+params.id_req_asst+'\',\'M_Subasset_Div\');getGroupID(event,this);">'+
				'</select>'+
				'</center></td>'+																																									
				'<td><center>'+
				'<select style="width: 100px;" name="id_sgrp1'+params.id_req_asst+'" patelUnPrc="'+params.id_req_asst+'" id="id_sgrp1'+i+'" class="common-validation dropsgrp" onchange="getSubGroupID(event,this);">'+
				'</select>'+
				'</center></td>'+*/
									'<td><center>'+params.no_req+'</center></td>'+
									'<td><center>'+params.qty+'</center></td>'+
									'<td><center>'+params.un_prc+'</center></td>'+
									'<td><center>'+params.tot_prc+'</center></td>'+
									/*'<td><center>'+
								'<select  name = "budget_type'+params.id_req_asst+'" class="common-validation" value="'+params.budget_type+'"  data-valid="mand" >'+
								'<option value = "non_budget" >Non-Budget</option>'+
								'<option value = "budget" >Budget</option>'+
								
					
						'</select>'+
						'</center></td>'+*/
									//id_grp=$('input[name="id_grp1'+domElement.attr('patelUnPrc')+'"]').val();
									'<input type="hidden"  name="id_prod'+params.id_req_asst+'" patelUnPrc="'+params.id_req_asst+'" value="'+params.id_prod+'">'+
									'<input type="hidden"  name="id_grp'+params.id_req_asst+'" patelUnPrc="'+params.id_req_asst+'" value="">'+
									'<input type="hidden"  name="id_sgrp'+params.id_req_asst+'" patelUnPrc="'+params.id_req_asst+'" value="">'+
									'<td><strong><center><input type="checkbox" name="createLineIndent" class="prRequestItemForIndentSelectAll"  value="'+params.id_req_asst+'"/></center></strong></td>'+
								changeEventHandlerprodForConsolidate(event,params.nm_model);
								'</tr>';
					}
					DisplayDropDownDataForGroup('M_Asset_Div','groupDrop','CapG',function (status){
					if(status)
					{
						
					SubDropDownDataDisplay('0','dropsgrp','M_Subasset_Div',function (status){
						
						
						
						for(var i=0;i<r.data.length;i++)
						{
							params=r.data[i];
							
							for (var key in r.data[i])
					        {
								
									
									if(key=='id_assetdiv')
									{ 
										$('select[name=id_grp'+r.data[i].id_req_asst+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
									if(key=='id_s_assetdiv')
									{
										$('select[name=id_sgrp'+r.data[i].id_req_asst+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
										
									}
									
					        }
							
						}	
					});
					
					
					}});
					list +='<tr class="success">'+
					         	'<td colspan="9">'+
						         	'<button type="button" style="margin-left: 475px;" class="btn btn-primary ind" onclick="ControlCreateIndent(\'Save\',\'displayIndent\',\'createIndent\')">Save </button>&nbsp;&nbsp;'+
						         	'<button type="button" class="btn btn-primary ind" onclick="ControlCreateIndent(\'Back\',\'displayIndent\',\'createIndent\')">Cancel </button>'+
					         	'</td>'+
							'</tr>';
					
						$('#'+DisplayDiv).show();
						$('#'+HideDiv).hide();
					}
				else
				{
					bootbox.alert("Something went wrong. Please try again.");
				}
	/*$("#dynItemData0").easyAutocomplete(options);
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
	$("#dynItemData30").easyAutocomplete(options);*/
	
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
				var list1='<tr>'+
					'<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 450px;">Details Of PR</p></td>'+
					'</tr>'+
					'<input type="hidden"  name="no_ind" value="'+r.Indent_No+'">'+
					'<input type="hidden"  name="dt_ind" value="'+r.dt_ind+'">'+
					'<input type="hidden"  name="id_req" value="'+id_req+'">'+
					
					'<tr class="info">'+
						'<td><center><strong>RFQ Number :</strong></center></td>'+
						'<td><center><strong>RFQ Date :</strong></center></td>'+
						/*'<td ><center><strong>Budget Type :</strong></center></td>'+
						'<td><center><strong>Remarks:</strong></center></td>'+*/
						//'<td><center><strong>Registration Number :</strong></center></td>'+
					'</tr>'+
					'<tr class="success">'+
						'<td ><center><b>'+r.Indent_No+'</b></center></td>'+
						
						'<td ><center><b>'+r.dt_ind+'</b></center></td>'+
						/*'<td>'+
							'<select  name = "budget_type" class="common-validation"   data-valid="mand" >'+
								''+bud+''+
					
						'</select>'+
					'</td>'+
					'<td>'+
						'<textarea type="text"  name="remarks" value="" style="width: 600px;height: 20px;"></textarea>'+
					'</td>'+*/
							/*'<td><center><input type="text"  name="regis_num" value="">'+
							'</center></td>'+*/	
						
					'</tr>'+
					'<tr class="success">'+
					
						//'<td ></td>'+
					'</tr>';
				
				$('.indentDataDisplay').html(list1);
				$('.requestDisplayForMakingIndent').html(list);
				$('.ind').removeAttr('disabled');
				
				var list2='<tr class="info">'+
									'<td  colSpan="5" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 300px;"></td>'+
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
			
			
				//$('.BudgetInfoForApproval').html(list2);
			
			}
		else
			{
			list2 +='<tr><td colspan="5"><strong><center>No Budget found for particular Sub-Function.</center></strong></td></tr>';
			//$('.BudgetInfoForApproval').html(list2);
			}
			},'json');
				/*}
			else
				{
					bootbox.alert("Budget Should be Same Type For Consolidation!!!");
				}
		},'json');*/
			$('.ind').attr('disabled','disabled');

			
			
			
			
				
			}
		
	}
	if(action =='Back')
	{
		$( ".createIndent" ).trigger( "click" );

	}
	if(action =='Save')
	{
		
		
		
		var t=false;
		var id_req_asst="0";
		$('.prRequestItemForIndentSelectAll').each(function(index){
			if(this.checked)
			{
				$('select[name=id_grp1'+index+']').removeAttr('disabled','disabled');
				$('select[name=id_sgrp1'+index+']').removeAttr('disabled','disabled');
				t=true;
				if(id_req_asst == '0')
					id_req_asst = $(this).val();
					else
						id_req_asst +='Patel'+ $(this).val();
			}
			
		});
	if(!t)
		{
				bootbox.alert("Please select at least one item then procced.");
		}
	else{
		
		$('input[name="id_req_asst"]').val(id_req_asst);
		
		$('.ind').attr('disabled','disabled');
		var x = $('#submitIndent').serialize();
		
		$.post('Create_Indent',x,function (r)
			{
					
				if(r.data)
					{
						bootbox.alert("Successfully Added.");
						$( ".createIndent" ).trigger( "click" );
					}
					else
					{
						bootbox.alert("Waiting for Approval.");
					}

				
				$('.ind').removeAttr('disabled');	
			},'json');
		
	}
		
			
	}
			}});

}

function changeEventHandlerprodForConsolidate(event,nm_prod) {
	var domElement =$(event.target || event.srcElement);
   /* alert('You like ' + event.target.value + ' ice cream.');*/
	//var nm_prod =event.target.value;
	
	//var nm_prod = $('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val();
	/*console.log(nm_prod);*/
	 $.post('M_Model',{action : 'GetModeldetails', id:nm_prod},function (r){
			
			if(r.data.lengthg>0)
				{
					/*alert("hello");*/
				console.log('pritesh'+r.data[0].id_s_assetdiv);
				$('select[name=id_grp1'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_assetdiv + ']').attr('selected',true);
					
					$('select[name=id_sgrp1'+domElement.attr('patelUnPrc')+'] option[value=' + r.data[0].id_s_assetdiv + ']').attr('selected',true);
					$('input[name="id_grp'+domElement.attr('patelUnPrc')+'"]').val(r.data[0].id_assetdiv);
					$('input[name="id_prod'+domElement.attr('patelUnPrc')+'"]').val(r.data[0].id_model);
					$('input[name="id_sgrp'+domElement.attr('patelUnPrc')+'"]').val(r.data[0].id_s_assetdiv);
					
					
				}
				else
				{
					//alert("hii");
					DisplayDropDownDataForGroup("M_Asset_Div","group","CapG",function (status){
				if(status)
				{
					
				}});	
				}
				
	 },'json');
	    
	
	
	
}
function DisplayPurchaseRequestForCreateIndent(servletName,displayContent,createDetails)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
	$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord},function (r){
		
		
		var list= '<tr class="tableHeader">'+
		'<td colspan="6"><center><strong><p class="tableHeaderContent" style="">Create RFQ</p></strong></center></td>'+'</tr>'+

		'<tr class="tableHeader info">'+
		'<td><center><strong>Request Number</strong></center></td>'+
		'<td><center><strong>Request Date</strong></center></td>'+
		'<td><center><strong>Requested By </strong></center></td>'+
		'<td><center><strong>Sub-Function </strong></center></td>'+
		/*'<td><center><strong>Budget Type </strong></center></td>'+*/
			'<td><center><strong>Create RFQ </strong></center></td>'+
	/*	'<td style="width:120px;"><strong></center><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'prRequestForSelectAll\')"> Check All</a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'prRequestForSelectAll\')"> Uncheck All</a></center></strong></td>'+
	*/
	
	'</tr>';
		
		if(r.data.length > 0)
			{
			
			var id_reqArr = new Array();
			var index = new Array();
			for(var i = 0; i < r.data.length ; i++)
			{
			if(id_reqArr.length === 0)
				{
				id_reqArr.push(r.data[i].id_req);
				index.push(i);
				}
			else
				{
				if($.inArray(r.data[i].id_req, id_reqArr) > -1){
					
				}
				else
					{
					id_reqArr.push(r.data[i].id_req);
					index.push(i);
					}
				}
			}
			
			
				for(var i=0;i<id_reqArr.length;i++)
				{
					var t =index[i];
				params = r.data[t];
				
				var budget_type1=params.budget_type;
				if(budget_type1=='non_budget')
				{
					budget_type1='Non-Budget';
				}
				if(budget_type1=='budget')
				{
					budget_type1='Budget';
				}
				if(budget_type1=='adhoc')
				{
					budget_type1='Ad-Hoc';
				}
				list = list + '<tr class="success">'+
				'<td><center>'+params.no_req+'</center></td>'+
				'<td><center>'+params.dt_req+'</center></td>'+
				'<td><center>'+params.nm_emp+'</center></td>'+
				'<td><center>'+params.nm_bu+'</center></td>'+
				/*'<td><center>'+budget_type1+'</center></td>'+*/
				/*'<td ><button name="save" type="button" class="btn btn-primary" onclick="ControlCreateIndent(\'Next\',\'createIndent\',\'displayIndent\',\''+params.id_req+'\')">Next</button></td>'+
				*/	'<td><center><a class="alert" href="#" onclick="ControlCreateIndent(\'Next\',\'createIndent\',\'displayIndent\',\''+params.id_req+'\')"> Preview </a></center>';
												
			//	'<td><strong><center><input type="checkbox" name="createIndent" class="prRequestForSelectAll" value="'+params.id_req+'"/></center></strong></td>'+
							  '</tr>';
				}
				/*list = list + '<tr class="success">'+
								'<td colspan="6"><button name="save" type="button" style="margin-left:475px;" class="btn btn-primary" onclick="ControlCreateIndent(\'Next\',\'createIndent\',\'displayIndent\')">Next</button></td>'+
								'</tr>';*/
				$('.displayPrRequestForcreateindent').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
				$('.displayPrRequestForcreateindent').html(list);
			}
		
	},'json');
			}});
}