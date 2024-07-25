# Project_Setting
프로젝트 기초 셋팅
Rest api를 만들기 전 프로젝트 전반적인 스프링 시큐리티, JWT 토큰 처리 과정, 공통 에러 처리, 스웨거 적용에 대한 코드를 볼 수 있는 프로젝트 파일이다.

## 목차
1. [환경설정](#환경설정)


## 환경설정 
Category | Detail
---- | ----
JDK | Amazon corretto 17 [JDK DOWNLOAD](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)
Language | Kotlin jvm version 1.9.23
Framwork | SpringBoot 3.1.7 , SpringSecurity
Library | Mybatis 3.0.1 , jjwt 0.11.5
Database | Mysql
IDE | IntelliJ Ultimate
Config Manager | Github
Swagger | swagger-doc

 

## 스프링 시큐리티 & JWT

스프링 시큐리티 권한 설정과 JWT 토큰을 활용한 토큰 검사 로직 
JWT 토큰 유효성 검사 및 오류 처리 로직 확인 가능

## 공통 오류 처리

ExceptionHandler를 활용하여 오류를 공통적으로 처리함으로 프로젝트 오류처리가 단순화됨
ErrorCode 파일에서 코드를 한번에 볼 수 있다.

## 스웨거

기존에는 springfox 를 사용했었는데 최근까지 업데이트가 이루어지고 있는 springDoc으로 변경하였음
또한 클라이언트와 원활한 소통을 위해 다양한 주석을 처리함



