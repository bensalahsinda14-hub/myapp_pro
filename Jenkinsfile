pipeline {
    agent any

    environment {
        MAVEN_HOME = tool name: 'Maven 3.9.3', type: 'maven'
        SONAR_TOKEN = credentials('sonar_token')  // Ton token SonarQube
        ZAP_REPORT_DIR = '/home/devops/zap-reports' // Dossier persistant pour le rapport DAST
        APP_URL = 'http://192.168.17.146:8080'     // URL de l'application à scanner
    }

    stages {

        /* --- 1) Checkout SCM --- */
        stage('Checkout') {
            steps {
                git(
                    url: 'git@github.com:bensalahsinda14-hub/myapp_pro.git',
                    credentialsId: 'github_ssh_key',
                    branch: 'main'
                )
            }
        }

        /* --- 2) Build Maven --- */
        stage('Build') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean package"
            }
        }

        /* --- 3) Archive Maven Artifacts --- */
        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        /* --- 4) SAST - SonarQube --- */
        stage('SAST - SonarQube') {
            steps {
                withCredentials([string(credentialsId: 'sonar_token', variable: 'SONAR_TOKEN')]) {
                    sh """
                        ${MAVEN_HOME}/bin/mvn sonar:sonar \
                        -Dsonar.projectKey=myapp_pro \
                        -Dsonar.host.url=http://192.168.17.146:9000 \
                        -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }

        /* --- 5) DAST - OWASP ZAP --- */
        stage('DAST - OWASP ZAP') {
            steps {
                sh """
                    # Créer dossier persistant si n'existe pas
                    mkdir -p ${ZAP_REPORT_DIR}
                    chmod 777 ${ZAP_REPORT_DIR}

                    # Lancer le scan DAST
                    docker run --rm -t \
                        -v ${ZAP_REPORT_DIR}:/zap/wrk/:rw \
                        ghcr.io/zaproxy/zaproxy:stable \
                        zap-baseline.py \
                        -t ${APP_URL} \
                        -r zap-report.html
                """
            }
        }

        /* --- 6) Archive ZAP Report --- */
        stage('Archive DAST Report') {
            steps {
                archiveArtifacts artifacts: "${ZAP_REPORT_DIR}/zap-report.html"
            }
        }

    } // stages
}

