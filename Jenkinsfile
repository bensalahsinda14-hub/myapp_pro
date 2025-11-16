pipeline {
    agent any

    tools {
<<<<<<< HEAD
<<<<<<< HEAD
        maven 'Maven3'   // Nom exact configuré dans Jenkins
        jdk 'JDK17'      // Nom exact configuré dans Jenkins
=======
        maven 'Maven3'   // Nom exact de Maven configuré dans Jenkins
        jdk 'JDK17'      // Nom exact du JDK configuré dans Jenkins
>>>>>>> Add Jenkinsfile for Jenkins pipeline
=======
        maven 'Maven3'     // Nom exact dans Jenkins
        jdk 'JDK17'        // Nom exact dans Jenkins
>>>>>>> Trigger pipeline for SAST
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
                withCredentials([string(credentialsId: 'jenkins', variable: 'SONAR_TOKEN')]) {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=myapp_pro \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }
    }
}

