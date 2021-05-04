pipeline {
    agent any
    tools {
        maven 'maven 3.8.1'
        jdk 'jdk 1.8'
    }
    stages {
        stage ('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage ('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }
    environment {
        EMAIL_TO = 'johannes99steinmeyer@web.de'
    }
    post {
    always {
            junit '**/application/target/surefire-reports/TEST-*.xml'
        }
        failure {
            emailext body: 'Check console output at $BUILD_URL to view the results. \n\n ${CHANGES} \n\n -------------------------------------------------- \n${BUILD_LOG, maxLines=100, escapeHtml=false}',
                    to: "${EMAIL_TO}",
                    subject: 'Build failed in Jenkins: $PROJECT_NAME - #$BUILD_NUMBER'
        }
        unstable {
            emailext body: 'Check console output at $BUILD_URL to view the results. \n\n ${CHANGES} \n\n -------------------------------------------------- \n${BUILD_LOG, maxLines=100, escapeHtml=false}',
                    to: "${EMAIL_TO}",
                    subject: 'Unstable build in Jenkins: $PROJECT_NAME - #$BUILD_NUMBER'
        }
        changed {
            emailext body: 'Check console output at $BUILD_URL to view the results.',
                    to: "${EMAIL_TO}",
                    subject: 'Jenkins build is back to normal: $PROJECT_NAME - #$BUILD_NUMBER'
        }
    }
}
