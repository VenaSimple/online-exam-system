# 在线培训考试系统

集"学、练、考"于一体的在线培训考试平台，功能对标微厦学习平台。

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端 | Spring Boot 3.1 + MyBatis-Plus + Spring Security + JWT |
| 前端 | Vue 3 + Element Plus + Vite + Pinia |
| 数据库 | MySQL 8.0 |
| 缓存 | Redis |

## 功能模块

### 📚 学习模块（学）
- 视频课程在线学习
- 课程分类浏览
- 学习进度跟踪
- 课程评价评分
- 知识库文章

### ✏️ 练习模块（练）
- 题库管理（单选/多选/判断/填空/简答）
- 在线刷题练习
- 按类型/难度筛选
- 答案解析查看

### 📝 考试模块（考）
- 试卷管理（固定/随机组卷）
- 考试发布与管理
- 在线答题（题号导航+答题卡）
- 自动阅卷
- 成绩查询与统计
- 多场次考试支持

### 👥 管理模块
- 用户管理（学生/教师/管理员三角色）
- 组织架构管理
- 通知公告管理
- 新闻资讯管理
- 证书/学习证明
- 数据统计仪表盘

## 项目结构

```
online-exam-system/
├── backend/                          # 后端SpringBoot项目
│   ├── pom.xml
│   └── src/main/java/com/exam/
│       ├── ExamApplication.java       # 启动类
│       ├── config/                    # 配置类
│       ├── common/                    # 通用类
│       ├── security/                  # 安全认证
│       ├── entity/                    # 实体类(16个)
│       ├── mapper/                    # 数据访问层(17个)
│       ├── dto/                       # 数据传输对象
│       ├── vo/                        # 视图对象
│       ├── service/                   # 服务接口(14个)
│       ├── service/impl/              # 服务实现(13个)
│       └── controller/               # 控制器(13个)
│
├── frontend/                          # 前端Vue3项目
│   ├── package.json
│   ├── vite.config.js
│   └── src/
│       ├── api/                       # API接口(6个)
│       ├── router/                    # 路由配置
│       ├── stores/                    # 状态管理
│       ├── layout/                    # 布局组件(3个)
│       └── views/                     # 页面(35个)
│           ├── login/                 # 登录注册
│           ├── student/               # 学生端(9个页面)
│           ├── teacher/               # 教师端(11个页面)
│           └── admin/                 # 管理端(11个页面)
```

## 快速部署

### 环境要求
- JDK 17+
- Node.js 18+
- MySQL 8.0+
- Redis (可选)
- Maven 3.8+

### 1. 初始化数据库

```bash
# 创建数据库并导入初始化脚本
mysql -u root -p < backend/src/main/resources/db/schema.sql
```

### 2. 后端启动

```bash
cd backend

# 修改配置 (如数据库连接信息)
vim src/main/resources/application.yml

# 编译打包
mvn clean package -DskipTests

# 运行
java -jar target/online-exam-system-1.0.0.jar
```

后端启动后访问: http://localhost:8088/api
接口文档: http://localhost:8088/api/doc.html

### 3. 前端启动

```bash
cd frontend

# 安装依赖
npm install

# 开发模式
npm run dev

# 生产构建
npm run build
```

前端启动后访问: http://localhost:3000

### 4. 默认账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | admin123 |
| 教师 | teacher01 | admin123 |
| 教师 | teacher02 | admin123 |
| 学生 | student01 | admin123 |
| 学生 | student02 | admin123 |

> 注意: 初始密码加密前的明文为 `admin123`，数据库中存储的是BCrypt加密后的值。如果需要生成新的密码hash，请使用Spring Security的BCryptPasswordEncoder。

## API接口列表

### 认证模块
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/auth/login | 登录 |
| POST | /api/auth/register | 注册 |

### 用户模块
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/user/list | 用户列表 |
| GET | /api/user/info | 当前用户信息 |
| POST | /api/user | 创建用户 |
| PUT | /api/user | 更新用户 |
| DELETE | /api/user/{id} | 删除用户 |

### 课程模块
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/course/list | 课程列表 |
| GET | /api/course/student | 学生课程 |
| GET | /api/course/{id} | 课程详情 |
| POST | /api/course | 创建课程 |
| PUT | /api/course | 更新课程 |

### 题库模块
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/question/list | 题目列表 |
| POST | /api/question | 创建题目 |
| PUT | /api/question | 更新题目 |
| POST | /api/question/import | 批量导入 |

### 考试模块
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/exam/list | 考试列表 |
| POST | /api/exam | 创建考试 |
| POST | /api/exam/{id}/start | 开始考试 |
| POST | /api/exam/submit | 交卷 |
| GET | /api/exam/result/{id} | 查看结果 |

### 试卷模块
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/paper/list | 试卷列表 |
| POST | /api/paper | 创建试卷 |
| PUT | /api/paper | 更新试卷 |

### 其他模块
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/knowledge/list | 知识库列表 |
| GET | /api/notice/list | 通知公告列表 |
| GET | /api/news/list | 新闻资讯列表 |
| GET | /api/org/tree | 组织架构树 |
| GET | /api/dashboard/stats | 仪表盘统计 |

## 生产部署建议

1. **Nginx反向代理**: 前端静态文件由Nginx托管，API请求代理到后端
2. **MySQL主从**: 生产环境建议配置数据库主从
3. **Redis缓存**: 启用Redis提升性能
4. **文件存储**: 视频和文档建议使用对象存储(OSS/MinIO)
5. **HTTPS**: 配置SSL证书
