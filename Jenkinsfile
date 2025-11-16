pipeline {
    agent any

    tools {
        maven 'Maven3'   // Nom exact configuré dans Jenkins
        jdk 'JDK17'      // Nom exact configuré dans Jenkins
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
    }
}
