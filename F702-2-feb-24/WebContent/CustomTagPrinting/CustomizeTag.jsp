<!--Tag_printing.jsp-->

<script type ="text/javascript" src="All_Js_File/Tagging/tag_print.js"></script>
<!-- <script type ="text/javascript" src="All_Js_File/Asset/A_Add_To_Store.js"></script> -->
<script type="text/javascript">

$(function() {
	
	$('#DisplayAssetForTagPrint').hide();
	$('#PreviewAssetForTagPrint').hide();
	DisplayTagPrintPreview();
	
	
});
</script>


<div id="ChooseAttributeForTagPrint">
<div class="card">

					
<h1>Choose Attributes</h1>
 <form action="" name="submitOptions" id="submitOptions">
	
<table class="table table-bordered ">
  <thead class="new">
    <tr>
    
      <th scope="col">Attributes</th>
      <th scope="col">Order Number </th>

    </tr>
  </thead>
			<tr>
					<td>
					<select id="selattr" name="selattr">
					<option>Select</option>
					<option id="appNo" value = "1" >Asset Ref. Number</option>
					
					<option id="serial_no" value = "1">Serial No.</option>
					<option id="id_grp" value = "1">Category</option>
					<option id="id_sgrp" value = "1">Sub Category</option>
					<option id="ds_pro" value = "1">Model Number</option>
					<option id="id_wh_dyn" value = "1">Asset ID</option>
					<option id="id_loc" value = "1">Location</option>
					</select>
					</td>
				
				<td><input id="ord_num" required type="number" min="1" max="7" name="inputfield" ></td> 
				
				</tr>
			
				
				  <input type="hidden" name="action" value="Save">
					


 <tr> <td colspan="2"><center><button name="save" type="button"	class="btn btn-primary px-7 " onclick="SaveAttribute()">Save</button></center></td></tr>
										 
		</table>						
</form> 

</div>
<div class="card-small" >

<table class="table table-bordered displaytag"> 

</table>
<table>
<tr><center><td>&nbsp;&nbsp;<font face="'Free 3 of 9'" size="6">*SAMPLETAG*</font></td></center></tr></table>
</div>
</div>

