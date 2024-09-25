pipeline {
    agent any
    parameters {
        choice(name: 'environment', choices: ['dev', 'test', 'Master'], description: 'select the environment')
    }
    stages {
        stage('branch') {
            steps {
                script {
                    def Branch = ''
                    if (params.environment == 'dev') {
                        Branch = 'DEV'
                    } else if (params.environment == 'test') {
                        Branch = 'TEST'
                    } else if (params.environment == 'Master') {
                        Branch = 'master'
                    }
                    env.BRANCH_NAME = Branch // Set the branch name to an environment variable
                }
            }
        }
        stage("git checkout") {
            steps {
                git branch: env.BRANCH_NAME, credentialsId: '892660ab-457e-4a6c-96f9-2c5a8534f953', url: 'https://github.com/MyPvtOrg/maven-web-application.git'
            }
        }
        stage('git build') {
            steps {
                sh 'mvn package'
            }
        }
    }
}
