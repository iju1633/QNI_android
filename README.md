# QNI (2023, Pipecoding Club Assignment)


![Kotlin](https://img.shields.io/badge/Kotlin-007396?style=flat-square&logo=Kotlin&logoColor=white)
![Android_Studio](https://img.shields.io/badge/Android_Studio-FFCC33?style=flat-square&logo=Androidstudio&logoColor=white)
![Spring-Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=Spring-Boot&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-FFCC00?style=flat-square&logo=Swagger&logoColor=white)
![RDS](https://img.shields.io/badge/Amazon_RDS-4285F4?style=flat-square&logo=Amazon-RDS&logoColor=white)
![EC2](https://img.shields.io/badge/Amazon_EC2-4285F4?style=flat-square&logo=Amazon-EC2&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-4285F4?style=flat-square&logo=Docker&logoColor=white)

This application asks you daily questions to help you understand yourself.

## 📖 Explanation

Many people cannot easily answer the question of who they are.

Accordingly, I thought it would be nice if there was a service that asked questions that could help me get to know myself.  
In addition, the accumulated data can be used as a tool to share with loved ones in the future.

## 🖥️ UI Design
[Figma Link](https://www.figma.com/file/ZY0M8jz6apBFTpYiXe5H4s/%ED%8C%8C%EC%9D%B4%ED%94%84-%EC%BD%94%EB%94%A9-%ED%81%B4%EB%9F%BD_%EA%B3%BC%EC%A0%9C?node-id=0%3A1&t=4G5qNN3cJOKI6Jc5-1)

## 💎 Main Features

- HTTP REST API Server configured With Spring Boot Application.
- Deploying using docker and dockerhub.
- DB management using Amazon RDS.


## 📐 Deployment/Diagram
![image](https://user-images.githubusercontent.com/43805087/213961889-1777c329-1e26-4774-9b74-c726983a649a.png)

- Build with Gradle.
- Build Jar file, Make Docker Image and deploy it using docker in Amazon EC2 instance.
- Use Amazno RDS to manage DB easily.

## 🖥️ Build Environment
This project uses Gradle, Amazon EC2, and Docker.
To build and run this project, first build `.jar` with Gradle, and build docker image and push it to dockerhub.  
After that, pull the image in Amazon EC2 and deploy it with docker.

## 📃 API Specification

[Swagger Link](http://3.35.39.43/swagger-ui/#/)  
This project utilize swagger Specification 2.0 and Swagger UI for communication with client.  
![api 명세서](https://user-images.githubusercontent.com/43805087/211522965-e428082d-c009-4834-9300-d6e5f6e8e494.png)

## 🏛️ Depedency Used
- Server
  - implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
  - implementation 'org.springframework.boot:spring-boot-starter-security'
  - implementation 'org.springframework.boot:spring-boot-starter-validation'
  - implementation 'org.springframework.boot:spring-boot-starter-web'
  - implementation 'io.springfox:springfox-boot-starter:3.0.0'
- Android
  - implementation("io.coil-kt:coil:2.2.2")
  - implementation "com.squareup.okhttp3:okhttp:3.4.2"
  - implementation 'com.squareup.okhttp3:logging-interceptor:3.4.2'
  - implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2'
  - implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2'
  - implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
  - implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
  - implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
  - implementation "androidx.lifecycle:lifecycle-service:2.3.1"
  - implementation "androidx.activity:activity-ktx:1.2.2"
  - implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
  - implementation 'com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2'
  - implementation 'com.squareup.retrofit2:retrofit:2.9.0'
  - implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
  - implementation 'com.google.code.gson:gson:2.8.9'
  - implementation "com.google.dagger:hilt-android:2.44"
  - kapt "com.google.dagger:hilt-android-compiler:2.44"
  - implementation "androidx.hilt:hilt-work:1.0.0"
  - kapt "com.google.dagger:hilt-compiler:2.44"
  - implementation "androidx.navigation:navigation-compose:2.5.3"
  - implementation 'androidx.work:work-runtime-ktx:2.7.1'
  
#### Contributor

[Jaeuk Im](https://github.com/iju1633)
|:---:|
|BACKEND & ANDROID|
