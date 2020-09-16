const container = document.querySelector(".items-content");
const rightBar = document.getElementById("")







function getRewards() {
    fetch(`http://localhost:8000/rewards_mentor`)
        .then(function (response) {
            return response.json();
        })
        .then(function (rewards) {
            innerRewards(rewards);
        })
}

function innerRewards(rewards){

    let htmlString = '';
    for (let reward of rewards) {
        htmlString += createItem(reward);

    }

    console.log(htmlString);
    container.innerHTML = htmlString;
}

function createItem(reward) {
    return `                 
                 
     <div class="item-content">
            <div class="item" id="artifact-${reward.id}">
                <p class="item-desc">${reward.description}</p>
                <p class="item-name">${reward.name}</p>
            </div>
            <p>Price: ${reward.price} mc</p>
    </div>
                                                              
`;
}

//getRewards();

// function innerRewards(rewards) {
//     let itemContent = document.createElement("div");
//     table.innerHTML = "<tr><th>NAME</th><th>SURNAME</th><th>EMAIL</th></tr>";
//     container.appendChild(table);
//
//     students.forEach(student => {
//         let row = document.createElement("tr");
//         row.innerHTML = `
//             <td>${student.name}</td>
//             <td>${student.surname}</td>
//             <td>${student.email}</td>
//         `;
//         table.appendChild(row);
//     });
// }

