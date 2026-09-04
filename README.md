# Afriverse Alpha Browser

A browser built for how Africa gets online.

Built on [Zen Browser](https://github.com/zen-browser/desktop) (Firefox/Gecko engine) — not Chromium.
Open source, no telemetry, no data collection.

## What this repo is

This is the **customisation layer** on top of Zen Browser. It does not contain
the full Firefox/Gecko source (that is ~4 GB). Instead it holds:

- Branding (name, icons, colours)
- Default preferences (homepage, new tab, sidebar panels)
- Patches applied at build time
- GitHub Actions that download Zen source, apply patches, and produce installers

## Features inherited from Zen / Firefox

- Real browser tabs, bookmarks, history
- Built-in password manager
- Page zoom
- Extensions (Firefox add-on compatible)
- Vertical sidebar panels (Afriverse feed, wallet, inbox pre-pinned)

## Building locally

Requires: Linux/macOS, Rust (stable), Python 3.10+, ~8 GB RAM, ~30 GB disk.

```bash
bash scripts/fetch-zen.sh
bash scripts/apply-patches.sh
bash scripts/build.sh
```

## Releasing

Push a tag starting with `v` — GitHub Actions build for Windows, macOS, and
Linux and upload the installers to the release automatically.

```bash
git tag v0.1.0-alpha
git push origin v0.1.0-alpha
```

## Contributing

Issues and PRs welcome at https://github.com/afriversedao/afriverse-browser

## License

Mozilla Public License 2.0 (same as Firefox and Zen Browser).
