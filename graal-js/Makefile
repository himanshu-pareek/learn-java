# ─────────────────────────────────────────────
#  GraalJS on Temurin JDK — Project Makefile
# ─────────────────────────────────────────────

LIBS_DIR   := graaljs-libs
SRC_DIR    := src
OUT_DIR    := out
MAIN_CLASS := Main

# Override CLASS and/or ARGS at the command line:
#   make run CLASS=com.example.OtherMain ARGS="arg1 arg2"
CLASS ?= $(MAIN_CLASS)
ARGS  ?=

# Build classpath from every JAR in LIBS_DIR
CP := $(shell find $(LIBS_DIR) -name "*.jar" | tr '\n' ':')$(OUT_DIR)

# JVM flags required for Truffle's deep reflection on JDK 17+
JVM_FLAGS := \
	-Dpolyglot.engine.WarnInterpreterOnly=false \
	--add-opens java.base/java.lang=ALL-UNNAMED \
	--add-opens java.base/java.lang.reflect=ALL-UNNAMED \
	--add-opens java.base/java.io=ALL-UNNAMED \
	--add-opens java.base/java.util=ALL-UNNAMED \
	-ea

# ─────────────────────────────────────────────

.PHONY: compile run shell clean verify help

## Default target
all: compile

## Compile all .java sources under src/
compile:
	@echo "── Compiling sources ──────────────────────────"
	@mkdir -p $(OUT_DIR)
	javac -cp "$(CP)" -d $(OUT_DIR) $(shell find $(SRC_DIR) -name "*.java")
	@echo "── Done. Classes written to $(OUT_DIR)/ ────────"

## Run a compiled class (default: Main)
## Usage:
##   make run
##   make run CLASS=com.example.OtherMain
##   make run ARGS="hello world"
##   make run CLASS=com.example.OtherMain ARGS="hello world"
run: compile
	@echo "── Running $(CLASS) $(ARGS) ────────────────────"
	java $(JVM_FLAGS) -cp "$(CP)" $(CLASS) $(ARGS)

## Open JShell with all GraalJS JARs on the classpath
shell:
	@echo "── Starting JShell with GraalJS classpath ──────"
	jshell \
		-J-Dpolyglot.engine.WarnInterpreterOnly=false \
		-J--add-opens=java.base/java.lang=ALL-UNNAMED \
		-J--add-opens=java.base/java.lang.reflect=ALL-UNNAMED \
		-J--add-opens=java.base/java.io=ALL-UNNAMED \
		-J--add-opens=java.base/java.util=ALL-UNNAMED \
		--class-path "$(CP)"

## Verify all JARs in LIBS_DIR are valid zip archives
verify:
	@echo "── Verifying JARs in $(LIBS_DIR)/ ──────────────"
	@ok=0; bad=0; \
	for jar in $(LIBS_DIR)/*.jar; do \
		if unzip -t "$$jar" > /dev/null 2>&1; then \
			echo "  OK   $$jar"; ok=$$((ok+1)); \
		else \
			echo "  BAD  $$jar"; bad=$$((bad+1)); \
		fi; \
	done; \
	echo "──────────────────────────────────────────────"; \
	echo "  $$ok OK, $$bad corrupt"; \
	[ $$bad -eq 0 ]

## Remove compiled output
clean:
	@echo "── Cleaning $(OUT_DIR)/ ────────────────────────"
	@rm -rf $(OUT_DIR)
	@echo "── Clean complete ──────────────────────────────"

## Print available targets
help:
	@echo ""
	@echo "  make compile                          Compile all sources in $(SRC_DIR)/"
	@echo "  make run                              Run $(MAIN_CLASS) with no args"
	@echo "  make run CLASS=com.example.Foo        Run a different class"
	@echo "  make run ARGS=\"a b c\"                 Pass arguments to Main"
	@echo "  make run CLASS=com.example.Foo ARGS=\"a b\"  Class + args"
	@echo "  make shell                            Open JShell with GraalJS classpath"
	@echo "  make verify                           Check all JARs are valid"
	@echo "  make clean                            Delete compiled output"
	@echo "  make help                             Show this message"
	@echo ""
