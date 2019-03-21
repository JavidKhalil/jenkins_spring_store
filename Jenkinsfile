pipeline {
    agent any
    stages {
        stage('Compile stage') {
            steps {
                withMaven(maven: 'maven 3.6.0') {
                    sh 'mvn clean compile'
                }
            }
        }
        stage('Testing stage') {
            steps {
                withMaven(maven: 'maven 3.6.0') {
                    sh 'mvn test'
                }
            }
        }
        stage('Deploymen stage') {
            steps {
                withMaven(maven: 'maven 3.6.0') {
                    sh 'mvn deploy'
                }
            }
        }
    }
}
