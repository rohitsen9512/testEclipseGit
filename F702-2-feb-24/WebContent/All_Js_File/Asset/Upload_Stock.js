

function UploadFileItem(){
		$('input[name="action"]').val('uploditem');
		var x = $('#upload_prod').serialize();
		//alert(x);
		$.post('Upload_Excel',x,function (r){
			$('#dialog').dialog('close');
				bootbox.alert("Uploaded successfully.");
				$('#dialog').dialog('close');
				window.location = $('.addToStore1_event').attr('href');
				$('#dialog').dialog('close');
			
			
		});
	
		window.location = $('.addToStore1_event').attr('href');
		$('#dialog').dialog('close');
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
			'<td><strong>Vendor<font color="red">  </font></strong></td>'+
			'<td><strong>Location<font color="red">  </font></strong></td>'+
			'<td><strong>Sub_Location<font color="red">  </font></strong></td>'+
			'<td><strong>Building<font color="red">  </font></strong></td>'+
			'<td><strong>Floor<font color="red">  </font></strong></td>'+
			'<td><strong>Department<font color="red">  </font></strong></td>'+
			'<td><strong>Group<font color="red"> </font></strong></td>'+
			'<td><strong>Sub Group</strong></td>'+
			'<td><strong>Model</strong></td>'+
			'<td><strong>Unit Price</strong></td>'+
			'<td><strong>Tax1_Name</strong></td>'+
			'<td><strong>Tax1_Percentage</strong></td>'+
			'<td><strong>Tax2_Name</strong></td>'+
			'<td><strong>Tax2_Percentage</strong></td>'+
			'<td><strong>Serial No</strong></td>'+
			'<td><strong>Asset Type</strong></td>'+
		'</tr>';
	for(var i = 0; i < x.length ; i++)
	{
		
		list+='<tr>'+
		'<td><input type="text" style="padding: 0px;width: 248px;"  name="Invoice_Number'+i+'" value="'+(x[i].Invoice_Number).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Invoice_Date'+i+'" value="'+(x[i].Invoice_Date).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="PO_Number'+i+'" value="'+(x[i].PO_Number).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="PO_Date'+i+'" value="'+(x[i].PO_Date).trim()+'" class="common-validation FieldResize datepickerexcel"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Vendor'+i+'" value="'+(x[i].Vendor).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Location'+i+'" value="'+(x[i].Location).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Sub_Location'+i+'" value="'+(x[i].Sub_Location).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Building'+i+'" value="'+(x[i].Building).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Floor'+i+'" value="'+(x[i].Floor).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Department'+i+'" value="'+(x[i].Department).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Group'+i+'" value="'+(x[i].Group).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Sub_Group'+i+'" value="'+(x[i].Sub_Group).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Model'+i+'" value="'+(x[i].Model).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Unit_Price'+i+'" value="'+(x[i].Unit_Price).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Tax1_Name'+i+'" value="'+(x[i].Tax1_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Tax1_Percentage'+i+'" value="'+(x[i].Tax1_Percentage).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Tax2_Name'+i+'" value="'+(x[i].Tax2_Name).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Tax2_Percentage'+i+'" value="'+(x[i].Tax2_Percentage).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Serial_No'+i+'" value="'+(x[i].Serial_No).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<td><input type="text"style="padding: 0px;width: 248px;"  name="Asset_Type'+i+'" value="'+(x[i].Asset_Type).trim()+'" class="common-validation FieldResize"  patelUnPrc="'+i+'"></td>'+
		'<input type="hidden" name="count" value="'+i+'">'+		
	'</tr>';
		
	}
	list+='</table></form>'+

	$('#dialog').html(list);
	
	 // $( "#dialog" ).dialog();
	  
	  
	  $( "#dialog" ).dialog({
		  
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
	   document.getElementById('upload').addEventListener('change', handleFileSelect, false);
	   function handleFileSelect(evt) {
		      var files = evt.target.files; // FileList object
		       var xl2json = new ExcelToJSON();
		       xl2json.parseExcel(files[0]);
		     }
	   
	