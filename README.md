# 戒烟助手 App - 安卓项目

## 构建 APK 步骤

### 方法一：用 Android Studio 构建（推荐）

1. 安装 **Android Studio**（如已安装可跳过）
   - 下载：https://developer.android.com/studio
   - 安装时选择「Standard」安装，会自动下载 SDK

2. 打开项目
   - 启动 Android Studio
   - 选择「Open an existing project」
   - 选择本文件夹（`QuitSmokeApp`）
   - 等待 Gradle 同步完成（首次可能需要 5-10 分钟）

3. 构建 APK
   - 菜单栏 → **Build** → **Generate Signed Bundle / APK**
   - 选择 **APK** → 下一步
   - 创建新的密钥库（Key Store）：
     - Key store path：选择一个位置保存 `.jks` 文件
     - Password：设置密钥密码（记住它！）
     - Key：Alias 填 `quitsmoke`，密码同上
     - 有效期：25年
   - 选择 **release** → 完成
   - APK 生成在：`app/release/app-release.apk`

4. 安装到手机
   - 把 APK 文件传到安卓手机
   - 在手机上允许「安装未知来源应用」
   - 点击 APK 文件安装

---

### 方法二：直接用 HTML（无需构建）

App 的核心是一个 HTML 文件，可以直接在手机浏览器里使用：

1. 把 `app/src/main/assets/www/index.html` 传到手机
2. 用浏览器打开即可使用
3. 在 Chrome 里可以「添加到主屏幕」，像原生 App 一样使用

---

## 项目结构

```
QuitSmokeApp/
├── app/
│   ├── build.gradle          # 模块构建配置
│   ├── proguard-rules.pro   # 混淆规则（空）
│   └── src/main/
│       ├── AndroidManifest.xml  # 应用清单
│       ├── java/com/quitsmoke/app/
│       │   └── MainActivity.java  # 主 Activity
│       ├── res/
│       │   ├── mipmap-hdpi/    # 应用图标
│       │   └── values/strings.xml
│       └── assets/www/
│           └── index.html        # App 核心页面（HTML+CSS+JS）
├── build.gradle              # 项目构建配置
├── settings.gradle           # 项目设置
└── gradle/wrapper/         # Gradle Wrapper
```

## 功能说明

- 🚬 记录每日吸烟情况
- 📊 查看吸烟历史统计和总花费
- ✅ 每日打卡，记录是否吸烟
- 🎯 设置戒烟目标（如买电脑），显示省钱进度
- 🔔 定时提醒（需在 App 内开启）
- 👤 手机号/微信登录（模拟）
- ⚙️ 绑定手机号、邮箱、微信

## 注意事项

- 本 App 为本地应用，数据保存在手机本地（localStorage）
- 登录功能为模拟，不会真的发送验证码
- 通知功能需在后端的 AlarmManager 配合，当前版本为应用内提醒
- 如要发布到应用商店，需添加真实的后端服务和推送通知

---

构建问题？请参考 Android Studio 官方文档：
https://developer.android.com/studio/build
