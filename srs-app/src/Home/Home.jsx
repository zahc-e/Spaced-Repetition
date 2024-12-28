import React from 'react';
import './Home.css'
import { useNavigate } from 'react-router-dom';


function Home() {

    let nav = useNavigate()
  
    return (
        <>
            <h1>SRS App</h1>
            <div className="card">
            <button onClick={() => {nav('/decks')}}>
                decks
            </button>
            <p>
                Home page. Need more buttons.
            </p>
            </div>
            <p className="read-the-docs">
            Also need to edit css
            </p>
        </>
    );
}

export default Home
