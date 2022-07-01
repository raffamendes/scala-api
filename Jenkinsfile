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
                    apiToken: 'eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3dGswIiwidHlwIjoiSldUIn0.eyJhdWQiOlsiaHR0cHM6Ly9zdGFja3JveC5pby9qd3Qtc291cmNlcyNhcGktdG9rZW5zIl0sImV4cCI6MTY4ODIyMDExOSwiaWF0IjoxNjU2Njg0MTE5LCJpc3MiOiJodHRwczovL3N0YWNrcm94LmlvL2p3dCIsImp0aSI6IjVhZTRlOWM5LTI4MTYtNDkxMS04ZGMxLWMxZjYwNjFhMmRiYyIsIm5hbWUiOiJqZW5raW5zIiwicm9sZXMiOlsiQ29udGludW91cyBJbnRlZ3JhdGlvbiJdfQ.UoCTcIq8Dd3LMXsZ_MT_Ut7ADv3U7JJ8EgxdBzxlic0W7Jj2Oh9viinW2rfqwMT7S5IGPIZLLDctZlwgl0GjHniYffmcWzvwx2KMWPT11Mjxiiq0YswE_o_JFwRBPpLQjGbXDj0sU1UVkscJnFeAB12O2fun4I082o5cQLm6DzxM6TD48qo-vGD8FxFKFezfoMC4AYuZciaCpYAw-dxmLe6Mu3x2uNAy9lFMe3tnc6Qkh-gvogi5TE6oD6sRVEsWVuLLCSBuUIZSbvTCOeXurnbHijlo0nMCxBAnPwNfZ0Y-evagbr7riGoL1tedJFo8sxbK4wnpzNBfLNArGEEpvzpSJakrJaItthTBmUdL5gzkc1OHQNUVXGaBBtkxzPyXUVsB8CdvK9wyrNOxGGd9DiZG1CAu_d-qci6tI-0tRfo10-oEahrCMnZPBQGuEV_3BQUvZehTwgcEvVO2ywH1oFRZfra23Qkd_RxPDFyG5fiDNgHqd2fJEwRIz_0zfsFmGDExUNZfsVeCa0bkQS5qVY1ym6Tc8scBL4CuyxpTh00zD55VfiYQLWMrKyYQ5FvNjP21SPy9aHx-MwBXw8LM1TRrsz6G7CtesIDAzuXaDSI425ELnF8psz3qiNIVvgtDXKftpWx3E0zS0LVGXe5ri9w0Y74jInaed2-WYjg1keM',
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
