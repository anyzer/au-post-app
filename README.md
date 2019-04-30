### Project Overview

Connect to AU Post RESTFul API to GetAccount, CreateShipment, CreateLabel, CreateOrderFromShipment and GetOrderSummary

### Deploy
    * sbt clean stage
    * copy target/universal/stage to execution folder
    * copy all files from script/ to execution folder
    
### Execution
    * change config in the file <PROJECT_CONFIG>
    * create folder <order_summary> if not exists
    * run script: ./run_jobs.sh  
 
