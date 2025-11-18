pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK17'
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

        stage('SAST - SonarQube') {
            steps {
                withCredentials([string(credentialsId: 'sonar_token', variable: 'SONAR_TOKEN')]) {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=myapp_pro \
                        -Dsonar.host.url=http://192.168.17.146:9000 \
                        -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }

        stage('DAST - OWASP ZAP') {
            steps {
                script {
                    // Crée un dossier pour les rapports dans le workspace
                    sh "mkdir -p ${WORKSPACE}/zap-reports"

                    // Lancer OWASP ZAP en Docker pour un scan baseline
                    sh """
                        docker run --rm -t \
                        -v ${WORKSPACE}/zap-reports:/zap/wrk \
                        ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
                        -t http://192.168.17.146:8080 \
                        -r /zap/wrk/report.html
                    """
                }
                // Archiver le rapport
                archiveArtifacts artifacts: 'zap-reports/report.html', fingerprint: true
            }
        }

        stage('DAST - Optional Full Scan') {
            steps {
                echo "Tu peux ajouter ici un scan complet OWASP ZAP si tu veux."
            }
        }
    }

    post {
        always {
            echo "Pipeline terminé, rapports SAST et DAST archivés."
        }
    }
}

