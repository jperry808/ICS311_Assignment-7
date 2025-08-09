# ICS311_Assignment-7
This is a simple Java program where each person is able to generate their own RSA public and private key pair. When a message is sent a random AES key is generated, then the message body is encrypted through AES-GCM. Afterwords the AES key is encrypted with the recievers RSA public key using RSA-OAEP. Finally, the router conducts a BFS to find a path to the reciever.

Feel free to play around with the main java file, you can add or remove edges to adjust connections or add new users.

# How to Run

1. Simply clone the repository via GitHub Desktop

2. Compile the program (make sure you are in the project root folder)

3. Run the program from the main file
