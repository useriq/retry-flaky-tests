def call(Map<String, String> params) {
    assert params["runId"]

    def threadsCount = params["threadsCount"] ?: "8"
    def testList = params["testList"] ?: "*Test"
    def runId = params["runId"]

    stage("start test job") {
        build job: 'QA/TestJob',
                parameters: [string(name: 'threadsCount', value: threadsCount),
                             string(name: 'runId', value: runId),
                             string(name: 'testList', value: testList)]
    }
}