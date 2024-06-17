function EditUserFun(servletName,displayContent,createDetails,id,id_loc,userType,id_div)
{
	
SessionCheck(function (ses){
	if(ses)
	{
		$('button[name="create btn"]').addClass('hideButton');
	
		$('button[name="save"]').addClass('hideButton');
		$('button[name="update"]').removeClass('hideButton');
		$('input[name="nm_login"]').attr('readonly','readonly');
						$.post(servletName,{action : 'Edit',id : id},function (r){
							if(r.data)
								{
									$('#'+displayContent).hide();
									$('#'+createDetails).show();
									
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
									
									
									$('#cpwdConfirm').removeClass('error');
									$('#Enabled').val('1');
									$('#Disabled').val('0');
									if(r.data[0].status_user == 1)
										{
											$('#Enabled').val('1');
											$('#Enabled').attr('checked',true);
											$('.disableDate').hide();
											$('.enableDate').show();
										}
									else
										{
											$('#Disabled').val('0');
											$('#Disabled').attr('checked',true);
											$('.disableDate').show();
											$('.enableDate').hide();
											
										}
									
									
									
									$('input[name="action"]').val("Update");
									$('input[name="id"]').val(id);
									
									
								}
							
							
							
						},'json');
						
				/*});
					
					}});*/
			}});
		
		
		


}

function getAllFloor(id_flr)
{
	  $.post('M_Country',{action : 'GetAllFloor' , id_flr : id_flr} , function (r){
			 if(r.data.length > 0)
				 {
				 	var list = '';
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
					$('#floorDataForUserPermission').html(list);
				 }
			 else
				 {
				 $('#floorDataForUserPermission').html('');
				 }
		  },'json');	

}
function getAllSublocation(locIds)
{
	  $.post('M_Loc',{action : 'GetAllSublocation' , locIds : locIds} , function (r){
			 if(r.data.length > 0)
				 {
				 	var list = '';
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
					$('#sublocDataForUser').html(list);
				 }
			 else
				 {
				 $('#sublocDataForUser').html('');
				 }
		  },'json');	

}

function CheckPermission()
{
	$('#typAssetForUser').removeClass('error');
	$('#locDataForUserPermission').removeClass('error');
	$('#sublocDataForUser').removeClass('error');
	
	var t=true;
	var type_user = $('#typeUser').val();
	if(type_user == 'BUHEAD')
		{
			if($('#typAssetForUser').val() == '' || $('#typAssetForUser').val() == null)
				{
					bootbox.alert('Please select at least one asset owner.');
					$('#typAssetForUser').addClass('error');
					$('#typAssetForUser').focus();
					t=false;
				}
		}
	else if(type_user == 'MNGR')
		{
			if($('#typAssetForUser').val() == '' || $('#typAssetForUser').val() == null)
			{
				bootbox.alert('Please select asset owner.');
				$('#typAssetForUser').addClass('error');
				$('#typAssetForUser').focus();
				t=false;
			}
			else if($('#locDataForUserPermission').val() == '' || $('#locDataForUserPermission').val() == null)
			{
				bootbox.alert('Please select at least one location.');
				$('#locDataForUserPermission').addClass('error');
				$('#locDataForUserPermission').focus();
				t=false;
			}
		} 
	else if(type_user == 'EXCTV')
		{
			if($('#typAssetForUser').val() == '' || $('#typAssetForUser').val() == null)
			{
				bootbox.alert('Please select asset owner.');
				$('#typAssetForUser').addClass('error');
				$('#typAssetForUser').focus();
				t=false;
			}
			else if($('#locDataForUserPermission').val() == '' || $('#locDataForUserPermission').val() == null)
			{
				bootbox.alert('Please select at least one location.');
				$('#locDataForUserPermission').addClass('error');
				$('#locDataForUserPermission').focus();
				t=false;
			}
			else if($('#sublocDataForUser').val() == '' || $('#sublocDataForUser').val() == null)
				{
					bootbox.alert('Please select at least one sub location.');
					$('#sublocDataForUser').addClass('error');
					$('#sublocDataForUser').focus();
					t=false;
				}
		}
	else if(type_user == 'SEQRT')
		{
			if($('#locDataForUserPermission').val() == '' || $('#locDataForUserPermission').val() == null)
			{
				bootbox.alert('Please select at least one location.');
				$('#locDataForUserPermission').addClass('error');
				$('#locDataForUserPermission').focus();
				t=false;
			}
			else if($('#sublocDataForUser').val() == '' || $('#sublocDataForUser').val() == null)
				{
					bootbox.alert('Please select at least one sub location.');
					$('#sublocDataForUser').addClass('error');
					$('#sublocDataForUser').focus();
					t=false;
				}
		
		}
			
return t;
}

function HideShowUserPermission(userType)
{
	var user = userType.value;
if(user == undefined)
	user = userType;

var user_type_name = $("#typeUser option:selected").text();
	$('input[name="user_type_name"]').val(user_type_name);
	
	if(user == 'SUPER' || user == 'LGSTC' || user == 'FINC')
		{
			$('.uPermission').hide();
			$('.owner').hide();
			$('.uLocation').hide();
			$('.uSubLocation').hide();
		}
	else if(user == 'BUHEAD')
		{
			$('.uPermission').show();
			$('.owner').show();
			$('.uLocation').hide();
			$('.uSubLocation').hide();
		}
	else if(user == 'MNGR')
		{
			$('.uPermission').show();
			$('.owner').show();
			$('.uLocation').show();
			$('.uSubLocation').hide();
		}
	else if(user == 'EXCTV')
		{
			$('.uPermission').show();
			$('.owner').show();
			$('.uLocation').show();
			$('.uSubLocation').show();
		}
	else if(user == 'SEQRT')
		{
			$('.uPermission').show();
			$('.owner').hide();
			$('.uLocation').show();
			$('.uSubLocation').show();
		}
	else
		{
			$('.uPermission').show();
			$('.owner').show();
			$('.uLocation').show();
			$('.uSubLocation').show();
			
		}
	
	
}


function DataForCreatingUser()
{
	$.post('M_Emp_User',{action : 'DisplayEmpForLogin'},function (r){
			if(r.data)
				{
					var list= '<option value=""> Select</option>';
					
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
					$('#empForUser').html(list);
				}
			else
				{
					bootbox.alert("Try again.");
				}
			
		},'json');
	}

function ConfirmPasswordCheck(){
	
	var cpwd = $('#cpwdConfirm').val();
	var pwd = $('#pwdConfirm').val();
	t=true;
	$('#cpwdConfirm').removeClass('error');
	
		if(cpwd != pwd)
			{
				t=false;
				bootbox.alert('Password should be same');
				$('#cpwdConfirm').val('');
				$('#pwdConfirm').val('');
				$('#pwdConfirm').addClass('error');
				$('#pwdConfirm').focus();
			}
	
		return t;
	}