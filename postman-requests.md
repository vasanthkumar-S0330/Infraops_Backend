# InfraOps Backend API - Postman / cURL Requests

Here are the example requests to test the API endpoints you requested.

## 1. User Registration (`POST /api/auth/register`)

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Vasanth",
    "lastName": "Kumar",
    "email": "vasanth@gmail.com",
    "password": "mySecurePassword123",
    "role": "ADMIN"
  }'
```

**Expected Response (200 OK):**
```
User registered successfully!
```

---

## 2. User Login (`POST /api/auth/login`)

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "vasanth@gmail.com",
    "password": "mySecurePassword123"
  }'
```

**Expected Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userId": "648a1b2c3d4e5f6g7h8i9j0k",
  "email": "vasanth@gmail.com",
  "role": "ADMIN"
}
```
*(Copy the `"token"` value for the next requests)*

---

## 3. Get User Profile (`GET /api/auth/profile`)

Replace `<YOUR_TOKEN>` with the token received from the Login API.

```bash
curl -X GET http://localhost:8080/api/auth/profile \
  -H "Authorization: Bearer <YOUR_TOKEN>"
```

**Expected Response (200 OK):**
```json
{
  "id": "648a1b2c3d4e5f6g7h8i9j0k",
  "firstName": "Vasanth",
  "lastName": "Kumar",
  "email": "vasanth@gmail.com",
  "role": "ADMIN",
  "createdAt": "2026-06-26T10:00:00"
}
```

---

## 4. Update User Profile (`PUT /api/auth/profile`)

Replace `<YOUR_TOKEN>` with the token received from the Login API.

```bash
curl -X PUT http://localhost:8080/api/auth/profile \
  -H "Authorization: Bearer <YOUR_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Vasanth",
    "lastName": "Updated"
  }'
```

**Expected Response (200 OK):**
```json
{
  "id": "648a1b2c3d4e5f6g7h8i9j0k",
  "firstName": "Vasanth",
  "lastName": "Updated",
  "email": "vasanth@gmail.com",
  "role": "ADMIN",
  "createdAt": "2026-06-26T10:00:00"
}
```
