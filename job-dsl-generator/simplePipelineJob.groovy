pipelineJob("QA/ExperimentalJob") {

    parameters {
        stringParam("runId", "unknonw")
    }

    description("Запуск тестов с дожатием")

    definition {

        cpsScm {
            lightweight(true)
            scm {
                git {
                    remote {
                        url('ssh://git@bitbucket.ru/qa/jenkins-groovy-scripts.git')
                        credentials("GIT_KEY_CREDENTIALS_ID")
                    }
                    branch('master')
                }
            }
            scriptPath "jenkins-pipeline-retry/src/main" +
                    "/groovy/pipelines/simple-pipeline.groovy"
        }
    }

    logRotator {
        numToKeep(100)
    }
}