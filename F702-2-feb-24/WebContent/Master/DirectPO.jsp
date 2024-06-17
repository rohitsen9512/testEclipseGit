<style>
      .easy-autocomplete-container { max-height: 250px; overflow: auto; overflow-y: auto; overflow-x:hidden; }
    </style>


<script type="text/javascript" src="js/purchaseCommon.js"></script>
<script type="text/javascript" src="common.js"></script>
<script type="text/javascript" src="All_Js_File/Purchase/Direct_po.js"></script>
<script type="text/javascript" src="All_Js_File/Order/Lead.js"></script>

<script src="Autocomp/jquery.easy-autocomplete.min.js"></script> 

<link rel="stylesheet" href="Autocomp/easy-autocomplete.min.css"> 

<link rel="stylesheet" href="Autocomp/easy-autocomplete.themes.min.css"> 


<script type="text/javascript">


$(function() {
	ApprovalByPass();
	$('.hideRowCol').hide();
	
	var currentDate = new Date();
	$( ".datepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	});
	$('input[name="dt_to"]').datepicker("setDate", currentDate);
	$('input[name="dt_quot"]').datepicker("setDate", currentDate);
	
	currentDate.setMonth(currentDate.getMonth() - 1);
	$('input[name="dt_frm"]').datepicker("setDate", currentDate);      
	      
	$( ".reqDatepicker" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function(selected,evnt) {
	    	  $('#purchaseDatePicker').removeClass('error');
	    	  var dt_req=$('#purchaseDatePicker').val();
	    	  $.post('Verbal_Purchase_Order',{action : 'CheckDate' ,dt_req:dt_req},function (r){
		    		if(r.data == 0)
		    			{
		    			
		    			alert('Requested Date should be between financial year : '+r.std_finance +' - '+r.end_finance);
		    			$('#purchaseDatePicker').focus();
		    			$('#purchaseDatePicker').val('');
		    			$('#purchaseDatePicker').addClass('error');
		    				exit(0);
		    			}
		    		
		    },'json');
	    	  
	    	  
	      }
	    });
	
	 currentDate = new Date();
	$('input[name="dt_req"]').datepicker("setDate", currentDate);
	$('input[name="dt_quot"]').datepicker("setDate", currentDate);
	$('input[name="dt_po"]').datepicker("setDate", currentDate);
	$('input[name="dt_po_valid_up_to"]').datepicker("setDate", currentDate);
	$('input[name="dt_po_delivery"]').datepicker("setDate", currentDate);
	
	$( ".poDatepickerFiller" ).datepicker({
		yearRange: '1985:2025',
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "dd/mm/yy",
	      autoSize: true,
	      altFormat: "dd/mm/yy",
	      onSelect: function () {
	    	  var count= $('input[name="count"]').val();
	    	  var dt_fill= $(this).val();
	    	  
	    	  for(var i=0;i<=count;i++){
	    		  //$('input[name=dt_rec_quot'+i+']').datepicker("setDate", dt_fill);
	    		  $('.autoset').datepicker("setDate", dt_fill);
	    	  }
	      }
	          
	       
	      
	    });
	DropDownDataDisplay("M_Loc","locDataForLead",function(status) {
		if (status) {
			
		}
	});	
	DisplaydirectVerbalOrder('Verbal_Purchase_Order','displaydirectVerbalOrder','createdirectVerbalOrder','','PRForDisplaydirect');
	$('button[name="update"]').addClass('hideButton');
	$('button[name="save1"]').addClass('hideButton');	
	
	
	  DisplayDropDownData("M_Emp_User","requestedPrByUser",function (status){
	 	if(status)
	    	{
	    	  DisplayDropDownData("M_Emp_User","repoMngrPo",function (status){
					if(status)
					{
	            //DisplaydirectVerbalOrder('Verbal_Purchase_Order','displaydirectVerbalOrder','createdirectVerbalOrder','','PRForDisplaydirect');
									}});
									
	 	}}); 

	
	
});
 
function ApprovalByPass(){
	
var val = $('select[name="aprvl_by_pass"]').val();
	
	if(val=='Yes'){
		$('.MandHide').hide();
		$(".addMandClass").removeAttr("data-valid","mand");
		$('.hideAppvl').hide();
		
	}else{
		$('.MandHide').show();
		$(".addMandClass").attr("data-valid","mand");
		$('.hideAppvl').show();
	}
}

function MakeDeliveryDateMandatory(event){
	var domElement =$(event.target || event.srcElement);
	var val = $('input[name="nm_prod'+domElement.attr('patelUnPrc')+'"]').val();
		
		if(val !=''){
		
			$('input[name="dt_rec_quot'+domElement.attr('patelUnPrc')+'"]').attr("data-valid","mand");
		}else{
			$('input[name="dt_rec_quot'+domElement.attr('patelUnPrc')+'"]').removeAttr("data-valid","mand");
		}
	}

jQuery("input#fileID").change(function () {
	
	 var formData = new FormData();	 
		formData.append('file', $('#fileID').get(0).files[0]);
		
			$.ajax({
			  url: 'Upload_File',
			    type: 'POST',
			    data: formData,
			    async: false,
			    cache: false,
			    contentType: false,
			    dataType: "json",
			    processData: false,
			    mimeType: "multipart/form-data",
			    success: function (r) {
			    	
			    		$('input[name="uploaded_file"]').val(r.upload_inv);
			    		//bootbox.alert("File Uploaded successfully");
			    }
			},'json'); 
			
	//$( "#submitForUploadData" ).trigger( "click" );
			
}); 

function calculateTot11111111(event, name){

	var domElement =$(event.target || event.srcElement);
	var totParticular=0.0,total=0.0;
	var intRegex = /^\d+$/;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	var str ='';
	var fieldName= name + domElement.attr('patelUnPrc');
	
	$('input[name="'+fieldName+'"]').removeClass('error');
	
	str = $('input[name="'+fieldName+'"]').val();
	if(str != '')
		{
	if(intRegex.test(str) || floatRegex.test(str)) {
		
	
	var id_tax = $('select[name="taxId'+domElement.attr('patelUnPrc')+'"]').val();
	if(id_tax != '0')
		{
		$.post('M_Tax',{action : 'DropDownResult',id:id_tax},function (r){
			
			if(r.data.length > 0 )
				{
				 var percent = r.data[0].per_tax;
				 var gr_tot=0.00;
				 var qty = $('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val();
				 
				 var item_prc = $('input[name="item_prc'+domElement.attr('patelUnPrc')+'"]').val();
				 
				 //freight_percent calculate....
				 var freight_percent = $('input[name="freight_percent'+domElement.attr('patelUnPrc')+'"]').val();
				var frt_per_rt=0;
				if(freight_percent !=0){
				frt_per_rt=parseFloat(item_prc)*parseFloat(freight_percent)/100;
				 $('input[name="freight_rate'+domElement.attr('patelUnPrc')+'"]').val(frt_per_rt.toFixed(2));
				 ///end
				}	
				 
				 
				 var freight_rate = $('input[name="freight_rate'+domElement.attr('patelUnPrc')+'"]').val();
				 var others_chrg = $('input[name="others_chrg'+domElement.attr('patelUnPrc')+'"]').val();
 				var taxVal = (((parseFloat(item_prc)+ parseFloat(freight_rate)+parseFloat(others_chrg) )*parseFloat(percent)))/100;
				 
				// var un_prc = parseFloat(item_prc)+parseFloat(taxVal) + parseFloat(freight_rate)+parseFloat(others_chrg);
				  var itemIns_chrg = $('input[name="itemins_chrg'+domElement.attr('patelUnPrc')+'"]').val();
				 
				 var un_prc = parseFloat(item_prc)+parseFloat(taxVal) + parseFloat(freight_rate)+parseFloat(others_chrg)+parseFloat(itemIns_chrg);
				 
					 $('input[name="un_prc'+domElement.attr('patelUnPrc')+'"]').val(un_prc.toFixed(2));
				 
					 var frt_rate = $('input[name="frt_rate'+domElement.attr('patelUnPrc')+'"]').val();
					 var frt_text = $('input[name="frt_text'+domElement.attr('patelUnPrc')+'"]').val();
					 var add_chrg = $('input[name="add_chrg'+domElement.attr('patelUnPrc')+'"]').val();
					 var buy_back = $('input[name="buy_back'+domElement.attr('patelUnPrc')+'"]').val();
				 var add_chrg1 = $('input[name="add_chrg1'+domElement.attr('patelUnPrc')+'"]').val();
					 var add_chrg2 = $('input[name="add_chrg2'+domElement.attr('patelUnPrc')+'"]').val();
				
					/* 	var add_chrg1=0.0;
						add_chrg1=((parseFloat(frt_rate)+parseFloat(frt_text)+parseFloat(add_chrg))*parseFloat(percent))/100;
					 */
					 
				 var tot= +parseFloat(qty)*parseFloat(un_prc);
				 grnd_tot=parseFloat(tot)+parseFloat(frt_rate)+parseFloat(frt_text)+parseFloat(add_chrg)+parseFloat(add_chrg1)+parseFloat(add_chrg2)-parseFloat(buy_back);
				
				 
				 //$('input[name="add_chrg1'+domElement.attr('patelUnPrc')+'"]').val(add_chrg1.toFixed(2));
					
						 $('input[name="taxVal'+domElement.attr('patelUnPrc')+'"]').val(taxVal.toFixed(2));
						 $('input[name="tot_prc'+domElement.attr('patelUnPrc')+'"]').val(tot.toFixed(2));
						 $('input[name="taxPercent'+domElement.attr('patelUnPrc')+'"]').val(percent);
						 $('input[name="grnd_tot'+domElement.attr('patelUnPrc')+'"]').val(grnd_tot.toFixed(2));
							
						 
						 $('.commonTotal').each(function (){
								if($(this).val() != undefined)
									gr_tot += +$(this).val();
								
							});
//budget....
							var flag=domElement.attr('patelUnPrc');
							var j=parseInt(flag)+1;
							console.log(j);
							var id_bud2 = $('input[name="id_bud'+j+'"]').val();
							console.log(id_bud2);
								console.log("shanu");
								var ind_bidge2='';
								var x=2;
								var k=parseInt(flag)-1;
								console.log(k);
								console.log(k);
								var exchange_rate = $('input[name="exchange_rate"]').val();
								if(exchange_rate==0.0){
									alert("Please Select The Currency.");
									$('input[name="grnd_tot'+domElement.attr('patelUnPrc')+'"]').val(0.0);
									$('input[name="tot_prc'+domElement.attr('patelUnPrc')+'"]').val(0.0);
									$('input[name="qty'+domElement.attr('patelUnPrc')+'"]').val(0.0);
									
								}else{
								var ind_bidgecondition = $('input[name="ind_bidge'+domElement.attr('patelUnPrc')+'"]').val();
								if(parseFloat(ind_bidgecondition)>grnd_tot){
								for(var i=0;i<=500;i++){
									var ind_bidgecom=$('input[name="id_bud'+domElement.attr('patelUnPrc')+'"]').val();
									var ind_bidgecom2=$('input[name="id_bud'+i+'"]').val();
									console.log(ind_bidgecom2);
									if(ind_bidgecom == ind_bidgecom2){
									
										console.log("pritesh");
										if(x==2){
										 ind_bidge2 = $('input[name="ind_bidge'+i+'"]').val();
											x=3;	
										}
									//	var qty1 = $('input[name="qty'+i+'"]').val();
										//var unitprice= $('#assetUnitPrice').val();
										var grnd_tot = $('input[name="grnd_tot'+i+'"]').val(); 
										if(parseFloat(grnd_tot)<=parseFloat(ind_bidge2)){
										 ind_bidge2=parseFloat(ind_bidge2)-parseFloat(grnd_tot)*parseFloat(exchange_rate);
										$('input[name="budget'+i+'"]').val(ind_bidge2.toFixed(2));
										
										
										}else{
											alert("Budget not Available! ");
											$('input[name="budget'+i+'"]').val(0.0);
											
											
										}
									}
									
									
									
							}
				}else{
					alert("Budget not Available! ");
					$('input[name="budget'+domElement.attr('patelUnPrc')+'"]').val(0.0);
					
					
				}
								}	/*  var others= $('input[name="others"]').val();
						 gr_tot = gr_tot + parseFloat(others);
					 */	
					 var bottomtotal = parseFloat($('input[name="frt_rate"]').val()) +parseFloat($('input[name="frt_text"]').val())+parseFloat($('input[name="freight_amount"]').val()) +parseFloat($('input[name="add_chrg"]').val())-parseFloat($('input[name="buy_back"]').val())-parseFloat($('input[name="discount"]').val());
						
						// var others= $('input[name="others"]').val();
						 gr_tot = gr_tot + parseFloat(bottomtotal);
					 	 console.log(gr_tot);
					
					 
						 $('input[name="tot"]').val(gr_tot.toFixed(2));
						 $('input[name="gr_tot"]').val(gr_tot.toFixed(2));
				}
			
			
		},'json');
		}
		
	}
	else
		{
			alert('Invalid number.');
			$('input[name="'+fieldName+'"]').addClass('error');
			$('input[name="'+fieldName+'"]').focus();
			$('input[name="'+fieldName+'"]').val(0);
			exit(0);
			
		}
	
		}
	 else
		{
		alert('Mandatory Field.');
		$('input[name="'+fieldName+'"]').addClass('error');
		$('input[name="'+fieldName+'"]').focus();
		$('input[name="'+fieldName+'"]').val(0);
		exit(0);
	} 
	
	
}
function exchange(id_curr)
{
	console.log(id_curr.value);
	$.post('P_Request_PR',{action : 'exchange_rate',id_curr : id_curr.value},function (r){
		
		if(r.data.length != 0)
			{
			console.log(r.data[0].rate);
			$('input[name="exchange_rate"]').val(r.data[0].rate);
			}
		else
			{
				bootbox.alert("Try again.");
			}
	},'json');
}
///////.char count.............
function charcountupdate(str) {
		var lng = str.length;
		document.getElementById("charcount").innerHTML = lng + ' out of 2000 characters';
	}

</script>


<!-- Content Wrapper. Contains page content -->
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1 id="directh1">
						Direct PO
						<button type="button" name="create btn" id="pobutton"
							class="btn btn-primary city"
							onclick="ControlDirectVerbalPurchaseOrder('Create','displaydirectVerbalOrder','createdirectVerbalOrder','submitdirectVerbalOrder','Verbal_Purchase_Order')">Create Direct PO</button>
					</h1>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	<div id="displaydirectVerbalOrder">
<div class="card">

	
		
				<table id="PRForDisplaydirect"
					class="table table-bordered table-hover PRForDisplaydirect">
		
						<tr>
							<td><strong>Invoice Number</strong></td>
							<td><strong>Invoice Date</strong></td>
							<td><strong>PO Number</strong></td>
							<td><strong>PO Date</strong></td>
							<td><strong>Quantity</strong></td>
							<td><strong>Vendor</strong></td>
							<td><strong><a href="#">Modify </a><a href="#">/ Delete</a></strong></td>
						</tr>
					</table>
				</div>
				</div>
		
	
	<div id="createdirectVerbalOrder" style="display:none;">
	
	
		<div class="card">
			
				<div class="card-header new">
					<h3 class="card-title1 new">Direct Purchase Order</h3>
				</div>



				<div class="card-body">
	
	<form name="submitdirectVerbalOrder" id="submitdirectVerbalOrder">
	<input type="hidden" name="others" value="0" >
	<input type="hidden" name="dt_quot" value="" class="datepicker">
	<input type="hidden" name="uploaded_file" value="">
	<!-- <input type="hidden" name="st_approv" value="Accepted" > -->
	
		<table class="table table-bordered" id="invoiceDetails" >
			
		   <tr>
		   	<td ><b>Raised  by<font color="red"> * </font></b></td>
				<td >
					<select name=add_by id="requestedPrByUser"  style="width:140" class="form-control " data-valid="mand" onchange="getDATAFromEMPuserForPO(this)">
						<option value="">--Select--</option>
						
				</select>
				</td>
					<td  ><b>PO Date<font color = 'Red'>*</font> </b></td>
				<td ><input type="text"  name="dt_po"  value="" class="form-control datepicker poDatepicker datepicForDissable readbaledata" data-valid="mand"></td>		
 			</tr>
	
				
				<!-- <td ><strong>PO Type <font color="red">*</font> :</strong></td>
				<td>
					<select id="type_po" class="form-control" name="type_po" data-valid="mand" onChange="SubDropDownDataDisplaystore(this,'patelTax','M_Tax');SubDropDownDataDisplaystore2(this,'patelTax2','M_Tax')">
						<option value="">Select</option>
						 <option value="Domestic">Domestic</option>
                         <option value="Overseas">Overseas</option>
                        <option value="Import">Import</option>
					</select>
				</td> -->
				 
				<tr>
					  <td><strong>Upload File</strong></td>
				<td><input id="fileID" type="file" name="file" class="form-control " value=""></td>
			  <td ><b>Delivery date<font color="red"> * </font></b></td>
			<td><input type="text" name="dt_po_delivery" value="" class="form-control datepicker poDatepicker" data-valid="mand"></td>			
                	 
			
				</tr>
			  <tr>	
				<td ><b>Reporting Manager<font color="red"> * </font></b></td>
				<td >
					<select name="repo_mngr" id="repoMngrPo"  style="width:140" class="form-control readbaledata" data-valid="mand" readonly> 
						<option value="1">--Select--</option>
						
				</select>
				</td>
				
				<td><b>Mode of Delivery</b></td>
				<td>
				<input type="text" name="mode_of_delv" value="" class="form-control" onkeyup="this.value = this.value.toUpperCase();">
			
			 
					</td>				
				</tr>
	      	<tr>
				
					
					 
				 	<td><b>Entity<font color="red">*</font></b></td>
						<td><select id="locDataForLead" name="id_loc"
							class="form-control" data-valid="mand" style="width: 140"
							onChange="DisplaySubDropDownData(this,'slocDataForLead','M_Subloc')">
								<option value="">Select</option>
						</select></td>
					
				
					
					
						<td><b>City<font color="red">*</font>
						</b></td>
						<td><select id="slocDataForLead" name="id_sloc" 
							class="form-control " data-valid="mand" style="width: 140" onchange="getDATA(this);SubDropDownDataDisplayforemp('','EmpdataforCordinator','M_Emp_User',this)">
								<option value="">Select</option>
						</select></td>
					<!-- 	<td><select id="slocDataForLead" name="id_sloc" 
							class="form-control " data-valid="mand" style="width: 140" onchange="getDATA(this); SubDropDownDataDisplay(this,'dgnForEmp','M_Designation')">
								<option value="">Select</option>
						</select></td> -->
						</tr>
				<tr>
				<td ><b>Ship To<font color="red"> * </font></b></td>
				<td><textarea cols="120" rows="5" class="form-control " name="ship_to" id="ship_to" style="margin: 0px 0px 10px;width: 300px;height: 100px;" onkeyup="this.value = this.value.toUpperCase();"></textarea></td>
				<td >
				<b>Bill To<font color="red"> * </font></b><br>
				<input type="checkbox" name="filladdress" id="filladdress" onclick="fillAddress()" /> Bill To address same as Ship To address.<br /></td>
				<td><textarea cols="120" rows="5" class="form-control " name="bill_to" id="bill_to" style="margin: 0px 0px 10px;width: 300px;height: 100px;"></textarea></td>
				
				</tr>
				<!--  <tr>
			  <td ><b>Submit Invoice till date<font color="red"> * </font></b></td>
			<td colspan="3"><input type="text" name="dt_po_delivery" value="" class="form-control datepicker poDatepicker" data-valid="mand"></td>
			 
			 </tr> -->
               	<tr>
               	
               		<input type="hidden" name="refund_amt" value="0.0" id="refund " class="form-control resetAcc FieldResize" onChange="addinTotLead(this)"></input>
					<input type="hidden" name="trnsprt_amt" value="0.0" id=" " class="form-control resetAcc FieldResize" onChange="addinTotLead(this)" ></input></tr>
					<input type="hidden" name="action" value="Save">
					<input  type="hidden" name="no_req_val" value="new">
					<input  type="hidden" name="itemCount" value="10">
					<input  id="id" type="hidden" name="id" value="">
					<input id="invoiceId" type="hidden" name="invoiceId" value="">
					<input type="hidden" name="typ_of_po" value="VO">
			 <td ><b>Remarks/Sub:</b></td>
		<!-- 		  <td colspan="3"><textarea style="width: 568px;height: 69px;" rows="6" cols="5"  name ="remarks" class="form-control "placeholder="500 Character Only..."></textarea> </td>
		 -->	<td colspan="3"><textarea  maxlength="2000" onkeyup="charcountupdate(this.value)" style="width: 568px;height: 69px;" rows="6" cols="5"  name ="remarks" id ="remarks" placeholder="2000 Character Only......"  class="form-control "></textarea><span id=charcount></span> </td>
			
			<!-- <td colspan="2"></td> -->
			</tr>
			</table>
		
	
	<div id="createAssetPR" style="display:none">
	<button name="save22" type="button" style=""   class="btn btn-primary" onclick="dispalyPOLineItemDynamically('Save')">Add</button>
		
		<form name="submitAssetPR" id="submitAssetPR">
		<table  class="table table-bordered " id="accessoryDetails2">
		
		
			
				
		</table>
		<table class="table table-bordered " id="accessoryDetails1">
		
		</table>
		<table style="display:none" id="print_mailpo">
		
		</table>		
			
		<input type="hidden" name="id_grp"   style="width:140" class="common-validatio resetAcc" readonly data-valid="mand"  style="width:140">
			<input type="hidden" name="id_sgrp"   style="width:140" class="common-validatio resetAcc" readonly data-valid="mand"  style="width:140">
			<input type="hidden" name="id_prod"   style="width:140" class="common-validatio resetAcc" readonly data-valid="mand"  style="width:140">
					
					
					
					
					<!-- <input  type="hidden" name="id_cc" value=""> -->
		<div id="CresteSaveUpdateButton">
					
						<button name="save" type="button" style="margin-left:400px;"   class="btn btn-primary req" onclick="ControlDirectVerbalPurchaseOrder('Save','displaydirectVerbalOrder','createdirectVerbalOrder','submitdirectVerbalOrder','Verbal_Purchase_Order')">Save</button>
						<!-- 
						<button name="save1" type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlVerbalPurchaseOrder('Save','displaydirectVerbalOrder','createdirectVerbalOrder','submitdirectVerbalOrder','Verbal_Purchase_Order')">Save</button>
						 -->
						<button name="update" type="button" style="margin-left:400px;"   class="btn btn-primary upd" onclick="ControlDirectVerbalPurchaseOrder('Save','displaydirectVerbalOrder','createdirectVerbalOrder','submitdirectVerbalOrder','Verbal_Purchase_Order')">Update</button>
						<button name="cancel" type="button"  class="btn btn-primary req" onclick="ControlDirectVerbalPurchaseOrder('Cancel','displaydirectVerbalOrder','createdirectVerbalOrder','submitdirectVerbalOrder','Verbal_Purchase_Order')">Cancel</button>
					
			</div>
	</form>
	</div>
	</div>
	</div>
	
	</div>
	