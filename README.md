<div align="center">

#  DiscPro

### A Multi-Vendor Near-Expiry Discount System for Local Small Businesses and Communities

*Less food waste. Lower grocery bills. Stronger neighborhoods.*

![SDG 12](https://img.shields.io/badge/SDG%2012-Responsible%20Consumption%20%26%20Production-bf8b2e?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)

</div>

---

##  The Problem

Local neighborhood stores and small businesses throw away a massive amount of **factory-sealed food** (like dairy, bread, and snacks) because it reaches its official expiration date before being sold, causing heavy financial losses.

At the same time, budget-conscious residents and low-income families struggle to afford groceries, but have **no way of knowing when or where** local merchants are marking down these items. Existing social media pages are too chaotic, so available neighborhood discounts get completely lost or ignored.

##  The Solution

**DiscPro** is a website connecting independent community sellers with budget-conscious local buyers.

It only allows **barcoded, pre-packaged goods with clear factory-stamped expiration dates**, completely excluding loose items like fresh vegetables or open restaurant food.

**How sellers use it:** neighborhood mom-and-pop shops, convenience stores, and local bakeries scan a product's barcode and enter the stamped expiry date. The website then **automatically slashes the price** based on the time left.

| Time left before expiry | Example discount |
|:-----------------------:|:----------------:|
| 1 week or less |  30% off |
| 1 day or less |  60% off |

> The exact rules are configurable, and some sellers (like bakeries) use faster-moving schedules.

##  Who Benefits

|  Small Community Businesses |  Local Residents & Families |
|---|---|
| Stop throwing away cash | Get safe, factory-sealed food at massive discounts |
| Recover lost revenue | Directly lower their monthly grocery bills |
| Automate stock clearance | Know exactly where and when deals appear |

##  Features

-  **Buyer Dashboard**: save items and browse nearby deals
-  **Seller Dashboard**: scan barcodes, set expiry dates, and manage listings
-  **Live Feed**: a real-time list of active discounts
-  **Store Finder**: find participating stores by address

##  Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot (Web, Thymeleaf, Data JPA, Validation) |
| Database | H2 (development) |
| Frontend | HTML, CSS, JavaScript |
| Barcode scanning | QuaggaJS |
| Maps | Leaflet.js + OpenStreetMap |

##  OOP Pillars in DiscPro

| Pillar | Where it appears |
|---|---|
| **Abstraction** | Abstract `User` class and the `DiscountStrategy` interface |
| **Encapsulation** | Private fields and validated setters in `Product` |
| **Inheritance** | `Buyer` and `Seller` extend `User` |
| **Polymorphism** | `StandardDiscount` and `BakeryDiscount` implement the same `calculateDiscount()` method differently |
<div align="center">

**SDG 12: Responsible Consumption and Production**

DiscPro supports **target 12.3** by cutting food waste at the retail level, helping sealed food get eaten instead of thrown away.

</div>
