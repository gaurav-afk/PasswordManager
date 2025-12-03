# 🔐 PasswordManager

## Overview
Password Saver is a secure and modern Android application built using **Jetpack Compose**, **MVVM + Clean Architecture**, and **Room Database**.  
It allows users to safely store, view, update, and delete their account passwords in a clean and intuitive UI.

The app focuses heavily on **local security**, **smooth animations**, and **modern Android development practices**.

## Demo
https://github.com/user-attachments/assets/cf16a801-97a1-445b-9858-19aa24109aa5



## Features
- **Secure Local Storage** using RoomDB  
- **Add / Edit / Delete Accounts**  
- **View Password with Toggle Visibility (Show/Hide)**  
- **Modern UI using Jetpack Compose**  
- **Search Accounts Instantly**  
- **MVVM + Clean Architecture** for maintainability  
- **Fully Offline App** – no data leaves the device  
- **Field Validations & Error States**  
- **Beautiful Card-based Dashboard**  

## Requirements
- Android SDK 30+  
- No internet required  
- Works on both emulator and real device

## Libraries & Tools
- **Jetpack Compose**  
- **RoomDB**  
- **Coroutines + Flow**  
- **Hilt** (Dependency Injection)  
- **Material3**  
- **ViewModel**  
- **Coil** (if icons/images are used)

## Architecture
The app follows **Clean Architecture + MVVM**:

## Compose APIs Used
- `LaunchedEffect`  
- `remember` & `mutableStateOf`  
- `Scaffold`, `Card`, `OutlinedTextField`, `LazyColumn`  
- `Dialog` for account delete confirmation  
- `rememberSaveable`  
- `AnimatedVisibility`  
- Material3 components

## Getting Started

### Clone the repository
```bash
git clone https://github.com/gaurav-afk/PasswordSaver.git
cd PasswordSaver
Build & Run
Open the project in Android Studio Arctic Fox or newer

Sync Gradle

Run on emulator or device

Screenshots
(Add your screenshots here)

Future Enhancements
Encrypted Room Database

Biometric Authentication (Fingerprint/FaceID)

Backup & Restore (Encrypted file)

Generate Strong Passwords

Cloud Sync (Optional)

License
This project is licensed under the MIT License.

