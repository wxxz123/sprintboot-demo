# Spring Boot 学生管理系统

## 学习笔记

笔记都有照片，更容易理解！（文件夹proj-notes,自行查阅！！）

- **笔记1**: 用 Navicat 连接 MySQL 教程（老师用的终端连接，我用可视化工具更方便，可以直接写 SQL 语句）
- **笔记2**: 借助 Claude 理解整个项目结构和一个具体的实例流程（强力建议看一下

**教学视频**: https://www.bilibili.com/video/BV1gm411m7i6/

我自己跟着写和理解了一下项目，感觉对 Spring Boot 的执行流程有了大致了解，**总的来说还是建议做一下的！**

---

## 项目介绍

一个简单的学生信息管理系统，实现学生的增删改查功能。技术栈：
- Spring Boot 3.5.7
- Spring Data JPA + Hibernate
- MySQL

## 项目结构

```
boot-demo/
├── controller/      # 控制层 - 处理 HTTP 请求
├── service/        # 服务层 - 业务逻辑
├── dao/            # 数据访问层 - 实体类和 Repository
├── dto/            # 数据传输对象
└── converter/      # DTO 和 Entity 转换
```

典型的 **MVC 三层架构**:
```
Controller → Service → Repository → Database
```

## 能学到什么

- ✅ Spring Boot 项目结构和启动流程
- ✅ RESTful API 设计（GET/POST/PUT/DELETE）
- ✅ JPA 自动生成 SQL，不用手写数据库操作
- ✅ 三层架构的调用关系
- ✅ 实体类和数据库表的映射

## 👨‍💻 作者

跟着教学视频学习并实现，理解了 Spring Boot 的核心执行流程。

⭐ 如果这个项目对你有帮助，欢迎 Star！有问题欢迎提 Issue 讨论~
