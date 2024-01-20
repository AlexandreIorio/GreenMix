![header](https://capsule-render.vercel.app/api?type=waving&height=150&color=38761d&text=Green%20Mix&fontColor=E8F4FF)

*Green Mix: Where you can grow your virtual garden and watch your green thumb thrive!*

<div>
<img src="https://img.shields.io/github/last-commit/AlexandreIorio/GreenMix?style=flat-square&color=5D6D7E" alt="git-last-commit" />
<img src="https://img.shields.io/github/commit-activity/m/AlexandreIorio/GreenMix?style=flat-square&color=5D6D7E" alt="GitHub commit activity" />
<img src="https://img.shields.io/github/languages/top/AlexandreIorio/GreenMix?style=flat-square&color=5D6D7E" alt="GitHub top language" />
</div>

## 🚀 Green Mix: Overview

Ajouter descritpion + fonctiannalité de l'appli quand c'est terminé

<br>

---

## Explains what the web application is for with its documented application protocol interface (API)

<br>

---

## Group composition and roles

1. **Alexandre Iorio** - Role: Coordinator
    - Responsibilities:
        - Communication with other groups and teachers.
        - Preparation and presentation of the project.
        - Managing the GitHub repository and writing the README.


2. **Colin Jaques** - Role: Architecture
    - Responsibilities:
        - Collaboration with the Back-end Developer for API integration.
        - Implementation of the CRUD API on the virtual machine.
        - DNS configuration for access via domain name.
        - Ensuring the security and availability of the web application.


3. **Theodros Mulugeta** - Role: System Administration
    - Responsibilities:
        - Configuration and management of the virtual machine.
        - Installation of Docker and Docker Compose on the VM.
        - Configuration of the reverse proxy (Traefik) and management of HTTPS certificates.


4. **Walid Slimani** - Role: Back-end Developer
    - Responsibilities:
        - Development of server-side logic and database.
        - Implementation of the CRUD API on the virtual machine.
        - Collaboration with the Front-end Developer for API integration.

<br>

---

## How to install and configure the server



### Access the virtual machine

1. Start your 
[VPN](https://intranet.heig-vd.ch/services/informatique/poste-de-travail/reseau/vpn/Pages/vpn.aspx).

2. In your terminal, run the following command: __ssh heiguser@10.190.132.59__

3. Then enter the password: __FTNXzTNJau3cskpc967l__

Other information:
- Hostname: IICT-MV358-DAI 
- internal IP: 10.190.132.59 
- external IP: 185.144.38.59


<br>

### Update the virtual machine

Once you have access to the virtual machine, you can update it with the
following commands:

```sh
# Update the list of packages
sudo apt update

# Upgrade the packages
sudo apt upgrade
```

This will ensure that the virtual machine is up-to-date.

<br>

### Install `apache2-utils`:

```sh
# Install apache2-utils
sudo apt install apache2-utils
```

This will ensure that you can use the `htpasswd` command line tool to create
users for the Traefik dashboard in a future step.

<br>

### Install Docker using the apt repository:

1. Set up Docker's apt repository:

```sh
# Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install ca-certificates curl gnupg
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
```

2. Install the Docker packages:

```sh
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

3. Verify that the Docker Engine installation is successful by running the hello-world image.

```sh
sudo docker run hello-world
```

<br>

### Linux post-installation steps for Docker Engine (manage Docker as a non-root user):

1. Create the docker group:

```sh
sudo groupadd docker
```

2. Add your user to the docker group:

```sh
sudo usermod -aG docker $USER
```

3. Log out and log back in so that your group membership is re-evaluated.


4. Verify that you can run docker commands without sudo:

```sh
docker run hello-world
```


<br>

### Install Docker Compose using the repository:

1. Update the package index, and install the latest version of Docker Compose:

```sh
sudo apt-get update
sudo apt-get install docker-compose-plugin
```

2. Verify that Docker Compose is installed correctly by checking the version.

```sh
docker compose version
```

Expected output:

```sh
Docker Compose version vN.N.N
```

<br>

### Clone the repository on the virtual machine with https

```sh
git clone https://github.com/AlexandreIorio/GreenMix.git
```
This will ensure that you can clone the repository without the need of an SSH
configuration.

<br>

---

## How to deploy, run and access the web application

<br>

---

## How to configure the DNS zone to access the web application

<br>

---

## How to build and publish the web application with Docker

### Clone and build the repository.

1. Clone the repository:
```sh
git clone https://github.com/AlexandreIorio/GreenMix.git
```

2. Change to the project directory:
```sh
cd GreenMix
```

3. Build the web application:
```sh
# Download the dependencies
./mvnw dependency:resolve clean compile

# Package the application
./mvnw package
```

<br>

### Once the Jar are generated, you can build the docker images.

1. Navigate to the server's directory. 
2. Build the image using the Docker build command.
  ```bash
  docker build -t ghcr.io/theodrosrun/greenmix:v1.0.0 .
  ```
be sure to adapt the version number as needed.

<br>

### Publishing to GitHub

After building the images, you can publish them to github. Be sure to be logged and have right to publish image.

Once done, you can publish it to github by using :

```sh
docker push ghcr.io/theodrosrun/greenmix:v1.0.0:v1.0.0
```

<br>

### Run it directly via Docker

First you must pull the image.

```sh
docker pull ghcr.io/theodrosrun/greenmix:v1.0.0
```

<br>

### Run it with docker compose

1. **Running with Docker Compose**:

In the directory containing your `docker-compose.yml`, start the services. This will pull the images from github and start the server and clients.
```bash
docker-compose up
```

2. **Interacting with the Application**:



3. **Shutting Down the Application**:
```bash
docker-compose down
```
      
<br>

---

## How to interact with the web application with examples and outputs using curl
