### Project Overview

Connect to AU Post RESTFul API to GetAccount, CreateShipment, CreateLabel, CreateOrderFromShipment and GetOrderSummary

### Deploy
    * sbt clean stage
    * copy target/universal/stage to execution folder
    * copy all files from script/ to execution folder
    
### Environment
    * test
    * prod

### Execution
    * change config in the file <PROJECT_CONFIG>
    * create folder <order_summary> if not exists
    * run script: ./run_jobs.sh  
    * order summary should be saved in the folder order_summary
    * order summary naming convention: [account_orderId_yyyyMMdd_hhMMss.pdf]
        * Example: 1003498426_TB00079055_20190430_211254.pdf 
 
 
