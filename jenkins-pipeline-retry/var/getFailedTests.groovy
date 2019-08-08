def call(String runId) {
    def response = httpRequest httpMode: 'GET', url: "http://reporter:8080/failedTests/$runId"
    def json = new JsonSlurper().parseText(response.content)

    return json.data
}