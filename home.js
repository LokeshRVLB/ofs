var persons = [];
var addresses = [];
var headerProperties = ['ID', 'First Name', 'Last Name', 'E-mail', 'Birth Date'];
var logger = document.getElementById('log');
var serviceConnected = false;
var idCount = 0;
var displayError = false;

var Person = function () {
    this.id = 0;
    this.firstName;
    this.lastName;
    this.email;
    this.address;
    this.dob;
}

var Address = function () {
    this.id = 0;
    this.street;
    this.city;
    this.postalCode;
}

var loadApp = function () {
    requestHelper("assests/persons.json", "GET", readPersons);
}


var showPopup = (element) => {
    let popup = document.getElementById('popup');
    let form = document.getElementById('personForm');
    form.reset();

    if (element.id === 'addPerson') {
        document.getElementById("popUpHeading").innerHTML = "Enter New Person Details";
        popup.style.display = 'block';
        return;
    }

    if (element.id === 'closePopup') {
        popup.style.display = 'none';
        return;
    }

    let person = persons[element.id];
    document.getElementById("popUpHeading").innerHTML = "Update Person With ID : " + person.id;
    document.getElementById("firstName").value = person.firstName;
    document.getElementById("lastName").value = person.lastName;
    document.getElementById("email").value = person.email;
    document.getElementById("dob").value = person.dob;
    document.getElementById("street").value = person.address.street;
    document.getElementById("city").value = person.address.city;
    document.getElementById("postalCode").value = person.address.postalCode;
    popup.style.display = 'block';

}

var requestHelper = function (url, method, callback, requestBody) {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
    if (this.readyState < 4) {
        log("loading...", "log info")
    }

    //if the service response is positive, display details
    if (this.readyState === 4 && this.status === 200) {
        console.log(displayError);
        if (! displayError) {
            logger.style.display = "none";
        }
        callback(this.responseText);
        return;
    }
    if (this.readyState === 4 && this.status !== 200) {
        //if the service response is negative display error message
        displayError = true;
        log("Bad request", "log failure");
    }
    }
    request.open(method, url, true);
    if (method === "POST" || method === "PUT"){
        request.send(requestBody);
        return;
    }
    request.send();
}

var login = function () {

}

var readPersons = function (response) {
    console.log("request inherit")
    persons = JSON.parse(response);
    let personList = document.getElementById('personList');
    let header = document.createElement("tr");
    header.className = "row"
    for (index in headerProperties) {
        let headerProperty = document.createElement("th");
        headerProperty.appendChild(document.createTextNode(headerProperties[index]));
        header.appendChild(headerProperty);
    }
    personList.appendChild(header);
    for (let i = 0; i < persons.length; i++) {
        personList.appendChild(constructRow(persons[i]));
    }
}

var constructRow = function (rowData) {
    let row = document.createElement("tr");
    row.className = "row"
    row.id = idCount++;
    row.addEventListener("click", function() { showPopup(this); });
    for (key in rowData) {
        if (key === "address") { addresses[idCount] = rowData[key]; continue; }
        let column = document.createElement("td");
        let columnData = document.createTextNode(rowData[key]);
        column.appendChild(columnData);
        row.appendChild(column);
    }
    return row;
}

var createPerson = function () {
    let person = getFormDetails();
    person.id = idCount + 1;
    address.id = person.id;
    persons.push(person);
    console.log(JSON.stringify(person));
    log("person created successfully", "log info", 5000);
    document.getElementById('personList').appendChild(constructRow(person));
    popup.style.display = 'none';

}

var updatePerson = function (element) {
    let person = getFormDetails();
    person.id = element[0];
    address.id = person.id;
    document.getElementById(element.id)
}

var getFormDetails = function () {
    let person = new Person();
    let address = new Address();
    person.firstName = document.getElementById('firstName').value;
    person.lastName = document.getElementById('lastName').value;
    person.email = document.getElementById('email').value;
    person.dob = document.getElementById('dob').value;
    address.street = document.getElementById('street').value;
    address.city = document.getElementById('city').value;
    address.postalCode = document.getElementById('postalCode').value;
    person.address = address;
    return person;
}

var log = function (message, type, time) {
    logger.style.display = "none";
    logger.className = type;
    logger.innerHTML = message;
    logger.style.display = "block";
    if (time !== undefined) {
        setTimeout(function () {
            logger.innerHTML = "";
            logger.style.display = "none";
        }, time);
    }
}
