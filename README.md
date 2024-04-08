<p align="center">
    <a href="" target="_blank">
      <img src="./docs/imgs/icon.jpg" width="280" />
    </a>
</p>
<h1 align="center">面试大师 - InterviewMaster</h1>
<p align="center"><strong>一个旨在帮助面试准备的系统，提供丰富的面试题目库和智能判题功能。<br>无论你是面试者还是面试官，面试大师都将成为你的最佳伙伴。<em>持续更新中～</em></strong></p>
<div align="center">
    <a href=""><img src="https://img.shields.io/badge/github-项目地址-yellow.svg?style=plasticr"></a>
    <a href=""><img src="https://img.shields.io/badge/前端-项目地址-blueviolet.svg?style=plasticr"></a>
</div>



### 功能特性

1. **题库管理**：
    - 提供 RESTful API 接口，支持题目的增删改查操作。
    - 支持从 Excel 表格导入题目数据到数据库中。

2. **自动刷题模块**：
    - 提供接口供前端调用，生成随机面试题目并返回给前端。

3. **判题系统**：
    - 实现 AI 判题功能，接收面试者的回答字符串，返回判题结果和解析建议。

### 技术栈

|        技术         | 说明                             | 官网                                                         |
| :-----------------: | -------------------------------- | ------------------------------------------------------------ |
|     SpringBoot      | web 开发必备框架                 | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
|      Hibernate      | ORM 框架                         | https://hibernate.org/                                       |
|        Redis        | 缓存加速，多数据结构支持业务功能 | [https://redis.io](https://redis.io)                         |
|      Caffeine       | 本地缓存                         | http://caffe.berkeleyvision.org/                             |
|       Docker        | 应用容器引擎                     | [https://www.docker.com](https://www.docker.com)             |
|       Lombok        | 简化代码                         | [https://projectlombok.org](https://projectlombok.org)       |
|       Hutool        | Java工具类库                     | https://github.com/looly/hutool                              |
|     Swagger-UI      | API文档生成工具                  | https://github.com/swagger-api/swagger-ui                    |
| Hibernate-validator | 接口校验框架                     | [hibernate.org/validator/](hibernate.org/validator/)         |

### 环境配置和部署

1. **数据库配置**：
    - 确保已安装并启动 MySQL 数据库。
    - 创建数据库和表结构，确保与后端项目配置文件中的数据库连接信息一致。

2. **项目配置**：
    - 克隆或下载后端项目代码到本地。
    - 打开项目，在 `application.properties` 文件中配置数据库连接信息。

3. **运行项目**：启动 InterviewMasterApplication 。

4. **接口测试**：
    - 使用 API 测试工具测试题库管理、自动刷题和判题系统的接口功能。

### 接口文档

- 提供接口文档，描述后端项目中各个模块的 API 接口、请求方法和参数说明。

### 参与贡献

如果您对项目有任何建议或想要贡献代码，欢迎提交 Issue 或 Pull Request。我们期待您的参与，共同完善和改进面试大师项目！
