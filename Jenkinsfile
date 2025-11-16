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
                    docker run -t owasp/zap2docker-stable zap-baseline.py \
                    -t http://192.168.17.146:8080 \
                    -r report.html
                """
                archiveArtifacts artifacts: 'report.html', fingerprint: true
            }
        }
    }
}

