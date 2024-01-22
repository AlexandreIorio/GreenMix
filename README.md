![header](https://capsule-render.vercel.app/api?type=waving&height=150&color=38761d&text=Green%20Mix&fontColor=E8F4FF)

*Green Mix: Where you can grow your virtual garden and watch your green thumb thrive!*

<div>
<img src="https://img.shields.io/github/last-commit/AlexandreIorio/GreenMix?style=flat-square&color=5D6D7E" alt="git-last-commit" />
<img src="https://img.shields.io/github/commit-activity/m/AlexandreIorio/GreenMix?style=flat-square&color=5D6D7E" alt="GitHub commit activity" />
<img src="https://img.shields.io/github/languages/top/AlexandreIorio/GreenMix?style=flat-square&color=5D6D7E" alt="GitHub top language" />
</div>

# Table of Contents

- [What the Web Application is For](#what-the-web-application-is-for)
- [API](#API)
- [How to install and configure the server](#How-to-install-and-configure-the-server)
- [How to build and publish the web application with Docker](#How-to-build-and-publish-the-web-application-with-Docker)
- [How to configure the DNS zone to access the web application](#How-to-configure-the-DNS-zone-to-access-the-web-application)
- [How to deploy, run and access the web application](#How-to-deploy-run-and-access-the-web-application)
- [How to interact with the web application with examples and outputs using curl](#How-to-interact-with-the-web-application-with-examples-and-outputs-using-curl)
- [Group composition and roles](#Group-composition-and-roles)

<br>

---

## What the web application is for

The web application provides an immersive virtual gardening experience, 
combining educational content with engaging activities like planting and harvesting. 
It's designed to be both entertaining and relaxing, offering users a unique way to learn 
about gardening and manage resources through a virtual wallet. The app is accessible to
users of all ages, making it a fun and interactive way to enjoy gardening virtually and learn 
strategic resource management.

### **Key Features**

1. **Gardening Activities**
    - Enables users to grow a variety of plants with unique care requirements and timelines.

2. **Use of Potions**
    - Offers special potions to enhance plant growth, increase yield.

3. **Real-Time Plant Growth**
    - Plants grow in real-time, requiring ongoing care and attention from the user.

4. **Interactive Garden Management**
    - Lets users design and manage their virtual garden, making decisions on plant placement and resource use.

<br>

---

## API

### Customer

- `GET /profile`
    - Retrieves the current customer's profile.
    - **Response**: Customer's information in JSON format.
    - **Status Codes**:
        - `200 OK`: Successfully retrieved.


- `POST /profile/wallet/{money}`
    - Adds money to the customer's wallet.
    - **Response**: Success or error message.
    - **Status Codes**:
        - `200 OK`: Money added successfully.
        - `400 Bad Request`: Invalid money format.


- `PUT /profile/update/{username}`
    - Updates the customer's username.
    - **Response**: Success or error message.
    - **Status Codes**:
        - `200 OK`: Username updated successfully.
        - `400 Bad Request`: Invalid username format.


- `DELETE /profile/tool/{tool}`
    - Removes a tool from the customer's profile.
    - **Response**: Success or error message.
    - **Status Codes**:
        - `200 OK`: Tool removed successfully.
        - `406 Not Acceptable`: Tool does not exist.

    
### Plantation

- `GET /grow/{plantType}`
    - Initiates the growth of a specific type of plant.
    - **Response**: Success or error message.
    - **Status Codes**:
        - `200 OK`: Plant is growing.
        - `402 Payment Required`: Insufficient funds.
        - `400 Bad Request`: Invalid plant type.


- `GET /harvest/{plantId}`
    - Harvests a fully-grown plant.
    - **Response**: Success message with profit details.
    - **Status Codes**:
        - `200 OK`: Harvest successful.
        - `422 Unprocessable Content`: Plant not fully grown.
        - `400 Bad Request`: Invalid plant ID.
        - `406 Not Acceptable`: Plant does not exist.


- `GET /potion/{potionType}/{plantId}`
    - Applies a potion to a plant.
    - **Response**: Success or error message.
    - **Status Codes**:
        - `200 OK`: Potion used successfully.
        - `402 Payment Required`: Insufficient funds for potion.
        - `400 Bad Request`: Invalid potion type or plant ID.
        - `406 Not Acceptable`: Plant does not exist.


- `GET /garden`
    - Retrieves all plants in the customer's garden.
    - **Response**: List of plants in JSON format.
    - **Status Codes**:
        - `200 OK`: Successfully retrieved.

<br>

---

## How to install and configure the server

### Access the virtual machine

- Hostname: IICT-MV358-DAI
- internal IP: 10.190.132.59
- external IP: 185.144.38.59

<br>

1. Start your 
[VPN](https://intranet.heig-vd.ch/services/informatique/poste-de-travail/reseau/vpn/Pages/vpn.aspx).

2. Login

Option 1: SSH with password 
- In your terminal: **ssh heiguser@10.190.132.59**
- Then enter the password: ...

Option 2: SSH without password if you key is added to the server
- In your terminal: **ssh -i C:\path\to\public_key heiguser@10.190.132.59**
- Then enter the password: ...

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

1. Navigate to the project's directory.
2. Build the image using the Docker build command.
  ```bash
  docker build -t ghcr.io/theodrosrun/greenmix:v1.0 .
  ```
be sure to adapt the version number as needed.

<br>

### Publishing to GitHub

After building the images, you can publish them to github. Be sure to be logged and have right to publish image. Once done, you can publish it to github by using :

```sh
docker push ghcr.io/theodrosrun/greenmix:v1.0
```

<br>

---

## How to configure the DNS zone to access the web application

The DNS setup for `greenmix.dedyn.io` is configured through deSEC, a DNS hosting service. To direct traffic to our web applications, subdomains are used. Here's how they are currently configured:

### A Records for Subdomains

- **Subdomain `app`**:
    - **Type:** A (Address Record)
    - **Name:** app.greenmix.dedyn.io
    - **Value (IP Address):** 185.144.38.59
    - **TTL:** 3600 seconds


- **Subdomain `whoami`**:
    - **Type:** A
    - **Name:** whoami.greenmix.dedyn.io
    - **Value (IP Address):** 185.144.38.59
    - **TTL:** 3600 seconds


- **Subdomain `traefik`**:
    - **Type:** A
    - **Name:** traefik.greenmix.dedyn.io
    - **Value (IP Address):** 185.144.38.59
    - **TTL:** 3600 seconds

<br>

---

## How to deploy, run and access the web application

### Clone the repository on the virtual machine with https

```sh
git clone https://github.com/AlexandreIorio/GreenMix.git
```
This will ensure that you can clone the repository without the need of an SSH
configuration.

<br>

### Run it with docker compose

1. **Running with Docker Compose**:

In the directory containing your `docker-compose.yml`, start the services. This will pull the images from github and start the server and clients.
```bash
docker-compose up
```

2. **Interacting with the Application**:

For interaction see the chapter: [How to interact with the web application with examples and outputs using curl](#How-to-interact-with-the-web-application-with-examples-and-outputs-using-curl)


3. **Shutting down the Application**:
```bash
docker-compose down
```

<br>

---

## How to interact with the web application with examples and outputs using curl

### Retrieve Customer Profile

**Curl Command:**
```bash
curl -X GET http://app.greenmix.dedyn.io/profile
```

**Expected Output:**
```json
{
  "username": "Jerry",
  "wallet": 2,
  "plants": [
    {
      "harvest": 3,
      "duration": 5,
      "purchasePrice": 3,
      "hasGrown": true,
      "id": 0,
      "type": "BITBUD",
      "sellingPricePrice": 5
    },
    null,
    null,
    null,
    null,
    null
  ],
  "tools": [
    {
      "name": "FOURCHE"
    },
    {
      "name": "CISAILLE"
    }
  ]
}
```

<br>

### Add Money to Wallet

**Curl Command:**
```bash
curl -X POST http://app.greenmix.dedyn.io/profile/wallet/{money}
```

*Replace `{money}` with the amount you want to add.*

**Expected Output:**
```
"Money added successfully."
```

<br>

### Update Customer Username

**Curl Command:**
```bash
curl -X PUT http://app.greenmix.dedyn.io/profile/update/{username}
```

*Replace `{username}` with the new username.*

**Expected Output:**
```
"Username updated successfully."
```

<br>

### Delete a Tool from the Profile

**Curl Command:**
```bash
curl -X DELETE http://app.greenmix.dedyn.io/profile/tool/{tool}
```

*Replace `{tool}` with the name of the tool to remove.*

**Expected Output:**
```
"Tool removed successfully."
```

<br>

### Grow a Plant

**Curl Command:**
```bash
curl -X GET http://app.greenmix.dedyn.io/grow/{plantType}
```

*Replace `{plantType}` with the type of plant to grow.*

**Expected Output:**
```
"Plant is growing."
```

<br>

### Harvest a Plant

To harvest a fully-grown plant:

**Curl Command:**
```bash
curl -X GET http://app.greenmix.dedyn.io/harvest/{plantId}
```

*Replace `{plantId}` with the ID of the plant to harvest.*

**Expected Output:**
```
"Harvest successful. Profit: {profitAmount}"
```

<br>

### Use a Potion on a Plant

**Curl Command:**
```bash
curl -X GET http://app.greenmix.dedyn.io/potion/{potionType}/{plantId}
```

*Replace `{potionType}` with the type of potion and `{plantId}` with the plant ID.*

**Expected Output:**
```
"Potion used successfully."
```

<br>

### View All Plants in the Garden

**Curl Command:**
```bash
curl -X GET http://app.greenmix.dedyn.io/garden
```

**Expected Output:**
```json
[
  {
    "harvest": 3,
    "duration": 5,
    "purchasePrice": 3,
    "hasGrown": true,
    "id": 0,
    "type": "BITBUD",
    "sellingPricePrice": 5
  },
  null,
  null,
  null,
  null,
  null
]
```

<br>

---

If you want a sweet susucre go to http://app.greenmix.dedyn.io/menu

## Group composition and roles

1. **Alexandre Iorio** - Role: Coordination and Management
    - Responsibilities:
        - Communication with other groups and teachers.
        - Preparation and presentation of the project.
        - Managing the GitHub repository and writing the README.


2. **Colin Jaques** - Role: Software Architecture
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


4. **Walid Slimani** - Role: Back-end Software Development
    - Responsibilities:
        - Development of server-side logic and database.
        - Implementation of the CRUD API on the virtual machine.
        - Collaboration for API integration.

<br>
