function Alarm(data) {
  this.id = ko.observable(data.id);
  this.date = ko.observable(data.date);
  this.tid = ko.observable(data.tid);
  this.aid = ko.observable(data.aid);
  this.severity = ko.observable(data.severity);
  this.message = ko.observable(data.message);
  this.state = ko.observable(data.state);
  this.group = ko.observable(data.group);
  this.link = ko.observable(data.link);
  this.description = ko.observable(data.description);
  this.visualSeverity = ko.observable(
    data.state === "CLEAR" ? data.state : data.severity
  );
}

function AlarmViewModel() {
  var self = this;
  self.webSocket = new WebSocket(
    "ws://" +
      window.location.hostname +
      ":" +
      window.location.port +
      "/alarmsocket"
  );
  self.alarms = ko.observableArray([]);
  self.selectedAlarm = ko.observable();
  self.connection = ko.observable("Disconnected");

  self.selected = function (alarm) {
    self.selectedAlarm(alarm);
  };

  self.refresh = function () {
    fetch("/api/alarms")
      .then((resp) => resp.json())
      .then((j) => {
        //console.log(j);
        var a = j.map((d) => new Alarm(d));
        self.alarms(a);
      });
  };

  self.createBlankAlarm = function () {
    var newDate = new Date().toISOString().replace("T", " ");
    newDate = newDate.substring(0, newDate.indexOf("."));

    self.selectedAlarm(
      new Alarm({
        tid: "tid",
        aid: "aid",
        date: newDate,
        severity: "INFO",
        message: "New Alarm",
        description: "New Alarm",
        state: "CLEAR",
        group: "default",
        link: "",
      })
    );
  };

  self.updateAlarm = function (form) {
    var json = ko.toJSON(self.selectedAlarm());
    fetch("/api/alarms", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: json,
    })
      .then((resp) => resp.json())
      .then((j) => {
        self.refresh();
      });
  };

  webSocket.onmessage = (event) => {
    console.log("Message: " + event.data);
    if (event.data != "pong") {
      self.refresh();
    } else {
        self.connection("Active");
    }
  };

  this.webSocket.onclose = () => {
    console.log("web socket closed for some reason!");
    self.connection("Offline");
  };

  setInterval(() => {
    self.connection("Connecting");
    this.webSocket.send("ping");
  }, 15000);

  refresh();
}

ko.applyBindings(AlarmViewModel);
