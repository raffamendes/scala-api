FROM registry.access.redhat.com/ubi8/openjdk-11:latest

USER root

RUN rm -f /etc/yum.repos.d/bintray-rpm.repo && \
    curl -L https://www.scala-sbt.org/sbt-rpm.repo > sbt-rpm.repo && \
    mv sbt-rpm.repo /etc/yum.repos.d/ && \
    microdnf install sbt && \
    sbt help && \    
    microdnf clean all    && rm -rf /var/cache/

USER 1001


ENTRYPOINT ["/bin/sh", "-c"]
