# 学生信息管理系统

基于 SpringBoot + MyBatis + Thymeleaf + MySQL 的 Web 学生信息管理系统。

## 系统特性

- **前后端一体化架构**：采用 SpringBoot 框架简化配置与开发流程
- **数据持久化**：通过 MyBatis 完成数据库的持久化操作
- **动态页面渲染**：结合 Thymeleaf 实现动态页面渲染
- **数据存储**：使用 MySQL 作为后台数据存储
- **权限管理**：通过角色权限设计，区分管理员与普通用户权限
- **功能完整**：实现学生基本信息管理、成绩管理、健康信息管理及多维度查询功能

## 主要功能

### 学生信息管理
- 学生基本信息的增删改查
- 支持按学号、姓名、专业、班级、学籍状态等条件进行数据检索
- 学生信息的批量操作

### 成绩管理
- 成绩录入、修改、删除
- 支持按学生、课程、学期、考试类型等条件查询
- 自动计算绩点和成绩等级
- 成绩统计和分析

### 健康信息管理
- 学生健康档案管理
- 体检信息记录
- 健康数据统计和分析
- BMI指数计算

### 用户权限管理
- 管理员和普通用户角色区分
- 用户账号管理
- 权限控制

## 技术栈

- **后端框架**：Spring Boot 2.7.14
- **数据访问层**：MyBatis 2.3.1
- **模板引擎**：Thymeleaf
- **数据库**：MySQL 8.0
- **安全框架**：Spring Security
- **前端框架**：Bootstrap 5.1.3
- **图标库**：Font Awesome 6.0.0
- **构建工具**：Maven

## 环境要求

- JDK 8 或以上版本
- MySQL 8.0 或以上版本
- Maven 3.6 或以上版本

## 安装和运行

### 1. 克隆项目
```bash
git clone <项目地址>
cd student-management-system
```

### 2. 数据库配置
1. 创建 MySQL 数据库：
```sql
CREATE DATABASE student_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 执行初始化脚本：
```bash
mysql -u root -p student_management < src/main/resources/sql/init.sql
```

### 3. 修改配置文件
编辑 `src/main/resources/application.yml` 文件，修改数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/student_management?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
```

### 4. 编译和运行
```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

### 5. 访问系统
打开浏览器访问：http://localhost:8080/student-management

## 默认账号

### 管理员账号
- 用户名：admin
- 密码：admin123

### 学生账号
- 用户名：2023001
- 密码：123456

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/student/
│   │       ├── config/          # 配置类
│   │       ├── controller/      # 控制器层
│   │       ├── entity/          # 实体类
│   │       ├── mapper/          # 数据访问层
│   │       ├── service/         # 业务逻辑层
│   │       └── StudentManagementApplication.java
│   └── resources/
│       ├── mapper/              # MyBatis XML映射文件
│       ├── sql/                 # 数据库初始化脚本
│       ├── static/              # 静态资源
│       │   ├── css/
│       │   └── js/
│       ├── templates/           # Thymeleaf模板
│       └── application.yml      # 应用配置文件
└── test/                        # 测试代码
```

## 数据库设计

### 主要数据表
- **users**：用户表（管理员和普通用户）
- **students**：学生表
- **courses**：课程表
- **scores**：成绩表
- **health_records**：健康信息表

## 功能截图

### 登录页面
- 美观的登录界面
- 支持管理员和学生登录

### 管理员功能
- 仪表板：显示系统统计信息
- 用户管理：管理用户账号
- 学生管理：学生信息的增删改查
- 课程管理：课程信息的增删改查
- 成绩管理：成绩录入和查询
- 健康信息管理：健康档案管理
- 健康统计：健康数据统计分析

### 学生功能
- 个人信息查看
- 成绩查询和统计
- 健康信息查看
- 课程信息查看

## 开发说明

### 添加新功能
1. 在 `entity` 包中创建实体类
2. 在 `mapper` 包中创建数据访问接口
3. 在 `resources/mapper` 中创建 XML 映射文件
4. 在 `service` 包中创建业务逻辑类
5. 在 `controller` 包中创建控制器
6. 在 `templates` 中创建前端页面

### 数据库操作
- 使用 MyBatis 进行数据库操作
- 支持动态 SQL 查询
- 自动映射实体类和数据库表

### 前端开发
- 使用 Thymeleaf 模板引擎
- Bootstrap 5 响应式设计
- Font Awesome 图标库
- 自定义 CSS 和 JavaScript

## 注意事项

1. 确保 MySQL 服务已启动
2. 数据库连接信息配置正确
3. 默认端口为 8080，可在配置文件中修改
4. 建议在生产环境中修改默认密码
5. 定期备份数据库数据

## 许可证

本项目采用 MIT 许可证。

## 联系方式

如有问题或建议，请联系开发团队。
