pipeline {
    agent { any
    stages {
        stage('build') {
            steps {
                sbt('default', 'dist',null,null,null,null)
            }
        }
    }
}