function onChangeProductName(id)
{
	$.post('A_Add_To_Store',{action : 'DisplaySlNoAndMfr',id : id.value},function (r){
		
		if(r.data)
			{
			
				for(var i = 0; i < r.data.length ; i++)
				{
					for (var key in r.data[i])
			        {
							$('input[name='+key+']').val(r.data[i][key]);
			         
			        }
				}
				
				}
		else
			{
				bootbox.alert("Try again.");
			}
	},'json');


}