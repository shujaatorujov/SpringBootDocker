pipeline {
  environment {
    registry = "progc3/spring-docker"
    registryCredential = 'dockerHub'
  }
  agent any
  stages {
    stage('Clone Image from Github'){
      steps{
        sh 'rm -rf SpringBootDocker' 
        sh 'git clone https://github.com/shujaatorujov/SpringBootDocker.git'
        sh 'cd SpringBootDocker/'
      }
    }
    
    stage('Compile and Test project'){
      steps{
        sh 'mvn clean compile install'
      }
    }
    
    stage('Building image') {
      steps{
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }
    stage('Push Image to the Docker Hub ') {
      steps{
        script {
          docker.withRegistry( '', registryCredential ) {
            dockerImage.push()
          }
        }
      }
    }
    stage('Remove Unused docker image') {
      steps{
        sh "docker rmi $registry" + ":$BUILD_NUMBER"
      }
    }
    
    stage('Deploy image with Kubernetes') {
      steps{
        sh "export KUBECONFIG=/var/lib/jenkins/config && sed -i 's/latest/$BUILD_NUMBER/g' deployment.yaml && kubectl apply -f deployment.yaml"
      }
    }
  }

  post {
          always {
              emailext body: 'Your Application home page is: http://192.168.47.129:30005/', recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Spring Boot Application'
          }
      }
}
