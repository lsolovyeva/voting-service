### Technical specification
https://github.com/JavaWebinar/topjava/blob/doc/doc/graduation.md#technical-requirement

#### Glossary

- *Restaurant*: place added by admins that has a menu (list of dishes)

- *Dish*:  menu item at a restaurant with name and price

- *Client*: client who use my API: admin or user

- *Vote*: a unique choice of user for a specific restaurant

### API specification
- http://localhost:8080/swagger-ui.html (http://localhost:8080/swagger-ui/index.html)

#### Endpoints to test the application
Basic Auth for the user: `user@gmail.com / password` <br>
Basic Auth for the admin: `admin@gmail.com / admin`

1) Add new restaurant if admin
   - POST http://localhost:8080/api/admin/restaurants
   - RequestBody `{ "name": "newRestaurant" }`

2) Update restaurant if admin
   - PUT http://localhost:8080/api/admin/restaurants/{restaurant_id}
   - RequestParam: restaurant_id=1
   - RequestBody `{ "name": "updatedRestaurant" }`

3) Add new dish to the specified restaurant if admin 
   - POST http://localhost:8080/api/admin/dishes?{restaurant_id}
   - RequestParams: restaurant_id=1
   - RequestBody : `{    
    "name": "newTea",
    "price": 15
    }`

4) Update dish of the specified restaurant if admin
   - PUT http://localhost:8080/api/admin/dishes/{dish_id}
   - RequestParams: dish_id=4
   - RequestBody : `{    
     "name": "dish4",
     "price": 44
     }`

5) Vote for the specified restaurant if user
   - POST http://localhost:8080/api/user/votes?{restaurant_id}
   - RequestParams: restaurant_id=1

6) Get all dishes for the specified restaurant if guest
   - GET http://localhost:8080/api/dishes?{restaurant_id}
   - RequestParams: restaurant_id=1

7) Get all restaurants if guest
   - GET http://localhost:8080/api/restaurants

8) Get number of votes for the specified restaurant if guest
   - GET http://localhost:8080/api/votes?{restaurant_id}
   - RequestParams: restaurant_id=1

#### Access H2 console to view DB
http://localhost:8080/h2-console

#### UML for VOTING DB
https://app.quickdatabasediagrams.com/ <br>
![DB scheme](https://github.com/lsolovyeva/voting-service/blob/master/database%20scheme.png)

#### Tech stack
- Java 17
- Maven
- Spring Boot 3.0 (Web, MVC, Data JPA, Cache, Security, Test)
- H2 DB
- Junit 5
- Swagger

#### Notes
- *ServiceImpl classes are omitted for simplicity
- Catalog=DB=VOTING, Schema=namespace=VOTES
- Test coverage report: 100% classes and 83% lines covered
