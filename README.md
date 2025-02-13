Below are the steps to run bank account(kafka producer) and log service(Kafka consumer)      
1.Bank Account service:  
  Steps to set up and build docker image:    	                        
  1.1 git clone https://github.com/anjaliarasu/bankaccountservices.git     
  1.2 cd bankaccountservices	        
  1.3 docker build -t gfg/producer .	      
  
2.Log Service:	
  Steps to set up and build docker image:              
  2.1 git clone https://github.com/anjaliarasu/loggerService.git	              
  2.2 cd loggerService	    
  2.3 docker build -t gfg/consumer .	    

3.Compose docker file present inside bankaccountservices	        
  3.1 cd bankaccountservices	    
  3.2 docker compose up -d	    

4.Now start both the application and start seeing results	    
  4.1 cd bankaccountservices	    
  4.2 mvn clean spring-boot:run	    
  4.3 cd loggingService	    
  4.4 mvn clean spring-boot:run	    

To Start with, please create user with unique email id's using. System has been designed that accounts can only be accessed by registered user.

1. Create user	
   Post: http://localhost:8086/users	
   Request body:	
   {"name":"feb13_1","email":"feb13_1@gmail.com"}	

   ![image](https://github.com/user-attachments/assets/dfc8e869-48f8-41f7-95b1-88415523ddb2)	

2. Create Account	
   Conditions:	
   1.Accounts can be created only for those users who is registered in the system using create user API, else below shown error comes	
     ![image](https://github.com/user-attachments/assets/950cc3f7-994b-4f94-bb08-938444ca6dfd)
   2.Accounts can be created only for those users who doesnt have an account already, if user with existing account passed below shown error comes
![image](https://github.com/user-attachments/assets/8f7282d2-aba7-46b5-a9c7-e574befae556)


Successful account creation: ![image](https://github.com/user-attachments/assets/a6265fda-8364-4e60-8db5-41df7a8b8772)


3.Deposit or Withdraw	
  Deposit:	
  Post: http://localhost:8086/user/feb13_2@gmail.com/account/2/deposit
{
      "amount":"5000"      
}
![image](https://github.com/user-attachments/assets/dc516a05-0338-4c17-b130-515cb449e117)

Withdraw:	
![image](https://github.com/user-attachments/assets/98668153-a344-42ba-b101-1472a7101500)






