
$("#MoveRight").click(function(){
	
	var selectedList='';
	$.each($("#assetDivForRequestQuotation option:selected"), function(){ 
		
		var leftBucketVal=$(this).val();
		var leftBucketText=$(this).text();
		var t=true;
		if($("#assetDivForRequestQuotationSelected") != null){
		
			$.each($("#assetDivForRequestQuotationSelected option"), function(){ 
				if($(this).val() ==leftBucketVal)
					t=false;
			    });
			
			if(t)
				selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
			
		}else{
			selectedList += '<option value="'+leftBucketVal+'">'+leftBucketText+'</option>';
		}
		 $(this).remove();
		 
	    });
	$('#assetDivForRequestQuotationSelected').append(selectedList);
	
	 
});
$("#MoveLeft").click(function(){
	
	var selectedList='';
	$.each($("#assetDivForRequestQuotationSelected option:selected"), function(){ 
		
		selectedList += '<option value="'+$(this).val()+'">'+$(this).text()+'</option>';
		 $(this).remove();
	    });
	$('#assetDivForRequestQuotation').append(selectedList);
	
	 
});

function DisplayDropDownDataForSelectedVendor(servletName,dropDownId,type_ven,no_ind,callback)
{
	var t =false;
	var list='';
	$.post(servletName,{action : 'DropDownResultForSelectedVendor',type_ven:type_ven,no_ind:no_ind},function (r){
		
		if(r.data)
			{
			t=true;
			
				 list= '';
			
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
			
			
				$('#'+dropDownId).html(list);
			
			}
		else
			{
				bootbox.alert("Try again.");
			}
		callback(t);
	},'json');

}

function ControlQuotation(action , DisplayDiv , HideDiv , no_ind,id_id_ind)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	if(action =='Create')
		{	
			DisplayRequestUnderIndent('StartAuction',DisplayDiv , HideDiv , no_ind,id_id_ind);
			$('#'+HideDiv).hide();
			$('#'+DisplayDiv).show();
		

			
		}
	
	else if(action =='Cancel')
	{
		$('#'+HideDiv).hide();
		$('#'+DisplayDiv).show();
	}
	else if(action == 'Save')
		{
		var cont_valid =$('#cont_valid').val();
		var t=false;
		t=ValidationForm('submitRequestQuotation');
		$('#assetDivForRequestQuotationSelected').removeClass('error');
		if(t)
			{
			var gg = $('#assetDivForRequestQuotationSelected');
			
			$.each($("#assetDivForRequestQuotationSelected option"), function(){ 
				//alert($(this).val());
				$(this).attr('selected', 'selected');
					
			    });
			
			if(gg.val() == null)
					{
						bootbox.alert("Mandatory Field.");
						$('#assetDivForRequestQuotationSelected').addClass('error');
						$('#assetDivForRequestQuotationSelected').focus();
						t=false;
						exit(0);
					}
			}
		if(t)
			{
			bootbox.confirm("Are you sure ?", function(result) {
				if(result)
				{	
			//$('.reqq').attr('disabled','disabled');
				var x = $('#submitRequestQuotation').serialize();
				console.log(x);
				$.post('StartAuction',x,function (r)
						{
					
							if(r.data)
								{
								bootbox.alert("Successfully Added.");
								$( ".requestQuotation" ).trigger( "click" );
								
								}
								else
									{
										bootbox.alert("Something went wrong. Please try again.");
									}
									
							//$('.reqq').removeAttr('disabled');
						},'json');
				}});
			}
		
		
		
		}
	
	
	
	
			}});


}



function DisplayRequestUnderIndent(servletName,DispDiv,HideDiv,no_ind,id_id_ind,dt_rate_cont_valid)
{
	$.post(servletName,{action : 'Display',id:no_ind},function (r){
		
		
		var list= '<tr>'+
				'<td colspan="10" class="tableHeader">'+
				'<p class="tableHeaderContent" style="margin-left: 35%;">Auction Category Details</p></td>'+
				'</tr>'+
				'<tr class="tableHeader info">'+
				'<td><strong><center>Auction Category</center></strong></td>'+
				'<td><center><strong>Qty</strong></center></td>'+
				'<td ><strong><center>Unit Price<a href=#></a></center></strong></td>'+
	 	           '<td ><strong><center>Total Price<a href=#></a></center></strong></td>'+
		
			
		'</tr>';
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
			
				
				list  += '<tr class="success">'+
				'<input type="hidden"  name="id_ind_asst" value="'+params.id_auc_asset+'">'+
				'<input type="hidden"  name="id_req" value="'+params.id_auc+'">'+
				'<input type="hidden"  name="id_prod" value="'+params.id_prod+'">'+
				'<input type="hidden"  name="qty" value="'+params.qty+'">'+
				'<input type="hidden"  name="un_prc" value="'+params.un_prc+'">'+
		 	  '<input type="hidden"  name="tot_prc" value="'+params.tot_prc+'">'+
			
								'<td><center>'+params.nm_model+'</center></td>'+
								'<td><center>'+params.qty+'</center></td>'+
								'<td><center>'+params.un_prc+'</center></td>'+
								'<td><center>'+params.tot_prc+'</center></td>'+
							 '</tr>';
				}
				
				var list1 ='<tr>'+
				'<td colspan="4" class="tableHeader"><p class="tableHeaderContent" style="margin-left: 470px;">Auction Details</p></td>'+
				'</tr>'+
				
				'<input type="hidden"  name="id_ind" value="'+r.data[0].id_auc+'">'+
					'<tr class="info">'+
				'<td><center><strong>Auction Name  :</strong></center></td>'+
				'<td style="background-color: cadetblue;color: white;"><center><b>'+r.data[0].auc_name+'</b></center></td>'+
				'<td><center><strong>Auction Date :</strong></center></td>'+
				'<td style="background-color: cadetblue;color: white;"><center><b>'+r.data[0].dt_auc+'</b></center></td>'+
			'</tr>';
			
			
				
				
				var tc='<center><textarea  style="margin-left: 0px;margin-right: 0px;width: 540px; height: 73px;"  name="quot_t_c">'+
					r.t_and_c+
				'</textarea></center>';
				$('#quot_t_c_data').html(tc);
				$('.indentDetailsForQuotation').html(list1);
				$('.requestDataForQuotaion').html(list);
				 $('input[name="min_amount_bid"').val(r.data[0].grand_tot);
				
			
			}
		else
			{
			list +='<tr><td colspan="10"><strong><center>No record(s) is available..</center></strong></td></tr>';
				$('.requestDataForQuotaion').html(list);
			}
		
	},'json');
}


function DisplayQuotation(servletName,DisplayDiv, HideDiv)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	var dt_frm =$('input[name="dt_frm"]').val();
	var dt_to =$('input[name="dt_to"]').val();
	var searchWord = $('input[name="search"').val();
	$.post(servletName,{action : 'Display',dt_frm:dt_frm,dt_to:dt_to,searchWord:searchWord,for_rate:'0'},function (r){
		var list= '<tr class="tableHeader">'+
		'<td colspan="5"><center><strong><p class="tableHeaderContent" style="">Start Auction</p></strong></center></td>'+'</tr>'+

		'<tr class="tableHeader info">'+
			'<td><center><strong>Auction Date</strong></center></td>'+
					'<td><center><strong>Auction Name</strong></center></td>'+
					'<td><center><strong>Auction Price </strong></center></td>'+
					'<td style="width:200px;"><center><a href="#"><strong>Start Auction</strong></a></center></td>'+
	'</tr>';
		if(r.data.length > 0)
			{
				for(var i = 0; i < r.data.length ; i++)
				{
				params = r.data[i];
				list = list + '<tr class="success">'+
										'<td><center>'+params.dt_auc+'</center></td>'+
											'<td><center>'+params.auc_name+'</center></td>'+
											'<td><center>'+params.grand_tot+'</center></td>'+
											'<td><strong><center><a class="alert" href="#" onclick="ControlQuotation(\'Create\',\'createRequestQuotation\',\'displayRequestQuotation\',\''+params.id_auc+'\')"> Start Auction </a><center></strong></td>'+
							  '</tr>';
				}
				$('.requestquotationForDisplay').html(list);
			}
		else
			{
			list +='<tr><td colspan="4"><strong><center>No record(s) is available..</center></strong></td></tr>';
				$('.requestquotationForDisplay').html(list);
			}
		
	},'json');
			}});
}


$( ".datepicker1" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "dd/mm/yy",
      autoSize: true,
      altFormat: "dd/mm/yy",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_req=$(this).val();
    	  var id_ind=$('input[name="id_ind"]').val();
    	  $(".datepicker2").val('');
    	  
    	/*  $.post('Request_Quotation',{action : 'CheckDate' ,dt_req:dt_req,id_ind:id_ind},function (r){
	    		if(r.data == 0)
	    			{
	    			alert('Requested Date should be greater or equal to indent date : '+r.dt_ind);
	    			$(".datepicker1").focus();
	    			$(".datepicker1").val('');
	    			$(".datepicker1").addClass('error');
	    				exit(0);
	    			}
	    		
	    },'json');
    	  */
    	  
      }
    });


$( ".datepicker2" ).datepicker({
	yearRange: '1985:2025',
      changeMonth: true,
      changeYear: true,
      dateFormat: "dd/mm/yy",
      autoSize: true,
      altFormat: "dd/mm/yy",
      onSelect: function(selected,evnt) {
    	  $(this).removeClass('error');
    	  var dt_req_quot=$('input[name="dt_req_quot"]').val();
    	  var dt_req_quot1=$('input[name="dt_req_quot"]').val();
    	  var dt_end = $(this).val();
    	  if(dt_req_quot == '')
    		  {
    		  	alert('First filled requested date.');
    		  	$('input[name="dt_req_quot"]').addClass('error');
    		  	$('input[name="dt_req_quot"]').val('');
    		  	$(this).val('');
    		  }
    	  else
    		  {
    		  
    		  var temp_strt = dt_req_quot.split("/");
				 var  temp_dt_strt = new Date(temp_strt[2], temp_strt[1] - 1, temp_strt[0]);
					
				var temp_end = dt_end.split("/");
				var temp_dt_end = new Date(temp_end[2], temp_end[1] - 1, temp_end[0]);
					
				dt_req_quot = $.datepicker.formatDate('yy-mm-dd', temp_dt_strt);
	    		  dt_end = $.datepicker.formatDate('yy-mm-dd', temp_dt_end);
	    		  
    		/*  if(dt_req_quot > dt_end)
    			  {
    			  	alert('Last date of quotation should be greater or equal to requested date : '+dt_req_quot1);
    			  	$(".datepicker2").focus();
    			  	$(".datepicker2").val('');
    			  	$(".datepicker2").addClass('error');
    			  	exit(0);
    			  }*/
    		  
    		  }
    	  
      }
      
    });






