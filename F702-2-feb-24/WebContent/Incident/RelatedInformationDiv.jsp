<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
jQuery('.tabs-2 .tab-links-2 a').on('click', function(e) {
	var currentAttrValue = jQuery(this).attr('href');

	// Show/Hide Tabs
	jQuery('.tabs-2 ' + currentAttrValue).show().siblings().hide();

	// Change/remove current tab to active
	jQuery(this).parent('li').addClass('active').siblings().removeClass('active');

	e.preventDefault();
});
</script>
</head>
<body>

<div class="tabs-2">
	 <div class="card card-primary card-outline card-outline-tabs">
<div class="card-header p-0 border-bottom-0">
	 <ul class="nav nav-tabs" id="custom-tabs-three-tab" role="tablist">
		<li class="nav-item"><a class="nav-link active" id="custom-tabs-two-home-tab" data-toggle="pill" href="#tab5" role="tab" >Task SLA</a></li>
		<li class="nav-item"><a class="nav-link " id="custom-tabs-two-home2-tab" data-toggle="pill" href="#tab6" role="tab">Child Incidents</a></li>
	</ul> 

	<div class="tab-content" id="custom-tabs-two-tabContent">
	
		<div id="tab5" class="tab active">
		<table id="displayDataForSLA"
					class="table table-bordered table-hover displayDataForSLA">	
					</table>
			</div>

		
		<div id="tab6" class="tab">
		
		
				
				 
				<input type="hidden" name="id_parent" id="id_parent"  class="form-control readbaledata" >
				
				
	<button type="button" class="btn btn-primary" name="create btn" style="float: right; margin-bottom:10px;"
						onclick="GoToCreateincident($('#id_parent').val())">Create Incident</button>
				
		<table	id="displayDataForChildIncident"
					class="table table-bordered table-hover displayDataForChildIncident">			

	
		
	</table> 
				
			
				
		</div>

		
	</div>
</div>
	</div>
	</div>
	</body>
	
</html>