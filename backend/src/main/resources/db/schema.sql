-- =========================================================
-- 在线培训考试系统 数据库初始化脚本
-- =========================================================
CREATE DATABASE IF NOT EXISTS online_exam DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE online_exam;

-- 用户表
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(200) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    avatar VARCHAR(500) COMMENT '头像',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    role VARCHAR(20) NOT NULL DEFAULT 'student' COMMENT '角色:admin/teacher/student',
    status INT NOT NULL DEFAULT 1 COMMENT '状态:0禁用1启用',
    org_id BIGINT COMMENT '所属机构ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '用户表';

-- 机构/组织表
DROP TABLE IF EXISTS organization;
CREATE TABLE organization (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '机构名称',
    code VARCHAR(50) COMMENT '机构编码',
    parent_id BIGINT DEFAULT 0 COMMENT '父级ID',
    type INT DEFAULT 1 COMMENT '1公司2部门3小组',
    leader VARCHAR(50) COMMENT '负责人',
    phone VARCHAR(20) COMMENT '联系电话',
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '机构组织表';

-- 课程分类
DROP TABLE IF EXISTS course_category;
CREATE TABLE course_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT 0,
    sort INT DEFAULT 0,
    icon VARCHAR(500),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '课程分类';

-- 课程表
DROP TABLE IF EXISTS course;
CREATE TABLE course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '课程标题',
    cover VARCHAR(500) COMMENT '封面图',
    description TEXT COMMENT '课程描述',
    category_id BIGINT COMMENT '分类ID',
    teacher_id BIGINT COMMENT '讲师ID',
    teacher_name VARCHAR(50) COMMENT '讲师姓名',
    price DECIMAL(10,2) DEFAULT 0 COMMENT '售价',
    original_price DECIMAL(10,2) DEFAULT 0 COMMENT '原价',
    status INT DEFAULT 0 COMMENT '0草稿1已发布2下架',
    is_free INT DEFAULT 0 COMMENT '0收费1免费',
    student_count INT DEFAULT 0 COMMENT '学习人数',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    avg_score DECIMAL(2,1) DEFAULT 0 COMMENT '平均评分',
    tags VARCHAR(500) COMMENT '标签',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '课程表';

-- 课程章节
DROP TABLE IF EXISTS course_chapter;
CREATE TABLE course_chapter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL COMMENT '课程ID',
    title VARCHAR(200) NOT NULL COMMENT '章节标题',
    parent_id BIGINT DEFAULT 0 COMMENT '父章节ID',
    sort INT DEFAULT 0,
    type INT DEFAULT 1 COMMENT '1章节2课时',
    media_id BIGINT COMMENT '媒体资源ID',
    media_url VARCHAR(500) COMMENT '视频/文档地址',
    duration INT DEFAULT 0 COMMENT '时长(秒)',
    is_free INT DEFAULT 0 COMMENT '0否1免费试看',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '课程章节';

-- 课程评价
DROP TABLE IF EXISTS course_review;
CREATE TABLE course_review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    user_name VARCHAR(50),
    score INT DEFAULT 5 COMMENT '评分1-5',
    content TEXT COMMENT '评价内容',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '课程评价';

-- 学习记录
DROP TABLE IF EXISTS study_record;
CREATE TABLE study_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    course_title VARCHAR(200),
    chapter_id BIGINT,
    chapter_title VARCHAR(200),
    progress INT DEFAULT 0 COMMENT '学习进度百分比',
    duration INT DEFAULT 0 COMMENT '学习时长(秒)',
    last_position INT DEFAULT 0 COMMENT '上次播放位置(秒)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '学习记录';

-- 题目分类
DROP TABLE IF EXISTS question_category;
CREATE TABLE question_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT 0,
    sort INT DEFAULT 0,
    question_count INT DEFAULT 0 COMMENT '题目数量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '题目分类';

-- 题目表
DROP TABLE IF EXISTS question;
CREATE TABLE question (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL COMMENT '题目内容',
    type INT NOT NULL COMMENT '1单选2多选3判断4填空5简答',
    options TEXT COMMENT '选项JSON',
    answer VARCHAR(500) NOT NULL COMMENT '正确答案',
    analysis TEXT COMMENT '解析',
    category_id BIGINT COMMENT '分类ID',
    category_name VARCHAR(100) COMMENT '分类名称',
    difficulty INT DEFAULT 1 COMMENT '1简单2中等3困难',
    score INT DEFAULT 2 COMMENT '分值',
    creator_id BIGINT,
    creator_name VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '题目表';

-- 试卷表
DROP TABLE IF EXISTS exam_paper;
CREATE TABLE exam_paper (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '试卷标题',
    description TEXT COMMENT '说明',
    total_score INT DEFAULT 100 COMMENT '总分',
    pass_score INT DEFAULT 60 COMMENT '及格分',
    duration INT DEFAULT 60 COMMENT '考试时长(分钟)',
    question_count INT DEFAULT 0 COMMENT '题目数量',
    type INT DEFAULT 1 COMMENT '1固定2随机',
    category_id BIGINT,
    creator_id BIGINT,
    creator_name VARCHAR(50),
    status INT DEFAULT 0 COMMENT '0草稿1启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '试卷表';

-- 试卷题目关联
DROP TABLE IF EXISTS exam_paper_question;
CREATE TABLE exam_paper_question (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paper_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    sort INT DEFAULT 0,
    score INT DEFAULT 2 COMMENT '该题分值'
) COMMENT '试卷题目关联';

-- 考试表
DROP TABLE IF EXISTS exam;
CREATE TABLE exam (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '考试标题',
    paper_id BIGINT COMMENT '试卷ID',
    paper_title VARCHAR(200) COMMENT '试卷标题',
    exam_type INT DEFAULT 1 COMMENT '1正式2模拟3练习',
    status INT DEFAULT 0 COMMENT '0草稿1已发布2进行中3已结束',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    duration INT DEFAULT 60 COMMENT '考试时长(分钟)',
    total_score INT DEFAULT 100,
    pass_score INT DEFAULT 60,
    max_attempt INT DEFAULT 1 COMMENT '最大尝试次数',
    show_answer INT DEFAULT 0 COMMENT '0不显示1交卷后2结束后',
    show_analysis INT DEFAULT 0 COMMENT '是否显示解析',
    participant_ids TEXT COMMENT '参与人员ID',
    participant_type VARCHAR(20) DEFAULT 'all' COMMENT 'participant/dept/role/all',
    creator_id BIGINT,
    creator_name VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '考试表';

-- 考试记录
DROP TABLE IF EXISTS exam_record;
CREATE TABLE exam_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exam_id BIGINT NOT NULL,
    exam_title VARCHAR(200),
    paper_id BIGINT,
    user_id BIGINT NOT NULL,
    user_name VARCHAR(50),
    user_score INT DEFAULT 0 COMMENT '得分',
    total_score INT DEFAULT 100 COMMENT '总分',
    pass_score INT DEFAULT 60 COMMENT '及格分',
    is_pass INT DEFAULT 0 COMMENT '0否1是',
    status INT DEFAULT 0 COMMENT '0未开始1答题中2已交卷3已批阅',
    start_time DATETIME,
    end_time DATETIME,
    duration INT DEFAULT 0 COMMENT '用时(秒)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '考试记录';

-- 答题详情
DROP TABLE IF EXISTS exam_answer;
CREATE TABLE exam_answer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    record_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    question_type INT COMMENT '题目类型',
    question_content TEXT COMMENT '题目内容',
    options TEXT COMMENT '选项',
    correct_answer VARCHAR(500) COMMENT '正确答案',
    user_answer VARCHAR(500) COMMENT '用户答案',
    is_correct INT DEFAULT 0 COMMENT '0错误1正确2部分正确',
    score INT DEFAULT 0 COMMENT '得分',
    question_score INT DEFAULT 0 COMMENT '题目分值',
    analysis TEXT COMMENT '解析'
) COMMENT '答题详情';

-- 知识库
DROP TABLE IF EXISTS knowledge;
CREATE TABLE knowledge (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content LONGTEXT,
    summary TEXT,
    category_id BIGINT,
    category_name VARCHAR(100),
    cover VARCHAR(500),
    author_id BIGINT,
    author_name VARCHAR(50),
    view_count INT DEFAULT 0,
    status INT DEFAULT 0 COMMENT '0草稿1已发布',
    tags VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '知识库';

-- 通知公告
DROP TABLE IF EXISTS notice;
CREATE TABLE notice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content LONGTEXT,
    type INT DEFAULT 1 COMMENT '1通知2公告',
    is_top INT DEFAULT 0 COMMENT '0否1是',
    status INT DEFAULT 1 COMMENT '0草稿1已发布',
    publisher_id BIGINT,
    publisher_name VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '通知公告';

-- 新闻资讯
DROP TABLE IF EXISTS news;
CREATE TABLE news (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content LONGTEXT,
    summary TEXT,
    cover VARCHAR(500),
    author VARCHAR(50),
    view_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '新闻资讯';

-- 证书/学习证明
DROP TABLE IF EXISTS certificate;
CREATE TABLE certificate (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    user_name VARCHAR(50),
    course_id BIGINT,
    course_title VARCHAR(200),
    certificate_no VARCHAR(50) UNIQUE COMMENT '证书编号',
    template VARCHAR(50) COMMENT '证书模板',
    type INT DEFAULT 1 COMMENT '1结业2培训3成绩',
    issue_date DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '证书/学习证明';

-- =========================================================
-- 初始化数据
-- =========================================================

-- 管理员 (密码: admin123 -> BCrypt加密)
INSERT INTO sys_user (username, password, real_name, role, status) VALUES
('admin', '$2a$10$4Rd3MK9YXMvBETg0qv.ol.l6cqSMk8LJZBQs45rzjjSvSFKqCtlbG', '系统管理员', 'admin', 1),
('teacher01', '$2a$10$4Rd3MK9YXMvBETg0qv.ol.l6cqSMk8LJZBQs45rzjjSvSFKqCtlbG', '张老师', 'teacher', 1),
('teacher02', '$2a$10$4Rd3MK9YXMvBETg0qv.ol.l6cqSMk8LJZBQs45rzjjSvSFKqCtlbG', '李老师', 'teacher', 1),
('student01', '$2a$10$4Rd3MK9YXMvBETg0qv.ol.l6cqSMk8LJZBQs45rzjjSvSFKqCtlbG', '王同学', 'student', 1),
('student02', '$2a$10$4Rd3MK9YXMvBETg0qv.ol.l6cqSMk8LJZBQs45rzjjSvSFKqCtlbG', '赵同学', 'student', 1);

-- 机构
INSERT INTO organization (name, code, type, leader) VALUES
('总公司', 'HQ', 1, '系统管理员'),
('技术部', 'TECH', 2, '张老师'),
('市场部', 'MKT', 2, '李老师');

-- 课程分类
INSERT INTO course_category (name, sort) VALUES
('编程开发', 1),
('人工智能', 2),
('项目管理', 3),
('办公技能', 4),
('职业素养', 5);

-- 示例课程
INSERT INTO course (title, description, category_id, teacher_id, teacher_name, status, is_free, avg_score) VALUES
('Java从入门到精通', '系统学习Java编程语言，从基础语法到高级特性，全面提升编程能力。', 1, 2, '张老师', 1, 1, 4.5),
('Python数据分析', '学习Python数据分析与可视化，掌握数据处理核心技能。', 1, 2, '张老师', 1, 0, 4.8),
('人工智能基础', '了解AI基本概念、机器学习算法和深度学习框架。', 2, 3, '李老师', 1, 0, 4.6),
('PMP项目管理', '系统学习项目管理知识体系，助力PMP认证考试。', 3, 3, '李老师', 1, 0, 4.3);

-- 课程章节
INSERT INTO course_chapter (course_id, title, type, sort) VALUES
(1, '第一章 Java基础', 1, 1),
(1, '1.1 环境搭建', 2, 1),
(1, '1.2 基本数据类型', 2, 2),
(1, '1.3 流程控制', 2, 3),
(1, '第二章 面向对象', 1, 2),
(1, '2.1 类与对象', 2, 1),
(1, '2.2 继承与多态', 2, 2);

-- 题目分类
INSERT INTO question_category (name, question_count) VALUES
('Java基础', 0),
('Python基础', 0),
('项目管理', 0),
('通用常识', 0);

-- 示例题目
INSERT INTO question (content, type, options, answer, analysis, category_id, category_name, difficulty, score, creator_id, creator_name) VALUES
('Java中哪个关键字用于定义类？', 1, '[{"key":"A","value":"interface"},{"key":"B","value":"class"},{"key":"C","value":"struct"},{"key":"D","value":"define"}]', 'B', 'Java使用class关键字定义类', 1, 'Java基础', 1, 2, 2, '张老师'),
('以下哪些是Java的基本数据类型？', 2, '[{"key":"A","value":"int"},{"key":"B","value":"String"},{"key":"C","value":"boolean"},{"key":"D","value":"double"}]', 'A,C,D', 'String是引用类型，不是基本数据类型', 1, 'Java基础', 2, 4, 2, '张老师'),
('Java是一种面向对象的编程语言。', 3, '[{"key":"A","value":"正确"},{"key":"B","value":"错误"}]', 'A', 'Java确实是面向对象的编程语言', 1, 'Java基础', 1, 2, 2, '张老师'),
('JDK的全称是Java ____ Kit', 4, NULL, 'Development', 'JDK = Java Development Kit', 1, 'Java基础', 2, 3, 2, '张老师'),
('简述Java中重载(Overload)和重写(Override)的区别。', 5, NULL, '重载发生在同一个类中，方法名相同但参数列表不同；重写发生在子类中，子类重新定义父类的方法，方法签名完全相同。', '重载是编译时多态，重写是运行时多态', 1, 'Java基础', 3, 10, 2, '张老师'),
('Python中哪个函数用于输出内容？', 1, '[{"key":"A","value":"echo"},{"key":"B","value":"print"},{"key":"C","value":"console.log"},{"key":"D","value":"printf"}]', 'B', 'Python使用print()函数输出', 2, 'Python基础', 1, 2, 3, '李老师'),
('项目管理的三大约束是？', 2, '[{"key":"A","value":"时间"},{"key":"B","value":"成本"},{"key":"C","value":"范围"},{"key":"D","value":"质量"}]', 'A,B,C', '项目管理三大约束:时间、成本、范围(铁三角)', 3, '项目管理', 2, 4, 3, '李老师');

-- 示例试卷
INSERT INTO exam_paper (title, description, total_score, pass_score, duration, question_count, type, creator_id, creator_name, status) VALUES
('Java基础测试卷', '测试Java基础知识掌握情况', 100, 60, 60, 5, 1, 2, '张老师', 1);

-- 试卷题目关联
INSERT INTO exam_paper_question (paper_id, question_id, sort, score) VALUES
(1, 1, 1, 20), (1, 2, 2, 20), (1, 3, 3, 20), (1, 4, 4, 15), (1, 5, 5, 25);

-- 示例考试
INSERT INTO exam (title, paper_id, paper_title, exam_type, status, start_time, end_time, duration, total_score, pass_score, creator_id, creator_name) VALUES
('Java期中考试', 1, 'Java基础测试卷', 1, 1, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 60, 100, 60, 2, '张老师'),
('Java模拟测试', 1, 'Java基础测试卷', 2, 1, NOW(), DATE_ADD(NOW(), INTERVAL 60 DAY), 60, 100, 60, 2, '张老师');

-- 通知公告
INSERT INTO notice (title, content, type, is_top, publisher_name) VALUES
('系统上线通知', '在线培训考试系统已正式上线，欢迎各位使用！', 2, 1, '系统管理员'),
('考试安排通知', 'Java期中考试将于下周一开始，请同学们做好准备。', 1, 0, '张老师');

-- 新闻资讯
INSERT INTO news (title, content, summary, author, status) VALUES
('在线学习新趋势', '随着互联网技术的不断发展，在线学习已经成为主流学习方式之一...', '探讨在线学习的发展趋势与未来方向', '编辑部', 1);
