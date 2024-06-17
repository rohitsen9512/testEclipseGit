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

$( "#menu" ).menu();
//$('.employeeUser').addClass('active');
//$('#rightBodyContent').load('Master/employeeUser.jsp');
	
});




function DisplayData(servletName,displayContent,createDetails)
{
	switch(servletName)
	{
	
		case  "M_Country" :
			DisplayCountry(servletName,displayContent,createDetails);
			break;
		case "M_Tax" :
			DisplayTax(servletName,displayContent,createDetails);
			break;
		case  "M_Designation" :
			DisplayDesignation(servletName,displayContent,createDetails);
			break;
		case  "M_Dept" :
			DisplayDepartment(servletName,displayContent,createDetails);
			break;
		case  "M_Loc" :
			DisplayLocation(servletName,displayContent,createDetails);
			break;
		case  "M_Subloc" :
			DisplaySublocation(servletName,displayContent,createDetails);
			break;
		case  "M_Floor" :
			DisplayFloor(servletName,displayContent,createDetails);
			break;
		case  "M_User_Login" :
			DisplayUser(servletName,displayContent,createDetails);
			break;
		case  "Delivery_Master" :
			DisplayDelivery(servletName,displayContent,createDetails);
			break;
		case  "M_Asset_Div" :
			DisplayAssetDiv(servletName,displayContent,createDetails);
			break;
		case  "M_Subasset_Div" :
			DisplaySubassetDiv(servletName,displayContent,createDetails);
			break;
		case  "M_Emp_User" :
			DisplayEmpUser(servletName,displayContent,createDetails);
			break;
		case  "M_Financial_Year" :
			DisplayFinancialYear(servletName,displayContent,createDetails);
			break;
		case  "M_Vendor" :
			DisplayVendor(servletName,displayContent,createDetails);
			break;
		case  "M_Currency" :
			DisplayCurrency(servletName,displayContent,createDetails);
			break;
		case  "M_Division" :
			DisplayDivision(servletName,displayContent,createDetails);
			break;
		case  "M_Cost_Center" :
			DisplayCostCenter(servletName,displayContent,createDetails);
			break;
	}
}



function DisplayCostCenter(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
								
								'<<td><strong>Division Name</strong></td>'+
								'<td><strong>Cost Center Code</strong></td>'+
								'<td><strong>Cost Center Name</strong></td>'+
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_div+'</center></td>'+
									'<td><center>'+params.cd_cc+'</center></td>'+
									'<td><center>'+params.nm_cc+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_cc+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_cc+')"> Delete</a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.costCenterForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}

function DisplayDivision(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Division Code</strong></td>'+
								'<<td><strong>Division Name</strong></td>'+
								
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.cd_div+'</center></td>'+
									'<td><center>'+params.nm_div+'</center></td>'+
									
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_div+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_div+')"> Delete</a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.divisionForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}


function DisplayCurrency(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Currency Name</strong></td>'+
								'<<td><strong>Currency Symbol</strong></td>'+
								
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_curr+'</center></td>'+
									'<td><center>'+params.cd_curr+'</center></td>'+
									
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_curr+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_curr+')"> Delete</a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.currencyForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}

function DisplayVendor(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Vendor Name</strong></td>'+
								'<<td><strong>Vendor Code</strong></td>'+
								
								'<td><strong>Contact Person Name</strong></td>'+
								'<td><strong>Phone Number</strong></td>'+
								'<td><strong>E-mail Id</strong></td>'+
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_ven+'</center></td>'+
									'<td><center>'+params.cd_ven+'</center></td>'+
									
									'<td><center>'+params.nm_contact+'</center></td>'+
									'<td><center>'+params.ct_phone+'</center></td>'+
									'<td><center>'+params.ct_mailid+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_ven+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_ven+')"> Delete</a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.vendorForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}

function DisplayFinancialYear(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
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
												'<td style="border-left: 0px;"><strong style="margin-left: 4px;"><a href="#">Modify </a></strong></td>'+
												'<td style="border-left: 0px;"><strong style="margin-left: 40px;"><a href="#">Delete </a></strong></td>'+
												'<td style="border-left: 0px;"><strong style="margin-left: 27px;"><a href="#">Current </a></strong></td>'+
											  '</tr>'+
										'</table>'+
								'</td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td ><center>'+params.std_finance+'</center></td>'+
									'<td><center>'+params.end_finance+'</center></td>'+
									'<td><center>'+params.stdt_first+'</center></td>'+
									'<td><center>'+params.endt_first+'</center></td>'+
									'<td><center>'+params.stdt_second+'</center></td>'+
									'<td><center>'+params.endt_second+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_fincance+')">'+ 
									'Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_fincance+')">'+ 
									'Delete</a><a class="alert" href="#" onclick="MakeItCurrent(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_fincance+')">Current</strong></td>'+
							  '</tr>';
				}
			
			
				$('.dataForFinanceYear').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}



function DisplayEmpUser(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Employee Name</strong></td>'+
								'<<td><strong>Employee Code</strong></td>'+
								'<<td><strong>Official Mail ID</strong></td>'+
								'<td><strong>Designation</strong></td>'+
								'<td><strong>Status</strong></td>'+
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><center>'+params.cd_emp+'</center></td>'+
									'<td><center>'+params.id_emp+'</center></td>'+
									'<td><center>'+params.nm_design+'</center></td>'+
									'<td><center>'+params.status_emp+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_emp_user+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_emp_user+')"> Delete</a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.empUserForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}

function DisplaySubassetDiv(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Group Name</strong></td>'+
								'<<td><strong>Sub Group Name</strong></td>'+
								'<<td><strong>Sub Group Code</strong></td>'+
								'<td><strong>Asset Prefix</strong></td>'+
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_assetdiv+'</center></td>'+
									'<td><center>'+params.nm_s_assetdiv+'</center></td>'+
									'<td><center>'+params.cd_s_assetdiv+'</center></td>'+
									'<td><center>'+params.pre_asset+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_s_assetdiv+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_s_assetdiv+')"> Delete</a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.subassetForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}


function DisplayAssetDiv(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
			
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Group Name</strong></td>'+
								'<<td><strong>Group Code</strong></td>'+
								'<td><strong>Group Description</strong></td>'+
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_assetdiv+'</center></td>'+
									'<td><center>'+params.cd_assetdiv+'</center></td>'+
									'<td><center>'+params.ds_assetdiv+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_assetdiv+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_assetdiv+')"> Delete</a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.assetForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}

function DisplayTax(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
			
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Tax Name</strong></td>'+
								'<<td><strong>Tax Code</strong></td>'+
								'<td><strong>Percentage</strong></td>'+
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_tax+'</center></td>'+
									'<td><center>'+params.cd_tax+'</center></td>'+
									'<td><center>'+params.per_tax+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_tax+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_tax+')"> Delete</a></strong></td>'+
							  '</tr>';
				}
			
			
				$('.taxForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}
function DisplayCountry(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Country Code</strong></td>'+
								'<td><strong>Country Name</strong></td>'+
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.cd_country+'</center></td>'+
									'<td><center>'+params.nm_country+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_country+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_country+')"> Delete</a></strong></td>'+
							 '</tr>';
				}
			
			
				$('.dataForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}
function DisplayDesignation(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Designation Code</strong></td>'+
								'<td><strong>Designation Name</strong></td>'+
								'<td><strong><a  href="#">Modify </a><a  href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.cd_design+'</center></td>'+
									'<td><center>'+params.nm_design+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_design+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_design+')"> Delete</a></strong></td>'+
							 '</tr>';
				}
			
			
				$('.designationForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}

function DisplayDepartment(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Department Code</strong></td>'+
								'<td><strong>Department Name</strong></td>'+
								'<td><strong><a  href="#">Modify </a><a  href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.cd_dept+'</center></td>'+
									'<td><center>'+params.nm_dept+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_dept+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_dept+')"> Delete</a></strong></td>'+
							 '</tr>';
				}
			
			
				$('.deptForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}

function DisplayLocation(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				
				var list= '<tr class="tableHeader info">'+
								'<td><strong>State Code</strong></td>'+
								'<td><strong>State Name</strong></td>'+
								'<td><strong><a  href="#">Modify </a><a  href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.cd_loc+'</center></td>'+
									'<td><center>'+params.nm_loc+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_loc+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_loc+')"> Delete</a></strong></td>'+
							 '</tr>';
				}
			
			
				$('.locationForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}

function DisplaySublocation(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Location</strong></td>'+
								'<td><strong>State Code</strong></td>'+
								'<td><strong>State Name</strong></td>'+
								'<td><strong>Country</strong></td>'+
								'<td><strong>STPI Unit</strong></td>'+
								'<td><strong><a  href="#">Modify </a><a  href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.id_loc+'</center></td>'+
									'<td><center>'+params.cd_subl+'</center></td>'+
									'<td><center>'+params.nm_subl+'</center></td>'+
									'<td><center>'+params.country+'</center></td>'+
									'<td><center>'+params.id_stpun_id+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_sloc+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_sloc+')"> Delete</a></strong></td>'+
							 '</tr>';
				}
			
			
				$('.sublocationForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}

function DisplayFloor(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Location</strong></td>'+
								'<td><strong>State</strong></td>'+
								'<td><strong>Floor Name</strong></td>'+
								'<td><strong>Floor Code</strong></td>'+
								'<td><strong><a  href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_loc+'</center></td>'+
									'<td><center>'+params.nm_subl+'</center></td>'+
									'<td><center>'+params.nm_flr+'</center></td>'+
									'<td><center>'+params.cd_flr+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_flr+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_flr+')"> Delete</a></strong></td>'+
							 '</tr>';
				}
			
			
				$('.floorForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}

function DisplayUser(servletName,displayContent,createDetails)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
				
				var list= '<tr class="tableHeader info">'+
								'<td><strong>Employee Name</strong></td>'+
								'<td><strong>Login Name</strong></td>'+
								'<td><strong>User Type</strong></td>'+
								'<td><strong>Status</strong></td>'+
								'<td><strong><a  href="#">Modify </a><a  href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.nm_emp+'</center></td>'+
									'<td><center>'+params.nm_login+'</center></td>'+
									'<td><center>'+params.type_user+'</center></td>'+
									'<td><center>'+params.status_user+'</center></td>'+
									'<td><strong><a class="alert" href="#" onclick="EditFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_log_user+')"> Modify </a><a class="alert" href="#" onclick="DeleteFun(\''+servletName+'\',\''+displayContent+'\',\''+createDetails+'\','+params.id_log_user+')"> Delete</a></strong></td>'+
							 '</tr>';
				}
			
			
				$('.userForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}

function DisplayDelivery(servletName)
{
	$.post(servletName,{action : 'Display'},function (r){
		
		if(r.data)
			{
			
				var list= '<tr class="tableHeader info">'+
								'<<td><strong>Delivery Code</strong></td>'+
								'<td><strong>Delivery Name</strong></td>'+
								'<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>'+
							'</tr>';
				
				for(var i = 0; i < r.data.length ; i++)
				{
				
				params = r.data[i];
				
				
				list = list + '<tr class="success">'+
									'<td><center>'+params.delivery_code+'</center></td>'+
									'<td><center>'+params.delivery_name+'</center></td>'+
									'<td><strong><a href="#" onclick="EditFun('+params.delivery_id+')"> Modify </a><a href="#" onclick="DeleteFun('+params.delivery_id+')">/ Delete</a></strong></td>'+
							 '</tr>';
				}
			
			
				$('.deliveryForDisplay').html(list);
			
			}
		else
			{
				alert("Try again.");
			}
		
	},'json');

}





