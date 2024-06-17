

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
	
		if(id_loc == 'all' )
			{
				t = true;
			}
		
		else if(id_loc != '')
		{
			t = true;
		}
		else 
			{
			t=false;
			bootbox.alert('Please select the Location Fields');
			return t;
			}
			
	if( id_grp == 'all')
	{
		t = true;
	}

	else if(id_grp != '')
	{
		t = true;
	}
	else 
		{
		t=false;
		bootbox.alert('Please select the Group Fields');
				
		return t;
			}
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

function InvoiceDate()
{
	var t=true;
	alert();
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
