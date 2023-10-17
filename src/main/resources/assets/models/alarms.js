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
}


function AlarmViewModel (){
    var self = this;
    self.alarms = ko.observableArray([]);
    self.refresh = function () {
        fetch("/api/alarms").then(resp => resp.json()).then(j => {
                console.log(j);
                var a = j.map(d => new Alarm(d));
                self.alarms(a);
            });
    };
    refresh();
}

ko.applyBindings(AlarmViewModel);
