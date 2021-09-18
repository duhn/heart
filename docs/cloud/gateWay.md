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

## 路由规则

## 路由配置

## 限流配置

## 熔断降级

## 黑名单配置

## 白名单配置

## 全局过滤器

## 实现 Sentinel 限流

## Setinel 分组限流

## Setinel 自定义异常