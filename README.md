# Team/Group Number: 18

### Team Members:
- Younes Messahel - ym218@student.le.ac.uk
- Raul Blanco Vazquez - rbv3@student.le.ac.uk
- Daniel Stewart - ds825@student.le.ac.uk

---

### How to Run the Project:

1. **Set up the MySQL Database:**
   - Create a database named `ibmhackathon`and Create a user with the username `user` and password `password`.
   - Ensure the user has permissions to access and use the database.

2. **Run the Project:**
   - To run the project, run the `FinanceAdviceChatbotApplication.java` file.

3. **Setting Up API Key:**
   - The project requires a Hugging Face API key. You'll need to set this up first.

   - **If you have the Zipped Folder**:
     - The necessary API key has been implemented, so the project should run without any issues.

   - **If using the GitHub repository**:
     - We cannot commit the API key to GitHub for security reasons.
     - You'll need to create a Hugging Face account and generate an Access Token.
     - You'll then need to store the token. There are two methods to this :
         - Replace ${OPENAI_API_KEY} in application.properties with your API key, if you are running the project locally
         - Store your API key as an Environment Variable, OPENAI_API_KEY, using the instructions below : 
             -setx OPENAI_API_KEY "your_api_key_here"
             - You can verify this worked using the command : echo %OPENAI_API_KEY%
    
4. **Running the Project:**
   - Once the API key is set up, the project should run successfully.

