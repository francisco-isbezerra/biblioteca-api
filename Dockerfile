# ---------- Build stage ----------
# Mudamos para 17 porque é a sua versão
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia os arquivos necessários
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x ./mvnw

# Copia o código fonte
COPY src src

# LIMITAMOS A MEMÓRIA AQUI para o Render não travar
# Usamos o 'mvn' da imagem que é mais estável no Docker do que o script wrapper
RUN mvn -DskipTests clean package