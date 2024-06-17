
<html>
  <script type="text/javascript">
  $('#todaydashboard').hide();
</script>
  
<body>
<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>
						<!--  Incident-->
						
					</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
				
						<li class="breadcrumb-item"><a href="#">Incident</a></li>
						<li class="breadcrumb-item">Dashboard</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	<div class="card-small" id="incidentDashboard">
	<center>
  
           <input type="radio" id="radio1" class="radiooverview" name="radios" value="all" onclick="divdash()" checked>
       <label for="radio1">Overall</label>
    <input type="radio" id="radio2" name="radios" class="radiooverview" value="false" onclick="divdash('today')" >
       <label for="radio2">Today</label>
    
        
    </center>
	<br>
	<div id ="incidashboard" class="incidashboard show">
<div class="row">
        </div>
        </div>
        <div id ="todaydashboard"class="todaydashboard">
<div class="row">
        </div>
        </div>
        <!-- /.row -->

        <!-- Small Box (State card) -->
        <div class="row">
          <div class="col-md-6">
						<div class="card">

							<div class="card-header new ">
								<h5 class="card-title">Priority Wise Incident Details</h5>

								<div class="card-tools">
									<button type="button" class="btn btn-tool"
										data-card-widget="collapse">
										<i class="fas fa-minus"></i>
									</button>
									
									<button type="button" class="btn btn-tool"
										data-card-widget="remove">
										<i class="fas fa-times"></i>
									</button>
								</div>
							</div>
<div class="card-body">
							<div class="chart">
								<canvas id="myChart" width="400px" height="400px"></canvas>

							</div>
						</div>
					</div>
        
        </div>
        <div class="col-md-6">
						<div class="card">

							<div class="card-header new">
								<h5 class="card-title">State Wise Incident Details</h5>

								<div class="card-tools">
									<button type="button" class="btn btn-tool"
										data-card-widget="collapse">
										<i class="fas fa-minus"></i>
									</button>
									
									<button type="button" class="btn btn-tool"
										data-card-widget="remove">
										<i class="fas fa-times"></i>
									</button>
								</div>
							</div>
<div class="card-body">
							<div class="chart">
								<canvas id="stateChart" width="400px" height="400px"></canvas>

							</div>
						</div>
					</div>
        
        </div>
        <!-- /.row -->
        
         </div>
         </div>
         </body>
         </html>
         <script>
         $(function() {
          incidentDashboard(function(status) { 
				 if (status) { 
					 setTimeout(function() {
						//do what you need here
						
								
								dashurgency(function(status) {
							
							if(status) {
								 setTimeout(function() {
									   dashstate();   
								 },100);
								 }});
								
							
					 },100);
				 }}); 								 
         
      
         });
         
         TodayDash();  
</script>
      