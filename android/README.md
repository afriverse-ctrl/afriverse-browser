# Afriverse Browser — Android

Native Android browser built on GeckoView (Firefox engine). Default homepage:
`https://alpha.afriversedao.org`.

## Build in CI (default path)

Push a `v*` tag on `main` — `.github/workflows/build-android.yml` builds a
signed release APK, uploads it to the matching GitHub Release as
`afriverse-alpha-android.apk`.

## Local build

Requires JDK 17 + Android SDK (platform 34, build-tools 34.0.0).

    cd android
    gradle wrapper --gradle-version 8.9
    ./gradlew :app:assembleDebug

APK lands in `app/build/outputs/apk/debug/`.

The `release` build type expects `AFRIVERSE_KEYSTORE_PATH`,
`AFRIVERSE_KEYSTORE_PASSWORD`, `AFRIVERSE_KEY_ALIAS`, `AFRIVERSE_KEY_PASSWORD`
in the environment; without them it falls back to the debug signing config.
