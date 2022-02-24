pipeline {
    agent  {label 'sbt-agent'}
    stages {
        stage('build') {
            steps {
                sh 'sbt dist'
            }
        }
    }
}