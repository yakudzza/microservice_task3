# Система Мониторинга Метрик с Использованием Spring Kafka
# Описание проекта

Этот проект реализует систему мониторинга для отслеживания работы различных компонентов приложения с использованием Spring Kafka. Система включает в себя три основных микросервиса: Producer для отправки метрик, Consumer для их обработки и анализа, и брокер Kafka для обмена сообщениями между ними. Все компоненты системы развёрнуты в Docker контейнерах для обеспечения портативности и простоты развертывания.

# Компоненты системы
    Producer Service
        Описание: Этот микросервис отвечает за сбор метрик работы приложения и их отправку в Kafka топик metrics-topic.
        API:
            POST /metrics: Отправка метрик в формате JSON.

    Consumer Service
        Описание: Этот микросервис получает метрики из Kafka топика metrics-topic, обрабатывает их, анализирует и добавляет в базу данных.
        API:
            GET /metrics: Получение списка всех метрик.
            GET /metrics/{id}: Получение конкретной метрики по её идентификатору.

    Kafka Broker
        Описание: Посредник для обмена сообщениями между Producer и Consumer.
        Топик: metrics-topic.

# Конфигурация
Docker Compose
Проект использует Docker Compose для оркестрации всех сервисов. Конфигурация docker-compose.yml обеспечивает автоматическое развёртывание и настройку всех необходимых контейнеров.

    version: '3'
    
    services:
      zookeeper:
        image: wurstmeister/zookeeper
        ports:
          - "2181:2181"
    
      kafka:
        image: wurstmeister/kafka
        ports:
          - "9092:9092"
        environment:
          KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
          KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
          KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT
          KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
          KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    
      producer:
        image: task3_producer
        build:
          context: .
          dockerfile: metric-producer/Dockerfile
        depends_on:
          - kafka
        environment:
          SPRING_KAFKA_BOOTSTRAP_SERVERS: kafka:9092
        ports:
          - "8080:8080"
    
      consumer:
        image: task3_consumer
        build:
          context: .
          dockerfile: metric-consumer/Dockerfile
        depends_on:
          - kafka
        environment:
          SPRING_KAFKA_BOOTSTRAP_SERVERS: kafka:9092
        ports:
          - "8081:8081"
          

# Запуск системы

Для запуска всех микросервисов используйте Docker Compose:

    docker-compose up --build

# Остановка системы

Для остановки всех микросервисов:

    docker-compose down

# Использование API
# Отправка метрик

Метрики можно отправлять с помощью следующего запроса:

    curl -X POST http://localhost:8081/metrics -H "Content-Type: application/json" -d '{"type": "temperature",
        "value": "100"}'

# Получение всех метрик

Для получения списка всех метрик:

    curl http://localhost:8080/metrics

# Получение метрики по ID

Для получения конкретной метрики по её идентификатору:

    curl http://localhost:8080/metrics/{id}

#Заключение
Эта система мониторинга предоставляет эффективный способ сбора, обработки и анализа метрик работы приложения с использованием микросервисной архитектуры и Apache Kafka. Все компоненты системы упакованы в Docker контейнеры, что обеспечивает их портативность и лёгкость в развертывании.
