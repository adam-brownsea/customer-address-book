# Customer Address Book 

## SUMMARY OF THIS APPLICATION
- Java Corretto 17
- Spring Book 
- Gradle 7.4.2
- PostgreSQL Database
- Docker
- JUnit
- H2 embedded database for tests

## BUILD the application 
./gradlew build   

## BUILD AND UP Docker Compose 
docker-compose up --build   
docker-compose down <- down docker compose     

## CURLS 

### POST /addressBook  -- create an address book
curl -s -X POST \
  http://localhost:8080/addressBook \
  -H 'Content-Type: application/json' \
  -d '{ "name":"Fred Address Book" }'

```
{
    "id": 1,
    "name": "Fred Address Book"
}
```
### GET /addressBook -- retrieve all address books
curl -s -X GET \
  http://localhost:8080/addressBook 
  
```
[
  {
      "id": 1,
      "name": "Fred Address Book"
  },
  {
      "id": 2,
      "name": "Fred Address Book"
  }
]
```
### GET /addressBook/{id} -- retrieve address book via id
curl -s -X GET \
  http://localhost:8080/addressBook/1 
  
```
{
    "id": 1,
    "name": "Fred Address Book"
}
```
### POST /contact  -- create an contact
curl -s -X POST \
  http://localhost:8080/contact \
  -H 'Content-Type: application/json' \
  -d '{ "name":"Jenny", "phoneNumber": "0404198764", "addressBookId": 1 }'
```
{
    "id": 1,
    "name": "Jenny",
    "phoneNumber": "0404198764",
    "addressBookId": 1
}
```
### GET /contact -- retrieve all contacts
curl -s -X GET \
  http://localhost:8080/contact
```
[
    {
        "id": 1,
        "name": "Jenny",
        "phoneNumber": "0404198764"
    },
    {
        "id": 2,
        "name": "Bob",
        "phoneNumber": "0494129875"
    }
]
```
### GET /contact/{id} -- retrieve contact via id
curl -s -X GET \
  http://localhost:8080/contact/1 
```
{
        "id": 1,
        "name": "Jenny",
        "phoneNumber": "0404198764"
}
```
### GET /contact?addressBookId=1 -- retrieve all contacts for an address book
curl -s -X GET \
  http://localhost:8080/contact?addressBookId=1 


### GET /contact?unique=true -- retrieve all contacts with unique names 
curl -s -X GET \
  http://localhost:8080/contact?unique=true 

### PUT /contact/{id} -- update contact via id and address book id must match
curl -s -X PUT \
  http://localhost:8080/contact/1  
  
### DELETE /contact/{id} -- delete contact via id
curl -s -X DELETE \
  http://localhost:8080/contact/1  