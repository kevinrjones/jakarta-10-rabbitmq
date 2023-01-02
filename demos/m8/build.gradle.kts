val jackson_version: String by project
val rabbitmq_java_version: String by project
val javalin_version: String by project
val logback_version: String by project

plugins {
    java
}

allprojects {
    apply(plugin = "java")
    repositories {
        mavenCentral()
    }

    version = "0.1-SNAPSHOT"

    dependencies {
        implementation("ch.qos.logback:logback-classic:$logback_version")

        implementation("com.fasterxml.jackson.core:jackson-databind:$jackson_version")
        implementation("com.rabbitmq:amqp-client:$rabbitmq_java_version")
    }
}

project(":QuoteRabbitLibrary") {
    dependencies {
    }
}

project(":RegisterUserConsolePollConsumer") {
    dependencies {
        implementation(project(":QuoteRabbitLibrary"))
    }
}

project(":RegisterUserConsolePullConsumer") {
    dependencies {
        implementation(project(":QuoteRabbitLibrary"))
    }
}

project(":RegisterUserRoutingKeyConsoleConsumer") {
    dependencies {
        implementation(project(":QuoteRabbitLibrary"))
    }
}

project(":FanOutPubSubConsumer") {
    dependencies {
        implementation(project(":QuoteRabbitLibrary"))
    }
}

project(":TopicConsoleConsumer") {
    dependencies {
        implementation(project(":QuoteRabbitLibrary"))
    }
}

project(":RpcConsoleConsumer") {
    dependencies {
        implementation(project(":QuoteRabbitLibrary"))
    }
}

project(":RestService") {
    dependencies {
        implementation(project(":QuoteRabbitLibrary"))
        implementation("io.javalin:javalin:$javalin_version")
    }
}


