# Build APK Using GitHub (No Android Studio Needed!)

## 🚀 Quick Steps

### 1. Upload to GitHub

**Option A: Using GitHub Website**
1. Go to [github.com](https://github.com) and log in
2. Click "+" in top right → "New repository"
3. Name it "UnicodeClipboard" 
4. Click "Create repository"
5. Click "uploading an existing file"
6. Drag and drop the UnicodeClipboard.zip file
7. Unzip it first, then upload all the folders/files
8. Click "Commit changes"

**Option B: Using Git Command Line**
```bash
# Extract the zip file first
unzip UnicodeClipboard.zip
cd UnicodeClipboard

# Initialize and push to GitHub
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/UnicodeClipboard.git
git push -u origin main
```

### 2. Enable GitHub Actions
1. Go to your repository on GitHub
2. Click "Actions" tab
3. Click "I understand my workflows, go ahead and enable them"

### 3. Build Automatically
GitHub Actions will automatically build your APK when you:
- Push code to main/master branch
- Create a pull request
- Manually trigger (see below)

### 4. Manual Build (Anytime)
1. Go to "Actions" tab
2. Click "Build Android APK" workflow
3. Click "Run workflow" button
4. Click green "Run workflow"
5. Wait 3-5 minutes for build to complete

### 5. Download Your APK
1. Click on the completed workflow run
2. Scroll down to "Artifacts" section
3. Click "app-debug" to download
4. Unzip the downloaded file
5. You'll find `app-debug.apk` inside

### 6. Install on Android
1. Transfer APK to your phone (email, USB, cloud storage)
2. Open the APK file on your phone
3. Allow "Install from unknown sources" if prompted
4. Install and enjoy!

## 📁 Repository Structure

Make sure your GitHub repository has this structure:
```
UnicodeClipboard/
├── .github/
│   └── workflows/
│       └── build.yml          ← GitHub Actions config
├── app/
│   ├── build.gradle
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/...
│           └── res/...
├── build.gradle               ← Project-level build file
├── settings.gradle
├── gradle.properties
├── .gitignore
└── README.md
```

## 🔧 Troubleshooting

**Build fails with "Permission denied":**
- The workflow automatically handles this with `chmod +x gradlew`
- If it still fails, add gradlew files from Android SDK

**Build fails with Gradle errors:**
- Check that all gradle files are present
- Verify build.gradle versions match

**No "Actions" tab visible:**
- Go to Settings → Actions → Enable workflows

**APK not appearing in Artifacts:**
- Check the workflow run logs for errors
- Make sure build completed successfully (green checkmark)

## 🎯 Alternative: Build Online (Even Easier!)

If GitHub Actions seems complex, try these online builders:
1. **AppBuild.io** - Upload zip, builds APK
2. **APK Builder Online** - Web-based builder
3. **Replit** - Code online and build

## ⚡ Pro Tips

- Every push to GitHub automatically builds a fresh APK
- You can download APKs from any successful build in the past
- Free for public repositories!
- For private repos, you get 2,000 free build minutes/month

## 🆘 Need the Gradle Wrapper?

If gradlew is missing, add these files:
```bash
# Download gradle wrapper
curl -L https://services.gradle.org/distributions/gradle-7.5-bin.zip -o gradle.zip
unzip gradle.zip
./gradle-7.5/bin/gradle wrapper
```

Or use the Android Studio gradle wrapper from any existing Android project.
