pipeline {
    agent  { node { label 'maven'}}
    environment{
        SBT_HOME="${tool 'default'}"
        PATH="${env.SBT_HOME}/bin:${env.PATH}"
    }
    stages {
        stage('build') {
            steps {
                sh 'sbt version'
            }
        }
    }
}