server:
  port: 80

spring:
  datasource:
    driver-class-name: ${(dbType == 'mysql')?string('com.mysql.cj.jdbc.Driver', 'org.postgresql.Driver')}
    url: ${url}
    username: ${username}
    password: ${password}
  
  # JSON 日期格式设置
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8

mybatis-plus:
  configuration:
    # 打印 SQL 日志
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

<#if config.enableKnife4j>

# Knife4j 配置
knife4j:
  enable: true
  setting:
    language: zh-CN
</#if> 