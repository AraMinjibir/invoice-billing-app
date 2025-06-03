Invoice Management System

A full-featured web application built with Angular Standalone Components, PrimeNG, PrimeFlex, and a Spring Boot (Java) backend. It supports CRUD operations for managing items and invoices, and includes a responsive UI with toast messages, confirmation dialogs, and form validations.

🔧 Tech Stack
Frontend:
Angular 17+ (Standalone Components)

PrimeNG – UI Components

PrimeFlex – Utility-first layout

RxJS – Reactive programming

TypeScript

Backend:
Spring Boot

Spring Data JPA

Hibernate

H2 / MySQL (pluggable DB)

REST API

✅ Features
📁 Items Module
Add, edit, view and delete items

Fields: Item Name, Description, Price, Quantity

Validations on form inputs

PrimeNG DataTable with Actions

Confirmation dialog for deletions

📄 Invoice Module
Create and manage invoices

Fields: Invoice Number, Date, and other invoice details

Supports linking to items (future enhancement)

Reusable InvoiceFormComponent using Angular Reactive Forms

🧭 Navigation
Responsive top navbar with routing

Navigation to Items and Invoices

🔄 CRUD Flow
Each module (Items, Invoices) supports:

Create – Uses modal dialog with a form

Read – Displays data in PrimeNG table

Update – Edit form with pre-filled data

Delete – Confirmation dialog before deletion


How to Run

cd frontend
npm install
ng serve

cd backend
./mvnw spring-boot:run

