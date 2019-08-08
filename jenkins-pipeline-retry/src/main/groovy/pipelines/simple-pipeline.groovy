package main.groovy.pipelines

library changelog: false,
        identifier: 'shared-lib@master',
        retriever: modernSCM([
                $class       : 'GitSCMSource',
                remote       : 'ssh://git@bitbucket.ru/qa/jenkins-groovy-scripts.git'])

assert runId != null

pipeline {
    agent {
        label any
    }

    stages {
        stage("start test") {
            steps {
              testsWithRerun(runId: runId)
            }
        }
    }
}