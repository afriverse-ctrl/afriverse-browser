#!/usr/bin/env bash
# Applies Afriverse customisation patches on top of the Zen source tree.
set -euo pipefail

SOURCE_DIR="${1:-zen-source}"
PATCHES_DIR="$(dirname "$0")/../patches"
BRANDING_DIR="$(dirname "$0")/../branding"
NEWTAB_DIR="$(dirname "$0")/../src/newtab"

if [[ ! -d "$SOURCE_DIR" ]]; then
  echo "Source directory '$SOURCE_DIR' not found. Run fetch-zen.sh first."
  exit 1
fi

echo "Applying patches..."
for patch in "$PATCHES_DIR"/*.patch; do
  echo "  → $(basename "$patch")"
  patch -p1 -d "$SOURCE_DIR" < "$patch"
done

echo "Copying Afriverse branding..."
mkdir -p "$SOURCE_DIR/browser/branding/afriverse"
cp -r "$BRANDING_DIR"/* "$SOURCE_DIR/browser/branding/afriverse/"

echo "Copying custom new-tab page..."
cp -r "$NEWTAB_DIR"/* "$SOURCE_DIR/browser/components/newtab/content-src/afriverse/"

echo "Copying mozconfig..."
cp "$(dirname "$0")/../configs/mozconfig" "$SOURCE_DIR/mozconfig"

echo "All customisations applied."
