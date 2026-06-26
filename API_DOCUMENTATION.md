# InfraOps Backend API Documentation

This document provides a comprehensive guide to all available REST API endpoints in the InfraOps Backend system. You can use these specifications to integrate the backend with your frontend application.

## Authentication Overview

Most endpoints require a JWT token for authorization. 
Include it in the HTTP headers of your requests:
```
Authorization: Bearer <YOUR_JWT_TOKEN>
```

> **Roles**: Some APIs are restricted to specific user roles (`ADMIN`, `ENGINEER`). Ensure the authenticated user has the necessary privileges.

---

## 1. Authentication APIs (`/api/auth`)

### 1.1 Register User
- **URL**: `POST /api/auth/register`
- **Access**: Public
- **Description**: Register a new user in the system.
- **Request Body**:
```json
{
  "firstName": "String",
  "lastName": "String",
  "email": "String (Valid Email)",
  "password": "String",
  "role": "ADMIN or ENGINEER"
}
```
- **Response** (200 OK): `"User registered successfully!"`

### 1.2 Login User
- **URL**: `POST /api/auth/login`
- **Access**: Public
- **Description**: Authenticate and retrieve a JWT token.
- **Request Body**:
```json
{
  "email": "String",
  "password": "String"
}
```
- **Response** (200 OK):
```json
{
  "token": "JWT_STRING",
  "userId": "String",
  "email": "String",
  "role": "String"
}
```

### 1.3 Get Own Profile
- **URL**: `GET /api/auth/profile`
- **Access**: Authenticated (Any Role)
- **Description**: Retrieve the profile of the currently logged-in user.
- **Response** (200 OK):
```json
{
  "id": "String",
  "firstName": "String",
  "lastName": "String",
  "email": "String",
  "role": "String",
  "createdAt": "Timestamp"
}
```

### 1.4 Update Own Profile
- **URL**: `PUT /api/auth/profile`
- **Access**: Authenticated (Any Role)
- **Description**: Update the logged-in user's name details.
- **Request Body**:
```json
{
  "firstName": "String",
  "lastName": "String"
}
```
- **Response** (200 OK): Returns the updated profile object.

---

## 2. Asset Management APIs (`/api/assets`)
**Access Requirement**: `ADMIN` or `ENGINEER`

### 2.1 Create Asset
- **URL**: `POST /api/assets`
- **Description**: Create a new infrastructure asset.
- **Request Body**:
```json
{
  "name": "String",
  "type": "SERVER, NETWORK, DATABASE, etc.",
  "ipAddress": "String",
  "location": "String",
  "status": "ACTIVE, INACTIVE, MAINTENANCE"
}
```
- **Response** (200 OK): `{"message": "Asset created successfully"}`

### 2.2 Get All Assets (Paginated)
- **URL**: `GET /api/assets`
- **Query Parameters**:
  - `page` (default: 0)
  - `size` (default: 10)
  - `sortBy` (default: "createdAt")
  - `sortDir` (default: "desc" / "asc")
- **Response** (200 OK): Spring Boot Pageable object containing an array of assets.

### 2.3 Search Assets
- **URL**: `GET /api/assets/search`
- **Query Parameters**:
  - `query` (Required: String to search for)
  - `page`, `size`, `sortBy`, `sortDir`
- **Response** (200 OK): Pageable object containing matching assets.

### 2.4 Get Asset By ID
- **URL**: `GET /api/assets/{id}`
- **Response** (200 OK): Asset object details.

### 2.5 Update Asset
- **URL**: `PUT /api/assets/{id}`
- **Request Body**: Same as Create Asset (`AssetRequest`).
- **Response** (200 OK): `{"message": "Asset updated successfully"}`

### 2.6 Delete Asset
- **URL**: `DELETE /api/assets/{id}`
- **Response** (200 OK): `{"message": "Asset deleted successfully"}`

---

## 3. Ticket Management APIs (`/api/tickets`)
**Access Requirement**: `ADMIN` or `ENGINEER`

### 3.1 Create Ticket
- **URL**: `POST /api/tickets`
- **Description**: Create a new support/issue ticket.
- **Request Body**:
```json
{
  "title": "String",
  "description": "String",
  "priority": "LOW, MEDIUM, HIGH, CRITICAL",
  "status": "OPEN, IN_PROGRESS, RESOLVED, CLOSED",
  "assetId": "String (Optional)"
}
```
- **Response** (200 OK): `{"message": "Ticket created successfully"}`

### 3.2 Get All Tickets (Paginated)
- **URL**: `GET /api/tickets`
- **Query Parameters**: `page`, `size`, `sortBy` (default: createdAt), `sortDir`
- **Response** (200 OK): Pageable object containing tickets.

### 3.3 Get Ticket By ID
- **URL**: `GET /api/tickets/{id}`
- **Response** (200 OK): Ticket object details.

### 3.4 Update Ticket
- **URL**: `PUT /api/tickets/{id}`
- **Request Body**: Same as Create Ticket (`TicketRequest`).
- **Response** (200 OK): `{"message": "Ticket updated successfully"}`

### 3.5 Update Ticket Status Only
- **URL**: `PUT /api/tickets/{id}/status`
- **Request Body**:
```json
{
  "status": "OPEN, IN_PROGRESS, RESOLVED, CLOSED"
}
```
- **Response** (200 OK): `{"message": "Ticket status updated successfully"}`

### 3.6 Delete Ticket
- **URL**: `DELETE /api/tickets/{id}`
- **Response** (200 OK): `{"message": "Ticket deleted successfully"}`

---

## 4. User Management APIs (`/api/users`)
**Access Requirement**: Primarily `ADMIN` (Some view APIs allow self-access)

### 4.1 Create User (Admin Only)
- **URL**: `POST /api/users`
- **Request Body**: Same as Register User (`CreateUserRequest`).
- **Response** (200 OK): `{"message": "User created successfully"}`

### 4.2 Get All Users (Admin Only, Paginated)
- **URL**: `GET /api/users`
- **Query Parameters**:
  - `email` (Optional filter)
  - `page`, `size`, `sortBy` (default: firstName), `sortDir`
- **Response** (200 OK): Pageable object of users.

### 4.3 Get User By ID
- **URL**: `GET /api/users/{id}`
- **Access Requirement**: `ADMIN` or the user themselves (self-access).
- **Response** (200 OK): User object details.

### 4.4 Update User (Admin Only)
- **URL**: `PUT /api/users/{id}`
- **Request Body**:
```json
{
  "firstName": "String",
  "lastName": "String",
  "role": "ADMIN or ENGINEER"
}
```
- **Response** (200 OK): `{"message": "User updated successfully"}`

### 4.5 Delete User (Admin Only)
- **URL**: `DELETE /api/users/{id}`
- **Response** (200 OK): `{"message": "User deleted successfully"}`

---

## 5. Dashboard APIs (`/api/dashboard`)
**Access Requirement**: `ADMIN` or `ENGINEER`

### 5.1 Get Overall Summary
- **URL**: `GET /api/dashboard/summary`
- **Response** (200 OK): Contains high-level counts (e.g., total assets, open tickets, etc.).

### 5.2 Get Asset Dashboard Data
- **URL**: `GET /api/dashboard/assets`
- **Response** (200 OK): Contains asset-specific charts/data summaries (e.g., assets by status/type).

### 5.3 Get Ticket Dashboard Data
- **URL**: `GET /api/dashboard/tickets`
- **Response** (200 OK): Contains ticket-specific summaries (e.g., tickets by priority/status).

### 5.4 Get Health Dashboard Data
- **URL**: `GET /api/dashboard/health`
- **Response** (200 OK): Contains system health metrics or infrastructure status.
