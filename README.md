<img width="1024" height="1024" alt="Convertly" src="https://github.com/user-attachments/assets/1d104574-eb7a-4ab5-9284-2c259e3581c8" />
# 🔧 Convertly API

Welcome to **Convertly** — a lightweight and powerful RESTful API for converting units across multiple categories like temperature, length, weight, and time.

---

## 🌟 What Is Convertly?

Convertly is a simple yet efficient unit conversion API built with **Spring Boot**. It offers structured endpoints, clean code architecture, and helpful responses, making it easy to integrate into any project that requires unit conversion.

---

## 🚀 Features

### 🔁 `POST /convert`
Converts a given value from one unit to another.

#### 📥 Request Example

{

  "category": "temperature",
  
  "fromUnit": "celsius",
  
  "toUnit": "fahrenheit",
  
  "value": 25

}



📤 Response Example

{

  "result": 77.0,
  
  "formula": "(25°C × 9/5) + 32 = 77°F",
  
  "status": "success"

}

📂 GET /categories


Fetches a list of all available measurement categories.

✅ Response:

["temperature", "length", "weight", "time"]



📦 GET /units?category=temperature

Returns supported units for the specified category.

✅ Example:

["celsius", "fahrenheit", "kelvin"]


🧪 GET /sample-payload

Get a ready-to-use sample request body for testing the /convert endpoint.


🩺 GET /health

Simple health check to confirm the API is running properly.

✅ Response:

{ "status": "Unit Converter API is up and running" }


⚙️ Behind the Scenes

Enum-driven design for categories and units ensures type safety and code clarity.

Case-insensitive input handling for units and categories.

Validation layer for inputs:

Ensures required fields are present

Prevents mismatched unit-category combinations

Rejects invalid or negative values where not applicable

📘 Supported Categories & Units

🌡️ Temperature
Celsius

Fahrenheit

Kelvin

📏 Length

Meter

Kilometer

Mile

Inch

Foot

⚖️ Weight

Gram

Kilogram

Pound

Ounce

⏱️ Time

Seconds

Minutes

Hours

Days

🛠️ Tech Stack

Java 17+

Spring Boot 3

Maven

Jakarta Validation

Swagger


🧰 Getting Started

Clone the repository

Build the project:

./mvnw clean install

Run the application:

./mvnw spring-boot:run

🧪 Test It Out

You can interact with the API using:

📫 Postman

🌐 Swagger UI

🧵 cURL or any HTTP client

🚀 Built with focus and passion by Seif Aldien Ahmed
