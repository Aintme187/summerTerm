import request from '@/request/index.js'

//教师课程表页面
export const getTeacherCourseTable = (teacherId) => {
  return request.get('/api/teachers/courseTable', {
    params: {
      teacherId
    }
  })
}

//管理课程成员页面
export const showScApplication = (teacherId) => {
  return request.get('/api/teachers/showScApplication', {
    params: {
      teacherId
    }
  })
}

export const showAssistantApplication = (teacherId) => {
  return request.get('/api/teachers/showAssistantApplication', {
    params:{
      teacherId
    }
  })
}

export const modifyCourseParticipants = (courseId, participantId, operation) => {
  return request.post('/api/teachers/participantsModify', null, {
    params:{
      courseId,
      participantId,
      operation
    }
  })
}

export const modifyCourseAssistants = (courseId, assistantId, operation) => {
  return request.post('/api/teachers/assistantsModify', null,{
    params:{
      courseId,
      assistantId,
      operation
    }
  })
}

//管理课程作业页面
export const getAssignments = (teacherId) => {
  return request.get('/api/teachers/assignments', {
    params: {
      teacherId
    }
  })
}

export const getCourseAssignments = (courseId) => {
  return request.get('/api/teachers/courseAssignments', {
    params: {
      courseId
    }
  })
}

export const releaseAssignment = (name, description, beginTime, endTime, courseId, multipartFile) => {
  const formData = new FormData()
  formData.append('name', name)
  formData.append('description', description)
  formData.append('beginTime', beginTime)
  formData.append('endTime', endTime)
  formData.append('courseId', courseId)
  formData.append('multipartFile', multipartFile)  // Ensure the key matches your @RequestParam

  return request.post('/api/teachers/releaseAssignment', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const showSubmission = (assignmentId) => {
  return request.get('/api/teachers/showSubmissions', {
    params: {
      assignmentId
    }
  })
}

export const downloadSubmission = (submissionId) => {
  return request.get('/api/teachers/downloadSubmission', {
    params: {
      submissionId
    }
  })
}

export const correctSubmission = (submissionId, score, feedbackFile, comment) => {
  const formData = new FormData()
  formData.append('submissionId', submissionId)
  formData.append('score', score)
  formData.append('feedbackFile', feedbackFile)  // Ensure the key matches your @RequestParam
  formData.append('comment', comment)

  return request.post('/api/teachers/correctSubmission', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

//管理课程资料页面
export const getTeachingCourses = (teacherId) => {
  return request.get('/api/teachers/teachingCourses', {
    params: {
      teacherId
    }
  })
}

export const getMaterials = (courseId) => {
  return request.get('/api/teachers/materials', {
    params: {
      courseId
    }
  })
}

export const submitMaterial = (courseId, multipartFile) => {
  const formData = new FormData()
  formData.append('courseId', courseId)
  formData.append('multipartFile', multipartFile)  // Ensure the key matches your @RequestParam

  return request.post('/api/teachers/submitMaterial', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
