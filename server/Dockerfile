FROM jenkins/jenkins:lts

# Switch to root to install Docker
USER root

# Install Docker
RUN apt-get update && apt-get install -y docker.io && rm -rf /var/lib/apt/lists/*

# Switch back to the jenkins user
USER jenkins
