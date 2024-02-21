# A Plaground to mess around with Server Sent Events!

Server-Sent Events (SSE) is a technology that enables servers to push real-time updates to web browsers over HTTP. It provides a simple and efficient way to send a continuous stream of data from the server to the client, allowing for real-time updates without the need for complex client-server communication protocols like WebSockets.

##### Caveats

We're working with 7 event sources. The reason I'm doing this is to show that browsers only allow 6 http1.1-connections per domain. If you have 7 events - the 7th event will have to wait for the other ones to finish in order to start.
