pipeline {
    agent any

    tools {
        // 安装配置为 "maven" 的 Maven 版本，并将其添加到路径中。
        maven "maven"
        nodejs "nodejs"
    }
    
    environment {
        KUBECONFIG = '"C:\\Users\\15252\\.kube\\config"'
        DOCKER_REGISTRY = 'aintme'
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials-1')
    }

    stages {
        stage('Checkout') {
            steps {
                // 从 GitHub 存储库获取代码
                git url: 'https://github.com/Aintme187/summerTerm.git', branch: 'weifuwu'
            }
        }
        
        stage('Unit Test') {
            steps {
                script {
                    dir('backend') {
                        sh 'echo "Running unit tests..."'
                    }
                }
            }
        }
        
        stage('Build') {
            steps {
                script {
                    // 后端构建
                    dir('backend') {
                        sh 'mvn clean package'
                    }
                    // 使用 NodeJS 构建前端
                    dir('frontend') {
                        // sh 'npm install'
                        sh 'npm run build'
                    }
                    sh 'echo "Building the application..."'
                }
            }
        }
        
        // stage('Dockerize') {
        //     steps {
        //         script {
        //             docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials-1') {
        //                 dir('backend') {
        //                     dir('blog-service'){
        //                         def blogService = docker.build 'aintme/summer-term-blog-service:latest'
        //                         blogService.push()
        //                     }
        //                     dir('gateway'){
        //                         def gateway = docker.build 'aintme/summer-term-gateway:latest'
        //                         gateway.push()
        //                     }
        //                     dir('manage-service'){
        //                         def manageService = docker.build 'aintme/summer-term-manage-service:latest'
        //                         manageService.push()
        //                     }
        //                     dir('teach-service'){
        //                         def teachService = docker.build 'aintme/summer-term-teach-service:latest'
        //                         teachService.push()
        //                     }
        //                     dir('user-center-service'){
        //                         def userCenterService = docker.build 'aintme/summer-term-user-center-service:latest'
        //                         userCenterService.push()
        //                     }
        //                 }
        //                 dir('frontend') {
        //                     def frontend = docker.build 'aintme/summer-term-frontend:latest'
        //                     frontend.push()
        //                     }
        //                 dir('mysql') {
        //                     def mysql = docker.build 'aintme/summer-term-mysql:latest'
        //                     mysql.push()
        //                 }
        //             }
        //         }
        //     }
        // }
        
        stage('Dockerize') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials-1', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        // 登录 Docker Hub
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        dir('backend') {
                            dir('blog-service'){
                                sh 'docker build -t aintme/summer-term-blog-service:latest .'
                                sh 'docker push aintme/summer-term-blog-service:latest .'
                            }
                            dir('gateway'){
                                sh 'docker build -t aintme/summer-term-gateway:latest .'
                                sh 'docker push aintme/summer-term-gateway:latest'
                            }
                            dir('manage-service'){
                                sh 'docker build -t aintme/summer-term-manage-service:latest .'
                                sh 'docker push aintme/summer-term-manage-service:latest'
                            }
                            dir('teach-service'){
                                sh 'docker build -t aintme/summer-term-teach-service:latest .'
                                sh 'docker push aintme/summer-term-teach-service:latest'
                            }
                            dir('user-center-service'){
                                sh 'docker build -t aintme/summer-term-user-center-service:latest .'
                                sh 'docker push aintme/summer-term-user-center-service:latest'
                            }
                        }
                        dir('frontend') {
                            sh 'docker build -t aintme/summer-term-frontend:latest .'
                            sh 'docker push aintme/summer-term-frontend:latest'
                        }
                        dir('mysql') {
                            sh 'docker build -t aintme/summer-term-mysql:latest .'
                            sh 'docker push aintme/summer-term-mysql:latest'
                        }
                    }
                }
            }
        }

        stage('k8s'){
            steps{
                script{
                    sh 'kubectl apply -f ./k8s'
                }
            }
        }
    }
}
      