_注意：以下使用方式基于已集成基础框架核心包（详见基础框架集成)_

## 接口定义模块

### 引入依赖
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.store59</groupId>
            <artifactId>kylin-dependencies</artifactId>
            <version>2.1-SNAPSHOT</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <dependency>
        <groupId>com.store59</groupId>
        <artifactId>kylin-rpc-context</artifactId>
    </dependency>
</dependencies>
```

### 注解使用

在对外提供远程服务的接口上增加注解

```java
@Remote
public interface MsgService {

    List<Message> getAllMsgList();

    ...
}
```

也可以设置接口建议默认超时时间, 该设置用于客户端不指定情况下默认使用; 
path配置服务暴露路径, 缺省为接口全路径名(例如: /com.store59.examplerpc.MsgService)

```java
@Remote(path="/msg", connectTimeout=2000, readTimeout=1500)
public interface MsgService {

    List<Message> getAllMsgList();

    ...
}
```

### 配置包名和服务名映射关系

修改gitlab上global-default.yml, 增加自身项目映射配置

例如: 增加rpc example包名和服务映射关系.

```yml
reference:
  mapping:
    ...
    com.store59.exmaple.rpc: example-rpc-service
```

## 服务端

### 引入依赖

```xml
<dependency>
   <groupId>com.store59</groupId>
   <artifactId>kylin-rpc-server</artifactId>
</dependency>
```

### 编写接口实现类

例如:
```java
 public class MessageServiceImpl implements MsgService {

    private static Logger logger = LoggerFactory.getLogger(RemotingMessageService.class);

    ...

    @Override
    public List<Message> getAllMsgList() {
        ...
    }

    @Override
    public boolean addMsg(Message message) {
        ...
    }
    ...
 }
```

## 客户端

### 引入依赖

```xml
<dependency>
    <groupId>com.store59</groupId>
    <artifactId>kylin-rpc-client</artifactId>
</dependency>
```

### 注解使用

```java
@RemoteResource
private MsgService msgService;

@RemoteResource(readTimeout = 3000, readTimeout=1500)
private MsgService msgService1;
```

注意: @RemoteResource中保留字段path用于兼容老版本@RemoteService做服务端的场景. 如果服务的接口上定义了@Remote注解, 则这里配置path无效.
