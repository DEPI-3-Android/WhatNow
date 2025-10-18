# WhatNow - News App

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Firebase](https://img.shields.io/badge/firebase-%23039BE5.svg?style=for-the-badge&logo=firebase)
![Retrofit](https://img.shields.io/badge/retrofit-%23FF6F00.svg?style=for-the-badge&logo=square&logoColor=white)

## 📱 Overview

**WhatNow** is a modern Android news application that allows users to stay updated with the latest news from around the world. The app provides a seamless experience for browsing news articles across different categories and regions, with features like favorites, sharing, and multilingual support.

## ✨ Features

### Authentication
- **Email/Password Registration & Login** with Firebase Authentication
- **Google Sign-In** integration
- **Email verification** for new accounts
- **Password reset** functionality

### News Features
- **Multiple News Categories**: Sports, Health, Technology, Science, Business, Entertainment, General
- **Regional News**: Support for multiple countries (USA, Germany, Spain, Italy, France, England, Russia, Worldwide)
- **Real-time News**: Fetches latest headlines using GNews API
- **Article Artwork**: High-quality images with fallback support
- **Web View**: Direct access to full articles

### Personalization
- **Favorites System**: Save articles to read later using Firebase Firestore
- **Dark/Light Theme**: System default, light, or dark mode options
- **Multilingual Support**: English and Arabic language support
- **Regional Customization**: Choose news region preferences

### Additional Features
- **Article Sharing**: Share articles via native Android sharing
- **Responsive UI**: Material Design with smooth animations
- **Offline Support**: Cached favorites and settings
- **Splash Screen**: Professional app launch experience

## 🛠️ Technical Stack

### Core Technologies
- **Language**: Kotlin
- **Platform**: Android (API 26+)
- **Architecture**: MVVM with View Binding
- **Build System**: Gradle with Kotlin DSL

### Key Libraries
- **Firebase Suite**:
  - Authentication
  - Firestore Database
- **Networking**: 
  - Retrofit 2.9.0
  - OkHttp 4.9.3
- **Image Loading**: Glide 4.15.1
- **UI Components**: Material Design Components
- **Architecture**: AndroidX Lifecycle & ViewModel

### API Integration
- **GNews API**: For fetching news articles and headlines
- **Firebase APIs**: Authentication and cloud database

## 📋 Prerequisites

Before running the project, ensure you have:

- **Android Studio** Arctic Fox or later
- **JDK 11** or higher
- **Android SDK** API 26+
- **Firebase Project** with Authentication and Firestore enabled
- **GNews API Key** (free tier available)

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/DEPI-3-Android/WhatNow.git
cd WhatNow
```

### 2. Firebase Setup
1. Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
2. Enable **Authentication** with Email/Password and Google providers
3. Enable **Firestore Database**
4. Download `google-services.json` and place it in the `app/` directory
5. Configure Google Sign-In in Firebase Console

### 3. API Keys Configuration
Create a `local.properties` file in the project root with:
```properties
NEWS_API_KEY=your_gnews_api_key_here
DEFAULT_WEB_CLIENT_ID=your_google_web_client_id_here
```

### 4. Build and Run
1. Open the project in Android Studio
2. Sync the project with Gradle files
3. Build and run on your device or emulator

## 📁 Project Structure

```
WhatNow/
├── app/
│   ├── src/main/java/com/acms/whatnow/
│   │   ├── api/                    # API service interfaces
│   │   │   ├── ApiClient.kt
│   │   │   └── NewsApiService.kt
│   │   ├── models/                 # Data models
│   │   │   └── NewsResponse.kt
│   │   ├── CategoryActivity.kt     # News categories selection
│   │   ├── FavoriteActivity.kt     # Saved articles
│   │   ├── LoginActivity.kt        # User authentication
│   │   ├── MainActivity.kt         # Main entry point
│   │   ├── NewsActivity.kt         # News articles display
│   │   ├── NewsAdapter.kt          # RecyclerView adapter
│   │   ├── SettingsActivity.kt     # App settings
│   │   ├── SignUpActivity.kt       # User registration
│   │   ├── SplashActivity.kt       # App splash screen
│   │   └── WebViewActivity.kt      # Article web view
│   ├── src/main/res/               # Resources
│   │   ├── layout/                 # UI layouts
│   │   ├── values/                 # Strings, colors, styles
│   │   └── drawable/               # Icons and images
│   └── build.gradle.kts            # App-level dependencies
├── build.gradle.kts                # Project-level configuration
└── gradle/libs.versions.toml       # Version catalog
```

## 🎨 UI/UX Features

- **Material Design 3** components and theming
- **Edge-to-edge** display with proper insets handling
- **Responsive layouts** for different screen sizes
- **Smooth animations** and transitions
- **Accessibility support** with proper content descriptions
- **RTL support** for Arabic language

## 🌐 Internationalization

The app supports multiple languages:
- **English** (default)
- **Arabic** (العربية)

Language switching is available in the settings menu and persists across app sessions.

## 📱 Screenshots


## 🔧 Configuration

### News API Setup
1. Register at [GNews API](https://gnews.io/)
2. Get your API key
3. Add it to `local.properties` as `NEWS_API_KEY`

### Firebase Configuration
1. Set up Firebase project
2. Enable required services
3. Download and configure `google-services.json`
4. Set up OAuth for Google Sign-In

## 🙏 Acknowledgments

- GNews API for providing news data
- Firebase for backend services
- Material Design for UI components
- Android community for excellent documentation