<div align="center">

# DiscPro

**A Multi-Vendor Near-Expiry Discount System for Local Stores and Communities**

*Less food waste. Lower grocery bills. Stronger neighborhoods.*

![SDG 12](https://img.shields.io/badge/SDG%2012-Responsible%20Consumption%20%26%20Production-bf8b2e?style=flat-square)
![Java](https://img.shields.io/badge/Java-17%2B-007396?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?style=flat-square)

</div>

---

## Overview

**DiscPro** connects neighborhood stores with local shoppers to reduce retail food waste. Stores scan barcoded, factory-sealed products and input their stamped expiration dates, and the system automatically applies dynamic markdowns as expiry approaches.

| Time Left Before Expiry | Example Markdown |
| :---: | :---: |
| ≤ 7 days | 30% off |
| ≤ 1 day | 60% off |

---

## Features

- **Buyer Auth & Dashboard** — Session-based login (via User ID or Email), registration, and saved deals tracking
- **Live Feed** — Real-time stream of active discounted items
- **Store Finder** — Location lookup for participating neighborhood stores
- **Seller Dashboard** — Barcode scanning, expiry entry, and inventory management

---

## Tech Stack

- **Backend:** Java, Spring Boot (Web MVC, Thymeleaf, Data JPA, Validation)
- **Database:** H2 In-Memory Database
- **Frontend:** HTML5, CSS3, Bootstrap 5, QuaggaJS, Leaflet.js

---

## OOP Architecture

| Pillar | Implementation |
|---|---|
| **Abstraction** | Abstract `User` class and `DiscountStrategy` interface |
| **Encapsulation** | Private fields with validated setters in `Product` |
| **Inheritance** | `Buyer` and `Seller` extend `User` (`JOINED` strategy) |
| **Polymorphism** | `StandardDiscount` and `BakeryDiscount` override `calculateDiscount()` |

---
