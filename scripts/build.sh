#!/usr/bin/env bash
# Full build: fetch → patch → compile → package.
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
REPO_ROOT="$(dirname "$SCRIPT_DIR")"
SOURCE_DIR="$REPO_ROOT/zen-source"

"$SCRIPT_DIR/fetch-zen.sh" "$SOURCE_DIR"
"$SCRIPT_DIR/apply-patches.sh" "$SOURCE_DIR"

cd "$SOURCE_DIR"
echo "Running bootstrap..."
python3 mach bootstrap --no-interactive

echo "Building Afriverse Alpha..."
python3 mach build

echo "Packaging..."
python3 mach package

echo "Build complete. Artifacts in $SOURCE_DIR/obj-*/dist/"
