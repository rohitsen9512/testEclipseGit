<div style="padding:10px;">
	<!--  <a href="#">Master ></a><a href="#"> Product Master</a>-->
</div>

<div id="displayProduct">
		
		<table width="100%" height="100%">
			<tr>
				<td>
					<p style="width:180px;margin-left: 200px;float:right;">
					<button type="button" style="float:right;"  class="btn btn-primary" onclick="ControlDiv('Create','displayProduct','createProduct','')">Create Product</button>
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<table class="table table-bordered table-hover">
						<tr class="new">
							<td><strong>Product Name</strong></td>
							<td><strong>Product Code</strong></td>
							<td><strong>PO Terms</strong></td>
							<td><strong>Quote Terms</strong></td>
							<td><strong>Consumable PO Terms</strong></td>
							<td><strong>Service PO Terms</strong></td>
							<td><strong>Modify / Delete</strong></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</div>


<div id="createProduct" style="display:none;">
		
<form name="SubmitProduct" method="post">
		<table class="table table-bordered">
      <tr >
        <td colspan="4" style="background-color: blue;"><p style="font-size: 24px;background-color: blue;color: white;margin-left: 400px;">Product Configuration</p> </td>
      </tr>
      <tr>
        <td ><b>Product Name<font color="red">*</font></b></td>
        <td width="25%" class ="righttd"><input class ="text"name ="prod_name" ></td>
        <td ><b>Product Code <font color="red">*</font></b></td>
        <td width="25%" class ="righttd"><input class="text" name ="prod_code" ></td>
      </tr>
      <tr>
        <td ><b>Manufacturer<font color="red">*</font></b></td>
        <td width="25%" class ="righttd"><input class="text" name ="manufacturer" ></td>
        <td ><b>Model<font color="red">*</font></b></td>
        <td width="25%" class ="righttd"><input class="text" name ="model" ></td>
        
      </tr>
      <tr >
      <td ><b>Cost<font color="red">*</font></b></td>
        <td  width="25%" class ="righttd"><input class="text" name ="pr_cost" ></td>
      <td><b>Select Asset/Accessory<font color="red">*</font></b></td>
        <td >
	        <select class="select" name="asset_acc">
	            <option value="-" selected>&lt;Select&gt;</option>
	            <option value="0">Asset</option>
	            <option value="1">Accessory</option>
	       	 </select>
        </td>
        
	</tr>
      <tr> 
      	
	     <td ><b>Asset Group<font color="red">*</font></b></td>
       	 <td>
			<select class="select" name="asset_grp">
	            <option value="-" selected>&lt;Select&gt;</option>
	            <option value="0">Computer</option>
	            <option value="1">Plant And Machinery</option>
	       	 </select>
		</td>
      	<td ><b>Asset Sub Group<font color="red">*</font></b></td>
       	 <td>
			<select class="select" name="asset_sub_grp">
	            <option value="-" selected>&lt;Select&gt;</option>
	            <option value="0">Other</option>
	           
	       	 </select>
		</td>
       </tr>
      <tr> 
      	
	     <td ><b>Description</b></td>
        <td colspan="3">
	        <textarea rows="2" class="text" name="desc" style="width : 400px;">
			</textarea>
		</td>
      	
       </tr>
      
      <tr>
        <td height="36" colspan="4" align="center">
        	
        	<button type="button" style="margin-left:400px;"   class="btn btn-primary" onclick="ControlDiv('Save','','','SubmitProduct');">Save</button>
			<button type="button"  class="btn btn-primary" onclick="ControlDiv('Cancel','displayProduct','createProduct','');">Back</button>
					
        
       </td>
      </tr>
    </table>
	</form>
	</div>