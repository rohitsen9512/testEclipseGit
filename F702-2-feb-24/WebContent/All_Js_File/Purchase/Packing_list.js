function DisplayPackingList(servletName,displayContent,createDetails,DisplayTableClass)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	$('#serialNo').hide();
	
	//$('.ind').attr('disabled','disabled');
	
				$.post(servletName,{action : 'Display' } , function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong><center>PO Number</center></strong></td>'+
					'<td><strong><center>PO Date</center></strong></td>'+
					
					'<td style="width: 155px;"><strong><center><a href="#">Create ASN </a></center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_po+'</center></td>'+
						'<td><center>'+params.dtpo+'</center></td>'+
						
						'<td><strong><center><a class="alert" href="#" onclick="CreatePackingList('+params.id_po+')"> Create ASN </a></center></strong></td>'+
						'</tr>';
						}
					
					
						$('.PackingListForDisplay').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
						$('.PackingListForDisplay').html(list);
					}
		//		$('.ind').removeAttr('disabled');	
				
			},'json');
		
			}});
}
function DisplayPackingListPreview(servletName,displayContent,createDetails,DisplayTableClass)
{
	SessionCheck(function (ses){		
		if(ses)
			{
	$('#serialNo').hide();
	
	//$('.ind').attr('disabled','disabled');
	
				$.post(servletName,{action : 'DisplayPreview' } , function (r){
					
					var list= '<tr class="tableHeader info">'+
					'<td><strong><center>PO Number</center></strong></td>'+
					'<td><strong><center>PO Date</center></strong></td>'+
					'<td><strong><center>Partner</center></strong></td>'+
					'<td style="width: 155px;"><strong><center><a href="#">Preview ASN </a></center></strong></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						list = list + '<tr class="success">'+
						'<td><center>'+params.no_po+'</center></td>'+
						'<td><center>'+params.dtpo+'</center></td>'+
						'<td><center>'+params.nm_ven+'</center></td>'+
						'<td><strong><center><a class="alert" href="#" onclick="CreatePackingListPreview('+params.id_pack_list+')"> Preview ASN </a></center></strong></td>'+
						'</tr>';
						}
					
					
						$('.PackingListForDisplay').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
						$('.PackingListForDisplay').html(list);
					}
		//		$('.ind').removeAttr('disabled');	
				
			},'json');
		
			}});
}
function CreatePackingList(id_po)
{

		$.post('Packing_List',{action : 'Edit',id : id_po},function (r){
				
				if(r.data.length > 0)
					{
					id_ven=r.data[0].id_ven;
					
					for(var i = 0; i < r.data.length ; i++)
						
						for (var key in r.data[i])
							{
							
								if($('select[name='+key+']').is("select") && r.data[i][key] != '')
									
									$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
								else
									$('input[name='+key+']').val(r.data[i][key]);
								
							}
					
						
						$('button[name="save"]').removeClass('hideButton');
						$('button[name="update"]').addClass('hideButton');
						$('#displayPackingListDiv').hide();
						$('#createPackingListDiv').show();
						//$('#DisplayAssetRP').show();
					
						$('#createAssetPR').show();
						//$('#DisplayAssetRP').hide();
						$('input[name="dt_po"]').val(r.data[0].dtpo);
						/*$('input[name="dt_gd_rcv"]').val(r.data[0].dtgdrcv);*/
						$('input[name="id"]').val(id_po);
						$('input[name="id_po"]').val(id_po);
						//alert(id);
					

					}
				/*else
					bootbox.alert("Try again.");*/
				DisplayLineItemDynamicallyForEditLineItem(id_po);
				DisplayLineItemDynamicallyForEditLineItem1('New');
			},'json');
				
				

}
function DisplayLineItemDynamicallyForEditLineItem(id_po)
{
$.post('Packing_List',{action : 'DisplayLineItem',id:id_po},function (r){
	
	if(r.data.length > 0)
		{
			/*for(var i=0;i<r.data.length;i++)
			{
				for (var key in r.data[i])
		        {
					if($('select[name='+key+']').is("select") && r.data[i][key] != '')
					{
						$('select[name='+key+']').val('' + r.data[i][key] + '').trigger('change');
						
					}
					else
					{
						$('input[name='+key+']').val(r.data[i][key]);
					}
				
		        }
			}*/
			
			var params='';
		var list ='<tr>'+
		'<td colspan="7" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 350px;">Line Item Details</p></td></tr>'+ 
			
		'<tr class="tableHeader info">'+
		'<td><strong><center>PO Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Item Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Item Code<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Quantity<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td ><strong><center>No. Of Small Boxes<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Wt. in KGS<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Container No.<a href=#></a></center></strong></td>'+
		
		'</tr>';
		var i=0;
		for(i=0;i<r.data.length;i++)
			{
			params=r.data[i];
		
			list += '<tr class="success">'+
			'<td><center>'+params.no_po+'</center></td>'+
			'<td><center>'+params.nm_model+'</center></td>'+
			'<td><center>'+params.cd_model+'</center></td>'+
						'<td><center><input type="text"  name="qty'+i+'" patelUnPrc="'+i+'" value="'+params.qty+'" style="text-align: right;" class="common-validation"></center></td>'+

			'<td><center><input type="text"  name="no_small_box'+i+'" patelUnPrc="'+i+'" value="0" style="text-align: right;" class="common-validation"></center></td>'+
			'<td><center><input type="text"  name="weight'+i+'" patelUnPrc="'+i+'" value="0" style="text-align: right;" class="common-validation"></center></td>'+
			'<td><center><input type="text"  name="container_no'+i+'" patelUnPrc="'+i+'" value="NA" style="text-align: right;" class="common-validation"></center></td>'+
			
			'<input type="hidden" name="count" value="'+i+'">'+	
			'<input type="hidden" name="id_prod'+i+'" value="'+params.id_prod+'">'+
			/*'<input type="hidden" name="qty'+i+'" value="'+params.qty+'">'+*/
			'</tr>';
			}

			
		$('input[name="itemCount"]').val(parseInt(i));
		
		list = list + '<tr class="success">'+
		
		'</tr>';
		
	
		$('.EditStore').html(list);
		/*$('.EditSalesOrder2').html(list2);*/
		
	
		}
	

	
},'json');

}
function CreatePackingListPreview(id_pack_list)
{

		$.post('Packing_List',{action : 'EditPreview',id : id_pack_list},function (r){
				
				if(r.data.length > 0)
					{
					id_ven=r.data[0].id_ven;
					
					for(var i = 0; i < r.data.length ; i++)
						
						for (var key in r.data[i])
							{
							
								if($('select[name='+key+']').is("select") && r.data[i][key] != '')
									
									$('select[name='+key+'] option[value=' + r.data[i][key] + ']').attr('selected',true);
								else
									$('input[name='+key+']').val(r.data[i][key]);
								
							}
					
						
						$('button[name="save"]').removeClass('hideButton');
						$('button[name="update"]').addClass('hideButton');
						$('#displayPackingListDiv').hide();
						$('#createPackingListDiv').show();
						//$('#DisplayAssetRP').show();
					
						$('#createAssetPR').show();
						//$('#DisplayAssetRP').hide();
						$('input[name="dt_po"]').val(r.data[0].dtpo);
							$('input[name="dt_exp_dlvr"]').val(r.data[0].dtexpdlvr);
						/*$('input[name="dt_gd_rcv"]').val(r.data[0].dtgdrcv);*/
						$('input[name="id"]').val(id_pack_list);
						$('input[name="id_pack_list"]').val(id_pack_list);
						$('#remarks').val(r.data[0].remarks);
						//alert(id);
					

					}
				/*else
					bootbox.alert("Try again.");*/
				DisplayLineItemDynamicallyForEditLineItemPreview(id_pack_list);
				
			},'json');
				
				

}

function DisplayLineItemDynamicallyForEditLineItem1(action){

	if(action == 'New')
	{
		//alert("hello");
		var list ='<tr>'+
			'<td colspan="5" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 350px;">Marks & No.s/Container No</p></td></tr>'+ 
			'<td><strong>Remarks<font color="red"> * </font><a href=#></a></strong></td>'+
			'<td colspan="3"><textarea style="width: 400px;height: 69px;" rows="6" cols="5"  id ="remarks"  name ="remarks" class="common-validation "placeholder="500 Character Only..."></textarea> </td>'+
			
			
		'<tr class="tableHeader info">'+
		
			'<td><strong><center>BOX Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
			'<td><strong><center>Number of Boxes<font color="red"> * </font><a href=#></a></center></strong></td>'+
			
			'<td ><strong><center>Gross Wt.<a href=#></a></center></strong></td>'+
		
			'<td ><strong><center>Net Wt.<a href=#></a></center></strong></td>'+
			'<td ><strong><center>Dimensions<a href=#></a></center></strong></td>'+
			
			
		'</tr>';
		
		for(var i = 0; i < 3 ; i++)
		{
			list+='<tr>'+
			' <td style="display:none;">'+
				
			'<td><input type="text" name="box_no'+i+'" value="" patelUnPrc="'+i+'" style="float:right;text-align:right"  class="common-validation" ></td>'+
			'<td><input type="text" name="no_box'+i+'" value="" patelUnPrc="'+i+'" style="float:right;text-align: right"  class="common-validation" ></td>'+
			'<td><input type="text" name="gross_wt'+i+'" value="" patelUnPrc="'+i+'" style="float:right;text-align: right"  class="common-validation" ></td>'+
			'<td><input type="text" name="net_wt'+i+'" value="" patelUnPrc="'+i+'" style="float:right;text-align: right"  class="common-validation" ></td>'+
			'<td><input type="text" name="dimensions'+i+'" value="" patelUnPrc="'+i+'" style="float:right;text-align: right"  class="common-validation" ></td>'+
			'<input type="hidden" name="count1" value="'+i+'">'+	
		'</tr>';
			
		}
		
		$('input[name="itemCount"]').val('3');
		
		$('.EditStore1').html(list);
		
	}
	
	else{
		var i=$('input[name="itemCount"]').val();
			
		var list='<tr>'+
		
		'<td><input type="text" name="box_no'+i+'" value="" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  class="common-validation" ></td>'+
		'<td><input type="text" name="no_box'+i+'" value="" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  class="common-validation" ></td>'+
		'<td><input type="text" name="gross_wt'+i+'" value="" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  class="common-validation" ></td>'+
		'<td><input type="text" name="net_wt'+i+'" value="" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  class="common-validation" ></td>'+
		'<td><input type="text" name="dimensions'+i+'" value="" patelUnPrc="'+i+'" style="float:right;text-align: right;width:80px""  class="common-validation" ></td>'+
		
		'<input type="hidden" name="count1" value="'+i+'">'+	
		
	'</tr>';
		$('input[name="itemCount"]').val(parseInt(i) +1);
	$('.EditStore1').append(list);
	
	}
	
}

function DisplayLineItemDynamicallyForEditLineItemPreview(id_pack_list)
{
$.post('Packing_List',{action : 'DisplayLineItemPreview',id:id_pack_list},function (r){
	
	if(r.data.length > 0)
		{
			/*for(var i=0;i<r.data.length;i++)
			{
				for (var key in r.data[i])
		        {
					if($('select[name='+key+']').is("select") && r.data[i][key] != '')
					{
						$('select[name='+key+']').val('' + r.data[i][key] + '').trigger('change');
						
					}
					else
					{
						$('input[name='+key+']').val(r.data[i][key]);
					}
				
		        }
			}*/
			
			var params='';
		var list ='<tr>'+
		'<td colspan="7" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 350px;">Line Item Details</p></td></tr>'+ 
			
		'<tr class="tableHeader info">'+
		'<td><strong><center>PO Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Item Name<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Item Code <font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Quantity<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td ><strong><center>No. Of Small Boxes<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Wt. in KGS<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Container No.<a href=#></a></center></strong></td>'+
		
		
		'</tr>';
		var i=0;
		for(i=0;i<r.data.length;i++)
			{
			params=r.data[i];
		
			list += '<tr class="success">'+
			'<td><center>'+params.no_po+'</center></td>'+
			'<td><center>'+params.nm_model+'</center></td>'+
			'<td><center>'+params.cd_model+'</center></td>'+
			'<td><center>'+params.qty+'</center></td>'+
			'<td><center>'+params.no_small_box+'</center></td>'+
			'<td><center>'+params.weight+'</center></td>'+
			'<td><center>'+params.container_no+'</center></td>'+
			//'<input type="hidden" name="count" value="'+i+'">'+	
			//'<input type="hidden" name="id_prod'+i+'" value="'+params.id_prod+'">'+
			
			'</tr>';
			}

			
		$('input[name="itemCount"]').val(parseInt(i));
		
		list = list + '<tr class="success">'+
		
		'</tr>';
		
	
		$('.EditStore').html(list);
		/*$('.EditSalesOrder2').html(list2);*/
		
	
		}
	
DisplayLineItemDynamicallyForEditLineItem1Preview(id_pack_list);
	
},'json');

}

function DisplayLineItemDynamicallyForEditLineItem1Preview(id_pack_list)
{
$.post('Packing_List',{action : 'DisplayLineItem1Preview',id:id_pack_list},function (r){
	
	if(r.data.length > 0)
		{
			/*for(var i=0;i<r.data.length;i++)
			{
				for (var key in r.data[i])
		        {
					if($('select[name='+key+']').is("select") && r.data[i][key] != '')
					{
						$('select[name='+key+']').val('' + r.data[i][key] + '').trigger('change');
						
					}
					else
					{
						$('input[name='+key+']').val(r.data[i][key]);
					}
				
		        }
			}*/
			
			var params='';
		var list ='<tr>'+
		'<td colspan="6" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 350px;">Line Item Details</p></td></tr>'+ 
			
		'<tr class="tableHeader info">'+
		'<td><strong><center>BOX Number<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>NO.Of BOX<font color="red"> * </font><a href=#></a></center></strong></td>'+
		'<td><strong><center>Gross Wt <font color="red"> * </font><a href=#></a></center></strong></td>'+
		
		'<td ><strong><center>Net Wt<a href=#></a></center></strong></td>'+
		'<td ><strong><center>Dimensions<a href=#></a></center></strong></td>'+
		
		
		'</tr>';
		var i=0;
		for(i=0;i<r.data.length;i++)
			{
			params=r.data[i];
		
			list += '<tr class="success">'+
			'<td><center>'+params.box_no+'</center></td>'+
			'<td><center>'+params.no_box+'</center></td>'+
			'<td><center>'+params.gross_wt+'</center></td>'+
			
			'<td><center>'+params.net_wt+'</center></td>'+
			'<td><center>'+params.dimensions+'</center></td>'+
			//'<input type="hidden" name="count" value="'+i+'">'+	
			//'<input type="hidden" name="id_prod'+i+'" value="'+params.id_prod+'">'+
			
			'</tr>';
			}

			
		
		list = list + '<tr class="success">'+
		
		'</tr>';
		
	
		$('.EditStore1').html(list);
		/*$('.EditSalesOrder2').html(list2);*/
		
	
		}
	
	
},'json');

}
function ControlPackingList(action,displayContent,createDetails,formName,servletName) {
	SessionCheck(function (ses){		
			if(ses)
				{
		
			$(".common-validation").each(function(){
			
				$(".common-validation").removeClass('error');
			});
			$('input[name="dt_req"]').attr("disabled", false);
		
		
		
		if(action=='Cancel')
			{
				/*$('#DisplayAssetRP').hide();
				$('#createAssetPR').hide();
				$('#'+createDetails).hide();
				
				$('#'+displayContent).show();
				DisplayDirectInvoice(servletName,displayContent,createDetails,'','PRForDisplay');*/
			$( ".packinglist" ).trigger( "click" );
			}
		
		
		
		if(action=='Save' || action=='Update')
			{
			
				if(action == 'Save')
					{
					$('button[name="save1"]').removeClass('hideButton');
					$('input[name="action"]').val('Save');
					DataInsertUpdateForMaterial(displayContent,createDetails,formName,servletName);
					}
				else
					{
						$('input[name="action"]').val('Update');
					}
				
				
				
			}
		
		if(action == 'Update')
		{
			var t=false;
			t=ValidationForm('submitVerbalOrder');
			if(t)
			{
				$('.upd').attr('disabled','disabled');

				
				var x = $('#submitVerbalOrder').serialize();
				$.post('Direct_Invoice',x,function (r){
					if(r.data == 1)
						{
						bootbox.alert("Update successfully.");
						$( ".creategrn" ).trigger( "click" );
						}
					else
					{
						bootbox.alert("Something went wrong Please try again.");
					}
					$('.upd').removeAttr('disabled');
				},'json');
			
			}
		}
		
				}
	});
	}

function DataInsertUpdateForMaterial(displayContent,createDetails,formName,servletName)
{
SessionCheck(function (ses){
		
		if(ses)
			{
	t=false;
	t = ValidationForm(formName);
		if(t)
		{
		///	var id = $('input[name="id"]').val();
			
		$('button[name="save"]').removeClass('hideButton');
		$('button[name="update"]').addClass('hideButton');
			var x = $('#'+formName).serialize();
			
			$.post(servletName,x,function (r){
				
				if(r.data==1)
					{
					
					bootbox.alert("ASN  is created...");
						$( ".packinglist" ).trigger( "click" );
					}
				else
					{
						bootbox.alert("Try again.");
					}
				$('.req').removeAttr('disabled');
				
			},'json');
		
		}
			}
});
}