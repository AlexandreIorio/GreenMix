## Group composition and roles

<br>

---

## How to install and configure the server

<br>

#### Access the virtual machine

You will have to be connected to the
[VPN](https://intranet.heig-vd.ch/services/informatique/poste-de-travail/reseau/vpn/Pages/vpn.aspx).
It is mandatory. Then, in your terminal, run the following command:

```sh
ssh heiguser@10.190.132.59 
```

- username: heiguser
- password: FTNXzTNJau3cskpc967l

<br>

#### Update the virtual machine

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

#### Install `apache2-utils`:

```sh
# Install apache2-utils
sudo apt install apache2-utils
```

This will ensure that you can use the `htpasswd` command line tool to create
users for the Traefik dashboard in a future step.

<br>

#### Install Docker using the apt repository:

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

#### Linux post-installation steps for Docker Engine (manage Docker as a non-root user):

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

#### Install Docker Compose using the repository:

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

#### Clone the repository on the virtual machine with https

```sh
git clone https://github.com/AlexandreIorio/GreenMix.git
```
This will ensure that you can clone the repository without the need of an SSH
configuration.

<br>

---

## How to deploy, run and access the web application

## How to configure the DNS zone to access the web application

## How to build and publish the web application with Docker

## How to interact with the web application with examples and outputs using curl