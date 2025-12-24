# 🚀 Quick Start Guide - Postman Collection

## ⚡ Import in 3 Steps

### Step 1: Open Postman
```
Launch Postman Desktop Application
```

### Step 2: Click Import
```
Top-left corner → "Import" button
```

### Step 3: Select File
```
Choose: postman_collection.json
Location: C:\Users\MahmoudBELAYEB\Documents\SpringBoot Projects\platform\
```

---

## 📋 30 Endpoints Ready to Test

### 🧪 Test (1)
```
POST /api/test/ping
```

### 👤 Users (11)
```
POST   /api/users/register          ← Start here
POST   /api/users/login             ← Get JWT token
GET    /api/users
GET    /api/users/{id}
GET    /api/users/email/{email}
PUT    /api/users/{id}
POST   /api/users/change-password
PUT    /api/users/{id}/activate
PUT    /api/users/{id}/deactivate
DELETE /api/users/{id}
```

### 📚 Courses (7)
```
POST   /api/courses                 ← Create course
GET    /api/courses
GET    /api/courses/{id}
GET    /api/courses/tutor/{tutorId}
PUT    /api/courses/{id}
DELETE /api/courses/{id}
POST   /api/courses/{id}/enrollments ← Enroll
```

### 📝 Assignments (4)
```
POST   /api/courses/{id}/assignments               ← Create
GET    /api/courses/{id}/assignments                ← List
POST   /api/courses/{id}/assignments/{id}/submissions ← Submit
POST   /api/courses/{id}/submissions/{id}/grade     ← Grade
```

### 📎 Materials (5)
```
POST   /api/courses/{id}/materials  ← Create (PDF/Video/Doc)
GET    /api/courses/{id}/materials  ← List
DELETE /api/courses/{id}/materials/{id} ← Delete
```

---

## 🔄 Quick Test Workflow

```
1. Register Student → Auto-saves studentId
2. Register Tutor → Auto-saves tutorId  
3. Login Tutor → Auto-saves jwtToken
4. Create Course → Auto-saves courseId
5. Login Student → Updates jwtToken
6. Enroll Student → Join course
7. Create Assignment → Auto-saves assignmentId
8. Submit Assignment → Auto-saves submissionId
9. Login Tutor → Get tutor token
10. Grade Submission → Complete!
```

---

## 🔑 Auto-Saved Variables

The collection automatically saves these IDs:

```
✅ studentId   ← From registration
✅ tutorId     ← From registration
✅ jwtToken    ← From login (updates each login)
✅ courseId    ← From course creation
✅ assignmentId ← From assignment creation
✅ submissionId ← From submission
✅ materialId  ← From material creation
```

**No manual copying needed!** 🎉

---

## 📝 Sample Data

### Register Student
```json
{
  "username": "student_john",
  "email": "john.student@example.com",
  "password": "Student@123",
  "role": "STUDENT"
}
```

### Login
```json
{
  "email": "john.student@example.com",
  "password": "Student@123"
}
```

### Create Course
```json
{
  "title": "Spring Boot Course",
  "description": "Learn Spring Boot",
  "tutorId": 1,
  "capacity": 30
}
```

---

## ✅ Status Codes

```
200 OK          ✅ Success (GET, PUT)
201 Created     ✅ Resource created (POST)
204 No Content  ✅ Success, no body (DELETE, Enroll)
400 Bad Request ❌ Validation error
401 Unauthorized ❌ Login required
404 Not Found   ❌ Resource doesn't exist
```

---

## 🎯 Testing Tips

1. **Start API first:** `mvn spring-boot:run`
2. **Run in order:** Follow the workflow above
3. **Check Console:** See auto-saved variables
4. **Re-login if needed:** Token expires in 24h
5. **Use different roles:** Test permissions

---

## 📁 File Info

**Name:** `postman_collection.json`  
**Size:** ~15KB  
**Format:** Postman Collection v2.1.0  
**Endpoints:** 30  
**Controllers:** 5  
**Status:** ✅ Ready to Import

---

## 🎉 You're All Set!

✅ Collection file created  
✅ All 30 endpoints included  
✅ Auto-variables configured  
✅ Sample data provided  
✅ Ready to import  

**Import now and start testing!** 🚀

