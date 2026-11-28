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

## Project Description

DiscPro is a Java-based web application designed to connect local stores with consumers looking for discounted near-expiry products. The system allows participating sellers to list barcoded products along with their expiration dates, while discounts are automatically calculated based on the products' remaining shelf life.

The platform provides a centralized place where consumers can discover nearby discounted products, check participating stores, and reserve available items for store pickup. DiscPro aims to help local businesses reduce inventory losses while giving consumers access to more affordable products.

---

## Problem

Local stores, convenience shops, and bakeries often experience losses when packaged food products remain unsold and reach their expiration dates. These products may have to be discarded even when they could still have been sold before expiring.

Meanwhile, consumers looking for affordable groceries may not know which nearby stores offer discounted near-expiry products. Discount information is often scattered across different social media platforms, making it difficult to find and track available deals.

DiscPro addresses these problems by providing a centralized platform for near-expiry discounts, helping businesses manage unsold inventory and making discounted products easier for consumers to discover.

---

## Project Objectives

General Objectives

To develop a Java-based application that connects local sellers and consumers by providing an automated discounting system for near-expiry pre-packaged products.

Specific Objectives

- Allow sellers to register and list near-expiry products using barcode and expiration date information.
- Automatically calculate and apply discounts based on a product's remaining shelf life.
- Provide consumers with a centralized platform for finding discounted products from nearby stores.
- Help local businesses reduce losses from unsold inventory.
- Promote food waste reduction by encouraging the sale of near-expiry products.

---

## Target Users

|Target Users| How They Will Used the System|
|---|---|
|Small Community Businesses|- Minimize financial losses from unsold inventory. <br>
- Recover lost revenue from products nearing expiration. <br>
- Automate the clearance of near-expiry stock.|
|Local Residents and Families|- Purchase factory-sealed products at discounted prices. - Find more affordable grocery options. - Track nearby stores and available discounted products.|

---

## Features

- **Buyer Authentication & Dashboard** — Session-based login (via User ID or Email), registration, and saved deals tracking
- **Live Feed** — Real-time stream of active discounted items
- **Store Finder** — Location lookup for participating neighborhood stores
- **Seller Dashboard** — Barcode scanning, expiry entry, and inventory management

---

## Technologies Used

- **Java** — Primary programming language.
- **Spring Boot** — Framework for developing the web application.
- **HTML and CSS** — Frontend structure and styling.
- **Java Collections (List)** — Handles collections of product and application data.
- **Java Streams** — Used for filtering and sorting active products.
- **JPA Repositories** — Used for database-related data handling.

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
