# Best practices — core/src/main/java

Any change under `core/src/main/java/` must be accompanied by a corresponding change
under `core/src/test/java/` in the same pull request. If a PR modifies production code
under `core/src/main/java/` without touching any file under `core/src/test/java/`, flag
it explicitly as missing test coverage for the changed code.
