
function ControlTaggingForTagPrint(Action)
{	
	SessionCheck(function (ses){		
		if(ses)
			{
	if(Action == 'Next')
	{
		
		var t = true;
		var id_grp = $('#prodDivDataForTagPrint').val();
		var id_sloc = $('#slocDataForTagPrint').val();
		var id_loc = $('#locDataForLead').val();
		
		$('#slocDataForTagPrint').removeClass('error');
		$('#prodDivDataForTagPrint').removeClass('error');
		
		if(id_grp == '')
			{
				bootbox.alert(" Mandatory field.");
				$('#prodDivDataForTagPrint').focus();
				$('#prodDivDataForTagPrint').addClass('error');
				t=false;
			}
			else if(id_loc == '')
		{
			bootbox.alert(" Mandatory field.");
			$('#locDataForLead').focus();
			$('#locDataForLead').addClass('error');
			t=false;
		}
		else if(id_sloc == '')
		{
			bootbox.alert(" Mandatory field.");
			$('#slocDataForTagPrint').focus();
			$('#slocDataForTagPrint').addClass('error');
			t=false;
		}
		if(t)
			{
				DisplayleadForTagPrint('Lead_Tag_printing',id_grp,id_sloc,id_loc);
				
			}
			
		
		
	}
	if(Action == 'Back')
	{
		window.location = $('.tag_event').attr('href');
 
	}
			}});
}



function CountProdIdForTagging(ClassName)
{
	var t= false;
	$('.'+ClassName).each(function (){
		if ($(this).is(":checked"))
		{
			t=true;
		}
		
			
	});
if(!t)
	{
	bootbox.alert('Please select at leat one asset.');
	}

return t;
}



function DisplayleadForTagPrint(servletName ,id_grp,id_sloc,id_loc)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
				var comNm='';
	$('#displayAssetForTagPrintSearch').hide();
	$('#PreviewProdForTagPrint').hide();
	$('#DisplayprodForTagPrint').show();
	
	var searchWord = $('#searchIdForTagging').val();
	
	
				$.post(servletName,{action : 'Display' ,searchWord : searchWord,id_grp : id_grp,id_sloc:id_sloc,id_loc:id_loc} , function (r){
					
					var list= '<tr><td colspan="5">'+
												'<button name="Back" type="button" style="margin-left:400px;" class="btn btn-primary" onclick="ControlTaggingForTagPrint(\'Back\')">Back</button>'+
												'<button name="print" type="submit" style="margin-left:10px;" class="btn btn-primary" onclick="return CountProdIdForTagging(\'tagClassForMakingBarCode\')">Preview</button>'+
											'</td></tr">'+
					'<tr class="tableHeader info">'+
					'<td><center><strong>Company Name</strong></center></td>'+
					'<td><center><strong>Product Name</strong></center></td>'+
					'<td><center><strong>Serial No.</strong></center></td>'+
					'<td><center><strong>Phone No.</strong></center></td>'+
					'<td><center><strong>Product Status.</strong></center></td>'+
					'<td><center><strong>Filling Status.</strong></center></td>'+
					//'<td><center><strong>Sub Category</strong></center></td>'+
					
					/*'<td><center><strong>Serial Number</strong></center></td>'+
					'<td><center><strong>Computer Name</strong></center></td>'+*/
					'<td style="width: 160px;"><center><strong><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'tagClassForMakingBarCode\')"> Check All </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'tagClassForMakingBarCode\')"> Uncheck All </a></strong></center></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						params1 = r.company[0];
						params2 = r.entityname[0];
						debugger;
							
					   if(params.device_status=='Sale'||params.device_status=='sale'){
							deviceStatus='Sale';
						}
						if(params.device_status=='Rental'||params.device_status=='rental'){
							deviceStatus='Rental';
						}
						  if(params.device_status=='in_store'){
							deviceStatus='In Store';
						}
						if(params.device_status=='2'){
							fill='-';
						}
					   if(params.filling_status=='1'){
							fill='Fill';
						}
						if(params.filling_status=='0'){
							fill='Empty';
						}
						//console.log(params2.nm_loc);
						if(params2.nm_loc=='F7 MEDICAL EQUIPMENT'){
							comNm='F7ME';
						}
						if(params2.nm_loc=='F7 HEALTHCARE PVT LTD'){
							comNm='F7HC';
						}
						if(params.nm_computer == '' || params.nm_computer == undefined) nm_computer = 'NA'; else nm_computer = params.nm_computer;
						list = list + '<tr class="success">'+
				         '<td><center>'+comNm+'</center></td>'+		
				         '<td><center>'+params.nm_product+'</center></td>'+
                         '<td><center>'+params.serial_no+'</center></td>'+
                       '<td><center>'+params1.phone+'</center></td>'+		
                         '<td><center>'+deviceStatus+'</center></td>'+
                        '<td><center>'+fill+'</center></td>'+
                   
											
						/*'<td><center>'+params.no_mfr+'</center></td>'+
						'<td><center>'+nm_computer+'</center></td>'+*/
						'<td><strong><center><input type="checkbox" class="tagClassForMakingBarCode" name="selectTagPrint" value="'+params.id_iwh+'"/></center></strong></td>'+
						'</tr>';
						}
						list = list + '<tr>'+
											'<td colspan="5">'+
											'<b>Note :</b><br>If You Are Unable To See The Bar Code In Next Page, Download The Font To: '+
											'Settings -> Control Panel -> Fonts.'+
											'To Download The Font,Click on The Below Link & Click Save Target As and Save The Font in Control Panel\Fonts folder. Be Sure You Have Logged in As Administrator !!<br>'+
											'<a href="download.jsp?fileName1=free3of9.ttf" target="_blank"><span style=" color: blue;">Font.....</span></a>'+
											'</td>'+
									'</tr>'+
									 '<tr>'+
											/*'<td colspan="5">'+
												'<button name="Back" type="button" style="margin-left:400px;" class="btn btn-primary" onclick="ControlTaggingForTagPrint(\'Back\')">Back</button>'+
												'<button name="print" type="submit" style="margin-left:10px;" class="btn btn-primary" onclick="return CountAssetIdForTagging(\'tagClassForMakingBarCode\')">Preview</button>'+
											'</td>'+*/
									  '</tr>';
					
						$('.DisplayprodForTagPrint').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
						list = list + '<tr>'+
								'<td colspan="5">'+
								'<button name="Back" type="button" style="margin-left:400px;" class="btn btn-primary" onclick="ControlTaggingForTagPrint(\'Back\')">Back</button>'+
								'</td>'+
							'</tr>';
						$('.DisplayprodForTagPrint').html(list);
					}
					
				
			},'json');
			}});

}



function DisplayAssetForTagPrint(servletName ,id_grp,id_sgrp)
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
	$('#displayAssetForTagPrintSearch').hide();
	$('#PreviewAssetForTagPrint').hide();
	$('#DisplayAssetForTagPrint').show();
	var searchWord = $('#searchIdForTagging').val();
	
	
				$.post(servletName,{action : 'Display' ,searchWord : searchWord,id_grp : id_grp ,id_sgrp :id_sgrp} , function (r){
					
					var list= '<tr><td colspan="5">'+
												'<button name="Back" type="button" style="margin-left:400px;" class="btn btn-primary" onclick="ControlTaggingForTagPrint(\'Back\')">Back</button>'+
												'<button name="print" type="submit" style="margin-left:10px;" class="btn btn-primary" onclick="return CountAssetIdForTagging(\'tagClassForMakingBarCode\')">Preview</button>'+
											'</td></tr">'+
					'<tr class="tableHeader info">'+
					'<td><center><strong>Asset ID</strong></center></td>'+
					'<td><center><strong>Asset Ref. Number</strong></center></td>'+
					'<td><center><strong>Serial No.</strong></center></td>'+
					'<td><center><strong>Sub Category</strong></center></td>'+
					
					/*'<td><center><strong>Serial Number</strong></center></td>'+
					'<td><center><strong>Computer Name</strong></center></td>'+*/
					'<td style="width: 160px;"><center><strong><a href="#" onclick="MakeAllCheckCommonFun(\'SelectAll\',\'tagClassForMakingBarCode\')"> Check All </a> / <a href="#" onclick="MakeAllCheckCommonFun(\'DeselectAll\',\'tagClassForMakingBarCode\')"> Uncheck All </a></strong></center></td>'+
				'</tr>';
					
				if(r.data.length > 0)
					{
						
						for(var i = 0; i < r.data.length ; i++)
						{
						
						params = r.data[i];
						if(params.nm_computer == '' || params.nm_computer == undefined) nm_computer = 'NA'; else nm_computer = params.nm_computer;
						list = list + '<tr class="success">'+
						'<td><center>'+params.id_wh_dyn+'</center></td>'+
						'<td><center>'+params.appNo+'</center></td>'+
						'<td><center>'+params.serial_no+'</center></td>'+
						'<td><center>'+params.nm_s_assetdiv+'</center></td>'+
						
						/*'<td><center>'+params.no_mfr+'</center></td>'+
						'<td><center>'+nm_computer+'</center></td>'+*/
						'<td><strong><center><input type="checkbox" class="tagClassForMakingBarCode" name="selectTagPrint" value="'+params.id_wh+'"/></center></strong></td>'+
						'</tr>';
						}
						list = list + '<tr>'+
											'<td colspan="5">'+
											'<b>Note :</b><br>If You Are Unable To See The Bar Code In Next Page, Download The Font To: '+
											'Settings -> Control Panel -> Fonts.'+
											'To Download The Font,Click on The Below Link & Click Save Target As and Save The Font in Control Panel\Fonts folder. Be Sure You Have Logged in As Administrator !!<br>'+
											'<a href="download.jsp?fileName1=free3of9.ttf" target="_blank"><span style=" color: blue;">Font.....</span></a>'+
											'</td>'+
									'</tr>'+
									 '<tr>'+
											/*'<td colspan="5">'+
												'<button name="Back" type="button" style="margin-left:400px;" class="btn btn-primary" onclick="ControlTaggingForTagPrint(\'Back\')">Back</button>'+
												'<button name="print" type="submit" style="margin-left:10px;" class="btn btn-primary" onclick="return CountAssetIdForTagging(\'tagClassForMakingBarCode\')">Preview</button>'+
											'</td>'+*/
									  '</tr>';
					
						$('.DisplayAssetForTagPrint').html(list);
					
					}
				
				else
					{
						list +='<tr rowspan="2"><td colspan="6"><center><strong>No record(s) is available.</strong></center></td></tr>';
						list = list + '<tr>'+
								'<td colspan="5">'+
								'<button name="Back" type="button" style="margin-left:400px;" class="btn btn-primary" onclick="ControlTaggingForTagPrint(\'Back\')">Back</button>'+
								'</td>'+
							'</tr>';
						$('.DisplayAssetForTagPrint').html(list);
					}
					
				
			},'json');
			}});

}

function SaveAttribute()
{
	
	SessionCheck(function (ses){		
		if(ses)
			{
				t=false;
	
	$(function () {
            //alert("Id: " + $(this).attr("id") + " Value: " + $(this).val());
               var select = document.getElementById('selattr');
               //var value = select.options[select.selectedIndex].value;

				var id = select.options[select.selectedIndex].id;
				var value ;
			//var order=$(this).attr("data-id");
			var order=$('input[name]').val();
			
			   switch(id) {
  case "id_grp":
    // code block
value="Category";
    break;
  case "ds_pro":
   value="Model No";
    break;
 case "appNo":
    value="Asset Ref No";
    break;
  case "serial_no":
    value="Serial No";
    break;
 case "id_wh_dyn":
    value="Asset ID";
    break;
 case "id_sgrp":
    value="Sub Category";
    break;
 case "id_loc":
    value="Location";
    break;
  
}
			if (order!=0) {
        
 

				
				
				//alert(id+" value==  "+value+" order    "+order);
				$.post('T_Tagg_Print',{action : 'updatetag', id : id, order: order, value:value},function (r){	
						if(r.data > 0)
						{
							bootbox.alert("Added Successfully.");
							t=true;
							window.location = $('.Customtag_event').attr('href');
						}
						else
						bootbox.alert("Please check Your Details carefully");
						
					},'json');
				}
				else
				bootbox.alert("Please Fill Order detail First");
				
				
		}
		
		);	
		
		

			}
			}
			
);

}

function DisplayTagPrintPreview(){
	//alert();
	
	
		$.post('T_Tagg_Print',{action : 'previewtag'},function (r){
			var list=list + '<thead><tr class="new">'+
		'<td><center><strong>Attribute</strong></center></td>'+
		'<td><center><strong>Order</strong></center></td>'+
		'<td><center><strong>Delete</strong></center></td>'+
	'</tr>';
		//
		//alert("length is " + r.data.length);
		if(r.data.length > 0)
					{
						
				for(var i = 0; i < r.data.length ; i++)
									{
					params=r.data[i];
										 list = list + '<tr>'+
				'<td><center>'+params.column_label+'</center></td>'+
							 		'<td><center><a class="alert" href="#" onclick="modifySerial(\''+params.column_name+'\','+params.column_order+')">'+params.column_order+'</a></center></td>'+
								'<td style="width: 140px;"><center><strong><a class="alert" href="#" onclick="DeleteTagOrder(\'T_Tagg_Print\','+params.id+')"> <i class="fas fa-trash" style="color: red;"></i></a></strong></center></td>'+
								//'<td>&nbsp;&nbsp;<font face="'Free 3 of 9'" size="6"></font></td>'+
									'</tr>';
				
			
			
				$('.displaytag').html('</tbody>'+list);
							        
									
		
	}
	}
	
		},'json');
}

function modifySerial(col_name,col_order){
	
	//$('#selattr').val(col_name).trigger('change');
	$('select[name=selattr] option[id='+col_name+']').attr('selected',true);
	$('#ord_num').val(col_order);
}


function DeleteTagOrder(servletName,id)
{
SessionCheck(function (ses){
		
		if(ses)
			{	
	bootbox.confirm("Are you sure? You want to delete the record?", function(result) {
		if(result)
		{
			
			$.post(servletName,{action : 'DeleteTagOrder',id : id},function (r){
				
				if(r.data == 1)
				{
					bootbox.alert("Succesfully deleted!!!");
					DisplayTagPrintPreview();
					}
				
			},'json');
		}
	}); 
			}
});
}	
