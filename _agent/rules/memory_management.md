# Progress & Memory Management

To maintain long-term context in large projects, you must actively manage the project's "memory".

## 1. Context Summarization
Every 10-15 turns, or after a major milestone:
- Summarize the current state of the project.
- List what has been accomplished and what remains in the backlog.

## 2. Rule Evolution
If you discover a project-specific quirk or a common mistake you keep making:
- Suggest an update to `CLAUDE.md` or the relevant Rule file to prevent future errors.

## 3. Project Map
Maintain an internal mental map of how files connect (e.g., how the iOS ViewModels connect to the Spring Boot Controllers via RabbitMQ). Use `grep` and `list_dir` to refresh this map whenever a new area of the codebase is touched.
