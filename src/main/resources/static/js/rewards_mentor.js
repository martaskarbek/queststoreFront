const container = document.querySelector(".items-content");
const rightBar = document.getElementById("right-bar-left")

function getMentorData() {
    fetch(`http://localhost:8000/rewards_mentor`)
        .then(function (response) {
            return response.json();
        })
        .then(function (mentor) {
            innerMentor(mentor);
        })
}

function innerMentor(mentor){

    let htmlString = '
        <img alt="severus" src="/static/img/severus.png">
        <h2>Name: ${mentor.first_name}</h2><br>
        <h2>Surname: ${mentor.last_name}</h2><br>
        <h2>Spec: Code brewing</h2><br>
    ';

    console.log(htmlString);
    container.innerHTML = htmlString;
}

//getMentorData();






