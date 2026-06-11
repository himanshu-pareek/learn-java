#!/bin/bash
set -e

rm -rf graaljs-libs || true
mkdir graaljs-libs && cd graaljs-libs

BASE="https://repo1.maven.org/maven2"
VER="24.1.0"

# ── Polyglot API ──────────────────────────────────────────────
wget "$BASE/org/graalvm/polyglot/polyglot/$VER/polyglot-$VER.jar"

# ── GraalJS engine ────────────────────────────────────────────
# js-language is the actual engine JAR (what GRAALJS dist publishes)
wget "$BASE/org/graalvm/js/js-language/$VER/js-language-$VER.jar"
# js-scriptengine is for JSR-223 ScriptEngine API support
wget "$BASE/org/graalvm/js/js-scriptengine/$VER/js-scriptengine-$VER.jar"

# ── Truffle ───────────────────────────────────────────────────
wget "$BASE/org/graalvm/truffle/truffle-api/$VER/truffle-api-$VER.jar"
wget "$BASE/org/graalvm/truffle/truffle-runtime/$VER/truffle-runtime-$VER.jar"

# ── SDK ───────────────────────────────────────────────────────
wget "$BASE/org/graalvm/sdk/collections/$VER/collections-$VER.jar"
wget "$BASE/org/graalvm/sdk/nativeimage/$VER/nativeimage-$VER.jar"
wget "$BASE/org/graalvm/sdk/word/$VER/word-$VER.jar"

# ── Regex & ICU4J (GraalVM's repackaged shadowed version) ─────
wget "$BASE/org/graalvm/regex/regex/$VER/regex-$VER.jar"
wget "$BASE/org/graalvm/shadowed/icu4j/$VER/icu4j-$VER.jar"

