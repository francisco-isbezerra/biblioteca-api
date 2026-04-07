# ---------- Build stage ----------
# Mudamos para a versão 17 que é a sua!
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia os arquivos de configuração
COPY pom.xml .
COPY src src

# REMOVEMOS o 'dependency:go-offline' porque ele consome muita RAM no Render
# Adicionamos limites de memória para o Maven não travar (-Xmx300m)
RUN mvn -DskipTests clean package -Dmaven.test.skip=true

# ---------- Runtime stage ----------
# Mudamos para a versão 17 aqui também
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia o arquivo gerado
COPY --from=build /app/target/*.jar app.jar

# O Render usa a porta 10000 por padrão no plano Free
ENV PORT=10000
EXPOSE 10000

# Adicionamos -Xmx400m para o Java não estourar a memória do Render ao rodar
ENTRYPOINT ["sh", "-c", "java -Xmx400m -Dserver.port=$PORT -jar app.jar"]