pipeline {
    agent  {label 'sbt-agent'}
    stages {
        stage('build') {
            steps {
                sbt('default', 'dist',null,null,null,null)
            }
        }
    }
}