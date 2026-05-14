package com.exam.common;

/**
 * 系统常量
 */
public class Constants {
    /** 用户角色 */
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_TEACHER = "teacher";
    public static final String ROLE_STUDENT = "student";

    /** 题目类型 */
    public static final int QUESTION_SINGLE = 1;    // 单选题
    public static final int QUESTION_MULTI = 2;     // 多选题
    public static final int QUESTION_JUDGE = 3;     // 判断题
    public static final int QUESTION_FILL = 4;      // 填空题
    public static final int QUESTION_ESSAY = 5;     // 简答题

    /** 考试状态 */
    public static final int EXAM_DRAFT = 0;         // 草稿
    public static final int EXAM_PUBLISHED = 1;     // 已发布
    public static final int EXAM_ONGOING = 2;        // 进行中
    public static final int EXAM_ENDED = 3;          // 已结束

    /** 课程状态 */
    public static final int COURSE_DRAFT = 0;
    public static final int COURSE_PUBLISHED = 1;
    public static final int COURSE_OFFLINE = 2;
}
