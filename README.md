# GV Welding Backend - Production Ready

This is the Spring Boot backend for the GV Welding Works website. It handles enquiries, email notifications (Admin + Client), and prepares data for future database storage.

## Tech Stack
- **Framework:** Spring Boot 3.2.4
- **Language:** Java 17
- **Email:** Spring Boot Starter Mail (SMTP)
- **Utilities:** Lombok, Spring Validation
- **Database:** H2 (In-memory) / JPA ready

## Prerequisites
- **JDK 17** or higher
- **Maven** (optional if using wrapper, but recommended)

## Configuration
Open `src/main/resources/application.properties` and configure your SMTP settings:
```properties
spring.mail.username=your-business-email@gmail.com
spring.mail.password=your-app-specific-password
business.email.receiver=your-business-email@gmail.com
```
*Note: For Gmail, use an **App Password**, not your regular password.*

## Running the Application
1. Navigate to the `backend` folder:
   ```bash
   cd backend
   ```
2. Run using Maven:
   ```bash
   mvn spring-boot:run
   ```
The server will start on `http://localhost:8080`.

## API Endpoints
### Submit Enquiry
- **URL:** `/api/enquiry`
- **Method:** `POST`
- **Payload:**
  ```json
  {
    "name": "John Doe",
    "phone": "9876543210",
    "email": "customer@example.com",
    "service": "Structural Welding",
    "message": "Interested in gate fabrication."
  }
  ```

## Features Implemented
- **Bilingual Auto-Response:** Clients receive a thank you email in both Tamil and English.
- **Admin Notifications:** Business owner gets immediate notification of new leads.
- **Data Persistence:** Enquiries are stored in an H2 database (viewable at `/h2-console`).
- **Global Error Handling:** Consistent error responses for validation and server errors.
- **CORS Support:** Pred-configured to allow requests from your React frontend.
