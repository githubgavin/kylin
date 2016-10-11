# 非jvm程序使用说明

对于非jvm语言应用程序需要使用Eureka, Ribbon和Config Server的功能, 可以参考本文.

![原理图](../imgs/sidecar.png)

## 功能
* 通过简单的HTTP API去获取所有注册的实例信息(包括host和port信息)
* 通过HTTP API获取某个应用的注册信息
* 通过HTTP API获取configserver提供的配置服务
* 通过HTTP API直接代理使用具体服务
* 非JVM应用程序提供健康检查, 向eureka同步应用up还是down的状态.

_注意, 如果支持xml解析且不需要后两点支持, 可直接使用eureka和configserver的HTTP API接口, 无需使用sidecar, api文档详见注册中心和配置中心说明_

## 使用说明
### 配置sidecar
修改git上的{appName}.yml(appName为代理非jvm的sidecar程序的命名,默认是sidecar), 配置非jvm程序监听端口, 配置健康检查url
```
sidecar:
  port: 3000
  health-uri: http://localhost:3000/manage/health.json
```

保证接口可以返回一个类似如下的json文档:
```
{
  "status":"UP"
}
```

### 运行sidecar应用程序
```
# 下载sidecar.war 

curl -Lo sidecar.war http://192.168.30.142/packages/sidecar.war

# 参数appName为sidecar程序命名, 用于服务不同非jvm程序, 注, 对于运行在同一个进程上的不同服务可以选择共用同一个sidecar.

java -jar -Xms256m -Xmx512m -D"appName=sidecar" -D"spring.profiles.active=dev" sidecar.war
```

### API

* 获取所有注册服务信息

http://localhost:5678/hosts

* 获取某个服务的注册信息

http://localhost:5678/hosts/{applicationName}

例如: http://localhost:5678/hosts/order-service

```
[
  {
    "host": "192.168.30.126",
    "port": 8080,
    "uri": "http://192.168.30.126:8080",
    "serviceId": "ORDER-SERVICE",
    "metadata": {
      "appVersion": "1.0",
      "instanceId": "order-service:634359a72b4741817aec6a7fee10c858",
      "contextPath": "/orderservice"
    },
    "secure": false,
    "instanceInfo": {
      "instanceId": "192.168.30.126:order-service:8080",
      "app": "ORDER-SERVICE",
      "appGroupName": null,
      "ipAddr": "192.168.30.126",
      "sid": "na",
      "homePageUrl": "http://192.168.30.126:8080/orderservice",
      "statusPageUrl": "http://192.168.30.126:8080/orderservice/manage/info",
      "healthCheckUrl": "http://192.168.30.126:8080/orderservice/manage/health",
      "secureHealthCheckUrl": null,
      "vipAddress": "order-service",
      "secureVipAddress": "order-service",
      "countryId": 1,
      "dataCenterInfo": {
        "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
        "name": "MyOwn"
      },
      "hostName": "192.168.30.126",
      "status": "UP",
      "leaseInfo": {
        "renewalIntervalInSecs": 10,
        "durationInSecs": 90,
        "registrationTimestamp": 1474183058067,
        "lastRenewalTimestamp": 1474380200139,
        "evictionTimestamp": 0,
        "serviceUpTimestamp": 1474183058068
      },
      "isCoordinatingDiscoveryServer": false,
      "metadata": {
        "appVersion": "1.0",
        "instanceId": "order-service:634359a72b4741817aec6a7fee10c858",
        "contextPath": "/orderservice"
      },
      "lastUpdatedTimestamp": 1474183058068,
      "lastDirtyTimestamp": 1474183057861,
      "actionType": "ADDED",
      "asgName": null,
      "overriddenStatus": "UNKNOWN"
    }
  }
]
```

* 直接代理访问某个应用

http://localhost:5678/{applicationName}/{applicationContext}/XXX

例如:

http://localhost:5678/order-service/orderservice/manage/info

```
{
    app: {
        version: "1.0",
        name: "order-service",
        description: "订单服务"
    }
}
```

该代理功能自带负载均衡能力, 即对于非jvm程序调用已注册的服务, 可以直接通过这种方式.

* 使用配置中心

http://localhost:5678/configserver/configserver/{appName}/default
OR http://localhost:5678/configserver/configserver/{appName}.json

例如:

```
http://localhost:5678/configserver/configserver/order-service/default

返回:

{
  name: "order-service",
  profiles: [
    "default"
  ],
  label: null,
  version: "bcb91d537cccf4bb45ccf3ef386d7743d55da29b",
  propertySources: [
    {
      name: "http://code.59store.com/config/config-dev.git/order-service.yml",
      source: {
        datasource.buyerDataSource.host: "192.168.30.126",
        datasource.buyerDataSource.port: 8066,
        datasource.buyerDataSource.db: "db59buyerorder_dev",
        datasource.buyerDataSource.username: "admin",
        datasource.buyerDataSource.password: "admin123",
        datasource.buyerDataSource.minIdle: 11,
        datasource.buyerDataSource.maxIdle: 20,
        datasource.buyerDataSource.maxActive: 20,
        datasource.buyerDataSource.validationInterval: 30000,
        datasource.buyerDataSource.validationQueryTimeout: 5,
        datasource.buyerDataSource.timeBetweenEvictionRunsMillis: 1800000,
        datasource.buyerDataSource.initialSize: 2,
        datasource.buyerDataSource.maxWait: 10000,
        datasource.buyerDataSource.removeAbandonedTimeout: 60,
        datasource.buyerDataSource.minEvictableIdleTimeMillis: 30000,
        datasource.sellerDataSource.host: "192.168.30.126",
        datasource.sellerDataSource.port: 8066,
        datasource.sellerDataSource.db: "db59sellerorder_dev",
        datasource.sellerDataSource.username: "admin",
        datasource.sellerDataSource.password: "admin123",
        datasource.sellerDataSource.minIdle: 10,
        datasource.sellerDataSource.maxIdle: 20,
        datasource.sellerDataSource.maxActive: 20,
        datasource.sellerDataSource.validationInterval: 30000,
        datasource.sellerDataSource.validationQueryTimeout: 5,
        datasource.sellerDataSource.timeBetweenEvictionRunsMillis: 1800000,
        datasource.sellerDataSource.initialSize: 2,
        datasource.sellerDataSource.maxWait: 10000,
        datasource.sellerDataSource.removeAbandonedTimeout: 60,
        datasource.sellerDataSource.minEvictableIdleTimeMillis: 30000,
        remoteService.productService: "product-service",
        remoteService.couponService: "coupon-service",
        logging.sentry.dsn: "http://125e017cbcdd44de914515f54772d8de:b5889daf56f94b71be0dccf2fe7597e2@121.40.181.82:9000/116"
      }
    }
  ]
}


http://localhost:5678/configserver/configserver/order-service.json

返回:

{
  datasource: {
    buyerDataSource: {
      db: "db59buyerorder_dev",
      host: "192.168.30.126",
      initialSize: 2,
      maxActive: 20,
      maxIdle: 20,
      maxWait: 10000,
      minEvictableIdleTimeMillis: 30000,
      minIdle: 11,
      password: "admin123",
      port: 8066,
      removeAbandonedTimeout: 60,
      timeBetweenEvictionRunsMillis: 1800000,
      username: "admin",
      validationInterval: 30000,
      validationQueryTimeout: 5
    },
    sellerDataSource: {
      db: "db59sellerorder_dev",
      host: "192.168.30.126",
      initialSize: 2,
      maxActive: 20,
      maxIdle: 20,
      maxWait: 10000,
      minEvictableIdleTimeMillis: 30000,
      minIdle: 10,
      password: "admin123",
      port: 8066,
      removeAbandonedTimeout: 60,
      timeBetweenEvictionRunsMillis: 1800000,
      username: "admin",
      validationInterval: 30000,
      validationQueryTimeout: 5
    }
  },
  logging: {
    sentry: {
      dsn: "http://125e017cbcdd44de914515f54772d8de:b5889daf56f94b71be0dccf2fe7597e2@121.40.181.82:9000/116"
    }
  },
  remoteService: {
    couponService: "coupon-service",
    productService: "product-service"
  }
}

```
