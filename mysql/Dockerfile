# 基础镜像
FROM mysql:8.0

# 将 SQL 文件复制到容器中的指定目录
COPY blog_service.sql /docker-entrypoint-initdb.d/
COPY teach_service.sql /docker-entrypoint-initdb.d/
COPY user_center_service.sql /docker-entrypoint-initdb.d/
