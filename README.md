# TheCatApp 🐱

Aplicación Kotlin Multiplatform (Android + Desktop) para explorar razas de gatos usando [TheCatAPI](https://thecatapi.com/). Soporta almacenamiento local con SQLDelight, inyección de dependencias con Koin y UI con Compose Multiplatform.

---

## 🔑 Configuración de TheCatAPI

1. Crea o edita el archivo `local.properties`:
   ```properties
   the_cat_api_key=TU_API_KEY
   
---

## 🔐 Requisitos de acceso

Para iniciar sesión en TheCatApp, los usuarios deben proporcionar credenciales válidas que cumplan con las siguientes condiciones:

---

### 📧 Correo electrónico

Debe ser un correo válido, por ejemplo:

- `usuario@gmail.com`
- `nombreapellido@gmail.com.mx`

Se valida que tenga formato de email correcto (con `@` y dominio).

---

### 🔑 Contraseña segura

La contraseña debe cumplir todas las siguientes reglas:

- Mínimo **8 caracteres**
- Al menos **una letra mayúscula**
- Al menos **una letra minúscula**
- Al menos **un número**
- Al menos **un símbolo especial** (por ejemplo: `!`, `@`, `#`, `$`, etc.)

#### ✅ Ejemplos válidos:

- `Password1!`
- `M1clave#`
- `Aa1@aaaa`

#### ❌ Ejemplos inválidos:

| Contraseña     | Motivo del rechazo                |
|----------------|-----------------------------------|
| `password1!`   | Falta mayúscula                   |
| `PASSWORD1!`   | Falta minúscula                   |
| `Password!`    | Falta número                      |
| `Password1`    | Falta símbolo especial            |
| `Pass1!`       | Menos de 8 caracteres             |

> 🧠 Consejo: usa una contraseña que combines palabras y números con algún símbolo para mantener tu cuenta segura.

---
## 📥 Descargas

| Plataforma      | Enlace de descarga                                                                                             |
|-----------------|----------------------------------------------------------------------------------------------------------------|
| Android (APK)   | [TheCatApp v1.0.0 - APK](https://github.com/luismikg/TheApiCat/releases/download/executablesV1/TheCatApp.apk)  |
| macOS (DMG)     | [TheCatApp v1.0.0 - DMG](https://github.com/luismikg/TheApiCat/releases/download/executablesV1/mac.zip)        |

---

## 🖥 Cómo ejecutar TheCatApp en macOS

Después de descargar e instalar el archivo `.dmg` desde la [sección de descargas](https://github.com/luismikg/TheApiCat/releases/download/v1.0.0/TheCatApp.app), puedes ejecutar la aplicación de dos formas:

---

### 🔧 1 Visual 

1. En finder ir a Applicatiopns
2. Ubicar la app "TheCatApp"
3. Click derecho, seleccionar "Mostrar contenido del paquete"
4. Navegar a "Content/MacOS/"
5. Doble click en el archivo exec "TheCatApp"

### 🔧 2 Opción avanzada — Ejecutar desde terminal con logs visibles

1. En terminal ejecutar
   ```bash
   open /Applications/TheCatApp.app/Contents/MacOS/TheCatApp

---

## 🏗 Arquitectura

- **Patrón:** MVVM
- **Capas:**
  - `ui`: Compose Multiplatform para Android y Desktop
  - `domain`: interfaces y modelos
  - `data`:
    - `remote`: Ktor + ApiService
    - `database`: SQLDelight
    - `paging`: Compose Paging
- **di**: Koin (`UIModule`, `DataModule`, `DomainModule`, `CoreDataBaseModule`)

---

## ⚙️ Tecnologías clave

- Kotlin Multiplatform + Compose
- Koin para DI
- Ktor (HTTP + JSON)
- SQLDelight
- Paging + Coil
- BuildKonfig

---

## 📁 Estructura del proyecto
```txt
/thecatapp 
├── androidApp/                    # launcher Android 
├── composeApp/                    # módulo KMP (UI, lógica común y desktop) 
│   ├── src/commonMain/            # código común (VMs, dominio, UI común) 
│   ├── src/androidMain/           # Android específico 
│   ├── src/jvmMain/               # Desktop (JVM) específico 
│   └── build.gradle.kts 
├── icon/                          # íconos desktop 
│   ├── windows_icon.ico 
│   ├── mac_icon.icns 
│   └── linux_icon.png 
├── local.properties               # the_cat_api_key=...
└── build.gradle.kts               # configuración raíz
```
