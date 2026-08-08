# SpringLastProject
<aside>
💡

**스프링MVC** 구조를 이용해 메인 페이지에는 상품목록과 쿠키기반 최근 본상품 목록, 인기음식 상위 7개 노출 / 답글형 게시판 / 회원가입 / 로그인 기능을 구현하고, **원래 음식(맛집)** 데이터 중심이던 걸 **상품**(Goods) 데이터로 직접 변경해본 프로젝트를 **GitHub Actions로 Tomcat에 자동 배포**함

</aside>

# 1. 소스 구성

---

<aside>

- 기술 스택:
    
    Spring MVC 5.1.5(core/webmvc/jdbc/orm/aop/aspects), 
    
    MyBatis 3.2.8,
    
    Spring Security 5.1.5, 
    
    Oracle(ojdbc8),
    
    commons-dbcp(커넥션 풀), 
    
    Lombok, 
    
    JSP/JSTL, 
    
    AOP(FooterCommonsAspect로 공통 기능 분리), 
    
    log4j(로깅)
    
- 빌드 도구: Maven (pom.xml)
- 주요 기능: Board(게시판), Member(회원), Goods(상품), Food(음식) 도메인별 Controller/Service/Mapper 구조
- 구조: Controller → Service(Impl) → Mapper(MyBatis) → Oracle DB
- 인증: Spring Security 기반 로그인 성공/실패 핸들러
</aside>

# 2. 배포 방식 (Tomcat 수동 → GitHub Actions 자동)

---

### 1) 수동 배포

```bash
// 로컬에서 war로 export
// WinSCP로 우분투 서버에 war 업로드
sudo mv SpringLastProject.war ./apache-tomcat-9.0.120/webapps/
cd apache-tomcat-9.0.120/
sudo ./bin/shutdown.sh
sudo ./bin/startup.sh
```

### 2) GitHub Actions 자동 배포 (.github/workflows/deploy.yml)

<aside>

- master 브랜치에 push하면 self-hosted runner(우분투 서버)에서 자동으로 배포
- 순서:
    1. Checkout
    2. mvn clean package -DskipTests 로 war 빌드
    3. 기존 Tomcat 종료 (shutdown.sh)
    4. 기존 war/배포 폴더 삭제
    5. 새 war를 webapps에 복사
    6. Tomcat 재시작 (startup.sh)
</aside>

---

<aside>
📌

WAR 파일 배포 방식이라 Tomcat이 war 압축을 풀어서 서비스하는 구조. 

push 한 번으로 빌드부터 배포까지 자동화됨

</aside>

# 3. 트러블슈팅

---

### Maven Build 실패: POM을 찾을 수 없음

<aside>
💡

!image.png

</aside>

<aside>
⚠️

원인: 소스를 main 브랜치에 push했는데, deploy.yml은 master 브랜치 push에만 반응하도록 되어있어서 워크플로우가 원하는 위치에 프로젝트 파일이 없는 상태로 빌드가 진행됨

해결: 소스를 main에서 master 브랜치로 옴김. 이후 정상적으로 mvn clean package 빌드 성공.

</aside>
