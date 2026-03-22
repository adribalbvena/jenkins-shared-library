#!/user/bin/env groovy
def call(Map config = [:]) {
    assert config.repo    : "Error: 'repo' (Docker Hub repository) is required"
    assert config.tag     : "Error: 'tag' (image version) is required"

    def dockerCreds = config.credsId ?: 'docker-hub-creds'

    sh "docker build -t ${config.repo}:${config.tag} ."
    withCredentials([usernamePassword(credentialsId: dockerCreds, passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo \$PASS | docker login -u \$USER --password-stdin"    
        sh "docker push ${config.repo}:${config.tag}"
        sh "docker logout"
    }
}