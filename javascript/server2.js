const http2 = require('http2');
const fs = require('fs');
const { randomUUID } = require('crypto');

const server = http2.createSecureServer({
  key: fs.readFileSync('server.key'), // Your SSL key path
  cert: fs.readFileSync('server.crt') // Your SSL certificate path
});

server.on('request', (req, res) => {
  if (req.url === '/') {
    // Serve the index.html file
    const indexPath = __dirname + '/public/indexhttp2.html';
    const indexHtml = fs.readFileSync(indexPath);
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end(indexHtml);
  } else {
    const endpointCount = 15; // Define the number of endpoints
    let foundEndpoint = false;

    for (let i = 1; i <= endpointCount; i++) {
        if (req.url === `/${i}`) {
          // Server-Sent Events endpoint
          res.writeHead(200, {
            'Content-Type': 'text/event-stream',
            'Cache-Control': 'no-cache',
            'Connection': 'keep-alive'
          });

          let eventCount = 0;

          const intervalId = setInterval(() => {
            if (eventCount >= 20) {
              const message = `data: All done!\n\n`;
              res.write(message);
              res.end(); // Close the connection
            } else {
              const id = randomUUID();
              const message = `id: ${id}\ndata: ${new Date().toLocaleTimeString()}\n\n`;
              res.write(message);
              eventCount++;
            }
          }, 1000);

          // Clean up on client disconnect
          req.on('close', () => {
            clearInterval(intervalId);
          });

          foundEndpoint = true;
          break;
        }
      }

      if (!foundEndpoint) {
        // For other endpoints or invalid URLs, return a 404 error
        res.writeHead(404);
        res.end('Not Found');
      }
  }
});

server.listen(3000, () => {
  console.log('Server is running on https://localhost:3000');
});
