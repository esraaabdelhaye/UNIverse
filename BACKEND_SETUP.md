# UNIverse Backend Setup Guide

## Project Status
✅ Backend implementation complete with the following features:

### Completed Components

#### 1. Services (All Services Fully Implemented)
- **StudentService**: CRUD operations for students, enrollment management
- **CourseService**: Course management, search, filtering by semester/department
- **DoctorService**: Doctor management, course assignment
- **TeachingAssistantService**: TA management, course assignment
- **SupervisorService**: Supervisor management, course coordination, performance metrics
- **StudentRepService**: Student representative management

#### 2. Controllers (RESTful APIs)
- **StudentController**: `/api/students` - Student CRUD and enrollment
- **CourseController**: `/api/courses` - Course CRUD and search
- **DoctorController**: `/api/doctors` - Doctor management
- **TeachingAssistantController**: `/api/teaching-assistants` - TA management
- **SupervisorController**: `/api/supervisors` - Supervisor management and metrics

#### 3. Data Layer
- **Repositories**: All repositories configured with query methods
- **DataLoader**: Automatic data seeding on application startup
- **Database**: H2 in-memory database (configurable for production)

#### 4. Configuration
- **CORS Configuration**: Enabled for Angular/Frontend communication
- **Database Configuration**: H2 database with Hibernate auto-create schema
- **Application Properties**: Configured for development environment

### Database Schema
The application creates the following tables automatically:

```
- department
- students (with single-table inheritance for STUDENT, SUPERVISOR, REPRESENTATIVE)
- teaching_assistant
- course
- course_enrollment
- assignment
- assignment_submission
- material
- announcement
- event
- post
- student_group
- poll
- poll_option
- question
- schedule
- semester
- grade
```

### Initial Data Loaded
The DataLoader automatically populates:
- **3 Departments**: Computer Science, Engineering, Business
- **2 Supervisors**: Dr. Ahmed Hassan, Dr. Fatima Mohamed
- **2 Doctors**: Dr. Omar Ali, Dr. Noor Ibrahim
- **3 Students**: Ahmed Mohammed, Fatima Ali, Sara Ibrahim
- **2 Student Representatives**: Mariam Khalil, Hassan Nour
- **2 Teaching Assistants**: Amira Saleh, Karim Mansour
- **3 Courses**: CS101, CS201, ENG101
- **3 Course Enrollments**: With grades
- **2 Assignments**: With due dates
- **2 Materials**: Lecture slides
- **2 Announcements**: Course announcements
- **2 Events**: Workshops and seminars

## Running the Backend

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- Git

### Steps to Run

1. **Navigate to backend directory:**
```bash
cd /home/hebo/Desktop/courses/UNIverse/backend
```

2. **Build the project:**
```bash
mvn clean install
```

3. **Run the application:**
```bash
mvn spring-boot:run
```

Or use:
```bash
java -jar target/backend-*.jar
```

4. **Access the application:**
- Backend API: `http://localhost:8080/api`
- H2 Console: `http://localhost:8080/api/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (leave empty)

## API Endpoints Summary

### Students
- `GET /api/students` - Get all students (paginated)
- `GET /api/students/{id}` - Get student by ID
- `GET /api/students/email/{email}` - Get student by email
- `POST /api/students` - Create new student
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student

### Courses
- `GET /api/courses` - Get all courses (paginated)
- `GET /api/courses/{id}` - Get course by ID
- `GET /api/courses/code/{code}` - Get course by code
- `GET /api/courses/semester/{semester}` - Get courses by semester
- `GET /api/courses/department/{departmentId}` - Get courses by department
- `POST /api/courses` - Create new course
- `PUT /api/courses/{id}` - Update course
- `DELETE /api/courses/{id}` - Delete course
- `GET /api/courses/search?name={name}` - Search courses

### Doctors
- `GET /api/doctors` - Get all doctors (paginated)
- `GET /api/doctors/{id}` - Get doctor by ID
- `GET /api/doctors/email/{email}` - Get doctor by email
- `POST /api/doctors` - Create new doctor
- `PUT /api/doctors/{id}` - Update doctor
- `DELETE /api/doctors/{id}` - Delete doctor
- `GET /api/doctors/{id}/courses` - Get doctor's courses
- `POST /api/doctors/{id}/courses/{courseId}` - Assign course to doctor

### Teaching Assistants
- `GET /api/teaching-assistants` - Get all TAs (paginated)
- `GET /api/teaching-assistants/{id}` - Get TA by ID
- `GET /api/teaching-assistants/email/{email}` - Get TA by email
- `POST /api/teaching-assistants` - Create new TA
- `PUT /api/teaching-assistants/{id}` - Update TA
- `DELETE /api/teaching-assistants/{id}` - Delete TA
- `GET /api/teaching-assistants/{id}/courses` - Get TA's courses
- `POST /api/teaching-assistants/{id}/courses/{courseId}` - Assign course to TA

### Supervisors
- `GET /api/supervisors` - Get all supervisors (paginated)
- `GET /api/supervisors/{id}` - Get supervisor by ID
- `GET /api/supervisors/email/{email}` - Get supervisor by email
- `POST /api/supervisors` - Create new supervisor
- `PUT /api/supervisors/{id}` - Update supervisor
- `DELETE /api/supervisors/{id}` - Delete supervisor
- `GET /api/supervisors/{id}/coordinated-courses` - Get coordinated courses
- `POST /api/supervisors/{id}/courses/{courseId}` - Assign course to coordinator
- `GET /api/supervisors/performance/metrics` - Get system performance metrics

## Response Format

All API responses follow a standard format:

### Success Response (200, 201, etc.)
```json
{
  "statusCode": 200,
  "message": "Operation successful",
  "data": { /* actual data */ },
  "timestamp": "2024-12-02T10:30:00"
}
```

### Error Response (4xx, 5xx, etc.)
```json
{
  "statusCode": 400,
  "message": "Error description",
  "data": null,
  "timestamp": "2024-12-02T10:30:00"
}
```

## Error Handling
All API endpoints include comprehensive error handling:
- **400 Bad Request**: Invalid input
- **404 Not Found**: Resource not found
- **409 Conflict**: Duplicate resource
- **500 Internal Server Error**: Server errors

## Next Steps for Frontend Integration

1. **Frontend Configuration**:
   - Update API base URL to `http://localhost:8080/api`
   - Ensure CORS is properly configured (already done)

2. **Testing Endpoints**:
   - Use Postman or similar tool to test endpoints
   - Or use curl commands

3. **Example curl commands**:
```bash
# Get all students
curl http://localhost:8080/api/students

# Create a new student
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": "5",
    "fullName": "New Student",
    "email": "new@student.edu"
  }'

# Get all courses
curl http://localhost:8080/api/courses

# Get course by code
curl http://localhost:8080/api/courses/code/CS101
```

## Database Configuration for Production

To switch from H2 to MySQL/PostgreSQL:

1. Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/universe
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=validate
```

2. Add MySQL driver dependency to `pom.xml`:
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

## Troubleshooting

### Port 8080 already in use
```bash
# Change port in application.properties
server.port=8081
```

### Database issues
```bash
# Clear H2 database and restart
# Delete the .h2.db file if present and restart the application
```

### CORS errors
- Ensure CorsConfig is properly configured
- Check frontend is accessing correct API URL
- Verify frontend origin is in CORS allowed origins

## Project Structure
```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/example/backend/
│   │   │   ├── config/          (CorsConfig, DataLoader)
│   │   │   ├── controller/      (REST controllers)
│   │   │   ├── service/         (Business logic)
│   │   │   ├── repository/      (Data access layer)
│   │   │   ├── entity/          (JPA entities)
│   │   │   └── dto/             (Data transfer objects)
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
└── pom.xml
```

## Support
For issues or questions, please check:
1. API response status codes and messages
2. Database logs in console
3. Application properties configuration
4. Data model relationships in entity files

✅ Backend is ready for frontend integration!
