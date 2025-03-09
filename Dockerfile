FROM eclipse-temurin:21

WORKDIR /app

ARG JAVA_ENABLE_DEBUG
ENV JAVA_ENABLE_DEBUG=${JAVA_ENABLE_DEBUG}
ENV JAR=mars-rover-api.jar

COPY entrypoint.sh .
COPY ./build/libs/${JAR} .

RUN groupadd --system appuser -g 1001 && \
    useradd --system -g appuser -u 1001 appuser && \
    mkdir -p /app/data && \
    chown -R appuser:appuser /app && \
    chown appuser:appuser ${JAR} && \
    chmod 500 ${JAR} && \
    chmod +x entrypoint.sh # Add this line to give execute permission to entrypoint.sh

EXPOSE 8080
USER 1001

CMD ["./entrypoint.sh"]
