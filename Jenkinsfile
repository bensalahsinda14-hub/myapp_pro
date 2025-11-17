pipeline {
    agent any

    tools {
        maven 'Maven3'      
        jdk 'JDK17'         
    }

    stages {

        /* --- 1) CLONE GITHUB --- */
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

        /* --- 2) BUILD MAVEN --- */
        stage('Build') {
            steps {
                sh "mvn clean package"
            }
        }

        /* --- 3) ARCHIVE ARTIFACTS --- */
        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        /* --- 4) SAST SonarQube --- */
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
    }
}

