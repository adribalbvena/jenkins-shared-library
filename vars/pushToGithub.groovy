#!/user/bin/env groovy
def call(Map config = [:]) {
    assert config.repoName : "Error: 'repoName' is required"
    assert config.file     : "Error: 'file' path to commit is required"
    assert config.message  : "Error: 'message' for git commit is required"

    def githubCreds = config.credsId ?: 'github-creds'

    withCredentials([usernamePassword(credentialsId: githubCreds, passwordVariable: 'GIT_TOKEN', usernameVariable: 'GIT_USER')]) {
        sh 'git config user.email "jenkins-bot@example.com"'
        sh 'git config user.name "Jenkins CI"'

        sh "git add ${config.file}"
        sh "git commit -m '${config.message} [skip ci]'"
        sh "git push https://${GIT_USER}:${GIT_TOKEN}@github.com/${GIT_USER}/${config.repoName}.git HEAD:main"
    }
}