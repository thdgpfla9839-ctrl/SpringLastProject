# SpringLastProject

스프링MVC 구조를 이용해 메인 페이지에는 상품목록과 쿠키기반 최근 본상품 목록, 인기음식 상위 7개 노출 / 답글형 게시판 / 회원가입 / 로그인 기능을 구현하고, 원래 음식(맛집) 데이터 중심이던 걸 상품(Goods) 데이터로 직접 변경해본 프로젝트를 GitHub Actions로 Tomcat에 자동 배포함.

## 💻기술 스택

- Spring MVC 5.1.5 (core / webmvc / jdbc / orm / aop / aspects)
- MyBatis 3.2.8, Oracle DB (ojdbc8)
- Apache Commons DBCP (커넥션 풀)
- Spring Security 5.1.5
- JSP, JSTL
- AOP (FooterCommonsAspect로 공통 로직 분리)
- log4j
- Lombok

## 구조

```
com.sist
├── web         # Controller (Board, Member, Goods, Food, Main)
├── service     # Service 인터페이스 / 구현체
├── mapper      # MyBatis Mapper
├── vo          # VO (Board, Member, Goods, Authority, Food)
├── security    # 로그인 성공/실패 핸들러
├── aop         # AOP
├── interceptor # 인터셉터
└── commons     # 공통 예외 처리
```

Controller → Service(Impl) → Mapper(MyBatis) → Oracle DB

## 🚀배포

### 1) 수동 배포 (Tomcat)


# 로컬에서 WAR export 후 WinSCP로 우분투 서버에 업로드
sudo mv SpringLastProject.war ./apache-tomcat-9.0.120/webapps/
cd apache-tomcat-9.0.120/
sudo ./bin/shutdown.sh
sudo ./bin/startup.sh
```

### 2) GitHub Actions 자동 배포

master 브랜치에 push하면 self-hosted runner(우분투 서버)에서 자동으로:

1. Checkout
2. `mvn clean package -DskipTests` 로 WAR 빌드
3. 기존 Tomcat 종료
4. 기존 WAR / 배포 폴더 삭제
5. 새 WAR를 webapps에 복사
6. Tomcat 재시작

WAR를 Tomcat이 풀어서 서비스하는 방식이라, push 한 번으로 빌드~배포까지 자동으로 됨.

## ⚙️실행 환경

- JDK 11
- Tomcat 9
- Oracle DB

## 🔥트러블슈팅

### Maven Build 실패: POM을 찾을 수 없음

```
[ERROR] The goal you specified requires a project to execute but there is no POM in this directory
(/home/sist/actions-runner/_work/SpringLastProject/SpringLastProject)
```

**원인**: 소스를 `main` 브랜치에 push했는데, `deploy.yml`은 `master` 브랜치 push에만 반응하도록 되어있어서 워크플로우가 원하는 위치에 프로젝트 파일이 없는 상태로 빌드가 진행됨.

**해결**: 소스를 `main`에서 `master` 브랜치로 옮김. 이후 정상적으로 `mvn clean package` 빌드 성공.
