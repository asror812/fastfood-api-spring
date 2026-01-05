# 🍔 FastFood API (Spring Boot)

A modern REST API backend for a food delivery application.
Provides authentication, product catalog, categories, discounts, cart, favorites, file storage, and ordering.

## 📌 Overview

FastFood API is a Spring Boot 3 application built with a layered architecture (Controller → Service → Repository).

It uses:

- PostgreSQL for database
- Redis for caching + token blacklist
- JWT Authentication for secure access
- Docker Compose to run required services
- Spring Security for route protection
- REST API consumed by an Android mobile application

## 🔐 Authentication

- Login by phone number
- JWT token generation
- Role-based permissions (Admin, User, Staff)
- Token blacklist using Redis

## 🍽 Product Catalog

- Categories & Subcategories
- Products with images (attachments)
- Product discounts
- Bonus conditions (holiday, birthday, first purchase)

## ❤️ Favorites

- Add product to favorites
- Remove product from favorites
- View user favorites

## 🧺 Shopping Cart

- Add items
- Remove items
- Price calculation

## 🏬 Filials (Branches)

- Filial list
- Working hours
- Region support

## 🧾 Orders

- Create order
- Track order status
- Order items inside order

## 🖼 Attachments

- Upload images
- Download images using:
- GET /attachments/download/{id}

## 🛠 Tech Stack
| Component           | Technology            |
| ------------------- | --------------------- |
| Backend Framework   | Spring Boot 3         |
| Build Tool          | Gradle                |
| Auth                | JWT + Spring Security |
| Database            | PostgreSQL            |
| OTP                 | Redis                 |
| ORM                 | Spring Data JPA       |
| API Docs            | SpringDoc / Swagger   |
| Deployment          | Docker Compose        |

## 🧪 How to Run the Backend

- This project requires Docker and Java 17+.

1️⃣ Clone the repository
```bash
git clone https://github.com/asror812/fastfood-api-spring.git
cd fastfood-api-spring
```

2️⃣ Start PostgreSQL + Redis using Docker Compose
```bash
docker compose up -d
```

3. Check running containers:
```bash
docker ps
```
You should see:

postgres

redis
