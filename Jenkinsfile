pipeline {
    agent any

    environment {
        DOCKERHUB_USERNAME = 'onsnas'
        IMAGE_NAME = "${DOCKERHUB_USERNAME}/boycott-app:latest"
        KUBECONFIG_CREDENTIALS = 'kuberconfig-file'
        NAMESPACE = 'boycott'
    }

    tools {
        maven 'M2_HOME'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/onsnasri/Boycott.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${IMAGE_NAME} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([string(credentialsId: 'dockerhub-password', variable: 'DOCKERHUB_TOKEN')]) {
                    sh "echo $DOCKERHUB_TOKEN | docker login -u ${DOCKERHUB_USERNAME} --password-stdin"
                    sh "docker push ${IMAGE_NAME}"
                }
            }
        }

        stage('Deploy MySQL & App to Kubernetes') {
            steps {
                script {
                    withCredentials([file(credentialsId: KUBECONFIG_CREDENTIALS, variable: 'KUBECONFIG')]) {
                        sh "kubectl --kubeconfig=$KUBECONFIG apply -n ${NAMESPACE} -f k8s/mysql-pvc.yaml"
                        sh "kubectl --kubeconfig=$KUBECONFIG apply -n ${NAMESPACE} -f k8s/mysql-deployment.yaml"
                        sh "kubectl --kubeconfig=$KUBECONFIG apply -n ${NAMESPACE} -f k8s/mysql-service.yaml"
                        sh "kubectl --kubeconfig=$KUBECONFIG apply -n ${NAMESPACE} -f k8s/app-deployment.yaml"
                        sh "kubectl --kubeconfig=$KUBECONFIG apply -n ${NAMESPACE} -f k8s/app-service.yaml"
                    }
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    withCredentials([file(credentialsId: KUBECONFIG_CREDENTIALS, variable: 'KUBECONFIG')]) {
                        sh "kubectl --kubeconfig=$KUBECONFIG get pods -n ${NAMESPACE}"
                        sh "kubectl --kubeconfig=$KUBECONFIG get svc -n ${NAMESPACE}"
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline terminé avec succès !"
        }
        failure {
            echo "Pipeline échoué ! Vérifier les logs."
        }
    }
}
