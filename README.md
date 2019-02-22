## ScbBookStoreAssignment

This is an assignment from SCB to develop RESTful APIs for a bookstore.

#### Project setup
1. Launch Spring Tool Suite (Tested with version 4.1.1) with any workspace.
2. File -> Import... -> Gradle -> Existing Gradle Project.
3. Browse to scbBookStoreAssignment\project\scbBookStoreRestApi for Project root directory, then click Finish.
4. Right click scbBookStoreRestApi project in Package Explorer -> Run As -> Spring Boot App to start the server.
5. Right click scbBookStoreRestApi project in Package Explorer -> Run As -> JUnit Test to run the test scripts. (The server will stop after finish the test)

#### Test the project manually

After the server has started, you can run cURL script in scbBookStoreAssignment\document\curl\curl.txt (For Windows CMD, use curl-windows.txt)
Note that you must replace token parameter with your token received from the login request.

#### Document

- ERD => scbBookStoreAssignment\document\database
- Sequence diagrams => scbBookStoreAssignment\document\sequenceDiagram
- Swagger document => scbBookStoreAssignment\document\swagger or https://app.swaggerhub.com/apis-docs/kongkana/scbBookStoreAssignment/1.0.0

For more information, please contact kongkana@gmail.com
