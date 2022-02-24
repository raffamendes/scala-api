pipeline {
    agent  { node {label 'sbt-agent'}}
    stages {
        stage('build') {
            steps {
                sh 'sbt dist'
            }
        }
    }
}