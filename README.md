# Assignment 1 - URL Shortner API Documentation

## Used Technologies
1. Java 17
2. Spring Boot Version 3.3.2
3. Maven
4. MySQL DB (In my local system I have installed only MySQL DB that is why i used MySQL DB instead of PostgreSQL )

### Used Dependencies
1. spring-boot-starter-web
2. spring-boot-starter-data-jpa
3. mysql-connector-j
4. lombok (For avoid boilerplate code)

## Instructions to run project
1. Clone 
   ```
   git clone https://github.com/Nandivaleamol/amol_vt_25_08_2024.git
   ```
2. Change DB credentials (
    If in your machine PostgreSql installed then change spring.datasource.url=jdbc:postgresql://localhost:<PORT>/<DB-NAME> )
3. Open project In IntelliJ Idea IDE (If installed)
4. Build the project using maven commands 
    ```
    mvn clean package
    ```
5. Execute the generated .jar file (target/*.jar)
    ```
     java -jar  app.jar
    ```
6. Embedded tomcat server will start on port 8080

## REST API DOCUMENTATION
1. Add new ORIGINAL url -> POST
    ```
   http://localhost:8080/shorten?destinationUrl=https://www.instagram.com
   ```
   Request: 
    RequestParam:
      destinationUrl=https://www.instagram.com
    Response: 
    ```
   {
    "id": 1,
    "shortUrl": "xZcgV_2_GOSpzg",
    "destinationUrl": "https://www.instagram.com",
    "expiryDate": "2025-06-15T21:24:49.277309"
   }
   ```
2. Redirect URL using shorten url -> GET
    ```
   http://localhost:8080/<shorten-url>
   
   Eg. http://localhost:8080/Kd82EJzD2ejOaQ
   ```
3. Update SHORT url -> POST : http://localhost:8080/shorten
  - Request Parameters
    - shortUrl
    - destinationUrl
  ```
    http://localhost:8080/shorten?destinationUrl=https://www.facebook.com&shortUrl=http://localhost:8080/5mMH8mMu
  ```
4. Retrieve URL details -> GET
    - Retrieve URL detail by its ID.
      - Request parameter -> id
   ```
   http://localhost:8080/get?id=1
    ```
   - Response:
   ```
   {
    "id": 1,
    "shortUrl": "tArlWW6p",
    "destinationUrl": "https://www.google.com",
    "expiryDate": "2025-06-15T18:40:08.250294"
    }
   ```
5. Retrieve all URL details
    - HTTP Method : GET
    - API URL : `http://localhost:8080/get/all`
    - Response:
   ```
   [
    {
        "id": 1,
        "shortUrl": "tArlWW6p",
        "destinationUrl": "https://www.google.com",
        "expiryDate": "2025-06-15T18:40:08.250294"
    },
    {
        "id": 2,
        "shortUrl": "hA1pYofX",
        "destinationUrl": "https://www.youtube.com",
        "expiryDate": "2025-06-15T19:19:02.673874"
    },
    {
        "id": 3,
        "shortUrl": "qNBXPF2e",
        "destinationUrl": "https://www.youtube.com",
        "expiryDate": "2025-06-15T19:42:38.886883"
    },
    {
        "id": 4,
        "shortUrl": "CXk4U33Vh4vwWg",
        "destinationUrl": "https://www.linkedin.com",
        "expiryDate": "2025-06-15T19:47:33.815272"
    },
    {
        "id": 5,
        "shortUrl": "NSXhi6a89PEd4w",
        "destinationUrl": "https://www.yahoo.com",
        "expiryDate": "2025-06-15T19:49:52.799197"
    },
    {
        "id": 6,
        "shortUrl": "Kd82EJzD2ejOaQ",
        "destinationUrl": "https://www.yahoo.com/",
        "expiryDate": "2025-06-15T19:54:56.190188"
    },
    {
        "id": 7,
        "shortUrl": "n1tag7lgQ9jWQA",
        "destinationUrl": "https://www.facebook.com",
        "expiryDate": "2025-06-15T21:23:43.150164"
    }
   ]
   ```