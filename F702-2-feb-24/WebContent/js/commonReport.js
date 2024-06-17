function SEZAnnexReport()
{
	var t=true;
	
			$('input[name="startdate"]').removeClass('error');
			$('input[name="enddate"]').removeClass('error');
				if($('input[name="startdate"]').val() == '')
					{
					t=false;
					$('input[name="startdate"]').focus();
					$('input[name="startdate"]').addClass('error');
					bootbox.alert('Please select start date');
					}

				else if($('input[name="enddate"]').val() == '')
					{
					t=false;
					$('input[name="enddate"]').focus();
					$('input[name="enddate"]').addClass('error');
					bootbox.alert('Please select enddate date');
					}
	return t;
}

function ControleStoreAsset()
{
	
	var id_grp = $('select[name="id_grp"]').val();
	var id_sgrp = $('select[name="id_sgrp"]').val();
	var t=false;
		if(id_grp == 'all')
			{
				t = true;
			}
		else if(id_grp != '')
		{
			t = true;
		}
		else 
			t=false;
		if(!t)
			{
			bootbox.alert('Please select at least group');
			}
		return t;
			
}

function ControleEmployee()
{
	
var to_assign = $('select[name="to_assign"]').val();
var t=false;

if(to_assign == 'all')
{
	t = true;
}
else if(to_assign != '')
{
t = true;
}
else 
t=false;
if(!t)
{
bootbox.alert('Please select at one Employee');
}
return t;
			
}

function ControleLocation()
{
	
	var id_loc = $('select[name="id_loc"]').val();
	var id_sloc = $('select[name="id_sloc"]').val();
	var id_grp = $('select[name="id_grp"]').val();
	var id_sgrp = $('select[name="id_sgrp"]').val();
	var id_flr = $('select[name="id_flr"]').val();
	
	var t=false;
		if(id_loc == 'all' && id_grp == 'all')
			{
				t = true;
			}
		else if(id_loc != '' && id_grp != '')
		{
			t = true;
		}
		else 
			t=false;
		if(!t)
			{
			bootbox.alert('Please select the Mandatory Fields');
			}
		return t;
		}


function ControleProc()
{
	var id_grp = $('select[name="id_grp"]').val();
var t=false;

if(id_grp == 'all')
{
	t = true;
}
else if(id_grp != '')
{
t = true;
}
else 
t=false;
if(!t)
{
bootbox.alert('Please select atleast one Group');
}
return t;
}

function ControleGRN()
{
	var t=true;
	var enddate = $('input[name="enddate"]').val();
	var startdate = $('input[name="startdate"]').val();
	
	$('input[name="startdate"]').removeClass('error');
	$('input[name="enddate"]').removeClass('error');
if(startdate == '')
	{
		t=false;
		alert('Mandatory Field');
		$('input[name="startdate"]').addClass('error');
		$('input[name="startdate"]').focus();
		exit(0);
	}
	if(enddate == '')
	{
		t=false;
		alert('Mandatory Field');
		$('input[name="enddate"]').addClass('error');
		$('input[name="enddate"]').focus();
		exit(0);
	}
	return t;

}
function ControleAMCAndCAL(IdName)
{
	var t=true;
	$('#'+IdName).removeClass('error');
if($('#'+IdName).val() == '')
	{
	t=false;
	$('#'+IdName).addClass('error');
	$('#'+IdName).focus();
	alert('Mandatory Field');
	exit(0);
	}
	return t;
}

function InvoiceDate()
{
	var t=true;
	
			$('input[name="startdate"]').removeClass('error');
			$('input[name="enddate"]').removeClass('error');
				if($('input[name="startdate"]').val() == '')
					{
					t=false;
					$('input[name="startdate"]').focus();
					$('input[name="startdate"]').addClass('error');
					bootbox.alert('Please select start date');
					}

				else if($('input[name="enddate"]').val() == '')
					{
					t=false;
					$('input[name="enddate"]').focus();
					$('input[name="enddate"]').addClass('error');
					bootbox.alert('Please select enddate date');
					}
	return t;
}




