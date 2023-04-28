### Technical specification:
https://github.com/JavaWebinar/topjava/blob/doc/doc/graduation.md#technical-requirement

#### Glossary:

- Restaurant: place added by admins that has a menu (list of dishes)

- Dish:  menu item at a restaurant with name and price

- Client: client who use my API: admin or user

- Vote: a unique choice of user for a specific restaurant

### API specification:
- http://localhost:8080/swagger-ui.html

#### Endpoints to test the application:
Basic Auth for the user: `user@gmail.com / password`
Basic Auth for the admin: `admin@gmail.com / admin`
1) Add new restaurant if admin
- POST http://localhost:8080/api/admin/restaurants/new
- RequestBody without dishes: `{}`
- RequestBody with dish(es): `{    
  "dishes": [{
  "name": "dish5",
  "price": 10
  }]    
  }`
2) Update dish of the specified restaurant if admin
- localhost:8080/{user_id}/menu/{dish_id}/update
- RequestParams: user_id=2, dish_id=4
- RequestBody : `{    
  "name": "dish4",
  "price": 44
  }`
3) Add new dish to the specified restaurant if admin
- localhost:8080/{user_id}/menu/add/{restaurant_id}/
- RequestParams: user_id=2, restaurant_id=2
- RequestBody : `{    
  "name": "newTea",
  "price": 15
  }`

4) Disable (not Remove for audit) the specified dish if admin
- localhost:8080/{user_id}/menu/{dish_id}/disable
- RequestParams: user_id=2, dish_id=3

#### Access H2 console to view DB:
- http://localhost:8080/h2-console

#### UML for VOTING DB:
https://app.quickdatabasediagrams.com/
![DB scheme](https://github.com/lsolovyeva/voting-service/blob/master/database%20scheme.png)

#### Tech stack:
- Java 17
- Maven
- Spring Boot 3.0
- H2 DB
- Junit 5

#### Notes:
- *ServiceImpl classes are omitted for simplicity
- Catalog=DB=VOTING, Schema=namespace=VOTES
- Test coverage report: 95% classes and 80% lines covered 
