import React from 'react'
import './Decks.css'
import { useNavigate } from 'react-router-dom';

function Decks() {

    let nav = useNavigate()

    return(
        <div>
            <h1>Decks Page</h1>
            <div>
            <button onClick={() => {nav('/flashcard')}}>
                deck1
            </button>
            </div>
            <div>
            <button onClick={() => {nav('/')}}>
                back
            </button>
            </div>
            <p>
                deck based on Wolfram article on ChatGPT
            </p>
        </div>
    );
}

export default Decks;

