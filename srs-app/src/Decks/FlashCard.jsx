import './FlashCard.css'
import React, {useEffect, useState} from 'react'
import { useNavigate } from 'react-router-dom';

class Card {
    constructor(front,back,id,due,stability,difficulty,elapsedDays,scheduledDays,lapses,state,lastReview) {
        this.front = front
        this.back = back
        this.id = id
        this.due = due
        this.stability = stability
        this.difficulty = difficulty
        this.elapsedDays = elapsedDays
        this.scheduledDays = scheduledDays
        this.lapses = lapses
        this.state = state
        this.lastReview = lastReview
    }
}

function FlashCard() {

    let nav = useNavigate()
    
    const [data,setData] = useState([])
    useEffect(() => {
        fetch('http://localhost:8081/notes')
            .then(res => res.json())
            .then(data => setData(data))
            .catch(err => console.log(err));
    }, [])
    
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var yyyy = today.getFullYear();
    today = mm + '/' + dd + '/' + yyyy;

    const reviewList = [];
    const [index,setIndex] = useState(0);
    
    for (let i = 0; i < data.length; i++) {

        var due = new Date(data[i].due);
        var dd = String(due.getDate()).padStart(2, '0');
        var mm = String(due.getMonth() + 1).padStart(2, '0');
        var yyyy = due.getFullYear();
        due = mm + '/' + dd + '/' + yyyy;

        const newCard = new Card(data[i].front, data[i].back, data[i].id, due, data[i].stability, data[i].difficulty, data[i].elapsedDays, data[i].scheduledDays, data[i].lapses, data[i].state, data[i].lastReview)
        if (newCard.due <=  today) {
            reviewList.push(newCard);
        }
    }
    
   
    const goodCard = () => {
        const response = fetch('http://localhost:8080/review', {
                method: 'POST',
                headers: {'Content-Type':'application/json','Accept':'application/json'},
                body: JSON.stringify({"card": reviewList[index], "rating": 3})
            }
        );

        if (response.ok) {
            const responseData = response.json();
            console.log("response below")
            console.log(responseData)
        } else {
            console.log("not working")
        }
        console.log(reviewList[index]?.id)

        if (reviewList.length - 1 > index) {
            setIndex(index + 1)
            var back = document.getElementById('cardBack');
            back.style.display = 'none';
            var next = document.getElementById('nextCard')
            next.style.display = 'none'
            var flip = document.getElementById('flipCard')
            flip.style.display = 'block'
        }
        if (reviewList.length - 1 == index) {
            var back = document.getElementById('cardBack');
            back.style.display = 'none';
            var next = document.getElementById('nextCard')
            next.style.display = 'none'
            var flip = document.getElementById('flipCard')
            flip.style.display = 'none'
            var front = document.getElementById('cardFront')
            front.style.display = 'none'
            var end = document.getElementById('endReview')
            end.style.display = 'block'
        }
    }
    const easyCard = () => {
        const response = fetch('http://localhost:8080/review', {
                method: 'POST',
                headers: {'Content-Type':'application/json','Accept':'application/json'},
                body: JSON.stringify({"card": reviewList[index], "rating": 4})
            }
        );

        if (response.ok) {
            const responseData = response.json();
            console.log("response below")
            console.log(responseData)
        } else {
            console.log("not working")
        }
        console.log(reviewList[index]?.id)

        if (reviewList.length - 1 > index) {
            setIndex(index + 1)
            var back = document.getElementById('cardBack');
            back.style.display = 'none';
            var next = document.getElementById('nextCard')
            next.style.display = 'none'
            var flip = document.getElementById('flipCard')
            flip.style.display = 'block'
        }
        if (reviewList.length - 1 == index) {
            var back = document.getElementById('cardBack');
            back.style.display = 'none';
            var next = document.getElementById('nextCard')
            next.style.display = 'none'
            var flip = document.getElementById('flipCard')
            flip.style.display = 'none'
            var front = document.getElementById('cardFront')
            front.style.display = 'none'
            var end = document.getElementById('endReview')
            end.style.display = 'block'
        }
    }
    const hardCard = () => {
        const response = fetch('http://localhost:8080/review', {
                method: 'POST',
                headers: {'Content-Type':'application/json','Accept':'application/json'},
                body: JSON.stringify({"card": reviewList[index], "rating": 2})
            }
        );

        if (response.ok) {
            const responseData = response.json();
            console.log("response below")
            console.log(responseData)
        } else {
            console.log("not working")
        }
        console.log(reviewList[index]?.id)

        if (reviewList.length - 1 > index) {
            setIndex(index + 1)
            var back = document.getElementById('cardBack');
            back.style.display = 'none';
            var next = document.getElementById('nextCard')
            next.style.display = 'none'
            var flip = document.getElementById('flipCard')
            flip.style.display = 'block'
        }
        if (reviewList.length - 1 == index) {
            var back = document.getElementById('cardBack');
            back.style.display = 'none';
            var next = document.getElementById('nextCard')
            next.style.display = 'none'
            var flip = document.getElementById('flipCard')
            flip.style.display = 'none'
            var front = document.getElementById('cardFront')
            front.style.display = 'none'
            var end = document.getElementById('endReview')
            end.style.display = 'block'
        }
    }
    const againCard = () => {
        const response = fetch('http://localhost:8080/review', {
                method: 'POST',
                headers: {'Content-Type':'application/json','Accept':'application/json'},
                body: JSON.stringify({"card": reviewList[index], "rating": 1})
            }
        );

        if (response.ok) {
            const responseData = response.json();
            console.log("response below")
            console.log(responseData)
        } else {
            console.log("not working")
        }
        console.log(reviewList[index]?.id)

        if (reviewList.length - 1 > index) {
            setIndex(index + 1)
            var back = document.getElementById('cardBack');
            back.style.display = 'none';
            var next = document.getElementById('nextCard')
            next.style.display = 'none'
            var flip = document.getElementById('flipCard')
            flip.style.display = 'block'
        }
        if (reviewList.length - 1 == index) {
            var back = document.getElementById('cardBack');
            back.style.display = 'none';
            var next = document.getElementById('nextCard')
            next.style.display = 'none'
            var flip = document.getElementById('flipCard')
            flip.style.display = 'none'
            var front = document.getElementById('cardFront')
            front.style.display = 'none'
            var end = document.getElementById('endReview')
            end.style.display = 'block'
        }
    }

    const flipCard = () => {
        var back = document.getElementById('cardBack');
        back.style.display = 'block';
        var next = document.getElementById('nextCard')
        next.style.display = 'block'
        var flip = document.getElementById('flipCard')
        flip.style.display = 'none'
    }
    
    return (
        <div style={{padding:"50px"}}>
            <h1 id="cardFront">{reviewList[index]?.front}</h1>
            <div id="cardBack">
                <p>{reviewList[index]?.back}</p>
            </div>
            <div id="nextCard">
                <button  onClick={againCard}>
                    again
                </button>
                <button  onClick={hardCard}>
                    hard
                </button>
                <button  onClick={goodCard}>
                    good
                </button>
                <button  onClick={easyCard}>
                    easy
                </button>
            </div>
            
            <button id="flipCard" onClick={flipCard}>
                flip card
            </button>
            <div id="endReview">
                <h3>Review finished for the day</h3>
                <button onClick={() => {nav('/decks')}}>
                    decks
                </button>
            </div>
        </div>
    )
}

export default FlashCard

