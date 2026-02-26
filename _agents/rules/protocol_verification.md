# Verification & Quality Protocol

Never consider a task "done" until it is verified.

## 1. Compiler Check
After editing Java/backend or React code:
- Run the build command (e.g., `./gradlew compileJava`, `npm run build`) to ensure no syntax errors were introduced.

## 2. Testing
- If the feature has existing tests, run them.
- If it's a new feature, generate unit tests to cover the core logic.
- If it's a bug fix, create a regression test.

## 3. Linting
- Ensure code follows formatting rules. Check for unused imports or variables.

## 4. Self-Correction
If a tool execution fails (e.g., build error), do not ask the user for help immediately. Analyze the error output, use `grep` or `view_file` to find the cause, and attempt to fix it yourself.
