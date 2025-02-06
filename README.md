# **Financial Institution**

## **Overview**
This project provides a RESTful API for managing financial institutions, users, roles, and permissions. It includes authentication, role-based access control, and dynamic CRUD operations.

## **Authentication**
- The API uses **Bearer Token Authentication**, which is obtained via the login API.
- Tokens are valid for **1 hour** (configurable in `application.properties`).
- Each API request must include the token in the **Authorization** header.

## **Database Schema**
The following tables are defined in `schema.sql` and populated in `data.sql`:

### **Users Table**
Defines users and their types:
- **Admin**
- **User**

### **Role Table**
Defines roles:
- **Admin Role**
- **User Role**

### **User_Role Table**
Maps users to their roles (a user can have multiple roles).

### **Permission Table**
Defines permissions for each role:
- **Admin inherits all permissions from Admin & User roles**

## **Authentication API**
### **Login API**
- **Admin Login:**
  ```json
  {
    "username": "admin",
    "password": "12345"
  }
  ```
- **User Login:**
  ```json
  {
    "username": "user",
    "password": "54321"
  }
  ```
- The response includes a **Bearer Token** that must be used to authorize requests.

## **Institution API**
### **Institution Model**
- `Institution Code`: Unique, numeric, max length **5**
- `Institution Name`: Required, max length **50**
- `Institution Status`: **0** (Disabled) / **1** (Enabled)
- `Currency List`: Example: **LBP, USD**

### **Available Endpoints**
- `GET /institutions/active` → Get **active** institutions (status = 1)
- `GET /institutions/{id}` → Get institution by **ID**
- `GET /institutions` → Get **all** institutions
- `POST /institutions` → **Create/Update** institution (if `id = 0`, create; otherwise, update)
- `DELETE /institutions/{id}` → Delete institution by **ID**

### **Permissions**
- **ROLE_D_INST** → Only **Admin** can delete institutions
- **ROLE_CU_F_INST** → All users can **Create/Update/Find** institutions
- If a user **without permission** tries to delete, they receive: `Access Denied`

## **Base Entity**
- **Generic base entity** (`BaseEntity`) includes:
    - `status`: **ACTIVE/INACTIVE** (saved in DB as **1/0**)
    - `dateCreated`: Automatically **set**

## **Project Structure**
- **Controllers:** Handle API requests (`CUDController`, `FindAllController`, `FindByIdController`)
- **Services:** Implement business logic
- **Exception Handling:** Custom exception handlers for meaningful error messages
- **Common Response DTO:**
    - `RestCommonResponseDTO` ensures a consistent response format

## **Running the Application**
- The application runs on **port 9092**
- Swagger UI is available at: **[http://localhost:9092/swagger-ui.html#/](http://localhost:9092/swagger-ui.html#/)**
- **Authorize API calls** in Swagger by adding the **Bearer Token**

## **Test Requirements**
1. **Database:** Uses embedded **H2 database**.
2. **API Implementations:**
    - Get all institutions
    - Get institution by ID
    - Get active institutions (status = 1)
    - Create/Update institution based on `id`
    - Delete institution by ID
    - Login API for authentication (returns bearer token)

Happy Coding! 😊 😊 😊 
