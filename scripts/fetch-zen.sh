#!/usr/bin/env bash
# Downloads the Zen Browser source tarball at the pinned version and extracts it.
set -euo pipefail

ZEN_VERSION="${ZEN_VERSION:-1.21.16b}"
DEST="${1:-zen-source}"
TARBALL="zen-${ZEN_VERSION}.source.tar.bz2"
URL="https://github.com/zen-browser/desktop/releases/download/${ZEN_VERSION}/${TARBALL}"

if [[ -d "$DEST" ]]; then
  echo "Source directory '$DEST' already exists, skipping download."
  exit 0
fi

echo "Fetching Zen Browser ${ZEN_VERSION} source..."
curl -fL --retry 3 --retry-delay 5 -o "/tmp/${TARBALL}" "$URL"
echo "Extracting..."
mkdir -p "$DEST"
tar -xjf "/tmp/${TARBALL}" --strip-components=1 -C "$DEST"
rm "/tmp/${TARBALL}"
echo "Done — source at ./$DEST"
