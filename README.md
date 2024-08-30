# MySoftwareCareer复现说明

## 服务器应用

- 前端访问`http://47.94.206.220:30080`
- 后端网关暴露接口`http://47.94.206.220:30088`

## 单体架构应用

1. 从[代码仓库](https://github.com/Aintme187/summerTerm.git)拉取源代码

   ```sh
   git clone https://github.com/Aintme187/summerTerm.git
   ```

2. 切换到`main`分支

   ```sh
   git checkout main
   ```

3. 部署到`k8s`集群（自动拉取`dockerhub`上的最新镜像）

   ```sh 
   kubectl apply -f ./k8s
   ```

- 前端访问`http://IP:80`
- 后端容器暴露端口`http://IP:30007`

## 微服务架构应用

1. 从[代码仓库](https://github.com/Aintme187/summerTerm.git)拉取源代码：

   ```sh
   git clone https://github.com/Aintme187/summerTerm.git
   ```

2. 切换到`weifuwu`分支

   ```sh
   git checkout weifuwu
   ```

3. 构建镜像

   ```sh
   docker build -t aintme/summer-term-blog-service:latest ./backend/blog-service
   docker build -t aintme/summer-term-gateway:latest ./backend/gateway
   docker build -t aintme/summer-term-manage-service:latest ./backend/manage-service
   docker build -t aintme/summer-term-teach-service:latest ./backend/teach-service
   docker build -t aintme/summer-term-user-center-service:latest ./backend/user-center-service
   docker build -t aintme/summer-term-frontend:latest ./frontend
   docker build -t aintme/summer-term-mysql:latest ./mysql
   ```

4. 部署到`k8s`集群

   ```sh
   kubectl apply -f ./k8s/changable
   kubectl apply -f ./k8s/relentless
   ```

- 前端访问`http://IP:30080`
- 后端网关暴露接口`http://IP:30088`