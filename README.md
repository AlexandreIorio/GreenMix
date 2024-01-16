## Group composition and roles

## How to install and configure the server

<br>

#### Access the virtual machine

You will have to be connected to the
[VPN](https://intranet.heig-vd.ch/services/informatique/poste-de-travail/reseau/vpn/Pages/vpn.aspx).
It is mandatory.

- terminal connexion: ssh heiguser@10.190.132.59 
- username: heiguser
- password: FTNXzTNJau3cskpc967l

<br>

#### Update the virtual machine and install `apache2-utils`

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

#### Install `apache2-utils` with the following command:

```sh
# Install apache2-utils
sudo apt install apache2-utils
```

This will ensure that you can use the `htpasswd` command line tool to create
users for the Traefik dashboard in a future step.

<br>

#### Install Docker and Docker Compose on the virtual machine

Install Docker and Docker Compose as seen in the
[Docker and Docker Compose](https://github.com/heig-vd-dai-course/heig-vd-dai-course/tree/main/10-docker-and-docker-compose)
chapter.

As the virtual machine is running Linux, follow the instructions for Linux. Do
not forget the post-installation steps. This will ensure that Docker and Docker
Compose start automatically when the virtual machine is rebooted and that you
can use Docker without the need of using `sudo` (= admin) each time.

Check that you can run the `hello-world` Docker image as seen in the Docker and
Docker Compose chapter to ensure you can use Docker on the virtual machine
without the need of `sudo`.

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