pipeline {
    agent  { node { label 'maven'}}
    environment{
        SBT_HOME="${tool 'default'}"
        PATH="${env.SBT_HOME}/bin:${env.PATH}"
    }
    stages {
        stage('build binary') {
            steps {
                sh 'sbt dist'
            }
        }
        stage('create ocp objects') {
            steps {
                sh 'oc process -f template.yaml -p APP_NAME=jenkins-teste | oc -n scala-api apply -f - '
            }
        }
        stage('build image') {
            steps {
                sh 'oc start-build jenkins-teste --from-archive=target/universal/scala-api-1.0-SNAPSHOT.zip --wait=true'
            }
        }
    }
}