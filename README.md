# LogE (Log.e)

**LogE**는 사용자의 일상과 감정을 기록하고, 이를 통계로 시각화하여 보여주는 안드로이드 애플리케이션입니다.
최신 안드로이드 기술 스택인 Jetpack Compose와 Clean Architecture를 기반으로 개발되었습니다.

## 📌 프로젝트 소개 (Project Overview)
LogE는 사용자가 하루의 감정을 기록하고(Log Emotion), 이를 통해 자신의 감정 흐름을 파악할 수 있도록 돕습니다.
직관적인 UI와 다양한 차트, 그리고 홈 화면 위젯을 통해 편리한 사용자 경험을 제공합니다.

## 🛠 기술 스택 (Tech Stack)

### Languages & Frameworks
- **Kotlin** (2.3.0)
- **Jetpack Compose** (Material3) - 최신 UI 툴킷
- **Coroutines & Flow** - 비동기 처리

### Architecture
- **MVVM** (Model-View-ViewModel) 패턴
- **Clean Architecture** (Presentation, Domain, Data Layer 분리)
- **Hilt** - 의존성 주입 (Dependency Injection)

### Libraries
- **Network**: Retrofit2, OkHttp3
- **Local Database**: Room (데이터 영속성)
- **Charts**: Vico (감정 통계 시각화)
- **Widgets**: Jetpack Glance (홈 화면 위젯)
- **Navigation**: Navigation Compose
- **Serialization**: Kotlinx Serialization
- **Logging**: Timber
- **Testing**: JUnit4, Espresso, Mockk

## ✨ 주요 기능 (Key Features)
- **홈 (Home)**: 기록된 감정 로그를 한눈에 확인하고 타임라인 형태로 조회
- **작성 (Write)**: 그날의 감정과 메모를 쉽고 빠르게 기록
- **상세 (Detail)**: 기록한 내용의 상세 조회 및 수정/삭제
- **통계 (Stat)**: 주간/월간 감정 분포 및 추이를 아름다운 차트로 확인
- **설정 (Setting)**: 앱 테마, 알림 설정 및 데이터 관리
- **위젯 (Widget)**: 앱을 실행하지 않고도 홈 화면에서 바로 기록 확인


## 📂 패키지 구조 (Package Structure)
```
com.devhjs.loge
├── data             # Data Layer (Repository impl, DataSource, DTO, Room, API)
├── domain           # Domain Layer (UseCase, Repository Interface, Model)
├── presentation     # Presentation Layer (ViewModel, Screen, Custom View)
│   ├── component    # 공통 UI 컴포넌트
│   ├── home         # 홈 화면
│   ├── write        # 작성 화면
│   ├── detail       # 상세 화면
│   ├── stat         # 통계 화면
│   └── setting      # 설정 화면
└── di               # Hilt Modules
```
