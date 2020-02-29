FROM ubuntu:18.04

# java 8
RUN apt-get update && apt-get install -y \
    openjdk-8-jdk \
    openjdk-8-jre

ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 \
    JRE_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre

# scala 2.12.8
RUN apt-get update && apt-get install -y wget && \
    wget www.scala-lang.org/files/archive/scala-2.12.8.deb && \
    dpkg -i scala-2.12.8.deb

# sbt
RUN apt-get install -y curl gnupg2 && \
    echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list && \
    curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add && \
    apt-get update && \
    apt-get install -y sbt

# vim, git, unzip
RUN apt-get install -y \
    vim \
    git \
    unzip 

# npm 6.8
RUN apt-get install -y npm && npm install -g npm@6.8.0 

EXPOSE 8000 9000 5000 8888

VOLUME /home/chrobak_1137045/projekt
