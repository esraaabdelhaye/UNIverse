# 🎓 UNIverse

A comprehensive University Management System built with Spring Boot and Angular. UNIverse streamlines academic operations by providing a unified platform for students, faculty, and administrators.

## Features

### For Students
- View enrolled courses and materials
- Submit assignments and track deadlines
- Access grades and academic progress
- Receive announcements and event notifications

### For Faculty (Doctors & TAs)
- Manage assigned courses
- Create and grade assignments
- Upload lecture materials
- Post announcements

### For Supervisors
- Coordinate courses across departments
- Manage faculty assignments
- View system performance metrics
- Oversee department operations

## Tech Stack

### Backend
- **Java 17+** with **Spring Boot 3.x**
- **Spring Data JPA** for persistence
- **H2 Database** (development) / MySQL/PostgreSQL (production)
- **RESTful API** architecture

### Frontend
- **Angular 17+**
- **TypeScript**
- **Responsive design**

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- Node.js 18+ and npm

### Backend Setup
```bash
# Navigate to backend directory
cd backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api`

### Frontend Setup
```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start development server
ng serve
```

The application will be available at `http://localhost:4200`

## API Endpoints

| Resource | Endpoint | Description |
|----------|----------|-------------|
| Students | `/api/students` | Student management |
| Courses | `/api/courses` | Course operations |
| Doctors | `/api/doctors` | Faculty management |
| TAs | `/api/teaching-assistants` | TA management |
| Supervisors | `/api/supervisors` | Admin operations |

## Database Console

Access the H2 database console at: `http://localhost:8080/api/h2-console`

## Project Structure

```
UNIverse/
├── backend/                 # Spring Boot application
│   ├── src/main/java/
│   │   ├── controller/      # REST controllers
│   │   ├── service/         # Business logic
│   │   ├── repository/      # Data access layer
│   │   ├── model/           # Entity classes
│   │   └── config/          # Configuration files
│   └── pom.xml
├── frontend/                # Angular application
│   ├── src/
│   │   ├── app/             # Components & services
│   │   └── assets/          # Static assets
│   └── package.json
└── README.md
```

## Documentation

- **[Backend Setup Guide](BACKEND_SETUP.md)** - Detailed backend configuration
- **[Implementation Summary](IMPLEMENTATION_SUMMARY.md)** - Technical documentation

---

⭐ **UNIverse** - Empowering Education Through Technology
