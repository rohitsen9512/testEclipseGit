

function UploadFileItem(){
		$('input[name="action"]').val('uploditem');
		var x = $('#upload_prod').serialize();
		//alert(x);
		$.post('Upload_Asset',x,function (r){
			$('#dialogforAsset').dialog('close');
				bootbox.alert("Uploaded successfully.");
				$( ".Upload_Asset_event" ).trigger( "click" );
			
			
		});
	
	$( ".Upload_Asset_event" ).trigger( "click" );	
	}	
	var ExcelToJSON = function() {
		
	    this.parseExcel = function(file) {
	      var reader = new FileReader();

	      reader.onload = function(e) {
	        var data = e.target.result;
	        var workbook = XLSX.read(data, {
	          type: 'binary'
	        });
	        workbook.SheetNames.forEach(function(sheetName) {
	          // Here is your object
	        	if(sheetName=='Sheet1')
	        		{
	        	 console.log(sheetName);
	        	 console.log(workbook.Sheets[sheetName]);
	          var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
	          var json_object = JSON.stringify(XL_row_object);
	         // console.log(JSON.parse(json_object));
	var x=JSON.parse(json_object);
	//console.log(json_object);
	var list ='<form action="" name="upload_prod" id="upload_prod">	'+
		//	'<td colspan="10" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 350px;">Line Item Details</p></td></tr>'+ 
		'<table><tr>'+
		'<td colspan="18">	<button name="save" type="button" style="margin-left:150px;"   class="btn btn-primary" onclick="UploadFileItem();">Upload</button></td>'+
		'</tr>'+
		'<tr>'+
		'<input type="hidden" name="action" value="uploditem">'+
			'<td><strong>Invoice Number</strong></td>'+
			'<td><strong>Invoice Date</strong></td>'+
			'<td><strong>PO Number<font color="red">  </font></strong></td>'+
			'<td><strong>PO Date</strong></td>'+
			'<td><strong>GRN Number<font color="red">  </font></strong></td>'+
			'<td><strong>GRN Date</strong></td>'+
			'<td><strong>DC Number<font color="red">  </font></strong></td>'+
			'<td><strong>DC Date</strong></td>'+
			'<td><strong>Location</strong></td>'+
			'<td><strong>Sub Location</strong></td>'+
			'<td><strong>Building</strong></td>'+
			'<td><strong>Floor</strong></td>'+
			'<td><strong>Vendor<font color="red">  </font></strong></td>'+
			'<td><strong>Category<font color="red"> </font></strong></td>'+
			'<td><strong>Sub Category</strong></td>'+
			'<td><strong>Model/Item</strong></td>'+
			'<td><strong>Asset Type</strong></td>'+
			'<td><strong>Manufacturer</strong></td>'+
			'<td><strong>Unit Price</strong></td>'+
			'<td><strong>Tax1 Name</strong></td>'+
			'<td><strong>Tax2 Name</strong></td>'+
			'<td><strong>Tax1 Value</strong></td>'+
			'<td><strong>Tax2 Value</strong></td>'+
			'<td><strong>Serial No</strong></td>'+
			'<td><strong>Asset Ref. No.</strong></td>'+
			'<td><strong>Tagging</strong></td>'+
			'<td><strong>Processor</strong></td>'+
			'<td><strong>Storage Type</strong></td>'+
			'<td><strong>Ram Type</strong></td>'+
			'<td><strong>AMC/Warrenty</strong></td>'+
			'<td><strong>AMC/Warrenty Start Date</strong></td>'+
			'<td><strong>AMC/Warrenty End Date</strong></td>'+
			'<td><strong>Lease Status</strong></td>'+
			'<td><strong>Lease Start Date</strong></td>'+
			'<td><strong>Lease End Date</strong></td>'+
			'<td><strong>Type of Procurement</strong></td>'+
			'<td><strong>Device Status</strong></td>'+
			'<td><strong>Allocated To</strong></td>'+
			'<td><strong>Designation</strong></td>'+
			'<td><strong>Mail ID</strong></td>'+
			'<td><strong>Allocation Date</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="Invoice_Number'+i+'" value="'+(x[i].Invoice_Number).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Invoice_Date'+i+'" value="'+(x[i].Invoice_Date).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="PO_Number'+i+'" value="'+(x[i].PO_Number).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="PO_Date'+i+'" value="'+(x[i].PO_Date).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="GRN_Number'+i+'" value="'+(x[i].GRN_Number).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="GRN_Date'+i+'" value="'+(x[i].GRN_Date).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="DC_Number'+i+'" value="'+(x[i].DC_Number).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="DC_Date'+i+'" value="'+(x[i].DC_Date).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Location'+i+'" value="'+(x[i].Location).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Sub_Location'+i+'" value="'+(x[i].Sub_Location).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Building'+i+'" value="'+(x[i].Building).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Floor'+i+'" value="'+(x[i].Floor).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Vendor'+i+'" value="'+(x[i].Vendor).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Category'+i+'" value="'+(x[i].Category).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Sub_Category'+i+'" value="'+(x[i].Sub_Category).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Model'+i+'" value="'+(x[i].Model).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Asset_Type'+i+'" value="'+(x[i].Asset_Type).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Manufacturer'+i+'" value="'+(x[i].Manufacturer).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Unit_Price'+i+'" value="'+(x[i].Unit_Price).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Tax1_Name'+i+'" value="'+(x[i].Tax1_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Tax2_Name'+i+'" value="'+(x[i].Tax2_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Tax1_Percentage'+i+'" value="'+(x[i].Tax1_Percentage).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Tax2_Percentage'+i+'" value="'+(x[i].Tax2_Percentage).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Serial_No'+i+'" value="'+(x[i].Serial_No).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Asset_Ref_No'+i+'" value="'+(x[i].Asset_Ref_No).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Tagging'+i+'" value="'+(x[i].Tagging).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Processor'+i+'" value="'+(x[i].Processor).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Storage'+i+'" value="'+(x[i].Storage).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Ram_Type'+i+'" value="'+(x[i].Ram_Type).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="AMC_Warrenty'+i+'" value="'+(x[i].AMC_Warrenty).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Start_Date'+i+'" value="'+(x[i].Start_Date).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="End_Date'+i+'" value="'+(x[i].End_Date).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Lease_Status'+i+'" value="'+(x[i].Lease_Status).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Lease_Start_Date'+i+'" value="'+(x[i].Lease_Start_Date).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Lease_End_Date'+i+'" value="'+(x[i].Lease_End_Date).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Procurement_Type'+i+'" value="'+(x[i].Procurement_Type).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Device_Status'+i+'" value="'+(x[i].Device_Status).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Allocated_To'+i+'" value="'+(x[i].Allocated_To).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Assignee_Designation'+i+'" value="'+(x[i].Assignee_Designation).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Assignee_Mail'+i+'" value="'+(x[i].Assignee_Mail).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Allocation_Date'+i+'" value="'+(x[i].Allocation_Date).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	list+='</table></form>'+

	$('#dialogforAsset').html(list);
	
	 // $( "#dialogforAsset" ).dialog();
	  
	  
	  $( "#dialogforAsset" ).dialog({
		  
          width: 1000,
          height: 500,
          modal: true,
		  close: function( event, ui ) {
				$( ".ItemCart" ).trigger( "click" );
			 // window.location.reload(true); 
			  
		  }
		});
	        
	        		}
	        	});
	      };

	      reader.onerror = function(ex) {
	        console.log(ex);
	      };

	      reader.readAsBinaryString(file);
	    };
	};
	
////file upload............
	   document.getElementById('upload1').addEventListener('change', handleFileSelect, false);
	   function handleFileSelect(evt) {
		      var files = evt.target.files; // FileList object
		       var xl2json = new ExcelToJSON();
		       xl2json.parseExcel(files[0]);
		     }
	   
	