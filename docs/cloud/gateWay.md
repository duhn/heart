# 服务网关

> 引用自 [RuoYi](http://doc.ruoyi.vip/ruoyi-cloud/cloud/gateway.html)

## 基本介绍
 + Spring Cloud Gateway  
 `Spring Cloud Gateway` 是基于 `Spring` 生态系统上构建的 `API` 网关，包括: `Spring 5.x`, `Spring Boot 2.x` 和 `Project Reactor`  
 `Spring Cloud Gateway` 旨在提供一种简单有效的方法来路由到 `API`，并为它们提供跨领域的关注点，例如: `安全性`、`监视/指标`、`限流`等

 + 什么是服务网关  
 `API Gateway (APIGW / API 网关)`，顾名思义，是系统对外的唯一入口。 `API` 网关封装了系统内部架构，为每个客户端提供定制的 `API` 。  
 近几年来移动应用与企业间互联需求的兴起，从以前单一的 WEB 应用扩展到多种使用场景，且每种使用场景对后台服务的要求都不尽相同，这不仅增加了后台服务的响应量，还增加了后台服务的复杂性。  
 随着微服务架构概念的提出，`API` 网关成为了微服务架构的一个标配组件。

 + 为什么要使用网关  
 微服务的应用可能部署在不同的机房，不同地区，不同域名下。此时客户端（浏览器/手机/软件工具）想要请求对应的服务，都需要知道具体的 `IP` 或者域名 `URL` ，当微服务实例众多时，这是非常难以记忆的，对于客户端来说也太复杂难以维护。  
 此时就有了网关，客户端相关的请求直接发送到网关，由网关根据请求标识解析判断出具体的微服务地址，再把请求转发到微服务实例。这其中的记忆功能就全部交由网关来操作了。

 + 核心概念  
 `路由（ Route ）`: 路由是网关最基础的部分，路由信息由 ID、目标 URL、一组断言和一组过滤器组成。如果路由断言为真，则说明请求的 URL 和配置匹配。  
 `断言（ Predicate `）: Java8 中的断言函数。`Spring Cloud Gateway` 中的断言函数输入类型是 `Spring5.0` 框架中的 `ServerWebExchange` ，`Spring Cloud Gateway` 中的断言函数允许开发者自定义匹配来自于 H`ttp Request` 中的任何信息，比如 请求头和参数等。  
 `过滤器（Filter）`: 一个标准的 `Spring Web Filter`。 `Spring Cloud Gateway` 中的 `Filter` 分为两种类型，分别是 `Gateway Filter` 和 `Global Filter`。过滤器将会对请求和响应进行处理。

## 使用网关
1. 添加依赖  

        <!-- spring cloud gateway 依赖 -->
        <dependency>
        	<groupId>org.springframework.cloud</groupId>
        	<artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>

2. `resources/application.yml` 配置文件  

        server:
          port: 8080

        spring: 
          application:
            name: gateway
          cloud:
            gateway:
              routes:
                # 系统模块
                - id: system
                  uri: http://localhost:9201/
                  predicates:
                    - Path=/system/**
                  filters:
                    - StripPrefix=1

3. 网关启动类  

        @SpringBootApplication
        public class GatewayApplication
        {
            public static void main(String[] args)
            {
                SpringApplication.run(GatewayApplication.class, args);
            }
        }

## 路由规则
`Spring Cloud Gateway` 创建 `Route` 对象时， 使用 `RoutePredicateFactory` 创建 `Predicate` 对象，Predicate 对象可以赋值给 `Route`。

`Spring Cloud Gateway` 包含许多内置的 `Route Predicate Factories`。
所有这些断言都匹配 `HTTP` 请求的不同属性。
多个 `Route Predicate Factories` 可以通过逻辑与（and）结合起来一起使用。
路由断言工厂 `RoutePredicateFactory` 包含的主要实现类如图所示，包括 `Datetime`、`请求的远端地址`、`路由权重`、`请求头`、`Host 地址`、`请求方法`、`请求路径`和`请求参数`等类型的路由断言。

### Datetime
匹配日期时间之后发生的请求

    spring: 
      application:
        name: gateway
      cloud:
        gateway:
          routes:
            - id: system
              uri: http://localhost:9201/
              predicates:
                - After=2021-02-23T14:20:00.000+08:00[Asia/Shanghai]

### Cookie
匹配指定名称且其值与正则表达式匹配的 `cookie`

    spring: 
      application:
        name: gateway
      cloud:
        gateway:
          routes:
            - id: system
              uri: http://localhost:9201/
              predicates:
                - Cookie=loginname

### Header
匹配具有指定名称的请求头，`\d+` 值匹配正则表达式

    spring: 
      application:
        name: gateway
      cloud:
        gateway:
          routes:
            - id: system
              uri: http://localhost:9201/
              predicates:
                - Header=X-Request-Id, \d+

### Host
匹配主机名的列表

    spring: 
      application:
        name: gateway
      cloud:
        gateway:
          routes:
            - id: system
              uri: http://localhost:9201/
              predicates:
                - Host=**.somehost.org,**.anotherhost.org

### Method
匹配请求 methods 的参数，它是一个或多个参数

    spring: 
      application:
        name: gateway
      cloud:
        gateway:
          routes:
            - id: system
              uri: http://localhost:9201/
              predicates:
                - Method=GET,POST

### Path
匹配请求路径

    spring: 
      application:
        name: gateway
      cloud:
        gateway:
          routes:
            - id: system
              uri: http://localhost:9201/
              predicates:
                - Path=/system/**

### Query
匹配查询参数

    spring: 
      application:
        name: gateway
      cloud:
        gateway:
          routes:
            - id: system
              uri: http://localhost:9201/
              predicates:
                - Query=username, abc.

### RemoteAddr
匹配 IP 地址和子网掩码

    spring: 
      application:
        name: gateway
      cloud:
        gateway:
          routes:
            - id: system
              uri: http://localhost:9201/
              predicates:
                - RemoteAddr=192.168.10.1/0

### Weight
匹配权重

    spring: 
      application:
        name: gateway
      cloud:
        gateway:
          routes:
            - id: system-a
              uri: http://localhost:9201/
              predicates:
                - Weight=group1, 8
            - id: system-b
              uri: http://localhost:9201/
              predicates:
                - Weight=group1, 2

## 路由配置
在 `Spring Cloud Gateway` 中配置 `uri` 有三种方式，包括

+ websocket 配置方式

        spring:
          cloud:
            gateway:
              routes:
                - id: api
                  uri: ws://localhost:9090/
                  predicates:
                    - Path=/api/**

+ http 地址配置方式

        spring:
          cloud:
            gateway:
              routes:
                - id: api
                  uri: http://localhost:9090/
                  predicates:
                    - Path=/api/**

+ 注册中心配置方式

        spring:
          cloud:
            gateway:
              routes:
                - id: api
                  uri: lb://api
                  predicates:
                    - Path=/api/**

## 限流配置
顾名思义，限流就是限制流量。通过限流，我们可以很好地控制系统的 QPS，从而达到保护系统的目的。
常见的限流算法有：`计数器算法`，`漏桶（Leaky Bucket）算法`，`令牌桶（Token Bucket）算法`。

`Spring Cloud Gateway` 官方提供了 `RequestRateLimiterGatewayFilterFactory` 过滤器工厂，使用 `Redis` 和 `Lua` 脚本实现了令牌桶的方式。

1. 添加依赖

        <!-- spring data redis reactive 依赖 -->
        <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
        </dependency>

2. 限流规则，根据 `URI` 限流

        spring:
          redis:
            host: localhost
            port: 6379
            password: 
          cloud:
            gateway:
              routes:
                # 系统模块
                - id: system
                  uri: lb://system
                  predicates:
                    - Path=/system/**
                  filters:
                    - StripPrefix=1
                    - name: RequestRateLimiter
                      args:
                        redis-rate-limiter.replenishRate: 1 # 令牌桶每秒填充速率
                        redis-rate-limiter.burstCapacity: 2 # 令牌桶总容量
                        key-resolver: "#{@pathKeyResolver}" # 使用 SpEL 表达式按名称引用 bean

> `StripPrefix=1` 配置，表示网关转发到业务模块时候会自动截取前缀。

3. 编写 `URI` 限流规则配置类

        package com.ruoyi.gateway.config;
        
        import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import reactor.core.publisher.Mono;
        
        /**
         * 限流规则配置类
         */
        @Configuration
        public class KeyResolverConfiguration
        {
            @Bean
            public KeyResolver pathKeyResolver()
            {
                return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
            }
        }

## 熔断降级
1. 添加依赖。

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>

2. 配置需要熔断降级服务

        spring:
          redis:
            host: localhost
            port: 6379
            password: 
          cloud:
            gateway:
              routes:
                # 系统模块
                - id: ruoyi-system
                  uri: lb://ruoyi-system
                  predicates:
                    - Path=/system/**
                  filters:
                    - StripPrefix=1
                    # 降级配置
                    - name: Hystrix
                      args:
                        name: default
                        # 降级接口的地址
                        fallbackUri: 'forward:/fallback'

> 上面配置包含了一个 `Hystrix` 过滤器，该过滤器会应用 `Hystrix` 熔断与降级，会将请求包装成名为 `fallback` 的路由指令 `RouteHystrixCommand`，`RouteHystrixCommand` 继承于 `HystrixObservableCommand`，其内包含了 `Hystrix` 的断路、资源隔离、降级等诸多断路器核心功能，当网关转发的请求出现问题时，网关能对其进行快速失败，执行特定的失败逻辑，保护网关安全。

> 配置中有一个可选参数 `fallbackUri`，当前只支持 `forward 模式`的 URI。如果服务被降级，请求会被转发到该 URI 对应的控制器。控制器可以是自定义的 `fallback 接口`，也可以使自定义的 `Handler`，需要实现接口 `org.springframework.web.reactive.function.server.HandlerFunction<T extends ServerResponse>`。

3. 实现添加熔断降级处理返回信息

        package com.ruoyi.gateway.handler;
        
        import com.alibaba.fastjson.JSON;
        import com.ruoyi.common.core.domain.R;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.MediaType;
        import org.springframework.stereotype.Component;
        import org.springframework.web.reactive.function.BodyInserters;
        import org.springframework.web.reactive.function.server.HandlerFunction;
        import org.springframework.web.reactive.function.server.ServerRequest;
        import org.springframework.web.reactive.function.server.ServerResponse;
        import reactor.core.publisher.Mono;
        import java.util.Optional;
        import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;
        
        /**
         * 熔断降级处理
         * 
         * @author ruoyi
         */
        @Component
        public class HystrixFallbackHandler implements HandlerFunction<ServerResponse>
        {
            private static final Logger log = LoggerFactory.getLogger(HystrixFallbackHandler.class);
        
            @Override
            public Mono<ServerResponse> handle(ServerRequest serverRequest)
            {
                Optional<Object> originalUris = serverRequest.attribute(GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
                originalUris.ifPresent(originalUri -> log.error("网关执行请求:{}失败,hystrix服务降级处理",originalUri));
                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(JSON.toJSONString(R.fail("服务已被降级熔断"))));
            }
        }

4. 路由配置信息加一个控制器方法用于处理重定向的 `/fallback` 请求

        package com.ruoyi.gateway.config;
        
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.http.MediaType;
        import org.springframework.web.reactive.function.server.RequestPredicates;
        import org.springframework.web.reactive.function.server.RouterFunction;
        import org.springframework.web.reactive.function.server.RouterFunctions;
        import com.ruoyi.gateway.handler.HystrixFallbackHandler;
        import com.ruoyi.gateway.handler.ValidateCodeHandler;
        
        /**
         * 路由配置信息
         * 
         * @author ruoyi
         */
        @Configuration
        public class RouterFunctionConfiguration
        {
            @Autowired
            private HystrixFallbackHandler hystrixFallbackHandler;
        
            @Autowired
            private ValidateCodeHandler validateCodeHandler;
        
            @SuppressWarnings("rawtypes")
            @Bean
            public RouterFunction routerFunction()
            {
                return RouterFunctions
                        .route(RequestPredicates.path("/fallback").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                                hystrixFallbackHandler)
                        .andRoute(RequestPredicates.GET("/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                                validateCodeHandler);
            }
        }

## 黑名单配置
顾名思义，就是不能访问的地址。实现自定义过滤器 `BlackListUrlFilter`，需要配置黑名单地址列表 `blacklistUrl`，当然有其他需求也可以实现自定义规则的过滤器。

    spring:
      cloud:
        gateway:
          routes:
            # 系统模块
            - id: ruoyi-system
              uri: lb://ruoyi-system
              predicates:
                - Path=/system/**
              filters:
                - StripPrefix=1
                - name: BlackListUrlFilter
                  args:
                    blacklistUrl:
                    - /user/list

## 白名单配置
顾名思义，就是允许访问的地址。`且无需登录就能访问`。
在 `ignore` 中设置 `whites` ，表示允许匿名访问。

    # 不校验白名单
    ignore:
      whites:
        - /auth/logout
        - /auth/login
        - /*/v2/api-docs
        - /csrf

## 全局过滤器
全局过滤器作用于所有的路由，不需要单独配置，我们可以用它来实现很多统一化处理的业务需求，比如`权限认证`，`IP 访问限制`等等。目前网关统一鉴权 `AuthFilter.java` 就是采用的全局过滤器。

单独定义只需要实现 `GlobalFilter`, `Ordered` 这两个接口就可以了。

    package com.ruoyi.gateway.filter;
    
    import org.springframework.cloud.gateway.filter.GatewayFilterChain;
    import org.springframework.cloud.gateway.filter.GlobalFilter;
    import org.springframework.core.Ordered;
    import org.springframework.core.io.buffer.DataBuffer;
    import org.springframework.http.server.reactive.ServerHttpResponse;
    import org.springframework.stereotype.Component;
    import org.springframework.web.server.ServerWebExchange;
    import reactor.core.publisher.Mono;
    
    /**
     * 全局过滤器
     * 
     * @author ruoyi
     */
    @Component
    public class AuthFilter implements GlobalFilter, Ordered
    {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
        {
            String token = exchange.getRequest().getQueryParams().getFirst("token");
            if (null == token)
            {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().add("Content-Type", "application/json; charset=utf-8");
                String message = "{\"message\":\"请求token信息不能为空\"}";
                DataBuffer buffer = response.bufferFactory().wrap(message.getBytes());
                return response.writeWith(Mono.just(buffer));
            }
            return chain.filter(exchange);
        }
    
        @Override
        public int getOrder()
        {
            return 0;
        }
    }

## 实现 `Sentinel` 限流
`Sentinel` 支持对 `Spring Cloud Gateway`、`Netflix Zuul` 等主流的 `API Gateway` 进行限流。
1. 添加依赖

        <!-- SpringCloud Alibaba Sentinel -->
        <dependency>
        	<groupId>com.alibaba.cloud</groupId>
        	<artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>
        		
        <!-- SpringCloud Alibaba Sentinel Gateway -->
        <dependency>
        	<groupId>com.alibaba.cloud</groupId>
        	<artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
        </dependency>

2. 限流规则配置类

        package com.ruoyi.gateway.config;
        
        import java.util.HashSet;
        import java.util.Set;
        import javax.annotation.PostConstruct;
        import org.springframework.cloud.gateway.filter.GlobalFilter;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.core.Ordered;
        import org.springframework.core.annotation.Order;
        import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
        import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
        import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
        import com.ruoyi.gateway.handler.SentinelFallbackHandler;
        
        /**
         * 网关限流配置
         * 
         * @author ruoyi
         */
        @Configuration
        public class GatewayConfig
        {
            @Bean
            @Order(Ordered.HIGHEST_PRECEDENCE)
            public SentinelFallbackHandler sentinelGatewayExceptionHandler()
            {
                return new SentinelFallbackHandler();
            }
        
            @Bean
            @Order(-1)
            public GlobalFilter sentinelGatewayFilter()
            {
                return new SentinelGatewayFilter();
            }
        
            @PostConstruct
            public void doInit()
            {
                // 加载网关限流规则
                initGatewayRules();
            }
        
            /**
             * 网关限流规则   
             */
            private void initGatewayRules()
            {
                Set<GatewayFlowRule> rules = new HashSet<>();
                rules.add(new GatewayFlowRule("ruoyi-system")
                        .setCount(3) // 限流阈值
                        .setIntervalSec(60)); // 统计时间窗口，单位是秒，默认是 1 秒
                // 加载网关限流规则
                GatewayRuleManager.loadRules(rules);
            }
        }

3. 测试验证，一分钟内访问三次系统服务出现异常提示表示限流成功。

## Setinel 分组限流
对 `ruoyi-system`、`ruoyi-gen` 分组限流配置  
1. `application.yml` 配置文件

        spring:
          cloud:
            gateway:
              routes:
                # 系统模块
                - id: ruoyi-system
                  uri: lb://ruoyi-system
                  predicates:
                    - Path=/system/**
                  filters:
                    - StripPrefix=1
                # 代码生成
                - id: ruoyi-gen
                  uri: lb://ruoyi-gen
                  predicates:
                    - Path=/code/**
                  filters:
                    - StripPrefix=1

2. 限流规则配置类

        package com.ruoyi.gateway;
        
        import java.util.HashSet;
        import java.util.Set;
        import javax.annotation.PostConstruct;
        import org.springframework.cloud.gateway.filter.GlobalFilter;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.core.Ordered;
        import org.springframework.core.annotation.Order;
        import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
        import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
        import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
        import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
        import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
        import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
        import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
        import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
        import com.ruoyi.gateway.handler.SentinelFallbackHandler;
        
        /**
         * 网关限流配置
         * 
         * @author ruoyi
         */
        @Configuration
        public class GatewayConfig
        {
            @Bean
            @Order(Ordered.HIGHEST_PRECEDENCE)
            public SentinelFallbackHandler sentinelGatewayExceptionHandler()
            {
                return new SentinelFallbackHandler();
            }
        
            @Bean
            @Order(-1)
            public GlobalFilter sentinelGatewayFilter()
            {
                return new SentinelGatewayFilter();
            }
        
            @PostConstruct
            public void doInit()
            {
                // 加载网关限流规则
                initGatewayRules();
            }
        
            /**
             * 网关限流规则   
             */
            private void initGatewayRules()
            {
                Set<GatewayFlowRule> rules = new HashSet<>();
                rules.add(new GatewayFlowRule("system-api")
                        .setCount(3) // 限流阈值
                        .setIntervalSec(60)); // 统计时间窗口，单位是秒，默认是 1 秒
                rules.add(new GatewayFlowRule("code-api")
                        .setCount(5) // 限流阈值
                        .setIntervalSec(60));
                // 加载网关限流规则
                GatewayRuleManager.loadRules(rules);
                // 加载限流分组
                initCustomizedApis();
            }
        
            /**
             * 限流分组   
             */
            private void initCustomizedApis()
            {
                Set<ApiDefinition> definitions = new HashSet<>();
                // ruoyi-system 组
                ApiDefinition api1 = new ApiDefinition("system-api").setPredicateItems(new HashSet<ApiPredicateItem>()
                {
                    private static final long serialVersionUID = 1L;
                    {
                        // 匹配 /user 以及其子路径的所有请求
                        add(new ApiPathPredicateItem().setPattern("/system/user/**")
                                .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                    }
                });
                // ruoyi-gen 组
                ApiDefinition api2 = new ApiDefinition("code-api").setPredicateItems(new HashSet<ApiPredicateItem>()
                {
                    private static final long serialVersionUID = 1L;
                    {
                        // 只匹配 /job/list
                        add(new ApiPathPredicateItem().setPattern("/code/gen/list"));
                    }
                });
                definitions.add(api1);
                definitions.add(api2);
                // 加载限流分组
                GatewayApiDefinitionManager.loadApiDefinitions(definitions);
            }
        }

访问：`http://localhost:8080/system/user/list （触发限流 ）`  
访问：`http://localhost:8080/system/role/list （不会触发限流）`  
访问：`http://localhost:8080/code/gen/list （触发限流）`  
访问：`http://localhost:8080/code/gen/xxxx （不会触发限流）`  

## Setinel 自定义异常
为了展示更加友好的限流提示， `Sentinel` 支持自定义异常处理。  
方案一：`yml` 配置

    # Spring
    spring: 
      cloud:
        sentinel:
          scg:
            fallback:
              mode: response
              response-body: '{"code":403,"msg":"请求超过最大数，请稍后再试"}'

方案二：`GatewayConfig` 注入 `Bean`

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelFallbackHandler sentinelGatewayExceptionHandler()
    {
    	return new SentinelFallbackHandler();
    }

SentinelFallbackHandler.java

    package com.ruoyi.gateway.handler;
    
    import java.nio.charset.StandardCharsets;
    import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
    import com.alibaba.csp.sentinel.slots.block.BlockException;
    import org.springframework.core.io.buffer.DataBuffer;
    import org.springframework.http.server.reactive.ServerHttpResponse;
    import org.springframework.web.reactive.function.server.ServerResponse;
    import org.springframework.web.server.ServerWebExchange;
    import org.springframework.web.server.WebExceptionHandler;
    import reactor.core.publisher.Mono;
    
    /**
     * 自定义限流异常处理
     *
     * @author ruoyi
     */
    public class SentinelFallbackHandler implements WebExceptionHandler
    {
        private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange)
        {
            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            byte[] datas = "{\"code\":429,\"msg\":\"请求超过最大数，请稍后再试\"}".getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(datas);
            return serverHttpResponse.writeWith(Mono.just(buffer));
        }
    
        @Override
        public Mono<Void> handle(ServerWebExchange exchange, Throwable ex)
        {
            if (exchange.getResponse().isCommitted())
            {
                return Mono.error(ex);
            }
            if (!BlockException.isBlockException(ex))
            {
                return Mono.error(ex);
            }
            return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange));
        }
    
        private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable)
        {
            return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
        }
    }