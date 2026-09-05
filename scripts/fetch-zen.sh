#!/usr/bin/env bash
# Clones the Zen Browser source at the pinned version tag.
set -euo pipefail

ZEN_VERSION="${ZEN_VERSION:-1.21.16b}"
DEST="${1:-zen-source}"

if [[ -d "$DEST" ]]; then
  echo "Source directory '$DEST' already exists, skipping clone."
  exit 0
fi

echo "Cloning Zen Browser ${ZEN_VERSION} source..."
git clone --depth=1 --branch "${ZEN_VERSION}" \
  https://github.com/zen-browser/desktop.git "$DEST"
echo "Done — source at ./$DEST"
