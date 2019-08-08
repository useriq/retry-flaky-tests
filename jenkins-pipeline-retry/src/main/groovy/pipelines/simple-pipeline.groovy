package main.groovy.pipelines

pipeline {
    agent {
        label any
    }

    stages {
        stage("start test") {
            steps {
                build job: 'QA/TestJob',
                        parameters: [string(name: 'threadsCount', value: threadsCount),
                                     string(name: 'runId', value: runId),
                                     string(name: 'testList', value: testList)]
            }
        }
    }
}