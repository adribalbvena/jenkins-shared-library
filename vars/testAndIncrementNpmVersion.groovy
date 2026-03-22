#!/user/bin/env groovy
def call(Map config = [:]) {
    assert config.directory : "Error: 'directory' parameter is required (e.g., 'app')"
    assert config.type : "Error: 'type' parameter is required (e.g., 'patch', 'minor', 'major')"

    dir(config.directory) {
        sh 'npm install'
        sh 'npm test'
        sh "npm version ${config.type} --no-git-tag-version"
        return sh(script: "node -p \"require('./package.json').version\"", returnStdout: true).trim()
    }
}