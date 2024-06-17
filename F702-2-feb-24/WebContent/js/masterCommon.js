$(function (){
	
	$('#createEmployee').hide();
	
	var ieVersion = (function(){
	    var undef=0, v = 3, div = document.createElement('div');

	    while (
	        div.innerHTML = '<!--[if gt IE '+(++v)+']><i></i><![endif]-->',
	        div.getElementsByTagName('i')[0]
	    );

	    return v> 4 ? v : undef;
	}());
	
	
	if(ieVersion !=0)
		{
		
			
			
		}


//$('.employeeUser').addClass('active');
//$('#rightBodyContent').load('Master/employeeUser.jsp');
	
});

function DisplaySubDropDownDataForReport(ids,dropDownId,servletName)
{
	if(ids.value == 'all' || ids.value == '')
	{
	
		$('#'+dropDownId).html('<option value=""> Select</option>');
		$('#'+dropDownId).focus();
	}
	else
		{
		
		
	$.post(servletName,{action:'DropDownResult' , id : ids.value},function (r){
		
		if(r.data)
			{
			var list= '<option value=""> Select</option>';
				if(r.data.length == 0)
					{
						bootbox.alert("No sub value is there. Please select other.");
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
						
					}
				else
					{
							for(var i = 0; i < r.data.length ; i++)
							{
							
							
							for (var key in r.data[i])
					        {
								id=r.data[i][key];
								break;
					        }
							for (var key in r.data[i])
					        {
								val=r.data[i][key];
								
					        }
							list = list + '<option value="'+id+'"> '+val+'</option>';
							}
					}
			
				$('#'+dropDownId).html(list);
				$('#'+dropDownId).focus();
			}
		else
			{
				bootbox.alert(" Please select the value.");
			}
		
	},'json');
		}
}
function SubDropDownDataDisplayForReport(ids,dropDownId,servletName)
{
	if(dropDownId == 'subGroupDataForMaintenanceReport')
		$('#assetIdForAssetMaintenanceReport').html('<option value=""> Select</option>');
		
	if(ids.value == 'all' || ids.value == '' || ids.value == 'All')
		{
		
			$('#'+dropDownId).html('<option value=""> Select</option>');
			$('#'+dropDownId).focus();
		}
	else
		{
			$.post(servletName,{action:'DropDownResult' , id : ids.value},function (r){
				
				if(r.data)
					{
					var list= '<option value=""> Select</option>';
						if(r.data.length == 0)
							{
								bootbox.alert("No sub value is there. Please select other.");
								$('#'+dropDownId).html(list);
								$('#'+dropDownId).focus();
							}
						else
							{
							
									
									
									for(var i = 0; i < r.data.length ; i++)
									{
									
									
									for (var key in r.data[i])
							        {
										id=r.data[i][key];
										break;
							        }
									for (var key in r.data[i])
							        {
										val=r.data[i][key];
										
							        }
									list = list + '<option value="'+val+'"> '+id+'</option>';
									}
							}
					
						$('#'+dropDownId).html(list);
						$('#'+dropDownId).focus();
					}
				else
					{
						bootbox.alert("Try again.");
					}
				
			},'json');
		}
}


function DisplayData(servletName,displayContent,createDetails)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
				
	switch(servletName)
	{
		
		case  "M_Country" :
			DisplayCountry(servletName,displayContent,createDetails);
			break;
			
		case  "M_User_Type" :
		
			DisplayUsertype(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('UsertypeForDisplay','Users_List');
				}});
			break;
		case "M_Tax" :
			DisplayTax(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('taxForDisplay','Tax_List');
				}});
			break;
			case "M_Group" :
			DisplayMgroup(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('MgroupForDisplay','Group_List');
				}});
			break;
		case  "M_Designation" :
			DisplayDesignation(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('designationForDisplay','Designation_List');
				}});
			break;
		case  "M_Dept" :
			DisplayDepartment(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('deptForDisplay','Department_List');
				}});
			break;
		case  "M_StorageDetail" :
			DisplayStorage(servletName,displayContent,createDetails);
			break;
		case  "M_Loc" :
			DisplayLocation(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('locationForDisplay','Location_List');
				}});
			break;
	
		case  "M_Subloc" :
			DisplaySublocation(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('SublocationForDisplay','Sub_Location_List');
				}});
			break;
		 case  "M_Source" :
			DisplaySource(servletName,displayContent,createDetails,function (status){ 
			if(status){
					getButtonsForListView('srcForDisplay','Src_List');
				}});
				break;
		case  "M_Floor" :
			DisplayFloor(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('floorForDisplay','Floor_List');
				}});
			break;
		case  "M_BU" :
			DisplayBU(servletName,displayContent,createDetails);
			break;
		case  "M_Team" :
			DisplayTeam(servletName,displayContent,createDetails);
			break;
		case  "M_Building" :
			DisplayBuilding(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('buildingForDisplay','Building_List');
				}});
			break;
		case  "M_S_Function" :
			DisplaySubFunction(servletName,displayContent,createDetails);
			break;
		case  "M_Model" :
			DisplayModel(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('ModelForDisplay','Model_List');
				}});
			break;
		case  "M_User_Login" :
			DisplayUser(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('userForDisplay','User_Login_List');
				}});
			break;
		
		case  "M_Asset_Div" :
			DisplayAssetDiv(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('assetForDisplay','Category_List');
				}});
			break;
		case  "M_Subasset_Div" :
			DisplaySubassetDiv(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('subassetForDisplay','Sub_Category_List');
				}});
			break;
		case  "M_Emp_User" :
			DisplayEmpUser(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('empUserForDisplay','Employee_List');
				}});
			break;
		case  "M_Financial_Year" :
			DisplayFinancialYear(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('buildingForDisplay','Building_List');
				}});
			break;
		case  "M_Vendor" :
			DisplayVendor(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('vendorForDisplay','Vendor_List');
				}});
			break;
		case  "M_Currency" :
			DisplayCurrency(servletName,displayContent,createDetails);
			break;
		case  "M_Division" :
			DisplayDivision(servletName,displayContent,createDetails);
			break;
		case  "M_Cost_Center" :
			DisplayCostCenter(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('costCenterForDisplay','Cost_Center_List');
				}});
			break;
		case  "M_CG" :
			DisplayCG(servletName,displayContent,createDetails);
			break;
		case  "M_User_Access" :
			DisplayUserAccess(servletName,displayContent,createDetails,function (status){ 
				if(status){
					getButtonsForListView('userpermissionForDisplay','User_Permission_Details_List');
				}});
			break;	
		case  "M_Unit" :
			DisplayUnit(servletName,displayContent,createDetails);
			break;
		case  "M_Prod_Cart" :
			DisplayItems(servletName,displayContent,createDetails);
			break;
		case  "M_Prod_Consumable" :
			DisplayItems(servletName,displayContent,createDetails);
			break;
		case  "M_Consumable_Div" :
			DisplayAssetDiv(servletName,displayContent,createDetails);
			break;
		case  "M_Consumable_Sub_Div" :
			DisplaySubassetDiv(servletName,displayContent,createDetails);
			break;
		case  "M_Budget" :
			DisplayBudget(servletName,displayContent,createDetails);
			break;
		case  "M_Terms_Conditions" :
			DisplayTermsConditions(servletName,displayContent,createDetails);
			break;
		case  "M_Exchange_Rate" :
			DisplayExchangeRate(servletName,displayContent,createDetails);
			break;
		case  "M_Delivery" :
			DisplayDelivery(servletName,displayContent,createDetails);
			break;
		case  "M_Warranty" :
			DisplayWarranty(servletName,displayContent,createDetails);
			break;
	  
	}
			}});
}

function DisplayWarranty(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		var list= '<thead><tr class="new">'+
		'<td><center><strong>Warranty Name</strong></center></td>'+
		'<td><center><strong>Warranty Symbol</strong></center></td>'+
		
		'<td style="width: 205px;"><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tbody><tr>'+
									'<td><center>'+params.nm_warr+'</center></td>'+
									'<td><center>'+params.cd_warr+'</center></td>'+
									
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_warr+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_warr+')"> <i class="fas fa-trash-alt"></i></a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.warrantyForDisplay').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.warrantyForDisplay').html(list);
			}
		
	},'json');

}
function DisplayMyAsset(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'showAssset'},function (r){
		var list= '<thead><tr class="new">'+
		'<th><center><strong>Asset Name</strong></center></th>'+
		'<th><center><strong>Asset ID</strong></center></th>'+
			'<th><center><strong>Asset Ref No.</strong></center></th>'+
			'<th><center><strong>Serial No..</strong></center></th>'+
		'<th><center><strong>Type</strong></center></th>'+
		'<th><center><strong>Remark</strong></center></th>'+
		//'<th style="width: 205px;"><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></th>'+
	'</tr>';
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tbody><tr>'+
									'<td><center>'+params.ds_pro+'</center></td>'+
									'<td><center>'+params.id_wh_dyn+'</center></td>'+
									'<td><center>'+params.appNo+'</center></td>'+
									'<td><center>'+params.serial_no+'</center></td>'+
									'<td><center>'+params.typ_asst+'</center></td>'+
									'<td><center>'+params.rmk_asst+'</center></td>'+
									//'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_warr+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_warr+')"> <i class="fas fa-trash-alt"></i></a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.MyAssetForDisplay').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.MyAssetForDisplay').html(list);
			}
		
	},'json');

}

function DisplayDelivery(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		var list= '<thead><tr class="new">'+
		'<td><center><strong>Payment Name</strong></center></td>'+
		'<td><center><strong>Payment Symbol</strong></center></td>'+
		
		'<td style="width: 205px;"><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tbody><tr>'+
									'<td><center>'+params.nm_dlvry+'</center></td>'+
									'<td><center>'+params.cd_dlvry+'</center></td>'+
									
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_dlvry+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_dlvry+')"> <i class="fas fa-trash-alt"></i></a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.deliveryForDisplay').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.deliveryForDisplay').html(list);
			}
		
	},'json');

}
function EditEmpFun(servletName,displayContent,createDetails,id,id_loc)
{
	
SessionCheck(function (ses){
		
		if(ses)
			{
	$('button[name="save"]').addClass('hideButton');
	$('button[name="update"]').addClass('hideButton');
	$('button[name="delete"]').addClass('hideButton');
	$('input[name="id_group"]').val(id);
		/*DropDownDataDisplay("M_Loc","locDataForSubLoc",function (status){
			if(status)
				{*/
				
	
		$.post(servletName,{action : 'Edit',id : id},function (r){
				if(r.data)
					{
					$('#'+displayContent).hide();
					$('#'+createDetails).show();
					
					$('button[name="create btn"]').addClass('hideButton');
					
					
						for(var i = 0; i < r.data.length ; i++)
						{
							
							for (var key in r.data[i])
					        {
								
									if($('select[name='+key+']').is("select"))
									{
										
										$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
									}
								else
									{
									
										$('input[name='+key+']').val(r.data[i][key]);
									}
								
					        }
							 
						}
						
						
					//	$('input[name="action"]').val("Update");
						$('input[name="id"]').val(id);
					}
				else
					{
						bootbox.alert("Try again.");
					}
					
					//
					var sts=$('input[name="action"]').val();
	if(sts=="Save")
	{
		$('#DisplayEmpForBulkInstall').show();
				var searchWord = $('input[name="search"]').val();
			
	$.post('M_Group',{action : 'Search'  ,searchWord : searchWord} , function (r){
		//console.log(r)
					var list="";
					 if(r.data.length > 0)
					{
						list = list + 
											'<tr class="success">'+
						'<td colspan="6"><button type="button" style="margin-left: 450px;"  class="btn btn-primary installUnInstall" onclick="checkBoxValidationForGroup(\'bulkinstallEmp\')">ADD</button>'+
						'</td></tr>';
						list= list+ '<tr class="tableHeader info">'+
					
					'<td><strong><center>Name of Employee</center></strong></td>'+
					'<td><strong><center>Mail</center></strong></td>'+
					'<td style="width: 125px;"><center><strong><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'bulkinstallAssetForSelectAll\')"> Check </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'bulkinstallAssetForSelectAll\')"> Uncheck </a></strong></center></td>'+
				'</tr>';
				for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.nm_emp+'</center></td>'+
						'<td><center>'+params.id_emp+'</center></td>'+
						'<td><strong><center><input type="checkbox"  name="bulkInstallEmp" class="bulkinstallAssetForSelectAll checkboxgroupasign" value="'+params.id_emp_user+'"/></center></strong></td>'+
						'</tr>'
							}
						
						
					}
					 else
						 {list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
					}
					
					 $('.DisplayEmpForBulkInstall').html(list);
					 //$('.UserDataForBulkInstallAsset').html(list);
					 
					 },'json');
					 }
					 else
					 {
						$('#DisplayEmpForBulkUninstall').show();
						var searchWord = $('input[name="search"]').val();
			
	$.post('M_Group',{action : 'GroupEdit',id:id  ,searchWord : searchWord} , function (r){
		//console.log(r)
					var list="";
					 if(r.data.length > 0)
					{
						list = list + 
											'<tr class="success">'+
						'<td colspan="6"><button type="button" style="margin-left: 450px;"  class="btn btn-primary installUnInstall" onclick="checkBoxValidationForGroup(\'bulkuninstallEmp\')">Delete</button>'+
						'</td></tr>';
						list= list+ '<tr class="tableHeader info">'+
					
					'<td><strong><center>Name of Employee</center></strong></td>'+
					'<td><strong><center>Mail</center></strong></td>'+
					'<td style="width: 125px;"><center><strong><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'bulkinstallAssetForSelectAll\')"> Check </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'bulkinstallAssetForSelectAll\')"> Uncheck </a></strong></center></td>'+
				'</tr>';
				for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						
						
						list = list + '<tr class="success">'+
						'<td><center>'+params.nm_emp+'</center></td>'+
						'<td><center>'+params.id_emp+'</center></td>'+
						'<td><strong><center><input type="checkbox"  name="bulkUninstallEmp" class="bulkinstallAssetForSelectAll checkboxgroupassign" value="'+params.id_emp_user+'"/></center></strong></td>'+
						'</tr>'
							}
						
						
					}
					 else
						 {list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
					}
					
					 $('.DisplayEmpForBulkUninstall').html(list);
					 //$('.UserDataForBulkInstallAsset').html(list);
					 
					 },'json');
					}

		},'json')
		
		
		
		.fail(function(jqXHR,status)
			    {
			    });
		
				//}});
	
			}
		
		
	
});
}
function checkBoxValidationForGroup(FormName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
				
				 

	if(FormName == "bulkinstallEmp")
		{ 
			t=false;
			$('.checkboxgroupasign:checked').each(function(){
				if(this.checked)
				{
				t=true	
	         }
	        
			

});

	 
		if(t==true){
					var x = $('#bulkinstallEmp').serialize();
		//console.log(x);
		$.post('M_Group',x,function (r)
				{
			
				if(r.data ==1)
				bootbox.alert("Done successfully.");
				window.location = $('.GroupAssignment_event').attr('href');
				},'json');
				}
				else{
					
					alert("Please Select atleast one.");
				}
		 
			
		

}
 if(FormName == "bulkuninstallEmp")
				{ 
		t=false;
			$('.checkboxgroupassign:checked').each(function(){
				if(this.checked)
				{
				t=true	
	         }
 });
  
		if(t==true){
					var x = $('#bulkuninstallEmp').serialize();
		console.log(x);
		$.post('M_Group',x,function (r)
				{
			
				if(r.data ==1)
				bootbox.alert("Done successfully.");
				 window.location = $('.EditAssignEmployee_event').attr('href');
				},'json');
				
				}
				else{
					
					alert("Please Select atleast one.");
				}
		 
			
		

}

}});}
function DisplayExchangeRate(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Currency Name</center></strong></td>'+
		/*'<td><strong><center>Date</center></strong></td>'+*/
		'<td><strong><center>Rate</center></strong></td>'+
		'<td style="width: 220px;"><strong><center><a href="#">Modify </a><a href="#">/ Delete</a></center></strong></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				 
				
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				
				list = list + '<tbody><tr>'+
									'<td><center>'+params.nm_curr+'</center></td>'+
									/*'<td><center>'+params.month_year+'</center></td>'+*/
									'<td><center>'+params.rate+'</center></td>'+
									'<td><center><strong><center><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_exng_rate+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_exng_rate+')"> <i class="fas fa-trash-alt"></i></a></center></center></strong></td>'+
							 '</tr>';
				}
				$('.exchangeRateForDisplay').html(list);
			}
		else
		{
			list +='<tr><td colspan="3"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.exchangeRateForDisplay').html(list);
			}
		
	},'json');
}

function DisplayTermsConditions(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Term & Condition Name</center></strong></td>'+
		'<td><strong><center>File Name</center></strong></td>'+ 
		'<td style="width: 220px;"><strong><center></center></strong></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				 
				
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				list = list + '<tbody><tr>'+
									'<td><center>'+params.t_and_c+'</center></td>'+
									/*'<td><center>'+params.month_year+'</center></td>'+*/
									'<td><center>'+params.file_name+'</center></td>'+
									'<td><center><strong><center><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_t_c+')"> Delete</a></center></center></strong></td>'+
							 '</tr>';
				}
				$('.termsconditionsForDisplay').html(list);
			}
		else
		{
			list +='<tr><td colspan="3"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.termsconditionsForDisplay').html(list);
			}
		
	},'json');
}




function DisplayConsumableDiv(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong>Group Name</strong></td>'+
		'<td><strong>Group Code</strong></td>'+
		'<td><strong>Group Description</strong></td>'+
		'<td><strong>Modify / Delete</strong></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
			
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tbody><tr>'+
									'<td><center>'+params.nm_cons_div+'</center></td>'+
									'<td><center>'+params.cd_cons_div+'</center></td>'+
									'<td><center>'+params.ds_cons_div+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_cons_div+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_cons_div+')"> <i class="fas fa-trash-alt"></i></a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.ConsumableForDisplay').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.ConsumableForDisplay').html(list);
			}
		
	},'json');

}


function DisplayItems(servletName,displayContent,createDetails,callback)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		var list= '<thead><tr class="new">'+
		'<td><center><strong>Group Name</strong></center></td>'+
		//'<td><strong>Sub Group Name</strong></td>'+
		'<td><center><strong>Item Name</strong></center></td>'+
		
	'</tr></thead>';
		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tbody><tr>'+
									'<td><center><a class="alert" href="#" onclick="ControlDiv(\'Edit\',\''+displayContent+'\',\''+createDetails+'\',\'\',\''+servletName+'\',\'\',\''+params.asset_consumable+'\','+params.id_prod+','+params.id_grp+')">'+params.nm_assetdiv+'</a></center></td>'+
									//'<td><center>'+params.nm_s_assetdiv+'</center></td>'+
									'<td><center>'+params.nm_prod+'</center></td>'+
									 '</tr>';
				}
			
			if(servletName == 'M_Prod_Consumable')
				$('.citemForDisplay').html('</tbody>'+list);
			else
				$('.itemForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
			if(servletName == 'M_Prod_Consumable')
				$('.citemForDisplay').html('</tbody>'+list);
			else
				$('.itemForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}

function DisplayUnit(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		var list= '<thead><tr class="new">'+
		'<td><strong><Center>Unit Name</Center></strong></td>'+
		'<td><strong><Center>Unit Symbol</Center></strong></td>'+
		'<td><strong><Center>Gate No</Center></strong></td>'+
		'<td style="width: 200px;"><strong><Center><a href="#">Modify </a><a href="#">/ Delete</a></Center></strong></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tbody><tr>'+
									'<td><center>'+params.nm_unit+'</center></td>'+
									'<td><center>'+params.cd_unit+'</center></td>'+
									'<td><center>'+params.no_gate+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_unit+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_unit+')"> <i class="fas fa-trash-alt"></i></a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.unitForDisplay').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.unitForDisplay').html(list);
			}
		
	},'json');

}

function DisplayCG(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		var list= '<thead><tr class="new">'+
		
		'<td><strong><Center>CG Date</Center></strong></td>'+

		'<td><strong><Center>CG Value</Center></strong></td>'+
		'<td><strong><Center>B17 Number</Center></strong></td>'+
		'<td><strong><Center>Expiry Date</Center></strong></td>'+
		'<td style="width: 200px;"><strong><Center><a href="#">Modify </a><a href="#">/ Delete</a></Center></strong></td>'+
	'</tr>';

		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tbody><tr>'+
									'<td><center>'+params.dt_cg+'</center></td>'+
									'<td><center>'+params.value_cg+'</center></td>'+
									'<td><center>'+params.no_b17_number+'</center></td>'+
									'<td><center>'+params.dt_expiry+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_cg+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_cg+')"> <i class="fas fa-trash-alt"></i></a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.cgdataForDisplay').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.cgdataForDisplay').html(list);
			}
		
	},'json');

}


function DisplayCostCenter(servletName,displayContent,createDetails,callback)
{
	var searchWord = $('input[name="search"]').val();
	$.post(servletName,{action : 'Display', searchWord: searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td style="width:8%"><center><strong>S No.</strong></center></td>'+
		/*'<td><center><strong>Department Name</strong></center></td>'+*/
		 
		'<td><center><strong>Cost Center/Project Name</strong></center></td>'+
		'</tr></thead><tbody>';
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
				                    '<td><center>'+(i+1)+'</center></td>'+
									/*'<td><center>'+params.nm_dept+'</center></td>'+*/
									'<td><center><a class="alertlink" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_cc+')">'+params.nm_cc+'<a></center></td>'+
									 '</tr>';
				}
			
			
				$('.costCenterForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.costCenterForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}

function DisplayDivision(servletName,displayContent,createDetails,callback)
{
	$.post(servletName,{action : 'Display'},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Entity Code</center></strong></td>'+
		'<td><strong><center>Entity Name</center></strong></td>'+
		
		'<td style="width: 200px;"><strong><center><a href="#">Modify </a><a href="#">/ Delete</a></center></strong></td>'+
	'</tr></thead>';
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tbody><tr>'+
									'<td><center>'+params.cd_div+'</center></td>'+
									'<td><center>'+params.nm_div+'</center></td>'+
									
									'<td><strong><center><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_div+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_div+')"> <i class="fas fa-trash-alt"></i></a></center></strong></td>'+
							  '</tr>';
				}
			
			
				$('.divisionForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.divisionForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}


function DisplayCurrency(servletName,displayContent,createDetails,callback)
{
	$.post(servletName,{action : 'Display'},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong>Currency Name</strong></td>'+
		'<td><strong>Currency Symbol</strong></td>'+
		
		'<td style="width: 205px;"><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
	'</tr></thead>';
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tbody><tr>'+
									'<td><center>'+params.nm_curr+'</center></td>'+
									'<td><center>'+params.cd_curr+'</center></td>'+
									
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_curr+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_curr+')"> <i class="fas fa-trash-alt"></i></a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.currencyForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.currencyForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}

function DisplayVendor(servletName,displayContent,createDetails,callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display' , searchWord: searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Vendor Name</center></strong></td>'+
		'<td><strong><center>Vendor Code</center></strong></td>'+
		
		'<td><strong><center>Contact Person</center></strong></td>'+
		'<td><strong><center>Mobile Number</center></strong></td>'+
		'</tr></thead><tbody>';
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				if(params.nm_contact == '' || params.nm_contact == undefined) nm_contact = 'NA'; else nm_contact = params.nm_contact;
				if(params.phone == '' || params.phone == undefined) phone = 'NA'; else phone = params.phone;
				/*if(params.nm_contact == undefined || params.nm_contact == '')
					var nm_contact = '--';
				else
					nm_contact = params.nm_contact;*/
					
				list = list + '<tr>'+
									'<td><center><a class="alertlink" href="#" onclick="EditFunVen(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_ven+')">'+params.nm_ven+'</a></center></td>'+
									'<td><center>'+params.cd_ven+'</center></td>'+
									
									'<td><center>'+nm_contact+'</center></td>'+
									'<td><center>'+phone+'</center></td>'+
								  '</tr>';
				}
			
			
				$('.vendorForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.vendorForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}

function DisplayVendorView(servletName,displayContent,callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'DisplayView' , searchWord: searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Vendor Name</center></strong></td>'+
		'<td><strong><center>Vendor Code</center></strong></td>'+
		
		'<td><strong><center>Contact Person</center></strong></td>'+
		'<td><strong><center>Phone Number</center></strong></td>'+
		'<td><strong><center>Status</center></strong></td>'+
		'<td style="width:138px;"><strong><center><a href="#">Modify </a></center></strong></td>'+
	
	'</tr></thead>';
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				if(params.nm_contact == '' || params.nm_contact == undefined) nm_contact = 'NA'; else nm_contact = params.nm_contact;
				if(params.phone == '' || params.phone == undefined) phone = 'NA'; else phone = params.phone;
				
				if(params.approve == '0' ) approve = 'Pending'; else approve = 'Approved';
				list = list + '<tbody><tr>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.cd_ven+'</center></td>'+
									
									'<td><center>'+nm_contact+'</center></td>'+
									'<td><center>'+phone+'</center></td>'+
									'<td><center>'+approve+'</center></td>'+
			'<td style="width: 200px;"><strong><center><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\'displayVendor\',\'createVendor\','+params.id_ven+')"> <i class="fas fa-edit"></i> </a></center></strong></td>'+
		
							  '</tr>';
				}
			
			
				$('.vendorForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.vendorForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}
function DisplayDataApprove(servletName,displayContent,createDetails,callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'DisplayApp' , searchWord: searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Vendor Name</center></strong></td>'+
		'<td><strong><center>Vendor Code</center></strong></td>'+
		
		'<td><strong><center>Contact Person</center></strong></td>'+
		'<td><strong><center>Phone Number</center></strong></td>'+
		
		'<td style="width:138px;"><strong><center><a href="#">Approve </a><a href="#">/ Reject</a></center></strong></td>'+
	'</tr></thead>';
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				if(params.nm_contact == '' || params.nm_contact == undefined) nm_contact = 'NA'; else nm_contact = params.nm_contact;
				if(params.phone == '' || params.phone == undefined) phone = 'NA'; else phone = params.phone;
				/*if(params.nm_contact == undefined || params.nm_contact == '')
					var nm_contact = '--';
				else
					nm_contact = params.nm_contact;*/
					
				list = list + '<tbody><tr>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.cd_ven+'</center></td>'+
									
									'<td><center>'+nm_contact+'</center></td>'+
									'<td><center>'+phone+'</center></td>'+
									
									'<td style="width: 200px;"><strong><center><a class="alert" href="#" onclick="EditFunApp(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_ven+')"> Approve/Reject </a></center></strong></td>'+
							  '</tr>';
				}
			
			
				$('.vendorForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.vendorForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}

function DisplayDataForInfo(servletName,displayContent,createDetails,callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'DisplayVenInfo' , searchWord: searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Vendor Name</center></strong></td>'+
		'<td><strong><center>Vendor Code</center></strong></td>'+
		
		'<td><strong><center>Contact Person</center></strong></td>'+
		'<td><strong><center>Phone Number</center></strong></td>'+
		
		'<td style="width: 125px;"><center><strong><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'GRNDCSelectAll\')"> Check </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'GRNDCSelectAll\')"> Uncheck </a></strong></center></td>'+
	'</tr></thead>';
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				if(params.nm_contact == '' || params.nm_contact == undefined) nm_contact = 'NA'; else nm_contact = params.nm_contact;
				if(params.phone == '' || params.phone == undefined) phone = 'NA'; else phone = params.phone;
				/*if(params.nm_contact == undefined || params.nm_contact == '')
					var nm_contact = '--';
				else
					nm_contact = params.nm_contact;*/
					
				list = list + '<tbody><tr>'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.cd_ven+'</center></td>'+
									
									'<td><center>'+nm_contact+'</center></td>'+
									'<td><center>'+phone+'</center></td>'+
									
						'<td><strong><center><input type="checkbox" name="invoiceCheckbox" class="GRNDCSelectAll" value="'+params.id_ven+'"/></center></strong></td>'+
							  '</tr>';
				}
			
			
						list = list + '<tbody><tr>'+
						'<td colspan="5"><button type="button" style="margin-left: 400px;"  class="btn btn-primary nextButtonForInvoice" onclick="InvoiceCheckBoxValidation(\'updateInvoice\')">Next</button>'+
						'</td></tr>';
				$('.vendorForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.vendorForDisplay').html('</tbody>'+list);
			}
		
	},'json');

}

function InvoiceCheckBoxValidation(FormName)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var t=0,ids='';
			
			$('input[type="checkbox"]').each(function(){
				
				if(this.checked){
					t=1;
					if(ids !='')
						{
						ids+=","+$(this).val();
						}
					
					else
						{
					 ids = $(this).val();
						}
				}
						
				
			});
	
	if(t == 0)
	{
		bootbox.alert("Please select at least one record then procced.");
	}
	else
		{

				$('button[name="save"]').removeClass('hideButton');
				$('button[name="update"]').addClass('hideButton');
				$('#displayVendor').hide();
				$('#createVendor').show();
				$('input[name="id"]').val(ids);
		}
		
	}});
}
function DisplayFinancialYear(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		var list= '<thead><tr class="new">'+
		'<td colspan="2"> <strong style="margin-left: 50px;">Financial Year</strong>'+
				'<table align="center" style="margin-bottom: -8px;">'+
					 '<tr>'+
						'<td style="border-left: 0px;"><center>Start Date</center></td>'+
						'<td >End Date</td>'+
					  '</tr>'+
				'</table>'+
		'</td>'+
		'<td colspan="2"> <strong style="margin-left: 50px;">First Half Year</strong>'+
				'<table align="center" style="margin-bottom: -8px;">'+
					 '<tr>'+
						'<td style="border-left: 0px;">Start Date</td>'+
						'<td >End Date</td>'+
					  '</tr>'+
				'</table>'+
		'</td>'+
		'<td colspan="2"> <strong style="margin-left: 50px;">Second Half Year</strong>'+
				'<table align="center" style="margin-bottom: -8px;">'+
					 '<tr>'+
						'<td style="border-left: 0px;">Start Date</td>'+
						'<td >End Date</td>'+
					  '</tr>'+
				'</table>'+
		'</td>'+
		'<td colspan="2"> <strong style="margin-left: 50px;">Manipulation</strong>'+
				'<table  style="margin-bottom: -8px;">'+
					 '<tr>'+
						'<td style="border-left: 0px;"><strong style="margin-left: 4px;">Modify </strong></td>'+
						'<td style="border-left: 0px;"><strong style="margin-left: 40px;">Delete</strong></td>'+
						'<td style="border-left: 0px;"><strong style="margin-left: 27px;">Current </strong></td>'+
					  '</tr>'+
				'</table>'+
		'</td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				if(params.active_year == 1)
				{
				list = list + '<tr class="warning">'+
									'<td ><center>'+params.std_finance+'</center></td>'+
									'<td><center>'+params.end_finance+'</center></td>'+
									'<td><center>'+params.stdt_first+'</center></td>'+
									'<td><center>'+params.endt_first+'</center></td>'+
									'<td><center>'+params.stdt_second+'</center></td>'+
									'<td><center>'+params.endt_second+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_fincance+')">'+ 
									'<i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_fincance+')">'+ 
									'<i class="fas fa-trash-alt"></i></a><a class="alert" href="#" onclick="MakeItCurrent(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_fincance+')">Current</strong></td>'+
							  '</tr>';
				
				}
				else
					{
					list = list + '<tbody><tr>'+
					'<td ><center>'+params.std_finance+'</center></td>'+
					'<td><center>'+params.end_finance+'</center></td>'+
					'<td><center>'+params.stdt_first+'</center></td>'+
					'<td><center>'+params.endt_first+'</center></td>'+
					'<td><center>'+params.stdt_second+'</center></td>'+
					'<td><center>'+params.endt_second+'</center></td>'+
					'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_fincance+')">'+ 
					'<i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_fincance+')">'+ 
					'<i class="fas fa-trash-alt"></i></a><a class="alert" href="#" onclick="MakeItCurrent(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_fincance+')">Current</strong></td>'+
			  '</tr>';
					}
				}
			
			
				$('.dataForFinanceYear').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.dataForFinanceYear').html(list);
			}
		
	},'json');

}



function DisplayEmpUser(servletName,displayContent,createDetails,callback)
{
	var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display', searchWord:searchWord},function (r){
		 var list= '<thead><tr class="new">'+
			'<td><strong><center>Employee Name</center></strong></td>'+
			'<td><strong><center>Employee ID</center></strong></td>'+
			
			'<td><center><strong>Official Mail ID</strong></center></td>'+
			
			'<td><strong><center>Designation</center></strong></td>'+
			'<td><strong><center>Status</center></strong></td>'+
			'</tr></thead><tbody>';
		if(r.data.length > 0)
			{
			 

for(var i = 0; i < r.data.length ; i++)
{

params = r.data[i];


list = list + '<tr>'+
				'<td><center><a class="alertlink" href="#" onclick="EditFunEmp(\'Edit\',\''+displayContent+'\',\''+createDetails+'\','+params.id_flr+',\''+servletName+'\','+params.id_sloc+','+params.id_building+','+params.id_emp_user+','+params.id_loc+','+params.id_dept+',\'\',\'\')">'+params.nm_emp+'</a></center></td>'+
				'<td><center>'+params.cd_emp+'</center></td>'+
				'<td><center>'+params.id_emp+'</center></td>'+
				'<td><center>'+params.nm_design+'</center></td>'+
				'<td><center>'+params.status_emp+'</center></td>'+
				'</tr>';
}

			
				$('.empUserForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.empUserForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}
function DisplayBudget(servletName,displayContent,createDetails)
{
var id_finance=$('#financialYearForSearch').val();
	$.post(servletName,{action : 'Display',id_finance:id_finance},function (r){
		
		var list= '<thead><tr class="new">'+
		 '<td><strong>SPOC</strong></td>'+
	'<td><strong>Deparment</strong></td>'+
		
'<td><strong>BU</strong></td>'+
	 '<td><strong>Function</strong></td>'+
		'<td><strong>Sub-Function</strong></td>'+
		'<td><center><strong>Group</strong></center></td>'+
		
		'<td><strong>SUB Group</strong></td>'+
	 /*	'<td><strong>Financial Year</strong></td>'+*/
		'<td><strong>Total Budget Allotted</strong></td>'+
		'<td style="width: 105px;"><strong><a href="#">Modify </a></strong></td>'+
	'</tr>';

		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				
				list = list + '<tbody><tr>'+
				'<td><center>'+params.nm_emp+'</center></td>'+
				'<td><center>'+params.nm_dept+'</center></td>'+
				'<td><center>'+params.nm_cc+'</center></td>'+
				'<td><center>'+params.nm_s_function+'</center></td>'+
				
				'<td><center>'+params.nm_bu+'</center></td>'+
				'<td><center>'+params.nm_assetdiv+'</center></td>'+
				'<td><center>'+params.nm_s_assetdiv+'</center></td>'+
				/*'<td><center>'+params.std_finance+'<b> - </b>'+params.end_finance+'</center></td>'+*/
				'<td><center>'+params.annual_bud+'</center></td>'+
				'<td><strong><a class="alert" href="#" onclick="EditFunBudget(\'Edit\',\''+displayContent+'\',\''+createDetails+'\',\''+servletName+'\','+params.id_assetdiv+','+params.id_s_assetdiv+','+params.id_bud+','+params.id_dept+','+params.id_cc+','+params.id_s_function+','+params.id_bu+')"> <i class="fas fa-edit"></i> </a></strong></td>'+
		  '</tr>';
				}
			
				$('.budgetdataForDisplay').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.budgetdataForDisplay').html(list);
			}
		
	},'json');

}

function DisplaySubassetDiv(servletName,displayContent,createDetails,callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	var type = $('input[name="type"]').val();
	$.post(servletName,{action : 'Display',type:type,searchWord:searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Category</center></strong></td>'+
		'<td><strong><center>Sub-Category</center></strong></td>'+
		'<td><strong><center>Sub-Category Code</center></strong></td>'+
		
		'</tr></thead><tbody>';
		
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				list = list + '<tr>'+
									'<td><center><a class="alertlink" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_s_assetdiv+')">'+params.nm_assetdiv+'</a></center></td>'+
									'<td><center>'+params.nm_s_assetdiv+'</center></td>'+
									'<td><center>'+params.cd_s_assetdiv+'</center></td>'+
									'</tr>';
				}
			
			if(servletName == 'M_Consumable_Sub_Div')
				$('.subConsumableForDisplay').html('</tbody>'+list);
			else
				$('.subassetForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
			if(servletName == 'M_Consumable_Sub_Div')
				$('.subConsumableForDisplay').html('</tbody>'+list);
			else
				$('.subassetForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}
function DisplaySubassetDivView(servletName,displayContent,createDetails)
{
var searchWord = $('input[name="searchWord"]').val();
	
	var type = $('input[name="type"]').val();
	$.post(servletName,{action : 'Display',type:type,searchWord:searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Category</center></strong></td>'+
		'<td><strong><center>Sub-Category</center></strong></td>'+
		'<td><strong><center>Sub-Category Code</center></strong></td>'+
		//'<td style="width: 220px;"><strong><center><a href="#">Modify </a><a href="#">/ Delete</a></center></strong></td>'+
	'</tr>';
		
		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				list = list + '<tbody><tr>'+
									'<td><center>'+params.nm_assetdiv+'</center></td>'+
									'<td><center>'+params.nm_s_assetdiv+'</center></td>'+
									'<td><center>'+params.cd_s_assetdiv+'</center></td>'+
									
									//'<td style="width: 220px;"><strong><center><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_s_assetdiv+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_s_assetdiv+')"> <i class="fas fa-trash-alt"></i></a></center></strong></td>'+
							  '</tr>';
				}
			
			if(servletName == 'M_Consumable_Sub_Div')
				$('.subConsumableForDisplay').html(list);
			else
				$('.subassetForDisplay').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
			if(servletName == 'M_Consumable_Sub_Div')
				$('.subConsumableForDisplay').html(list);
			else
				$('.subassetForDisplay').html(list);
			}
		
	},'json');

}

function DisplayAssetDiv(servletName,displayContent,createDetails,callback)
{
	var searchWord = $('input[name="searchWord"]').val();
	var type = $('input[name="type"]').val();
	$.post(servletName,{action : 'Display',type:type,searchWord:searchWord},function (r){
		
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Product Name</center></strong></td>'+
		//'<td><strong><center>Product Code</center></strong></td>'+
		'<td><strong><center>HSN/SAC-Code</center></strong></td>'+
		'<td><strong><center>Product Prefix</center></strong></td>'+
         '<td><strong><center>Sale Price</center></strong></td>'+
		
		'</tr></thead><tbody>';
		
		if(r.data.length > 0)
			{
			
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				if(params.un_prc_assetdiv==''||params.un_prc_assetdiv=='Null'||params.un_prc_assetdiv=='undefined'){
					
					var unintprice='0.00';
				}
				else{
					var unintprice=params.un_prc_assetdiv;
				}
				list = list + '<tr>'+
									'<td><center><a class="alertlink" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_assetdiv+')">'+params.nm_assetdiv+'</a></center></td>'+
									//'<td><center>'+params.cd_assetdiv+'</center></td>'+
									'<td><center>'+params.hsn_cd_assetdiv+'</center></td>'+
									'<td><center>'+params.asset_prod_prefix+'</center></td>'+
									'<td><center>'+unintprice+'</center></td>'+
								
									 '</tr>';
				}
			
			if(servletName == 'M_Consumable_Div')
				$('.ConsumableForDisplay').html('</tbody>'+list);
			else
				$('.assetForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
			if(servletName == 'M_Consumable_Div')
				$('.ConsumableForDisplay').html('</tbody>'+list);
			else
				$('.assetForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}

function DisplayAssetDivView(servletName,displayContent,createDetails,callback)
{
	var type = $('input[name="type"]').val();
	$.post(servletName,{action : 'Display',type:type},function (r){
		
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Category</center></strong></td>'+
		'<td><strong><center>Category Code</center></strong></td>'+
		'<td><strong><center>Category Description</center></strong></td>'+
		//'<td style="width: 220px;"><strong><center><a href="#">Modify </a><a href="#">/ Delete</a></center></strong></td>'+
	'</tr></thead><tbody>';
		
		if(r.data.length > 0)
			{
			
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
									'<td><center>'+params.nm_assetdiv+'</center></td>'+
									'<td><center>'+params.cd_assetdiv+'</center></td>'+
									'<td><center>'+params.ds_assetdiv+'</center></td>'+
									//'<td style="width: 220px;"><strong><center><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_assetdiv+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_assetdiv+')"> <i class="fas fa-trash-alt"></i></a></center></strong></td>'+
							  '</tr>';
				}
			
			if(servletName == 'M_Consumable_Div')
				$('.ConsumableForDisplay').html('</tbody>'+list);
			else
				$('.assetForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available.</center></strong></td></tr>';
			if(servletName == 'M_Consumable_Div')
				$('.ConsumableForDisplay').html('</tbody>'+list);
			else
				$('.assetForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}


function DisplayMgroup(servletName,displayContent,createDetails,callback)
{

	var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display',searchWord:searchWord},function (r){
		var list= '<thead> <tr class="new">'+
		'<th><strong><center>Group Name</center></strong></th>'+
		'<th><strong><center>Group Code</center></strong></th>'+
		
	'</tr></thead><tbody>';
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
										'<td><center><a class="alertlink" href="#" onclick="EditEmpFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_grp+')">'+params.nm_grp+'</a></center></td>'+
										'<td><center>'+params.cd_grp+'</center></td>'+
									'</tr>';
				}
			
			
				$('.MgroupForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.MgroupForDisplay').html('</tbody>' +list);
			}
		callback(true);
	},'json');

}

function DisplayTax(servletName,displayContent,createDetails,callback)
{
	$.post(servletName,{action : 'Display'},function (r){
		var list= '<thead><tr class="new">'+
		'<th><center><strong>Tax1 Name</strong></center></th>'+
		/*'<td><center><strong>Tax Code</strong></center></td>'+*/
		'<th><center><strong>Tax1 Percentage</strong></center></th>'+
		'<th><center><strong>Tax2 Name</strong></center></th>'+
		'<th><center><strong>Tax2 Percentage</strong></center></th>'+
		'</tr></thead><tbody>';
		
		if(r.data.length > 0)
			{
			
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
									'<td><center><a class="alertlink" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_tax+')">'+params.nm_tax1+'</a></center></td>'+
									/*'<td><center>'+params.cd_tax+'</center></td>'+*/
									'<td><center>'+params.per_tax1+'</center></td>'+
									'<td><center>'+params.nm_tax2+'</center></td>'+
									'<td><center>'+params.per_tax2+'</center></td>'+
									'</tr>';
				}
			
			
				$('.taxForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="4"><b><center>No record(s) is available.</center></b></td></tr>';
				$('.taxForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}
function DisplayCountry(servletName,displayContent,createDetails,callback)
{
	$.post(servletName,{action : 'Display'},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Country Code</center></strong></td>'+
		'<td><strong><center>Country Name</center></strong></td>'+
		'<td style="width: 220px;"><strong><center><a href="#">Modify </a><a href="#">/ Delete</a></center></strong></td>'+
	'</tr></thead><tbody>';

		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
									'<td><center>'+params.cd_country+'</center></td>'+
									'<td><center>'+params.nm_country+'</center></td>'+
									'<td><center><strong><center><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_country+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_country+')"> <i class="fas fa-trash-alt"></i></a></center></center></strong></td>'+
							 '</tr>';
				}
			
			
				$('.dataForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.dataForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}

function DisplayDesignation(servletName,displayContent,createDetails,callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display' , searchWord: searchWord},function (r){
		//alert("length is " + r.data.length);
		var list= '<thead><tr class="new">'+
		'<td style="width:8%"><strong><center>S No.</center></strong></td>'+
		'<td><strong><center>Designation Name</center></strong></td>'+
		'</tr></thead><tbody>';

		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
				'<td><center>'+(i+1)+'</center></td>'+
									'<td><center><a class="alertlink" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_design+')">'+params.nm_design+'</a></center></td>'+
									/*'<td><center>'+params.nm_design+'</center></td>'+*/
									 '</tr>';
				}
			
			
				$('.designationForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.designationForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}
function DisplayUsertype(servletName,displayContent,createDetails,callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display' , searchWord: searchWord},function (r){
		//alert("length is " + r.data.length);
		var list= '<thead><tr class="new">'+
		'<th style="width:8%"><strong>S No.</strong></th>'+
		'<th><strong><center>UserType</center></strong></th>'+
		'</tr></thead><tbody>';

		if(r.data.length > 0)
			{
				
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				if(params.nm_usertype=="Super"){
					list = list + '<tr>'+
				'<td><center>'+(i+1)+'</center></td>'+
									'<td><center>'+params.nm_usertype+'</center></td>'+
									/*'<td><center>'+params.nm_design+'</center></td>'+*/
									 '</tr>';
				}
				else{
					list = list + '<tr>'+
				'<td><center>'+(i+1)+'</center></td>'+
									'<td><center><a class="alertlink" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_usertype+')">'+params.nm_usertype+'</a></center></td>'+
									/*'<td><center>'+params.nm_design+'</center></td>'+*/
									 '</tr>';
				}
				
				}
			
			
				$('.UsertypeForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.UsertypeForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}
function DisplayDepartment(servletName,displayContent,createDetails, callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display' , searchWord: searchWord},function (r){

		var list= '<thead><tr class="new">'+
		'<td style="width:8%"><strong><center>S No.</center></strong></td>'+
					 		'<td><strong><center>Department Name</center></strong></td>'+
						/*'<td><strong><center>Department Code</center></strong></td>'+*/
						'</tr></thead><tbody>';
		
		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
				'<td><center>'+(i+1)+'</center></td>'+
							 		'<td><center><a class="alertlink" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_dept+')">'+params.nm_dept+'</a></center></td>'+
								/*'<td><center>'+params.cd_dept+'</center></td>'+*/
									 '</tr>';
				}
			
			
				$('.deptForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.deptForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}

function DisplayLocation(servletName,displayContent,createDetails,callback)
{
	var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display',searchWord:searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Entity Name</center></strong></td>'+
		'<td><strong><center>Entity Code</center></strong></td>'+
		
	'</tr></thead><tbody>';
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
										'<td><center><a class="alertlink" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_loc+')">'+params.nm_loc+'</a></center></td>'+
										'<td><center>'+params.cd_loc+'</center></td>'+
									'</tr>';
				}
			
			
				$('.locationForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.locationForDisplay').html('</tbody>' +list);
			}
		callback(true);
	},'json');

}

function DisplaySublocation(servletName,displayContent,createDetails, callback)
{
	var searchWord = $('input[name="searchWord"]').val();

	$.post(servletName,{action : 'Display',searchWord:searchWord},function (r){
		var list='<thead><tr class="new">'+
		
		
		'<td><strong><center>City Name</center></strong></td>'+
		'<td><strong><center>Entity</center></strong></td>'+
		'<td><strong><center>City Code</center></strong></td>'+
		
		'<th><strong><center>GSTIN NO</center></strong></th>'+
	
		'<th><strong><center>DL NO</center></strong></th>'+
		
		'</tr></thead><tbody>';
		
		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				list = list + '<tr>'+
					
								
									'<td><center><center><a class= "alertlink" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_sloc+')">'+params.nm_subl+'</a></center></td>'+
									'<td><center>'+params.nm_loc+'</center></td>'+
									'<td><center>'+params.cd_subl+'</center></td>'+
									'<td><center>'+params.gstin_no_city+'</center></td>'+
									'<td><center>'+params.dl_no_city+'</center></td>'+
									
									
								'</tr>';
				}
			
			
				$('.SublocationForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><<b><center>No record(s) is available.</center></b></tr>';
			$('.SublocationForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}

function DisplaySource(servletName,displayContent,createDetails,callback)
{
	var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display',searchWord:searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Source Name</center></strong></td>'+
		'<td><strong><center>Source Code</center></strong></td>'+
		
	'</tr></thead><tbody>';
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
										'<td><center><a class="alertlink" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_src+')">'+params.nm_src+'</a></center></td>'+
										'<td><center>'+params.cd_src+'</center></td>'+
									'</tr>';
				}
			
			
				$('.srcForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.srcForDisplay').html('</tbody>' +list);
			}
		callback(true);
	},'json');

}

function DisplayTeam(servletName,displayContent,createDetails)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display',searchWord:searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>BU</center></strong></td>'+
		'<td><strong><center>Function</center></strong></td>'+
		'<td><strong><center>Sub Function</center></strong></td>'+
		'<td><strong><center>Department </center></strong></td>'+
		'<td><strong><center>Team </center></strong></td>'+
		'<td style="width: 220px;"><strong><center><a  href="#">Modify </a><a href="#">/ Delete</a></center></strong></td>'+
	'</tr></thead><tbody>';

		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
										'<td><center>'+params.nm_dept+'</center></td>'+
									'<td><center>'+params.nm_cc+'</center></td>'+
									'<td><center>'+params.nm_s_function+'</center></td>'+
									'<td><center>'+params.nm_bu+'</center></td>'+
									'<td><center>'+params.nm_team+'</center></td>'+
									'<td style="width: 220px;"><strong><center><a class="alert" href="#" onclick="ControlDiv(\'Edit\',\''+displayContent+'\',\''+createDetails+'\' ,'+params.id_s_function+', \''+servletName+'\', '+params.id_cc+' , '+params.id_dept+', '+params.id_team+', '+params.id_team+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_team+')"> <i class="fas fa-trash-alt"></i></a></center></strong></td>'+
							 '</tr>';
				}
			
			
				$('.teamForDisplay').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.teamForDisplay').html(list);
			}
		
	},'json');

}

function DisplayBU(servletName,displayContent,createDetails,callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display',searchWord:searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Department</center></strong></td>'+
		'<td><strong><center>BU</center></strong></td>'+
		'<td><strong><center>Function</center></strong></td>'+
		'<td><strong><center>Sub-Function</center></strong></td>'+
		'<td style="width: 220px;"><strong><center><a  href="#">Modify </a><a href="#">/ Delete</a></center></strong></td>'+
	'</tr><tbody>';

		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
										'<td><center>'+params.nm_dept+'</center></td>'+
									'<td><center>'+params.nm_cc+'</center></td>'+
									'<td><center>'+params.nm_s_function+'</center></td>'+
									'<td><center>'+params.nm_bu+'</center></td>'+
									'<td style="width: 220px;"><strong><center><a class="alert" href="#" onclick="ControlDiv(\'Edit\',\''+displayContent+'\',\''+createDetails+'\' ,\'\', \''+servletName+'\', '+params.id_cc+' , '+params.id_dept+', '+params.id_bu+', '+params.id_bu+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_bu+')"> <i class="fas fa-trash-alt"></i></a></center></strong></td>'+
							 '</tr>';
				}
			
			
				$('.buForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.buForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}
function DisplayFloor(servletName,displayContent,createDetails,callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display',searchWord:searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Location</center></strong></td>'+
		'<td><strong><center>Sub Location</center></strong></td>'+
		'<td><strong><center>Building</center></strong></td>'+
		'<td><strong><center>Floor Name</center></strong></td>'+
		/*'<td style="width: 220px;"><strong><center><a  href="#">Modify </a><a href="#">/ Delete</a></center></strong></td>'+*/
	'</tr></thead><tbody>';

		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
										'<td><center><a class="alertlink" href="#" onclick="ControlDiv(\'Edit\',\''+displayContent+'\',\''+createDetails+'\' ,\'\', \''+servletName+'\', '+params.id_sloc+' , '+params.id_loc+', '+params.id_flr+', '+params.id_flr+')">'+params.nm_loc+'</a></center></td>'+
									'<td><center>'+params.nm_subl+'</center></td>'+
									'<td><center>'+params.nm_building+'</center></td>'+
									'<td><center>'+params.nm_flr+'</center></td>'+
/*									'<td style="width: 220px;"><strong><center> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_flr+')"> <i class="fas fa-trash-alt"></i></a></center></strong></td>'+
*/							 '</tr>';
				}
			
			
				$('.floorForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.floorForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}

function DisplaySubFunction(servletName,displayContent,createDetails)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display',searchWord:searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Department</center></strong></td>'+
		'<td><strong><center>BU</center></strong></td>'+
		'<td><strong><center>Function Name</center></strong></td>'+
			'<td style="width: 220px;"><strong><center>Modify / Delete</center></strong></td>'+
	'</tr></thead><tbody>';

		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
									'<td><center>'+params.nm_dept+'</center></td>'+
									'<td><center>'+params.nm_cc+'</center></td>'+
									'<td><center>'+params.nm_s_function+'</center></td>'+
									'<td style="width: 220px;"><strong><center><a class="alert" href="#" onclick="ControlDiv(\'Edit\',\''+displayContent+'\',\''+createDetails+'\' ,\'\', \''+servletName+'\', \'\' , '+params.id_dept+', '+params.id_s_function+', '+params.id_cc+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_s_function+')"> <i class="fas fa-trash-alt"></i></a></center></strong></td>'+
							 '</tr>';
				}
			
			
				$('.subfunctionForDisplay').html(list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.subfunctionForDisplay').html(list);
			}
		
	},'json');

}

function DisplayBuilding(servletName,displayContent,createDetails, callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display',searchWord:searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<th><strong><center>Location</center></strong></th>'+
		'<th><strong><center>Sub Location</center></strong></th>'+
		'<th><strong><center>Building Name</center></strong></th>'+
			'</tr></thead><tbody>';

		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
										'<td><center><a class="alertlink" href="#" onclick="ControlDiv(\'Edit\',\''+displayContent+'\',\''+createDetails+'\' ,\'\', \''+servletName+'\', \'\' , \'\', '+params.id_building+', '+params.id_loc+')">'+params.nm_loc+'</a></center></td>'+
									'<td><center>'+params.nm_subl+'</center></td>'+
									'<td><center>'+params.nm_building+'</center></td>'+
									 '</tr>';
				}
			
			
				$('.buildingForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><<b><center>No record(s) is available.</center></b></tr>';
			$('.buildingForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}


function DisplayModel(servletName,displayContent,createDetails,callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display',searchWord:searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Category</center></strong></td>'+
		'<td><strong><center>Sub Category</center></strong></td>'+
		'<td><strong><center>Assets/Model Name</center></strong></td>'+
		'</tr></thead><tbody>';

		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
									'<td width="225px"><center><a class="alertlink" href="#" onclick="ControlDiv(\'Edit\',\''+displayContent+'\',\''+createDetails+'\' ,\'\', \''+servletName+'\', \'\' , \'\', '+params.id_model+' ,'+params.id_assetdiv+')">'+params.nm_assetdiv+'</a></center></td>'+
									'<td width="225px"><center>'+params.nm_s_assetdiv+'</center></td>'+
									'<td width="225px"><center>'+params.nm_model+'</center></td>'+
									/*'<td><center>'+params.cd_model+'</center></td>'+*/
									'</tr>';
				}
			
			
				$('.ModelForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.ModelForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}
function DisplayModelView(servletName,displayContent,createDetails,callback)
{
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display',searchWord:searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Category</center></strong></td>'+
		//'<td><strong><center>Sub Group</center></strong></td>'+
		'<td><strong><center>Assets/Model Name</center></strong></td>'+
		'<td><strong><center>Assets/Model Code</center></strong></td>'+
		//'<td style="width: 220px;"><strong><center><a  href="#">Modify </a><a href="#">/ Delete</a></center></strong></td>'+
	'</tr></thead><tbody>';

		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr>'+
									'<td><center>'+params.nm_assetdiv+'</center></td>'+
									//'<td><center>'+params.nm_s_assetdiv+'</center></td>'+
									'<td><center>'+params.nm_model+'</center></td>'+
									'<td><center>'+params.cd_model+'</center></td>'+
								//	'<td style="width: 220px;"><strong><center><a class="alert" href="#" onclick="ControlDiv(\'Edit\',\''+displayContent+'\',\''+createDetails+'\' ,\'\', \''+servletName+'\', \'\' , \'\', '+params.id_model+' ,'+params.id_assetdiv+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_model+')"> <i class="fas fa-trash-alt"></i></a></center></strong></td>'+
							 '</tr>';
				}
			
			
				$('.ModelForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.ModelForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}
function DisplayUser(servletName,displayContent,createDetails,callback)
{
	
var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display',searchWord:searchWord},function (r){
		var list= '<thead><tr class="new">'+
		'<td><strong><center>Employee Name</center></strong></td>'+
		'<td><strong><center>Login Name</center></strong></td>'+
		'<td><strong><center>User Type</center></strong></td>'+
		'<td><strong><center>Status</center></strong></td>'+
		
	'</tr></thead><tbody>';
		var status='Working';
		var type_user='';
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
					status='Working';
				params = r.data[i];
				
				if(params.status_user == '0')
					{
						status = 'Non Working';
					}
					if(params.type_user == 'NULL')
					{
						type_user = 'NA';
					}
					else{
					type_user=params.type_user;
				}
				list = list + '<tr>'+
									'<td><center><a class="alertlink" href="#" onclick="EditUserFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_log_user+',\''+params.id_flr+'\','+params.id_loc+','+params.id_div+')">'+params.nm_emp+'</a></center></td>'+
									'<td><center>'+params.nm_login+'</center></td>'+
									'<td><center>'+type_user+'</center></td>'+
									'<td><center>'+status+'</center></td>'+
									
							 '</tr>';
				}
			
			
				$('.userForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="5"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.userForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}

function DisplayUserAccess(servletName,displayContent,createDetails,callback)
{
	var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display2', searchWord:searchWord},function (r){
		 var list= '<thead><tr class="new">'+
			'<td><strong><center>User Type</center></strong></td>'+
			
			'<td style="width: 220px;"><strong><center>Modify/ Delete</center></strong></td>'+
		'</tr></thead>';
		if(r.data.length > 0)
			{
			 

for(var i = 0; i < r.data.length ; i++)
{

params = r.data[i];

if(params.type_user=="Super"){
					list = list + '<tbody><tr>'+
				'<td><center>'+params.type_user+'</center></td>'+
				
				'<td style="width: 220px;"><center><strong><a class="alert" href="#" > &nbsp;</center></strong></td>'+
			
		  '</tr>';
				}
				else{
list = list + '<tbody><tr>'+
				'<td><center>'+params.type_user+'</center></td>'+
				
				'<td style="width: 220px;"><center><strong><a class="alert" href="#" onclick="ControlDivAccess(\'Edit\',\''+displayContent+'\',\''+createDetails+'\',\'\',\''+servletName+'\',\'\',\'\',\''+params.type_user+'\',\''+params.id_design+'\',\''+params.id_loc+'\',\''+params.id_subl+'\')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\',\''+params.type_user+'\')"> <i class="fas fa-trash-alt"></i></a></center></strong></td>'+
			
		  '</tr>';
}
}

			
				$('.userpermissionForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.userpermissionForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}
/*function DisplayUserAccess(servletName,displayContent,createDetails,callback)
{
	var searchWord = $('input[name="searchWord"]').val();
	
	$.post(servletName,{action : 'Display2', searchWord:searchWord},function (r){
		 var list= '<thead><tr class="new">'+
			'<td><strong><center>User Type</center></strong></td>'+
			
			'<td style="width: 220px;"><strong><center>Modify/ Delete</center></strong></td>'+
		'</tr></thead>';
		if(r.data.length > 0)
			{
			 

for(var i = 0; i < r.data.length ; i++)
{

params = r.data[i];

if(params.type_user=="Super"){
					list = list + '<tbody><tr>'+
				'<td><center>'+params.type_user+'</center></td>'+
				
				'<td style="width: 220px;"><center><strong><a class="alert" href="#" > &nbsp;</center></strong></td>'+
			
		  '</tr>';
				}
				else{debugger;
list = list + '<tbody><tr>'+
				'<td><center>'+params.type_user+'</center></td>'+
				
				'<td style="width: 220px;"><center><strong><a class="alert" href="#" onclick="ControlDivAccess(\'Edit\',\''+displayContent+'\',\''+createDetails+'\',\'\',\''+servletName+'\',\'\',\'\',\''+params.type_user+'\',\''+params.typ_asst+'\',\''+params.id_design+'\',\''+params.id_loc+'\',\''+params.id_subl+'\')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\',\''+params.type_user+'\')"> <i class="fas fa-trash-alt"></i></a></center></strong></td>'+
			
		  '</tr>';
}
}

			
				$('.userpermissionForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="7"><strong><center>No record(s) is available.</center></strong></td></tr>';
			$('.userpermissionForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}*/
function DisplayStorage(servletName,displayContent,createDetails,callback)
{
	$.post(servletName,{action : 'Display'},function (r){

		var list= '<thead><tr>'+
						'<td><strong><center>Storage Code</center></strong></td>'+
						'<td><strong><center>Storage Name</center></strong></td>'+
						'<td style="width: 220px;"><center><strong><a  href="#">Modify </a><a  href="#">/ Delete</a></center></strong></td>'+
					'</tr></thead>';
		
		if(r.data.length > 0)
			{
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tbody><tr>'+
									'<td><center>'+params.cd_strg+'</center></td>'+
									'<td><center>'+params.nm_strg+'</center></td>'+
									'<td style="width: 220px;"><strong><center><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_strg+')"> <i class="fas fa-edit"></i> </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_strg+')"> <i class="fas fa-trash-alt"></i></a></center></strong></td>'+
							 '</tr>';
				}
			
			
				$('.deptForDisplay').html('</tbody>'+list);
			
			}
		else
			{
			list +='<tr><td colspan="3"><b><center>No record(s) is available.</center></b></td></tr>';
			$('.deptForDisplay').html('</tbody>'+list);
			}
		callback(true);
	},'json');

}



function DisplayLogin(servletName,displayContent,createDetails,id)
{
var searchWord = $('input[name="searchWord"]').val();
	
	var name = $('#empForUser').val();
		$.post(servletName,{action:'DisplayEmp',id : name,searchWord:searchWord},function (r){
				if(r.data)
					{
					
					var userIdTemp = r.data[0]['id_emp'];
					var userId = userIdTemp.split('@')[0];
					
					$('#nm_login').val(userId);
					$('#userEmail').val(r.data[0]['id_emp']);
					$('#user_type').val(r.data[0]['nm_usertype']);
					/*for(var i = 0; i < r.data.length ; i++)
						{
							for (var key in r.data[i])
					        {
										
					        }
							 
						}*/
						
					}
				
				
		},'json')
		.fail(function(jqXHR,status)
			    {
			    });
}
