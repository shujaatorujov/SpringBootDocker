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
          dockerImage = docker.build registry //+ ":$BUILD_NUMBER"
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
        sh "docker rmi $registry" //:$BUILD_NUMBER
      }
    }
    
    stage('Deploy image with Kubernetes') {
      steps{
        sh "kubectl  --kubeconfig=/etc/kubernetes/admin.conf apply –k deployment.yaml"
      }
    }
  }
}
