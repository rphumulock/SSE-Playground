
const endpoints = [
  "/sse/service-executor",
  "/sse/httpServletResponse",
  "/sse/thread",
];

for (let i = 0; i <= endpoints.length - 1; i++) {
  (function (index) {
    const eventsDiv = document.createElement("div");
    const button = document.createElement("button");
    button.style.height = "50px";
    button.style.width = "100px";
    button.innerHTML = "Start SSE";

    eventsDiv.id = `${index}`;
    const header = document.createElement("h1");
    header.innerHTML = `Event Stream ${endpoints[i]}`;
    eventsDiv.appendChild(header);
    eventsDiv.appendChild(button);
    eventsDiv.style.height = "500px";
    eventsDiv.style.overflow = "scroll";
    eventsDiv.style.border = "3px solid black";
    eventsDiv.style.padding = "5px";
    document.body.appendChild(eventsDiv);

    button.addEventListener("click", () => {

      const eventSource = new EventSource(`${endpoints[i]}`, {
        withCredentials: true,
      });

      eventSource.addEventListener(`${endpoints[i]}`, function(e) {
        console.log(e.data);
        const message = document.createElement("p");
        message.textContent = `Received: ${e.data}`;
        eventsDiv.appendChild(message);
      }, false);

      eventSource.onopen = function () {
        console.log("Event stream opened");
      };

      eventSource.onmessage = function (event) {
        console.log("Event");
        console.log(event);
        const message = document.createElement("p");
        message.textContent = `Received: ${event.data}`;
        eventsDiv.appendChild(message);
      };

      eventSource.onend = function () {
        console.log("Event stream ended");
        eventSource.close();
      };

      eventSource.onerror = function () {
        console.log("Error stream ended");
        eventSource.close();
      };
    });
  })(i);
}