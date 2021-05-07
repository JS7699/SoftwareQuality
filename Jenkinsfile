pipeline {
    agent any
    tools {
        maven 'maven 3.8.1'
        jdk 'jdk 1.8'
    }
    stages {
        stage ('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage ('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }
    post {
        always {
            junit 'build/reports/**/*.xml'
        }
    }
}
