
# 项目结构
![image](https://github.com/wxxz123/springboot-demo/blob/main/img-folder/Pasted%20image%2020251113211509.png)


```
graph TD
    A[浏览器/客户端] -->|HTTP 请求| B[StudentController]
    B -->|调用业务方法| C[StudentService]
    C -->|操作数据库| D[StudentRepository]
    D -->|JPA 自动生成 SQL| E[(MySQL 数据库)]
    
    C -->|需要转换时| F[StudentConverter]
    F -->|DTO ↔ Entity| C
    
    B -->|包装返回| G[Response]
    G -->|JSON 响应| A
    
    style B fill:#e1f5ff
    style C fill:#fff4e1
    style D fill:#f0e1ff
    style F fill:#e1ffe1
    style G fill:#ffe1e1
```


# 典型请求走读:查询学生(GET /student/1)

![image](https://github.com/wxxz123/springboot-demo/blob/main/img-folder/Pasted%20image%2020251114111558.png)

#### 伪代码详解(带行号注释)

java
```java
// ===== 1. 请求进入 Controller =====
// 文件: controller/StudentController.java 第 14~17 行
@GetMapping("/student/{id}")  // Spring 识别到 GET 请求,路径匹配
public Response<StudentDTO> getStudentById(@PathVariable long id) {
    // @PathVariable 把 URL 中的 {id} 提取出来,比如 /student/1 → id=1
    
    // 调用 Service 层查询
    StudentDTO dto = studentService.getStudentById(id);
    
    // 包装成统一返回格式
    return Response.newSuccess(dto);
}

// ===== 2. Service 层处理业务 =====
// 文件: service/StudentServiceImpl.java 第 23~26 行
@Override
public StudentDTO getStudentById(long id) {
    // 从仓库查询,如果没找到就抛异常
    Student student = studentRepository.findById(id)
                        .orElseThrow(RuntimeException::new);  // 等价于 () -> new RuntimeException()
    
    // 手动构造 DTO(这里没用 Converter,直接 new)
    return new StudentDTO(
        student.getId(),      // 从 Entity 取 ID
        student.getName(),    // 取姓名
        student.getEmail(),   // 取邮箱
        student.getAge()      // 取年龄
    );
}

// ===== 3. Repository 层操作数据库 =====
// 文件: dao/StudentRepository.java 第 9 行
public interface StudentRepository extends JpaRepository<Student, Long> {
    // 继承 JpaRepository 后,自动拥有 findById() 方法
    // JPA 会生成 SQL: SELECT * FROM student WHERE id = ?
}

// ===== 4. Entity 映射数据库表 =====
// 文件: dao/Student.java 第 7~23 行
@Entity              // 告诉 JPA 这是个数据库实体
@Table(name="student")  // 对应表名 student
public class Student {
    @Id  // 主键
    @GeneratedValue(strategy = IDENTITY)  // 自增
    private long id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="email")
    private String email;
    
    @Column(name="age")
    private int age;
    
    // ... getter/setter 省略
}

// ===== 5. 返回格式包装 =====
// 文件: Response.java 第 8~13 行
public static <K> Response<K> newSuccess(K data) {
    Response<K> response = new Response<>();
    response.setDate(data);      // 注意:这里字段名拼错了,应该是 setData
    response.setSuccess(true);   // 标记成功
    return response;
}
```

### 完整执行流程(一句话一步)


```
浏览器 发送 `GET http://localhost:8080/student/1`
Tomcat 接收请求,交给 Spring 的 `DispatcherServlet`
DispatcherServlet 根据 `@GetMapping` 路由到 `StudentController.getStudentById(1)`
Controller 提取路径参数 `id=1`,调用 `studentService.getStudentById(1)`
ServiceImpl 调用 `studentRepository.findById(1)`
JPA/Hibernate 生成 SQL:`SELECT * FROM student WHERE id=1`
MySQL 执行查询,返回结果集
Hibernate 把结果集转成 `Student` 对象(自动填充字段)
ServiceImpl 把 `Student` 转成 `StudentDTO`(只暴露需要的字段)
Controller 调用 `Response.newSuccess(dto)` 包装
Spring MVC 把 `Response` 对象转成 JSON(用 Jackson 库)
Tomcat 返回 HTTP 响应给浏览器
```


**最终 JSON 示例:**

json
```json
{
  "date": {
    "id": 1,
    "name": "张三",
    "email": "zhangsan@example.com",
    "age": 20
  },
  "success": true,
  "errorMsg": null
}
```


