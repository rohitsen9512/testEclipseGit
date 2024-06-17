$(function(){
	
	$( "#menu" ).menu();
	
});
 
function AddDepreciationConfig(formName , type_id)
{	
	var t =true;
	$('#capitalization_comp2').removeClass('error');
	var gg = $('#capitalization_comp2');
	if(gg.val() == null)
			{
				alert('Mandatory field');
				$('#capitalization_comp2').addClass('error');
				$('#capitalization_comp2').focus();
				t=false;
				exit(0);
			}
	
	$('.common-validation').each(function (){
		$(this).removeClass('error');
		if($(this).val() == '')
			{
			alert('Mandatory field');
			$(this).addClass('error');
			$(this).focus();
			t=false;
			exit(0);
			}
		
		var str = $(this).val();
		var str = '1' + str;
	    var str = str.replace(/[\(\)\.\-\/\ ]/g, '');
	    
	        if (parseInt(str) != str) {
	        	t=false;
	            alert('Invalid number');
	            $(this).addClass('error');
				$(this).focus();
	            exit(0);
	        }
		
	});
	
	if(t)
		{
		var x = $('#'+formName).serialize();
			$.post('D_Dep_Config',x,function (r)
					{
				
						if(r.data)
							{
								DepConfigForDiplayData(type_id);
								bootbox.alert(" Inserted Successfully.");
							}
							else
								{
									
									bootbox.alert("Data is not inserted in data base please try again.");
								}
					},'json');
			
		}

}


function DropDownForDeprnType(servletName,dropDownId,YearOnly)
{
	
$.post(servletName,{action : 'DropDownResult' , DepnType :'Monthly' , YearOnly :YearOnly},function (r){
		
		if(r.data)
			{
			
				var list= '<option value=""> Select</option>';
				if(YearOnly == "YearOnly")
					{
						for(var i = 0; i < r.data.length ; i++)
						{
							list = list + '<option value="'+r.data[i].std_finance+'"> '+r.data[i].std_finance+'</option>';
						}
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
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		
	},'json');


}



function DepConfigForDiplayData(type_id)
{
	
$.post('D_Dep_Config',{action : 'Display' , type_id : type_id},function (r){
				
				if(r.data)
					{
						for(var i = 0; i < r.data.length ; i++)
						{
							for (var key in r.data[i])
					        {
								if($('select[name='+key+']').is("select"))
								{
									$('option[value=' + r.data[i][key] + ']').attr('selected',true);
									
								}
							else
								{
									$('input[name='+key+']').val(r.data[i][key]);
								}
					        }
							 
						}
						
						var res = (r.data[0].capitalization_comp).split(",");;
						for(var i = 0 ; i < res.length ; i++)
							{
								$('option[value=' + res[i] + ']').attr('selected',true);
							}
						
						}
				else
					{
						
						$('input[name="write_off_value"]').val('');
						$('input[name="dep_stop"]').val('');
						
					}
					
		},'json');
			
}


function DropdownResultForFinanceYear(){
	
	$.post('M_Financial_Year',{action : 'DropDownResult'},function (r){
		var list= '<option value=""> Select</option>';
			
		if(r.data)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				
				list = list + '<option value="'+r.data[i].id_fincance+'"> '+r.data[i].std_finance+' / '+r.data[i].end_finance+'</option>';
				}
			
				$('#financeYear').html(list);
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		
	},'json');
}

function YearlyDepreciation()
{
	




}

