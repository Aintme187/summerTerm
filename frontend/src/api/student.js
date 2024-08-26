import request from '@/request/index.js'


// 学生信息页面

// 获取课程表
export const getStudentCourseTableService = (studentId) => {
  return request.get('/api/students/course-table', {
    params: {
      studentId
    }
  })
}


// 学生选课页面

// 筛选课程
export const getCoursesApi = (page, pageSize, filterData, excludeFull, excludeConflict) => {
    // ES6 属性简写
    // const data = {page, pageSize, "filterDataList": filterData}
    return request.post("/api/students/listCourse", {
        "adminPageParam": {
            page,
            pageSize,
            "filterDataList": filterData,
        },
        excludeFull,
        excludeConflict,
    })
    // return Request({
    //     url: "/admin/user/list",
    //     method: "POST",
    //     data,
    // })
}

// 申请加入课程
export const applyForCourseEnrollmentService = (courseId, studentId) => {
  return request.post('/api/students/applyForCourseEnrollment', null, {
    params: {
      courseId,
      studentId
    }
  })
}

// 申请成为助教
export const applyToBeAssistantService = (courseId, studentId) => {
  return request.post('/api/students/applyToBeAssistant', null, {
    params: {
      courseId,
      studentId,
    }
  })
}


// 已选课程页面

// 获取已选课程
export const getSelectedCoursesService = (studentId) => {
  return request.get('/api/students/selectedCourses', {
    params: {
      studentId
    }
  })
}

// 退课
export const dropCourseService = (studentId, courseId) => {
  return request.post('/api/students/dropCourse', null, {
    params: {
      studentId,
      courseId
    }
  })
}


// 课程资料页面

// 获取已加入课程
export const getJoinedCoursesService = (studentId) => {
  return request.get('/api/students/myCourse', {
    params: {
      studentId
    }
  })
}

// 展示课程资料
export const getMaterialService = (courseId) => {
  return request.get('/api/students/materials', {
    params: {
      courseId
    }
  })
}

export const getMaterialForArticle= (articleId) => {
  return request.get('/articles/materials', {
    params: {
      articleId
    }
  })
}

// 获取课程资料打开链接
export const downloadMaterialService = (materialId) => {
  return request.get('/api/students/download', {
    params: {
      materialId
    }
  })
}

export const downloadMaterialForArticle = (materialId) => {
  return request.get('/articles/materials/download', {
    params: {
      materialId
    }
  })
}

// 获取作业列表和作业详情
export const getAssignmentsService = (courseId, studentId) => {
  return request.get('/api/students/assignments', {
    params: {
      courseId,
      studentId
    }
  })
}

// 获取课程作业下载链接
export const downloadAssignmentService = (assignmentId) => {
  return request.get('/api/students/assignments/download', {
    params: {
      assignmentId
    }
  })
}

// 提交课程作业
export const submitAssignmentService = (studentId, assignmentId, multipartFile) => {
  const formData = new FormData()
  formData.append('studentId', studentId)
  formData.append('assignmentId', assignmentId)
  formData.append('multipartFile', multipartFile)  // Ensure the key matches your @RequestParam

  return request.post('/api/students/submitAssignment', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 获取助教课程
export const getAssisitCoursesService = (assistantId) => {
  return request.get('/api/assistants/assistCourses', {
    params: {
      assistantId
    }
  })
}

// 上传课程资料
export const submitMaterialService = (courseId, multipartFile) => {
  const formData = new FormData()
  formData.append('courseId', courseId)
  formData.append('multipartFile', multipartFile)  // Ensure the key matches your @RequestParam

  return request.post('/api/assistants/submitMaterial', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const submitMaterialForArticle = (courseId, multipartFile) => {
  const formData = new FormData()
  formData.append('courseId', courseId)
  formData.append('multipartFile', multipartFile)  // Ensure the key matches your @RequestParam

  return request.post('/api/assistants/submitMaterial', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}