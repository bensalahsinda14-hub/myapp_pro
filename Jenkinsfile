pipeline {
    agent any

    tools {
        maven 'Maven3'   // Nom exact de Maven configuré dans Jenkins
        jdk 'JDK17'      // Nom exact du JDK configuré dans Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: 'main']],
                    userRemoteConfigs: [[
                        url: 'git@github.com:bensalahsinda14-hub/myapp_pro.git',
                        credentialsId: 'github_ssh_key'
                    ]]
                ])
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('SAST') {
            steps {
                withCredentials([string(credentialsId: 'sonar_token', variable: 'SONAR_TOKEN')]) {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=myapp_pro \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }

        stage('DAST') {
            steps {
                sh """
                    mkdir -p /home/jenkins/zap-reports
                    docker run -t -v /home/jenkins/zap-reports:/zap/wrk \
                    ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
                    -t http://192.168.17.146:8080 \
                    -r /zap/wrk/report.html
                """
                archiveArtifacts artifacts: '/home/jenkins/zap-reports/report.html', fingerprint: true
            }
        }
    }
}

