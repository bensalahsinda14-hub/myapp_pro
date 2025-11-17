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
                    // Crée le répertoire des rapports si pas existant
                    sh 'mkdir -p /home/jenkins/zap-reports'

                    // Lancer ZAP en mode baseline scan
                    sh """
                        docker run --rm -t \
                        -v /home/jenkins/zap-reports:/zap/wrk \
                        ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
                        -t http://192.168.17.146:8080 \
                        -r /zap/wrk/report.html \
                        -J /zap/wrk/report.json
                    """
                }

                // Archiver le rapport HTML et JSON
                archiveArtifacts artifacts: '/home/jenkins/zap-reports/report.html', fingerprint: true
                archiveArtifacts artifacts: '/home/jenkins/zap-reports/report.json', fingerprint: true
            }
        }

        stage('DAST - Optional Full Scan') {
            steps {
                script {
                    echo 'Si besoin, on peut ajouter un full scan avec ZAP GUI ou API pour tests approfondis'
                    // Exemple (commenté):
                    // sh 'docker run --rm -v /home/jenkins/zap-reports:/zap/wrk ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py -t http://192.168.17.146:8080 -r /zap/wrk/full-report.html'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline terminé, rapports SAST et DAST archivés.'
        }
    }
}

