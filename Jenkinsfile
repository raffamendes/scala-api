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
                sh 'oc process -f template.yaml -p APP_NAME=jenkins-teste | oc apply -f - '
            }
        }
        stage('build image') {
            steps {
                sh 'oc start-build jenkins-teste --from-archive=target/universal/scala-api-1.0-SNAPSHOT.zip --wait=true'
            }
        }
        stage('acs scan image') {
            steps {
                stackrox (
                    apiToken: 'eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3dGswIiwidHlwIjoiSldUIn0.eyJhdWQiOlsiaHR0cHM6Ly9zdGFja3JveC5pby9qd3Qtc291cmNlcyNhcGktdG9rZW5zIl0sImV4cCI6MTY3NzI3MTkzNywiaWF0IjoxNjQ1NzM1OTM3LCJpc3MiOiJodHRwczovL3N0YWNrcm94LmlvL2p3dCIsImp0aSI6Ijc3ZmQyODk2LTJlMWMtNGU3Zi05YmIxLWFhODk5MzNiMDc1NSIsIm5hbWUiOiJqZW5raW5zIiwicm9sZXMiOlsiQ29udGludW91cyBJbnRlZ3JhdGlvbiJdfQ.T8rbGhRSRICOSCvNJoY2yBsiNuUiysfoUQYiLiZC7fwaNAUaVyjG3Pqec6IfpBqUi0g6F1yxlzVg5hwGod2LtQFDPKf_iHNUxT9_dO708MQs8ffrXkB9F4TpGewGb65_Hu01WiOHWDvuR9rSbFAuR2NKZqu-xAE1cWXYvGPP6UW9EiQ8oHWOnhbzaDjUklb9jcq0WS48AFfV8eD42nvs7_2eTueZG2fEmmtZgmFlp6TZ-baGCV3eZ29JTV7bldbJa9YYVPNguQV2eUj6FgNuo1kvyWxLdDRJXo7cQDtdRRZdmKTBDoHp8JozVhMrOeKErGX08htJDm4j5iyEDMVktM9SuIGFoguB-iThtemg2Qzma9ccu1KNcUtGwfR3ap9mmrlj59m5TMs7aEGkSLmFhsTnqXfIywdJfQd4mpoSYOXTE7tREuePD58Sv1h4cWVv5qb3NsDQ6VgiX5Qt5r1S_b4HeTBG3QjcZGR6juxP-2mFF2dfTNIGJDPLMEr8ob9QLhRKB-E-Zbw5vkv84EudirRVnAqA0ppdNFgULnWsNzxSU0ZGdaSwZstb4ldULncIpuIvRT0PfmXk1XS4VohdXG7-rXA7oRB_ti8M8eGhACoCftnwLTArTrwXvv7Dq3OEEEQh8zasZUS7hVS9UWviZTfdjXGLGZmr6iDiAJWFhKQ',
                    caCertPEM: '',
                    enableTLSVerification: false,
                    failOnCriticalPluginError: true,
                    failOnPolicyEvalFailure: true,
                    portalAddress: 'https://central.acs.svc.cluster.local:443',
                    imageNames: "default-route-openshift-image-registry.apps.dam-iptu-1.latam-1.rht-labs.com/jenkins/jenkins-teste:latest"

                )
            }
        }
    }
}
