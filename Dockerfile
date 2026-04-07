# ---------- Estágio 1: Build (Construção) ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia os arquivos de configuração
COPY pom.xml .
COPY src ./src

# Comando de build direto e leve
RUN mvn clean package -DskipTests

# ---------- Estágio 2: Runtime (Execução) ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia o arquivo .jar gerado no estágio anterior
# O comando 'clean package' gera o arquivo dentro da pasta target
COPY --from=build /app/target/*.jar app.jar

# Configura a porta que o Render exige (10000)
ENV PORT=10000
EXPOSE 10000

# Inicia a aplicação limitando a memória para não crashar o plano Free
ENTRYPOINT ["java", "-Xmx380m", "-Dserver.port=10000", "-jar", "app.jar"]