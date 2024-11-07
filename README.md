#Assignment Description:
-----------------------------    


A rental agency manages properties near MTU's Bishopstown campus. which are rented to students. 

The rental agency records the following data about each property:

Address
Eircode
Capacity of property i.e. number of students it can hold
Cost of property rental per calendar month.
The rental agency stores the following information about each tenant:

Name
Email
Phone number
Several tenants can share the same name but must have unique emails and phone numbers. 

A house's address and eircode must be unique.

For each user store

email as primary key (Id) used for authentication
password (must be at least 8 characters, contain uppercase and lowercase letters along with digits -  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$"). 
locked (boolean) - true if account is locked, false if not locked
role (String which reflects if they are office staff or management staff) used for authorisation
phone number
PPSN
You are required to create a RESTful web service with appropriate status codes to

GET

get a list of all properties (excluding the tenants in that property)
get a list of properties that have availability in them (excluding tenants)
get a property given its id, along with the number of tenants currently in that house (see Hint below)
get a tenant given their id 
get a list of tenants in a property give its id
get total rental income of all occupied properties.
POST

add a new property
add a new user 
add a new tenant to a property subject to capacity restriction
DELETE

delete a property and delete the associated tenants
delete a tenant 
PATCH

move a tenant from one property to another subject to capacity restriction
change the rental rate of a property
Help on password patterns
This detail is not core to your learning so if you find this challenging, focus on other functionality.  

The password @Pattern constraint should not be applied to the password field in the Entity class. Instead create a new DTO class (record) which is similar to your User entity. Add the validation annotations to the fields in this record(e.g. @Email, @Pattern). Then create a user entity from the data in this record, encrypting the password in the constructor. This object may then be added to the database. 

Unit Tests
Write unit tests for the RESTful endpoints to test:

find property by Id endpoint if ID exists
find property by Id endpoint if ID does not exist
delete tenant by Id if ID exists and authorised
delete property by Id if authenticated but not authorised
delete tenant by Id if not authenticated
GraphQL
Create GraphQL services to:

get all properties offering all fields
get a single property offering all fields
delete a property
add a new tenant (also adding them to a property)
Security
Security must be applied to GraphQL as well as REST.

There are 2 types of authenticated user:

office staff
manager
Authorisations

Any user (authenticated or not) can view properties.
All authenticated users can view, create and edit (create/delete/patch) tenants 
Only managers can edit (create/delete/patch) properties.
Only managers can view and edit (create/delete/patch) users.
Anyone can view a list of properties or view a property's data, excluding tenant information.
AOP
Create 3 AOP logging points that demonstrate an aspect applied to a 

method
all methods in a class
all methods in all classes annotated with a particular annotation e.g. all @Controller classes.
