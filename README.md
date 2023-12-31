# Santechture assignment.

### Service Description:

First, We have attached two files, the first is the source code for the Spring Boot project, and the second is the postman
collection containing three APIs for testing:
- Admin / Login
- User / list
- User / Add new account
 # The source code includes the code of the previous resources, and we have used the H2 database in order to save time.
  The Assignment:
  In order to prevent unauthorized access to resources, you must implement JWT security in the previous source code so
  that when the administrator successfully calls the Admin Login API, he/she will receive the JWT Token in response (in
  addition to account details) so that it can call other resources. Then when the administrator calls User / Add new
  Account You must log out the current logged-in admin username and id
  Notes
- All other APIs should be secured with JWT token.
- Do not store any sensitive data in JWT body.
- When you run the project, you can directly call and test the previous APIs.
  Timeframe and delivery:
  You should complete the test in 40 Min and export your postman collection, and the source code on public Git.
- 
### Current Solution Description:
* Tech stack:
  * Java 17
  * Spring boot 3
  * H2 Database

# Solution
### Solution points 
- Adding security dependency: to activate the security module.
- Adding WebSecurityConfig: to configure the beans that we will use in the security module.
- Adding CustomUserDetailsService: to implement the UserDetailsService for loading the users with their roles.

### Additional Information
- The admin password is encoded using passwordEncoder.
- added the postman collection in folder (postman-collection)

## And here is the Flowchart for the security solution module:

![alt text](https://github.com/elshabrawy/admin-test-api/blob/main/Security-chart.png?raw=true)
