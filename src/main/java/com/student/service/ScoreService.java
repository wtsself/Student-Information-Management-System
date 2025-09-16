package com.student.service;

import com.student.entity.Score;
import com.student.mapper.ScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 成绩业务逻辑层
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Service
@Transactional
public class ScoreService {
    
    @Autowired
    private ScoreMapper scoreMapper;
    
    /**
     * 根据ID查询成绩
     * 
     * @param id 成绩ID
     * @return 成绩信息
     */
    public Score findById(Long id) {
        return scoreMapper.findById(id);
    }
    
    /**
     * 根据学生ID和课程ID查询成绩
     * 
     * @param studentId 学生ID
     * @param courseId 课程ID
     * @return 成绩信息
     */
    public Score findByStudentAndCourse(Long studentId, Long courseId) {
        return scoreMapper.findByStudentAndCourse(studentId, courseId);
    }
    
    /**
     * 查询所有成绩
     * 
     * @return 成绩列表
     */
    public List<Score> findAll() {
        return scoreMapper.findAll();
    }
    
    /**
     * 分页查询成绩
     * 
     * @param page 页码（从1开始）
     * @param size 每页大小
     * @return 成绩列表
     */
    public List<Score> findByPage(int page, int size) {
        int offset = (page - 1) * size;
        return scoreMapper.findByPage(offset, size);
    }
    
    /**
     * 根据学生ID查询成绩
     * 
     * @param studentId 学生ID
     * @return 成绩列表
     */
    public List<Score> findByStudentId(Long studentId) {
        return scoreMapper.findByStudentId(studentId);
    }
    
    /**
     * 根据课程ID查询成绩
     * 
     * @param courseId 课程ID
     * @return 成绩列表
     */
    public List<Score> findByCourseId(Long courseId) {
        return scoreMapper.findByCourseId(courseId);
    }
    
    /**
     * 根据学期查询成绩
     * 
     * @param semester 学期
     * @return 成绩列表
     */
    public List<Score> findBySemester(String semester) {
        return scoreMapper.findBySemester(semester);
    }
    
    /**
     * 根据考试类型查询成绩
     * 
     * @param examType 考试类型
     * @return 成绩列表
     */
    public List<Score> findByExamType(String examType) {
        return scoreMapper.findByExamType(examType);
    }
    
    /**
     * 多条件查询成绩
     * 
     * @param studentId 学生ID
     * @param courseId 课程ID
     * @param semester 学期
     * @param examType 考试类型
     * @return 成绩列表
     */
    public List<Score> findByConditions(Long studentId, Long courseId, String semester, String examType) {
        return scoreMapper.findByConditions(studentId, courseId, semester, examType);
    }
    
    /**
     * 分页多条件查询成绩
     * 
     * @param studentId 学生ID
     * @param courseId 课程ID
     * @param semester 学期
     * @param examType 考试类型
     * @param page 页码
     * @param size 每页大小
     * @return 成绩列表
     */
    public List<Score> findByConditionsWithPage(Long studentId, Long courseId, String semester, String examType, int page, int size) {
        List<Score> allScores = scoreMapper.findByConditions(studentId, courseId, semester, examType);
        int offset = (page - 1) * size;
        int endIndex = Math.min(offset + size, allScores.size());
        
        if (offset >= allScores.size()) {
            return List.of();
        }
        
        return allScores.subList(offset, endIndex);
    }
    
    /**
     * 查询学生成绩统计信息
     * 
     * @param studentId 学生ID
     * @return 成绩统计信息
     */
    public List<Score> findStudentScoreStats(Long studentId) {
        return scoreMapper.findStudentScoreStats(studentId);
    }
    
    /**
     * 查询课程成绩统计信息
     * 
     * @param courseId 课程ID
     * @return 成绩统计信息
     */
    public List<Score> findCourseScoreStats(Long courseId) {
        return scoreMapper.findCourseScoreStats(courseId);
    }
    
    /**
     * 统计成绩总数
     * 
     * @return 成绩总数
     */
    public int countAll() {
        return scoreMapper.countAll();
    }
    
    /**
     * 根据条件统计成绩数量
     * 
     * @param studentId 学生ID
     * @param courseId 课程ID
     * @param semester 学期
     * @param examType 考试类型
     * @return 成绩数量
     */
    public int countByConditions(Long studentId, Long courseId, String semester, String examType) {
        return scoreMapper.countByConditions(studentId, courseId, semester, examType);
    }
    
    /**
     * 创建成绩
     * 
     * @param score 成绩信息
     * @return 是否成功
     */
    public boolean createScore(Score score) {
        // 检查该学生该课程该考试类型该学期是否已有成绩
        Score existingScore = scoreMapper.findByStudentAndCourse(score.getStudentId(), score.getCourseId());
        if (existingScore != null && existingScore.getExamType().equals(score.getExamType()) 
            && existingScore.getSemester().equals(score.getSemester())) {
            throw new RuntimeException("该学生该课程该考试类型该学期已有成绩记录");
        }
        
        // 自动计算绩点
        if (score.getScore() != null) {
            score.setGradePoint(score.calculateGradePoint());
        }
        
        return scoreMapper.insert(score) > 0;
    }
    
    /**
     * 更新成绩
     * 
     * @param score 成绩信息
     * @return 是否成功
     */
    public boolean updateScore(Score score) {
        // 检查成绩是否存在
        Score existingScore = scoreMapper.findById(score.getId());
        if (existingScore == null) {
            throw new RuntimeException("成绩不存在");
        }
        
        // 自动计算绩点
        if (score.getScore() != null) {
            score.setGradePoint(score.calculateGradePoint());
        }
        
        return scoreMapper.update(score) > 0;
    }
    
    /**
     * 删除成绩
     * 
     * @param id 成绩ID
     * @return 是否成功
     */
    public boolean deleteScore(Long id) {
        // 检查成绩是否存在
        Score score = scoreMapper.findById(id);
        if (score == null) {
            throw new RuntimeException("成绩不存在");
        }
        
        return scoreMapper.deleteById(id) > 0;
    }
    
    /**
     * 根据学生ID删除成绩
     * 
     * @param studentId 学生ID
     * @return 是否成功
     */
    public boolean deleteByStudentId(Long studentId) {
        return scoreMapper.deleteByStudentId(studentId) > 0;
    }
    
    /**
     * 根据课程ID删除成绩
     * 
     * @param courseId 课程ID
     * @return 是否成功
     */
    public boolean deleteByCourseId(Long courseId) {
        return scoreMapper.deleteByCourseId(courseId) > 0;
    }
    
    /**
     * 批量删除成绩
     * 
     * @param ids 成绩ID列表
     * @return 是否成功
     */
    public boolean deleteScores(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        
        return scoreMapper.deleteByIds(ids) > 0;
    }
    
    /**
     * 计算学生平均绩点
     * 
     * @param studentId 学生ID
     * @return 平均绩点
     */
    public BigDecimal calculateStudentGPA(Long studentId) {
        List<Score> scores = scoreMapper.findByStudentId(studentId);
        if (scores.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalGradePoints = BigDecimal.ZERO;
        BigDecimal totalCredits = BigDecimal.ZERO;
        
        for (Score score : scores) {
            if (score.getScore() != null && score.getCourse() != null && score.getCourse().getCredits() != null) {
                totalGradePoints = totalGradePoints.add(score.getGradePoint().multiply(score.getCourse().getCredits()));
                totalCredits = totalCredits.add(score.getCourse().getCredits());
            }
        }
        
        if (totalCredits.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        return totalGradePoints.divide(totalCredits, 2, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 计算课程平均分
     * 
     * @param courseId 课程ID
     * @return 平均分
     */
    public BigDecimal calculateCourseAverage(Long courseId) {
        List<Score> scores = scoreMapper.findByCourseId(courseId);
        if (scores.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalScore = BigDecimal.ZERO;
        int count = 0;
        
        for (Score score : scores) {
            if (score.getScore() != null) {
                totalScore = totalScore.add(score.getScore());
                count++;
            }
        }
        
        if (count == 0) {
            return BigDecimal.ZERO;
        }
        
        return totalScore.divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP);
    }
}
