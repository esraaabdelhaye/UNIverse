# UNIverse - Quick Start Guide

## 🚀 Start Backend (Java Spring Boot)

```bash
# Navigate to backend
cd backend

# Build
mvn clean install

# Run
mvn spring-boot:run

# Backend will be available at: http://localhost:8080/api
```

## 🎨 Start Frontend (Angular)

```bash
# Navigate to frontend
cd frontend

# Install dependencies (first time only)
npm install

# Run
ng serve

# Frontend will be available at: http://localhost:4200
```

## 📊 Access Database Console (H2)

When backend is running:
- URL: http://localhost:8080/api/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (leave empty)

## 🧪 Test API Endpoints

### Example API Calls:
```bash
# Get all students
curl http://localhost:8080/api/students

# Get all courses
curl http://localhost:8080/api/courses

# Get all doctors
curl http://localhost:8080/api/doctors

# Get all teaching assistants
curl http://localhost:8080/api/teaching-assistants

# Get system performance metrics
curl http://localhost:8080/api/supervisors/performance/metrics
```

## ✅ Initial Test Data Loaded

The system automatically loads test data including:
- ✅ 3 Departments
- ✅ 4 Faculty members (Doctors & Supervisors)
- ✅ 2 Teaching Assistants
- ✅ 3 Students
- ✅ 2 Student Representatives
- ✅ 3 Courses with enrollments
- ✅ 2 Assignments
- ✅ Materials and announcements

## 📌 Key Ports

- **Backend API**: http://localhost:8080/api
- **Frontend**: http://localhost:4200
- **Database Console**: http://localhost:8080/api/h2-console

## 🔗 API Endpoints Summary

### Students: `/api/students`
- GET / (all)
- GET /{id}
- GET /email/{email}
- POST (create)
- PUT /{id} (update)
- DELETE /{id}

### Courses: `/api/courses`
- GET / (all)
- GET /{id}
- GET /code/{code}
- GET /semester/{semester}
- GET /department/{departmentId}
- POST (create)
- PUT /{id}
- DELETE /{id}

### Doctors: `/api/doctors`
- GET / (all)
- GET /{id}
- GET /{id}/courses
- POST (create)
- PUT /{id}
- DELETE /{id}

### Teaching Assistants: `/api/teaching-assistants`
- GET / (all)
- GET /{id}
- GET /{id}/courses
- POST (create)
- PUT /{id}
- DELETE /{id}

### Supervisors: `/api/supervisors`
- GET / (all)
- GET /{id}
- GET /{id}/coordinated-courses
- GET /performance/metrics
- POST (create)
- PUT /{id}
- DELETE /{id}

## 📝 Response Format

All API responses follow this format:
```json
{
  "statusCode": 200,
  "message": "Success",
  "data": { /* data here */ },
  "timestamp": "2024-12-02T10:30:00"
}
```

## ⚠️ Troubleshooting

### Port 8080 already in use
Edit `backend/src/main/resources/application.properties`:
```properties
server.port=8081
```

### Port 4200 already in use
Run with different port:
```bash
ng serve --port 4201
```

### Clear H2 Database
The database resets automatically when the backend restarts.

---

**Ready to go! Backend ✅ | Frontend ✅ | Data ✅**
