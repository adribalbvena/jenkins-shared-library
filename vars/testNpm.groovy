#!/user/bin/env groovy
def call(Map config = [:]) {
    assert config.directory : "Error: 'directory' parameter is required (e.g., 'app')"

    dir(config.directory) {
        sh "npm install"
        sh "npm test"
    }
}