# Call Record Android Flutter

A Flutter-based Android application for automatic call recording with server upload functionality.

## Features

- **Automatic Call Recording**: Detects phone call state changes and records calls automatically
- **Background Upload**: Uploads recorded audio files to a server using WorkManager for reliable background processing
- **Native Android Integration**: Uses Kotlin-based BroadcastReceiver for call state detection
- **Material Design 3**: Modern UI with Material Design 3 theming

## Technologies Used

- **Flutter & Dart** - Cross-platform UI framework
- **Kotlin** - Android native code for call handling
- **Retrofit** - Type-safe HTTP client for Android
- **OkHttp** - HTTP client for networking
- **WorkManager** - Background task scheduling
- **Coroutines** - Asynchronous programming

## Suggested GitHub Topics

The following topics are recommended for this repository to improve discoverability:

### Core Technology Topics
- `flutter`
- `dart`
- `android`
- `kotlin`
- `material-design`

### Functionality Topics
- `call-recorder`
- `call-recording`
- `audio-recording`
- `phone-call`
- `telephony`

### Library/Framework Topics
- `retrofit`
- `okhttp`
- `workmanager`
- `coroutines`
- `broadcast-receiver`

### Category Topics
- `mobile-app`
- `android-app`
- `flutter-app`
- `audio`
- `file-upload`

## How to Add GitHub Topics

### Method 1: GitHub Web Interface

1. Navigate to your repository on GitHub
2. Click on the gear icon (⚙️) next to "About" in the right sidebar
3. In the "Topics" field, add topics separated by spaces
4. Click "Save changes"

### Method 2: GitHub CLI (gh)

The GitHub CLI provides a convenient way to manage repository topics from the command line.

#### Prerequisites
```bash
# Install GitHub CLI
# On macOS:
brew install gh

# On Ubuntu/Debian:
curl -fsSL https://cli.github.com/packages/githubcli-archive-keyring.gpg | sudo dd of=/usr/share/keyrings/githubcli-archive-keyring.gpg
echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/githubcli-archive-keyring.gpg] https://cli.github.com/packages stable main" | sudo tee /etc/apt/sources.list.d/github-cli-stable.list > /dev/null
sudo apt update
sudo apt install gh

# On Windows (with winget):
winget install GitHub.cli

# Authenticate with GitHub
gh auth login
```

#### Add Topics Using GitHub CLI

```bash
# Add all recommended topics at once (using line continuation for readability)
gh repo edit Baneeishaque/Call-Record-Android-Flutter \
  --add-topic flutter,dart,android,kotlin \
  --add-topic call-recorder,call-recording,audio-recording \
  --add-topic material-design,retrofit,okhttp,workmanager \
  --add-topic coroutines,mobile-app,android-app,flutter-app,telephony

# Or add topics individually
gh repo edit --add-topic flutter
gh repo edit --add-topic dart
gh repo edit --add-topic android
gh repo edit --add-topic kotlin
gh repo edit --add-topic call-recorder
gh repo edit --add-topic call-recording
gh repo edit --add-topic audio-recording
gh repo edit --add-topic material-design
gh repo edit --add-topic retrofit
gh repo edit --add-topic workmanager
gh repo edit --add-topic mobile-app
gh repo edit --add-topic flutter-app
```

#### Remove Topics Using GitHub CLI

```bash
# Remove a specific topic
gh repo edit --remove-topic topic-name

# Remove multiple topics
gh repo edit --remove-topic topic1,topic2,topic3
```

#### View Current Topics

```bash
# View repository information including topics
gh repo view Baneeishaque/Call-Record-Android-Flutter --json repositoryTopics

# Using jq for formatted output
gh repo view Baneeishaque/Call-Record-Android-Flutter --json repositoryTopics --jq '.repositoryTopics[].name'
```

### Method 3: GitHub REST API

You can also use the GitHub REST API to manage topics programmatically.

#### Get Current Topics

```bash
curl -H "Authorization: token YOUR_GITHUB_TOKEN" \
  -H "Accept: application/vnd.github.mercy-preview+json" \
  https://api.github.com/repos/Baneeishaque/Call-Record-Android-Flutter/topics
```

#### Replace All Topics

```bash
curl -X PUT \
  -H "Authorization: token YOUR_GITHUB_TOKEN" \
  -H "Accept: application/vnd.github.mercy-preview+json" \
  https://api.github.com/repos/Baneeishaque/Call-Record-Android-Flutter/topics \
  -d '{"names":["flutter","dart","android","kotlin","call-recorder","call-recording","audio-recording","material-design","retrofit","okhttp","workmanager","coroutines","mobile-app","android-app","flutter-app","telephony"]}'
```

### Method 4: GitHub GraphQL API

```graphql
mutation {
  updateTopics(input: {
    repositoryId: "REPOSITORY_NODE_ID"
    topicNames: ["flutter", "dart", "android", "kotlin", "call-recorder", "call-recording", "audio-recording", "material-design", "retrofit", "okhttp", "workmanager", "coroutines", "mobile-app", "android-app", "flutter-app", "telephony"]
  }) {
    repository {
      repositoryTopics(first: 20) {
        nodes {
          topic {
            name
          }
        }
      }
    }
  }
}
```

To get the repository node ID:
```bash
gh api graphql -f query='{ repository(owner:"Baneeishaque", name:"Call-Record-Android-Flutter") { id } }'
```

### Method 5: Using Python with PyGithub

```python
from github import Github

# Authenticate
g = Github("YOUR_GITHUB_TOKEN")

# Get the repository
repo = g.get_repo("Baneeishaque/Call-Record-Android-Flutter")

# Get current topics
current_topics = repo.get_topics()
print("Current topics:", current_topics)

# Replace all topics
new_topics = [
    "flutter", "dart", "android", "kotlin",
    "call-recorder", "call-recording", "audio-recording",
    "material-design", "retrofit", "okhttp", "workmanager",
    "coroutines", "mobile-app", "android-app", "flutter-app", "telephony"
]
repo.replace_topics(new_topics)
print("Topics updated successfully!")
```

### Method 6: Using JavaScript with Octokit

```javascript
const { Octokit } = require("@octokit/rest");

const octokit = new Octokit({
  auth: "YOUR_GITHUB_TOKEN"
});

async function updateTopics() {
  const topics = [
    "flutter", "dart", "android", "kotlin",
    "call-recorder", "call-recording", "audio-recording",
    "material-design", "retrofit", "okhttp", "workmanager",
    "coroutines", "mobile-app", "android-app", "flutter-app", "telephony"
  ];

  await octokit.repos.replaceAllTopics({
    owner: "Baneeishaque",
    repo: "Call-Record-Android-Flutter",
    names: topics
  });

  console.log("Topics updated successfully!");
}

updateTopics();
```

## Complete List of Recommended Topics

| Topic | Description |
|-------|-------------|
| `flutter` | Google's UI toolkit for building natively compiled applications |
| `dart` | Programming language optimized for building UIs |
| `android` | Android platform |
| `kotlin` | Modern programming language for Android development |
| `call-recorder` | Application that records phone calls |
| `call-recording` | Functionality to record phone conversations |
| `audio-recording` | Recording audio files |
| `material-design` | Google's design system |
| `retrofit` | Type-safe HTTP client for Android and Java |
| `okhttp` | HTTP client for Android and Java |
| `workmanager` | Android library for deferrable background work |
| `coroutines` | Kotlin's solution for asynchronous programming |
| `mobile-app` | Mobile application |
| `android-app` | Android application |
| `flutter-app` | Flutter application |
| `telephony` | Phone/telecommunications related functionality |

## Quick Start Command

Copy and run this command to add all recommended topics:

```bash
gh repo edit Baneeishaque/Call-Record-Android-Flutter \
  --add-topic flutter,dart,android,kotlin \
  --add-topic call-recorder,call-recording,audio-recording \
  --add-topic material-design,retrofit,okhttp,workmanager \
  --add-topic coroutines,mobile-app,android-app,flutter-app,telephony
```

## Getting Started with Development

### Prerequisites

- Flutter SDK 3.29.2 or later
- Java 17
- Android SDK with compile SDK 35

### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/Baneeishaque/Call-Record-Android-Flutter.git
   cd Call-Record-Android-Flutter
   ```

2. Install dependencies
   ```bash
   flutter pub get
   ```

3. Run the app
   ```bash
   flutter run
   ```

## Resources

- [Flutter Documentation](https://docs.flutter.dev/)
- [Dart Documentation](https://dart.dev/guides)
- [Android Developer Guide](https://developer.android.com/guide)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [WorkManager Documentation](https://developer.android.com/topic/libraries/architecture/workmanager)

## License

This project is open source and available under the [MIT License](LICENSE).
