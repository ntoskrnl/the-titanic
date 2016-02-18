# Software Development Plan #
**Work name of the project:** The Titanic

**Problem:** create cross-platform client-server application, which provides users to play Russian billiard via the internet.

## Algorithm and logic ##

Server supports database (_SQLite DB_) of registered users and provides access to the database via _Java Socket_. Client software interacts with server through specific protocol (see [Server Protocol](ServerProtocol.md) for details).

During entire process of the game one client sends commands to the server (e.g. to inform about a change in the game), and server processes the data and redirects requests to another client (if needed).

The data, which are transmitted between client and server, include information about current user status (authentication, waiting, playing, AFK, busy), personal information (login, name, location, gender), game changes (make move, balls number, win/loose). Some information must be encrypted to avoid disclosure of personal data.

All information about registered users is stored in the database. We will provide access to the database through our server (password required). Then we are planning to create a web-site where users can register, modify personal information and view their score.

Physics is implemented as a separate class with the only public method for computing location and speed of the balls after a small period of time.

The graphic user interface will be implemented with Java Swing package. For rendering objects we will use _Java3D_ package.

## Parallel computing ##
On the client side we will create separate thread for computing of the result of user's move.

On the server side there will be one basic thread waiting for incoming connections. When there is one incoming connection (new client appeared), the server initiates new thread for interaction with the client application (creates private room).

When the process of the game starts, server interacts with clients and plays a role of a tunnel between these two machines.

To provide multi-threading we will use standard _Java Threads_.

## Main goal and problems ##
**The main goal** of the project is to create cross-platform 3D game that provides people to play Russian billiard through the Internet.

We want to implement the following features:
  * **Dedicated game server**, which services the requests from client applications and support a database. The database will contain the information about users and their score.
  * **Client application** will enable the user to see the other ones to play with and will provide to play Russian Billiard in comfortable conditions of friendly user interface.
  * **3D rendering** of the game scene.
  * **Physical engine** will be based on a concept of the impact theory.
  * **Web-site** for registration of users. It will be also used to display current game stats (score, who is online).

The process of development can be divided into several parts:
  1. Server side implementation (connection, authentication, DataBase, server command protocol)
  1. Creating of the server management tool
  1. Physics implementation (maths + mechanics of collision)
  1. 3D Rendering of the game scene
  1. Client GUI development
  1. Testing and fixing bugs
  1. Translating to Russian
  1. Writing Manual
  1. Web-site
  1. Starting up.

## Estimates of the labor ##
The main difficulties of the project are:
  * implementing client-server interaction and game logic;
  * rendering of the game scene in 3D;
  * processing collisions of more than 3 balls.

## Project participants ##
  * **Anton Danshin** (server, GUI, web-site)
  * **Alexander Kozlovsky** (3D rendering, GUI, translation)
  * **Artur Gilmullin** (physics, manual, testing and fixing bugs)