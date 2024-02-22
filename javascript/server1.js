const express = require("express");
const favicon = require("serve-favicon");
const path = require("path");
const { randomUUID } = require("crypto");

const app = express();
const http = require("http").Server(app);

// Serve the favicon
app.use(favicon(path.join(__dirname, "public", "favicon.ico")));

// Serve static files from the public directory
app.use(express.static("public"));

// Route for serving the index.html file
app.get("/", (req, res) => {
  res.sendFile(path.join(__dirname, "/public/indexhttp1.1.html"));
});

// Server-Sent Events endpoint
for (let i = 1; i <= 8; i++) {
  app.get(`/${i}`, (req, res) => {
    res.setHeader("Content-Type", "text/event-stream");
    res.setHeader("Cache-Control", "no-cache");
    res.setHeader("Connection", "keep-alive");

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
    req.on("close", () => {
      clearInterval(intervalId);
    });
  });
}

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port http://localhost:${PORT}`);
});
