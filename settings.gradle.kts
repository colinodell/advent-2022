rootProject.name = "advent-2022"

plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "1.0.24"
}

gitHooks {
    preCommit {
        from {
            """
            ./gradlew check
            """.trimIndent()
        }
    }
    createHooks(true)
}
